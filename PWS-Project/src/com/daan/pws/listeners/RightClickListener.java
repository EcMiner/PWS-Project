package com.daan.pws.listeners;

import java.util.HashMap;
import java.util.Map;

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

	public static Map<String, Integer> zoomedInTimes = new HashMap<String, Integer>();

	public RightClickListener() {
		SpoutManager.getKeyBindingManager().registerBinding("Gun Zoom", Keyboard.MOUSE_RIGHT, "This will toggle zoom if the gun you're holding allows that.", this, Main.getInstance());
	}

	@Override
	public void keyPressed(KeyBindingEvent evt) {
		SpoutPlayer player = evt.getPlayer();
		ItemStack is = player.getItemInHand();
		if (WeaponManager.isGun(is)) {
			Gun gun = WeaponManager.getGun(is);
			if (gun.isZoomable() && !gun.isReloading(player)) {
				if (!PlayerUtil.isZoomedIn(player)) {
					PlayerUtil.zoomIn(player, gun.getZoomFactors()[0], gun.getZoomUrl());
					zoomedInTimes.put(player.getName(), 1);
				} else {
					int times = zoomedInTimes.containsKey(player.getName()) ? zoomedInTimes.get(player.getName()) : 1;
					if (times >= gun.getZoomFactors().length) {
						PlayerUtil.zoomOut(player);
					} else {
						PlayerUtil.zoomIn(player, gun.getZoomFactors()[times]);
						zoomedInTimes.put(player.getName(), times + 1);
					}
				}
			}
		}
	}

	@Override
	public void keyReleased(KeyBindingEvent evt) {
	}

}
