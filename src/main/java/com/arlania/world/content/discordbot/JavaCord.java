package com.arlania.world.content.discordbot;

import com.arlania.world.World;
import org.javacord.api.DiscordApi;
import org.javacord.api.DiscordApiBuilder;
import org.javacord.api.entity.channel.TextChannel;
import org.javacord.api.entity.message.MessageBuilder;
import org.javacord.api.util.logging.ExceptionLogger;


/**
 * @author Patrity || https://www.rune-server.ee/members/patrity/
 */

public class JavaCord {

	private static final String token = "ODAzMDM3NTQ1ODc2ODE1ODkz.YA38_g.uuZC7jwvUJJjQeIIBnU3LBBCyUo";

	private static final String serverName = "Platinum";
	
	private static DiscordApi api = null;
	public static void init() {
		new DiscordApiBuilder().setToken(token).login().thenAccept(api -> {
			JavaCord.api = api;
			api.addMessageCreateListener(event -> {
				if (event.getMessageContent().equalsIgnoreCase("::players")) {
					event.getChannel().sendMessage(World.getPlayers().size()+ " online on Platinum");
				}
							
				
				
				
			});
		})
				// Log any exceptions that happened
				.exceptionally(ExceptionLogger.get());
	}

	public static void sendMessage(String channel, String msg) {
		try {
			new MessageBuilder()
			.append(msg)
			.send((TextChannel) api.getTextChannelsByNameIgnoreCase(channel).toArray()[0]);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}