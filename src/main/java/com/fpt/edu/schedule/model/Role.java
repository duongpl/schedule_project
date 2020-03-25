package com.fpt.edu.schedule.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@Entity
@NoArgsConstructor

public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String roleName;
    @JsonIgnore
    @ManyToMany(mappedBy = "roleList")
    private List<UserName> userList;

    public Role(String roleName) {
        this.roleName = roleName;
    }
}
