package com.daan.pws;

import org.bukkit.Bukkit;
import org.bukkit.Effect;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import com.daan.pws.listeners.BlockListener;
import com.daan.pws.listeners.PlayerListener;
import com.daan.pws.listeners.WorldListener;
import com.daan.pws.protocol.MCProtocol;
import com.daan.pws.protocol.PacketEvent;
import com.daan.pws.protocol.PacketListener;
import com.daan.pws.protocol.PacketType;
import com.daan.pws.protocol.injection.PlayerInjection;
import com.daan.pws.protocol.wrappers.Packet63WorldParticles;
import com.daan.pws.protocol.wrappers.Packet63WorldParticles.Particle;

public class Main extends JavaPlugin {

	private static Main instance;
	public PlayerInjection injection;
	public MCProtocol protocol;

	@Override
	public void onEnable() {
		instance = this;

		PacketType.init();

		registerListeners();

		this.injection = new PlayerInjection(this);
		this.protocol = new MCProtocol();

		protocol.addPacketListener(PacketType.Play.Server.WORLD_PARTICLES, new PacketListener() {

			@Override
			public void onPacketEvent(PacketEvent event) {
				Packet63WorldParticles packet = new Packet63WorldParticles(event.getPacket());
				if (packet.getParticle() != Particle.FLAME) {
					packet.setOffsetX(0);
					packet.setOffsetY(0);
					packet.setOffsetZ(0);
					packet.setParticle(Particle.FLAME);
					packet.setAmount(10);
					packet.setSpeed(0.001f);
				}
			}

		});
		Bukkit.getWorld("world").playEffect(null, Effect.CLOUD, 0);
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

}
