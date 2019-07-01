package com.tomkeeble.dogbot3;

import com.tomkeeble.dogbot3.messages.Message;

import javax.ejb.Startup;
import javax.inject.Inject;

@Startup
public interface Module {

    public void processMessage(Message message);

    public void processHistoricMessage(Message message);
}
