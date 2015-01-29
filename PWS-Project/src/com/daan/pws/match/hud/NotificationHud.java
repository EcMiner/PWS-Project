package com.daan.pws.match.hud;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;
import org.getspout.spoutapi.gui.Color;
import org.getspout.spoutapi.gui.GenericGradient;
import org.getspout.spoutapi.gui.GenericLabel;
import org.getspout.spoutapi.gui.GenericTexture;
import org.getspout.spoutapi.gui.RenderPriority;
import org.getspout.spoutapi.gui.Widget;
import org.getspout.spoutapi.gui.WidgetAnchor;
import org.getspout.spoutapi.player.SpoutPlayer;

import com.daan.pws.Main;
import com.daan.pws.match.enums.NotificationPriority;
import com.daan.pws.match.enums.NotificationType;

public class NotificationHud {

	private static Map<String, NotificationHud> stored = new HashMap<String, NotificationHud>();

	public static boolean showAlert(SpoutPlayer player, String notification, int duration, NotificationPriority priority) {
		if (stored.containsKey(player.getName())) {
			NotificationHud hud = stored.get(player.getName());
			if (hud.getPriority().isHigherThan(priority)) {
				return false;
			}
			hud.remove(player);
			stored.remove(player.getName());
		}
		stored.put(player.getName(), new NotificationHud(player, notification, duration, priority));
		return true;
	}

	public static boolean showProgressBar(SpoutPlayer player, String notification, int duration, int timeLeft, int barLength, NotificationPriority priority) {
		if (stored.containsKey(player.getName())) {
			NotificationHud hud = stored.get(player.getName());
			if (hud.getType() == NotificationType.PROGRESS_BAR && hud.getNotification().equalsIgnoreCase(notification)) {
				hud.setPriority(priority);
				hud.updateBarLength(barLength);
				hud.updateTimeLeft(timeLeft);
				hud.updateTimer(player, duration);
				return true;
			}
			if (hud.getPriority().isHigherThan(priority)) {
				return false;
			}
			hud.remove(player);
			stored.remove(player.getName());
		}
		stored.put(player.getName(), new NotificationHud(player, notification, duration, barLength, timeLeft, priority));
		return true;
	}

	private final NotificationType type;
	private NotificationPriority priority;
	private final String notification;
	private final int duration;
	private int barLength = -1;
	private BukkitTask task;
	private final Widget[] widgets;

	private NotificationHud(final SpoutPlayer player, String notification, int duration, NotificationPriority priority) {
		this.type = NotificationType.TEXT;
		this.priority = priority;
		this.notification = notification;
		this.duration = duration;

		GenericGradient background = new GenericGradient(new Color(0, 0, 0, 129));
		background.setAnchor(WidgetAnchor.CENTER_CENTER).shiftXPos(-70).shiftYPos(60).setWidth(150).setHeight(30).setPriority(RenderPriority.Highest);

		GenericTexture texture = new GenericTexture("http://panisme.nl/csgo/others/info.png");
		texture.setAnchor(WidgetAnchor.CENTER_CENTER).shiftXPos(-90).shiftYPos(55).setWidth(40).setHeight(40);

		GenericLabel alertLabel = new GenericLabel("Alert!");
		alertLabel.setAnchor(WidgetAnchor.CENTER_CENTER).shiftYPos(62).shiftXPos(-45);
		alertLabel.setScale(0.8f);
		alertLabel.setTextColor(new Color(232, 58, 58));
		alertLabel.setShadow(false);

		GenericLabel notificationLabel = new GenericLabel(notification);
		notificationLabel.setAnchor(WidgetAnchor.CENTER_CENTER).shiftYPos(70).shiftXPos(-45);
		notificationLabel.setScale(0.9f);
		notificationLabel.setShadow(false);

		player.getMainScreen().attachWidgets(Main.getInstance(), background, texture, alertLabel, notificationLabel);

		this.widgets = new Widget[] { background, alertLabel, notificationLabel, texture };

		this.task = new BukkitRunnable() {

			@Override
			public void run() {
				remove(player);
			}

		}.runTaskLater(Main.getInstance(), duration);
	}

