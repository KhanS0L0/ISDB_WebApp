package com.example.entity.users;

import com.example.dto.AuthRegDTO.RegRequestDTO;
import com.example.entity.enums.Status;
import com.example.entity.pivots.worker.Worker;
import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity(name = "USERS")
@Data
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "USERNAME")
    private String username;

    @Column(name = "EMAIL")
    private String email;

    @Column(name = "PASSWORD")
    private String password;

    @Column(name = "STATUS")
    @Enumerated(EnumType.STRING)
    private Status status;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "USERS_ROLES",
            joinColumns = @JoinColumn(name = "USER_ID", referencedColumnName = "ID"),
            inverseJoinColumns = @JoinColumn(name = "ROLE_ID", referencedColumnName = "ID")
    )
    private List<Role> roles;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "WORKER_ID", referencedColumnName = "ID")
    private Worker worker;

    public User(){}

    public User(String username, String email, String password, Worker worker) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.worker = worker;
    }

    public static User createUser(RegRequestDTO requestDTO, Worker worker){
        return new User(
                requestDTO.getUsername(),
                requestDTO.getEmail(),
                requestDTO.getPassword(),
                worker
        );
    }

}
