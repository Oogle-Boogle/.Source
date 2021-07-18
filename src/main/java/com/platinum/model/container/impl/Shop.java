package com.platinum.model.container.impl;

import java.text.NumberFormat;
import java.util.HashMap;
import java.util.Map;

import com.platinum.engine.task.TaskManager;
import com.platinum.engine.task.impl.ShopRestockTask;
import com.platinum.model.GameMode;
import com.platinum.model.Item;
import com.platinum.model.PlayerRights;
import com.platinum.model.Skill;
import com.platinum.model.container.ItemContainer;
import com.platinum.model.container.StackType;
import com.platinum.model.definitions.ItemDefinition;
import com.platinum.model.input.impl.EnterAmountToBuyFromShop;
import com.platinum.model.input.impl.EnterAmountToSellToShop;
import com.platinum.util.JsonLoader;
import com.platinum.util.Misc;
import com.platinum.world.World;
import com.platinum.world.content.minigames.impl.RecipeForDisaster;
import com.platinum.world.content.skill.impl.dungeoneering.Dungeoneering;
import com.platinum.world.entity.impl.player.Player;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

/**
 * Messy but perfect Shop System
 *
 * @author Gabriel Hannason
 */

public class Shop extends ItemContainer {

	/*
	 * The shop constructor
	 */
	
	private static final String PREFIX = "Tax Bags";
	
	public Shop(Player player, int id, String name, Item currency, Item[] stockItems) {
		super(player);
		if (stockItems.length > 102)
			throw new ArrayIndexOutOfBoundsException(
					"Stock cannot have more than 100 items; check shop[" + id + "]: stockLength: " + stockItems.length);
		this.id = id;
		this.name = name.length() > 0 ? name : "General Store";
		this.currency = currency;
		this.originalStock = new Item[stockItems.length];
		for (int i = 0; i < stockItems.length; i++) {
			Item item = new Item(stockItems[i].getId(), stockItems[i].getAmount());
			add(item, false);
			this.originalStock[i] = item;
		}
	}

	private final int id;

	private String name;

	private Item currency;

	private Item[] originalStock;

	public Item[] getOriginalStock() {
		return this.originalStock;
	}

	public int getId() {
		return this.id;
	}

	public String getName() {
		return name;
	}

	public Shop setName(String name) {
		this.name = name;
		return this;
	}

	public static String formatNumber(int number) {
		return NumberFormat.getInstance().format(number);
	}

	public Item getCurrency() {
		return currency;
	}

	public Shop setCurrency(Item currency) {
		this.currency = currency;
		return this;
	}

	private boolean restockingItems;

	public boolean isRestockingItems() {
		return restockingItems;
	}

	public void setRestockingItems(boolean restockingItems) {
		this.restockingItems = restockingItems;
	}

	/**
	 * Opens a shop for a player
	 *
	 * @param player
	 *            The player to open the shop for
	 * @return The shop instance
	 */
	public Shop open(Player player) {
		setPlayer(player);
		getPlayer().getPacketSender().sendInterfaceRemoval().sendClientRightClickRemoval();
		getPlayer().setShop(ShopManager.getShops().get(id)).setInterfaceId(INTERFACE_ID).setShopping(true);
		refreshItems();
		if (Misc.getMinutesPlayed(getPlayer()) <= 190)
			getPlayer().getPacketSender()
					.sendMessage("Note: When selling an item to a store, it loses 15% of its original value.");
		return this;
	}

	/**
	 * Refreshes a shop for every player who's viewing it
	 */
	public void publicRefresh() {
		Shop publicShop = ShopManager.getShops().get(id);
		if (publicShop == null)
			return;
		publicShop.setItems(getItems());
		for (Player player : World.getPlayers()) {
			if (player == null)
				continue;
			if (player.getShop() != null && player.getShop().id == id && player.isShopping())
				player.getShop().setItems(publicShop.getItems());
		}
	}

	/**
	 * Checks a value of an item in a shop
	 *
	 * @param player
	 *            The player who's checking the item's value
	 * @param slot
	 *            The shop item's slot (in the shop!)
	 * @param sellingItem
	 *            Is the player selling the item?
	 */
	public void checkValue(Player player, int slot, boolean sellingItem) {
		this.setPlayer(player);
		Item shopItem = new Item(getItems()[slot].getId());
		if (!player.isShopping()) {
			player.getPacketSender().sendInterfaceRemoval();
			return;
		}
		Item item = sellingItem ? player.getInventory().getItems()[slot] : getItems()[slot];
		if (item.getId() == 10835)
			return;
		if (sellingItem) {
			if (!shopBuysItem(id, item)) {
				player.getPacketSender().sendMessage("You cannot sell this item to this store.");
				return;
			}
		}
		int finalValue = 0;
		String finalString = sellingItem ? "" + ItemDefinition.forId(item.getId()).getName() + ": shop will buy for "
				: "" + ItemDefinition.forId(shopItem.getId()).getName() + " currently costs ";
		if (getCurrency().getId() != -1) {
			finalValue = ItemDefinition.forId(item.getId()).getValue();
			String s = currency.getDefinition().getName().toLowerCase().endsWith("s")
					? currency.getDefinition().getName().toLowerCase()
					: currency.getDefinition().getName().toLowerCase() + "s";
			/** CUSTOM CURRENCY, CUSTOM SHOP VALUES **/
					if (id == TOKKUL_EXCHANGE_STORE || id == STARDUST_STORE|| id == RAIDSTORE
							|| id == AGILITY_TICKET_STORE || id == TOKEN_STORE || id == SUIC_NUMBER_ONE_TOKEN_STORE|| id == GENERAL_STORE
							|| id == GRAVEYARD_STORE || id == DBZ_TOKEN_SHOP || id == SANTAS_STORE || id == STARTER_STORE  || id == TAX_BAG_SHOP
							|| id == LOYALTYPOINT_STORE || id == BLOOD_MONEY_STORE || id == BLOOD_MONEY_STORE2 || id == SELL_FOR_TAXBAGS_SHOP
							|| id == DARKLORD_TOKEN_SHOP || id == WOODCUTTING || id == FIREMAKING_SHOP || id == RAIDS_FISHING_STORE || id == GAMBLING_STORE) {
						Object[] obj = ShopManager.getCustomShopData(id, item.getId());
				if (obj == null)
					return;
				finalValue = (int) obj[0];
				s = (String) obj[1];
			}
			if (sellingItem) {
				if (finalValue != 1) {
					finalValue = (int) (finalValue * 0.85);
				}
			}
			finalString += "" + formatNumber((int) finalValue) + " " + s + ".";
		} else {
			Object[] obj = ShopManager.getCustomShopData(id, item.getId());
			if (obj == null)
				return;
			finalValue = (int) obj[0];
			if (sellingItem) {
				if (finalValue != 1) {
					finalValue = (int) (finalValue * 0.85);
				}
			}
			finalString += "" + finalValue + " " + (String) obj[1] + ".";
		}
		if (player != null && finalValue > 0) {
			player.getPacketSender().sendMessage(finalString);
			return;
		}
	}

