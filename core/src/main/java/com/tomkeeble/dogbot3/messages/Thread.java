package com.tomkeeble.dogbot3.messages;

import com.tomkeeble.dogbot3.MessageProvider;
import com.tomkeeble.dogbot3.exceptions.MessageSelectionOutOfRangeException;

import javax.persistence.*;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="thread")
public class Thread {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;


    /**
     * The name of the group (according to fb)
     */
    private String name;

    /**
     * The group's ID (according to fb)
     */
    private String groupId;

    public List<Actor> getActors() {
        return actors;
    }

    @OneToMany(
            mappedBy = "thread",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<Actor> actors = new ArrayList<>();

    @OneToMany(
            mappedBy = "thread",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<Message> messages = new ArrayList<>();

    public List<Message> getMessages(){
        return messages;
    }



    public void sendMessage(MessageProvider mp, Message message) {

        mp.sendMessage(this, message);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public Message getNMessagesBack(EntityManager em, int n) throws MessageSelectionOutOfRangeException {

        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery cq = cb.createQuery();
        Root<Message> messageRoot = cq.from(Message.class);
        cq.select(messageRoot)
                .where(
                        cb.equal(messageRoot.get("thread"), this.id)
                ).orderBy(cb.desc(messageRoot.get("id")));
        Query q = em.createQuery(cq);

        q.setMaxResults(n+1);
        List rl = q.getResultList();
        if (rl.size()>=n) {
            return (Message) q.getResultList().get(n);
        } else {
            throw new MessageSelectionOutOfRangeException("Cannot select " + n + " messages back.");
        }


    }



}
