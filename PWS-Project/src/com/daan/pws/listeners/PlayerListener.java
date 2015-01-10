package com.daan.pws.listeners;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.getspout.spoutapi.SpoutManager;
import org.getspout.spoutapi.player.SpoutPlayer;

import com.daan.pws.Main;
import com.daan.pws.entities.GrenadeEntity;
import com.daan.pws.grenades.Molotov;
import com.daan.pws.hud.DeathsHud;
import com.daan.pws.hud.GunHud;
import com.daan.pws.hud.HealthHud;
import com.daan.pws.hud.MoneyHud;
import com.daan.pws.match.hud.GameHud;
import com.daan.pws.utilities.PlayerUtil;
import com.daan.pws.weapon.Gun;
import com.daan.pws.weapon.WeaponManager;

public class PlayerListener implements Listener {

	private Main plugin;

	public PlayerListener(Main plugin) {
		this.plugin = plugin;
	}

	@EventHandler(priority = EventPriority.MONITOR)
	public void onPlayerInteract(final PlayerInteractEvent e) {
		Player p = e.getPlayer();
		if (e.getItem() != null && e.getItem().getType() == Material.BLAZE_ROD) {
			e.setCancelled(true);
			Molotov bang = new Molotov();
			new GrenadeEntity(bang, (SpoutPlayer) p).setVelocity(p.getEyeLocation().getDirection());
		}
		if (e.getAction() == Action.LEFT_CLICK_BLOCK) {
			if (e.getClickedBlock().getRelative(BlockFace.UP).getType() == Material.FIRE) {
				Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getInstance(), new Runnable() {

					@Override
					public void run() {
						e.getClickedBlock().getRelative(BlockFace.UP).setType(Material.FIRE);
					}

				}, 1l);
			}
		}
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
			hud.addDeath(player, player, WeaponManager.getGun("Glock-18"), true);
			hud.addDeath(player, player, WeaponManager.getGun("AK-47"), false);
			hud.addDeath(player, player, WeaponManager.getGun("AK-47"), true);
			hud.addDeath(player, player, WeaponManager.getGun("Glock-18"), false);

			new BukkitRunnable() {

				@Override
				public void run() {
					hud.addDeath(player, player, WeaponManager.getGun("Glock-18"), true);
					hud.addDeath(player, player, WeaponManager.getGun("AK-47"), false);
					hud.addDeath(player, player, WeaponManager.getGun("P90"), true);
					hud.addDeath(player, player, WeaponManager.getGun("Glock-18"), false);
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
		if (WeaponManager.isGun(p.getInventory().getItem(e.getNewSlot()))) {
			PlayerUtil.addPotionEffectNoParticles(p, 3, 1029389, 100);
			if ((player = SpoutManager.getPlayer(p)) != null) {
				GunHud.updateBulletsOnScreen(player, WeaponManager.getGun(p.getInventory().getItem(e.getNewSlot())));
			}
			Gun gun = WeaponManager.getGun(p.getInventory().getItem(e.getNewSlot()));
			p.setWalkSpeed((gun.getMovementSpeed() * 0.3F) / 250);
		} else {
			PlayerUtil.removePotionEffectSafe(p, 3, PotionEffectType.FAST_DIGGING);
			if ((player = SpoutManager.getPlayer(p)) != null) {
				GunHud.removeBulletsOnScreen(player);
			}
			player.setWalkSpeed(0.3F);
			if (PlayerUtil.isZoomedIn(player)) {
				PlayerUtil.zoomOut(player);
			}
		}
	}

}
