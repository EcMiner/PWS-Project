package com.daan.pws.listeners;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;
import org.getspout.spoutapi.SpoutManager;
import org.getspout.spoutapi.event.input.KeyBindingEvent;
import org.getspout.spoutapi.keyboard.BindingExecutionDelegate;
import org.getspout.spoutapi.keyboard.Keyboard;
import org.getspout.spoutapi.player.SpoutPlayer;

import com.daan.pws.Main;
import com.daan.pws.match.Competitive;
import com.daan.pws.match.CompetitiveGun;
import com.daan.pws.match.CompetitivePlayer;
import com.daan.pws.match.enums.TeamEnum;
import com.daan.pws.runnables.BombDefuseTimer;

public class UseButtonListener implements BindingExecutionDelegate {

	public UseButtonListener() {
		SpoutManager.getKeyBindingManager().registerBinding("Use/interact", Keyboard.KEY_F, "This will perform an action if possible", this, Main.getInstance());
	}

	@SuppressWarnings("deprecation")
	@Override
	public void keyPressed(KeyBindingEvent evt) {
		SpoutPlayer player = evt.getPlayer();
		if (Competitive.isInMatch(player)) {
			Competitive comp = Competitive.getMatch(player);
			CompetitivePlayer cPlayer = comp.getCompetitivePlayer(player);

			if (cPlayer.getTeam() == TeamEnum.COUNTER_TERRORISTS) {
				Block insight = player.getTargetBlock(null, 4);
				if (insight != null && insight.getType() == Material.DAYLIGHT_DETECTOR) {
					if (comp.getBombBlock() != null && (comp.getBombBlock().getX() == insight.getX() && comp.getBombBlock().getY() == insight.getY() && comp.getBombBlock().getZ() == insight.getZ())) {
						new BombDefuseTimer(cPlayer);
					}
				}
			}

			Location loc = player.getLocation().clone();
			Vector v = player.getEyeLocation().getDirection();
			for (int i = 0; i < 4; i++) {
				for (Entity e : loc.getChunk().getEntities()) {
					if (e instanceof Item && loc.distanceSquared(e.getLocation()) <= 4) {
						Item item = (Item) e;
						ItemStack stack = item.getItemStack();
						if (CompetitiveGun.isCompetitiveGun(stack)) {
							CompetitiveGun gun = CompetitiveGun.getCompetitiveGun(stack);
							if (gun.getGun().getWeaponType().isPrimary()) {
								if (cPlayer.getLoadout().hasPrimary()) {
									cPlayer.getLoadout().getPrimary().drop(cPlayer);
								}
								gun.pickup(cPlayer);
								item.remove();
								return;
							} else if (gun.getGun().getWeaponType().isSecondary()) {
								if (cPlayer.getLoadout().hasSecondary()) {
									cPlayer.getLoadout().getSecondary().drop(cPlayer);
								}
								gun.pickup(cPlayer);
								item.remove();
								return;
							}
						}
					}
				}
				loc.add(v);
			}
		}
	}

	@Override
	public void keyReleased(KeyBindingEvent evt) {
		SpoutPlayer player = evt.getPlayer();
		if (BombDefuseTimer.map.containsKey(player.getName())) {
			BombDefuseTimer.map.get(player.getName()).cancel();
		}
	}

}
