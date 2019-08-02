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

//    @Id
//    @GeneratedValue(strategy= GenerationType.AUTO)
//    private Long id;


  /**
     * The group's ID (according to fb)
     */
    @Id
    private String id;

    /**
     * The name of the group (according to fb)
     */
    private String name;



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

    public List<Message> getAllMessages(EntityManager em){
        Query q = em.createQuery("FROM Message M WHERE M.thread = " + this.getId());
//        Query q = em.createQuery("FROM Message M");
        List results = q.setMaxResults(10000).getResultList();

        return results;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

//    public String getGroupId() {
//        return groupId;
//    }
//
//    public void setGroupId(String groupId) {
//        this.groupId = groupId;
//    }

    public Message getNMessagesBack(EntityManager em, int n) throws MessageSelectionOutOfRangeException {

        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery cq = cb.createQuery();
        Root<Message> messageRoot = cq.from(Message.class);
        cq.select(messageRoot)
                .where(
                        cb.equal(messageRoot.get("thread"), this)
                ).orderBy(cb.desc(messageRoot.get("timestamp")));
        Query q = em.createQuery(cq);

        q.setMaxResults(n+1);
        List rl = q.getResultList();
        if (rl.size()>n) {
            return (Message) q.getResultList().get(n);
        } else {
            throw new MessageSelectionOutOfRangeException("Cannot select " + n + " messages back.");
        }


    }

    public static Actor getActorInGroup(EntityManager em, String user_id, Thread thread) {
        Person p = Person.getPersonById(em, user_id);

        Query q = em.createQuery("FROM Actor A WHERE A.person = " + p.getId() + " AND A.thread = " + thread.getId());
        List results = q.setMaxResults(1).getResultList();

        Actor a;
//        q.setParameter("name", fid);
        if (results.size() == 1) {
            a = (Actor) results.get(0);
        } else {
            a = new Actor();
            a.setNickname("nickname");
            a.setPerson(p);
            a.setThread(thread);
            em.persist(a);
        }

        return a;

    }

    public static Thread getGroupByFid(EntityManager em, String fid) {
        Query q = em.createQuery("FROM Thread G WHERE G.id = " + fid);
        List results = q.setMaxResults(1).getResultList();
        Thread g;

//        q.setParameter("name", fid);
        if (results.size() == 1) {
            return (Thread) results.get(0);
        } else {
        g = new Thread();
        g.setName("A Name");
        g.setId(fid);
        em.persist(g);
    }

        return g;

    }

    public Actor searchActorInThread(EntityManager em, String search) {
        Query q =em.createQuery("FROM Actor A WHERE A.person.name LIKE :search");
        q.setParameter("search", search);
        List results = q.setMaxResults(1).getResultList();

        if (results.size() == 1) {
            return (Actor) results.get(0);
        } else {
            return null;
        }
    }



}