	public void sellItem(Player player, int slot, int amountToSell) {
		this.setPlayer(player);
		if (!player.isShopping() || player.isBanking()) {
			player.getPacketSender().sendInterfaceRemoval();
			return;
		}

		if (!player.isShopping() || player.isBanking()) {
			player.getPacketSender().sendInterfaceRemoval();
			return;
		}
		Item itemToSell = player.getInventory().getItems()[slot];
		if (!itemToSell.sellable()) {
			player.getPacketSender().sendMessage("This item cannot be sold.");
			return;
		}

		if (id == BLOOD_MONEY_STORE) {
			player.getPacketSender().sendMessage("You cannot sell items to this store.");
			return;
		}
		if (id == BLOOD_MONEY_STORE2) {
			player.getPacketSender().sendMessage("You cannot sell items to this store.");
			return;
		}
		
		if (id == CONSUMABLES) {
			player.sendMessage("You cannot sell items to this store.");
			return;
		}
		if (id == WOODCUTTING) {
			player.sendMessage("You cannot sell items to this store.");
			return;
		}
		if (id == FIREMAKING_SHOP) {
			player.sendMessage("You cannot sell items to this store.");
			return;
		}
		if (id == DARKLORD_TOKEN_SHOP || id == WOODCUTTING || id == RAIDS_FISHING_STORE || id == RAIDSTORE|| id == GENERAL_STORE) {
			player.sendMessage("You cannot sell items to this store.");
			return;
		}

		if (!shopBuysItem(id, itemToSell)) {
			player.getPacketSender().sendMessage("You cannot sell this item to this store.");
			return;
		}
		if (!player.getInventory().contains(itemToSell.getId()) || itemToSell.getId() == 995)
			return;
		if (this.full(itemToSell.getId()))
			return;
		if (player.getInventory().getAmount(itemToSell.getId()) < amountToSell)
			amountToSell = player.getInventory().getAmount(itemToSell.getId());
		if (amountToSell == 0)
			return;
		/*
		 * if(amountToSell > 300) { String s =
		 * ItemDefinition.forId(itemToSell.getId()).getName().endsWith("s") ?
		 * ItemDefinition.forId(itemToSell.getId()).getName() :
		 * ItemDefinition.forId(itemToSell.getId()).getName() + "s";
		 * player.getPacketSender().sendMessage("You can only sell 300 "+s+
		 * " at a time."); return; }
		 */
		int itemId = itemToSell.getId();
		boolean customShop = this.getCurrency().getId() == -1;
		boolean inventorySpace = customShop ? true : false;
		if (!customShop) {
			if (!itemToSell.getDefinition().isStackable()) {
				if (!player.getInventory().contains(this.getCurrency().getId()))
					inventorySpace = true;
			}
			if (player.getInventory().getFreeSlots() <= 0
					&& player.getInventory().getAmount(this.getCurrency().getId()) > 0)
				inventorySpace = true;
			if (player.getInventory().getFreeSlots() > 0
					|| player.getInventory().getAmount(this.getCurrency().getId()) > 0)
				inventorySpace = true;
		}
		int itemValue = 0;
		if (getCurrency().getId() > 0 && id != 119) {
			itemValue = ItemDefinition.forId(itemToSell.getId()).getValue();
		} else {
			Object[] obj = ShopManager.getCustomShopData(id, itemToSell.getId());
			if (obj == null)
				return;
			itemValue = (int) obj[0];
		}

		if (itemValue <= 0)
			return;
		itemValue = (int) (itemValue * 0.85);
		if (itemValue <= 0) {
			itemValue = 1;
		}

		for (int i = amountToSell; i > 0; i--) {
			itemToSell = new Item(itemId);
			if (this.full(itemToSell.getId()) || !player.getInventory().contains(itemToSell.getId())
					|| !player.isShopping())
				break;
			if (!itemToSell.getDefinition().isStackable()) {
				if (inventorySpace) {
					super.switchItem(player.getInventory(), this, itemToSell.getId(), -1);
					if (!customShop) {
						player.getInventory().add(new Item(getCurrency().getId(), itemValue), false);
					} else {
						// Return points here
					}
				} else {
					player.getPacketSender().sendMessage("Please free some inventory space before doing that.");
					break;
				}
			} else {
				if (inventorySpace) {
					super.switchItem(player.getInventory(), this, itemToSell.getId(), amountToSell);
					if (!customShop) {
						player.getInventory().add(new Item(getCurrency().getId(), itemValue * amountToSell), false);
					} else {
						// Return points here
					}
					break;
				} else {
					player.getPacketSender().sendMessage("Please free some inventory space before doing that.");
					break;
				}
			}
			amountToSell--;
		}
		if (customShop) {
			player.getPointsHandler().refreshPanel();
		}
		player.getInventory().refreshItems();
		fireRestockTask();
		refreshItems();
		publicRefresh();
	}

