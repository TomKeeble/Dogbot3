package com.tomkeeble.dogbot3.messages;

import com.tomkeeble.dogbot3.exceptions.MessageDoesNotExistException;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name="message")
public class Message {

//    @Id
//    @GeneratedValue(strategy= GenerationType.AUTO)
//    private Long id;

    @Id
    private String id;

    @ManyToOne
    private Thread thread;

    @ManyToOne
    private Actor actor;

    @Lob
    @Column(columnDefinition = "TEXT")
    private String message;

    @ManyToOne
    private Message replied_to;



    private Date timestamp;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

//    public String getFb_id() {
//        return fb_id;
//    }
//
//    public void setFb_id(String fb_id) {
//        this.fb_id = fb_id;
//    }

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

    public Message getReplied_to() {
        return replied_to;
    }

    public void setReplied_to(Message replied_to) {
        this.replied_to = replied_to;
    }

    public static Message getMessageByFid(EntityManager em, String fid) throws MessageDoesNotExistException {
        Query q = em.createQuery("FROM Message M WHERE M.id = ?1").setParameter(1, fid);
        List results = q.setMaxResults(1).getResultList();

        if (results.size() == 1) {
            return (Message) results.get(0);
        } else {
            throw new MessageDoesNotExistException("Message: " + fid + " does not exist");
        }

    }

}
