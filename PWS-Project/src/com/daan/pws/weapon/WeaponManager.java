package com.daan.pws.weapon;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.bukkit.inventory.ItemStack;

import com.daan.pws.guns.AK47;
import com.daan.pws.guns.AUG;
import com.daan.pws.guns.AWP;
import com.daan.pws.guns.Glock18;
import com.daan.pws.guns.P2000;
import com.daan.pws.guns.P90;
import com.daan.pws.guns.SSG08;

public class WeaponManager {

	private static final Map<String, Gun> guns = new HashMap<String, Gun>();

	public WeaponManager() {
		loadGuns();
	}

	private void loadGuns() {
		_(new AK47());
		_(new Glock18());
		_(new P90());
		_(new P2000());
		_(new AWP());
		_(new AUG());
		_(new SSG08());
	}

	private void _(Gun gun) {
		guns.put(gun.getName().toLowerCase().replace(" ", "").replace("-", ""), gun);
	}

	public static Collection<Gun> getAllGuns() {
		return guns.values();
	}

	public static Collection<Entry<String, Gun>> getGunEntry() {
		return guns.entrySet();
	}

	public static boolean isGun(String gunName) {
		return guns.containsKey(gunName.toLowerCase().replace(" ", "").replace("-", ""));
	}

	public static boolean isGun(ItemStack item) {
		return item != null && item.hasItemMeta() && item.getItemMeta().getDisplayName() != null && isGun(item.getItemMeta().getDisplayName());
	}

	public static Gun getGun(String gunName) {
		return guns.get(gunName.toLowerCase().replace(" ", "").replace("-", ""));
	}

	public static Gun getGun(ItemStack item) {
		return item != null && item.hasItemMeta() && item.getItemMeta().getDisplayName() != null ? getGun(item.getItemMeta().getDisplayName()) : null;
	}

}