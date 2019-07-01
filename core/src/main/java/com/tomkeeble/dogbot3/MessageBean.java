package com.tomkeeble.dogbot3;

import com.tomkeeble.dogbot3.messages.Actor;
import com.tomkeeble.dogbot3.messages.Message;
import com.tomkeeble.dogbot3.messages.Person;
import com.tomkeeble.dogbot3.messages.Thread;
import org.jboss.logging.Logger;
import org.json.JSONObject;

import javax.ejb.Stateful;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import javax.persistence.Query;
import java.util.List;

@Stateful
public class MessageBean {

    private static Logger logger = Logger.getLogger(MessageBean.class);

    @PersistenceContext(unitName = "persistence", type = PersistenceContextType.EXTENDED)
    private EntityManager entityManager;

    public Message processMessage(String msgbody){
        JSONObject obj = new JSONObject(msgbody);

        com.tomkeeble.dogbot3.messages.Message msg = new com.tomkeeble.dogbot3.messages.Message(obj.getString("message"));

        Thread group = getGroupByFid(obj.getString("thread_id"));
        logger.info(group.getName());
        msg.setThread(group);
        msg.setActor(getActorInGroup(obj.getString("user_id"), group));
        entityManager.persist(msg);
        entityManager.flush();

        return msg;
    }

    public Thread getGroupByFid(String fid) {
        Query q = entityManager.createQuery("FROM Thread G WHERE G.groupId = " + fid);
        List results = q.setMaxResults(1).getResultList();

//        q.setParameter("name", fid);
        if (results.size() == 1) {
            return (Thread) results.get(0);
        }

        logger.info("Unable to find group");
        return entityManager.find(Thread.class, Long.valueOf(1));

    }

    public Person getPersonById(String user_id) {
        Query q = entityManager.createQuery("FROM Person P WHERE P.userID = " + user_id);
        List results = q.setMaxResults(1).getResultList();

        Person p;

//        q.setParameter("name", fid);
        if (results.size() == 1) {
            p = (Person) results.get(0);
        } else {
            p = new Person();
            p.setName("A Name");
            p.setUserID(user_id);
            entityManager.persist(p);
            logger.info("New person created");
        }
        return p;
    }

    public Actor getActorInGroup(String user_id, Thread thread) {
        Person p = getPersonById(user_id);

        Query q = entityManager.createQuery("FROM Actor A WHERE A.person = " + p.getId());
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
            entityManager.persist(a);
            logger.info("New actor created");
        }

       return a;

    }

}
