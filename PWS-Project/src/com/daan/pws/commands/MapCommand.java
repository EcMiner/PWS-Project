package com.daan.pws.commands;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.daan.pws.Main;
import com.daan.pws.listeners.SelectionListener;
import com.daan.pws.match.map.CompetitiveMap;
import com.daan.pws.match.map.CompetitiveMapManager;
import com.daan.pws.match.map.CuboidRegion;
import com.daan.pws.utilities.NumberUtil;

public class MapCommand implements CommandExecutor {

	private Map<String, CompetitiveMap> selected = new HashMap<String, CompetitiveMap>();

	public MapCommand() {
		Main.registerCommands(this, "map");
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (sender instanceof Player) {
			Player player = (Player) sender;
			if (args.length == 0) {
				sendHelpMessage(player, label);
				return false;
			} else {
				if (args[0].equalsIgnoreCase("create")) {
					if (args.length == 2) {
						String mapName = args[1];
						CompetitiveMap map = CompetitiveMapManager.createNewMap(mapName);
						player.sendMessage(ChatColor.GREEN + "Succesfully created a map with the name " + mapName + " and the id " + map.getId());
						player.sendMessage(ChatColor.GREEN + "Type /" + label + " select " + mapName + " " + map.getId() + " to select this map so you can edit its settings.");
						return true;
					} else {
						player.sendMessage(ChatColor.RED + "Correct usage: /" + label + " create <mapname>");
						return false;
					}
				} else if (args[0].equalsIgnoreCase("select")) {
					if (args.length == 3) {
						String mapName = args[1];
						if (CompetitiveMapManager.hasMaps(mapName)) {
							if (NumberUtil.isInteger(args[2])) {
								int id = Integer.valueOf(args[2]);
								if (CompetitiveMapManager.isMap(mapName, id)) {
									selected.put(player.getName(), CompetitiveMapManager.getMap(mapName, id));
									player.sendMessage(ChatColor.GREEN + "Successfully selected map " + mapName + " with id " + id);
									return true;
								} else {
									player.sendMessage(ChatColor.RED + "No " + mapName + " map exists with the id " + id);
									return false;
								}
							} else {
								player.sendMessage(ChatColor.RED + "The id of the map must be a number!");
								return false;
							}
						} else {
							player.sendMessage(ChatColor.RED + "No maps have been created with this name. Type /" + label + " create " + mapName + " to create a new map with this name.");
							return false;
						}
					} else {
						player.sendMessage(ChatColor.RED + "Correct usage: /" + label + " select <mapname> <mapid>");
						return false;
					}
				} else if (args[0].equalsIgnoreCase("deselect")) {
					if (selected.containsKey(player.getName())) {
						selected.remove(player.getName());
						player.sendMessage(ChatColor.GREEN + "Successfully unselected the selected map.");
						return true;
					} else {
						player.sendMessage(ChatColor.RED + "You don't have a map selected!");
						return false;
					}
				} else if (args[0].equalsIgnoreCase("add")) {
					if (args.length == 2) {
						if (selected.containsKey(player.getName())) {
							CompetitiveMap map = selected.get(player.getName());
							if (args[1].equalsIgnoreCase("ctspawn")) {
								map.addCounterTerroristsSpawn(player.getLocation());
								player.sendMessage(ChatColor.GREEN + "Successfully added a Counter Terrorists spawn at your location for the map " + map.getName() + " with id " + map.getId());
								CompetitiveMapManager.save(map);
								return true;
							} else if (args[1].equalsIgnoreCase("tspawn")) {
								map.addTerroristsSpawn(player.getLocation());
								player.sendMessage(ChatColor.GREEN + "Successfully added a Terrorists spawn at your location for the map " + map.getName() + " with id " + map.getId());
								CompetitiveMapManager.save(map);
								return true;
							} else {
								player.sendMessage(ChatColor.RED + "Correct usage: /" + label + " add <ctspawn / tspawn>");
								return false;
							}
						} else {
							player.sendMessage(ChatColor.RED + "You don't have a map selected. Type: /" + label + " select <mapname> <mapid> to select a map so you can edit it.");
							return false;
						}
					} else {
						player.sendMessage(ChatColor.RED + "Correct usage: /" + label + " add <ctspawn / tspawn>");
						return false;
					}
				} else if (args[0].equalsIgnoreCase("set")) {
					if (args[1].equalsIgnoreCase("ctbuyzone")) {
						if (selected.containsKey(player.getName())) {
							CompetitiveMap map = selected.get(player.getName());
							if (SelectionListener.loc1.containsKey(player.getName()) && SelectionListener.loc2.containsKey(player.getName())) {
								Location loc1 = SelectionListener.loc1.get(player.getName());
								Location loc2 = SelectionListener.loc2.get(player.getName());
								CuboidRegion region = new CuboidRegion(loc1, loc2);
								map.setCounterTerroristsBuyZone(region);
								player.sendMessage(ChatColor.GREEN + "Succesfully set the Counter Terrorists Buyzone for the map " + map.getName() + " with id " + map.getId());
								CompetitiveMapManager.save(map);
								return true;
							} else {
								player.sendMessage(ChatColor.RED + "You haven't selected 2 location yet!");
								return false;
							}
						} else {
							player.sendMessage(ChatColor.RED + "You don't have a map selected. Type: /" + label + " select <mapname> <mapid> to select a map so you can edit it.");
							return false;
						}
					} else if (args[1].equalsIgnoreCase("tbuyzone")) {
						if (selected.containsKey(player.getName())) {
							CompetitiveMap map = selected.get(player.getName());
							if (SelectionListener.loc1.containsKey(player.getName()) && SelectionListener.loc2.containsKey(player.getName())) {
								Location loc1 = SelectionListener.loc1.get(player.getName());
								Location loc2 = SelectionListener.loc2.get(player.getName());
								CuboidRegion region = new CuboidRegion(loc1, loc2);
								map.setTerroristsBuyZone(region);
								player.sendMessage(ChatColor.GREEN + "Succesfully set the Terrorists Buyzone for the map " + map.getName() + " with id " + map.getId());
								CompetitiveMapManager.save(map);
								return true;
							} else {
								player.sendMessage(ChatColor.RED + "You haven't selected 2 location yet!");
								return false;
							}
						} else {
							player.sendMessage(ChatColor.RED + "You don't have a map selected. Type: /" + label + " select <mapname> <mapid> to select a map so you can edit it.");
							return false;
						}
					} else if (args[1].equalsIgnoreCase("bbombsite")) {
						if (selected.containsKey(player.getName())) {
							CompetitiveMap map = selected.get(player.getName());
							if (SelectionListener.loc1.containsKey(player.getName()) && SelectionListener.loc2.containsKey(player.getName())) {
								Location loc1 = SelectionListener.loc1.get(player.getName());
								Location loc2 = SelectionListener.loc2.get(player.getName());
								CuboidRegion region = new CuboidRegion(loc1, loc2);
								map.setBBombSite(region);
								player.sendMessage(ChatColor.GREEN + "Succesfully set Bombsite B for the map " + map.getName() + " with id " + map.getId());
								CompetitiveMapManager.save(map);
								return true;
							} else {
								player.sendMessage(ChatColor.RED + "You haven't selected 2 location yet!");
								return false;
							}
						} else {
							player.sendMessage(ChatColor.RED + "You don't have a map selected. Type: /" + label + " select <mapname> <mapid> to select a map so you can edit it.");
							return false;
						}
					} else if (args[1].equalsIgnoreCase("abombsite")) {
						if (selected.containsKey(player.getName())) {
							CompetitiveMap map = selected.get(player.getName());
							if (SelectionListener.loc1.containsKey(player.getName()) && SelectionListener.loc2.containsKey(player.getName())) {
								Location loc1 = SelectionListener.loc1.get(player.getName());
								Location loc2 = SelectionListener.loc2.get(player.getName());
								CuboidRegion region = new CuboidRegion(loc1, loc2);
								map.setABombSite(region);
								player.sendMessage(ChatColor.GREEN + "Succesfully set Bombsite A for the map " + map.getName() + " with id " + map.getId());
								CompetitiveMapManager.save(map);
								return true;
							} else {
								player.sendMessage(ChatColor.RED + "You haven't selected 2 location yet!");
								return false;
							}
						} else {
							player.sendMessage(ChatColor.RED + "You don't have a map selected. Type: /" + label + " select <mapname> <mapid> to select a map so you can edit it.");
							return false;
						}
					} else {
						player.sendMessage(ChatColor.RED + "Correct usage: /" + label + " set <ctbuyzone / tbuyzone / bbombsite / abombsite>");
						return false;
					}
				} else {
					sendHelpMessage(player, label);
					return false;
				}
			}
		} else {
			sender.sendMessage(ChatColor.RED + "Only in-game players can perform this command!");
			return false;
		}
	}

	private void sendHelpMessage(Player player, String label) {
		player.sendMessage(new String[] { ChatColor.GRAY + "Showing help for the " + ChatColor.GOLD + "/" + label + ChatColor.GRAY + " command:", ChatColor.GOLD + "/" + label + " <mapname> " + ChatColor.GRAY + "- With this command you can create a new map with a specific name.",
				ChatColor.GOLD + "/" + label + " select <mapname> <mapid> " + ChatColor.GRAY + "- With this command you can select a map so you can edit its options.", ChatColor.GOLD + "/" + label + " unselect " + ChatColor.GRAY + "- With this command you will unselect the map you selected",
				ChatColor.GOLD + "/" + label + " add <ctspawn / tspawn> " + ChatColor.GRAY + "- With this command you can add a spawnpoint for the Counter Terrorists side or the Terrorists side.",
				ChatColor.GOLD + "/" + label + " set <ctbuyzone / tbuyzone / bbombsite / abombsite> " + ChatColor.GRAY + "- With this command you can set the the buyzone for the Counter Terrorists side or the Terrorists side." });
	}

}
