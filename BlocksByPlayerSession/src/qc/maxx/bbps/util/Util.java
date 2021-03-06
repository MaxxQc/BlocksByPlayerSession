package qc.maxx.bbps.util;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.block.Sign;

import qc.maxx.bbps.main.BBPSPlugin;
import qc.maxx.bbps.session.BlockSession;
import qc.maxx.bbps.session.PlayerSession;

public class Util {
	private static List<PlayerSession> playerSessions = new ArrayList<PlayerSession>();
	
	public static String colorize(String string) {
		if (string == null) {
			return null;
		}
		return string.replaceAll("&([0-9a-z])", "�$1");
	}
	
	public static List<PlayerSession> getPlayerSessions() {
		return playerSessions;
	}
	
	public static void addPlayerSession(PlayerSession playerSession) {
		playerSessions.add(playerSession);
	}

	public static void removePlayerSession(PlayerSession playerSession) {
		playerSessions.remove(playerSession);
	}

	public static PlayerSession getPlayerSessionByUUID(String playerUIID) {
		for (PlayerSession playerSession : playerSessions)
			if (playerSession.getPlayerUUID().equals(playerUIID))
				return playerSession;

		return null;
	}
	
	public static Location correctLocation(Location loc) {
		return new Location(loc.getWorld(), loc.getBlockX(), loc.getBlockY(), loc.getBlockZ());
	}

	@SuppressWarnings("deprecation")
	public static void replaceBlocksFromPlayer(BBPSPlugin plugin, PlayerSession player) {
		for (BlockSession block : player.getChangedBlocks()) {
			if (block.getLine1() != null) {
				Bukkit.getWorld(block.getLocation().getWorld().getName()).getBlockAt(block.getLocation()).setTypeIdAndData(block.getMaterialId(), block.getData(), false);
				Sign sign = (Sign) Bukkit.getWorld(block.getLocation().getWorld().getName()).getBlockAt(block.getLocation()).getState();
				sign.setLine(0, block.getLine1());
				sign.setLine(1, block.getLine2());
				sign.setLine(2, block.getLine3());
				sign.setLine(3, block.getLine4());
				sign.update();
			} else
				Bukkit.getWorld(block.getLocation().getWorld().getName()).getBlockAt(block.getLocation()).setTypeIdAndData(block.getMaterialId(), block.getData(), false);
		}

		for (BlockSession block : player.getPlacedBlocks()) {
			if (block.getLine1() != null) {
				Bukkit.getWorld(block.getLocation().getWorld().getName()).getBlockAt(block.getLocation()).setTypeIdAndData(block.getMaterialId(), block.getData(), false);
				Sign sign = (Sign) Bukkit.getWorld(block.getLocation().getWorld().getName()).getBlockAt(block.getLocation()).getState();
				sign.setLine(0, block.getLine1());
				sign.setLine(1, block.getLine2());
				sign.setLine(2, block.getLine3());
				sign.setLine(3, block.getLine4());
				sign.update();
			} else
				Bukkit.getWorld(block.getLocation().getWorld().getName()).getBlockAt(block.getLocation()).setTypeIdAndData(block.getMaterialId(), block.getData(), false);
		}

		player.getChangedBlocks().clear();
		player.getPlacedBlocks().clear();
	}
}