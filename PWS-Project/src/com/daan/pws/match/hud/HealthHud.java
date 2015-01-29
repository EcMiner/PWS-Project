package com.daan.pws.match.hud;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.Bukkit;
import org.getspout.spoutapi.SpoutManager;
import org.getspout.spoutapi.gui.Color;
import org.getspout.spoutapi.gui.GenericGradient;
import org.getspout.spoutapi.gui.GenericLabel;
import org.getspout.spoutapi.gui.GenericRectangle;
import org.getspout.spoutapi.gui.GenericTexture;
import org.getspout.spoutapi.gui.RenderPriority;
import org.getspout.spoutapi.gui.WidgetAnchor;
import org.getspout.spoutapi.player.SpoutPlayer;

import com.daan.pws.Main;

@SuppressWarnings("deprecation")
public class HealthHud {

	private static Map<String, HealthHud> healthStorage = new HashMap<String, HealthHud>();

	public static void updateHealth(SpoutPlayer player, int health, int armour) {
		if (healthStorage.containsKey(player.getName())) {
			healthStorage.get(player.getName()).setHealth(health).setArmour(armour);
		} else {
			healthStorage.put(player.getName(), new HealthHud(player, health, armour));
		}
	}

	public static void updateHealth(SpoutPlayer player, int health) {
		if (healthStorage.containsKey(player.getName())) {
			healthStorage.get(player.getName()).setHealth(health);
		} else {
			healthStorage.put(player.getName(), new HealthHud(player, health, 0));
		}
	}

	public static void updateArmour(SpoutPlayer player, int armour) {
		if (healthStorage.containsKey(player.getName())) {
			healthStorage.get(player.getName()).setArmour(armour);
		} else {
			healthStorage.put(player.getName(), new HealthHud(player, 100, 0));
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

	private GenericGradient healthBackground;

	private GenericTexture armourIcon;
	private GenericLabel armourLabel;
	private GenericGradient armourBar;

	private GenericGradient armourLeft;
	private GenericGradient armourRight;
	private GenericGradient armourTop;
	private GenericGradient armourBottom;

	private GenericGradient armourBackground;

	private int health;
	private int armour;

	private HealthHud(SpoutPlayer player, int health, int armour) {
		this.health = health;
		healthLabel = new GenericLabel();
		healthLabel.setAnchor(WidgetAnchor.BOTTOM_LEFT);
		healthLabel.shiftXPos(10).shiftYPos(-25);
		healthLabel.setScale(1.4f);
		healthLabel.setWidth(150).setHeight(50);
		healthLabel.setTextColor(new Color(154, 163, 116));
		healthLabel.setText("✚ " + Math.round(health));
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

		healthBackground = new GenericGradient(new Color(0, 0, 0, 129));
		healthBackground.setAnchor(WidgetAnchor.BOTTOM_LEFT).shiftYPos(-28).setHeight(17).setWidth(90).setPriority(RenderPriority.Highest);

		armourIcon = new GenericTexture("http://panisme.nl/csgo/icons/shield_icon.png");
		armourIcon.setAnchor(WidgetAnchor.BOTTOM_LEFT);
		armourIcon.shiftYPos(-25).shiftXPos(100);
		armourIcon.setWidth(10).setHeight(13);

		armourLabel = new GenericLabel(armour + "");
		armourLabel.setAnchor(WidgetAnchor.BOTTOM_LEFT);
		armourLabel.setScale(1.4f);
		armourLabel.shiftXPos(115).shiftYPos(-25);
		armourLabel.setWidth(150).setHeight(50);
		armourLabel.setTextColor(new Color(154, 163, 116));
		armourLabel.setShadow(false);

		armourBar = new GenericRectangle(new Color(154, 163, 116));
		armourBar.setAnchor(WidgetAnchor.BOTTOM_LEFT);
		armourBar.shiftXPos(145);
		armourBar.shiftYPos(-20);
		armourBar.setWidth(armour / 3);
		armourBar.setHeight(3);

		armourLeft = new GenericRectangle(new Color(125, 132, 95));
		armourLeft.setAnchor(WidgetAnchor.BOTTOM_LEFT);
		armourLeft.shiftXPos(144);
		armourLeft.shiftYPos(-21);
		armourLeft.setWidth(1);
		armourLeft.setHeight(5);
		armourLeft.setPriority(RenderPriority.High);

		armourRight = new GenericRectangle(new Color(125, 132, 95));
		armourRight.setAnchor(WidgetAnchor.BOTTOM_LEFT);
		armourRight.shiftXPos(178);
		armourRight.shiftYPos(-21);
		armourRight.setWidth(1);
		armourRight.setHeight(5);
		armourRight.setPriority(RenderPriority.High);

		armourBottom = new GenericRectangle(new Color(125, 132, 95));
		armourBottom.setAnchor(WidgetAnchor.BOTTOM_LEFT);
		armourBottom.shiftXPos(144);
		armourBottom.shiftYPos(-17);
		armourBottom.setWidth(35);
		armourBottom.setHeight(1);
		armourBottom.setPriority(RenderPriority.High);

		armourTop = new GenericRectangle(new Color(125, 132, 95));
		armourTop.setAnchor(WidgetAnchor.BOTTOM_LEFT);
		armourTop.shiftXPos(144);
		armourTop.shiftYPos(-21);
		armourTop.setWidth(35);
		armourTop.setHeight(1);
		armourTop.setPriority(RenderPriority.High);

		armourBackground = new GenericGradient(new Color(0, 0, 0, 129));
		armourBackground.shiftXPos(90).setAnchor(WidgetAnchor.BOTTOM_LEFT).shiftYPos(-28).setHeight(17).setWidth(90).setPriority(RenderPriority.Highest);

		player.getMainScreen().attachWidgets(Main.getInstance(), healthBackground, healthTop, healthBottom, healthRight, healthLeft, healthBar, healthLabel, armourIcon, armourLabel, armourBar, armourLeft, armourRight, armourBottom, armourTop, armourBackground);
	}

	public void remove(SpoutPlayer player) {
		player.getMainScreen().removeWidget(healthBar);
		player.getMainScreen().removeWidget(healthLabel);
		player.getMainScreen().removeWidget(healthLeft);
		player.getMainScreen().removeWidget(healthRight);
		player.getMainScreen().removeWidget(healthBottom);
		player.getMainScreen().removeWidget(healthTop);
		player.getMainScreen().removeWidget(armourIcon).removeWidget(armourLabel).removeWidget(armourBar).removeWidget(armourRight).removeWidget(armourLeft).removeWidget(armourBottom).removeWidget(armourTop).removeWidget(armourBackground);
	}

	public int getHealth() {
		return health;
	}

	public int getArmour() {
		return armour;
	}

	public HealthHud setHealth(int health) {
		this.health = health;
		this.healthLabel.setText("✚ " + health);
		this.healthBar.setWidth(health > 0 ? (health / 3) : 0);
		if (health <= 20) {
			healthBackground.setColor(new Color(104, 14, 14, 95));
		}
		return this;
	}

	public HealthHud setArmour(int armour) {
		this.armour = armour;
		this.armourLabel.setText(armour + "");
		return this;
	}

}
