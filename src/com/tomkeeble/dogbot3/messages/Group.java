package com.tomkeeble.dogbot3.messages;

import javax.persistence.*;

@Entity
public class Group {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private long id;

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

    /**
     * The name of the group (according to fb)
     */
    private String name;

    /**
     * The group's ID (according to fb)
     */
    private String groupId;
}
