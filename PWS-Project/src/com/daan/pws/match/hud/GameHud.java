package com.daan.pws.match.hud;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.getspout.spoutapi.gui.Color;
import org.getspout.spoutapi.gui.GenericGradient;
import org.getspout.spoutapi.gui.GenericLabel;
import org.getspout.spoutapi.gui.GenericTexture;
import org.getspout.spoutapi.gui.RenderPriority;
import org.getspout.spoutapi.gui.WidgetAnchor;
import org.getspout.spoutapi.player.SpoutPlayer;

import com.daan.pws.Main;

public class GameHud {

	private static final Map<String, GameHud> gameHuds = new HashMap<String, GameHud>();

	public static void removeGameHud(SpoutPlayer player) {
		if (gameHuds.containsKey(player.getName())) {
			gameHuds.get(player.getName()).remove(player);
		}
	}

	public static void updateBombActive(SpoutPlayer player, boolean active) {
		if (!gameHuds.containsKey(player.getName())) {
			new GameHud(player, 0, 0, 0, null, null).setBombActive(active);
		} else {
			gameHuds.get(player.getName()).setBombActive(active);
		}
	}

	public static void updateTimeVisibility(SpoutPlayer player, boolean visible) {
		if (!gameHuds.containsKey(player.getName())) {
			new GameHud(player, 0, 0, 0, null, null).setTimeVisibility(visible);
		} else {
			gameHuds.get(player.getName()).setTimeVisibility(visible);
		}
	}

	public static void updateGameHud(SpoutPlayer player, int timeInSeconds, int ctWins, int tWins, List<String> terrorists, List<String> counter_terrorists) {
		if (!gameHuds.containsKey(player.getName())) {
			new GameHud(player, timeInSeconds, ctWins, tWins, terrorists, counter_terrorists);
		} else {
			gameHuds.get(player.getName()).update(timeInSeconds, ctWins, tWins);
		}
	}

	public static void updateTime(SpoutPlayer player, int timeInSeconds) {
		if (!gameHuds.containsKey(player.getName())) {
			new GameHud(player, timeInSeconds, 0, 0, null, null);
		} else {
			gameHuds.get(player.getName()).updateTime(timeInSeconds);
		}
	}

	public static void updateCtWins(SpoutPlayer player, int ctWins) {
		if (!gameHuds.containsKey(player.getName())) {
			new GameHud(player, 0, ctWins, 0, null, null);
		} else {
			gameHuds.get(player.getName()).updateCtWins(ctWins);
		}
	}

	public static void updateTWins(SpoutPlayer player, int tWins) {
		if (!gameHuds.containsKey(player.getName())) {
			new GameHud(player, 0, 0, tWins, null, null);
		} else {
			gameHuds.get(player.getName()).updateTWins(tWins);
		}
	}

	public static void updateIsPlayAlive(SpoutPlayer player, String playerName, boolean alive) {
		if (!gameHuds.containsKey(player.getName())) {
			new GameHud(player, 0, 0, 0, null, null).updateIsPlayerAlive(playerName, alive);
		} else {
			gameHuds.get(player.getName()).updateIsPlayerAlive(playerName, alive);
		}
	}

	public static void resetAllPlayers(SpoutPlayer player) {
		if (!gameHuds.containsKey(player.getName())) {
			new GameHud(player, 0, 0, 0, null, null);
		} else {
			gameHuds.get(player.getName()).resetPlayers();
		}
	}

	private GenericGradient timeBackground;
	private GenericLabel time;

	private GenericGradient ctWinsBackground;
	private GenericLabel ctWins;

	private GenericGradient tWinsBackground;
	private GenericLabel tWins;

	private GenericTexture bomb;

	private HashMap<String, GenericTexture> players = new HashMap<String, GenericTexture>();

