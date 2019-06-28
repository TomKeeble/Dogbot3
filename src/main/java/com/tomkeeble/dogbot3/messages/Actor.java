package com.tomkeeble.dogbot3.messages;


import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * A person interacting in a group chat
 */
@Entity
@Table(name="actor")
public class Actor {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private long id;

    @ManyToOne
    private Person person;

    private String nickname;

    @OneToMany(
            mappedBy = "actor",
            cascade = CascadeType.ALL,
            orphanRemoval = false
    )
    private List<Message> messages = new ArrayList<>();


    @ManyToOne
    private Group group;

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

}
