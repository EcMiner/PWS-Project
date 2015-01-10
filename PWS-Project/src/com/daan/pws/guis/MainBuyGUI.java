package com.daan.pws.guis;

import com.daan.pws.match.CompetitivePlayer;

public class MainBuyGUI extends BuyGUI {

	public MainBuyGUI(final CompetitivePlayer player) {
		super("buy menu", player, null, "SELECT");
		addButton("rifles", false, new Runnable() {

			@Override
			public void run() {
				BuyGUI.openPage(player, RiflesGUI.class);
			}

		});
		addButton("pistols");
		addButton("smg");
		addButton("heavy");
		addButton("grenades");
		addButton("equipment");
	}

}
