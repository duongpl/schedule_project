package com.fpt.edu.schedule.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@Entity
@NoArgsConstructor
public class Expected implements Cloneable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private Date createdDate;
    private Date updatedDate;

    @ManyToOne
    @JoinColumn(name = "semester_id")
    private Semester semester;

    @ManyToOne
    @JoinColumn(name = "lecturer_id")
    private Lecturer lecturer;

    @OneToMany(mappedBy = "expected", cascade = CascadeType.ALL)
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<ExpectedSlot> expectedSlots;

    @LazyCollection(LazyCollectionOption.FALSE)
    @OneToMany(mappedBy = "expected", cascade = CascadeType.ALL)
    private List<ExpectedSubject> expectedSubjects;

    @OneToOne(mappedBy = "expected", cascade = CascadeType.ALL)
    private ExpectedNote expectedNote;
    @Transient
    private boolean canReuse;

    public Object clone() throws
            CloneNotSupportedException {
        return super.clone();
    }

}

