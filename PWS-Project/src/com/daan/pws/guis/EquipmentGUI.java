package com.daan.pws.guis;

import com.daan.pws.match.CompetitivePlayer;
import com.daan.pws.match.enums.TeamEnum;

public class EquipmentGUI extends BuyGUI {

	public EquipmentGUI(CompetitivePlayer player) {
		super("BUY EQUIPMENT", player, MainBuyGUI.class, "EQUIPMENT");
		if (player.getTeam() == TeamEnum.TERRORISTS) {
			addButton("Kevlar Vest", false);
			addButton("Kevlar + Helmet", false);
			addButton("Zeus x27", false);
		} else {
			addButton("Kevlar Vest", false);
			addButton("Kevlar + Helmet", false);
			addButton("Zeus x27", false);
			addButton("Defuse Kit", false);
		}
	}

}
