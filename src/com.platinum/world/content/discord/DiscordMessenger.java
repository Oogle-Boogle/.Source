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

    private static String testhook = "https://discordapp.com/api/webhooks/707218092400574545/nowe11Ec9tTy7_cgvhUaSyXMtmCJsWuh5VSlbT2BMLlldYjsNSxaFAq9vLMDLuaimGj4";
    private static String announcementhook = "https://discordapp.com/api/webhooks/707218092400574545/nowe11Ec9tTy7_cgvhUaSyXMtmCJsWuh5VSlbT2BMLlldYjsNSxaFAq9vLMDLuaimGj4";
    private static String staffhook = "https://discordapp.com/api/webhooks/728151083188486155/ugXlAm4sQJsXNCCkhX6LrCycNxUS6K4LUqGwriGOTuuBbQSRSxxHFL-ltMXxzE4cNQl9";
    private static String ingamehook = "https://discordapp.com/api/webhooks/707218092400574545/nowe11Ec9tTy7_cgvhUaSyXMtmCJsWuh5VSlbT2BMLlldYjsNSxaFAq9vLMDLuaimGj4";
    private static String debughook = "https://discordapp.com/api/webhooks/707218092400574545/nowe11Ec9tTy7_cgvhUaSyXMtmCJsWuh5VSlbT2BMLlldYjsNSxaFAq9vLMDLuaimGj4";
    private static String yellhook = "https://discordapp.com/api/webhooks/707218092400574545/nowe11Ec9tTy7_cgvhUaSyXMtmCJsWuh5VSlbT2BMLlldYjsNSxaFAq9vLMDLuaimGj4";
    private static String pmhook = "https://discordapp.com/api/webhooks/718367692658049056/5AhcwVd6JNkf7tYXDxPpOUQjXjxzDZRIgFAPGvmMEve63iTu9x4eI_V0aKqLlgW83Rdm";
    private static String chathook = "https://discordapp.com/api/webhooks/707218092400574545/nowe11Ec9tTy7_cgvhUaSyXMtmCJsWuh5VSlbT2BMLlldYjsNSxaFAq9vLMDLuaimGj4";
    private static String clanhook = "https://discordapp.com/api/webhooks/718193019299430590/ScDvUR9kvO02IAbWD-NxjY7OycS59Oj5vVGVvwWzezJEuDejKmw1CBAu4BlfhHIjpmte";
    private static String newplayer = "https://discordapp.com/api/webhooks/718223719809417286/EtaJH48I3_YX0nKh8wGBthi9jUFuSnAakcxcidffHXuh2w6YG5L1CnKaHCvGTes1_EuI";
    private static String droplog = "https://discordapp.com/api/webhooks/718213933105676310/yLfikMJCI9MRf3Jylm8JK9ywhbkVmICSWFIbTJQ-g8ZTqv6WlmwlMRKkFuZFczt5r29x";
    private static String tradelog = "https://discordapp.com/api/webhooks/718396466561024001/QlXw13PcrhkKDUu2n8LLuYXp41-Wp4-AK_XadrMcu1IMERpRP9SWeWZCltX5xh7TVMUi";
    private static String rareDrop = "https://discord.com/api/webhooks/872972884337893406/D_TjB54LzBNU8tsETEqJeFjs9-Go5pFPZJzv7Ri2GVOJWR9HgyvGOeUQRegw-Q2zwzZm";
    private static String commandLog = "https://discordapp.com/api/webhooks/718391356212379759/3yc1zrK1O4MPsGmSdndYXbCN0Z1wS91cTw0HW2ERHkZFFnjAmTTVGRIUw87bcg4pKoaY";
    private static String chatLog = "https://discordapp.com/api/webhooks/718193019299430590/ScDvUR9kvO02IAbWD-NxjY7OycS59Oj5vVGVvwWzezJEuDejKmw1CBAu4BlfhHIjpmte";
    private static String dailyLoginLog = "https://discordapp.com/api/webhooks/721862498789228694/_NOpxSUPoxNFGer_RGIxrFRb6IkxEbKgpy1xfhRz2f5EuijJZCdW70wLLxWA5rRr5K_S";
    private static String errorlog = "https://discordapp.com/api/webhooks/731625796149444619/iDv5ed6au_wsQX8BlAM48agBcNIU6VRemateHth_FVM_LAVr56KgNneNXhRXiLfmmlC3";
    private static String bugChannel = "https://discord.com/api/webhooks/859897654090924042/T6xEmh260p-LAmJv5k2avldyYWPRn5m6nBzOcfO4hNpU2PqZ1KQekoC9n29jSrEy7ksE";


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

    public static void sendTradeLog(String msg) {
        if (GameSettings.DEVELOPERSERVER) {
            return;
        }
        try {


            String webhook = tradelog;

            WebhookClient client = new WebhookClientBuilder()
                    .withURI(new URI(webhook))
                    .build(); // Create the webhook client

            @SuppressWarnings("unused")
            DiscordEmbed embed = new DiscordEmbed.Builder()
                    .withTitle("janusps") // The title of the embed element
                    .withURL("https://janusps.com/") // The URL of the embed element
                    .withColor(Color.GREEN) // The color of the embed. You can leave this at null for no color
                    .withDescription("Remember, you can mute any specific channel by clicking the bell in the top right of Discord.") // The description of the embed object
                    .build(); // Build the embed element

            DiscordMessage message = new DiscordMessage.Builder(Misc.stripIngameFormat(msg)) // The content of the message
                    //.withEmbed(embed) // Add our embed object
                    .withUsername("Announcement Bot") // Override the username of the bot
                    .build(); // Build the message

            client.sendPayload(message);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void sendDropLog(String msg) {
        if (GameSettings.DEVELOPERSERVER) {
            return;
        }
        try {


            String webhook = droplog;

            WebhookClient client = new WebhookClientBuilder()
                    .withURI(new URI(webhook))
                    .build(); // Create the webhook client

            @SuppressWarnings("unused")
            DiscordEmbed embed = new DiscordEmbed.Builder()
                    .withTitle("janusps") // The title of the embed element
                    .withURL("https://janusps.com/") // The URL of the embed element
                    .withColor(Color.GREEN) // The color of the embed. You can leave this at null for no color
                    .withDescription("Remember, you can mute any specific channel by clicking the bell in the top right of Discord.") // The description of the embed object
                    .build(); // Build the embed element

            DiscordMessage message = new DiscordMessage.Builder(Misc.stripIngameFormat(msg)) // The content of the message
                    //.withEmbed(embed) // Add our embed object
                    .withUsername("Announcement Bot") // Override the username of the bot
                    .build(); // Build the message

            client.sendPayload(message);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void sendErrorLog(String msg) {
        if (GameSettings.DEVELOPERSERVER) {
            return;
        }
        try {


            String webhook = errorlog;

            WebhookClient client = new WebhookClientBuilder()
                    .withURI(new URI(webhook))
                    .build(); // Create the webhook client

            @SuppressWarnings("unused")
            DiscordEmbed embed = new DiscordEmbed.Builder()
                    .withTitle("janusps") // The title of the embed element
                    .withURL("https://janusps.com/") // The URL of the embed element
                    .withColor(Color.GREEN) // The color of the embed. You can leave this at null for no color
                    .withDescription("Remember, you can mute any specific channel by clicking the bell in the top right of Discord.") // The description of the embed object
                    .build(); // Build the embed element

            DiscordMessage message = new DiscordMessage.Builder(Misc.stripIngameFormat(msg)) // The content of the message
                    //.withEmbed(embed) // Add our embed object
                    .withUsername("ERROR LOG") // Override the username of the bot
                    .build(); // Build the message

            client.sendPayload(message);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void sendAnnouncement(String msg) {
        if (GameSettings.DEVELOPERSERVER) {
            return;
        }
        try {


            String webhook = announcementhook;

            WebhookClient client = new WebhookClientBuilder()
                    .withURI(new URI(webhook))
                    .build(); // Create the webhook client

            @SuppressWarnings("unused")
            DiscordEmbed embed = new DiscordEmbed.Builder()
                    .withTitle("janusps") // The title of the embed element
                    .withURL("https://janusps.com/") // The URL of the embed element
                    .withColor(Color.GREEN) // The color of the embed. You can leave this at null for no color
                    .withDescription("Remember, you can mute any specific channel by clicking the bell in the top right of Discord.") // The description of the embed object
                    .build(); // Build the embed element

            DiscordMessage message = new DiscordMessage.Builder(Misc.stripIngameFormat(msg)) // The content of the message
                    //.withEmbed(embed) // Add our embed object
                    .withUsername("Announcement Bot") // Override the username of the bot
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


            String webhook = newplayer;

            WebhookClient client = new WebhookClientBuilder()
                    .withURI(new URI(webhook))
                    .build(); // Create the webhook client

            @SuppressWarnings("unused")
            DiscordEmbed embed = new DiscordEmbed.Builder()
                    .withTitle("janusps") // The title of the embed element
                    .withURL("https://janusps.com/") // The URL of the embed element
                    .withColor(Color.GREEN) // The color of the embed. You can leave this at null for no color
                    .withDescription("Remember, you can mute any specific channel by clicking the bell in the top right of Discord.") // The description of the embed object
                    .build(); // Build the embed element

            DiscordMessage message = new DiscordMessage.Builder(Misc.stripIngameFormat(msg)) // The content of the message
                    //.withEmbed(embed) // Add our embed object
                    .withUsername("New Player Bot!") // Override the username of the bot

                    .build(); // Build the message

            client.sendPayload(message);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void dropMessage(String msg) {
        if (GameSettings.DEVELOPERSERVER) {
            return;
        }
        try {

            String webhook = ingamehook;

            WebhookClient client = new WebhookClientBuilder().withURI(new URI(webhook)).build(); // Create the webhook
            // client

            DiscordEmbed embed = new DiscordEmbed.Builder().withTitle("Rare Loot") // The title of the embed
                    // element
                    .withColor(Color.YELLOW) // The color of the embed. You can leave this at null for no color
                    .withDescription("" + msg + "") // The description of the embed object

                    .build(); // Build the embed element

            DiscordMessage message = new DiscordMessage.Builder(Misc.stripIngameFormat("--")) // The content of the
                    // message

                    .withEmbed(embed) // Add our embed object
                    .withUsername("Dropped Item Tracker") // Override the username of the bot

                    .build(); // Build the message

            client.sendPayload(message);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void sendBug(String bug, Player player) {
        /*if (GameSettings.DEVELOPERSERVER) {
            return;
        }*/
        try {

            String webhook = bugChannel;

            WebhookClient client = new WebhookClientBuilder()
                    .withURI(new URI(webhook))
                    .build(); // Create the webhook client

            DiscordEmbed embed = new DiscordEmbed.Builder()
                    .withTitle("STAFF MESSAGE") // The title of the embed element
                    .withURL("https://janusps.com/") // The URL of the embed element
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

    public static void sendStaffMessage(String msg) {
        if (GameSettings.DEVELOPERSERVER) {
            return;
        }
        try {

            String webhook = staffhook;

            WebhookClient client = new WebhookClientBuilder()
                    .withURI(new URI(webhook))
                    .build(); // Create the webhook client

            DiscordEmbed embed = new DiscordEmbed.Builder()
                    .withTitle("STAFF MESSAGE") // The title of the embed element
                    .withURL("https://janusps.com/") // The URL of the embed element
                    .withColor(Color.RED) // The color of the embed. You can leave this at null for no color
                    .withDescription(Misc.stripIngameFormat(msg)) // The description of the embed object
                    .build(); // Build the embed element

            DiscordMessage message = new DiscordMessage.Builder(Misc.stripIngameFormat(msg)) // The content of the message
                    //.withEmbed(embed) // Add our embed object
                    .withUsername("Staff Bot") // Override the username of the bot

                    .build(); // Build the message

            client.sendPayload(message);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void sendChatMessage(String msg) {
        if (GameSettings.DEVELOPERSERVER) {
            return;
        }
        try {


            String webhook = chathook;

            WebhookClient client = new WebhookClientBuilder()
                    .withURI(new URI(webhook))
                    .build(); // Create the webhook client

            DiscordEmbed embed = new DiscordEmbed.Builder()
                    .withTitle("janusps") // The title of the embed element
                    .withURL("https://janusps.com/") // The URL of the embed element
                    .withColor(Color.YELLOW) // The color of the embed. You can leave this at null for no color
                    .withDescription("Remember, you can mute any specific channel by clicking the bell in the top right of Discord.") // The description of the embed object
                    .build(); // Build the embed element

            String msgToSend = Misc.stripIngameFormat(msg);

            DiscordMessage message = new DiscordMessage.Builder(msgToSend) // The content of the message
                    .withUsername("Chat Logger") // Override the username of the bot

                    .build(); // Build the message

            if (msgToSend.equalsIgnoreCase(":information_source:!")) {
                sendDebugMessage("Bad message from sendChatMessage, \n" + msgToSend);
            } else {
                client.sendPayload(message);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void sendClanMessage(String msg) {
        if (GameSettings.DEVELOPERSERVER) {
            return;
        }
        try {


            String webhook = clanhook;

            WebhookClient client = new WebhookClientBuilder()
                    .withURI(new URI(webhook))
                    .build(); // Create the webhook client

            DiscordEmbed embed = new DiscordEmbed.Builder()
                    .withTitle("janusps") // The title of the embed element
                    .withURL("https://janusps.com/") // The URL of the embed element
                    .withColor(Color.GREEN) // The color of the embed. You can leave this at null for no color
                    //.withDescription("Remember, you can mute any specific channel by clicking the bell in the top right of Discord.") // The description of the embed object
                    .build(); // Build the embed element

            String msgToSend = Misc.stripIngameFormat(msg);

            DiscordMessage message = new DiscordMessage.Builder(msgToSend) // The content of the message
                    //.withEmbed(embed) // Add our embed object
                    .withUsername("CC Logger") // Override the username of the bot

                    .build(); // Build the message

            if (msgToSend.equalsIgnoreCase(":information_source:!")) {
                sendDebugMessage("Bad message from sendClanMessage, \n" + msgToSend);
            } else {
                client.sendPayload(message);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void sendPrivateMessage(String msg) {
        if (GameSettings.DEVELOPERSERVER) {
            return;
        }
        try {


            String webhook = pmhook;

            WebhookClient client = new WebhookClientBuilder()
                    .withURI(new URI(webhook))
                    .build(); // Create the webhook client

            DiscordEmbed embed = new DiscordEmbed.Builder()
                    .withTitle("janusps") // The title of the embed element
                    .withURL("https://janusps.com/") // The URL of the embed element
                    .withColor(Color.MAGENTA) // The color of the embed. You can leave this at null for no color
                    //.withDescription("Remember, you can mute any specific channel by clicking the bell in the top right of Discord.") // The description of the embed object
                    .build(); // Build the embed element

            String msgToSend = Misc.stripIngameFormat(msg);

            DiscordMessage message = new DiscordMessage.Builder(msgToSend) // The content of the message
                    //.withEmbed(embed) // Add our embed object
                    .withUsername("PM Logger") // Override the username of the bot

                    .build(); // Build the message

            if (msgToSend.equalsIgnoreCase(":information_source:!")) {
                sendDebugMessage("Bad message from sendPrivateMessage, \n" + msgToSend);
            } else {
                client.sendPayload(message);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void sendInGameMessage(String msg) {
        if (GameSettings.DEVELOPERSERVER) {
            return;
        }
        try {


            String webhook = ingamehook;

            WebhookClient client = new WebhookClientBuilder()
                    .withURI(new URI(webhook))
                    .build(); // Create the webhook client

            DiscordEmbed embed = new DiscordEmbed.Builder()
                    .withTitle("janusps") // The title of the embed element
                    .withURL("https://janusps.com/") // The URL of the embed element
                    .withColor(Color.BLUE) // The color of the embed. You can leave this at null for no color
                    .withDescription("Remember, you can mute any specific channel by clicking the bell in the top right of Discord.") // The description of the embed object
                    .build(); // Build the embed element

            String msgToSend = Misc.stripIngameFormat(msg);

            DiscordMessage message = new DiscordMessage.Builder(msgToSend) // The content of the message
                    //.withEmbed(embed) // Add our embed object
                    .withUsername("In Game Message!") // Override the username of the bot

                    .build(); // Build the message

            if (msgToSend.equalsIgnoreCase(":support:")) {
                sendDebugMessage("Bad message from sendInGameMessage, \n" + msgToSend);
            } else {
                client.sendPayload(message);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void sendDebugMessage(String msg) {
        if (GameSettings.DEVELOPERSERVER) {
            return;
        }
        try {


            String webhook = debughook;

            WebhookClient client = new WebhookClientBuilder()
                    .withURI(new URI(webhook))
                    .build(); // Create the webhook client

            DiscordEmbed embed = new DiscordEmbed.Builder()
                    .withTitle("janusps") // The title of the embed element
                    .withURL("https://janusps.com/") // The URL of the embed element
                    .withColor(Color.MAGENTA) // The color of the embed. You can leave this at null for no color
                    .withDescription("Remember, you can mute any specific channel by clicking the bell in the top right of Discord.") // The description of the embed object
                    .build(); // Build the embed element

            //DiscordMessage message = new DiscordMessage.Builder(msg)
            DiscordMessage message = new DiscordMessage.Builder(Misc.stripIngameFormat(msg)) // The content of the message
                    //.withEmbed(embed) // Add our embed object
                    .withUsername("Debug Bot") // Override the username of the bot
                    .build(); // Build the message

            client.sendPayload(message);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void sendYellMessage(String msg) {
        if (GameSettings.DEVELOPERSERVER) {
            return;
        }
        try {


            String webhook = yellhook;

            WebhookClient client = new WebhookClientBuilder()
                    .withURI(new URI(webhook))
                    .build(); // Create the webhook client

            DiscordEmbed embed = new DiscordEmbed.Builder()
                    .withTitle("janusps") // The title of the embed element
                    .withURL("https://janusps.com/") // The URL of the embed element
                    .withColor(Color.WHITE) // The color of the embed. You can leave this at null for no color
                    .withDescription("Remember, you can mute any specific channel by clicking the bell in the top right of Discord.") // The description of the embed object
                    .build(); // Build the embed element

            String msgToSend = Misc.stripIngameFormat(msg);

            DiscordMessage message = new DiscordMessage.Builder(msgToSend) // The content of the message
                    //.withEmbed(embed) // Add our embed object
                    .withUsername("Yell Bot") // Override the username of the bot
                    .build(); // Build the message

            if (msgToSend.equalsIgnoreCase(":information_source:!")) {
                sendDebugMessage("Bad message from sendYellMessage, \n" + msgToSend);
            } else {
                client.sendPayload(message);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
