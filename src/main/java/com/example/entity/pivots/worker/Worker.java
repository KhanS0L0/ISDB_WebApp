package com.example.entity.pivots.worker;

import com.example.dto.AuthRegDTO.RegRequestDTO;
import com.example.entity.users.User;
import lombok.Data;

import javax.persistence.*;

@Entity(name = "WORKERS")
@Data
public class Worker {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "FIRST_NAME")
    private String firstName;

    @Column(name = "LAST_NAME")
    private String lastName;

    @Column(name = "DESCRIPTION")
    private String description;

    @OneToOne(mappedBy = "worker")
    private User user;

    @OneToOne(mappedBy = "worker")
    private Artist artist;

    @OneToOne(mappedBy = "worker")
    private Screenwriter screenwriter;

    public Worker(){}

    public Worker(String firstName, String lastName, String description) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.description = description;
    }

    public static Worker createWorker(RegRequestDTO requestDTO){
        return new Worker(
                requestDTO.getFirstName(),
                requestDTO.getLastName(),
                requestDTO.getDescription()
        );
    }
}
