package com.daan.pws.weapon;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.bukkit.inventory.ItemStack;
import org.getspout.spoutapi.player.SpoutPlayer;

import com.daan.pws.guns.AK47;
import com.daan.pws.guns.Glock18;
import com.daan.pws.guns.P2000;
import com.daan.pws.guns.P90;
import com.daan.pws.hud.GunHud;

public class GunManager {

	private static final Map<String, Gun> guns = new HashMap<String, Gun>();

	private static Map<String, Map<Gun, Integer>> magazine = new HashMap<String, Map<Gun, Integer>>();
	private static Map<String, Map<Gun, Integer>> reserve = new HashMap<String, Map<Gun, Integer>>();

	public static int getMagazine(SpoutPlayer player, Gun gun) {
		if (magazine.containsKey(player.getName()) && magazine.get(player.getName()).containsKey(gun)) {
			return magazine.get(player.getName()).get(gun);
		}
		updateMagazine(player, gun, gun.getBulletsInRound());
		return gun.getBulletsInRound();
	}

	public static int getReserve(SpoutPlayer player, Gun gun) {
		if (reserve.containsKey(player.getName()) && reserve.get(player.getName()).containsKey(gun)) {
			return reserve.get(player.getName()).get(gun);
		}
		updateReserve(player, gun, gun.getTotalAmmo());
		return gun.getTotalAmmo() - gun.getBulletsInRound();
	}

	public static void shootBullet(SpoutPlayer player, Gun gun) {
		updateMagazine(player, gun, getMagazine(player, gun) - 1);
		GunHud.updateBulletsOnScreen(player, gun);
	}

	public static void reload(SpoutPlayer player, Gun gun) {
		int reserve = getReserve(player, gun);
		int toReload = gun.getBulletsInRound() - getMagazine(player, gun);
		updateReserve(player, gun, reserve >= toReload ? reserve - toReload : 0);
		updateMagazine(player, gun, reserve >= toReload ? gun.getBulletsInRound() : getMagazine(player, gun) + reserve);
		GunHud.updateBulletsOnScreen(player, gun);
	}

	private static void updateMagazine(SpoutPlayer player, Gun gun, int bullets) {
		if (!magazine.containsKey(player.getName())) {
			magazine.put(player.getName(), new HashMap<Gun, Integer>());
		}
		magazine.get(player.getName()).put(gun, bullets);
	}

	private static void updateReserve(SpoutPlayer player, Gun gun, int bullets) {
		if (!reserve.containsKey(player.getName())) {
			reserve.put(player.getName(), new HashMap<Gun, Integer>());
		}
		reserve.get(player.getName()).put(gun, bullets);
	}

	public GunManager() {
		loadGuns();
	}

	private void loadGuns() {
		_(new AK47());
		_(new Glock18());
		_(new P90());
		_(new P2000());
	}

	private void _(Gun gun) {
		guns.put(gun.getName().toLowerCase().replace(" ", ""), gun);
	}

	public static Collection<Gun> getAllGuns() {
		return guns.values();
	}

	public static Collection<Entry<String, Gun>> getGunEntry() {
		return guns.entrySet();
	}

	public static boolean isGun(String gunName) {
		return guns.containsKey(gunName.toLowerCase().replace(" ", ""));
	}

	public static boolean isGun(ItemStack item) {
		return item != null && item.hasItemMeta() && item.getItemMeta().getDisplayName() != null && isGun(item.getItemMeta().getDisplayName());
	}

	public static Gun getGun(String gunName) {
		return guns.get(gunName.toLowerCase().replace(" ", ""));
	}

	public static Gun getGun(ItemStack item) {
		return item != null && item.hasItemMeta() && item.getItemMeta().getDisplayName() != null ? getGun(item.getItemMeta().getDisplayName()) : null;
	}

}