package com.daan.pws.listeners;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public class SelectionListener implements Listener {

	public static Map<String, Location> loc1 = new HashMap<String, Location>();
	public static Map<String, Location> loc2 = new HashMap<String, Location>();

	@EventHandler
	public void onSelect(PlayerInteractEvent e) {
		Player p = e.getPlayer();
		if (p.getItemInHand() != null && p.getItemInHand().getType() == Material.GOLD_HOE) {
			if (e.getAction() == Action.RIGHT_CLICK_BLOCK) {
				e.setCancelled(true);
				Location loc = e.getClickedBlock().getLocation();
				loc2.put(p.getName(), loc);
				p.sendMessage(ChatColor.GREEN + "Selected second location (" + loc.getBlockX() + ", " + loc.getBlockY() + ", " + loc.getBlockZ() + ")");
			} else if (e.getAction() == Action.LEFT_CLICK_BLOCK) {
				e.setCancelled(true);
				Location loc = e.getClickedBlock().getLocation();
				loc1.put(p.getName(), loc);
				p.sendMessage(ChatColor.GREEN + "Selected first location (" + loc.getBlockX() + ", " + loc.getBlockY() + ", " + loc.getBlockZ() + ")");
			}
		}
	}

}
