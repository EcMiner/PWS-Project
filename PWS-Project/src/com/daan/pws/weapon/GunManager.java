package com.daan.pws.weapon;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.bukkit.inventory.ItemStack;

import com.daan.pws.guns.*;

public class GunManager {

	private static final Map<String, Gun> guns = new HashMap<String, Gun>();

	public GunManager() {
		loadGuns();
	}

	private void loadGuns() {
		_(new AK47());
	}

	private void _(Gun gun) {
		guns.put(gun.getName().toLowerCase(), gun);
	}

	public static Collection<Gun> getAllGuns() {
		return guns.values();
	}

	public static Collection<Entry<String, Gun>> getGunEntry() {
		return guns.entrySet();
	}

	public static boolean isGun(String gunName) {
		return guns.containsKey(gunName.toLowerCase());
	}

	public static boolean isGun(ItemStack item) {
		// TODO Check if itemstack is gun.
		return false;
	}

	public static Gun getGun(String gunName) {
		return guns.get(gunName.toLowerCase());
	}

	public static Gun getGun(ItemStack item) {
		// TODO Get gun from itemstack.
		return null;
	}

}