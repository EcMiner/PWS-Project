package com.daan.pws.protocol.wrappers;

import net.minecraft.server.v1_7_R4.EnumDifficulty;
import net.minecraft.server.v1_7_R4.EnumGamemode;
import net.minecraft.server.v1_7_R4.Packet;
import net.minecraft.server.v1_7_R4.WorldType;

import org.bukkit.Difficulty;
import org.bukkit.GameMode;

public class Packet1Login extends AbstractPacket {

	public Packet1Login(Packet nmsPacket) {
		super(nmsPacket);
	}

	public int getEntityId() {
		return getField("a");
	}

	public void setEntityId(int value) {
		setField("a", value);
	}

	public boolean isHardcore() {
		return getField("b");
	}

	public void setHardcore(boolean value) {
		setField("b", value);
	}

	public GameMode getGameMode() {
		return fromEnumGamemode((EnumGamemode) getField("c"));
	}

	public void setGameMode(GameMode gamemode) {
		setField("c", fromGameMode(gamemode));
	}

	public Dimension getDimension() {
		return Dimension.fromId((int) getField("d"));
	}

	public void setDimension(Dimension dimension) {
		setField("d", dimension.getId());
	}

	public Difficulty getDifficulty() {
		return fromEnumDifficulty((EnumDifficulty) getField("e"));
	}

	public void setDifficulty(Difficulty difficulty) {
		setField("e", fromDifficulty(difficulty));
	}

	public int getMaxPlayers() {
		return getField("f");
	}

	public void setMaxPlayers(int value) {
		setField("f", value);
	}

	public WorldType getWorldType() {
		return getField("g");
	}

	public void setWorldType(WorldType worldType) {
		setField("g", worldType);
	}

	private Difficulty fromEnumDifficulty(EnumDifficulty difficulty) {
		switch (difficulty) {
		case PEACEFUL:
			return Difficulty.PEACEFUL;
		case EASY:
			return Difficulty.EASY;
		case NORMAL:
			return Difficulty.NORMAL;
		case HARD:
			return Difficulty.HARD;
		default:
			return Difficulty.NORMAL;
		}
	}

	private EnumDifficulty fromDifficulty(Difficulty difficulty) {
		switch (difficulty) {
		case PEACEFUL:
			return EnumDifficulty.PEACEFUL;
		case EASY:
			return EnumDifficulty.EASY;
		case NORMAL:
			return EnumDifficulty.NORMAL;
		case HARD:
			return EnumDifficulty.HARD;
		default:
			return EnumDifficulty.NORMAL;
		}
	}

	private GameMode fromEnumGamemode(EnumGamemode gamemode) {
		switch (gamemode) {
		case NONE:
			return GameMode.SURVIVAL;
		case SURVIVAL:
			return GameMode.SURVIVAL;
		case CREATIVE:
			return GameMode.CREATIVE;
		case ADVENTURE:
			return GameMode.ADVENTURE;
		}
		return GameMode.SURVIVAL;
	}

	private EnumGamemode fromGameMode(GameMode gamemode) {
		switch (gamemode) {
		case SURVIVAL:
			return EnumGamemode.SURVIVAL;
		case CREATIVE:
			return EnumGamemode.CREATIVE;
		case ADVENTURE:
			return EnumGamemode.ADVENTURE;
		}
		return EnumGamemode.NONE;
	}

	public static enum Dimension {

		NETHER(-1), OVERWORLD(0), END(1);

		private int id;

		private Dimension(int id) {
			this.id = id;
		}

		public int getId() {
			return id;
		}

		public static Dimension fromId(int id) {
			switch (id) {
			case -1:
				return NETHER;
			case 0:
				return OVERWORLD;
			case 1:
				return END;
			default:
				return null;
			}
		}

	}

}