	/**
	 * Buying an item from a shop
	 */
	@Override
	public Shop switchItem(ItemContainer to, Item item, int slot, boolean sort, boolean refresh) {
		final Player player = getPlayer();
		if (player == null)
			return this;
		if (!player.isShopping() || player.isBanking()) {
			player.getPacketSender().sendInterfaceRemoval();
			return this;
		}
		if (this.id == GENERAL_STORE) {
			if (Dungeoneering.doingDungeoneering(player)) {
				player.getPacketSender().sendMessage("You can't do this whilst Dungeoneering");
				return this;
			}
			if (player.getGameMode() == GameMode.IRONMAN) {
				player.getPacketSender()
						.sendMessage("Ironman-players are not allowed to buy items from the general-store.");
				return this;
			}
			if (player.getGameMode() == GameMode.GROUP_IRONMAN) {
				player.getPacketSender()
						.sendMessage("Ironman-players are not allowed to buy items from the general-store.");
				return this;
			}
			if (player.getGameMode() == GameMode.HARDCORE_IRONMAN) {
				player.getPacketSender()
						.sendMessage("Hardcore-ironman-players are not allowed to buy items from the general-store.");
				return this;
			}
		}
		if (!shopSellsItem(item))
			return this;

		if (getItems()[slot].getAmount() <= 1 && id != GENERAL_STORE) {

			player.getPacketSender()
					.sendMessage("The shop can't be 1 items and needs to regenerate some items first..");

		}

		if (item.getAmount() > getItems()[slot].getAmount())
			item.setAmount(getItems()[slot].getAmount());
		int amountBuying = item.getAmount();
		if (id == 21) { // farming cheapfix
			if (getItems()[slot].getAmount() - amountBuying <= 1) {
				amountBuying = getItems()[slot].getAmount() - 1;
				while (getItems()[slot].getAmount() - amountBuying <= 1) {
					if (getItems()[slot].getAmount() - amountBuying == 1)
						break;
					amountBuying--;
				}
			}
		}
		if (getItems()[slot].getAmount() < amountBuying) {
			amountBuying = getItems()[slot].getAmount() - 101;
		}
		if (amountBuying == 0)
			return this;

		if (amountBuying > 25000) {
			player.getPacketSender().sendMessage(
					"You can only buy 25000 " + ItemDefinition.forId(item.getId()).getName() + "s at a time.");
			return this;
		}
		boolean customShop = getCurrency().getId() == -1;
		boolean usePouch = false;
		long playerCurrencyAmount = 0;
		int value = ItemDefinition.forId(item.getId()).getValue();
		String currencyName = "";
		if (getCurrency().getId() != -1) {
			playerCurrencyAmount = player.getInventory().getAmount(currency.getId());
			currencyName = ItemDefinition.forId(currency.getId()).getName().toLowerCase();
			if (currency.getId() == 995) {
				if (player.getMoneyInPouch() >= value) {
					playerCurrencyAmount = player.getMoneyInPouchAsInt();
					if (!(player.getInventory().getFreeSlots() == 0
							&& player.getInventory().getAmount(currency.getId()) == value)) {
						usePouch = true;
					}
				}
			} else {
				/** CUSTOM CURRENCY, CUSTOM SHOP VALUES **/
				if (id == TOKKUL_EXCHANGE_STORE || id == STARDUST_STORE|| id == RAIDSTORE
						|| id == AGILITY_TICKET_STORE || id == TOKEN_STORE || id == SUIC_NUMBER_ONE_TOKEN_STORE
						|| id == GRAVEYARD_STORE || id == DBZ_TOKEN_SHOP || id == SANTAS_STORE || id == STARTER_STORE  || id == TAX_BAG_SHOP
						|| id == LOYALTYPOINT_STORE || id == BLOOD_MONEY_STORE || id == BLOOD_MONEY_STORE2 || id == SELL_FOR_TAXBAGS_SHOP
						|| id == DARKLORD_TOKEN_SHOP || id == AMONG_REWARDS_STORE || id == FIREMAKING_SHOP  || id == WOODCUTTING || id == RAIDS_FISHING_STORE || id == GAMBLING_STORE ) {
					value = (int) ShopManager.getCustomShopData(id, item.getId())[0];
				}
			}
		} else {
			Object[] obj = ShopManager.getCustomShopData(id, item.getId());
			if (obj == null)
				return this;
			value = (int) obj[0];
			currencyName = (String) obj[1];
			if (id == PKING_REWARDS_STORE) {
			} else if (id == LOYALTYPOINT_STORE) {
				playerCurrencyAmount = player.getPointsHandler().getLoyaltyPoints();
			} else if (id == VOTING_REWARDS_STORE) {
				playerCurrencyAmount = player.getPointsHandler().getVotingPoints();
			} else if (id == AMONG_REWARDS_STORE) {
				playerCurrencyAmount = player.getPointsHandler().getAmongPoints();
			} else if (id == DUNGEONEERING_STORE) {
				playerCurrencyAmount = player.getPointsHandler().getDungeoneeringTokens();
			} else if (id == DONATOR_STORE_1) {
				playerCurrencyAmount = player.getPointsHandler().getDonationPoints();
			} else if (id == VOID_STORE) {
				playerCurrencyAmount = player.getPointsHandler().getCustompestcontrolpoints();
			} else if (id == SKILLING_STORE) {
				playerCurrencyAmount = player.getPointsHandler().getSkillPoints();
			} else if (id == TRIVIA_STORE) {
				playerCurrencyAmount = player.getPointsHandler().getTriviaPoints();
			} else if (id == BOSS_POINT_STORE) {
				playerCurrencyAmount = player.getBossPoints();

			} else if (id == DONATOR_STORE_2) {
				playerCurrencyAmount = player.getPointsHandler().getDonationPoints();
			} else if (id == DONATOR_STORE_3) {
				playerCurrencyAmount = player.getPointsHandler().getDonationPoints();
			} else if (id == PRESTIGE_STORE) {
				playerCurrencyAmount = player.getPointsHandler().getPrestigePoints();
			} else if (id == SLAYER_STORE) {
				playerCurrencyAmount = player.getPointsHandler().getSlayerPoints();
			} else if (id == CUSTOMSLAYER_POINT_STORE) {
				playerCurrencyAmount = player.getPointsHandler().getSlayerPoints();
			}
			else if (id == RAIDSTORE) {
			playerCurrencyAmount = player.getPointsHandler().getRaidPoints();
			}
			
		}
		if (value <= 0) {
			return this;
		}
		if (!hasInventorySpace(player, item, getCurrency().getId(), value)) {
			player.getPacketSender().sendMessage("You do not have any free inventory slots.");
			return this;
		}
		if (playerCurrencyAmount <= 0 || playerCurrencyAmount < value) {
			player.getPacketSender()
					.sendMessage("You do not have enough "
							+ ((currencyName.endsWith("s") ? (currencyName) : (currencyName + "s")))
							+ " to purchase this item.");
			return this;
		}
		if (id == SKILLCAPE_STORE_1 || id == SKILLCAPE_STORE_2 || id == SKILLCAPE_STORE_3) {
			for (int i = 0; i < item.getDefinition().getRequirement().length; i++) {
				int req = item.getDefinition().getRequirement()[i];
				if ((i == 3 || i == 5) && req == 99)
					req *= 10;
				if (req > player.getSkillManager().getMaxLevel(i)) {
					player.getPacketSender().sendMessage("You need to have at least level 99 in "
							+ Misc.formatText(Skill.forId(i).toString().toLowerCase()) + " to buy this item.");
					return this;
				}
			}
		} else if (id == GAMBLING_STORE) {
			if (item.getId() == 15084 || item.getId() == 299) {
				if (player.getRights() == PlayerRights.PLAYER) {
					player.getPacketSender().sendMessage("You need to be a member to use these items.");
					return this;
				}
			}
		}

		for (int i = amountBuying; i > 0; i--) {
			if (!shopSellsItem(item)) {
				break;
			}
			if (getItems()[slot].getAmount() < amountBuying) {
				amountBuying = getItems()[slot].getAmount() - 101;

			}

			if (getItems()[slot].getAmount() <= 1 && id != GENERAL_STORE) {

				player.getPacketSender()
						.sendMessage("The shop can't be below 1 items and needs to regenerate some items first...");
				break;
			}
			if (!item.getDefinition().isStackable()) {
				if (playerCurrencyAmount >= value && hasInventorySpace(player, item, getCurrency().getId(), value)) {

					if (!customShop) {
						if (usePouch) {
							player.setMoneyInPouch((player.getMoneyInPouch() - value));
						} else {
							player.getInventory().delete(currency.getId(), value, false);
						}
					} else {
						if (id == PKING_REWARDS_STORE) {
							player.getPointsHandler().setPkPoints(-value, true);
						} else if (id == LOYALTYPOINT_STORE) {
							player.getPointsHandler().setLoyaltyPoints(-value, true);
						} else if (id == VOTING_REWARDS_STORE) {
							player.getPointsHandler().setVotingPoints(-value, true);
						} else if (id == AMONG_REWARDS_STORE) {
							player.getPointsHandler().setAmongPoints(-value, true);
						} else if (id == DUNGEONEERING_STORE) {
							player.getPointsHandler().setDungeoneeringTokens(-value, true);
						} else if (id == DONATOR_STORE_1) {
							player.getPointsHandler().setDonationPoints(-value, true);
						} else if (id == SKILLING_STORE) {
							player.getPointsHandler().setSkillPoints(-value, true);
						} else if (id == BOSS_POINT_STORE) {
							player.setBossPoints(player.getBossPoints() - value);
						} else if (id == TRIVIA_STORE) {
							player.getPointsHandler().setTriviaPoints(-value, true);
						} else if (id == VOID_STORE) {
							player.getPointsHandler().setCustompestcontrolpoints(-value, true);
						} else if (id == DONATOR_STORE_2) {
							player.getPointsHandler().setDonationPoints(-value, true);
						} else if (id == DONATOR_STORE_3) {
							player.getPointsHandler().setDonationPoints(-value, true);
						} else if (id == PRESTIGE_STORE) {
							player.getPointsHandler().setPrestigePoints(-value, true);
						} else if (id == SLAYER_STORE) {
							player.getPointsHandler().setSlayerPoints(-value, true);
						} else if (id == RAIDSTORE) {
							player.getPointsHandler().setRaidPoints(-value, true);
						} else if (id == CUSTOMSLAYER_POINT_STORE) {
							player.getPointsHandler().setSlayerPoints(-value, true);
						}
					}
					

					super.switchItem(to, new Item(item.getId(), 1), slot, false, false);

					playerCurrencyAmount -= value;
				} else {
					break;
				}
			} else {
				if (playerCurrencyAmount >= value && hasInventorySpace(player, item, getCurrency().getId(), value)) {

					int canBeBought = (int) playerCurrencyAmount / (value);
					if (canBeBought >= amountBuying) {
						canBeBought = amountBuying;
					}
					if (canBeBought == 0)
						break;

					if (!customShop) {
						if (usePouch) {
							player.setMoneyInPouch((player.getMoneyInPouch() - (value * canBeBought)));
						} else {
							player.getInventory().delete(currency.getId(), value * canBeBought, false);
						}
					} else {
						if (id == PKING_REWARDS_STORE) {
							player.getPointsHandler().setPkPoints(-value * canBeBought, true);
						} else if (id == LOYALTYPOINT_STORE) {
							player.getPointsHandler().setLoyaltyPoints(-value * canBeBought, true);
						} else if (id == VOTING_REWARDS_STORE) {
							player.getPointsHandler().setVotingPoints(-value * canBeBought, true);
						} else if (id == AMONG_REWARDS_STORE) {
							player.getPointsHandler().setAmongPoints(-value * canBeBought, true);
						} else if (id == DUNGEONEERING_STORE) {
							player.getPointsHandler().setDungeoneeringTokens(-value * canBeBought, true);
						} else if (id == DONATOR_STORE_1) {
							player.getPointsHandler().setDonationPoints(-value * canBeBought, true);
						} else if (id == SKILLING_STORE) {
							player.getPointsHandler().setSkillPoints(-value * canBeBought, true);
						} else if (id == TRIVIA_STORE) {
							player.getPointsHandler().setTriviaPoints(-value * canBeBought, true);
						} else if (id == BOSS_POINT_STORE) {
							player.setBossPoints(player.getBossPoints() - (value * canBeBought));
						} else if (id == DONATOR_STORE_2) {
							player.getPointsHandler().setDonationPoints(-value * canBeBought, true);
						} else if (id == VOID_STORE) {
							player.getPointsHandler().setCustompestcontrolpoints(-value * canBeBought, true);
						} else if (id == DONATOR_STORE_3) {
							player.getPointsHandler().setDonationPoints(-value * canBeBought, true);
						} else if (id == PRESTIGE_STORE) {
							player.getPointsHandler().setPrestigePoints(-value * canBeBought, true);
						} else if (id == SLAYER_STORE) {
							player.getPointsHandler().setSlayerPoints(-value * canBeBought, true);
						} else if (id == CUSTOMSLAYER_POINT_STORE) {
							player.getPointsHandler().setSlayerPoints(-value * canBeBought, true);
						} else if (id == RAIDSTORE) {
							player.getPointsHandler().setRaidPoints(-value * canBeBought, true);
						}
					}
					super.switchItem(to, new Item(item.getId(), canBeBought), slot, false, false);
					playerCurrencyAmount -= value;
					break;
				} else {
					break;
				}
			}
			amountBuying--;
		}
		if (!customShop) {
			if (usePouch) {
				player.getPacketSender().sendString(8135, "" + player.getMoneyInPouch()); // Update
				// the
				// money
				// pouch
			}
		} else {
			player.getPointsHandler().refreshPanel();
		}
		player.getInventory().refreshItems();
		fireRestockTask();
		refreshItems();
		publicRefresh();
		return this;
	}

	/**
	 * Checks if a player has enough inventory space to buy an item
	 *
	 * @param item
	 *            The item which the player is buying
	 * @return true or false if the player has enough space to buy the item
	 */
	public static boolean hasInventorySpace(Player player, Item item, int currency, int pricePerItem) {
		if (player.getInventory().getFreeSlots() >= 1) {
			return true;
		}
		if (item.getDefinition().isStackable()) {
			if (player.getInventory().contains(item.getId())) {
				return true;
			}
		}
		if (currency != -1) {
			if (player.getInventory().getFreeSlots() == 0
					&& player.getInventory().getAmount(currency) == pricePerItem) {
				return true;
			}
		}
		return false;
	}

