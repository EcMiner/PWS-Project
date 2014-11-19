package com.daan.pws.utilities;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import net.minecraft.server.v1_6_R3.EntityInsentient;
import net.minecraft.server.v1_6_R3.EntityTypes;

import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Entity;

public class EntityUtil {

	public static Entity getEntityById(int id) {
		for (World w : Bukkit.getWorlds()) {
			Entity e = getEntityById(id, w);
			if (e != null) {
				return e;
			}
		}
		return null;
	}

	public static Entity getEntityById(int id, Location loc) {
		return getEntityById(id, loc.getChunk());
	}

	public static Entity getEntityById(int id, Chunk c) {
		return getEntityById(id, c.getEntities());
	}

	public static Entity getEntityById(int id, World w) {
		return getEntityById(id, w.getEntities());
	}

	public static Entity getEntityById(int id, Entity[] toChoosFrom) {
		return getEntityById(id, Arrays.asList(toChoosFrom));
	}

	public static Entity getEntityById(int id, List<Entity> toChooseFrom) {
		for (Entity e : toChooseFrom) {
			if (e.getEntityId() == id) {
				return e;
			}
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	public static void registerEntity(String name, int id, Class<? extends EntityInsentient> customClass) {
		try {

			List<Map<?, ?>> dataMaps = new ArrayList<Map<?, ?>>();
			for (Field f : EntityTypes.class.getDeclaredFields()) {
				if (f.getType().getSimpleName().equals(Map.class.getSimpleName())) {
					f.setAccessible(true);
					dataMaps.add((Map<?, ?>) f.get(null));
				}
			}

			((Map<Class<? extends EntityInsentient>, String>) dataMaps.get(1)).put(customClass, name);
			((Map<Class<? extends EntityInsentient>, Integer>) dataMaps.get(3)).put(customClass, id);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
