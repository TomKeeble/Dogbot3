package com.tomkeeble.dogbot3;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.MissingCommandException;
import com.tomkeeble.dogbot3.messages.Message;
import org.jboss.logging.Logger;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Stateful;
import javax.ejb.Stateless;
import javax.inject.Inject;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Stateless
@Singleton
public class CommandManagerBean {

    private static Logger logger = Logger.getLogger(CommandManagerBean.class);

    @Inject
    private Dogbot3 dogbot3;

    List<CommandModel> commands;


    public void processCommand(Message message){
        String cmdLine = message.getMessage();

        Optional<CommandModel> cm_opt = commands.stream().filter(x -> cmdLine.startsWith(x.command)).findAny();

        if (cm_opt.isPresent()) {
            CommandModel cm = cm_opt.get();
//            cm.m.invoke(cm.moduleInstance);
        } //else no command

    }

    @PostConstruct
    public void start(){

        for (Class<? extends Module> command: dogbot3.getCommands()) {

            //find all methods annotated as commands
            Method m = (Method) Arrays.stream(command.getMethods())
                    .filter(x -> x.isAnnotationPresent(com.tomkeeble.dogbot3.Commands.Command.class))
                    .toArray()[0];

            //find all annotations of all parameters of method
            Annotation[][] parameterAnnotations = m.getParameterAnnotations();


        }
    }

    static class CommandModel {

        Module moduleInstance;
        Method m;
        String command;

    }

}
