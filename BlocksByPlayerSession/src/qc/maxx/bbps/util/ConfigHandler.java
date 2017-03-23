package qc.maxx.bbps.util;

import java.io.File;
import java.io.IOException;

import org.bukkit.Material;

public class ConfigHandler {
	public static int timeLimit;
	public static int blocksPlaceLimit;
	public static int blocksChangeLimit;

	public static String noPermMsg;
	public static String playersOnlyMsg;

	public static String menuName;
	public static Material statsItemMaterial;
	public static String statsItemName;
	public static Material resetItemMaterial;
	public static String resetItemName;

	public static String playersListMenuName;
	public static String statsMenuName;
	public static String resetMenuName;
	public static String nextPageItemName;
	public static String previousPageItemName;
	public static String playerHasBypassPermMsg;
	public static String placedBlocksMsg;
	public static String changedBlocksMsg;

	public static void init(File configPath) {
		configPath.mkdirs();

		File configFile = new File(configPath, "config.yml");

		if (!configFile.exists())
			try {
				configFile.createNewFile();
			} catch (IOException e) {
			}

		Yaml config = new Yaml(configFile);

		if (config.get("global.time-limit") == null) {
			config.set("global.time-limit", 0);
			config.save();
		}

		if (config.get("global.placed-blocks-limit") == null) {
			config.set("global.placed-blocks-limit", 0);
			config.save();
		}

		if (config.get("global.changed-blocks-limit") == null) {
			config.set("global.changed-blocks-limit", 0);
			config.save();
		}

		if (config.get("msg.noperm") == null) {
			config.set("msg.noperm", "&cYou don't have access to this command.");
			config.save();
		}

		if (config.get("msg.players-only") == null) {
			config.set("msg.players-only", "&cYou need to be a player to perform this action.");
			config.save();
		}

		if (config.get("menu.name") == null) {
			config.set("menu.name", "&6BBPS - Menu");
			config.save();
		}

		if (config.get("menu.stats-item-material") == null) {
			config.set("menu.stats-item-material", Material.MAGMA_CREAM.toString());
			config.save();
		}

		if (config.get("menu.stats-item-name") == null) {
			config.set("menu.stats-item-name", "&aStats");
			config.save();
		}

		if (config.get("menu.reset-item-material") == null) {
			config.set("menu.reset-item-material", Material.GOLD_PICKAXE.toString());
			config.save();
		}

		if (config.get("menu.reset-item-name") == null) {
			config.set("menu.reset-item-name", "&cReset");
			config.save();
		}

		if (config.get("players-list.name") == null) {
			config.set("players-list.name", "&ePlayers");
			config.save();
		}

		if (config.get("players-list.stats-menu-name") == null) {
			config.set("players-list.stats-menu-name", "Stats");
			config.save();
		}

		if (config.get("players-list.reset-menu-name") == null) {
			config.set("players-list.reset-menu-name", "Reset");
			config.save();
		}

		if (config.get("players-list.next-page-item-name") == null) {
			config.set("players-list.next-page-item-name", "&dNext page");
			config.save();
		}

		if (config.get("players-list.previous-page-item-name") == null) {
			config.set("players-list.previous-page-item-name", "&dPrevious page");
			config.save();
		}

		if (config.get("players-list.bypass-player-msg") == null) {
			config.set("players-list.bypass-player-msg", "&5Can place how many blocks he wants to");
			config.save();
		}
		
		if (config.get("players-list.placed-blocks-msg") == null) {
			config.set("players-list.placed-blocks-msg", "&5Placed Blocks: &f");
			config.save();
		}

		if (config.get("players-list.changed-blocks-msg") == null) {
			config.set("players-list.changed-blocks-msg", "&5Changed Blocks: &f");
			config.save();
		}

		timeLimit = config.getInteger("global.time-limit");
		blocksPlaceLimit = config.getInteger("global.placed-blocks-limit");
		blocksChangeLimit = config.getInteger("global.changed-blocks-limit");

		noPermMsg = Util.colorize(config.getString("msg.noperm"));
		playersOnlyMsg = Util.colorize(config.getString("msg.players-only"));

		menuName = Util.colorize(config.getString("menu.name"));
		statsItemMaterial = Material.valueOf(config.getString("menu.stats-item-material"));
		statsItemName = Util.colorize(config.getString("menu.stats-item-name"));
		resetItemMaterial = Material.valueOf(config.getString("menu.reset-item-material"));
		resetItemName = Util.colorize(config.getString("menu.reset-item-name"));

		playersListMenuName = Util.colorize(config.getString("players-list.name"));
		statsMenuName = Util.colorize(config.getString("players-list.stats-menu-name"));
		resetMenuName = Util.colorize(config.getString("players-list.reset-menu-name"));
		nextPageItemName = Util.colorize(config.getString("players-list.next-page-item-name"));
		previousPageItemName = Util.colorize(config.getString("players-list.previous-page-item-name"));
		playerHasBypassPermMsg = Util.colorize(config.getString("players-list.bypass-player-msg"));
		placedBlocksMsg = Util.colorize(config.getString("players-list.placed-blocks-msg"));
		changedBlocksMsg = Util.colorize(config.getString("players-list.changed-blocks-msg"));
	}
}