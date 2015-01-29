package com.daan.pws.guis;

import com.daan.pws.match.CompetitivePlayer;
import com.daan.pws.match.enums.TeamEnum;

public class GrenadesGUI extends BuyGUI {

	public GrenadesGUI(CompetitivePlayer player) {
		super("BUY GRENADES", player, MainBuyGUI.class, "GRENADES");
		if (player.getTeam() == TeamEnum.TERRORISTS) {
			addButton("Molotov", false);
			addButton("Flashbang", false);
			addButton("Frag Grenade", false);
			addButton("Smoke Grenade", false);
		} else {
			addButton("Incendiary Grenade", false);
			addButton("Flashbang", false);
			addButton("Frag Grenade", false);
			addButton("Smoke Grenade", false);
		}
	}

}
