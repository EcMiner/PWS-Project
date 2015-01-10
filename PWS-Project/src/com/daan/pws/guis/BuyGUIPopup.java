package com.daan.pws.guis;

import org.getspout.spoutapi.gui.GenericPopup;

public class BuyGUIPopup extends GenericPopup {

	private BuyGUI buyGui;

	public BuyGUIPopup(BuyGUI buyGui) {
		this.buyGui = buyGui;
	}

	public BuyGUI getBuyGui() {
		return buyGui;
	}

}
