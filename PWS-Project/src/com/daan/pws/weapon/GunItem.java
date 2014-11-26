package com.daan.pws.weapon;

import org.getspout.spoutapi.material.item.GenericCustomItem;

import com.daan.pws.Main;

public class GunItem extends GenericCustomItem {

	public GunItem(String name, String texture) {
		super(Main.getInstance(), name, texture);
		setStackable(false);
	}

}