	@Override
	public Shop add(Item item, boolean refresh) {
		super.add(item, false);
		if (id != RECIPE_FOR_DISASTER_STORE)
			publicRefresh();
		return this;
	}

	@Override
	public int capacity() {
		return 100;
	}

	@Override
	public StackType stackType() {
		return StackType.STACKS;
	}

	@Override
	public Shop refreshItems() {
		if (id == RECIPE_FOR_DISASTER_STORE) {
			RecipeForDisaster.openRFDShop(getPlayer());
			return this;
		}
		for (Player player : World.getPlayers()) {
			if (player == null || !player.isShopping() || player.getShop() == null || player.getShop().id != id)
				continue;
			player.getPacketSender().sendItemContainer(player.getInventory(), INVENTORY_INTERFACE_ID);
			player.getPacketSender().sendItemContainer(ShopManager.getShops().get(id), ITEM_CHILD_ID);
			player.getPacketSender().sendString(NAME_INTERFACE_CHILD_ID, name);
			if (player.getInputHandling() == null || !(player.getInputHandling() instanceof EnterAmountToSellToShop
					|| player.getInputHandling() instanceof EnterAmountToBuyFromShop))
				player.getPacketSender().sendInterfaceSet(INTERFACE_ID, INVENTORY_INTERFACE_ID - 1);
		}
		return this;
	}

	@Override
	public Shop full() {
		getPlayer().getPacketSender().sendMessage("The shop is currently full. Please come back later.");
		return this;
	}

	private boolean shopSellsItem(Item item) {
		return contains(item.getId());
	}

	public void fireRestockTask() {
		if (isRestockingItems() || fullyRestocked())
			return;
		setRestockingItems(true);
		TaskManager.submit(new ShopRestockTask(this));
	}

	public void restockShop() {
		for (int shopItemIndex = 0; shopItemIndex < getOriginalStock().length; shopItemIndex++) {
			int currentStockAmount = getItems()[shopItemIndex].getAmount();
			add(getItems()[shopItemIndex].getId(), getOriginalStock()[shopItemIndex].getAmount() - currentStockAmount);
			// publicRefresh();
			refreshItems();
		}

	}

	public boolean fullyRestocked() {
		if (id == GENERAL_STORE) {
			return getValidItems().size() == 0;
		} else if (id == RECIPE_FOR_DISASTER_STORE) {
			return true;
		}
		if (getOriginalStock() != null) {
			for (int shopItemIndex = 0; shopItemIndex < getOriginalStock().length; shopItemIndex++) {
				if (getItems()[shopItemIndex].getAmount() != getOriginalStock()[shopItemIndex].getAmount())
					return false;
			}
		}
		return true;
	}

	public static boolean shopBuysItem(int shopId, Item item) {
		if (shopId == GENERAL_STORE || shopId ==SELL_FOR_TAXBAGS_SHOP)
			return true;
		if (shopId == DUNGEONEERING_STORE || shopId == BOSS_POINT_STORE || shopId == SANTAS_STORE || shopId == RAIDSTORE
				|| shopId == SKILLING_STORE || shopId == STARTER_STORE || shopId == TRIVIA_STORE
				|| shopId == DONATOR_STORE_1 || shopId == DONATOR_STORE_2 || shopId == VOID_STORE
				|| shopId == DONATOR_STORE_3 || shopId == PKING_REWARDS_STORE || shopId == VOTING_REWARDS_STORE
				|| shopId == RECIPE_FOR_DISASTER_STORE || shopId == DBZ_TOKEN_SHOP
				|| shopId == AGILITY_TICKET_STORE || shopId == TOKEN_STORE || shopId == SUIC_NUMBER_ONE_TOKEN_STORE
				|| shopId == GRAVEYARD_STORE || shopId == TOKKUL_EXCHANGE_STORE || shopId == PRESTIGE_STORE
				|| shopId == STARDUST_STORE || shopId == AMONG_REWARDS_STORE ||  shopId == BLOOD_MONEY_STORE || shopId == SLAYER_STORE || shopId == GAMBLING_STORE
				|| shopId == CUSTOMSLAYER_POINT_STORE || shopId == CONSUMABLES || shopId == FIREMAKING_SHOP  || shopId == WOODCUTTING || shopId == BLOOD_MONEY_STORE2 || shopId == DARKLORD_TOKEN_SHOP || shopId == RAIDSTORE|| shopId == RAIDS_FISHING_STORE || shopId == FIREMAKING_SHOP  || shopId == WOODCUTTING  || shopId == TAX_BAG_SHOP)
			return false;
		Shop shop = ShopManager.getShops().get(shopId);
		if (shop != null && shop.getOriginalStock() != null) {
			for (Item it : shop.getOriginalStock()) {
				if (it != null && it.getId() == item.getId())
					return true;
			}
		}
		return false;
	}

	public static int getSantasStore() {
		return SANTAS_STORE;
	}

	public static int getSkillingStore() {
		return SKILLING_STORE;
	}

	public static int getStarterStore() {
		return STARTER_STORE;
	}

	public static class ShopManager {

		private static Map<Integer, Shop> shops = new HashMap<Integer, Shop>();

		public static Map<Integer, Shop> getShops() {
			return shops;
		}
		
		public static JsonLoader parseShops() {
			System.out.println("starting to load shops..");
			return new JsonLoader() {
				@Override
				public void load(JsonObject reader, Gson builder) {
					int id = reader.get("id").getAsInt();
					String name = reader.get("name").getAsString();
					Item[] items = builder.fromJson(reader.get("items").getAsJsonArray(), Item[].class);
					Item currency = new Item(reader.get("currency").getAsInt());
					if (id != 0)
						System.out.println("Id=" + id + " " + name + " " + currency + " " + "");
					shops.put(id, new Shop(null, id, name, currency, items));
				}

				@Override
				public String filePath() {
					return "./data/def/json/world_shops.json";
				}
			};
		}

