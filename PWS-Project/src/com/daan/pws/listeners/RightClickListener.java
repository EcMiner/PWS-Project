package com.daan.pws.listeners;

import org.bukkit.inventory.ItemStack;
import org.getspout.spoutapi.SpoutManager;
import org.getspout.spoutapi.event.input.KeyBindingEvent;
import org.getspout.spoutapi.keyboard.BindingExecutionDelegate;
import org.getspout.spoutapi.keyboard.Keyboard;
import org.getspout.spoutapi.player.SpoutPlayer;

import com.daan.pws.Main;
import com.daan.pws.utilities.PlayerUtil;
import com.daan.pws.weapon.Gun;
import com.daan.pws.weapon.WeaponManager;

public class RightClickListener implements BindingExecutionDelegate {

	public RightClickListener() {
		SpoutManager.getKeyBindingManager().registerBinding("Gun Zoom", Keyboard.MOUSE_RIGHT, "This will toggle zoom if the gun you're holding allows that.", this, Main.getInstance());
	}

	@Override
	public void keyPressed(KeyBindingEvent evt) {
		SpoutPlayer player = evt.getPlayer();
		ItemStack is = player.getItemInHand();
		if (WeaponManager.isGun(is)) {
			Gun gun = WeaponManager.getGun(is);
			if (gun.isZoomable()) {
				if (!PlayerUtil.isZoomedIn(player))
					PlayerUtil.zoomIn(player, 8, gun.getZoomUrl());
				else
					PlayerUtil.zoomOut(player);
			}
		}
	}

	@Override
	public void keyReleased(KeyBindingEvent evt) {
	}

}
