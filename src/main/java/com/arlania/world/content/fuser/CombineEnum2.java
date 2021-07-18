package com.arlania.world.content.fuser;

import com.arlania.model.Item;
import com.arlania.model.definitions.ItemDefinition;
import com.arlania.world.entity.impl.player.Player;

public enum CombineEnum2 {

    TEST(new Item[] {new Item(50911,20000),new Item(52402,20000),new Item(51249,20000)},6640,25),
    test2(new Item[] {new Item(50911,20000),new Item(52402,20000),new Item(51249,20000)},6642,25),
    te23st2(new Item[] {new Item(50911,20000),new Item(52402,20000),new Item(51249,20000)},6644,25),
    tes23t2(new Item[] {new Item(50911,20000),new Item(52402,20000),new Item(51249,20000)},16554,25),
    te3234st2(new Item[] {new Item(50911,20000),new Item(52402,20000),new Item(51249,20000)},16555,25),
    te54st2(new Item[] {new Item(50911,20000),new Item(52402,20000),new Item(51249,20000)},16556,25);


    private CombineEnum2(Item[] requirements, int endItem, int chance) {
        this.requirements = requirements;
        this.endItem = endItem;
        this.chance = chance;
    }

    Item[] requirements;
    int endItem;
    int chance;

    public int getChance() {
        return chance;
    }

    public void setChance(int chance) {
        this.chance = chance;
    }

    public Item[] getRequirements() {
        return requirements;
    }

    public int getEndItem() {
        return endItem;
    }

    public static boolean checkRequirements(CombineEnum2 combine, Player player) {
        Item[] reqs = combine.getRequirements();
        for (Item req : reqs) {
            if (player.getInventory().contains(req.getId()) && player.getInventory().getAmount(req.getId()) >= req.getAmount()) {
            } else {
                return false;

            }
        }
        return true;

    }
    public static void removeRequirements(CombineEnum2 combine, Player player){
      Item[] reqs = combine.getRequirements();
      for(Item req : reqs) {
          if(player.getInventory().contains(new Item[] {req})) {
              player.getInventory().delete(req.getId(),req.getAmount());
              player.sendMessage("@bla@ Removed "+req.getAmount()+"x "+ ItemDefinition.forId(req.getId()).getName() + " From your inventory!");
          }
      }
    }
}
