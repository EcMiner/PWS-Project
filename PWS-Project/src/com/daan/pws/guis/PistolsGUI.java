package com.daan.pws.guis;

import com.daan.pws.guis.callables.GunCallable;
import com.daan.pws.guis.runnables.BuyGunRunnable;
import com.daan.pws.match.CompetitivePlayer;
import com.daan.pws.match.enums.TeamEnum;

public class PistolsGUI extends BuyGUI {

	public PistolsGUI(CompetitivePlayer player) {
		super("BUY PISTOLS (SECONDARY WEAPON)", player, MainBuyGUI.class, "PISTOLS");
		if (player.getTeam() == TeamEnum.TERRORISTS) {
			addButton("Glock-18", false);
			addButton("P250", false);
			addButton("CZ-75 Auto", false);
			addButton("Desert Eagle", false);
		} else {
			addButton("P2000", false, new BuyGunRunnable("P2000", player), new GunCallable("P2000", player));
			addButton("P250", false);
			addButton("CZ-75 Auto", false);
			addButton("Desert Eagle", false);
		}
	}

}
