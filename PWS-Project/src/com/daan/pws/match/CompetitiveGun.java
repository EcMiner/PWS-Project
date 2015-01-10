package com.daan.pws.match;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.inventory.ItemStack;
import org.getspout.spoutapi.inventory.SpoutItemStack;

import com.daan.pws.utilities.ItemUtil;
import com.daan.pws.weapon.Gun;

public class CompetitiveGun {

	private static final Map<ItemStack, CompetitiveGun> competitiveGuns = new HashMap<ItemStack, CompetitiveGun>();

	public static boolean isCompetitiveGun(ItemStack item) {
		return competitiveGuns.containsKey(item);
	}

	public static CompetitiveGun getCompetitiveGun(ItemStack item) {
		return competitiveGuns.get(item);
	}

	private final ItemStack gunItem;
	private final Gun gun;

	private int bulletsInMagazine;
	private int bulletsReserve;

	public CompetitiveGun(Gun gun) {
		this.gun = gun;
		this.gunItem = ItemUtil.setDisplayName(new SpoutItemStack(gun.getGunItem()), gun.getName());
		this.bulletsInMagazine = gun.getBulletsInRound();
		this.bulletsReserve = gun.getTotalAmmo() - gun.getBulletsInRound();

		competitiveGuns.put(gunItem, this);
	}

	public ItemStack getGunItem() {
		return gunItem;
	}

	public int getBulletsInMagazine() {
		return bulletsInMagazine;
	}

	public int getBulletsReserve() {
		return bulletsReserve;
	}

	public void shoot() {
		if (bulletsInMagazine > 0) {
			--bulletsInMagazine;
		}
	}

	public Gun getGun() {
		return gun;
	}

	public String getGunName() {
		return gun.getName();
	}

	public void reload() {
		int reserve = bulletsReserve;
		int toReload = gun.getBulletsInRound() - bulletsInMagazine;
		bulletsReserve = bulletsReserve >= toReload ? bulletsReserve - toReload : 0;
		bulletsInMagazine = reserve >= toReload ? gun.getBulletsInRound() : bulletsInMagazine + reserve;
	}
}
