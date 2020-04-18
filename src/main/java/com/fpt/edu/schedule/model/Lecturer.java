package com.fpt.edu.schedule.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fpt.edu.schedule.common.enums.StatusLecturer;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@Entity
@NoArgsConstructor
public class Lecturer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String googleId;
    private String fullName;
    private String shortName;
    private String email;
    private String phone;
    private String department;
    private int quotaClass;
    private boolean fullTime;
    private boolean login = false;
    private StatusLecturer status;
    @Type(type = "text")
    private String picture;
    @ManyToOne
    @JoinColumn(name = "role_id")
    private Role role;

    @OneToMany(mappedBy = "lecturer", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Expected> expectedList;
    @OneToMany(mappedBy = "lecturer", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Request> requestList;
    @Transient
    private boolean fillingExpected;
    @Transient
    private boolean headOfDepartment;

    public Lecturer(String email) {
        this.email = email;
    }

    @Override
    public boolean equals(Object obj) {
        Lecturer lecturer = (Lecturer) obj;
        return id == lecturer.getId();
    }
}