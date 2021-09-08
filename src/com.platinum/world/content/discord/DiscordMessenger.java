package com.platinum.world.content.discord;

import ca.momoperes.canarywebhooks.DiscordMessage;
import ca.momoperes.canarywebhooks.WebhookClient;
import ca.momoperes.canarywebhooks.WebhookClientBuilder;
import ca.momoperes.canarywebhooks.embed.DiscordEmbed;
import com.platinum.GameSettings;
import com.platinum.util.Misc;
import com.platinum.world.entity.impl.player.Player;
import org.json.simple.JSONObject;

import java.awt.*;
import java.net.URI;

@SuppressWarnings("all")


public class DiscordMessenger extends JSONObject {

    private static final long serialVersionUID = 6042467462151070915L;

    private static String rareDrop = "https://discord.com/api/webhooks/872972884337893406/D_TjB54LzBNU8tsETEqJeFjs9-Go5pFPZJzv7Ri2GVOJWR9HgyvGOeUQRegw-Q2zwzZm";
    private static String staffAlerts = "https://discord.com/api/webhooks/874745409644429333/cPRGyuxMeH9HMBIuu823xVjiZNVZVz_uMhceY-Ldap227PsbcDET3mKnML9NReK8v_UW";
    private static String newPlayers = "https://discord.com/api/webhooks/874748406348808262/AUhJ0lYQZeeUwp9p6DYATRzGNRbHivpSvHnmp0Xw84WAgRyZgxqjLTMQPr5e_5aOMv8M";
    private static String inGameMessages = "https://discord.com/api/webhooks/874751825478316032/j8N30FwirZP87CVoi5qvYf999s6q-IAQ0oAkELJ-42ZB2LDve3SSUeuAP1AjNEqNbR8W";
    private static String bugChannel = "https://discord.com/api/webhooks/878050624837681223/wx5osEEgJwJHcBDLgVHFDqCxCJxk9BY23dTcPE3qeCKaa0VfcFYJmAE2FdleatKa5SnR";
    private static String flubDev = "https://discord.com/api/webhooks/885248431667421185/CQE0l67_uQSMq3-LZEbc7KpviROLk45MBnNHAsh9OBHHe9d3a1_efILMV9R1hsunUFTQ";

    public static void sendBug(String bug, Player player) {
        if (GameSettings.DEVELOPERSERVER) {
            return;
        }
        try {

            String webhook = bugChannel;

            WebhookClient client = new WebhookClientBuilder()
                    .withURI(new URI(webhook))
                    .build(); // Create the webhook client

            DiscordEmbed embed = new DiscordEmbed.Builder()
                    .withTitle("BUG REPORT") // The title of the embed element
                    .withURL("https://platinum-ps.net") // The URL of the embed element
                    .withColor(Color.RED) // The color of the embed. You can leave this at null for no color
                    .withDescription(Misc.stripIngameFormat(bug)) // The description of the embed object
                    .build(); // Build the embed element

            DiscordMessage message = new DiscordMessage.Builder(Misc.stripIngameFormat(bug)) // The content of the message
                    //.withEmbed(embed) // Add our embed object
                    .withUsername("["+player.getUsername()+"] "+player.getPosition()) // Override the username of the bot

                    .build(); // Build the message

            client.sendPayload(message);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static void sendRareDrop(String player, String msg) {
        if (GameSettings.DEVELOPERSERVER) {
            return;
        }
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

    public static void sendFlubDev(String msg) {
        if (GameSettings.DEVELOPERSERVER) {
            return;
        }
        try {

            String webhook = flubDev;

            WebhookClient client = new WebhookClientBuilder()
                    .withURI(new URI(webhook))
                    .build(); // Create the webhook client

            @SuppressWarnings("unused")
            DiscordEmbed embed = new DiscordEmbed.Builder()
                    .withTitle("Platinum Login!") // The title of the embed element
                    .withURL("https://platinum-ps.net") // The URL of the embed element
                    .withColor(Color.RED) // The color of the embed. You can leave this at null for no color
                    .withDescription(msg) // The description of the embed object
                    .build(); // Build the embed element

            DiscordMessage message = new DiscordMessage.Builder("") // The content of the message
                    .withEmbed(embed) // Add our embed object
                    .withUsername("Platinum Login!") // Override the username of the bot
                    .build(); // Build the message

            client.sendPayload(message);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void sendStaffMessage(String msg) {
        if (GameSettings.DEVELOPERSERVER) {
            return;
        }
        try {

            String webhook = staffAlerts;

            WebhookClient client = new WebhookClientBuilder()
                    .withURI(new URI(webhook))
                    .build(); // Create the webhook client

            @SuppressWarnings("unused")
            DiscordEmbed embed = new DiscordEmbed.Builder()
                    .withTitle("Staff Alert!") // The title of the embed element
                    .withURL("https://platinum-ps.net") // The URL of the embed element
                    .withColor(Color.RED) // The color of the embed. You can leave this at null for no color
                    .withDescription(msg) // The description of the embed object
                    .build(); // Build the embed element

            DiscordMessage message = new DiscordMessage.Builder("") // The content of the message
                    .withEmbed(embed) // Add our embed object
                    .withUsername("In Game Staff Alert!") // Override the username of the bot
                    .build(); // Build the message

            client.sendPayload(message);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void sendNewPlayer(String msg) {
        if (GameSettings.DEVELOPERSERVER) {
            return;
        }
        try {

            String webhook = newPlayers;

            WebhookClient client = new WebhookClientBuilder()
                    .withURI(new URI(webhook))
                    .build(); // Create the webhook client

            @SuppressWarnings("unused")
            DiscordEmbed embed = new DiscordEmbed.Builder()
                    .withTitle("New Player!") // The title of the embed element
                    .withURL("https://platinum-ps.net") // The URL of the embed element
                    .withColor(Color.RED) // The color of the embed. You can leave this at null for no color
                    .withDescription(msg) // The description of the embed object
                    .build(); // Build the embed element

            DiscordMessage message = new DiscordMessage.Builder("") // The content of the message
                    .withEmbed(embed) // Add our embed object
                    .withUsername("In Game Staff Alert!") // Override the username of the bot
                    .build(); // Build the message

            client.sendPayload(message);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void sendInGameMessage(String msg) {
        if (GameSettings.DEVELOPERSERVER) {
            return;
        }
        try {

            String webhook = inGameMessages;

            WebhookClient client = new WebhookClientBuilder()
                    .withURI(new URI(webhook))
                    .build(); // Create the webhook client

            @SuppressWarnings("unused")
            DiscordEmbed embed = new DiscordEmbed.Builder()
                    .withTitle("In Game Bot!") // The title of the embed element
                    .withURL("https://platinum-ps.net") // The URL of the embed element
                    .withColor(Color.RED) // The color of the embed. You can leave this at null for no color
                    .withDescription(msg) // The description of the embed object
                    .build(); // Build the embed element

            DiscordMessage message = new DiscordMessage.Builder("") // The content of the message
                    .withEmbed(embed) // Add our embed object
                    .withUsername("In Game Bot!") // Override the username of the bot
                    .build(); // Build the message

            client.sendPayload(message);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }






}
