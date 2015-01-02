package com.daan.pws.commands;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.getspout.spoutapi.player.SpoutPlayer;

import com.daan.pws.Main;
import com.daan.pws.match.Competitive;
import com.daan.pws.match.enums.TeamEnum;
import com.daan.pws.match.map.CompetitiveMap;
import com.daan.pws.match.map.CompetitiveMapManager;

public class LobbyCommand implements CommandExecutor, TabCompleter {

	public LobbyCommand() {
		Main.registerCommands(this, "lobby");
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (sender instanceof Player) {
			if (sender instanceof SpoutPlayer) {
				SpoutPlayer player = (SpoutPlayer) sender;
				if (args.length == 0) {
					sendHelpMessage(player, label);
					return false;
				} else {
					if (args[0].equalsIgnoreCase("create")) {
						if (args.length == 2) {
							if (!Competitive.isInMatch(player)) {
								String mapName = args[1];
								if (CompetitiveMapManager.hasMaps(mapName)) {
									CompetitiveMap map = CompetitiveMapManager.getMap(mapName);
									if (map != null) {
										Competitive comp = new Competitive(map);
										TeamEnum team = comp.addPlayer(player);
										player.sendMessage(ChatColor.GREEN + "Successfully created a lobby on the map " + map.getName() + ". You have been put on the " + team.getName() + " side");
										return true;
									} else {
										player.sendMessage(ChatColor.RED + "There are no available " + mapName + " maps");
										return false;
									}
								} else {
									player.sendMessage(ChatColor.RED + "No maps with the name " + mapName + " have been created!");
									return false;
								}
							} else {
								player.sendMessage(ChatColor.RED + "You are already in a lobby! Type /" + label + " leave to leave your current lobby.");
								return false;
							}
						} else {
							player.sendMessage(ChatColor.RED + "Correct usage: /" + label + " create <mapname>");
							return false;
						}
					} else if (args[0].equalsIgnoreCase("join")) {
						if (args.length == 2) {
							if (!Competitive.isInMatch(player)) {
								String mapName = args[1];
								if (CompetitiveMapManager.hasMaps(mapName)) {
									for (Competitive match : Competitive.getAllMatches()) {
										if (!match.isGameStarted() && match.getMap().getName().equalsIgnoreCase("") && match.canJoin()) {
											TeamEnum team = match.addPlayer(player);
											player.sendMessage(ChatColor.GREEN + "Successfully joined a game on the map " + match.getMap().getName() + " and you joined the " + team.getName() + " side.");
											return true;
										}
									}
									player.sendMessage(ChatColor.RED + "No match available on the map " + mapName);
									return false;
								} else {
									player.sendMessage(ChatColor.RED + "No maps with the name " + mapName + " have been created!");
									return false;
								}
							} else {
								player.sendMessage(ChatColor.RED + "You are already in a lobby! Type /" + label + " leave to leave your current lobby.");
								return false;
							}
						} else {
							player.sendMessage(ChatColor.RED + "Correct usage: /" + label + " join <mapname>");
							return false;
						}
					} else if (args[0].equalsIgnoreCase("leave")) {
						if (args.length == 1) {
							if (Competitive.isInMatch(player)) {
								if (!Competitive.getMatch(player).isGameStarted()) {
									Competitive.getMatch(player).removePlayer(player);
								} else {
									player.sendMessage(ChatColor.RED + "Can't leave current lobby because the match has already started.");
									return false;
								}
							} else {
								player.sendMessage(ChatColor.RED + "You are not in a lobby so you can not leave one!");
								return false;
							}
						} else {
							player.sendMessage(ChatColor.RED + "Correct usage: /" + label + " leave");
							return false;
						}
					} else {
						sendHelpMessage(player, label);
						return false;
					}
				}
			} else {
				sender.sendMessage(ChatColor.RED + "Only players that use spout can use this command!");
				return false;
			}
		} else {
			sender.sendMessage(ChatColor.RED + "Only in-game players can perform this command!");
			return false;
		}
		return false;
	}

	String[] subs = new String[] { "create", "join", "leave" };

	@Override
	public List<String> onTabComplete(CommandSender sender, Command cmd, String label, String[] args) {
		if (args.length == 2) {
			if (args[0].equalsIgnoreCase("create") || args[0].equalsIgnoreCase("join")) {
				if (args.length == 2) {
					ArrayList<String> list = new ArrayList<String>();
					for (String mapName : CompetitiveMapManager.getAllMapNames()) {
						if (mapName.toLowerCase().startsWith(args[1].toLowerCase())) {
							list.add(mapName);
						}
					}
					return list;
				}
			}
		} else if (args.length == 1) {
			ArrayList<String> list = new ArrayList<String>();
			for (String sub : subs) {
				if (sub.toLowerCase().startsWith(args[0].toLowerCase())) {
					list.add(sub);
				}
			}
			return list;
		}
		return null;
	}

	private void sendHelpMessage(Player player, String label) {
		player.sendMessage(new String[] { ChatColor.GOLD + "/" + label + " create <mapname> " + ChatColor.GRAY + "- With this command you can create a lobby with a specific map.", ChatColor.GOLD + "/" + label + " join <mapname> " + ChatColor.GRAY + "- With this command you can join a lobby with a specific maps",
				ChatColor.GOLD + "/" + label + " leave " + ChatColor.GRAY + "- With this command you current lobby (If you joined one)." });
	}

}
