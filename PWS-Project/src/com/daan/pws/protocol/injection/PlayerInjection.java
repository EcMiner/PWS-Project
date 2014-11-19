package com.daan.pws.protocol.injection;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.minecraft.server.v1_7_R4.EntityPlayer;
import net.minecraft.server.v1_7_R4.MinecraftServer;
import net.minecraft.server.v1_7_R4.NetworkManager;
import net.minecraft.server.v1_7_R4.Packet;
import net.minecraft.server.v1_7_R4.ServerConnection;
import net.minecraft.util.io.netty.channel.Channel;
import net.minecraft.util.io.netty.channel.ChannelDuplexHandler;
import net.minecraft.util.io.netty.channel.ChannelHandlerContext;
import net.minecraft.util.io.netty.channel.ChannelPromise;

import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_7_R4.CraftServer;
import org.bukkit.craftbukkit.v1_7_R4.entity.CraftPlayer;
import org.bukkit.entity.Player;

import com.daan.pws.Main;
import com.daan.pws.protocol.PacketEvent;
import com.daan.pws.protocol.PacketManager;

public class PlayerInjection {

	private Main plugin;
	private Field mChannel;
	private Field getConsole;
	private Field getServerConnection;
	private Field getF;
	private Map<String, Channel> channels = new HashMap<String, Channel>();
	private List<Channel> injectedChannels = new ArrayList<Channel>();

	@SuppressWarnings("deprecation")
	public PlayerInjection(Main plugin) {
		this.plugin = plugin;

		try {
			this.mChannel = NetworkManager.class.getDeclaredField("m");
			this.mChannel.setAccessible(true);

			this.getConsole = CraftServer.class.getDeclaredField("console");
			this.getConsole.setAccessible(true);

			this.getServerConnection = MinecraftServer.class.getDeclaredField("p");
			this.getServerConnection.setAccessible(true);

			this.getF = ServerConnection.class.getDeclaredField("f");
			this.getF.setAccessible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}

		for (Player o : Bukkit.getOnlinePlayers()) {
			injectPlayer(o);
		}
	}

	public final void injectPlayer(final Player p) {
		try {
			EntityPlayer nmsPlayer = ((CraftPlayer) p).getHandle();
			Channel channel = (Channel) this.mChannel.get(nmsPlayer.playerConnection.networkManager);
			this.channels.put(p.getUniqueId().toString(), channel);
			this.injectedChannels.add(channel);

			channel.pipeline().addBefore("packet_handler", "MCProtocol", new ChannelDuplexHandler() {

				@Override
				public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
					try {
						if (msg instanceof Packet) {

							PacketEvent evt = new PacketEvent((Packet) msg, p, PacketManager.getPacketType(((Packet) msg).getClass()));
							plugin.protocol.handlePacket((Packet) msg, evt);
							if (evt.isCancelled()) {
								return;
							}
						}
					} catch (Exception e) {
						System.out.println("An error occured while reading the outgoing packets!\n" + e.getMessage());
					}
					super.write(ctx, msg, promise);
				}

				@Override
				public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
					try {
						if (msg instanceof Packet) {
							PacketEvent evt = new PacketEvent((Packet) msg, p, PacketManager.getPacketType(((Packet) msg).getClass()));
							plugin.protocol.handlePacket((Packet) msg, evt);
							if (evt.isCancelled()) {
								return;
							}
						}
					} catch (Exception e) {
						System.out.println("An error occured while reading the incoming packets!\n" + e.getMessage());
					}
					super.channelRead(ctx, msg);
				}

			});
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public final void injectConnection(NetworkManager manager) {
		try {
			Channel channel = (Channel) this.mChannel.get(manager);

			if (this.injectedChannels.contains(channel)) {
				return;
			}
			this.injectedChannels.add(channel);

			channel.pipeline().addBefore("packet_handler", "MCProtocol PING", new ChannelDuplexHandler() {

				@Override
				public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
					super.write(ctx, msg, promise);
				}

				@Override
				public void read(ChannelHandlerContext ctx) throws Exception {
					super.read(ctx);
				}

			});

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public final void disable() {
		for (Channel channel : injectedChannels) {
			channel.pipeline().remove("MCProtocol");
			channel.pipeline().remove("MCProtocol Ping");
		}
	}

}
