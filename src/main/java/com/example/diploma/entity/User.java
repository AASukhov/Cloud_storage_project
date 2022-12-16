package com.example.diploma.entity;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Builder
@Table(name = "Users")
@AllArgsConstructor
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String login;

    private String password;

//    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
//    @JoinTable (name = "user_roles", joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
//        inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"))
//    private List<Role> roles = new ArrayList<>();

    //private String role;

    @OneToMany (cascade = CascadeType.ALL)
    private List<File> files;
}
