package com.daan.pws;

import java.lang.reflect.Field;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import net.minecraft.server.v1_7_R4.DataWatcher;
import net.minecraft.server.v1_7_R4.EntityInsentient;
import net.minecraft.server.v1_7_R4.EntityTypes;
import net.minecraft.server.v1_7_R4.EntityWolf;
import net.minecraft.server.v1_7_R4.Packet;
import net.minecraft.server.v1_7_R4.PacketPlayOutNamedEntitySpawn;
import net.minecraft.util.com.mojang.authlib.GameProfile;
import net.minecraft.util.com.mojang.authlib.properties.Property;

import org.bukkit.Bukkit;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.craftbukkit.v1_7_R4.entity.CraftPlayer;
import org.bukkit.craftbukkit.v1_7_R4.entity.CraftWolf;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import com.daan.pws.entities.NPCEntity;
import com.daan.pws.entities.StatusZombie;
import com.daan.pws.listeners.BlockListener;
import com.daan.pws.listeners.PlayerListener;
import com.daan.pws.listeners.WorldListener;
import com.daan.pws.protocol.MCProtocol;
import com.daan.pws.protocol.PacketEvent;
import com.daan.pws.protocol.PacketListener;
import com.daan.pws.protocol.PacketType;
import com.daan.pws.protocol.injection.PlayerInjection;
import com.daan.pws.protocol.wrappers.Packet24SpawnEntityLiving;
import com.daan.pws.protocol.wrappers.PacketWindowClick;

public class Main extends JavaPlugin {

	private static Main instance;
	public PlayerInjection injection;
	public MCProtocol protocol;

	@SuppressWarnings("deprecation")
	@Override
	public void onEnable() {
		instance = this;

		registerEntity("Zombie", 54, StatusZombie.class);
		registerEntity("Wolf", EntityType.WOLF.getTypeId(), NPCEntity.class);

		PacketType.init();

		registerListeners();

		this.injection = new PlayerInjection(this);
		this.protocol = new MCProtocol();

		/*
		 * protocol.addPacketListener(PacketType.Play.Server.WORLD_PARTICLES, new PacketListener() {
		 * 
		 * @Override public void onPacketEvent(PacketEvent event) { Packet63WorldParticles packet = new Packet63WorldParticles(event.getPacket()); if (packet.getParticle() != Particle.FLAME) { packet.setOffsetX(0); packet.setOffsetY(0); packet.setOffsetZ(0); packet.setParticle(Particle.FLAME);
		 * packet.setAmount(10); packet.setSpeed(0.001f); } }
		 * 
		 * });
		 */

		protocol.addPacketListener(PacketType.Play.Client.WINDOW_CLICK, new PacketListener() {

			@Override
			public void onPacketEvent(PacketEvent event) {
				PacketWindowClick packet = new PacketWindowClick(event.getPacket());
				System.out.println(packet.getTransactionID());

			}

		});

		protocol.addPacketListener(PacketType.Play.Server.SPAWN_ENTITY_LIVING, new PacketListener() {

			@Override
			public void onPacketEvent(PacketEvent event) {
				Packet24SpawnEntityLiving packet = new Packet24SpawnEntityLiving(event.getPacket());
				if (EntityTypes.b(packet.getEntityType()) == "Wolf") {
					EntityWolf wolf = ((CraftWolf) packet.getEntity()).getHandle();
					if (wolf instanceof NPCEntity) {
						event.setCancelled(true);
						Packet pcket = getPlayerSpawnPacket(wolf.getBukkitEntity().getWorld(), "NPC", wolf.getBukkitEntity().getEntityId(), wolf.getBukkitEntity().getLocation(), Material.DIAMOND_SWORD.getId(), "schmockyyy");
						for (Player o : Bukkit.getOnlinePlayers()) {
							((CraftPlayer) o).getHandle().playerConnection.sendPacket(pcket);
						}
					}
				}
			}

		});

		Bukkit.getWorld("world").playEffect(null, Effect.CLOUD, 0);

	}

