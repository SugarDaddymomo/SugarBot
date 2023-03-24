package com.sugardaddy.sugarbot.commands;

import com.sugardaddy.sugarbot.events.HelloEvent;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.*;

public class Menu extends ListenerAdapter {

    Logger logger = LoggerFactory.getLogger(HelloEvent.class);

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        EmbedBuilder embedBuilder = new EmbedBuilder();
        String eventMessage = event.getMessage().getContentRaw();
        if (eventMessage.equalsIgnoreCase("$help") && !event.getAuthor().isBot()) {
            embedBuilder.setTitle("Help Menu");
            embedBuilder.setColor(Color.MAGENTA);
            embedBuilder.setThumbnail("https://thumbs2.imgbox.com/12/65/mGFRCKki_t.png");
            embedBuilder.setAuthor("Built By SugarDaddy");
            embedBuilder.addField("For commands", "$help", true);
            embedBuilder.addField("For music commands", "$musicHelp", true);
            embedBuilder.setFooter("For any queries and feedbacks reach out to me on Instagram: @sugardaddy_senpai");
            logger.info("trying to send the help menu in text channel");
            event.getChannel().sendMessageEmbeds(embedBuilder.build()).queue();
        } else if (eventMessage.equalsIgnoreCase("$musichelp") && !event.getAuthor().isBot()) {
            embedBuilder.setTitle("Music Commands");
            embedBuilder.setColor(Color.MAGENTA);
            embedBuilder.setThumbnail("https://thumbs2.imgbox.com/12/65/mGFRCKki_t.png");
            embedBuilder.setAuthor("Built By SugarDaddy");
            embedBuilder.addField("For playing any song via YT lookup", "$play song name", false);
            embedBuilder.addField("For commands", "$help", true);
            embedBuilder.addField("For music commands", "$musicHelp", true);
            embedBuilder.addField("For playing using YT link", "$play link", false);
            embedBuilder.addField("To disconnect bot", "$leave", true);
            embedBuilder.setFooter("For any queries and feedbacks reach out to me on Instagram: @sugardaddy_senpai");
            logger.info("trying to send the music menu in text channel");
            event.getChannel().sendMessageEmbeds(embedBuilder.build()).queue();
        }
    }
}
