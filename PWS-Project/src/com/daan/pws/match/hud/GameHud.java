package com.daan.pws.match.hud;

import java.util.HashMap;
import java.util.Map;

import org.getspout.spoutapi.gui.Color;
import org.getspout.spoutapi.gui.GenericGradient;
import org.getspout.spoutapi.gui.GenericLabel;
import org.getspout.spoutapi.gui.RenderPriority;
import org.getspout.spoutapi.gui.WidgetAnchor;
import org.getspout.spoutapi.player.SpoutPlayer;

import com.daan.pws.Main;

public class GameHud {

	private static final Map<String, GameHud> gameHuds = new HashMap<String, GameHud>();

	public static void updateGameHud(SpoutPlayer player, int timeInSeconds, int inRound, int ctWins, int tWins) {
		if (!gameHuds.containsKey(player.getName())) {
			new GameHud(player, timeInSeconds, inRound, ctWins, tWins);
		} else {
			gameHuds.get(player.getName()).update(player, timeInSeconds, inRound, ctWins, tWins);
		}
	}

	public static void updateTime(SpoutPlayer player, int timeInSeconds) {
		
	}

	private GenericLabel time;
	private GenericLabel rounds;
	private GenericLabel ctLabel;
	private GenericLabel tLabel;
	private GenericGradient background;

	private GameHud(SpoutPlayer player, int timeInSeconds, int inRound, int ctWins, int tWins) {
		time = new GenericLabel();
		time.setAlign(WidgetAnchor.TOP_RIGHT);
		time.setAnchor(WidgetAnchor.TOP_CENTER);
		time.shiftYPos(2).shiftXPos(12);
		time.setText(formatTime(timeInSeconds));
		time.setShadow(false);

		rounds = new GenericLabel();
		rounds.setAnchor(WidgetAnchor.TOP_CENTER);
		rounds.setAlign(WidgetAnchor.TOP_CENTER);
		rounds.shiftYPos(11);
		rounds.setScale(0.7f);
		rounds.setText("Round " + inRound + "/30");
		rounds.setShadow(false);
		rounds.shiftXPos((rounds.getText().length() * 2) / 2);

		ctLabel = new GenericLabel();
		ctLabel.setAlign(WidgetAnchor.TOP_RIGHT);
		ctLabel.setAnchor(WidgetAnchor.TOP_CENTER);
		ctLabel.shiftXPos(-85).shiftYPos(3);
		ctLabel.setScale(1.4f);
		ctLabel.setText("COUNTER-TERRORISTS  " + ctWins);
		ctLabel.setShadow(false);
		ctLabel.setTextColor(new Color(129, 161, 230));

		tLabel = new GenericLabel();
		tLabel.setAnchor(WidgetAnchor.TOP_CENTER);
		tLabel.shiftXPos(40).shiftYPos(3);
		tLabel.setScale(1.4f);
		tLabel.setText(tWins + "  TERRORISTS");
		tLabel.setShadow(false);
		tLabel.setTextColor(new Color(248, 217, 89));

		background = new GenericGradient(new Color(0, 0, 0, 75));
		background.setX(0).setY(0);
		background.setHeight(rounds.getY() + 2).setWidth(player.getMainScreen().getWidth());
		background.setPriority(RenderPriority.High);

		player.getMainScreen().attachWidgets(Main.getInstance(), background, time, rounds, ctLabel, tLabel);
		gameHuds.put(player.getName(), this);
	}

	public void update(SpoutPlayer player, int timeInSeconds, int inRound, int ctWins, int tWins) {
		time.setText(formatTime(timeInSeconds));
		rounds.setText("Round " + inRound + "/30");
		ctLabel.setText("COUNTER-TERRORISTS  " + ctWins);
		tLabel.setText(tWins + "  TERRORISTS");
	}

	private String formatTime(int timeInSeconds) {
		int minutes = timeInSeconds >= 60 ? (timeInSeconds - (timeInSeconds % 60)) / 60 : 0;
		int seconds = timeInSeconds - (minutes * 60);
		return minutes + ":" + (seconds < 10 ? "0" : "") + seconds;
	}

}
