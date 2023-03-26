package com.sugardaddy.sugarbot.commands;

import com.sugardaddy.sugarbot.music.PlayerManager;
import net.dv8tion.jda.api.entities.GuildVoiceState;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.entities.channel.concrete.VoiceChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.managers.AudioManager;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.net.URI;
import java.net.URISyntaxException;

public class PlayAllCommand extends ListenerAdapter {

    Logger logger = LoggerFactory.getLogger(PlayAllCommand.class);

    @Override
    public void onMessageReceived(@NotNull MessageReceivedEvent event) {
        final TextChannel textChannel = event.getChannel().asTextChannel();
        final Member self = event.getGuild().getSelfMember();
        final GuildVoiceState selfVoiceState = self.getVoiceState();
        final Member member = event.getMember();
        final GuildVoiceState memberVoiceState = member.getVoiceState();
        final AudioManager audioManager = event.getGuild().getAudioManager();
        final VoiceChannel voiceChannel = (VoiceChannel) event.getMember().getVoiceState().getChannel();
        String restCommand = "";
        try {
            if (event.getMessage().getContentRaw().contains("$playall") && !event.getMessage().getAuthor().isBot()) {
                if (!selfVoiceState.inAudioChannel() && !event.getMessage().getAuthor().isBot()) {
                    textChannel.sendMessage("I need to be in voice channel for this command to work. Use `$join`").queue();
                    return;
                }
                if (!memberVoiceState.inAudioChannel() && !event.getMessage().getAuthor().isBot()) {
                    textChannel.sendMessage("You need to be in voice channel for this command to work!").queue();
                    return;
                }
                // TO BE FIXED LATER
//                if (!memberVoiceState.getChannel().equals(Objects.nonNull(selfVoiceState.getChannel())) && !event.getMessage().getAuthor().isBot()) {
//                    event.getChannel().sendMessage("You need to be in the same voice channel as me for this to work!").queue();
//                    return;
//                }
                restCommand = event.getMessage().getContentRaw().substring(9);
                logger.info("play krein?");
                try {
                    audioManager.openAudioConnection(voiceChannel);
                } catch (Exception e) {
                    logger.error("error bhau");
                }
                if (!isURL(restCommand)) {
                    String link = "ytsearch:" + restCommand;
                    logger.info("yt link: {}", link);
                    PlayerManager.getINSTANCE().loadAndPlay(event.getChannel().asTextChannel(), link);
                } else {
                    logger.info(restCommand);
                    PlayerManager.getINSTANCE().loadAndPlay(event.getChannel().asTextChannel(), restCommand);
                }
            }
        } catch (Exception e) {
            logger.error("error occured");
            if (!event.getMessage().getAuthor().isBot()) {
                textChannel.sendMessage("You need to be in the same voice channel as me for this to work!").queue();
            }
        }
    }

    public boolean isURL(String url) {
        try {
            new URI(url);
            return true;
        } catch (URISyntaxException e) {
            return false;
        }
    }
}
