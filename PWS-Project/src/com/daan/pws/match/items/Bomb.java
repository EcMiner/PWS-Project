package com.daan.pws.match.items;

import org.bukkit.Location;
import org.getspout.spoutapi.block.design.GenericCuboidBlockDesign;
import org.getspout.spoutapi.inventory.SpoutItemStack;
import org.getspout.spoutapi.material.block.GenericCuboidCustomBlock;
import org.getspout.spoutapi.material.item.GenericCustomItem;
import org.getspout.spoutapi.player.SpoutPlayer;

import com.daan.pws.Main;
import com.daan.pws.utilities.ItemUtil;

public class Bomb {

	private static final BombItem bombItem = new BombItem();

	private static final BombBlock bombBlock = new BombBlock();

	public static void giveBomb(SpoutPlayer player) {
		SpoutItemStack spec = new SpoutItemStack(bombItem);
		ItemUtil.setDisplayName(spec, "Bomb");
		player.getInventory().addItem(spec);
	}

	public static void placeBomb(Location loc) {
	}

	private static class BombItem extends GenericCustomItem {

		public BombItem() {
			super(Main.getInstance(), "Bomb", "http://i.imgur.com/AoB5UeR.png");
			setStackable(false);
		}

	}

	private static class BombBlock extends GenericCuboidCustomBlock {

		public BombBlock() {
			super(Main.getInstance(), "Bomb Block", new GenericCuboidBlockDesign(Main.getInstance(), "i.imgur.com/BdDGEq5.png", 32, 0f, 0f, 0f, 1f, 1f, 1f));
		}

	}

}
