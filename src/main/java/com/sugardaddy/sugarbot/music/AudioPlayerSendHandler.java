package com.sugardaddy.sugarbot.music;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.track.playback.MutableAudioFrame;
import net.dv8tion.jda.api.audio.AudioSendHandler;
import org.jetbrains.annotations.Nullable;
import java.nio.Buffer;
import java.nio.ByteBuffer;

public class AudioPlayerSendHandler implements AudioSendHandler {

    private final AudioPlayer audioPlayer;
    private final ByteBuffer byteBuffer;
    private final MutableAudioFrame audioFrame;

    public AudioPlayerSendHandler(AudioPlayer audioPlayer) {
        this.audioPlayer = audioPlayer;
        this.byteBuffer = ByteBuffer.allocate(1024);
        this.audioFrame = new MutableAudioFrame();
        this.audioFrame.setBuffer(byteBuffer);
    }

    @Override
    public boolean isOpus() {
        return true;
    }

    @Override
    public boolean canProvide() {
        return this.audioPlayer.provide(this.audioFrame);
    }

    @Nullable
    @Override
    public ByteBuffer provide20MsAudio() {
        final Buffer buffer = ((Buffer) this.byteBuffer).flip();
        return (ByteBuffer) buffer;
    }
}
