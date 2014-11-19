package com.daan.pws.protocol;

import net.minecraft.server.v1_7_R4.Packet;

import com.daan.pws.utilities.StringUtil;

public class PacketType {

	public static final void init() {
		Handshake.Client.getSender();
		Login.Client.getSender();
		Login.Server.getSender();
		Status.Client.getSender();
		Status.Server.getSender();
		Play.Client.getSender();
		Play.Server.getSender();
	}

	private ProtocolType protocolType;
	private SenderType senderType;
	private int mcPacketId;
	private int simpleId;
	private String packetName;
	private Class<? extends net.minecraft.server.v1_7_R4.Packet> packetClass;

	@SuppressWarnings("unchecked")
	public PacketType(String packetName, ProtocolType protocolType, SenderType senderType, int mcPacketId, int simpleId) {
		this.protocolType = protocolType;
		this.senderType = senderType;
		this.mcPacketId = mcPacketId;
		this.simpleId = simpleId;
		this.packetName = packetName;

		StringBuilder sb = new StringBuilder();
		sb.append("net.minecraft.server.v1_7_R4.Packet" + StringUtil.capitalize(protocolType.name().toLowerCase(), 0, 1) + (senderType == SenderType.CLIENT ? "In" : "Out"));
		String[] array = packetName.split("_");
		for (String string : array) {
			sb.append(StringUtil.capitalize(string.toLowerCase(), 0, 1));
		}
		try {
			this.packetClass = (Class<? extends Packet>) Class.forName(sb.toString());
			System.out.println("======>>>" + packetClass.getCanonicalName());
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

		PacketManager.addPacketType(this);
	}

	public ProtocolType getProtocolType() {
		return protocolType;
	}

	public SenderType getSenderType() {
		return senderType;
	}

	public int getMcPacketId() {
		return mcPacketId;
	}

	public int getSimpleId() {
		return simpleId;
	}

	public String getPacketName() {
		return packetName;
	}

	public Class<? extends net.minecraft.server.v1_7_R4.Packet> getPacketClass() {
		return packetClass;
	}

	public static enum SenderType {

		CLIENT, SERVER;

	}

	public static enum ProtocolType {

		HANDSHAKING, PLAY, STATUS, LOGIN;

	}

	public static class Handshake {

		private static final ProtocolType protocol = ProtocolType.HANDSHAKING;

		public static class Client {

			private static final SenderType sender = SenderType.CLIENT;

			public static final PacketType SET_PROTOCOL = new PacketType("SET_PROTOCOL", protocol, sender, 0x00, 0);

			public static final SenderType getSender() {
				return sender;
			}

		}

		public static final ProtocolType getProtocol() {
			return protocol;
		}

	}

	public static class Play {

		private static final ProtocolType protocol = ProtocolType.PLAY;

		public static class Server {

			private static final SenderType sender = SenderType.SERVER;

			public static final PacketType KEEP_ALIVE = new PacketType("KEEP_ALIVE", protocol, sender, 0x00, 0);
			public static final PacketType LOGIN = new PacketType("LOGIN", protocol, sender, 0x01, 1);
			public static final PacketType CHAT = new PacketType("CHAT", protocol, sender, 0x02, 2);
			public static final PacketType UPDATE_TIME = new PacketType("UPDATE_TIME", protocol, sender, 0x03, 3);
			public static final PacketType ENTITY_EQUIPMENT = new PacketType("ENTITY_EQUIPMENT", protocol, sender, 0x04, 4);
			public static final PacketType SPAWN_POSITION = new PacketType("SPAWN_POSITION", protocol, sender, 0x05, 5);
			public static final PacketType UPDATE_HEALTH = new PacketType("UPDATE_HEALTH", protocol, sender, 0x06, 6);
			public static final PacketType RESPAWN = new PacketType("RESPAWN", protocol, sender, 0x07, 7);
			public static final PacketType POSITION = new PacketType("POSITION", protocol, sender, 0x08, 8);
			public static final PacketType HELD_ITEM_SLOT = new PacketType("HELD_ITEM_SLOT", protocol, sender, 0x09, 9);
			public static final PacketType BED = new PacketType("BED", protocol, sender, 0x0A, 10);
			public static final PacketType ANIMATION = new PacketType("ANIMATION", protocol, sender, 0x0B, 11);
			public static final PacketType NAMED_ENTITY_SPAWN = new PacketType("NAMED_ENTITY_SPAWN", protocol, sender, 0x0C, 12);
			public static final PacketType COLLECT = new PacketType("COLLECT", protocol, sender, 0x0D, 13);
			public static final PacketType SPAWN_ENTITY = new PacketType("SPAWN_ENTITY", protocol, sender, 0x0E, 14);
			public static final PacketType SPAWN_ENTITY_LIVING = new PacketType("SPAWN_ENTITY_LIVING", protocol, sender, 0x0F, 15);
			public static final PacketType SPAWN_ENTITY_PAINTING = new PacketType("SPAWN_ENTITY_PAINTING", protocol, sender, 0x10, 16);
			public static final PacketType SPAWN_ENTITY_EXPERIENCE_ORB = new PacketType("SPAWN_ENTITY_EXPERIENCE_ORB", protocol, sender, 0x11, 17);
			public static final PacketType ENTITY_VELOCITY = new PacketType("ENTITY_VELOCITY", protocol, sender, 0x12, 18);
			public static final PacketType ENTITY_DESTROY = new PacketType("ENTITY_DESTROY", protocol, sender, 0x13, 19);
			public static final PacketType ENTITY = new PacketType("ENTITY", protocol, sender, 0x14, 20);
			public static final PacketType REL_ENTITY_MOVE = new PacketType("REL_ENTITY_MOVE", protocol, sender, 0x15, 21);
			public static final PacketType ENTITY_LOOK = new PacketType("ENTITY_LOOK", protocol, sender, 0x16, 22);
			public static final PacketType REL_ENTITY_MOVE_LOOK = new PacketType("REL_ENTITY_MOVE_LOOK", protocol, sender, 0x17, 23);
			public static final PacketType ENTITY_TELEPORT = new PacketType("ENTITY_TELEPORT", protocol, sender, 0x18, 24);
			public static final PacketType ENTITY_HEAD_ROTATION = new PacketType("ENTITY_HEAD_ROTATION", protocol, sender, 0x19, 25);
			public static final PacketType ENTITY_STATUS = new PacketType("ENTITY_STATUS", protocol, sender, 0x1A, 26);
			public static final PacketType ATTACH_ENTITY = new PacketType("ATTACH_ENTITY", protocol, sender, 0x1B, 27);
			public static final PacketType ENTITY_METADATA = new PacketType("ENTITY_METADATA", protocol, sender, 0x1C, 28);
			public static final PacketType ENTITY_EFFECT = new PacketType("ENTITY_EFFECT", protocol, sender, 0x1D, 29);
			public static final PacketType REMOVE_ENTITY_EFFECT = new PacketType("REMOVE_ENTITY_EFFECT", protocol, sender, 0x1E, 30);
			public static final PacketType EXPERIENCE = new PacketType("EXPERIENCE", protocol, sender, 0x1F, 31);
			public static final PacketType UPDATE_ATTRIBUTES = new PacketType("UPDATE_ATTRIBUTES", protocol, sender, 0x20, 32);
			public static final PacketType MAP_CHUNK = new PacketType("MAP_CHUNK", protocol, sender, 0x21, 33);
			public static final PacketType MULTI_BLOCK_CHANGE = new PacketType("MULTI_BLOCK_CHANGE", protocol, sender, 0x22, 34);
			public static final PacketType BLOCK_CHANGE = new PacketType("BLOCK_CHANGE", protocol, sender, 0x23, 35);
			public static final PacketType BLOCK_ACTION = new PacketType("BLOCK_ACTION", protocol, sender, 0x24, 36);
			public static final PacketType BLOCK_BREAK_ANIMATION = new PacketType("BLOCK_BREAK_ANIMATION", protocol, sender, 0x25, 37);
			public static final PacketType MAP_CHUNK_BULK = new PacketType("MAP_CHUNK_BULK", protocol, sender, 0x26, 38);
			public static final PacketType EXPLOSION = new PacketType("EXPLOSION", protocol, sender, 0x27, 39);
			public static final PacketType WORLD_EVENT = new PacketType("WORLD_EVENT", protocol, sender, 0x28, 40);
			public static final PacketType NAMED_SOUND_EFFECT = new PacketType("NAMED_SOUND_EFFECT", protocol, sender, 0x29, 41);
			public static final PacketType WORLD_PARTICLES = new PacketType("WORLD_PARTICLES", protocol, sender, 0x2A, 42);
			public static final PacketType GAME_STATE_CHANGE = new PacketType("GAME_STATE_CHANGE", protocol, sender, 0x2B, 43);
			public static final PacketType SPAWN_ENTITY_WEATHER = new PacketType("SPAWN_ENTITY_WEATHER", protocol, sender, 0x2C, 44);
			public static final PacketType OPEN_WINDOW = new PacketType("OPEN_WINDOW", protocol, sender, 0x2D, 45);
			public static final PacketType CLOSE_WINDOW = new PacketType("CLOSE_WINDOW", protocol, sender, 0x2E, 46);
			public static final PacketType SET_SLOT = new PacketType("SET_SLOT", protocol, sender, 0x2F, 47);
			public static final PacketType WINDOW_ITEMS = new PacketType("WINDOW_ITEMS", protocol, sender, 0x30, 48);
			public static final PacketType WINDOW_DATA = new PacketType("WINDOW_DATA", protocol, sender, 0x31, 49);
			public static final PacketType TRANSACTION = new PacketType("TRANSACTION", protocol, sender, 0x32, 50);
			public static final PacketType UPDATE_SIGN = new PacketType("UPDATE_SIGN", protocol, sender, 0x33, 51);
			public static final PacketType MAP = new PacketType("MAP", protocol, sender, 0x34, 52);
			public static final PacketType TILE_ENTITY_DATA = new PacketType("TILE_ENTITY_DATA", protocol, sender, 0x35, 53);
			public static final PacketType OPEN_SIGN_EDITOR = new PacketType("OPEN_SIGN_EDITOR", protocol, sender, 0x36, 54);
			public static final PacketType STATISTIC = new PacketType("STATISTIC", protocol, sender, 0x37, 55);
			public static final PacketType PLAYER_INFO = new PacketType("PLAYER_INFO", protocol, sender, 0x38, 56);
			public static final PacketType ABILITIES = new PacketType("ABILITIES", protocol, sender, 0x39, 57);
			public static final PacketType TAB_COMPLETE = new PacketType("TAB_COMPLETE", protocol, sender, 0x3A, 58);
			public static final PacketType SCOREBOARD_OBJECTIVE = new PacketType("SCOREBOARD_OBJECTIVE", protocol, sender, 0x3B, 59);
			public static final PacketType SCOREBOARD_SCORE = new PacketType("SCOREBOARD_SCORE", protocol, sender, 0x3C, 60);
			public static final PacketType SCOREBOARD_DISPLAY_OBJECTIVE = new PacketType("SCOREBOARD_DISPLAY_OBJECTIVE", protocol, sender, 0x3D, 61);
			public static final PacketType SCOREBOARD_TEAM = new PacketType("SCOREBOARD_TEAM", protocol, sender, 0x3E, 62);
			public static final PacketType CUSTOM_PAYLOAD = new PacketType("CUSTOM_PAYLOAD", protocol, sender, 0x3F, 63);
			public static final PacketType KICK_DISCONNECT = new PacketType("KICK_DISCONNECT", protocol, sender, 0x40, 64);

			public static SenderType getSender() {
				return sender;
			}

		}

		public static class Client {

			private static final SenderType sender = SenderType.CLIENT;

			public static final PacketType KEEP_ALIVE = new PacketType("KEEP_ALIVE", protocol, sender, 0x00, 0);
			public static final PacketType CHAT = new PacketType("CHAT", protocol, sender, 0x01, 1);
			public static final PacketType USE_ENTITY = new PacketType("USE_ENTITY", protocol, sender, 0x02, 2);
			public static final PacketType FLYING = new PacketType("FLYING", protocol, sender, 0x03, 3);
			public static final PacketType POSITION = new PacketType("POSITION", protocol, sender, 0x04, 4);
			public static final PacketType LOOK = new PacketType("LOOK", protocol, sender, 0x05, 5);
			public static final PacketType POSITION_LOOK = new PacketType("POSITION_LOOK", protocol, sender, 0x06, 6);
			public static final PacketType BLOCK_DIG = new PacketType("BLOCK_DIG", protocol, sender, 0x07, 7);
			public static final PacketType BLOCK_PLACE = new PacketType("BLOCK_PLACE", protocol, sender, 0x08, 8);
			public static final PacketType HELD_ITEM_SLOT = new PacketType("HELD_ITEM_SLOT", protocol, sender, 0x09, 9);
			public static final PacketType ARM_ANIMATION = new PacketType("ARM_ANIMATION", protocol, sender, 0x0A, 10);
			public static final PacketType ENTITY_ACTION = new PacketType("ENTITY_ACTION", protocol, sender, 0x0B, 11);
			public static final PacketType STEER_VEHICLE = new PacketType("STEER_VEHICLE", protocol, sender, 0x0C, 12);
			public static final PacketType CLOSE_WINDOW = new PacketType("CLOSE_WINDOW", protocol, sender, 0x0D, 13);
			public static final PacketType WINDOW_CLICK = new PacketType("WINDOW_CLICK", protocol, sender, 0x0E, 14);
			public static final PacketType TRANSACTION = new PacketType("TRANSACTION", protocol, sender, 0x0F, 15);
			public static final PacketType SET_CREATIVE_SLOT = new PacketType("SET_CREATIVE_SLOT", protocol, sender, 0x10, 16);
			public static final PacketType ENCHANT_ITEM = new PacketType("ENCHANT_ITEM", protocol, sender, 0x11, 17);
			public static final PacketType UPDATE_SIGN = new PacketType("UPDATE_SIGN", protocol, sender, 0x12, 18);
			public static final PacketType ABILITIES = new PacketType("ABILITIES", protocol, sender, 0x13, 19);
			public static final PacketType TAB_COMPLETE = new PacketType("TAB_COMPLETE", protocol, sender, 0x14, 20);
			public static final PacketType SETTINGS = new PacketType("SETTINGS", protocol, sender, 0x05, 21);
			public static final PacketType CLIENT_COMMAND = new PacketType("CLIENT_COMMAND", protocol, sender, 0x16, 22);
			public static final PacketType CUSTOM_PAYLOAD = new PacketType("CUSTOM_PAYLOAD", protocol, sender, 0x07, 23);

			public static SenderType getSender() {
				return sender;
			}

		}

		public static ProtocolType getProtocol() {
			return protocol;
		}

	}

	public static class Status {

		private static final ProtocolType protocol = ProtocolType.STATUS;

		public static class Server {

			private static final SenderType sender = SenderType.SERVER;

			public static final PacketType SERVER_INFO = new PacketType("SERVER_INFO", protocol, sender, 0x00, 0);
			public static final PacketType PONG = new PacketType("PONG", protocol, sender, 0x01, 1);

			public static SenderType getSender() {
				return sender;
			}

		}

		public static class Client {

			private static final SenderType sender = SenderType.CLIENT;

			public static final PacketType START = new PacketType("START", protocol, sender, 0x00, 0);
			public static final PacketType PING = new PacketType("PING", protocol, sender, 0x01, 1);

			public static SenderType getSender() {
				return sender;
			}

		}

		public static ProtocolType getProtocol() {
			return protocol;
		}

	}

	public static class Login {

		private static final ProtocolType protocol = ProtocolType.LOGIN;

		public static class Server {

			private static final SenderType sender = SenderType.SERVER;

			public static final PacketType DISCONNECT = new PacketType("DISCONNECT", protocol, sender, 0x00, 0);
			public static final PacketType ENCRYPTION_BEGIN = new PacketType("ENCRYPTION_BEGIN", protocol, sender, 0x01, 1);
			public static final PacketType SUCCESS = new PacketType("SUCCESS", protocol, sender, 0x02, 2);

			public static SenderType getSender() {
				return sender;
			}

		}

		public static class Client {

			private static final SenderType sender = SenderType.CLIENT;

			public static final PacketType START = new PacketType("START", protocol, sender, 0x00, 0);
			public static final PacketType ENCRYPTION_BEGIN = new PacketType("ENCRYPTION_BEGIN", protocol, sender, 0x01, 1);

			public static SenderType getSender() {
				return sender;
			}

		}

		public static ProtocolType getProtocol() {
			return protocol;
		}

	}

}