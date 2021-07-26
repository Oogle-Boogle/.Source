package com.platinum.world.entity.impl.player;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.logging.Level;

import com.platinum.GameServer;
import com.platinum.model.Item;
import com.platinum.model.definitions.ItemDefinition;
import com.platinum.world.entity.impl.player.Player;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class MiniMeData {

	private Path filePath;
	private File file;

	private Player player;
	private Player botOwner;
	private int loadedIceSlot = 0, loadedSmokeSlot = 0, loadedBloodSlot = 0, loadedShadowSlot = 0;

	public MiniMeData(Player player) {
		this.player = player;
		this.botOwner = player.getBotOwner();
		filePath = Paths.get("./data/minimes/" + botOwner.getUsername() + ".json");
		file = filePath.toFile();

		// Attempt to make the player save directory if it doesn't
		// exist.
		if (!file.getAbsoluteFile().exists()) {
			try {
				try {
					file.getParentFile().createNewFile();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				initialize();
			} catch (SecurityException e) {
				System.out.println("Unable to create directory for player data!");
			}
		} else {
			load();
		}
	}

	private void initialize() {
		file.getParentFile().setWritable(true);

		try (FileWriter writer = new FileWriter(file)) {
			Gson builder = new GsonBuilder().setPrettyPrinting().create();
			JsonObject object = new JsonObject();
			object.addProperty("ice-diamond", 0);
			object.addProperty("blood-diamond", 0);
			object.addProperty("smoke-diamond", 0);
			object.addProperty("shadow-diamond", 0);
			writer.write(builder.toJson(object));
			writer.close();
			System.out.println("Created minime data file: " + file.getPath());
		} catch (Exception e) {
			// An error happened while saving.
			GameServer.getLogger().log(Level.WARNING, "An error has occured while creating mini me data file!", e);
		}
	}

	public void load() {
		try (FileReader fileReader = new FileReader(file)) {
			System.out.println("Now attempting to read from mini me data file");
			System.out.println(file.getAbsolutePath());
			JsonParser fileParser = new JsonParser();
			Gson builder = new GsonBuilder().create();
			JsonObject reader = (JsonObject) fileParser.parse(fileReader);

			if (reader.has("blood-diamond")) {
				loadedBloodSlot = reader.get("blood-diamond").getAsInt();
				if (loadedBloodSlot > 50) {
					botOwner.sendMessage(
							"@blu@ Mini-Me Extra Damage: 50.0% | Bonus Drop Rate: " + loadedBloodSlot + ".0%");
				} else {
					botOwner.sendMessage("@blu@ Mini-Me Extra Damage: " + loadedBloodSlot + ".0% | Bonus Drop Rate: "
							+ loadedBloodSlot + "%");
				}

			}

		/*	if (reader.has("blood-diamond")) {
				loadedBloodSlot = builder.fromJson(reader.get("blood-diamond").getAsJsonArray(), Integer.class);
			}

			if (reader.has("smoke-diamond")) {
				loadedSmokeSlot = builder.fromJson(reader.get("smoke-diamond").getAsJsonArray(), Integer.class);
			}

			if (reader.has("shadow-diamond")) {
				loadedShadowSlot = builder.fromJson(reader.get("shadow-diamond").getAsJsonArray(), Integer.class);
			}*/

			fileReader.close();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	public int checkDiamonds(int diamondId) {
		switch (diamondId) {
		case 4670: // blood diamond
			return loadedBloodSlot;
		case 4671: // ice diamond
			return loadedIceSlot;
		case 4672: // smoke diamond
			return loadedSmokeSlot;
		case 4673: // shadow diamond
			return loadedShadowSlot;
		}

		return -1;
	}

	public void addEnchantment(int diamondId) {
		switch (diamondId) {
		case 4670: // blood
			if (checkDiamonds(diamondId) >= 100) {
				botOwner.sendMessage("@red@You reached the maximum cap of blood enchantments for your mini-me!");
				return;
			}
			int oldEnchantments = checkDiamonds(diamondId);
			int amountOfDiamonds = botOwner.getInventory().getAmount(diamondId);

			if (amountOfDiamonds > 100)
				amountOfDiamonds = 100;

			if (amountOfDiamonds + oldEnchantments > 100) {
				amountOfDiamonds = 100 - oldEnchantments;
			}

			botOwner.getInventory().delete(diamondId, amountOfDiamonds);
			loadedBloodSlot = amountOfDiamonds + oldEnchantments;

			double extraDamage = amountOfDiamonds > 50 ? 50 : amountOfDiamonds;
			double extraDropRate = amountOfDiamonds;

			if (oldEnchantments < 50) {
				botOwner.sendMessage("@blu@You gained " + extraDamage + "% bonus damage for your mini-me!");
			} else {
				botOwner.sendMessage("@red@You have capped out at 50.0% bonus damage for your mini-me!");
			}
			botOwner.sendMessage("@blu@You gained " + extraDropRate + "% more drop rate from your mini-me!");
			player.setMiniMeData(this);
			save();
			break;
		case 4671:
		case 4672:
		case 4673:
			player.sendMessage("@red@This enchantment has not been unlocked!");
			break;
		}
	}

	public void save() {
		file.getParentFile().setWritable(true);

		// Attempt to make the player save directory if it doesn't
		// exist.
		if (!file.getAbsoluteFile().exists()) {
			try {
				try {
					file.getParentFile().createNewFile();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} catch (SecurityException e) {
				System.out.println("Unable to create directory for player data!");
			}
		}

		JsonObject reader = null;

		try (FileReader fileReader = new FileReader(file)) {

			System.out.println("Now attempting to read file from " + file.toPath());
			JsonElement fileParser = new Gson().fromJson(fileReader, JsonElement.class);
			Gson builder = new GsonBuilder().create();
			reader = fileParser.getAsJsonObject();

			if (reader.has("blood-diamond")) {
				reader.remove("blood-diamond");
			}
			reader.addProperty("blood-diamond", loadedBloodSlot);

			if (reader.has("ice-diamond")) {
				reader.remove("ice-diamond");
			}
			reader.addProperty("ice-diamond", loadedIceSlot);

			if (reader.has("smoke-diamond")) {
				reader.remove("smoke-diamond");
			}
			reader.addProperty("smoke-diamond", loadedSmokeSlot);

			if (reader.has("shadow-diamond")) {
				reader.remove("shadow-diamond");
			}
			reader.addProperty("shadow-diamond", loadedShadowSlot);

			fileReader.close();
		} catch (Exception e) {
			System.out.print(e.getMessage());
		}

		try (FileWriter writer = new FileWriter(file)) {
			Gson builder = new GsonBuilder().setPrettyPrinting().create();
			writer.write(builder.toJson(reader));
			writer.close();
			System.out.println("Updated file: " + file.getPath());
		} catch (Exception e) {
			// An error happened while saving.
			GameServer.getLogger().log(Level.WARNING, "An error has occured while saving a file file!", e);
		}
	}
}
