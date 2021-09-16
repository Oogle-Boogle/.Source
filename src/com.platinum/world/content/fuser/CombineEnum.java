package com.platinum.world.content.fuser;

import com.platinum.model.Item;
import com.platinum.model.definitions.ItemDefinition;
import com.platinum.world.World;
import com.platinum.world.entity.impl.player.Player;
import lombok.Getter;
import lombok.Setter;

public enum CombineEnum {

    
    AURA(new Item[] {new Item(10835,7500),new Item(12845,12),
    		new Item(12846,12),new Item(12847,12) },19156,100, 3600000),
    
    DROPRATE_AURA(new Item[] {new Item(10835,8000),new Item(12845,10),
    		new Item(12846,10),new Item(12847,10) },15454,100, 3600000),
    
    DROPRATE_BOOTS(new Item[] {new Item(10835,6000),new Item(12845,10),
    		new Item(12846,10),new Item(12847,10) },11694,100, 3600000),
    
    PERMENANT_DR(new Item[] {new Item(10835,10000),new Item(12845,10),
    		new Item(12846,10),new Item(12847,10) },5197,100, 3600000),

    SULPHUR_HELMET(new Item[] {new Item(10835,50000),new Item(12845,20),
            new Item(12846,20),new Item(12847,20) },3322,100, 3600000),

    SULPHUR_PLATEBODY(new Item[] {new Item(10835,50000),new Item(12845,20),
            new Item(12846,20),new Item(12847,20) },3313,100, 3600000),

    SULPHUR_PLATELEGS(new Item[] {new Item(10835,50000),new Item(12845,20),
            new Item(12846,20),new Item(12847,20) },3314,100, 3600000),

    SULPHUR_GLOVES(new Item[] {new Item(10835,50000),new Item(12845,20),
            new Item(12846,20),new Item(12847,20) },3318,100, 3600000),

    SULPHUR_BOOTS(new Item[] {new Item(10835,50000),new Item(12845,20),
            new Item(12846,20),new Item(12847,20) },3315,100, 3600000),

    SULPHUR_SCYTHE(new Item[] {new Item(10835,55000),new Item(12845,25),
            new Item(12846,25),new Item(12847,25) },1413,100, 3600000),
    
    COLLECTOR(new Item[] {new Item(10835,5000),new Item(12845,5),
    		new Item(12846,5), new Item(12847,5) },19886,100, 3600000);






    CombineEnum(Item[] requirements, int endItem, int chance, long timer) {
        this.requirements = requirements;
        this.endItem = endItem;
        this.chance = chance;
        this.timer = timer;
    }

    Item[] requirements;
    int endItem;
    int chance;
    @Getter
    @Setter
    long timer; //TIME IS IN MILLISECONDS.. 1h = 3600000

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
        if (!(System.currentTimeMillis() >= player.getFuseCombinationTimer())) {
            player.sendMessage("You have @red@" + CombineHandler.timeLeft(player) + "@bla@ until you can claim this item.");
            player.getPA().closeAllWindows();
            return false;
        }



        Item[] reqs = combine.getRequirements();
        if (player.getInventory().contains(reqs)){
            System.out.println("True! Had all items");
        } else {
            System.out.println("FALSE! Had all items");

        }

        return player.getInventory().contains(reqs); //TODO Check this because it can let things slip through.. prob set to false by default
    }

    public static void handlerFuser(Player player, CombineEnum chosenItem) {

        if (player.isFuseInProgress() && player.getFuseCombinationTimer() > 0) {
            player.getPacketSender()
                    .sendMessage("@red@You have not finished fusing your @blu@"
                            + ItemDefinition.forId(player.getFuseItemSelected()).getName()
                            + "@red@ yet!");
            return;
        }

        if (!player.isClaimedFuseItem() && player.getFuseItemSelected() > 0) {
            player.getPacketSender()
                    .sendMessage("@red@You haven't claimed your @blu@"
                    + ItemDefinition.forId(player.getFuseItemSelected()).getName()
                    + "@red@ yet!");
            return;
        }

        if (checkRequirements(chosenItem, player)) {
            removeRequirements(chosenItem, player);
            player.setFuseCombinationTimer(System.currentTimeMillis() + (chosenItem.getTimer()));
            player.setClaimedFuseItem(false);
            player.setFuseInProgress(true);
            player.setFuseItemSelected(chosenItem.getEndItem());
            player.getPacketSender().sendString(43541, CombineHandler.timeLeft(player));
        } else
            player.sendMessage("You don't meet the requirements for this item!");
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

    public static void claimItem(Player player) {
        if (System.currentTimeMillis() >= player.getFuseCombinationTimer()){
            player.setFuseInProgress(false);
        }
        if (!player.isClaimedFuseItem() && !player.isFuseInProgress() && player.getFuseItemSelected() > 0){ //If the player has an unclaimed item, and a fuse is not in progress
            if (player.getInventory().getFreeSlots() <= 1){
                player.getPacketSender().sendMessage("You need 1 free slot to claim your fused item!");
                return;
            }
            player.getInventory().addItem(player.getFuseItemSelected(), 1);
            World.sendMessageDiscord("[News] " + player.getUsername() + "has Fused a " + ItemDefinition.forId(player.getFuseItemSelected()).getName() + "!");
            player.setClaimedFuseItem(true);
            player.setFuseInProgress(false);
            player.setFuseItemSelected(0);
        }
    }
}
