package com.daan.pws.weapon;

import org.getspout.spoutapi.material.item.GenericCustomTool;

import com.daan.pws.Main;

public class GunItem extends GenericCustomTool {

	public GunItem(String name, String texture, int maxDurability) {
		super(Main.getInstance(), name, texture);
		setStackable(false);
		setMaxDurability((short) (maxDurability + 1));
	}

}
