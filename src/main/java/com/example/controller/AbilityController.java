package com.example.controller;

import com.example.dto.PivotDTO.AbilityDTO;
import com.example.dto.ProcessDTO.ProcessDTO;
import com.example.exceptions.AbilityAlreadyExistException;
import com.example.service.interfaces.AbilityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/ability")
public class AbilityController {

    private final AbilityService abilityService;

    @Autowired
    public AbilityController(AbilityService abilityService) {
        this.abilityService = abilityService;
    }

    @GetMapping(produces = "application/json")
    public ResponseEntity getAllAbilities(){
        List<AbilityDTO> abilities = abilityService.getAll();
        if(abilities.size() == 0){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No abilities were found");
        }
        return ResponseEntity.ok(abilities);
    }

    @PostMapping(produces = "application/json")
    public ResponseEntity createAbility(@RequestBody AbilityDTO abilityDTO){
        try {
            abilityDTO = abilityService.addAbility(abilityDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(abilityDTO);
        } catch (AbilityAlreadyExistException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping(path = "/test", produces = "application/json")
    public void test(@RequestAttribute("username") String username,
                     @RequestAttribute("userId") Long userId,
                     @RequestAttribute("workerId") Long workerId,
                     @RequestBody ProcessDTO processDTO
//                   @RequestAttribute("abilityDTO") AbilityDTO abilityDTO
    ){
        System.out.println("USERNAME: " + username);
        System.out.println("USER_ID: " + userId);
        System.out.println("WORKER_ID: " + workerId);
        System.out.println("PROCESS_DTO: " + processDTO.toString());
//        System.out.println("ABILITY_DTO" + abilityDTO.toString());
    }

//    @PostMapping(path = "/test", produces = "application/json")
//    public void test(@RequestBody String json) throws JsonParseException, JsonMappingException, IOException{
//
//        Map<String, Object> jsonToMap = new ObjectMapper().readValue(json, Map.class);
//        ProcessDTO temp1 = (ProcessDTO) jsonToMap.get("processDTO");
//        AbilityDTO temp2 = (AbilityDTO) jsonToMap.get("abilityDTO");
//        System.out.println("PROCESS_DTO" + temp1.toString());
//        System.out.println("ABILITY_DTO" + temp2.toString());
//    }

}
