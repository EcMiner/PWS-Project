package com.daan.pws.weapon;

import org.getspout.spoutapi.material.item.GenericCustomItem;

import com.daan.pws.Main;

public class GrenadeItem extends GenericCustomItem {

	public GrenadeItem(String name, String texture, boolean stackAble) {
		super(Main.getInstance(), name, texture);
		setStackable(stackAble);
	}

}
