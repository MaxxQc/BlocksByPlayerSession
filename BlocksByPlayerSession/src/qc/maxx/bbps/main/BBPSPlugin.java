package qc.maxx.bbps.main;

import java.io.File;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import qc.maxx.bbps.command.BBPSCommand;
import qc.maxx.bbps.event.BlockEventsHigher;
import qc.maxx.bbps.event.BlockEventsLower;
import qc.maxx.bbps.event.InventoryEvents;
import qc.maxx.bbps.event.PlayerEvents;
import qc.maxx.bbps.session.PlayerSession;
import qc.maxx.bbps.util.ConfigHandler;
import qc.maxx.bbps.util.Util;

public class BBPSPlugin extends JavaPlugin {
	public void onEnable() {
		for (Player p : Bukkit.getOnlinePlayers())
			Util.addPlayerSession(new PlayerSession(p.getUniqueId().toString()));

		ConfigHandler.init(new File(getDataFolder().getAbsolutePath()));

		String version = Bukkit.getBukkitVersion().replaceAll("[^R.0-9]", "").split("\\.")[1];
		if (Integer.valueOf(version) >= 8)
			new BlockEventsHigher(this);
		else
			new BlockEventsLower(this);

		new InventoryEvents(this);
		new PlayerEvents(this);
		getServer().getPluginCommand("bbps").setExecutor(new BBPSCommand());
	}

	public void onDisable() {
		for (PlayerSession player : Util.getPlayerSessions())
			Util.replaceBlocksFromPlayer(this, player);

		Util.getPlayerSessions().clear();
	}
}