package com.tomkeeble.dogbot3.messages;


import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * A person interacting in a thread chat
 */
@Entity
@Table(name="actor")
public class Actor {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;

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
    private Thread thread;

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public String getNickname() {
        if (nickname.equals("NO-NICK")){
            return this.getPerson().getName();
        } else {
            return nickname;
        }
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public Thread getThread() {
        return thread;
    }

    public void setThread(Thread thread) {
        this.thread = thread;
    }

}
