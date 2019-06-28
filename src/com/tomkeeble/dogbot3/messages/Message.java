package com.tomkeeble.dogbot3.messages;

import javax.persistence.*;

@Entity
public class Message {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private long id;

    @OneToMany
    private Group group;

    @OneToMany
    private Actor actor;

    private String message;

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    public Actor getActor() {
        return actor;
    }

    public void setActor(Actor actor) {
        this.actor = actor;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
