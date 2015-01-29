package com.daan.pws.match.items;

import org.bukkit.ChatColor;
import org.bukkit.inventory.ItemStack;
import org.getspout.spoutapi.inventory.SpoutItemStack;
import org.getspout.spoutapi.material.item.GenericCustomItem;

import com.daan.pws.Main;
import com.daan.pws.utilities.ItemUtil;

public class Bomb {

	private static final BombItem bombItem = new BombItem();

	public static SpoutItemStack newBomb() {
		SpoutItemStack spec = new SpoutItemStack(bombItem);
		ItemUtil.setDisplayName(spec, ChatColor.ITALIC + "Bomb");
		return spec;
	}

	public static boolean isBomb(ItemStack item) {
		if (item != null && item.hasItemMeta() && item.getItemMeta().getDisplayName() != null) {
			return item.getItemMeta().getDisplayName().equals(ChatColor.ITALIC + "Bomb");
		}
		return false;
	}

	private static class BombItem extends GenericCustomItem {

		public BombItem() {
			super(Main.getInstance(), "Bomb", "http://i.imgur.com/AoB5UeR.png");
			setStackable(false);
		}

	}

}
