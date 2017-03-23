package qc.maxx.bbps.event;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import qc.maxx.bbps.main.BBPSPlugin;
import qc.maxx.bbps.session.PlayerSession;
import qc.maxx.bbps.util.ConfigHandler;
import qc.maxx.bbps.util.StringNumberComparator;
import qc.maxx.bbps.util.Util;

public class InventoryEvents implements Listener {
	private BBPSPlugin plugin;

	public InventoryEvents(BBPSPlugin plugin) {
		this.plugin = plugin;
		Bukkit.getPluginManager().registerEvents(this, plugin);
	}

	@EventHandler
	public void onInventoryClick(InventoryClickEvent e) {
		if (e.getInventory().getName().equals(ConfigHandler.menuName) || e.getInventory().getName().startsWith(ConfigHandler.playersListMenuName)) {
			e.setCancelled(true);

			List<String> playersList = new ArrayList<String>();
			for (Player p : Bukkit.getOnlinePlayers())
				playersList.add(p.getName());

			for (int i = 1; i <= 325; i++)
				playersList.add(String.valueOf(i));

			Collections.sort(playersList, new StringNumberComparator());

			if (e.getWhoClicked() instanceof Player) {
				Player clicker = (Player) e.getWhoClicked();
				Inventory[] playersInvStats;
				Inventory[] playersInvReset;

				if (playersList.size() > 50)
					playersInvStats = new Inventory[(int) Math.ceil(((double) playersList.size() - 50) / 46) + 1];
				else
					playersInvStats = new Inventory[1];

				if (playersList.size() > 50)
					playersInvReset = new Inventory[(int) Math.ceil(((double) playersList.size() - 50) / 46) + 1];
				else
					playersInvReset = new Inventory[1];

				for (int i = 0; i < playersInvStats.length; i++)
					playersInvStats[i] = Bukkit.createInventory(null, 54,
							ConfigHandler.playersListMenuName + " - " + ConfigHandler.statsMenuName + " (" + String.valueOf(i + 1) + ")");

				for (int i = 0; i < playersInvReset.length; i++)
					playersInvReset[i] = Bukkit.createInventory(null, 54,
							ConfigHandler.playersListMenuName + " - " + ConfigHandler.resetMenuName + " (" + String.valueOf(i + 1) + ")");

				if (e.getCurrentItem() != null) {
					if (e.getCurrentItem().getType() == ConfigHandler.statsItemMaterial) {
						if (playersInvStats.length > 1) {
							ItemStack nextPageItem = new ItemStack(Material.PAPER, 1);
							ItemMeta nextPageItemMeta = nextPageItem.getItemMeta();
							nextPageItemMeta.setDisplayName(ConfigHandler.nextPageItemName);
							nextPageItem.setItemMeta(nextPageItemMeta);

							playersInvStats[0].setItem(53, nextPageItem);
						}

						int toIndex = 50;

						if (toIndex > playersList.size())
							toIndex = playersList.size();

						fillInventoryWithPlayersStats(playersList.subList(0, toIndex), clicker, playersInvStats, 0, (playersInvStats.length == 1));
						clicker.openInventory(playersInvStats[0]);
					}

					if (e.getCurrentItem().getType() == ConfigHandler.resetItemMaterial) {
						e.getWhoClicked().sendMessage(Util.colorize("&cSorry but this section is still under construction!"));

						/*
						 * if (playersInvReset.length > 1) { ItemStack
						 * nextPageItem = new ItemStack(Material.PAPER, 1);
						 * ItemMeta nextPageItemMeta =
						 * nextPageItem.getItemMeta();
						 * nextPageItemMeta.setDisplayName(ConfigHandler.
						 * nextPageItemName);
						 * nextPageItem.setItemMeta(nextPageItemMeta);
						 * 
						 * playersInvReset[0].setItem(53, nextPageItem); }
						 * 
						 * int toIndex = 46;
						 * 
						 * if (toIndex > playersList.size()) toIndex =
						 * playersList.size();
						 * 
						 * fillInventoryWithPlayersForReset(playersList.subList(
						 * 0, toIndex), clicker, playersInvReset, 0,
						 * (playersInvReset.length == 1));
						 * clicker.openInventory(playersInvReset[0]);
						 */
					}

					if (e.getCurrentItem().hasItemMeta() && e.getCurrentItem().getItemMeta().hasDisplayName()) {
						if (e.getCurrentItem().getItemMeta().getDisplayName().equals(ConfigHandler.nextPageItemName)) {
							String invType = e.getInventory().getName()
									.substring(new String(ConfigHandler.playersListMenuName + " - ").length(), e.getInventory().getName().length()).split(" ")[0];
							int nextInv = Integer.valueOf(e.getInventory().getName().replaceAll("[^0-9]", ""));

							if (invType.equals(ConfigHandler.statsMenuName)) {
								if (playersInvStats.length > 1) {
									if (nextInv > 0) {
										ItemStack previousPageItem = new ItemStack(Material.PAPER, 1);
										ItemMeta previousPageItemMeta = previousPageItem.getItemMeta();
										previousPageItemMeta.setDisplayName(ConfigHandler.previousPageItemName);
										previousPageItem.setItemMeta(previousPageItemMeta);

										playersInvStats[nextInv].setItem(45, previousPageItem);
									}

									if (((nextInv + 1) != playersInvStats.length)) {
										ItemStack nextPageItem = new ItemStack(Material.PAPER, 1);
										ItemMeta nextPageItemMeta = nextPageItem.getItemMeta();
										nextPageItemMeta.setDisplayName(ConfigHandler.nextPageItemName);
										nextPageItem.setItemMeta(nextPageItemMeta);

										playersInvStats[nextInv].setItem(53, nextPageItem);
									}
								}

								int fromIndex = (nextInv * 46) + 4;
								int toIndex = fromIndex + 46;

								if (toIndex > playersList.size())
									toIndex = playersList.size();

								fillInventoryWithPlayersStats(playersList.subList(fromIndex, (nextInv == playersInvStats.length) ? playersList.size() : toIndex), clicker,
										playersInvStats, nextInv, (playersInvStats.length == 1));
								clicker.openInventory(playersInvStats[nextInv]);
							}
						}

						if (e.getCurrentItem().getItemMeta().getDisplayName().equals(ConfigHandler.previousPageItemName)) {
							String invType = e.getInventory().getName()
									.substring(new String(ConfigHandler.playersListMenuName + " - ").length(), e.getInventory().getName().length()).split(" ")[0];
							int nextInv = Integer.valueOf(e.getInventory().getName().replaceAll("[^0-9]", ""));
							int previousInv = nextInv - 2;

							if (invType.equals(ConfigHandler.statsMenuName)) {
								if (playersInvStats.length > 1) {
									if (previousInv > 0) {
										ItemStack previousPageItem = new ItemStack(Material.PAPER, 1);
										ItemMeta previousPageItemMeta = previousPageItem.getItemMeta();
										previousPageItemMeta.setDisplayName(ConfigHandler.previousPageItemName);
										previousPageItem.setItemMeta(previousPageItemMeta);

										playersInvStats[previousInv].setItem(45, previousPageItem);
									}

									ItemStack nextPageItem = new ItemStack(Material.PAPER, 1);
									ItemMeta nextPageItemMeta = nextPageItem.getItemMeta();
									nextPageItemMeta.setDisplayName(ConfigHandler.nextPageItemName);
									nextPageItem.setItemMeta(nextPageItemMeta);

									playersInvStats[previousInv].setItem(53, nextPageItem);
								}

								int fromIndex = (previousInv * 46) + 4;
								int toIndex = fromIndex + 46;

								if (previousInv == 0)
									fromIndex = 0;

								fillInventoryWithPlayersStats(playersList.subList(fromIndex, toIndex), clicker, playersInvStats, previousInv, (playersInvStats.length == 1));
								clicker.openInventory(playersInvStats[previousInv]);
							}
						}
					}
				}

				clicker.updateInventory();
			}
		}
	}

