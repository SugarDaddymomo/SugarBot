package com.sugardaddy.sugarbot.commands;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ServerInfo extends Command {

    Logger logger = LoggerFactory.getLogger(ServerInfo.class);

    public ServerInfo() {
        this.name = "serverInfo";
        this.aliases = new String[]{"server"};
        this.help = "Gives information about the Server.";
    }


    @Override
    protected void execute(CommandEvent commandEvent) {
        logger.info("yaha tk aaya");
        commandEvent.reply("This is a test message");
    }
}