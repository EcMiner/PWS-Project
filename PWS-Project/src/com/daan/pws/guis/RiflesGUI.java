package com.daan.pws.guis;

import com.daan.pws.guis.callables.GunCallable;
import com.daan.pws.guis.runnables.BuyGunRunnable;
import com.daan.pws.match.CompetitivePlayer;
import com.daan.pws.match.enums.TeamEnum;

public class RiflesGUI extends BuyGUI {

	public RiflesGUI(CompetitivePlayer player) {
		super("BUY RIFLES (PRIMARY WEAPON)", player, MainBuyGUI.class, "RIFLES");
		if (player.getTeam() == TeamEnum.TERRORISTS) {
			addButton("Galil AR", false, new BuyGunRunnable("galilar", player), new GunCallable("galilar", player));
			addButton("AK-47", false, new BuyGunRunnable("ak47", player), new GunCallable("ak47", player));
			addButton("SSG 08", false, new BuyGunRunnable("ssg08", player), new GunCallable("ssg08", player));
			addButton("SG 553", false);// TODO This one needs to be done
			addButton("AWP", false, new BuyGunRunnable("awp", player), new GunCallable("awp", player));
			addButton("G3SG1", false);// TODO This one need to be done.
		} else {
			addButton("FAMAS", false, new BuyGunRunnable("famas", player), new GunCallable("famas", player));
			addButton("M4A4", false, new BuyGunRunnable("m4a4", player), new GunCallable("m4a4", player));
			addButton("SSG 08", false, new BuyGunRunnable("ssg08", player), new GunCallable("ssg08", player));
			addButton("AUG", false, new BuyGunRunnable("aug", player), new GunCallable("aug", player));
			addButton("AWP", false, new BuyGunRunnable("awp", player), new GunCallable("awp", player));
			addButton("SCAR-20", false);// TODO This one needs to be done
		}
	}

}
