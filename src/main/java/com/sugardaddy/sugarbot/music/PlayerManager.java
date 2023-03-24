package com.sugardaddy.sugarbot.music;

import com.sedmelluq.discord.lavaplayer.player.AudioLoadResultHandler;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.player.DefaultAudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.source.AudioSourceManagers;
import com.sedmelluq.discord.lavaplayer.tools.FriendlyException;
import com.sedmelluq.discord.lavaplayer.track.AudioPlaylist;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PlayerManager {

    Logger logger = LoggerFactory.getLogger(PlayerManager.class);

    private static PlayerManager INSTANCE;
    private final Map<Long, GuildMusicManager> musicManagerMap;
    private final AudioPlayerManager audioPlayerManager;

    public PlayerManager() {
        this.musicManagerMap = new HashMap<>();
        this.audioPlayerManager = new DefaultAudioPlayerManager();

        AudioSourceManagers.registerRemoteSources(this.audioPlayerManager);
        AudioSourceManagers.registerLocalSource(this.audioPlayerManager);
    }

    public GuildMusicManager getGuildMusicManager(Guild guild) {
        return this.musicManagerMap.computeIfAbsent(guild.getIdLong(), aLong -> {
            final GuildMusicManager guildMusicManager = new GuildMusicManager(this.audioPlayerManager);
            guild.getAudioManager().setSendingHandler(guildMusicManager.getAudioPlayerSendHandler());
            return guildMusicManager;
        });
    }

    public void loadAndPlay(TextChannel textChannel, String trackURL) {
        final GuildMusicManager guildMusicManager = this.getGuildMusicManager(textChannel.getGuild());

        logger.info("ghus gya player m");

        this.audioPlayerManager.loadItemOrdered(guildMusicManager, trackURL, new AudioLoadResultHandler() {
            @Override
            public void trackLoaded(AudioTrack audioTrack) {
                logger.info("gaana shuru?");
                guildMusicManager.trackScheduler.queue(audioTrack);

                textChannel.sendMessage("Adding to queue "+audioTrack.getInfo().title).queue();

            }

            @Override
            public void playlistLoaded(AudioPlaylist audioPlaylist) {
                final List<AudioTrack> tracks = audioPlaylist.getTracks();
                if (!tracks.isEmpty()) {
                    guildMusicManager.trackScheduler.queue(tracks.get(0));
                    textChannel.sendMessage("Adding to queue "+tracks.get(0).getInfo().title).queue();
                }
            }

            @Override
            public void noMatches() {

            }

            @Override
            public void loadFailed(FriendlyException e) {

            }
        });
    }

    public static PlayerManager getINSTANCE() {
        if (INSTANCE == null) {
            INSTANCE = new PlayerManager();
        }
        return INSTANCE;
    }
}
