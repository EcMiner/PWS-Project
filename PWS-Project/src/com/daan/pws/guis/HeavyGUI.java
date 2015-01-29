package com.daan.pws.guis;

import com.daan.pws.match.CompetitivePlayer;
import com.daan.pws.match.enums.TeamEnum;

public class HeavyGUI extends BuyGUI {

	public HeavyGUI(CompetitivePlayer player) {
		super("BUY HEAVY RIFLES (PRIMARY WEAPON)", player, MainBuyGUI.class, "HEAVY RIFLES");
		if (player.getTeam() == TeamEnum.TERRORISTS) {
			addButton("Nova", false);
			addButton("XM1014", false);
			addButton("Sawed-Off", false);
			addButton("M249", false);
			addButton("Negev", false);
		} else {
			addButton("Nova", false);
			addButton("XM1014", false);
			addButton("Sawed-Off", false);
			addButton("M249", false);
			addButton("Negev", false);
		}
	}

}
