package com.tomkeeble.dogbot3.modules;

import com.tomkeeble.dogbot3.Dogbot3;
import com.tomkeeble.dogbot3.Module;
import com.tomkeeble.dogbot3.messages.Message;

import javax.ejb.Startup;
import javax.inject.Inject;

public class ExampleModule implements Module {


    @Override
    public void processMessage(Message message) {
        System.out.println("Message!");
        message.getThread().sendMessage(new Message("Hello World"));
    }

    @Override
    public void processHistoricMessage(Message message) {
        //Do nothing
    }
}
