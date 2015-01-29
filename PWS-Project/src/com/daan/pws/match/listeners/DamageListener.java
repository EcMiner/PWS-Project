package com.daan.pws.match.listeners;

import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.getspout.spoutapi.player.SpoutPlayer;

import com.daan.pws.events.PlayerDiedEvent;
import com.daan.pws.events.PlayerShotEvent;
import com.daan.pws.match.Competitive;
import com.daan.pws.match.CompetitivePlayer;
import com.daan.pws.match.enums.TeamEnum;
import com.daan.pws.weapon.DamagePattern.PlayerHeight;
import com.daan.pws.weapon.Gun;

public class DamageListener implements Listener {

	@EventHandler
	public void onEntityDamageEvent(EntityDamageEvent e) {
		if (e.getEntity() instanceof SpoutPlayer) {
			SpoutPlayer player = (SpoutPlayer) e.getEntity();
			if (Competitive.isInMatch(player)) {
				if (e.getCause() != DamageCause.CUSTOM) {
					e.setCancelled(true);
				}
			}
		}
	}

	@EventHandler
	public void onPlayerDamage(PlayerShotEvent evt) {
		CompetitivePlayer damaged = evt.getDamaged();
		CompetitivePlayer shooter = evt.getShooter();
		Gun gun = evt.getGun();

		if (damaged.getMatch() == shooter.getMatch()) {
			if (evt.getHeight() == PlayerHeight.HEAD) {
				if (damaged.getLoadout().hasHelmet()) {
					int newDamage = (int) Math.round((double) (evt.getDamage() * (gun.getArmourPenetration() / 100)));
					evt.setDamage(newDamage);
				}
			} else if (evt.getHeight() == PlayerHeight.CHEST || evt.getHeight() == PlayerHeight.STOMACH) {
				if (damaged.getLoadout().hasKevlar()) {
					int newDamage = (int) Math.round((double) (evt.getDamage() * (gun.getArmourPenetration() / 100)));
					evt.setDamage(newDamage);
				}
			}

			if (damaged.getTeam() == shooter.getTeam()) {
				evt.setDamage(evt.getDamage() / 2);
			}
		} else {
			evt.setCancelled(true);
		}
	}

	@EventHandler
	public void onEntityDamage(EntityDamageEvent evt) {
		if (evt.getEntity() instanceof SpoutPlayer) {
			SpoutPlayer player = (SpoutPlayer) evt.getEntity();
			if (evt.getCause() == DamageCause.FIRE) {
				if (player.getLocation().getBlock().getType() == Material.FIRE || player.getEyeLocation().getBlock().getType() == Material.FIRE || player.getLocation().getBlock().getRelative(BlockFace.DOWN).getType() == Material.FIRE) {
					return;
				}
				player.setFireTicks(0);
				evt.setCancelled(true);
			} else if (evt.getCause() == DamageCause.FIRE_TICK) {
				if (player.getLocation().getBlock().getType() == Material.FIRE || player.getEyeLocation().getBlock().getType() == Material.FIRE || player.getLocation().getBlock().getRelative(BlockFace.DOWN).getType() == Material.FIRE) {
					evt.setCancelled(true);
					return;
				}
				player.setFireTicks(0);
				evt.setCancelled(true);
			}
		}
	}

	@EventHandler
	public void onPlayerDied(PlayerDiedEvent evt) {
		CompetitivePlayer player = evt.getPlayer();
		CompetitivePlayer killer = evt.getKiller();
		if (killer.getTeam() != player.getTeam()) {
			String[] lastDamagers = player.getLastDamagers();
			String killers = lastDamagers.length > 1 ? lastDamagers[0] + " + " + lastDamagers[1] : lastDamagers[0];
			player.getMatch().addDeath((killer.getTeam().equals(TeamEnum.TERRORISTS) ? "{t}" : "") + killers, player.getPlayer().getName(), killer.getInHand().getGun(), evt.isHeadshot());
		} else {
			player.getMatch().addDeath((killer.getTeam().equals(TeamEnum.TERRORISTS) ? "{t}" : "") + killer.getPlayer().getName(), player.getPlayer().getName(), killer.getInHand().getGun(), evt.isHeadshot());
		}
		player.clearDamage();

		Competitive comp = player.getMatch();
		if (!comp.getTimer().isPaused()) {
			if (comp.getCompetitiveTeam(player.getTeam()).getPlayersAlive() == 0) {
				comp.winRound(player.getTeam());
			}
		}
	}
}
