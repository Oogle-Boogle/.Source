package com.platinum.world.content.serverperks;


import com.platinum.util.QuickUtils;
import com.platinum.util.StringUtils;
import com.platinum.world.World;
import com.platinum.world.entity.impl.player.Player;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Stream;

public class GlobalPerks {

    public enum Perk {
    	SLAYER_POINTS(0, 3500, 1225),
        DAMAGE(1, 5000, 1226),
        NPC_KILLS(2, 7000, 1227),
        BOSS_POINTS(3, 7000, 1228),
        XP(4, 5000, 1229),
        DOUBLE_DROPS(5, 10000, 1229);

        private final int index;
        private final int amount;
        private final int spriteId;

        Perk(int index, int amount, int spriteId) {
            this.index = index;
            this.amount = amount;
            this.spriteId = spriteId;
        }

        public int getIndex() {
            return index;
        }

        public int getAmount() {
            return amount;
        }

        public int getSpriteId() {
            return spriteId;
        }
    }

    private final Map<Perk, Integer> contributions = new HashMap<>();
    private final int TIME = 6000; // 1 hour // 30 mins
    private final int INTERFACE_ID = 42050;
    private final int OVERLAY_ID = 42112;
    private final Perk[] PERKS = Perk.values();
    private Perk activePerk;
    private int currentTime = 0;
    private boolean active = false;

    public Perk getActivePerk() {
        return activePerk;
    }

    public void open(Player player) {
        player.getPacketSender().sendInterface(INTERFACE_ID);
        player.setPerkIndex(0);
        updateInterface(player);
    }

    public void contribute(Player player, int amount) {
        if (active) {
            player.sendMessage("@red@A perk is already active");
            return;
        }
        if(!player.getInventory().contains(10835, amount)) {
            return;
        }
        int index = player.getPerkIndex();
        Perk perk = PERKS[index];
        int current = contributions.getOrDefault(perk, 0);
        int necessary = perk.getAmount();
        amount = Math.min(amount, necessary - current);
        player.getInventory().delete(10835, amount);
        int total = contributions.merge(perk, amount, Integer::sum);
        updateInterface(player);

        if (total >= necessary) {
            start(perk);
        }
    }


    public void tick() {
        if (!active) {
            return;
        }

        currentTime--;

        if (currentTime == 0) {
            end();
        }

        if (currentTime % 100 == 0) {
            updateOverlay();
        }
    }

    private void start(Perk perk) {
        currentTime = TIME;
        active = true;
        activePerk = perk;
        updateOverlay();
    }

    private void end() {
        active = false;
        contributions.put(activePerk, 0);
        World.sendFilteredMessage("<img=10>[WORLD]<img=10> @red@Perk " + StringUtils.usToSpace(activePerk.toString()) + " has ended");
        activePerk = null;
        resetInterface();
    }

    private void updateOverlay() {
        //System.out.println("Starting with " + activePerk);
        if (activePerk == null) {
            return;
        }
        World.getPlayers().forEach(player -> {
            int minutes = (int) QuickUtils.tickToMin(currentTime);
            player.getPacketSender().sendSpriteChange(OVERLAY_ID + 1, activePerk.getSpriteId());
            player.getPacketSender().sendWalkableInterface(OVERLAY_ID, true);
            player.getPacketSender()
                    .sendString(OVERLAY_ID + 3, StringUtils.usToSpace(activePerk.toString()));
            player.getPacketSender().sendString(OVERLAY_ID + 2, minutes + " min");
        });
    }

    private void resetInterface() {
        World.getPlayers().forEach(player -> {
            player.getPacketSender().sendWalkableInterface(OVERLAY_ID, false);
            player.getPacketSender().updateProgressBar(INTERFACE_ID + 10, 0);
        });
    }

    private void updateInterface(Player player) {
        int index = player.getPerkIndex();
        Perk perk = PERKS[index];
        int current = contributions.getOrDefault(perk, 0);
        int required = perk.getAmount();
        int percentage = getPercentage(current, required);
        player.getPacketSender().updateProgressBar(INTERFACE_ID + 10, percentage);
        player.getPacketSender().sendString(INTERFACE_ID + 11, current + "Q / " + required + "Q");
    }

    private int getPercentage(int n, int total) {
        float proportion = ((float) n) / ((float) total);
        return (int) (proportion * 100f);
    }

    public boolean handleButton(Player player, int id) {
        //System.out.println((id > -23465) + " | " + (id < -23470));
        if (id > -23465 || id < -23470) {
            return false;
        }

        int index = 23470 + id;
        player.setPerkIndex(index);
        updateInterface(player);
        player.sendMessage("Clicked index = " + index);
        return true;
    }

    private final Path FILE_PATH = Paths.get("./data/serverperks.txt");

    public void save() {
        List<String> data = new ArrayList<>();
        contributions.forEach((k, v) -> {
            data.add(k.toString() + ", " + v);
        });

        try {
            Files.write(FILE_PATH, data);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void load() {
        try (Stream<String> lines = Files.lines(FILE_PATH)) {
            lines.forEach(line -> {
                String[] split = line.split(", ");
                contributions.put(Perk.valueOf(split[0]), Integer.parseInt(split[1]));
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
        ////System.out.println("Loaded: " + contributions);
    }

    public void reset() {
        contributions.clear();
        World.getPlayers().forEach(this::updateInterface);
    }

    private static GlobalPerks instance = null;

    public static GlobalPerks getInstance() {
        if (instance == null) {
            instance = new GlobalPerks();
        }
        return instance;
    }

    private GlobalPerks() {

    }


}
