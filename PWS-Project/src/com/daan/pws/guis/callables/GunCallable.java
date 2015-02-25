package com.daan.pws.guis.callables;

import java.util.concurrent.Callable;

import com.daan.pws.match.CompetitivePlayer;
import com.daan.pws.weapon.Gun;
import com.daan.pws.weapon.WeaponManager;

public class GunCallable implements Callable<Boolean> {

	private Gun gun;
	private CompetitivePlayer player;

	public GunCallable(String gunName, CompetitivePlayer player) {
		this(WeaponManager.getGun(gunName), player);
	}

	public GunCallable(Gun gun, CompetitivePlayer player) {
		this.gun = gun;
		this.player = player;
	}

	@Override
	public Boolean call() throws Exception {
		if (gun != null && player != null) {
			if (player.getLoadout().getPrimary() != null && player.getLoadout().getPrimary().getGun().equals(gun))
				return false;
			if (player.getLoadout().getSecondary() != null && player.getLoadout().getSecondary().getGun().equals(gun))
				return false;
			return player.getMoney() >= gun.getPrice();
		}
		return false;
	}

}
