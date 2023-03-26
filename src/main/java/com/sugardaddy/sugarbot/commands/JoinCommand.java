package com.sugardaddy.sugarbot.commands;

import com.sugardaddy.sugarbot.music.GuildMusicManager;
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

public class JoinCommand extends ListenerAdapter {

    Logger logger = LoggerFactory.getLogger(JoinCommand.class);

    @Override
    public void onMessageReceived(@NotNull MessageReceivedEvent event) {
        final TextChannel textChannel = event.getChannel().asTextChannel();
        final Member self = event.getGuild().getSelfMember();
        final Member member = event.getMember();
        final GuildVoiceState memberVoiceState = member.getVoiceState();
        final AudioManager audioManager = event.getGuild().getAudioManager();
        final VoiceChannel voiceChannel = (VoiceChannel) event.getMember().getVoiceState().getChannel();

        try {

            if (event.getMessage().getContentRaw().equalsIgnoreCase("$join") && !event.getMessage().getAuthor().isBot()) {
                if (!memberVoiceState.inAudioChannel() && !event.getMessage().getAuthor().isBot()) {
                    textChannel.sendMessage("You need to be in voice channel for this command to work!").queue();
                    return;
                }
                logger.info("join krein?");
                try {
                    audioManager.openAudioConnection(voiceChannel);
                } catch (Exception e) {
                    logger.error("error bhau");
                }
            }
        } catch (Exception e) {
            logger.error("error occured");
            if (!event.getMessage().getAuthor().isBot()) {
                textChannel.sendMessage("You need to be in the same voice channel as me for this to work!").queue();
            }
        }
    }
}
