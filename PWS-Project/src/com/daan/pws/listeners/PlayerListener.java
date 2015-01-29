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
import org.getspout.spoutapi.SpoutManager;
import org.getspout.spoutapi.event.input.KeyPressedEvent;
import org.getspout.spoutapi.event.input.KeyReleasedEvent;
import org.getspout.spoutapi.keyboard.Keyboard;
import org.getspout.spoutapi.player.SpoutPlayer;

import com.daan.pws.Main;
import com.daan.pws.entities.GrenadeEntity;
import com.daan.pws.grenades.Flashbang;
import com.daan.pws.grenades.Incendiary;
import com.daan.pws.grenades.Molotov;
import com.daan.pws.grenades.Smoke;
import com.daan.pws.match.CompetitiveGun;
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
		} else if (e.getItem() != null && e.getItem().getType() == Material.BONE) {
			e.setCancelled(true);
			Incendiary bang = new Incendiary();
			new GrenadeEntity(bang, (SpoutPlayer) p).setVelocity(p.getEyeLocation().getDirection());
		} else if (e.getItem() != null && e.getItem().getType() == Material.WOOD_HOE) {
			e.setCancelled(true);
			Flashbang bang = new Flashbang();
			new GrenadeEntity(bang, (SpoutPlayer) p).setVelocity(p.getEyeLocation().getDirection());
		} else if (e.getItem() != null && e.getItem().getType() == Material.GOLD_HOE) {
			e.setCancelled(true);
			Smoke bang = new Smoke();
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

	@EventHandler(priority = EventPriority.LOWEST)
	public void onPlayerJoin(PlayerJoinEvent e) {
		e.getPlayer().getInventory().clear();
		e.getPlayer().setMaxHealth(100d);
		e.getPlayer().setHealth(100d);

		final SpoutPlayer player;
		if ((player = SpoutManager.getPlayer(e.getPlayer())) != null) {

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
				if (CompetitiveGun.isCompetitiveGun(p.getInventory().getItem(e.getNewSlot()))) {
					com.daan.pws.match.hud.GunHud.updateGunHud(player, CompetitiveGun.getCompetitiveGun(p.getInventory().getItem(e.getNewSlot())));
				}
			}
			Gun gun = WeaponManager.getGun(p.getInventory().getItem(e.getNewSlot()));
			p.setWalkSpeed((gun.getMovementSpeed() * 0.3F) / 250);
		} else {
			PlayerUtil.removePotionEffectSafe(p, 3, PotionEffectType.FAST_DIGGING);
			if ((player = SpoutManager.getPlayer(p)) != null) {
				com.daan.pws.match.hud.GunHud.removeGunHud(player);
			}
			player.setWalkSpeed(0.3F);
			if (PlayerUtil.isZoomedIn(player)) {
				PlayerUtil.zoomOut(player);
			}
		}
	}

	@EventHandler
	public void onKeyPressedEvent(KeyPressedEvent evt) {
		if (evt.getKey() == Keyboard.MOUSE_LEFT) {
			LeftClickListener.instance.keyPressed(evt);
		}
	}

	@EventHandler
	public void onKeyReleased(KeyReleasedEvent evt) {
		if (evt.getKey() == Keyboard.MOUSE_LEFT) {
			LeftClickListener.instance.keyReleased(evt);
		}
	}

}
