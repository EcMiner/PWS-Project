package com.daan.pws.guis;

import com.daan.pws.match.CompetitivePlayer;

public class MainBuyGUI extends BuyGUI {

	public MainBuyGUI(final CompetitivePlayer player) {
		super("buy menu", player, null, "SELECT");
		addButton("Rifles", new Runnable() {

			@Override
			public void run() {
				BuyGUI.openPage(player, RiflesGUI.class);
			}

		});
		addButton("Pistols", new Runnable() {

			@Override
			public void run() {
				BuyGUI.openPage(player, PistolsGUI.class);
			}

		});
		addButton("SMGs", new Runnable() {

			@Override
			public void run() {
				BuyGUI.openPage(player, SMGGui.class);
			}

		});
		addButton("Heavy Rifles", new Runnable() {

			@Override
			public void run() {
				BuyGUI.openPage(player, HeavyGUI.class);
			}

		});
		addButton("Grenades", new Runnable() {

			@Override
			public void run() {
				BuyGUI.openPage(player, GrenadesGUI.class);
			}

		});
		addButton("Equipment", new Runnable() {

			@Override
			public void run() {
				BuyGUI.openPage(player, EquipmentGUI.class);
			}

		});
	}
}
