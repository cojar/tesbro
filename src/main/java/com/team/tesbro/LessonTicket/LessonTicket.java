package com.team.tesbro.LessonTicket;


import com.team.tesbro.User.SiteUser;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
public class LessonTicket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column
    private String ticketName;
    @Column
    private Integer ticketPrice;

    @OneToMany
    private List<SiteUser> ticketUsers;
}