	public static void setSkin(Plugin plugin, final Player p, final String toSkin) {
		new BukkitRunnable() {

			@Override
			public void run() {
				try {
					Packet packet = new PacketPlayOutNamedEntitySpawn(((CraftPlayer) p).getHandle());

					Field gameProfileField = PacketPlayOutNamedEntitySpawn.class.getDeclaredField("b");
					gameProfileField.setAccessible(true);

					@SuppressWarnings("deprecation")
					GameProfile profile = new GameProfile(Bukkit.getOfflinePlayer(p.getName()).getUniqueId(), p.getName());
					fixSkin(profile, toSkin);

					gameProfileField.set(packet, profile);

					for (Player pl : Bukkit.getOnlinePlayers()) {
						if (pl.equals(p)) {
							continue;
						}
						((CraftPlayer) pl).getHandle().playerConnection.sendPacket(packet);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}.runTaskAsynchronously(plugin);
	}

	@SuppressWarnings({ "deprecation", "resource" })
	private static void fixSkin(GameProfile profile, String skinOwner) {
		try {
			URL url = new URL("https://sessionserver.mojang.com/session/minecraft/profile/" + Bukkit.getOfflinePlayer(skinOwner).getUniqueId().toString().replace("-", ""));
			URLConnection uc = url.openConnection();

			// Parse it
			Scanner scanner = new Scanner(uc.getInputStream(), "UTF-8").useDelimiter("\\A");
			String json = scanner.next();

			JSONArray properties = (JSONArray) ((JSONObject) new JSONParser().parse(json)).get("properties");
			for (int i = 0; i < properties.size(); i++) {
				JSONObject property = (JSONObject) properties.get(i);
				String name = (String) property.get("name");
				String value = (String) property.get("value");
				String signature = property.containsKey("signature") ? (String) property.get("signature") : null;
				if (signature != null) {
					profile.getProperties().put(name, new Property(name, value, signature));
				} else {
					profile.getProperties().put(name, new Property(value, name));
				}
			}
		} catch (Exception e) {
		}
	}

	@SuppressWarnings("deprecation")
	private PacketPlayOutNamedEntitySpawn getPlayerSpawnPacket(World w, String name, int id, Location l, int itemInHand, String skin) {
		DataWatcher d = new DataWatcher(null);
		d.a(0, (byte) 0);
		d.a(1, (short) 0);
		d.a(8, (byte) 0);
		PacketPlayOutNamedEntitySpawn spawn = new PacketPlayOutNamedEntitySpawn();
		setField(spawn, "a", id);
		GameProfile profile = new GameProfile(Bukkit.getOfflinePlayer(name).getUniqueId(), name);
		fixSkin(profile, skin);
		setField(spawn, "b", profile);
		setField(spawn, "c", (int) l.getX() * 32);
		setField(spawn, "d", (int) l.getY() * 32);
		setField(spawn, "e", (int) l.getZ() * 32);
		setField(spawn, "f", (byte) ((l.getYaw() * 256f) / 360f));
		setField(spawn, "g", (byte) ((l.getPitch() * 256f) / 360f));
		setField(spawn, "h", itemInHand);
		setField(spawn, "i", d);
		return spawn;
	}

	protected final void setField(Object targetedClass, String fieldName, Object value) {
		try {
			Field f = targetedClass.getClass().getDeclaredField(fieldName);
			f.setAccessible(true);
			f.set(targetedClass, value);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void onDisable() {
		injection.disable();
	}

	public static Main getInstance() {
		return instance;
	}

	private void registerListeners() {
		PluginManager pluginManager = getServer().getPluginManager();

		pluginManager.registerEvents(new BlockListener(this), this);
		pluginManager.registerEvents(new PlayerListener(this), this);
		pluginManager.registerEvents(new WorldListener(this), this);
	}

	@SuppressWarnings("unchecked")
	public void registerEntity(String name, int id, Class<? extends EntityInsentient> customClass) {
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
