package com.platinum.world.content.fuser;

import com.platinum.model.Item;
import com.platinum.model.definitions.ItemDefinition;
import com.platinum.world.entity.impl.player.Player;

public enum CombineEnum {

    OP_INVESTOR(new Item[] {new Item(10835,10000),new Item(12845,15),new Item(12846,15),
    		new Item(12847,15) },6482,100),
    
    AURA(new Item[] {new Item(10835,7500),new Item(12845,12),
    		new Item(12846,12),new Item(12847,12) },19156,100),
    
    DROPRATE_AURA(new Item[] {new Item(10835,8000),new Item(12845,10),
    		new Item(12846,10),new Item(12847,10) },15454,100),
    
    DROPRATE_BOOTS(new Item[] {new Item(10835,6000),new Item(12845,10),
    		new Item(12846,10),new Item(12847,10) },11694,100),
    
    PERMENANT_DR(new Item[] {new Item(10835,10000),new Item(12845,10),
    		new Item(12846,10),new Item(12847,10) },5197,100),
    
    COLLECTOR(new Item[] {new Item(10835,5000),new Item(12845,5),
    		new Item(12846,5), new Item(12847,5) },19886,100),
    
    PERFECT_NECKLACE(new Item[] {new Item(10835,50000),new Item(12845,25),
    		new Item(12846,25),new Item(12847,50) },774,100);

    private CombineEnum( Item[] requirements, int endItem,int chance) {
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

    public static boolean checkRequirements(CombineEnum combine, Player player) {
        Item[] reqs = combine.getRequirements();
        for (Item req : reqs) {
            if (player.getInventory().contains(req.getId()) && player.getInventory().getAmount(req.getId()) >= req.getAmount()) {
            } else {
                return false;

            }
        }
        return true;

    }
    public static void removeRequirements(CombineEnum combine, Player player){
      Item[] reqs = combine.getRequirements();
      for(Item req : reqs) {
          if(player.getInventory().contains(new Item[] {req})) {
              player.getInventory().delete(req.getId(),req.getAmount());
              player.sendMessage("@bla@ Removed "+req.getAmount()+"x "+ ItemDefinition.forId(req.getId()).getName() + " From your inventory!");
          }
      }
    }
}