	public void fillInventoryWithPlayersStats(List<String> players, Player clicker, Inventory[] inv, int invNumber, boolean onlyOneInv) {
		int currentSlot = 0;
		players = players.subList(0, players.size());

		for (String p : players) {
			if (currentSlot < 54) {
				if (!onlyOneInv) {
					if (invNumber == 0) {
						if (currentSlot == 43)
							currentSlot++;

						if (currentSlot == 44)
							currentSlot++;

						if (currentSlot == 52)
							currentSlot++;

						if (currentSlot == 53)
							currentSlot++;
					} else if ((invNumber + 1) == inv.length) {
						if (currentSlot == 36)
							currentSlot++;

						if (currentSlot == 37)
							currentSlot++;

						if (currentSlot == 45)
							currentSlot++;

						if (currentSlot == 46)
							currentSlot++;
					} else {
						if (currentSlot == 36)
							currentSlot++;

						if (currentSlot == 37)
							currentSlot++;

						if (currentSlot == 43)
							currentSlot++;

						if (currentSlot == 44)
							currentSlot++;

						if (currentSlot == 45)
							currentSlot++;

						if (currentSlot == 46)
							currentSlot++;

						if (currentSlot == 52)
							currentSlot++;

						if (currentSlot == 53)
							currentSlot++;
					}
				}

				inv[invNumber].setItem(currentSlot, getPlayerHead(p));
				currentSlot++;
			}
		}
	}

