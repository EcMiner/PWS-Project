package com.daan.pws.match.items;

import org.bukkit.Location;
import org.bukkit.block.BlockFace;
import org.getspout.spoutapi.block.SpoutBlock;
import org.getspout.spoutapi.block.design.GenericCubeBlockDesign;
import org.getspout.spoutapi.inventory.SpoutItemStack;
import org.getspout.spoutapi.material.block.GenericCubeCustomBlock;
import org.getspout.spoutapi.material.item.GenericCustomItem;
import org.getspout.spoutapi.player.SpoutPlayer;

import com.daan.pws.Main;

public class Bomb {

	private static final BombItem bombItem = new BombItem();

	// private static final BombBlock bombBlock = new BombBlock();

	public static void giveBomb(SpoutPlayer player) {
		SpoutItemStack spec = new SpoutItemStack(bombItem);
		// ItemMeta im = spec.getItemMeta();
		// im.setDisplayName("  Bomb  ");
		// spec.setItemMeta(im);
		player.getInventory().addItem(spec);
	}

	public static void placeBomb(Location loc) {
		// SpoutCraftBlock block = new SpoutCraftBlock(SpoutCraftChunk.getChunkSafe(loc.getChunk()), loc.getBlockX(), loc.getBlockY(), loc.getBlockZ());
		// block.setCustomBlock(bombBlock);
	}

	private static class BombItem extends GenericCustomItem {

		public BombItem() {
			super(Main.getInstance(), "Bomb", "http://i.imgur.com/AoB5UeR.png");
			setStackable(false);
			// setMaxDurability((short) 7);
		}

		@Override
		public boolean onItemInteract(SpoutPlayer player, SpoutBlock block, BlockFace face) {
			// block.getRelative(face).setCustomBlock(bombBlock);
			return true;
		}

	}

	private static class BombBlock extends GenericCubeCustomBlock {

		public BombBlock() {
			super(Main.getInstance(), "Bomb", false, new GenericCubeBlockDesign(Main.getInstance(), "http://prntscr.com/5jmn1p", 32));
		}

	}

}
