package com.daan.pws;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.getspout.spoutapi.inventory.SpoutItemStack;
import org.getspout.spoutapi.player.SpoutPlayer;

import com.daan.pws.commands.LobbyCommand;
import com.daan.pws.commands.MapCommand;
import com.daan.pws.guis.MainBuyGUI;
import com.daan.pws.guis.listeners.BuyGUIListener;
import com.daan.pws.listeners.LeftClickListener;
import com.daan.pws.listeners.PlayerListener;
import com.daan.pws.listeners.ReloadListener;
import com.daan.pws.listeners.SelectionListener;
import com.daan.pws.listeners.WeaponDropListener;
import com.daan.pws.listeners.WorldListener;
import com.daan.pws.listeners.RightClickListener;
import com.daan.pws.match.Competitive;
import com.daan.pws.match.listeners.BlockListener;
import com.daan.pws.match.listeners.BombListener;
import com.daan.pws.match.map.CompetitiveMapManager;
import com.daan.pws.utilities.PlayerUtil;
import com.daan.pws.weapon.Gun;
import com.daan.pws.weapon.WeaponManager;

public class Main extends JavaPlugin {

	private static Main instance;
	public WeaponManager gunManager;

	@Override
	public void onEnable() {
		instance = this;

		registerListeners();

		this.gunManager = new WeaponManager();
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
		new LeftClickListener();
		new ReloadListener();
		new WeaponDropListener();
		new RightClickListener();
		new BuyGUIListener();
		pluginManager.registerEvents(new PlayerListener(this), this);
		pluginManager.registerEvents(new WorldListener(this), this);
		pluginManager.registerEvents(new PlayerUtil(), this);
		pluginManager.registerEvents(new SelectionListener(), this);
		pluginManager.registerEvents(new BlockListener(), this);
		pluginManager.registerEvents(new com.daan.pws.match.listeners.PlayerListener(), this);
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (command.getName().equalsIgnoreCase("ak")) {
			Gun gun = WeaponManager.getGun("AK-47");
			SpoutPlayer player = (SpoutPlayer) (Player) sender;
			SpoutItemStack spec = new SpoutItemStack(gun.getGunItem());
			ItemMeta im = spec.getItemMeta();
			im.setDisplayName("AK-47");
			spec.setItemMeta(im);
			player.getInventory().addItem(spec);
		} else if (command.getName().equalsIgnoreCase("glock")) {
			Gun gun = WeaponManager.getGun("Glock-18");
			SpoutPlayer player = (SpoutPlayer) (Player) sender;
			SpoutItemStack spec = new SpoutItemStack(gun.getGunItem());
			ItemMeta im = spec.getItemMeta();
			im.setDisplayName("Glock-18");
			spec.setItemMeta(im);
			player.getInventory().addItem(spec);
		} else if (command.getName().equalsIgnoreCase("p90")) {
			Gun gun = WeaponManager.getGun("P90");
			SpoutPlayer player = (SpoutPlayer) (Player) sender;
			SpoutItemStack spec = new SpoutItemStack(gun.getGunItem());
			ItemMeta im = spec.getItemMeta();
			im.setDisplayName("P90");
			spec.setItemMeta(im);
			player.getInventory().addItem(spec);
		} else if (command.getName().equalsIgnoreCase("AWP")) {
			Gun gun = WeaponManager.getGun("AWP");
			SpoutPlayer player = (SpoutPlayer) (Player) sender;
			SpoutItemStack spec = new SpoutItemStack(gun.getGunItem());
			ItemMeta im = spec.getItemMeta();
			im.setDisplayName("AWP");
			spec.setItemMeta(im);
			player.getInventory().addItem(spec);
		} else if (command.getName().equalsIgnoreCase("aug")) {
			Gun gun = WeaponManager.getGun("AUG");
			SpoutPlayer player = (SpoutPlayer) (Player) sender;
			SpoutItemStack spec = new SpoutItemStack(gun.getGunItem());
			ItemMeta im = spec.getItemMeta();
			im.setDisplayName("AUG");
			spec.setItemMeta(im);
			player.getInventory().addItem(spec);
		} else if (command.getName().equalsIgnoreCase("bomb")) {
			SpoutPlayer player = (SpoutPlayer) sender;
			new MainBuyGUI(Competitive.getMatch(player).getCompetitivePlayer(player)).render();
		}
		return false;
	}

}
