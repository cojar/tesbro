package com.team.tesbro.lesson_res;

import com.team.tesbro.Academy.Academy;
import com.team.tesbro.Lesson.Lesson;
import com.team.tesbro.User.SiteUser;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Setter
@Getter
public class Lesson_Res {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private LocalDateTime bookDate;
    @ManyToOne
    private Lesson lesson;
    @ManyToMany
    @JoinTable(
            name = "lesson_res_user_info",
            joinColumns = @JoinColumn(name = "lesson_res_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private List<SiteUser> users = new ArrayList<>();
}