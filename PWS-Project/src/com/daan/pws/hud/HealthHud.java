package com.daan.pws.hud;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.Bukkit;
import org.getspout.spoutapi.SpoutManager;
import org.getspout.spoutapi.gui.Color;
import org.getspout.spoutapi.gui.GenericGradient;
import org.getspout.spoutapi.gui.GenericLabel;
import org.getspout.spoutapi.gui.GenericRectangle;
import org.getspout.spoutapi.gui.RenderPriority;
import org.getspout.spoutapi.gui.WidgetAnchor;
import org.getspout.spoutapi.player.SpoutPlayer;

import com.daan.pws.Main;

@SuppressWarnings("deprecation")
public class HealthHud {

	private static Map<String, HealthHud> healthStorage = new HashMap<String, HealthHud>();

	public static void updateHealth(SpoutPlayer player, int health) {
		if (healthStorage.containsKey(player.getName())) {
			healthStorage.get(player.getName()).setHealth(health);
		} else {
			healthStorage.put(player.getName(), new HealthHud(player, health));
		}
	}

	public static void removeHealth(SpoutPlayer player) {
		if (healthStorage.containsKey(player.getName())) {
			healthStorage.get(player.getName()).remove(player);
			healthStorage.remove(player.getName());
		}
	}

	public static void onDisable() {
		for (String name : healthStorage.keySet()) {
			SpoutPlayer player;
			if ((player = SpoutManager.getPlayer(Bukkit.getPlayer(name))) != null) {
				healthStorage.get(name).remove(player);
			}
		}
	}

	public static int getHealth(SpoutPlayer player) {
		return healthStorage.containsKey(player.getName()) ? healthStorage.get(player.getName()).getHealth() : 100;
	}

	private GenericLabel healthLabel;
	private GenericRectangle healthBar;

	private GenericRectangle healthLeft;
	private GenericRectangle healthRight;
	private GenericRectangle healthTop;
	private GenericRectangle healthBottom;

	private GenericGradient background;

	private int health;

	private HealthHud(SpoutPlayer player, int health) {
		this.health = health;
		healthLabel = new GenericLabel();
		healthLabel.setAnchor(WidgetAnchor.BOTTOM_LEFT);
		healthLabel.shiftXPos(10).shiftYPos(-25);
		healthLabel.setScale(1.4f);
		healthLabel.setWidth(150).setHeight(50);
		healthLabel.setTextColor(new Color(154, 163, 116));
		healthLabel.setText("+ " + Math.round(health));
		healthLabel.setShadow(false);

		healthBar = new GenericRectangle(new Color(154, 163, 116));
		healthBar.setAnchor(WidgetAnchor.BOTTOM_LEFT);
		healthBar.shiftXPos(55);
		healthBar.shiftYPos(-20);
		healthBar.setWidth(health / 3);
		healthBar.setHeight(3);

		healthLeft = new GenericRectangle(new Color(125, 132, 95));
		healthLeft.setAnchor(WidgetAnchor.BOTTOM_LEFT);
		healthLeft.shiftXPos(54);
		healthLeft.shiftYPos(-21);
		healthLeft.setWidth(1);
		healthLeft.setHeight(5);
		healthLeft.setPriority(RenderPriority.High);

		healthRight = new GenericRectangle(new Color(125, 132, 95));
		healthRight.setAnchor(WidgetAnchor.BOTTOM_LEFT);
		healthRight.shiftXPos(88);
		healthRight.shiftYPos(-21);
		healthRight.setWidth(1);
		healthRight.setHeight(5);
		healthRight.setPriority(RenderPriority.High);

		healthBottom = new GenericRectangle(new Color(125, 132, 95));
		healthBottom.setAnchor(WidgetAnchor.BOTTOM_LEFT);
		healthBottom.shiftXPos(54);
		healthBottom.shiftYPos(-17);
		healthBottom.setWidth(35);
		healthBottom.setHeight(1);
		healthBottom.setPriority(RenderPriority.High);

		healthTop = new GenericRectangle(new Color(125, 132, 95));
		healthTop.setAnchor(WidgetAnchor.BOTTOM_LEFT);
		healthTop.shiftXPos(54);
		healthTop.shiftYPos(-21);
		healthTop.setWidth(35);
		healthTop.setHeight(1);
		healthTop.setPriority(RenderPriority.High);

		background = new GenericGradient(new Color(0, 0, 0, 75));
		background.setAnchor(WidgetAnchor.BOTTOM_LEFT).shiftYPos(-28).setHeight(17).setWidth(90).setPriority(RenderPriority.Highest);

		player.getMainScreen().attachWidgets(Main.getInstance(), background, healthTop, healthBottom, healthRight, healthLeft, healthBar, healthLabel);
	}

	public void remove(SpoutPlayer player) {
		player.getMainScreen().removeWidget(healthBar);
		player.getMainScreen().removeWidget(healthLabel);
		player.getMainScreen().removeWidget(healthLeft);
		player.getMainScreen().removeWidget(healthRight);
		player.getMainScreen().removeWidget(healthBottom);
		player.getMainScreen().removeWidget(healthTop);
	}

	public int getHealth() {
		return health;
	}

	public void setHealth(int health) {
		this.health = health;
		this.healthLabel.setText("+ " + health);
		this.healthBar.setWidth(health > 0 ? (health / 3) : 0);
		if (health <= 20) {
			background.setColor(new Color(104, 14, 14, 95));
		}
	}

}