	private GameHud(SpoutPlayer player, int timeInSeconds, int ctWinsAmount, int tWinsAmount, List<String> terrorists, List<String> counter_terrorists) {
		timeBackground = new GenericGradient(new Color(0, 0, 0, 129));
		timeBackground.setAnchor(WidgetAnchor.TOP_CENTER).shiftXPos(-20).shiftYPos(1).setWidth(40).setHeight(12).setPriority(RenderPriority.High);

		time = new GenericLabel(formatTime(timeInSeconds));
		time.setAnchor(WidgetAnchor.TOP_CENTER);
		time.shiftYPos(2).shiftXPos((int) -((time.getText().length() * 6) / 2));
		time.setShadow(false);
		time.setScale(1.3f);
		time.setTextColor(timeInSeconds <= 10 ? new Color(255, 113, 111) : new Color(255, 255, 255));

		bomb = new GenericTexture("http://www.panisme.nl/csgo/icons/bomb_planted_icon.png");
		bomb.setAnchor(WidgetAnchor.TOP_CENTER);
		bomb.setWidth(16).setHeight(10);
		bomb.shiftXPos(-8).shiftYPos(2);
		bomb.setVisible(false);

		ctWinsBackground = new GenericGradient(new Color(0, 0, 0, 129));
		ctWinsBackground.setAnchor(WidgetAnchor.TOP_CENTER).shiftXPos(-20).shiftYPos(14).setHeight(9).setWidth(19).setPriority(RenderPriority.High);

		ctWins = new GenericLabel(ctWinsAmount + "");
		ctWins.setAnchor(WidgetAnchor.TOP_CENTER);
		ctWins.shiftXPos(-(10 + ((ctWins.getText().length() * 6) / 2))).shiftYPos(15);
		ctWins.setTextColor(new Color(129, 161, 230));
		ctWins.setShadow(false);

		tWinsBackground = new GenericGradient(new Color(0, 0, 0, 129));
		tWinsBackground.setAnchor(WidgetAnchor.TOP_CENTER).shiftYPos(14).setWidth(20).setHeight(9).setPriority(RenderPriority.High);

		tWins = new GenericLabel(tWinsAmount + "");
		tWins.setAnchor(WidgetAnchor.TOP_CENTER);
		tWins.shiftXPos(10 - ((tWins.getText().length() * 6) / 2)).shiftYPos(15);
		tWins.setTextColor(new Color(248, 217, 89));
		tWins.setShadow(false);

		if (terrorists != null) {
			int index = 0;
			for (String name : terrorists) {
				GenericTexture texture = new GenericTexture("https://minotar.net/helm/" + name + ".png");
				texture.setAnchor(WidgetAnchor.TOP_CENTER);

				int shiftX = 25 + (index * 21) + (index * 2);
				texture.shiftXPos(shiftX).shiftYPos(1);
				texture.setWidth(21).setHeight(21);
				player.getMainScreen().attachWidget(Main.getInstance(), texture);
				players.put(name.toLowerCase(), texture);
				index++;
			}
		}
		if (counter_terrorists != null) {
			int index = 0;
			for (String name : counter_terrorists) {
				GenericTexture texture = new GenericTexture("https://minotar.net/helm/" + name + ".png");
				texture.setAnchor(WidgetAnchor.TOP_CENTER);

				int shiftX = 45 + (index * 21) + (index * 4);
				texture.shiftXPos(-shiftX).shiftYPos(1);
				texture.setWidth(21).setHeight(21);
				player.getMainScreen().attachWidget(Main.getInstance(), texture);
				players.put(name.toLowerCase(), texture);
				index++;
			}
		}

		player.getMainScreen().attachWidgets(Main.getInstance(), time, bomb, timeBackground, ctWinsBackground, ctWins, tWinsBackground, tWins);
		gameHuds.put(player.getName(), this);
	}

	public void resetPlayers() {
		for (String name : players.keySet()) {
			updateIsPlayerAlive(name, true);
		}
	}

	public void updateIsPlayerAlive(String name, boolean alive) {
		if (players.containsKey(name.toLowerCase())) {
			GenericTexture texture = players.get(name.toLowerCase());
			texture.setUrl(alive ? "https://minotar.net/helm/" + name + ".png" : "http://panisme.nl/csgo/icons/player_death_icon.png");
		}
	}

	public void update(int timeInSeconds, int ctWins, int tWins) {
		updateTime(timeInSeconds);
		updateCtWins(ctWins);
		updateTWins(tWins);
	}

	public void updateTime(int timeInSeconds) {
		time.setText(formatTime(timeInSeconds));
		time.setTextColor(timeInSeconds <= 10 ? new Color(255, 113, 111) : new Color(255, 255, 255));
	}

	public void setTimeVisibility(boolean visible) {
		time.setVisible(visible);
	}

	public void setBombActive(boolean active) {
		bomb.setVisible(active);
	}

	public void updateCtWins(int ctWins) {
		this.ctWins.setText(ctWins + "");
	}

	public void updateTWins(int tWins) {
		this.tWins.setText(tWins + "");
	}

	private String formatTime(int timeInSeconds) {
		int minutes = timeInSeconds >= 60 ? (timeInSeconds - (timeInSeconds % 60)) / 60 : 0;
		int seconds = timeInSeconds - (minutes * 60);
		return minutes + ":" + (seconds < 10 ? "0" : "") + seconds;
	}

	public void remove(SpoutPlayer player) {
		player.getMainScreen().removeWidget(time).removeWidget(timeBackground).removeWidget(bomb).removeWidget(ctWinsBackground).removeWidget(ctWins).removeWidget(tWinsBackground).removeWidget(tWins);
		for (GenericTexture texture : players.values()) {
			player.getMainScreen().removeWidget(texture);
		}
	}

}
