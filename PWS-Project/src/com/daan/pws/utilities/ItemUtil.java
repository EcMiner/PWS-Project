package com.daan.pws.utilities;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;

public class ItemUtil {

	public static ItemStack setItemMeta(ItemStack is, String displayName, String... lore) {
		ItemMeta im = is.getItemMeta();
		im.setDisplayName(displayName);
		List<String> list = new ArrayList<String>();
		for (String s : lore) {
			list.add(s);
		}
		im.setLore(list);
		is.setItemMeta(im);
		return is;
	}

	public static ItemStack setItemMeta(ItemStack is, String displayName, List<String> lore) {
		ItemMeta im = is.getItemMeta();
		im.setDisplayName(displayName);
		im.setLore(lore);
		is.setItemMeta(im);
		return is;
	}

	public static ItemStack setDisplayName(ItemStack is, String displayName) {
		ItemMeta im = is.getItemMeta();
		im.setDisplayName(displayName);
		is.setItemMeta(im);
		return is;
	}

	public static ItemStack setLore(ItemStack is, String... lines) {
		ItemMeta im = is.getItemMeta();
		List<String> lore = new ArrayList<String>();
		for (String line : lines) {
			lore.add(line);
		}
		im.setLore(lore);
		is.setItemMeta(im);
		return is;
	}

	public static ItemStack setLore(ItemStack is, List<String> lore) {
		ItemMeta im = is.getItemMeta();
		im.setLore(lore);
		is.setItemMeta(im);
		return is;
	}

	public static ItemStack addEnchant(ItemStack item, Enchantment ench, int level) {
		ItemStack stack = item.clone();
		stack.addUnsafeEnchantment(ench, level);

		return stack;
	}

	public static boolean matchesDislayname(ItemStack is, String s) {
		return is.hasItemMeta() && ChatColor.stripColor(is.getItemMeta().getDisplayName()).equals(s);
	}

	@SuppressWarnings("deprecation")
	public static boolean isSameItem(ItemStack is1, ItemStack is2) {
		if (is1.getType() == is2.getType() && is1.getData().getData() == is2.getData().getData()) {
			if (!is1.hasItemMeta() && !is2.hasItemMeta()) {
				return true;
			} else if (is1.hasItemMeta() && is2.hasItemMeta()) {
				return (is1.getItemMeta().getDisplayName() == null && is2.getItemMeta().getDisplayName() == null ? true : (is1.getItemMeta().getDisplayName() != null && is2.getItemMeta().getDisplayName() != null ? is1.getItemMeta().getDisplayName().equals(is2.getItemMeta().getDisplayName()) : false))
						&& (is1.getItemMeta().getLore() == null && is2.getItemMeta().getLore() == null ? true : (is1.getItemMeta().getLore() != null && is2.getItemMeta().getLore() != null ? is1.getItemMeta().getLore().equals(is2.getItemMeta().getLore()) : false));
			}
		}
		return false;
	}

	public static ItemStack setColour(ItemStack is, Color colour) {
		if (is.getType() == Material.LEATHER_BOOTS || is.getType() == Material.LEATHER_CHESTPLATE || is.getType() == Material.LEATHER_HELMET || is.getType() == Material.LEATHER_LEGGINGS) {
			LeatherArmorMeta meta = (LeatherArmorMeta) is.getItemMeta();
			meta.setColor(colour);
			is.setItemMeta(meta);
		}
		return is;
	}

}
