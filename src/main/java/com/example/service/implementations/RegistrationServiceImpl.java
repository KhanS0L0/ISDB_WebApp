package com.example.service.implementations;

import com.example.dto.AuthRegDTO.RegRequestDTO;
import com.example.entity.pivots.worker.Artist;
import com.example.entity.pivots.worker.Screenwriter;
import com.example.entity.pivots.worker.Worker;
import com.example.entity.users.User;
import com.example.exceptions.alreadyExistExceptions.UserAlreadyExistsException;
import com.example.repository.ArtistRepository;
import com.example.repository.ScreenwriterRepository;
import com.example.repository.WorkerRepository;
import com.example.service.interfaces.RegistrationService;
import com.example.service.interfaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RegistrationServiceImpl implements RegistrationService {

    private final UserService userService;
    private final WorkerRepository workerRepository;
    private final ArtistRepository artistRepository;
    private final ScreenwriterRepository screenwriterRepository;

    @Autowired
    public RegistrationServiceImpl(UserService userService,
                                   WorkerRepository workerRepository,
                                   ArtistRepository artistRepository,
                                   ScreenwriterRepository screenwriterRepository) {
        this.userService = userService;
        this.workerRepository = workerRepository;
        this.artistRepository = artistRepository;
        this.screenwriterRepository = screenwriterRepository;
    }

    @Override
    public void signUp(RegRequestDTO requestDTO) throws UserAlreadyExistsException {
        String username = requestDTO.getUsername();
        User user = userService.findByUsername(username);
        if(user != null){
            throw new UserAlreadyExistsException("User with username: " + username + " already exist");
        }

        Worker registeredWorker = workerRepository.save(Worker.createWorker(requestDTO));
        if(requestDTO.isArtist()){
            artistRepository.save(new Artist(registeredWorker));
        }
        if(requestDTO.isScreenwriter()){
            screenwriterRepository.save(new Screenwriter(registeredWorker));
        }

        user = User.createUser(requestDTO, registeredWorker);
        requestDTO.setUserId(user.getId());
        userService.addUser(user);
    }
}
