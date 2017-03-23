package qc.maxx.bbps.command;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import qc.maxx.bbps.util.ConfigHandler;

public class BBPSCommand implements CommandExecutor {
	public static Inventory BBPSInventory = Bukkit.createInventory(null, 9, ConfigHandler.menuName);

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (!sender.hasPermission("bbps.menu.open")) {
			sender.sendMessage(ConfigHandler.noPermMsg);
			return true;
		}

		if (!(sender instanceof Player)) {
			sender.sendMessage(ConfigHandler.playersOnlyMsg);
			return true;
		}

		Player p = (Player) sender;

		ItemStack statsItem = new ItemStack(ConfigHandler.statsItemMaterial, 1);
		ItemMeta statsItemMeta = statsItem.getItemMeta();
		statsItemMeta.setDisplayName(ConfigHandler.statsItemName);
		statsItem.setItemMeta(statsItemMeta);

		ItemStack resetItem = new ItemStack(ConfigHandler.resetItemMaterial, 1);
		ItemMeta resetItemMeta = resetItem.getItemMeta();
		resetItemMeta.setDisplayName(ConfigHandler.resetItemName);
		resetItem.setItemMeta(resetItemMeta);

		if (sender.hasPermission("bbps.menu.stats") && sender.hasPermission("bbps.menu.reset")) {
			BBPSInventory.setItem(2, statsItem);
			BBPSInventory.setItem(6, resetItem);
		} else if (sender.hasPermission("bbps.menu.stats")) {
			BBPSInventory.setItem(4, statsItem);
		} else if (sender.hasPermission("bbps.menu.reset"))
			BBPSInventory.setItem(4, resetItem);

		p.openInventory(BBPSInventory);
		return true;
	}
}