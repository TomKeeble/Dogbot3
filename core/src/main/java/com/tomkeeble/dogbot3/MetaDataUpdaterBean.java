package com.tomkeeble.dogbot3;

import com.tomkeeble.dogbot3.DTO.GroupDTO;
import com.tomkeeble.dogbot3.DTO.NicknameDTO;
import com.tomkeeble.dogbot3.DTO.UpdateDTO;
import com.tomkeeble.dogbot3.DTO.UserDTO;
import com.tomkeeble.dogbot3.messages.Actor;
import com.tomkeeble.dogbot3.messages.Person;
import com.tomkeeble.dogbot3.messages.Thread;
import org.jboss.logging.Logger;

import javax.ejb.Stateful;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;

@Stateful
public class MetaDataUpdaterBean {

    private static Logger logger = Logger.getLogger(MetaDataUpdaterBean.class);

    @PersistenceContext(unitName = "persistence", type = PersistenceContextType.EXTENDED)
    private EntityManager entityManager;

    public void doUpdate(UpdateDTO updateDTO){
        for (UserDTO user : updateDTO.getUsers()) {
            Person p = Person.getPersonById(entityManager,user.getId());
            p.setName(user.getName());
            entityManager.merge(p);
            entityManager.flush();
        }
        for (GroupDTO group : updateDTO.getGroups()) {
            Thread t = Thread.getGroupByFid(entityManager,group.getId());
            t.setName(group.getName());
            entityManager.merge(t);
            entityManager.flush();
        }
        for (NicknameDTO nick : updateDTO.getNicknames()) {
            Actor a = Thread.getActorInGroup(entityManager, nick.getUser_id(), Thread.getGroupByFid(entityManager, nick.getGroup_id()));
            a.setNickname(nick.getNickname());
            entityManager.merge(a);
            entityManager.flush();
        }
    }

}