		public static Object[] getCustomShopData(int shop, int item) {
			if (shop == VOTING_REWARDS_STORE) {
				switch (item) {
				
                case 3092: //Custom infernal Cape
				case 3932: // Infernal Kiteshield
				case 3956: //Hellfire wings
				case 3975: // Infernal Phat
				case 3976: // Infernal Santa
				case 3977: // Infernal Battlestaff Cosmetic only
				case 19081: //Infernal Sled
                    return new Object[] { 5, "Voting Points" };
				case 19890: //2h double drops scroll
				case 14808: //scroll of praise 
							return new Object[] { 40, "Voting Points" };
				}
				return new Object[] { 100, "Voting Points" };
				
			} else if (shop == SELL_FOR_TAXBAGS_SHOP) {
				switch (item) {

				case 19821:
				case 19754:
					return new Object[] { 20000, "Tax Bags" };
					
				case 12426:
					return new Object[] { 10000, "Tax Bags" };
					
					
				case 933:
					return new Object[] { 15550, "Tax Bags" };
					
					
				case 4669:
					return new Object[] { 3550, "Tax Bags" };
					
					
				case 4670:
					return new Object[] { 3750, "Tax Bags" };
					
				case 4671:
					return new Object[] { 1050, "Tax Bags" };
					
				case 4672:
					return new Object[] { 2575, "Tax Bags" };
					
					
				case 2577:
				case 989:
				case 3082:
					return new Object[] { 5, "Tax Bags" };
					
				case 18967:
				case 19024:
				case 19025:
				case 19026:
				case 19027:
					
					
				case 905:
				case 902:
				case 903:
				case 904:
				case 5161:
					return new Object[] { 15, "Tax Bags" };
					
				case 20016:
				case 20017:
				case 20018:
				case 10720:
				case 14006:
				case 20021:
				case 20022:
				case 18910:
					return new Object[] { 20, "Tax Bags" };
					
				case 17911:
				case 19892:
				case 3956:
				case 3928:
				case 17908:
				case 17909:
				case 3932:
				case 4775:
				case 11732:
				case 12601:
				case 18346:
					return new Object[] { 45, "Tax Bags" };
					
				case 5079:
				case 18933:
				case 4799:
				case 4800:
				case 4801:
				case 3973:
				case 15012:
				case 1499:
					return new Object[] { 115, "Tax Bags" };
					
					
				case 2749:
				case 2750:
				case 2751:
				case 2752:
				case 2753:
				case 2754:
				case 2755:
				case 18865:
					return new Object[] { 65, "Tax Bags" };
					
				case 19137:
				case 19138:
				case 19139:
				case 6041:
				case 15044:
				case 5130:
				case 3073:
					return new Object[] { 55, "Tax Bags" };
					
				case 3960:
				case 3958:
				case 3959:
				case 5187:
				case 5186:
				case 3316:
				case 3931:
				case 14559:
				case 6583:
					return new Object[] { 275, "Tax Bags" };
					
				case 17776:
				case 19131:
				case 19132:
				case 19133:
				case 18942:
				case 18941:
				case 18940:
				case 922:
				case 20002:
					return new Object[] { 97, "Tax Bags" };
					
				case 18380:
				case 18381:
				case 18382:
				case 9006:
				case 3941:
				case 3974:
				case 5162:
					return new Object[] { 145, "Tax Bags" };
					
				case 19721:
				case 19722:
				case 19723:
				case 19736:
				case 19734:
				case 18892:
				case 15418:
				case 15398:
				case 18363:
					return new Object[] { 105, "Tax Bags" };
					
				case 6193:
				case 6194:
				case 6195:
				case 6196:
				case 6197:
				case 6198:
				case 6199:
					return new Object[] { 405, "Tax Bags" };
					
				case 11148:
				case 11149:
				case 11150:
				case 11160:
				case 11161:
					return new Object[] { 625, "Tax Bags" };
					
				case 5131:
				case 4772:
				case 4771:
				case 4770:
				case 12708:
				case 13235:
				case 13239:
				case 18347:
					return new Object[] { 350, "Tax Bags" };
					
				case 15020:
				case 15018:
				case 15019:
				case 15220:
					return new Object[] { 60, "Tax Bags" };
				case 14453:
				case 14457:
				case 14459:
					return new Object[] { 55, "Tax Bags" };
					
				case 4059:
				case 4057:
				case 4058:
					return new Object[] { 2550, "Tax Bags" };
					
				case 5209:
				case 923:
					return new Object[] { 875, "Tax Bags" };
					
				case 12605:
				case 3908:
				case 3909:
				case 3910:
				case 3907:
				case 19720:
					return new Object[] { 1100, "Tax Bags" };	
					
					
				case 3980:
				case 3999:
				case 4000:
				case 4001:
				case 18955:
					return new Object[] { 1350, "Tax Bags" };	
					
					
					
					
				case 15649:
				case 15650:
				case 15651:
				case 15654:
				case 15655:
				case 5167:
				case 15652:
				case 15653:
					return new Object[] { 1350, "Tax Bags" };	
					
				case 4761:
				case 4762:
				case 4763:
				case 4764:
				case 4765:
				case 3905:
				case 5089:
				case 18894:
					return new Object[] { 2225, "Tax Bags" };
					
				case 15045:
				case 930:
				case 926:
				case 5210:
				case 931:
				case 5211:
					return new Object[] { 1150, "Tax Bags" };
					

				case 3820:
				case 3821:
				case 3822:
				case 4781:
				case 4782:
				case 4783:
				case 15032:
				case 4785:
				case 5195:
				case 3914:
				case 3957:
				case 19140:
				case 18950:
					return new Object[] { 2550, "Tax Bags" };
					
				case 3985:
				case 5082:
				case 5083:
				case 5084:
				case 15656:
					return new Object[] { 2850, "Tax Bags" };	
				case 19619:
				case 19470:
				case 19471:
				case 19472:
				case 19473:
				case 19474:
				case 5129:

					return new Object[] { 3550, "Tax Bags" };	
					
					
				case 3064:
				case 3983:
				case 4641:
				case 4642:
				case 4643:
				case 19085:

					return new Object[] { 4050, "Tax Bags" };	
					
				case 19618:
				case 19620:
				case 19691:
				case 19692:
				case 19693:
				case 19694:
				case 19695:
				case 19696:
					return new Object[] { 4350, "Tax Bags" };	
					
					
				case 19159:
				case 19160:
				case 19161:
				case 19162:
				case 19163:
				case 19164:	
				case 19165:
				case 19166:

					return new Object[] { 4775, "Tax Bags" };
					
					
				case 9492:
				case 9493:
				case 9494:
				case 9495:
				case 14490:
				case 14492:	
				case 14494:
				case 2760:

					return new Object[] { 5200, "Tax Bags" };
					
					
					
				case 19727:
				case 19728:
				case 19729:
				case 19730:
				case 19731:
				case 19732:	

					return new Object[] { 5775, "Tax Bags" };
					
				case 13202:
				case 13203:
				case 13204:
				case 13205:
				case 13206:
				case 13207:	

					return new Object[] { 6075, "Tax Bags" };
					
				case 11143:
				case 11144:
				case 11145:
				case 11146:
				case 11147:

					return new Object[] { 6575, "Tax Bags" };
					
				case 4794:
				case 4795:
				case 4796:
				case 4797:
				case 19127:
				case 19128:
				case 19129:

					return new Object[] { 7275, "Tax Bags" };
					
				case 13991:
				case 13992:
				case 13993:
				case 13994:
				case 13995:
				case 14447:
				case 14448:

					return new Object[] { 8050, "Tax Bags" };
					
					
				case 9496:
				case 9497:
				case 9498:
				case 9499:
				case 10905:
				case 19155:

					return new Object[] { 10250, "Tax Bags" };
				}
			} else if (shop == GAMBLING_STORE) {
				switch (item) {
				case 15084:
					return new Object[] { 100000, "Tax Bags" };
				}
			} else if (shop == BLOOD_MONEY_STORE) {
				switch (item) {
				case 11614:
					return new Object[] { 10000, "Blood Money" };
				case 15492:
					return new Object[] { 20000, "Blood Money" };
				case 18967:
					return new Object[] { 8500, "Blood Money" };
				case 15373:
					return new Object[] { 5000, "Blood Money" };
				case 15243:
					return new Object[] { 15000, "Blood Money" };
				case 19024:
				case 19025:
				case 19026:
				case 19027:
					return new Object[] { 7500, "Blood Money" };
				case 13740:
					return new Object[] { 10000, "Blood Money" };
				case 19002:
					return new Object[] { 12500, "Blood Money" };
				case 3445:
					return new Object[] { 30000, "Blood Money" };
				case 20250:
					return new Object[] { 15000, "Blood Money" };
				case 18931:
					return new Object[] { 35000, "Blood Money" };
				case 18963:
				case 18964:
				case 18972:
					return new Object[] { 100000, "Blood Money" };
				}
				return new Object[] { 70000, "Blood Money" };
			} else if (shop == DARKLORD_TOKEN_SHOP) {
				switch(item) {
					
				case 3941:
				case 3974:
					return new Object[] { 10, "Claw Tokens" };
					
				case 18380:
				case 18381:
				case 18382:
				case 9006:

					return new Object[] { 3, "Claw Tokens" };
				case 5162:
					return new Object[] { 20, "Claw Tokens" };	
				case 18392:
					return new Object[] { 5, "Claw Tokens" };
				}
				
			} else if (shop == WOODCUTTING) {
				switch(item) {
					
				case 1351:
					return new Object[] { 1, "tax Bags" };
				case 1353:
					return new Object[] { 2, "tax Bags" };
				case 1355:
					return new Object[] { 5, "tax Bags" };
				case 1357:
					return new Object[] { 7, "tax Bags" };
				case 1359:
					return new Object[] { 12, "tax Bags" };
				case 1361:
					return new Object[] { 25, "tax Bags" };
				case 946:
					return new Object[] { 10, "tax bags" };
				
				}
			} else if (shop == FIREMAKING_SHOP) {
				switch(item) {
					
				case 590:
					return new Object[] { 1, "tax Bags" };
				case 13661:
					return new Object[] { 500, "tax Bags" };
				
				}
				
			} else if (shop == RAIDS_FISHING_STORE) {
				switch(item) {
					
				case 4777:
					return new Object[] { 25, "Fish tokens" };
				case 19479:
					return new Object[] { 20, "Fish tokens" };
				case 18950:
					return new Object[] { 13, "Fish tokens" };
				case 19886:
					return new Object[] { 18, "Fish tokens" };
				case 5185:
					return new Object[] { 12, "Fish tokens" };

				
				}
			} else if (shop == BLOOD_MONEY_STORE2) {
				switch (item) {
				case 20555:
				case 12284:
				case 4706:
					return new Object[] { 100000, "Blood Money" };
				case 13848:// brawlers
				case 13849:
				case 13850:
				case 13851:
				case 13852:
				case 13853:
				case 13854:
				case 13855:
				case 13856:
				case 13857:
					return new Object[] { 3000, "Blood Money" };
				case 12936:
				case 12282:
				case 20998:
					return new Object[] { 600000, "Blood Money" };
				case 15273:
				case 4451:
					return new Object[] { 50, "Blood Money" };
				case 6585:
				case 11732:
				case 6920:
				case 2577:
					return new Object[] { 10000, "Blood Money" };
				case 4153:
					return new Object[] { 5000, "Blood Money" };
				case 11924:
				case 11926:
					return new Object[] { 40000, "Blood Money" };
				case 6914:
				case 6889:
				case 6918:
				case 6916:
				case 6924:
					return new Object[] { 30000, "Blood Money" };
				}
				return new Object[] { 60000, "Blood Money" };
			} else if (shop == STARDUST_STORE) {
				switch (item) {
				case 19089:
				case 19092:
				case 19091:
				case 19094:
				case 19090:
				case 19093:
					return new Object[] { 2000, "Stardust" };
				case 17933:
					return new Object[] { 3000, "Stardust" };
				case 6666:
				case 19055:
					return new Object[] { 2500, "Stardust" };
				case 1666:
					return new Object[] { 3500, "Stardust" };
				case 19935:
					return new Object[] { 4000, "Stardust" };
					
				case 19936:
					return new Object[] { 7500, "Stardust" };
					
				case 15332:
					return new Object[] { 450, "Stardust" };
				case 15373:
					return new Object[] { 350, "Stardust" };
				case 2572:
					return new Object[] { 750, "Stardust" };
				case 11133:
					return new Object[] { 1250, "Stardust" };
					
				}
				return new Object[] { 100, "Stardust Points" };
			} else if (shop == SKILLING_STORE) {
				switch (item) {
				case 454:// Coal ore ( Noted )
					return new Object[] { 3, "Skilling Points" };
				case 15369:// Common mbox
					return new Object[] { 400, "Skilling Points" };
				case 15370:// Uncommon box
					return new Object[] { 600, "Skilling Points" };
				case 15373:
					return new Object[] { 1250, "Skilling Points" };
				case 19002:
					return new Object[] { 1000, "Skilling Points" };
				case 19780:// Korasi
					return new Object[] { 100, "Skilling Points" };
				case 17291:// Blood Necklace
				case 13738:// Arcane
					return new Object[] { 125, "Skilling Points" };
				case 11591:
				case 11587:
				case 11589:
					return new Object[] { 2000, "Skilling Points" };
				case 18979:
					return new Object[] { 10000, "Skilling Points" };
				case 9470:
					return new Object[] { 475, "Skilling Points" };
				case 19708:// Blowpipe
				case 19707:
				case 19706:
					return new Object[] { 1500, "Skilling Points" };
				case 1419:
					return new Object[] { 2000, "Skilling Points" };
				case 18978:
				case 18964:
				case 18963:
				case 18972:
				case 19023: // goldclaws
				case 3082:
				case 12434:
				case 12435:
				case 12436:
				case 18901:
					return new Object[] { 75, "Skilling Points" };
				case 18971:
				case 18903:
				case 14559:
				case 12433:
					return new Object[] { 120, "Skilling Points" };
				case 3083:
					return new Object[] { 95, "Skilling Points" };
				case 16049:
					return new Object[] { 90, "Skilling Points" };
				case 12428:
					return new Object[] { 185, "Skilling Points" };
				case 19054:
				case 19055:
				case 18957:
				case 12430:

					return new Object[] { 150, "Skilling Points" };
				}
				return new Object[] { 150, "Skilling Points" };
			} else if (shop == ENERGY_FRAGMENT_STORE) {
				switch (item) {
				case 5509:
					return new Object[] { 400, "energy fragments" };
				case 5510:
					return new Object[] { 750, "energy fragments" };
				case 5512:
					return new Object[] { 1100, "energy fragments" };
				}
			} else if (shop == DBZ_TOKEN_SHOP) {
				switch (item) {
				case 18337:
					return new Object[] { 350, "DBZ Tokens" };
				case 5127:
				case 5128:
				case 5136:
					return new Object[] { 7500, "DBZ Tokens" };
					
				case 9481:
				case 9482:
				case 9483:
					
				case 9484:
				case 9485:
				case 9486:
					return new Object[] { 5000, "DBZ Tokens" };
				case 9490:
				case 9491:
					return new Object[] { 7000, "DBZ Tokens" };
					
				case 9487:
				case 9488:
				case 9489:
					return new Object[] { 10000, "DBZ Tokens" };
					
				case 13271:
				case 13272:
				case 13273:
				case 13274:
				case 13275:
				case 13594:
				case 13595:
				case 13596:
				case 13597:
				case 13664:
					return new Object[] { 1500, "DBZ Tokens" };
				}
				return new Object[] { 10000, "DBZ Tokens" };
			} else if (shop == TAX_BAG_SHOP) {
				switch (item) {
				
				
				case 19886: // col neck
					return new Object[] {5000, PREFIX};
				case 4770: // crimson pieces
				case 4771:
				case 4772:
					return new Object[] {1500, PREFIX};
				case 4802: // defenders sword
					return new Object[] {2500, PREFIX};
					
				case 3316: // greywave ss
					return new Object[] {750, PREFIX};
				case 18865: // lit sword
					return new Object[] {8000, PREFIX};
				case 3286: // egyptian g
					return new Object[] {20, PREFIX};
				case 3943: // Text neck
					return new Object[] {2500, PREFIX};
				case 3958: // rex (reg)
				case 3959:
				case 3960:
					return new Object[] {1000, PREFIX};
				case 4764: //Suic nr1 gloves/boots
				case 4765:
					return new Object[] {1000, PREFIX};
				case 3064: // defenders ss
					return new Object[] {200, PREFIX};
					
				case 5131: // dmg
					return new Object[] {6000, PREFIX};
					
				case 5133: // suics mg
					return new Object[] {4500, PREFIX};
				case 5132: // vortexs mg
					return new Object[] {3500, PREFIX};
					
				case 16140: // 100% ammy(ruby)
					return new Object[] {5000, PREFIX};
					
				case 20054: // 100% ring(devotion)
					return new Object[] {3000, PREFIX};

				}

			} else if (shop == STARTER_STORE) {
				switch (item) {
				case 902:
				case 903:
				case 904:
				case 905:
				case 3082:
					return new Object[] { 50, "Starter Tickets" };
				case 20016:
				case 20017:
				case 20018:
				case 20021:
				case 20022:
				case 18910:
				case 10720:
				case 14006:
					return new Object[] { 100, "Starter Tickets" };
					
				case 989:
					return new Object[] { 50, "Starter Tickets" };
				case 15243:
					return new Object[] { 1, "Starter Tickets" };
				case 1543:
				case 5161:
					return new Object[] { 200, "Starter Tickets" };
				case 5157:
					return new Object[] { 500, "Starter Tickets" };
				case 5160:
					return new Object[] { 750, "Starter Tickets" };
				case 17911:
				case 17908:
				case 17909:
				case 11732:
					return new Object[] { 300, "Starter Tickets" };
				}
			} else if (shop == RAIDSTORE) {
				switch (item) {
				case 6927:
				case 6928:
				case 6929:
				case 6930:
				case 6931:
					return new Object[] { 10000, "Raid Points" };
				case 1648:
				case 1647:
				case 1855:
				case 2756:
				case 2757:
				case 2758:
				case 2759:
				case 2762:
				case 2763:
				case 2764:
					return new Object[] { 5000, "Raid Points" };
				}
				return new Object[] { 10_000, "Raid Points" };
				
			} else if (shop == BOSS_POINT_STORE) {
				switch (item) {
				case 18349:
				case 18351:
				case 18353:
				case 18355:
				case 18357:
				case 19040:
					return new Object[] { 750, "Boss Points" };
				case 3912:
				case 15374:
				case 5130:
					return new Object[] { 15000, "Boss Points" };
				case 18989:
				case 19935:
					return new Object[] { 8000, "Boss Points" };
				case 19086:
				case 19090:
					return new Object[] { 2000, "Boss Points" };
				case 11730:
				case 11716:
				case 15486:
				case 10550:
				case 10551:
				case 10548:
				case 13263:
				case 18337:
					return new Object[] { 250, "Boss Points" };
				case 6831:
				case 6833:
				case 6829:
				case 14018:
				case 11587:
				case 11588:
					return new Object[] { 2000, "Boss Points" };
				case 19008:
					return new Object[] { 5000, "Boss Points" };
				case 14008:
				case 14009:
				case 14010:
					return new Object[] { 1500, "Boss Points" };
				case 18965:
					return new Object[] { 53000, "Boss Points" };
				case 989:
				case 18782:
					return new Object[] { 65, "Boss Points" };
				case 3667:
				case 17778:
				case 17779:
					return new Object[] { 8000, "Boss Points" };
				case 20012:
				case 20010:
				case 20011:
				case 20020:
				case 20019:
				case 20016:
				case 20017:
				case 20018:
				case 20022:
				case 20021:
					return new Object[] { 250, "Boss Points" };
				case 11720:
				case 11722:
				case 11724:
				case 11726:
					return new Object[] { 200, "Boss Points" };
				case 20000:
				case 20001:
				case 20002:
				case 19335:
				case 11718:
					return new Object[] { 150, "Boss Points" };
				case 5134:
					return new Object[] { 4500, "Boss Points" };
				}
				return new Object[] { 100, "Boss Points" };
			} else if (shop == LOYALTYPOINT_STORE) {
				switch (item) {
				case 1038: // Red Phat
				case 1040: //Yellow Phat
				case 1042: // blue Phat
				case 1044: //green Phat
				case 1046: //purple Phat
				case 1048: //white Phat
				case 1050: // santa hat
									return new Object[] { 600, "Loyalty Points" };
				case 3975: //Infernal phat
				case 3976: //infernal santa
									return new Object[] { 2500, "Loyalty Points" };
				case 18750: //5% demonic olm phat
				case 18751: //5% Demonic Olm Gloves
				case 18748: // Demonic Olm Cape
				case 18749: // Demonic Olm Shield
									return new Object[] { 5000, "Loyalty Points" };
				case 3638: //Advanced Box Pet
									return new Object[] { 6000, "Loyalty Points" };
				case 3824: //Vote mystery Box
				case 3912: //Taxbag Box
				case 6183: //Donation Box
									return new Object[] { 10000, "Loyalty Points" };
				}
				return new Object[] { 100, "Loyalty Points" };
			} else if (shop == DONATOR_STORE_1) {
				switch (item) {
				case 19935: // 5$ bond
					return new Object[] { 10, "Donation Points" };
				case 19936: // 10$ bond
				return new Object[] { 20, "Donation Points" };
				case 19938: //50$ bond
					return new Object[] { 100, "Donation Points" };
				case 5185: //inf overload potion
				case 14808: //Scroll of praise
				case 19890: // 2h double drop
			return new Object[] { 25, "Donation Points" };
				case 5080: // infernal pickaxe
				return new Object[] { 20, "Donation Points" };
				case 5197: // 10% perm dr scroll
						return new Object[] { 100, "Donation Points" };
				case 5170: //inf prayer scroll
					return new Object[] { 500, "Donation Points" };
				case 13201: //supreme herbal bow
				return new Object[] { 1000, "Donation Points" };
				}
				return new Object[] { 150, "Donation Points" };
			} else if (shop == VOID_STORE) {
				switch (item) {
				case 10835:// Tax bag lol
					return new Object[] { 5000000, "Custom Pest Control Points" };
				case 14044:// Black Partyhat
				case 14050:// Black Santa Hat
				case 11288:// Black hween
					return new Object[] { 50, "Custom Pest Control Points" };
				case 11858:// 3rd Melee
					return new Object[] { 75, "Custom Pest Control Points" };
				}
				return new Object[] { 100, "Custom Pest Control Points" };
			} else if (shop == DONATOR_STORE_2) {
				switch (item) {
				case 1647: //Pet MewTwo
				case 1648: //Pet Antman
				case 1855: //Pet Zorbak
				case 2756: //Stoned Toad Pet
				case 2757: //Drakkon Pet
				case 2758: //Galvek Pet
				case 2759: //Vasa Nistirio Pet
				case 2762: // Supreme Darkbeast pet
				case 2763: // Eternal Dragon Pet
				case 2764: // Leo the Lion Pet
				case 3647: // Killer chucky pet
				case 3695: //Pet rock
				case 3957: //hulk pet
				case 5127: // Goku Pet
				case 5128: //Vegeta Pet
				case 5155: //Custom Vorago Pet
				case 5156: // Custom Olm pet
				case 5157: // pet Bulbasaur
				case 5160: //Pet Charmander
				case 5161: // pet joker
				case 5162: //Pet Charizard
				case 11996: //Pet KBD
				case 11981: // Pet Blue Dragon
								return new Object[] { 50, "Donation Points" };
				}
				return new Object[] { 100, "Donation Points" };
			} else if (shop == DONATOR_STORE_3) {
				switch (item) {
				case 14691: //pet mystery box
					return new Object[] { 10, "Donation Points" };
					case 3638: //advanced pet box
					return new Object[] { 20, "Donation Points" };
					case 3639: //extreme pet box
					return new Object[] { 40, "Donation Points" };
					case 3640: //supreme pet box
					return new Object[] { 50, "Donation Points" };
					case 3912: //taxbag box
					return new Object[] { 15, "Donation Points" };
					case 3988: //extreme box
					return new Object[] { 30, "Donation Points" };
					case 4635: //infernal Mystery Box
					return new Object[] { 25, "Donation Points" };
					case 6183: // Donation Box
					return new Object[] { 15, "Donation Points" };
					case 18768: //Ultra Donation box
					return new Object[] { 40, "Donation Points" };
					case 6199: // advanced box
					case 15374: // Supreme mystery box
	return new Object[] { 25, "Donation Points" };
				}
				return new Object[] { 100, "Donation Points" };
			} else if (shop == AGILITY_TICKET_STORE) {
				switch (item) {
				case 14936:
				case 14938:
					return new Object[] { 60, "agility tickets" };
				case 10941:
				case 10939:
				case 10940:
				case 10933:
					return new Object[] { 20, "agility tickets" };
				case 13661:
					return new Object[] { 100, "agility tickets" };
				}
			} else if (shop == TOKEN_STORE) {
				switch (item) {
				case 3648:
				case 3649:
				case 3650:
				case 3651:
				case 3652:
				case 3659:
					return new Object[] { 10000, "Colorful Tokens" };
				case 5118:
				case 5119:
				case 5120:
					return new Object[] { 50000, "Colorful Tokens" };
				case 19935:
					return new Object[] { 40000, "Colorful Tokens" };
				case 19936:
					return new Object[] { 75000, "Colorful Tokens" };
				case 3912:
					return new Object[] { 2500, "Colorful Tokens" };

				}
			} else if (shop == SUIC_NUMBER_ONE_TOKEN_STORE) {
				switch (item) {
				case 19935:
					return new Object[] { 1500, "VIP Gems" };
				case 19936:
					return new Object[] { 2750, "VIP Gems" };
				case 7759:
					return new Object[] { 5000, "VIP Gems" };
				case 7760:
					return new Object[] { 5000, "VIP Gems" };
				case 7761:
					return new Object[] { 5000, "VIP Gems" };
				case 7762:
					
				case 13999:
					return new Object[] { 3000, "VIP Gems" };
					
				case 7682:
					return new Object[] { 3000, "VIP Gems" };
				case 7683:
					return new Object[] { 3000, "VIP Gems" };
				case 7684:
					return new Object[] { 3000, "VIP Gems" };
				case 7686:
					return new Object[] { 3000, "VIP Gems" };
				case 7687:
					return new Object[] { 3000, "VIP Gems" };
				case 7688:
					return new Object[] { 3000, "VIP Gems" };
				case 7763:
					return new Object[] { 5000, "VIP Gems" };
				case 7764:
					return new Object[] { 5000, "VIP Gems" };
				case 7765:
					return new Object[] { 5000, "VIP Gems" };

				}
			} else if (shop == GRAVEYARD_STORE) {
				switch (item) {
				case 18337:
					return new Object[] { 350, "zombie fragments" };
				case 20010:
				case 20011:
				case 20012:
				case 20009:
				case 20020:
				case 10551:
					return new Object[] { 500, "zombie fragments" };
				case 10548:
				case 10549:
				case 10550:
				case 11846:
				case 11848:
				case 11850:
				case 11852:
				case 11854:
				case 11856:
					return new Object[] { 200, "zombie fragments" };
				case 11842:
				case 11844:
				case 7592:
				case 7593:
				case 7594:
				case 7595:
				case 7596:
					return new Object[] { 150, "zombie fragments" };
				case 15241:
					return new Object[] { 1250, "zombie fragments" };
				case 18889:
				case 18890:
				case 18891:

				case 16137:
				case 13045:
				case 13047:
				case 16403:
				case 16425:
				case 16955:
					return new Object[] { 2500, "zombie fragments" };
				case 1:
				case 15243:
					return new Object[] { 2, "zombie fragments" };
				}
				return new Object[] { 10000, "zombie fragments" };
			} else if (shop == TOKKUL_EXCHANGE_STORE) {
				switch (item) {
				case 11978:
					return new Object[] { 300000, "tokkul" };
				case 438:
				case 436:
					return new Object[] { 10, "tokkul" };
				case 440:
					return new Object[] { 25, "tokkul" };
				case 453:
					return new Object[] { 30, "tokkul" };
				case 442:
					return new Object[] { 30, "tokkul" };
				case 444:
					return new Object[] { 40, "tokkul" };
				case 447:
					return new Object[] { 70, "tokkul" };
				case 449:
					return new Object[] { 120, "tokkul" };
				case 451:
					return new Object[] { 250, "tokkul" };
				case 1623:
					return new Object[] { 20, "tokkul" };
				case 1621:
					return new Object[] { 40, "tokkul" };
				case 1619:
					return new Object[] { 70, "tokkul" };
				case 1617:
					return new Object[] { 150, "tokkul" };
				case 1631:
					return new Object[] { 1600, "tokkul" };
				case 6571:
					return new Object[] { 50000, "tokkul" };
				case 11128:
					return new Object[] { 22000, "tokkul" };
				case 6522:
					return new Object[] { 20, "tokkul" };
				case 6524:
				case 6523:
				case 6526:
					return new Object[] { 5000, "tokkul" };
				case 6528:
				case 6568:
					return new Object[] { 800, "tokkul" };
				}
			} else if (shop == DUNGEONEERING_STORE) {
				switch (item) {
				case 18351:
				case 18349:
				case 18353:
				case 18357:
				case 18355:
				case 18359:
				case 18361:
				case 18363:
					return new Object[] { 150000, "Dungeoneering tokens" };
				case 16955:
				case 16425:
				case 16403:
					return new Object[] { 300000, "Dungeoneering tokens" };
				case 18335:
				case 18509:
					return new Object[] { 75000, "Dungeoneering tokens" };
				case 19709:
					return new Object[] { 500000, "Dungeoneering tokens" };
				}
			} else if (shop == TRIVIA_STORE) {
				switch (item) {
				case 19935:
					return new Object[] { 200, "Trivia Points" };
				case 19936:
					return new Object[] { 300, "Trivia Points" };
				case 15648:
					return new Object[] { 10, "Trivia Points" };
					
				case 6483:
					return new Object[] { 300, "Trivia Points" };
					
				case 6484:
					
				case 6486:
					return new Object[] { 250, "Trivia Points" };
					
					
				case 6445:
				case 6447:
				case 6446:
				case 6443:
				case 6444:
					return new Object[] { 250, "Trivia Points" };
					
				case 2771:
				case 2867:
				case 2868:
				case 2870:
				case 2772:
				case 2869:
					return new Object[] { 500, "Trivia Points" };
					
				case 15000:
				case 1419:
					
					return new Object[] { 1000, "Trivia Points" };
									
				}
				
			} else if (shop == CUSTOMSLAYER_POINT_STORE) {
				switch (item) {

				case 6199:
					return new Object[] { 500, "Slayer points" };
				case 19935:
					return new Object[] { 1250, "Slayer points" };
				case 19890:
					return new Object[] { 7500, "Slayer points" };
				case 19336:
				case 19337:
				case 19338:
					return new Object[] { 7500, "Slayer points" };
				case 19339:
				case 19340:
				case 19341:
					return new Object[] { 7500, "Slayer points" };
				case 19342:
					return new Object[] { 7500, "Slayer points" };
				case 19343:
				case 19344:
				case 19345:
				case 14587:
				case 18994:
					return new Object[] { 7500, "Slayer points" };
				case 1837:
				case 3060:
				case 9470:
					return new Object[] { 12000, "Slayer points" };
				case 18782:
					return new Object[] { 50, "Slayer points" };
				case 15374:
					return new Object[] { 2500, "Slayer points" };
				}

			} else if (shop == PRESTIGE_STORE) {
				switch (item) {
				case 1042:
					return new Object[] { 100, "Prestige Points" };
				case 19935:
					return new Object[] { 7500, "Prestige points" };
				case 3072:
					return new Object[] { 60, "Prestige points" };
				case 3666:
					return new Object[] { 200, "Prestige points" };
				case 3286:
					return new Object[] { 50, "Prestige points" };
				case 19007:
					return new Object[] { 30, "Prestige points" };
				case 12428:
					return new Object[] { 45, "Prestige points" };
				case 11588:
				case 11587:
				case 11589:
				case 11591:
					return new Object[] { 75, "Prestige points" };
				case 15373:
					return new Object[] { 20, "Prestige points" };
				case 6183:
					return new Object[] { 45, "Prestige points" };
				case 3444:
					return new Object[] { 70, "Prestige points" };
				case 10408:
				case 10410:
					return new Object[] { 15, "Prestige points" };
				case 10404:
				case 10406:
					return new Object[] { 15, "Prestige points" };
				case 20000:
				case 20001:
				case 20002:
					return new Object[] { 5, "Prestige points" };
				}
			} else if (shop == SLAYER_STORE) {
				switch (item) {

				case 6199:
					return new Object[] { 500, "Slayer points" };
				case 19935:
					return new Object[] { 1250, "Slayer points" };
				case 19890:
					return new Object[] { 7500, "Slayer points" };
				case 4772:
				case 4771:
				case 4770:
					return new Object[] { 400, "Slayer points" };
				case 17896:
				case 17897:
				case 17898:
					return new Object[] { 1500, "Slayer points" };
				case 19101:
					return new Object[] { 1000, "Slayer points" };
				case 3322:
				case 3315:
				case 3318:
					return new Object[] { 2000, "Slayer points" };
				case 3313:
				case 3314:
				case 3312:
					return new Object[] { 3000, "Slayer points" };
					
				case 3810:
				case 3813:
				case 3814:
					return new Object[] { 5000, "Slayer points" };
				case 3811:
				case 3812:

				case 3815:
					return new Object[] { 7500, "Slayer points" };
					
				case 19087:
				case 19103:
				case 19886:
					return new Object[] { 2500, "Slayer points" };
				case 3912:
					return new Object[] { 500, "Slayer points" };
				case 18782:
					return new Object[] { 50, "Slayer points" };
				case 15374:
					return new Object[] { 2500, "Slayer points" };
				}

			}
			return null;
		}
	}