	public void fillInventoryWithPlayersForReset(List<String> players, Player clicker, Inventory[] inv, int invNumber, boolean onlyOneInv) {
		int currentSlot = 0;
		players = players.subList(0, players.size());

		for (String p : players) {
			if (currentSlot < 54) {
				if (!onlyOneInv) {
					if (invNumber == 0) {
						if (currentSlot == 0)
							currentSlot++;

						if (currentSlot == 1)
							currentSlot++;

						if (currentSlot == 9)
							currentSlot++;

						if (currentSlot == 10)
							currentSlot++;

						if (currentSlot == 43)
							currentSlot++;

						if (currentSlot == 44)
							currentSlot++;

						if (currentSlot == 52)
							currentSlot++;

						if (currentSlot == 53)
							currentSlot++;
					} else if ((invNumber + 1) == inv.length) {
						if (currentSlot == 0)
							currentSlot++;

						if (currentSlot == 1)
							currentSlot++;

						if (currentSlot == 9)
							currentSlot++;

						if (currentSlot == 36)
							currentSlot++;

						if (currentSlot == 37)
							currentSlot++;

						if (currentSlot == 45)
							currentSlot++;

						if (currentSlot == 46)
							currentSlot++;
					} else {
						if (currentSlot == 0)
							currentSlot++;

						if (currentSlot == 1)
							currentSlot++;

						if (currentSlot == 9)
							currentSlot++;

						if (currentSlot == 36)
							currentSlot++;

						if (currentSlot == 37)
							currentSlot++;

						if (currentSlot == 43)
							currentSlot++;

						if (currentSlot == 44)
							currentSlot++;

						if (currentSlot == 45)
							currentSlot++;

						if (currentSlot == 46)
							currentSlot++;

						if (currentSlot == 52)
							currentSlot++;

						if (currentSlot == 53)
							currentSlot++;
					}
				}

				inv[invNumber].setItem(currentSlot, getPlayerHead(p));
				currentSlot++;
			}
		}
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public ItemStack getPlayerHead(String name) {
		ItemStack skull = new ItemStack(Material.SKULL_ITEM, 1, (byte) 3);
		SkullMeta skullMeta = (SkullMeta) skull.getItemMeta();
		PlayerSession p = null;
		try {
			p = plugin.getPlayerSessionByUUID(Bukkit.getPlayerExact(name).getUniqueId().toString());
		} catch (NullPointerException npe) {
		}

		skullMeta.setOwner(name);
		skullMeta.setDisplayName(Util.colorize("&9" + name));

		if (p != null && !Bukkit.getPlayer(name).hasPermission("bbps.bypass.overall")) {
			List<String> itemLore = new ArrayList();
			itemLore.add(" ");

			if (ConfigHandler.blocksChangeLimit > 0) {
				itemLore.add(Util.colorize(ConfigHandler.placedBlocksMsg + p.getPlacedBlocks().size()
						+ (Bukkit.getPlayerExact(name).hasPermission("bbps.bypass.placedblocks") ? "" : " / " + ConfigHandler.blocksPlaceLimit)));
				itemLore.add(Util.colorize(ConfigHandler.changedBlocksMsg + p.getChangedBlocks().size()
						+ (Bukkit.getPlayerExact(name).hasPermission("bbps.bypass.changedblocks") ? "" : " / " + ConfigHandler.blocksChangeLimit)));
			} else {
				itemLore.add(Util.colorize(ConfigHandler.placedBlocksMsg + p.getPlacedBlocks().size()));
				itemLore.add(Util.colorize(ConfigHandler.changedBlocksMsg + p.getChangedBlocks().size()));
			}

			skullMeta.setLore(itemLore);
		}

		skull.setItemMeta(skullMeta);
		return skull;
	}
}