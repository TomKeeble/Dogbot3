package com.tomkeeble.dogbot3;

import com.beust.jcommander.IStringConverter;
import com.beust.jcommander.IStringConverterFactory;
import com.tomkeeble.dogbot3.messages.Message;

public class MessageConverterFactory implements IStringConverterFactory {
    @Override
    public Class<? extends IStringConverter<?>> getConverter(Class forType) {
//        if (forType.equals(Message.class)){
            return MessageConverter.class;
//        } else {
//            return null;
//        }
    }
}
