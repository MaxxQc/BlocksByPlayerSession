package qc.maxx.bbps.event;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.BlockState;
import org.bukkit.block.Sign;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.material.Door;

import qc.maxx.bbps.main.BBPSPlugin;
import qc.maxx.bbps.session.BlockSession;
import qc.maxx.bbps.session.PlayerSession;
import qc.maxx.bbps.util.ConfigHandler;
import qc.maxx.bbps.util.Util;

public class BlockEventsLower implements Listener {
	private BBPSPlugin plugin;

	public BlockEventsLower(BBPSPlugin plugin) {
		this.plugin = plugin;
		Bukkit.getPluginManager().registerEvents(this, plugin);
	}

	@SuppressWarnings("deprecation")
	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent e) {
		if (e.getPlayer().hasPermission("bbps.bypass.overall"))
			return;

		if (e.getAction() == Action.PHYSICAL) {
			PlayerSession p = plugin.getPlayerSessionByUUID(e.getPlayer().getUniqueId().toString());

			if (ConfigHandler.blocksChangeLimit > 0) {
				if (p.getNumberOfChangedBlocks() >= ConfigHandler.blocksChangeLimit && !e.getPlayer().hasPermission("bbps.bypass.changedblocks")) {
					e.setCancelled(true);
					e.getPlayer().sendMessage(Util.colorize("&cVous atteint votre limite de bloc à briser pour cette session!"));
					return;
				}

				if (p != null && !p.getChangedBlocks().contains(Util.correctLocation(e.getClickedBlock().getLocation())))
					p.addChangedBlock(new BlockSession(e.getClickedBlock().getLocation(), e.getClickedBlock().getType(), e.getClickedBlock().getData()));

				p.addOneToNumberOfChangedBlocks();
			} else {
				if (p != null && !p.getChangedBlocks().contains(Util.correctLocation(e.getClickedBlock().getLocation())))
					p.addChangedBlock(new BlockSession(e.getClickedBlock().getLocation(), e.getClickedBlock().getType(), e.getClickedBlock().getData()));
			}
		}
	}

	@SuppressWarnings("deprecation")
	@EventHandler
	public void onPlayerBreakBlock(BlockBreakEvent e) {
		if (e.getPlayer().hasPermission("bbps.bypass.overall"))
			return;

		PlayerSession p = plugin.getPlayerSessionByUUID(e.getPlayer().getUniqueId().toString());

		if (ConfigHandler.blocksChangeLimit > 0) {
			if (p.getNumberOfChangedBlocks() >= ConfigHandler.blocksChangeLimit && !e.getPlayer().hasPermission("bbps.bypass.changedblocks")) {
				e.setCancelled(true);
				e.getPlayer().sendMessage(Util.colorize("&cVous atteint votre limite de bloc à briser pour cette session!"));
				return;
			}

			if (p != null && !p.getChangedBlocks().contains(Util.correctLocation(e.getBlock().getLocation()))) {
				if (e.getBlock().getType() == Material.SIGN_POST || e.getBlock().getType() == Material.WALL_SIGN) {
					Sign sign = (Sign) e.getBlock().getState();
					p.addChangedBlock(new BlockSession(e.getBlock().getLocation(), e.getBlock().getType(), e.getBlock().getData(), sign.getLines()));
				} else if (e.getBlock().getType() == Material.WOODEN_DOOR || e.getBlock().getType() == Material.IRON_DOOR_BLOCK) {
					Door door = (Door) e.getBlock().getState().getData();
					Block upBlock = e.getBlock().getRelative(BlockFace.UP);
					Block downBlock = e.getBlock().getRelative(BlockFace.DOWN);
					if (door.isTopHalf())
						p.addChangedBlocks(new BlockSession(e.getBlock().getLocation(), e.getBlock().getType(), e.getBlock().getData()),
								new BlockSession(downBlock.getLocation(), downBlock.getType(), downBlock.getData()));
					else
						p.addChangedBlocks(new BlockSession(e.getBlock().getLocation(), e.getBlock().getType(), e.getBlock().getData()),
								new BlockSession(upBlock.getLocation(), upBlock.getType(), upBlock.getData()));
				} else
					p.addChangedBlock(new BlockSession(e.getBlock().getLocation(), e.getBlock().getType(), e.getBlock().getData()));
			}

			p.addOneToNumberOfChangedBlocks();
		} else {
			if (p != null && !p.getChangedBlocks().contains(Util.correctLocation(e.getBlock().getLocation()))) {
				if (e.getBlock().getType() == Material.SIGN_POST || e.getBlock().getType() == Material.WALL_SIGN) {
					Sign sign = (Sign) e.getBlock().getState();
					p.addChangedBlock(new BlockSession(e.getBlock().getLocation(), e.getBlock().getType(), e.getBlock().getData(), sign.getLines()));
				} else if (e.getBlock().getType() == Material.WOODEN_DOOR || e.getBlock().getType() == Material.IRON_DOOR_BLOCK) {
					Door door = (Door) e.getBlock().getState().getData();
					Block upBlock = e.getBlock().getRelative(BlockFace.UP);
					Block downBlock = e.getBlock().getRelative(BlockFace.DOWN);
					if (door.isTopHalf())
						p.addChangedBlocks(new BlockSession(e.getBlock().getLocation(), e.getBlock().getType(), e.getBlock().getData()),
								new BlockSession(downBlock.getLocation(), downBlock.getType(), downBlock.getData()));
					else
						p.addChangedBlocks(new BlockSession(e.getBlock().getLocation(), e.getBlock().getType(), e.getBlock().getData()),
								new BlockSession(upBlock.getLocation(), upBlock.getType(), upBlock.getData()));
				} else
					p.addChangedBlock(new BlockSession(e.getBlock().getLocation(), e.getBlock().getType(), e.getBlock().getData()));
			}
		}
	}

	@SuppressWarnings("deprecation")
	@EventHandler
	public void onPlayerPlaceBlock(BlockPlaceEvent e) {
		if (e.getPlayer().hasPermission("bbps.bypass.overall"))
			return;

		BlockState b = e.getBlockReplacedState();
		PlayerSession p = plugin.getPlayerSessionByUUID(e.getPlayer().getUniqueId().toString());

		if (ConfigHandler.blocksPlaceLimit > 0) {
			if (p.getNumberOfPlacedBlocks() >= ConfigHandler.blocksPlaceLimit && !e.getPlayer().hasPermission("bbps.bypass.placedblocks")) {
				e.setCancelled(true);
				e.getPlayer().sendMessage(Util.colorize("&cVous atteint votre limite de bloc à placer pour cette session!"));
				return;
			}

			if (p != null && !p.getPlacedBlocks().contains(Util.correctLocation(b.getLocation()))) {
				if (b.getType() == Material.SIGN_POST || b.getType() == Material.WALL_SIGN) {
					Sign sign = (Sign) b;
					p.addPlacedBlock(new BlockSession(b.getLocation(), b.getType(), b.getRawData(), sign.getLines()));
				} else if (e.getBlock().getType() == Material.WOODEN_DOOR || e.getBlock().getType() == Material.IRON_DOOR_BLOCK) {
					Door door = (Door) e.getBlock().getState().getData();
					if (door.isTopHalf())
						p.addPlacedBlocks(new BlockSession(b.getLocation(), Material.AIR), new BlockSession(b.getLocation().subtract(0, 1, 0), Material.AIR));
					else
						p.addPlacedBlocks(new BlockSession(b.getLocation(), Material.AIR), new BlockSession(b.getLocation().add(0, 1, 0), Material.AIR));
				} else
					p.addPlacedBlock(new BlockSession(b.getLocation(), b.getType(), b.getRawData()));
			}

			p.addOneToNumberOfPlacedBlocks();
		} else {
			if (p != null && !p.getPlacedBlocks().contains(Util.correctLocation(b.getLocation()))) {
				if (b.getType() == Material.SIGN_POST || b.getType() == Material.WALL_SIGN) {
					Sign sign = (Sign) b;
					p.addPlacedBlock(new BlockSession(b.getLocation(), b.getType(), b.getRawData(), sign.getLines()));
				} else if (e.getBlock().getType() == Material.WOODEN_DOOR || e.getBlock().getType() == Material.IRON_DOOR_BLOCK) {
					Door door = (Door) e.getBlock().getState().getData();
					if (door.isTopHalf())
						p.addPlacedBlocks(new BlockSession(b.getLocation(), Material.AIR), new BlockSession(b.getLocation().subtract(0, 1, 0), Material.AIR));
					else
						p.addPlacedBlocks(new BlockSession(b.getLocation(), Material.AIR), new BlockSession(b.getLocation().add(0, 1, 0), Material.AIR));
				} else
					p.addPlacedBlock(new BlockSession(b.getLocation(), b.getType(), b.getRawData()));
			}
		}
	}
}