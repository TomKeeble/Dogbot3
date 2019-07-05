package com.tomkeeble.dogbot3;

import com.tomkeeble.dogbot3.messages.Message;

public interface Module {

    public String getClassName();

    public void processMessage(Message message);

    public void processHistoricMessage(Message message);
}
