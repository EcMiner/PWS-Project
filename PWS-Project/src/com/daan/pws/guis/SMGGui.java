package com.daan.pws.guis;

import com.daan.pws.guis.callables.GunCallable;
import com.daan.pws.guis.runnables.BuyGunRunnable;
import com.daan.pws.match.CompetitivePlayer;
import com.daan.pws.match.enums.TeamEnum;

public class SMGGui extends BuyGUI {

	public SMGGui(CompetitivePlayer player) {
		super("BUY SMGs (PRIMARY WEAPON)", player, MainBuyGUI.class, "SMGs");
		if (player.getTeam() == TeamEnum.TERRORISTS) {
			addButton("MAC-10", false);
			addButton("MP7", false);
			addButton("UMP-45", false);
			addButton("P90", false, new BuyGunRunnable("p90", player), new GunCallable("p90", player));
			addButton("PP-Bizon", false, new BuyGunRunnable("ppbizon", player), new GunCallable("ppbizon", player));
		} else {
			addButton("MAC-10", false);
			addButton("MP7", false);
			addButton("UMP-45", false);
			addButton("P90", false, new BuyGunRunnable("p90", player), new GunCallable("p90", player));
			addButton("PP-Bizon", false, new BuyGunRunnable("ppbizon", player), new GunCallable("ppbizon", player));
		}
	}

}