	private NotificationHud(final SpoutPlayer player, String notification, int duration, int barLength, int timeLeft, NotificationPriority priority) {
		this.type = NotificationType.PROGRESS_BAR;
		this.priority = priority;
		this.notification = notification;
		this.duration = duration;
		this.barLength = barLength;

		GenericGradient background = new GenericGradient(new Color(0, 0, 0, 129));
		background.setAnchor(WidgetAnchor.CENTER_CENTER).shiftXPos(-75).shiftYPos(60).setWidth(150).setHeight(30).setPriority(RenderPriority.Highest);

		GenericTexture texture = new GenericTexture("http://panisme.nl/csgo/others/info.png");
		texture.setAnchor(WidgetAnchor.CENTER_CENTER).shiftXPos(-90).shiftYPos(55).setWidth(40).setHeight(40).setPriority(RenderPriority.Lowest);

		GenericLabel notificationLabel = new GenericLabel(notification);
		notificationLabel.setAnchor(WidgetAnchor.CENTER_CENTER).shiftYPos(62).shiftXPos(-45);
		notificationLabel.setScale(0.9f);
		notificationLabel.setShadow(false);

		GenericLabel timeLabel = new GenericLabel("00:" + (timeLeft < 10 ? "0" : "") + timeLeft);
		timeLabel.setAnchor(WidgetAnchor.CENTER_CENTER).shiftYPos(62).shiftXPos(68);
		timeLabel.setAlign(WidgetAnchor.TOP_RIGHT);
		timeLabel.setTextColor(new Color(232, 58, 58));
		timeLabel.setScale(0.9f);
		timeLabel.setShadow(false);

		GenericGradient barBackground = new GenericGradient(new Color(0, 0, 0, 150));
		barBackground.setAnchor(WidgetAnchor.CENTER_CENTER).shiftXPos(-75).shiftYPos(70).setWidth(150).setHeight(6).setPriority(RenderPriority.High);

		GenericGradient bar = new GenericGradient(new Color(209, 57, 59), new Color(174, 73, 71));
		int width = (int) Math.round((double) (126 * (Double.valueOf((barLength / 100) + ""))));
		bar.setAnchor(WidgetAnchor.CENTER_CENTER).shiftXPos(-75).shiftYPos(71).setHeight(4).setWidth(24 + width);

		player.getMainScreen().attachWidgets(Main.getInstance(), background, texture, notificationLabel, timeLabel, barBackground, bar);

		this.widgets = new Widget[] { background, texture, notificationLabel, timeLabel, barBackground, bar };

		this.task = new BukkitRunnable() {

			@Override
			public void run() {
				if (player.isOnline())
					remove(player);
			}

		}.runTaskLater(Main.getInstance(), duration);
	}

	public NotificationType getType() {
		return type;
	}

	public NotificationPriority getPriority() {
		return priority;
	}

	public void setPriority(NotificationPriority priority) {
		this.priority = priority;
	}

	public String getNotification() {
		return notification;
	}

	public int getDuration() {
		return duration;
	}

	public int getBarLength() {
		return barLength;
	}

	public void updateTimeLeft(int timeLeft) {
		if (this.type == NotificationType.PROGRESS_BAR) {
			((GenericLabel) widgets[3]).setText("00:" + (timeLeft < 10 ? "0" : "") + timeLeft);
		}
	}

	public void updateBarLength(int barLength) {
		if (this.type == NotificationType.PROGRESS_BAR) {
			int width = (int) Math.round((double) (126 * (Double.valueOf(barLength + "") / Double.valueOf(100d))));
			widgets[5].setWidth(24 + width);
		}
	}

	public void updateTimer(final SpoutPlayer player, int duration) {
		this.task.cancel();
		this.task = new BukkitRunnable() {

			@Override
			public void run() {
				if (player.isOnline())
					remove(player);
			}

		}.runTaskLater(Main.getInstance(), duration);
	}

	public void remove(SpoutPlayer player) {
		task.cancel();
		for (Widget widget : widgets) {
			player.getMainScreen().removeWidget(widget);
		}
		stored.remove(player.getName());
	}

}
