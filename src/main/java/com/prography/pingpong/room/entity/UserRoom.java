package com.prography.pingpong.room.entity;

import com.prography.pingpong.team.entity.Team;
import com.prography.pingpong.user.entity.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserRoom {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    @JoinColumn(name="roomId")
    private Room room;

    @ManyToOne
    @JoinColumn(name="userRoom")
    private User user;

    @Column
    @Enumerated(EnumType.STRING)
    private Team team;

    @Builder
    public UserRoom(Room room, User user){
        this.room = room;
        this.user = user;
        this.team = Team.RED;
    }

    public static UserRoom from(Room room, User user, Team team){
        return new UserRoom(room, user, team);
    }

    private  UserRoom(Room room, User user, Team team){
        this.room = room;
        this.user = user;
        this.team = team;
    }

    public void setTeam(Team team){
        this.team = team;
    }

    public void toggleTeam(Team team) {
        if (team == Team.BLUE) {
            this.team = Team.RED;
        } else {
            this.team = Team.BLUE;
        }
    }
}

