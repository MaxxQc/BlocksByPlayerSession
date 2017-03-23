package qc.maxx.bbps.event;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import qc.maxx.bbps.main.BBPSPlugin;
import qc.maxx.bbps.session.PlayerSession;
import qc.maxx.bbps.util.ConfigHandler;
import qc.maxx.bbps.util.Util;

public class PlayerEvents implements Listener {
	private BBPSPlugin plugin;
	private Map<String, Integer> taskIds = new HashMap<String, Integer>();

	public PlayerEvents(BBPSPlugin plugin) {
		this.plugin = plugin;
		Bukkit.getPluginManager().registerEvents(this, plugin);
	}

	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent e) {
		if (taskIds.containsKey(e.getPlayer().getUniqueId().toString())) {
			Bukkit.getScheduler().cancelTask(taskIds.get(e.getPlayer().getUniqueId().toString()));
			taskIds.remove(e.getPlayer().getUniqueId().toString());
		}
		plugin.addPlayerSession(new PlayerSession(e.getPlayer().getUniqueId().toString()));
	}

	@EventHandler
	public void onPlayerLeave(PlayerQuitEvent e) {
		if (ConfigHandler.timeLimit == 0) {
			Util.replaceBlocksFromPlayer(plugin, plugin.getPlayerSessionByUUID(e.getPlayer().getUniqueId().toString()));
			plugin.removePlayerSession(plugin.getPlayerSessionByUUID(e.getPlayer().getUniqueId().toString()));
		} else {
			int id = Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
				public void run() {
					Util.replaceBlocksFromPlayer(plugin, plugin.getPlayerSessionByUUID(e.getPlayer().getUniqueId().toString()));
					plugin.removePlayerSession(plugin.getPlayerSessionByUUID(e.getPlayer().getUniqueId().toString()));
					taskIds.remove(e.getPlayer().getUniqueId().toString());
				}
			}, ConfigHandler.timeLimit * 20 * 60);
			taskIds.put(e.getPlayer().getUniqueId().toString(), id);
		}
	}

	@EventHandler
	public void onPlayerKick(PlayerKickEvent e) {
		if (ConfigHandler.timeLimit == 0) {
			Util.replaceBlocksFromPlayer(plugin, plugin.getPlayerSessionByUUID(e.getPlayer().getUniqueId().toString()));
			plugin.removePlayerSession(plugin.getPlayerSessionByUUID(e.getPlayer().getUniqueId().toString()));
		} else {
			int id = Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
				public void run() {
					Util.replaceBlocksFromPlayer(plugin, plugin.getPlayerSessionByUUID(e.getPlayer().getUniqueId().toString()));
					plugin.removePlayerSession(plugin.getPlayerSessionByUUID(e.getPlayer().getUniqueId().toString()));
					taskIds.remove(e.getPlayer().getUniqueId().toString());
				}
			}, ConfigHandler.timeLimit * 20 * 60);
			taskIds.put(e.getPlayer().getUniqueId().toString(), id);
		}
	}
}