package com.daan.pws.guis;

import com.daan.pws.match.CompetitivePlayer;
import com.daan.pws.match.enums.TeamEnum;

public class RiflesGUI extends BuyGUI {

	public RiflesGUI(CompetitivePlayer player) {
		super("BUY RIFLES (PRIMARY WEAPON)", player, MainBuyGUI.class, "RIFLES");
		if (player.getTeam() == TeamEnum.TERRORISTS) {
			addButton("Galil AR", false);
			addButton("AK-47", false);
			addButton("SSG 08", false);
			addButton("SG 553", false);
			addButton("AWP", false);
			addButton("G3SG1", false);
		} else {
			addButton("FAMAS", false);
			addButton("M4A4", false);
			addButton("SSG 08", false);
			addButton("AUG", false);
			addButton("AWP", false);
			addButton("SCAR-20", false);
		}
	}

}
