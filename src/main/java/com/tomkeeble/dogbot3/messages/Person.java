package com.tomkeeble.dogbot3.messages;

import javax.persistence.*;

@Entity
public class Person {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private long id;

    /**
     * The person's real name
     */
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
