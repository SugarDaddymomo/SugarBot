package com.sugardaddy.sugarbot.events;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HelloEvent extends ListenerAdapter {

    Logger logger = LoggerFactory.getLogger(HelloEvent.class);

    @Override
    public void onMessageReceived(MessageReceivedEvent messageReceivedEvent) {
        String eventMessage = messageReceivedEvent.getMessage().getContentRaw();
        if (eventMessage.equalsIgnoreCase("$hello") && !messageReceivedEvent.getAuthor().isBot()) {
            logger.info("trying to send the hello in text channel");
            messageReceivedEvent.getChannel().sendMessage("Hello "+messageReceivedEvent.getAuthor().getName()).queue();
        }
    }
}