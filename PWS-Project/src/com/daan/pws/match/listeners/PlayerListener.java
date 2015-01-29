package com.daan.pws.match.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ItemDespawnEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.inventory.ItemStack;
import org.getspout.spoutapi.player.SpoutPlayer;
import org.kitteh.tag.PlayerReceiveNameTagEvent;

import com.daan.pws.match.Competitive;
import com.daan.pws.match.CompetitiveGun;
import com.daan.pws.match.CompetitivePlayer;

public class PlayerListener implements Listener {

	@EventHandler
	public void onNameTagReceive(PlayerReceiveNameTagEvent evt) {
		Player r = evt.getPlayer();
		Player s = evt.getNamedPlayer();
		if (s instanceof SpoutPlayer) {
			SpoutPlayer sender = (SpoutPlayer) s;
			if (Competitive.isInMatch(sender)) {
				if (r instanceof SpoutPlayer) {
					SpoutPlayer receiver = (SpoutPlayer) r;
					if (Competitive.isInMatch(receiver)) {
						if (Competitive.getMatch(receiver).equals(Competitive.getMatch(sender))) {
							if (Competitive.getMatch(receiver).getTeam(receiver) == Competitive.getMatch(sender).getTeam(sender)) {
								return;
							}
						}
					}
					sender.hideTitleFrom(receiver);
				}
			}
		}
	}

	@EventHandler
	public void onPlayerMove(PlayerMoveEvent e) {
		if (e.getFrom().getBlockX() != e.getTo().getBlockX() || e.getFrom().getBlockY() != e.getTo().getBlockY() || e.getFrom().getBlockZ() != e.getTo().getBlockZ()) {

		}
	}

	@EventHandler(priority = EventPriority.MONITOR)
	public void onPlayerPickupItem(PlayerPickupItemEvent evt) {
		Player p = evt.getPlayer();
		if (Competitive.isInMatch(p)) {
			evt.setCancelled(true);
			ItemStack item = evt.getItem().getItemStack();
			if (CompetitiveGun.isCompetitiveGun(item)) {
				CompetitivePlayer player = Competitive.getMatch(p).getCompetitivePlayer((SpoutPlayer) p);
				CompetitiveGun gun = CompetitiveGun.getCompetitiveGun(item);
				if (gun.getGun().getWeaponType().isPrimary() && !player.getLoadout().hasPrimary()) {
					gun.pickup(player);
					evt.getItem().remove();
					return;
				} else if (gun.getGun().getWeaponType().isSecondary() && !player.getLoadout().hasSecondary()) {
					gun.pickup(player);
					evt.getItem().remove();
					return;
				}
			}
		}
	}

	@EventHandler
	public void onInventoryClick(InventoryClickEvent evt) {
		if (evt.getWhoClicked() instanceof SpoutPlayer) {
			SpoutPlayer player = (SpoutPlayer) evt.getWhoClicked();
			if (Competitive.isInMatch(player)) {
				evt.setCancelled(true);
			}
		}
	}

	@EventHandler
	public void onPlayerDropItem(PlayerDropItemEvent evt) {
		if (Competitive.isInMatch(evt.getPlayer())) {
			Player p = evt.getPlayer();
			p.getInventory().setItem(p.getInventory().getHeldItemSlot(), evt.getItemDrop().getItemStack());
			evt.getItemDrop().remove();
		}
	}

	@EventHandler(priority = EventPriority.MONITOR)
	public void onItemDespawnEvent(ItemDespawnEvent evt) {
		ItemStack stack = evt.getEntity().getItemStack();
		if (CompetitiveGun.isCompetitiveGun(stack)) {
			evt.setCancelled(true);
		}
	}

}
