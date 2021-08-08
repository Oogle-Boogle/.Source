package com.platinum.world.content.discord;

import ca.momoperes.canarywebhooks.DiscordMessage;
import ca.momoperes.canarywebhooks.WebhookClient;
import ca.momoperes.canarywebhooks.WebhookClientBuilder;
import ca.momoperes.canarywebhooks.embed.DiscordEmbed;
import org.json.simple.JSONObject;

import java.awt.*;
import java.net.URI;

@SuppressWarnings("all")


public class DiscordMessenger extends JSONObject {

    private static final long serialVersionUID = 6042467462151070915L;

    private static String rareDrop = "https://discord.com/api/webhooks/872972884337893406/D_TjB54LzBNU8tsETEqJeFjs9-Go5pFPZJzv7Ri2GVOJWR9HgyvGOeUQRegw-Q2zwzZm";


    public static void sendRareDrop(String player, String msg) {
       /* if (GameSettings.DEVELOPERSERVER) {
            return;
        }*/
        try {

            String webhook = rareDrop;

            WebhookClient client = new WebhookClientBuilder()
                    .withURI(new URI(webhook))
                    .build(); // Create the webhook client

            @SuppressWarnings("unused")
            DiscordEmbed embed = new DiscordEmbed.Builder()
                    .withTitle(player + " has just received..") // The title of the embed element
                    .withURL("https://platinum-ps.net") // The URL of the embed element
                    .withColor(Color.RED) // The color of the embed. You can leave this at null for no color
                    .withDescription(msg) // The description of the embed object
                    .build(); // Build the embed element

            DiscordMessage message = new DiscordMessage.Builder("") // The content of the message
                    .withEmbed(embed) // Add our embed object
                    .withUsername("Rare Drop Alert!") // Override the username of the bot
                    .build(); // Build the message

            client.sendPayload(message);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }






}
