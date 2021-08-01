package com.platinum.world.entity.impl.player;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.logging.Level;

import com.platinum.GameServer;
import com.platinum.model.Item;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class MiniMeData {

	private Path filePath;
	private File file;

	private Player player;
	private Player botOwner;
	private Item[] items;
	private Item[] resources;

	private int MAX_ITEMS = 200;
	private int MAX_RESOURCE_SLOTS = 200;

	public MiniMeData(Player player) {
		this.player = player;
		this.botOwner = player.getMinimeOwner();
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
			object.add("items", builder.toJsonTree(items));
			object.add("resources", builder.toJsonTree(resources));

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
			JsonParser fileParser = new JsonParser();
			Gson builder = new GsonBuilder().create();
			JsonObject reader = (JsonObject) fileParser.parse(fileReader);

			if (reader.has("items")) {
				items = builder.fromJson(reader.get("items").getAsJsonArray(), Item[].class);
			}

			if (reader.has("resources")) {
				items = builder.fromJson(reader.get("resources").getAsJsonArray(), Item[].class);
			}


		} catch (Exception e) {
			e.printStackTrace();
			GameServer.getLogger().log(Level.WARNING, "An error has occured while loading mini me data file!", e);
		}
	}


	public void addItem(int itemID, int amount) {
		System.out.println("Attempting to add "+amount + " x " + itemID);
		for (int i = 0; i < MAX_ITEMS; i++) {
			System.out.println("Into slot "+ i);
			if (items[i] == null) {
				items[i] = new Item(itemID, amount);
			}
		}
	}

	public void addResources(int itemID, int amount) {
		for (int i = 0; i < MAX_RESOURCE_SLOTS; i++) {
			if (resources[i] == null) {
				resources[i] = new Item(itemID, amount);
			}
		}
	}

	public void save() {
		try (FileWriter writer = new FileWriter(file)) {
			Gson builder = new GsonBuilder().setPrettyPrinting().create();
			JsonObject object = new JsonObject();

			object.add("items", builder.toJsonTree(items));
			object.add("resources", builder.toJsonTree(resources));


			writer.write(builder.toJson(object));
		} catch (Exception e) {
			// An error happened while saving.
			GameServer.getLogger().log(Level.WARNING, "An error has occured while saving a minime file!", e);
		}

	}

}
