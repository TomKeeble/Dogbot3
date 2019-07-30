package com.tomkeeble.dogbot3.services;

import com.tomkeeble.dogbot3.messageproviders.facebook.FacebookMessageProvider;
import com.tomkeeble.dogbot3.messages.Message;
import com.tomkeeble.dogbot3.messages.Thread;

import javax.ejb.Stateless;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.*;
import javax.ws.rs.core.Application;

@Path("/rest")
@Stateless
public class SendMessageService {

    @PersistenceContext(unitName = "persistence")
    EntityManager entityManager;

    @Inject
    FacebookMessageProvider msg_provider;


    @GET
    @Path("/{g_id}")
    @Produces({ "application/json" })
    public String getHelloWorldJSON(@PathParam("g_id") String g_id, @QueryParam("msg") String msg) {
        Thread.getGroupByFid(entityManager, g_id).sendMessage(msg_provider, new Message(msg));
        return "{\"result\":\"Okay\"}";
    }
}
