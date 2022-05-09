package com.codegym.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;


@Entity
@Data
@Table(name = "user", uniqueConstraints = {
        @UniqueConstraint(columnNames = {
                "username"
        }),
        @UniqueConstraint(columnNames = {
                "email"
        })
})
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(min = 3, max = 50)
    private String userName;

    @JsonIgnore
    @NotBlank
    @Size(min = 6, max = 100)
    private String password;

    @NotBlank
    @Size(max = 50)
    @Email
    private String email;

    @NotBlank
    @Size(min = 3, max = 50)
    private String fullName;
    private String address;
    private String phone;
    private String avatar;
    private String status;


    @JsonIgnore
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles = new HashSet<>();

    public User(@NotBlank @Size(min = 3, max = 50) String fullName, @NotBlank @Size(min = 3, max = 50) String username, @NotBlank @Size(max = 50) @Email String email, @NotBlank @Size(min = 6, max = 100) String password) {
        this.fullName = fullName;
        this.userName = username;
        this.email = email;
        this.password = password;
    }

    public User() {

    }
}
