package qc.maxx.bbps.main;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

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
	private List<PlayerSession> playerSessions = new ArrayList<PlayerSession>();

	public void onEnable() {
		for (Player p : Bukkit.getOnlinePlayers())
			addPlayerSession(new PlayerSession(p.getUniqueId().toString()));

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
		for (PlayerSession player : getPlayerSessions())
			Util.replaceBlocksFromPlayer(this, player);

		getPlayerSessions().clear();
	}

	public List<PlayerSession> getPlayerSessions() {
		return playerSessions;
	}

	public PlayerSession getPlayerSessionByUUID(String playerUIID) {
		for (PlayerSession playerSession : playerSessions) {
			if (playerSession.getPlayerUUID().equals(playerUIID))
				return playerSession;
		}

		return null;
	}

	public void addPlayerSession(PlayerSession playerSession) {
		this.playerSessions.add(playerSession);
	}

	public void removePlayerSession(PlayerSession playerSession) {
		this.playerSessions.remove(playerSession);
	}
}