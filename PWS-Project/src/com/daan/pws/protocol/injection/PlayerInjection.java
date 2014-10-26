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

import org.bukkit.craftbukkit.v1_7_R4.CraftServer;
import org.bukkit.craftbukkit.v1_7_R4.entity.CraftPlayer;
import org.bukkit.entity.Player;

import com.daan.pws.Main;
import com.daan.pws.protocol.PacketEvent;
import com.daan.pws.protocol.PacketReceiveEvent;
import com.daan.pws.protocol.PacketSendEvent;
import com.daan.pws.protocol.PacketType;

public class PlayerInjection {

	private Main plugin;
	private Field mChannel;
	private Field getConsole;
	private Field getServerConnection;
	private Field getF;
	private Map<String, Channel> channels = new HashMap<String, Channel>();
	private List<Channel> injectedChannels = new ArrayList<Channel>();

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

	}

	public final void injectPlayer(final Player p) {
		try {
			EntityPlayer nmsPlayer = ((CraftPlayer) p).getHandle();
			Channel channel = (Channel) this.mChannel.get(nmsPlayer.playerConnection.networkManager);
			this.channels.put(p.getUniqueId().toString(), channel);

			channel.pipeline().addBefore("packet_handler", "MCProtocol", new ChannelDuplexHandler() {

				@Override
				public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
					if (msg instanceof Packet) {
						PacketSendEvent event = new PacketSendEvent((Packet) msg);
						plugin.getServer().getPluginManager().callEvent(event);
						if (event.isCancelled()) {
							return;
						}
					}
					super.write(ctx, msg, promise);
				}

				@Override
				public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
					if (msg instanceof Packet) {
						PacketReceiveEvent event = new PacketReceiveEvent((Packet) msg);
						plugin.getServer().getPluginManager().callEvent(event);

						PacketEvent evt = new PacketEvent((Packet) msg, PacketType.getTypeFromPacketClass(msg.getClass()));
						plugin.protocol.handlePacket((Packet) msg, evt);
						if (evt.isCancelled() || event.isCancelled()) {
							return;
						}
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
}
