package com.daan.pws.listeners;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffectType;
import org.getspout.spoutapi.SpoutManager;
import org.getspout.spoutapi.event.input.KeyBindingEvent;
import org.getspout.spoutapi.keyboard.BindingExecutionDelegate;
import org.getspout.spoutapi.keyboard.Keyboard;
import org.getspout.spoutapi.player.SpoutPlayer;

import com.daan.pws.Main;
import com.daan.pws.hud.GunHud;
import com.daan.pws.weapon.GunManager;

public class WeaponDropListener implements BindingExecutionDelegate {

	public WeaponDropListener() {
		SpoutManager.getKeyBindingManager().registerBinding("Weapon Drop", Keyboard.KEY_G, "This will drop the weapon you're holding", this, Main.getInstance());
	}

	@Override
	public void keyPressed(KeyBindingEvent evt) {
	}

	@Override
	public void keyReleased(KeyBindingEvent evt) {
		SpoutPlayer player = evt.getPlayer();
		if (GunManager.isGun(player.getItemInHand())) {
			GunHud.removeBulletsOnScreen(player);
			player.removePotionEffect(PotionEffectType.FAST_DIGGING);
			player.getWorld().dropItem(player.getEyeLocation(), player.getItemInHand()).setVelocity(player.getEyeLocation().getDirection().multiply(.2));
			player.setItemInHand(new ItemStack(Material.AIR));
		}
	}

}