	/**
	 * The shop interface id.
	 */
	public static final int INTERFACE_ID = 3824;

	/**
	 * The starting interface child id of items.
	 */
	public static final int ITEM_CHILD_ID = 3900;

	/**
	 * The interface child id of the shop's name.
	 */
	public static final int NAME_INTERFACE_CHILD_ID = 3901;

	/**
	 * The inventory interface id, used to set the items right click values to
	 * 'sell'.
	 */
	public static final int INVENTORY_INTERFACE_ID = 3823;

	/*
	 * Declared shops
	 */

	public static final int DONATOR_STORE_1 = 48;
	public static final int DONATOR_STORE_2 = 49;
	public static final int DONATOR_STORE_3 = 54;

	public static final int TRIVIA_STORE = 50;

	public static final int GENERAL_STORE = 12;
	public static final int RECIPE_FOR_DISASTER_STORE = 36;
	private static final int CONSUMABLES = 6;
	private static final int VOTING_REWARDS_STORE = 27;
	private static final int AMONG_REWARDS_STORE = 33;
	private static final int PKING_REWARDS_STORE = 26;
	private static final int ENERGY_FRAGMENT_STORE = 33;
	private static final int AGILITY_TICKET_STORE = 39;
	private static final int GRAVEYARD_STORE = 42;
	private static final int TOKKUL_EXCHANGE_STORE = 43;
	private static final int DBZ_TOKEN_SHOP = 51;
	private static final int FIREMAKING_SHOP = 15;
	private static final int SANTAS_STORE = 57;
	private static final int SKILLING_STORE = 59;
	private static final int STARTER_STORE = 58;
	private static final int SKILLCAPE_STORE_1 = 8;
	private static final int SKILLCAPE_STORE_2 = 9;
	private static final int SKILLCAPE_STORE_3 = 10;
	private static final int GAMBLING_STORE = 41;
	private static final int DUNGEONEERING_STORE = 44;
	private static final int PRESTIGE_STORE = 46;
	public static final int BOSS_POINT_STORE = 92;
	public static final int RAIDSTORE = 124;
	private static final int SLAYER_STORE = 47;
	public static final int STARDUST_STORE = 55;
	private static final int LOYALTYPOINT_STORE = 205;

	private static final int BLOOD_MONEY_STORE = 100;
	private static final int BLOOD_MONEY_STORE2 = 101;
	private static final int VOID_STORE = 115;
	private static final int TOKEN_STORE = 116;
	private static final int CUSTOMSLAYER_POINT_STORE = 117;
	private static final int SUIC_NUMBER_ONE_TOKEN_STORE = 118;
	private static final int SELL_FOR_TAXBAGS_SHOP = 119;
	private static final int DARKLORD_TOKEN_SHOP = 120;
	private static final int TAX_BAG_SHOP = 121;
	
	private static final int WOODCUTTING = 122;
	private static final int RAIDS_FISHING_STORE = 123;
}