package com.daan.pws.guis;

import com.daan.pws.match.CompetitivePlayer;
import com.daan.pws.match.enums.TeamEnum;

public class SMGGui extends BuyGUI {

	public SMGGui(CompetitivePlayer player) {
		super("BUY SMGs (PRIMARY WEAPON)", player, MainBuyGUI.class, "SMGs");
		if (player.getTeam() == TeamEnum.TERRORISTS) {
			addButton("MAC-10", false);
			addButton("MP7", false);
			addButton("UMP-45", false);
			addButton("P90", false);
			addButton("PP-Bizon", false);
		} else {
			addButton("MAC-10", false);
			addButton("MP7", false);
			addButton("UMP-45", false);
			addButton("P90", false);
			addButton("PP-Bizon", false);
		}
	}

}
