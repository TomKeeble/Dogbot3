package com.tomkeeble.dogbot3.messages;

import javax.persistence.*;

@Entity
@Table(name="message")
public class Message {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;

    @ManyToOne
    private Thread thread;

    @ManyToOne
    private Actor actor;

    @Lob
    @Column(columnDefinition = "TEXT")
    private String message;

    public Message(String message) {
        this.message = message;
    }

    public Message() {
    }

    public Thread getThread() {
        return thread;
    }

    public void setThread(Thread thread) {
        this.thread = thread;
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
