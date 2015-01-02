package com.daan.pws.listeners;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.getspout.spoutapi.SpoutManager;
import org.getspout.spoutapi.player.SpoutPlayer;

import com.daan.pws.Main;
import com.daan.pws.hud.DeathsHud;
import com.daan.pws.hud.GunHud;
import com.daan.pws.hud.HealthHud;
import com.daan.pws.hud.MoneyHud;
import com.daan.pws.match.hud.GameHud;
import com.daan.pws.weapon.Gun;
import com.daan.pws.weapon.GunManager;

public class PlayerListener implements Listener {

	private Main plugin;

	public PlayerListener(Main plugin) {
		this.plugin = plugin;
	}

	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent e) {
		e.getPlayer().setMaxHealth(100d);
		e.getPlayer().setHealth(100d);

		final SpoutPlayer player;
		if ((player = SpoutManager.getPlayer(e.getPlayer())) != null) {
			HealthHud.updateHealth(player, 100);
			MoneyHud.updateMoney(player, 800);

			GameHud.updateGameHud(player, 114, 7, 6, 10);
			Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {

				@Override
				public void run() {
					GameHud.updateGameHud(player, 1000, 25, 16, 9);
				}
				
			}, 100);
			
			final DeathsHud hud = new DeathsHud(player);
			hud.addDeath(player, player, GunManager.getGun("Glock-18"), true);
			hud.addDeath(player, player, GunManager.getGun("AK-47"), false);
			hud.addDeath(player, player, GunManager.getGun("AK-47"), true);
			hud.addDeath(player, player, GunManager.getGun("Glock-18"), false);

			new BukkitRunnable() {

				@Override
				public void run() {
					hud.addDeath(player, player, GunManager.getGun("Glock-18"), true);
					hud.addDeath(player, player, GunManager.getGun("AK-47"), false);
					hud.addDeath(player, player, GunManager.getGun("P90"), true);
					hud.addDeath(player, player, GunManager.getGun("Glock-18"), false);
				}

			}.runTaskLater(plugin, 20 * 30);

			player.getMainScreen().getArmorBar().setVisible(false);
			player.getMainScreen().getHealthBar().setVisible(false);
			player.getMainScreen().getHungerBar().setVisible(false);
			player.getMainScreen().getExpBar().setVisible(false);
			player.getMainScreen().getBubbleBar().setVisible(false);
			player.getMainScreen().getChatTextBox().setVisible(false);
			player.getMainScreen().getChatBar().setVisible(false);
		}
	}

	@EventHandler
	public void onHeldItemSlot(PlayerItemHeldEvent e) {
		final Player p = e.getPlayer();
		SpoutPlayer player = null;
		if (GunManager.isGun(p.getInventory().getItem(e.getNewSlot()))) {
			p.addPotionEffect(new PotionEffect(PotionEffectType.FAST_DIGGING, 1029389, 100));
			if ((player = SpoutManager.getPlayer(p)) != null) {
				GunHud.updateBulletsOnScreen(player, GunManager.getGun(p.getInventory().getItem(e.getNewSlot())));
			}
			Gun gun = GunManager.getGun(p.getInventory().getItem(e.getNewSlot()));
			p.setWalkSpeed((gun.getMovementSpeed() * 0.3F) / 250);
		} else {
			if (p.hasPotionEffect(PotionEffectType.FAST_DIGGING)) {
				p.removePotionEffect(PotionEffectType.FAST_DIGGING);
			}
			if ((player = SpoutManager.getPlayer(p)) != null) {
				GunHud.removeBulletsOnScreen(player);
			}
			player.setWalkSpeed(0.3F);
		}
	}

}
