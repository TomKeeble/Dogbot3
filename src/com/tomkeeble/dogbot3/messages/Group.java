package com.tomkeeble.dogbot3.messages;

import com.tomkeeble.dogbot3.Dogbot3;
import com.tomkeeble.dogbot3.MessageProvider;

import javax.persistence.*;

@Entity
public class Group {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private long id;


    /**
     * The name of the group (according to fb)
     */
    private String name;

    /**
     * The group's ID (according to fb)
     */
    private String groupId;

    private MessageProvider messageProvider=Dogbot3.getMessageProvider();


    public void sendMessage(Message message) {

        this.messageProvider.sendMessage(this, message);
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

    public MessageProvider getMessageProvider() {
        return messageProvider;
    }

    public void setMessageProvider(MessageProvider messageProvider) {
        this.messageProvider = messageProvider;
    }



}
