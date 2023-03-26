package com.sugardaddy.sugarbot.events;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.events.guild.member.GuildMemberJoinEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class MemberJoinEvent extends ListenerAdapter {

    static final List<String> WELCOME = Arrays.asList("स्वागत है आपका", "नमस्ते", "नमस्कार",
            "रोम रोम जी", "और बढ़े दिन बाद", "एक और चुतिया", "और भूतनी के",
            "रोम रोम भाई", "और भाई", "कुछ दिन तो गुजारो सर्वर में",
            "दारू लाया?", "शराबी", "हरामी", "ब्रुह",
            "सर जी आप?", "आईये ना", "तू फिर आ गया",
            "अबे यार");

    @Override
    public void onGuildMemberJoin(@NotNull GuildMemberJoinEvent event) {
        String user = event.getMember().getAsMention();
        JDA jda = event.getJDA();
        User user1 = event.getUser();
        Random random = new Random();
        List<TextChannel> channels = jda.getTextChannelsByName("welcome-members", true);
        for (TextChannel textChannel : channels) {
            textChannel.sendMessage(WELCOME.get(random.nextInt((WELCOME.size())))+" "+user).queue();
        }
//        user1.openPrivateChannel().queue(
//                privateChannel -> privateChannel.sendMessageFormat("Welcome to **%s** Server.\nHope you enjoy your stay.", event.getGuild().getName()).queue()
//        );
    }
}
