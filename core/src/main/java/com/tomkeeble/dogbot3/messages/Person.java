package com.tomkeeble.dogbot3.messages;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name="person")
public class Person {

//    @Id
//    @GeneratedValue(strategy= GenerationType.AUTO)
//    private Long id;

    @Id
    private String id;

    /**
     * The person's real name
     */
    private String name;

    public String getId() {
        return id;
    }

//    public String getUserID() {
//        return id;
//    }

    public void setUserID(String userID) {
        this.id = userID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public static Person getPersonById(EntityManager em, String user_id) {
        Query q = em.createQuery("FROM Person P WHERE P.id = " + user_id);
        List results = q.setMaxResults(1).getResultList();

        Person p;

//        q.setParameter("name", fid);
        if (results.size() == 1) {
            p = (Person) results.get(0);
        } else {
            p = new Person();
            p.setName("A Name");
            p.setUserID(user_id);
            em.persist(p);
        }
        return p;
    }
    public boolean equals(Person person){
        return this.id.equals(person.id);
    }
}
