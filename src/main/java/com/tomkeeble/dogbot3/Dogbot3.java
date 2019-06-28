package com.tomkeeble.dogbot3;

import com.rabbitmq.client.ConnectionFactory;

public class Dogbot3 {

    public static MessageProvider getMessageProvider() {
        return messageProvider;
    }

    private static MessageProvider messageProvider;

    private void connectMQ(){
        ConnectionFactory factory = new ConnectionFactory();
    }
}
