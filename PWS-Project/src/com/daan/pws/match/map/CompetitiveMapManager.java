package com.daan.pws.match.map;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import com.daan.pws.Main;
import com.google.common.base.Function;
import com.google.common.collect.Lists;

public class CompetitiveMapManager {

	private static Map<String, List<CompetitiveMap>> maps = new HashMap<String, List<CompetitiveMap>>();

	private static File file;
	private static FileConfiguration config;

	public CompetitiveMapManager() {
		file = new File(Main.getInstance().getDataFolder(), "maps.yml");
		config = YamlConfiguration.loadConfiguration(file);
		save();
		load();
	}

	public static Set<String> getAllMapNames() {
		return maps.keySet();
	}

	public static boolean hasMaps(String mapName) {
		return maps.containsKey(mapName.toLowerCase());
	}

	public static boolean isMap(String mapName, int id) {
		if (maps.containsKey(mapName.toLowerCase())) {
			for (CompetitiveMap map : maps.get(mapName.toLowerCase())) {
				if (map.getId() == id) {
					return true;
				}
			}
		}
		return false;
	}

	public static CompetitiveMap getMap(String mapName) {
		if (maps.containsKey(mapName.toLowerCase())) {
			for (CompetitiveMap map : maps.get(mapName.toLowerCase())) {
				if (map.isValid()) {
					return map;
				}
			}
		}
		return null;
	}

	public static CompetitiveMap getMap(String mapName, int id) {
		if (maps.containsKey(mapName.toLowerCase())) {
			for (CompetitiveMap map : maps.get(mapName.toLowerCase())) {
				if (map.getId() == id) {
					return map;
				}
			}
		}
		return null;
	}

	public static CompetitiveMap createNewMap(String mapName) {
		if (!maps.containsKey(mapName.toLowerCase())) {
			maps.put(mapName.toLowerCase(), new ArrayList<CompetitiveMap>());
		}

		int id = 0;
		outerloop: for (int i = 0; i < 100; i++) {
			for (CompetitiveMap map : maps.get(mapName.toLowerCase())) {
				if (map.getId() == i) {
					continue;
				} else {
					id = i;
					break outerloop;
				}
			}
		}

		CompetitiveMap map = new CompetitiveMap(mapName, id);
		maps.get(mapName.toLowerCase()).add(map);
		save(map);
		return map;
	}

	public static void save(CompetitiveMap map) {
		String string = "maps." + map.getName() + "." + map.getId() + ".";

		if (map.getCounterTerroristsBuyZone() != null)
			config.set(string + "ctBuyZone", serializeLocation(map.getCounterTerroristsBuyZone().getMax()) + ";" + serializeLocation(map.getCounterTerroristsBuyZone().getMin()));
		if (map.getTerroristsBuyZone() != null)
			config.set(string + "tBuyZone", serializeLocation(map.getTerroristsBuyZone().getMax()) + ";" + serializeLocation(map.getTerroristsBuyZone().getMin()));
		if (map.getABombSite() != null)
			config.set(string + "aBombSite", serializeLocation(map.getABombSite().getMax()) + ";" + serializeLocation(map.getABombSite().getMin()));
		if (map.getBBombSite() != null)
			config.set(string + "bBombSite", serializeLocation(map.getBBombSite().getMax()) + ";" + serializeLocation(map.getBBombSite().getMin()));

		Function<Location, String> transformFunction = new Function<Location, String>() {

			@Override
			public String apply(Location loc) {
				return serializeLocation(loc);
			}

		};
		config.set(string + "ctSpawns", Lists.transform((List<Location>) map.getCounterTerroristsSpawns(), transformFunction));
		config.set(string + "tSpawns", Lists.transform((List<Location>) map.getTerroristsSpawns(), transformFunction));
		save();
	}

	private void load() {
		if (config.isSet("maps")) {
			for (String mapName : config.getConfigurationSection("maps").getKeys(false)) {
				if (!maps.containsKey(mapName.toLowerCase())) {
					maps.put(mapName.toLowerCase(), new ArrayList<CompetitiveMap>());
				}
				for (String mapId : config.getConfigurationSection("maps." + mapName).getKeys(false)) {
					int id = Integer.valueOf(mapId);

					CompetitiveMap map = new CompetitiveMap(mapName, id);

					if (config.isSet("maps." + mapName + "." + mapId + ".ctBuyZone")) {
						String[] a = config.getString("maps." + mapName + "." + mapId + ".ctBuyZone").split(";");
						map.setCounterTerroristsBuyZone(new CuboidRegion(deserializeLocation(a[0]), deserializeLocation(a[1])));
					}
					if (config.isSet("maps." + mapName + "." + mapId + ".tBuyZone")) {
						String[] a = config.getString("maps." + mapName + "." + mapId + ".tBuyZone").split(";");
						map.setTerroristsBuyZone(new CuboidRegion(deserializeLocation(a[0]), deserializeLocation(a[1])));
					}
					if (config.isSet("maps." + mapName + "." + mapId + ".aBombSite")) {
						String[] a = config.getString("maps." + mapName + "." + mapId + ".aBombSite").split(";");
						map.setABombSite(new CuboidRegion(deserializeLocation(a[0]), deserializeLocation(a[1])));
					}
					if (config.isSet("maps." + mapName + "." + mapId + ".bBombSite")) {
						String[] a = config.getString("maps." + mapName + "." + mapId + ".bBombSite").split(";");
						map.setBBombSite(new CuboidRegion(deserializeLocation(a[0]), deserializeLocation(a[1])));
					}
					if (config.isSet("maps." + mapName + "." + mapId + ".ctSpawns")) {
						for (String sLoc : config.getStringList("maps." + mapName + "." + mapId + ".ctSpawns")) {
							map.addCounterTerroristsSpawn(deserializeLocation(sLoc));
						}
					}
					if (config.isSet("maps." + mapName + "." + mapId + ".tSpawns")) {
						for (String sLoc : config.getStringList("maps." + mapName + "." + mapId + ".tSpawns")) {
							map.addTerroristsSpawn(deserializeLocation(sLoc));
						}
					}
					maps.get(mapName.toLowerCase()).add(map);
					System.out.println("Loaded map: " + map.getName() + ":" + map.getId());
					System.out.println("Map valid?: " + map.isValid());
				}
			}
		}
	}

	private static void save() {
		try {
			config.save(file);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static String serializeLocation(Location location) {
		return location.getWorld().getName() + "," + location.getX() + "," + location.getY() + "," + location.getZ() + "," + location.getYaw() + "," + location.getPitch();
	}

	private static Location deserializeLocation(String serialized) {
		String[] args = serialized.split(",");
		return new Location(Bukkit.getWorld(args[0]), Double.valueOf(args[1]), Double.valueOf(args[2]), Double.valueOf(args[3]), Float.valueOf(args[4]), Float.valueOf(args[5]));
	}

}
