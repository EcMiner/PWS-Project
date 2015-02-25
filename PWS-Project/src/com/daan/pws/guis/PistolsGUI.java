package com.daan.pws.guis;

import com.daan.pws.guis.callables.GunCallable;
import com.daan.pws.guis.runnables.BuyGunRunnable;
import com.daan.pws.match.CompetitivePlayer;
import com.daan.pws.match.enums.TeamEnum;

public class PistolsGUI extends BuyGUI {

	public PistolsGUI(CompetitivePlayer player) {
		super("BUY PISTOLS (SECONDARY WEAPON)", player, MainBuyGUI.class, "PISTOLS");
		if (player.getTeam() == TeamEnum.TERRORISTS) {
			addButton("Glock-18", false, new BuyGunRunnable("glock18", player), new GunCallable("block18", player));
			addButton("P250", false, new BuyGunRunnable("p250", player), new GunCallable("p250", player));
			addButton("CZ-75 Auto", false, new BuyGunRunnable("cz75auto", player), new GunCallable("cz75auto", player));
			addButton("Desert Eagle", false, new BuyGunRunnable("deserteagle", player), new GunCallable("deserteagle", player));
		} else {
			addButton("P2000", false, new BuyGunRunnable("P2000", player), new GunCallable("P2000", player));
			addButton("P250", false, new BuyGunRunnable("p250", player), new GunCallable("p250", player));
			addButton("CZ-75 Auto", false, new BuyGunRunnable("cz75auto", player), new GunCallable("cz75auto", player));
			addButton("Desert Eagle", false, new BuyGunRunnable("deserteagle", player), new GunCallable("deserteagle", player));
		}
	}

}
