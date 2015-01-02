package com.daan.pws;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.getspout.spoutapi.inventory.SpoutItemStack;
import org.getspout.spoutapi.material.item.GenericCustomTool;
import org.getspout.spoutapi.player.SpoutPlayer;

import com.daan.pws.commands.LobbyCommand;
import com.daan.pws.commands.MapCommand;
import com.daan.pws.listeners.GunFireListener;
import com.daan.pws.listeners.PlayerListener;
import com.daan.pws.listeners.ReloadListener;
import com.daan.pws.listeners.SelectionListener;
import com.daan.pws.listeners.WeaponDropListener;
import com.daan.pws.listeners.WorldListener;
import com.daan.pws.match.listeners.BlockListener;
import com.daan.pws.match.listeners.BombListener;
import com.daan.pws.match.map.CompetitiveMapManager;
import com.daan.pws.utilities.PlayerUtil;
import com.daan.pws.weapon.Gun;
import com.daan.pws.weapon.GunManager;

public class Main extends JavaPlugin {

	private static Main instance;
	public GunManager gunManager;

	@Override
	public void onEnable() {
		instance = this;

		registerListeners();

		this.gunManager = new GunManager();
		new CompetitiveMapManager();

		loadCommands();
	}

	@Override
	public void onDisable() {
	}

	public static Main getInstance() {
		return instance;
	}

	public static void registerCommands(CommandExecutor exec, String... commands) {
		for (String command : commands) {
			instance.getCommand(command).setExecutor(exec);
		}
	}

	private void loadCommands() {
		new MapCommand();
		new LobbyCommand();
	}

	private void registerListeners() {
		PluginManager pluginManager = getServer().getPluginManager();

		pluginManager.registerEvents(new BombListener(), this);
		new GunFireListener();
		new ReloadListener();
		new WeaponDropListener();
		pluginManager.registerEvents(new PlayerListener(this), this);
		pluginManager.registerEvents(new WorldListener(this), this);
		pluginManager.registerEvents(new PlayerUtil(), this);
		pluginManager.registerEvents(new SelectionListener(), this);
		pluginManager.registerEvents(new BlockListener(), this);
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (command.getName().equalsIgnoreCase("ak")) {
			Gun gun = GunManager.getGun("AK-47");
			SpoutPlayer player = (SpoutPlayer) (Player) sender;
			SpoutItemStack spec = new SpoutItemStack(gun.getGunItem());
			ItemMeta im = spec.getItemMeta();
			im.setDisplayName("AK-47");
			spec.setItemMeta(im);
			player.getInventory().addItem(spec);
		} else if (command.getName().equalsIgnoreCase("glock")) {
			Gun gun = GunManager.getGun("Glock-18");
			SpoutPlayer player = (SpoutPlayer) (Player) sender;
			SpoutItemStack spec = new SpoutItemStack(gun.getGunItem());
			GenericCustomTool.setDurability(spec, (short) 50);
			ItemMeta im = spec.getItemMeta();
			im.setDisplayName("Glock-18");
			spec.setItemMeta(im);
			player.getInventory().addItem(spec);
		} else if (command.getName().equalsIgnoreCase("p90")) {
			Gun gun = GunManager.getGun("P90");
			SpoutPlayer player = (SpoutPlayer) (Player) sender;
			SpoutItemStack spec = new SpoutItemStack(gun.getGunItem());
			GenericCustomTool.setDurability(spec, (short) 50);
			ItemMeta im = spec.getItemMeta();
			im.setDisplayName("P90");
			spec.setItemMeta(im);
			player.getInventory().addItem(spec);
		} else if (command.getName().equalsIgnoreCase("bomb")) {
			Player player = (Player) sender;
			if (!PlayerUtil.isFrozen(player)) {
				PlayerUtil.freeze(player);
			} else {
				PlayerUtil.unfreeze(player);
			}
		}
		return false;
	}

}
