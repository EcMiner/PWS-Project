package com.daan.pws.hud;

import java.util.HashMap;
import java.util.Map;

import org.getspout.spoutapi.gui.Color;
import org.getspout.spoutapi.gui.GenericGradient;
import org.getspout.spoutapi.gui.GenericLabel;
import org.getspout.spoutapi.gui.RenderPriority;
import org.getspout.spoutapi.gui.WidgetAnchor;
import org.getspout.spoutapi.player.SpoutPlayer;

import com.daan.pws.Main;

public class MoneyHud {

	private static Map<String, MoneyHud> moneyStorage = new HashMap<String, MoneyHud>();

	public static void updateMoney(SpoutPlayer player, int money) {
		if (moneyStorage.containsKey(player.getName())) {
			moneyStorage.get(player.getName()).setMoney(money);
		} else {
			moneyStorage.put(player.getName(), new MoneyHud(player, money));
		}
	}

	public static void removeMoney(SpoutPlayer player) {
		if (moneyStorage.containsKey(player.getName())) {
			moneyStorage.get(player.getName()).remove(player);
			moneyStorage.remove(player.getName());
		}
	}

	public static int getMoney(SpoutPlayer player) {
		if (moneyStorage.containsKey(player.getName())) {
			return moneyStorage.get(player.getName()).getMoney();
		}
		return 800;
	}

	private int money;
	private GenericLabel moneyLabel;
	private GenericGradient background;

	private MoneyHud(SpoutPlayer player, int money) {
		this.money = money;

		this.moneyLabel = new GenericLabel();
		this.moneyLabel.setAnchor(WidgetAnchor.CENTER_LEFT);
		this.moneyLabel.shiftXPos(15).shiftYPos(-25);
		this.moneyLabel.setWidth(135).setHeight(50);
		this.moneyLabel.setScale(1.8f);
		this.moneyLabel.setText("$ " + money);
		this.moneyLabel.setTextColor(new Color(154, 163, 116));
		this.moneyLabel.setShadow(false);

		this.background = new GenericGradient(new Color(0, 0, 0, 70));
		this.background.setAnchor(WidgetAnchor.CENTER_LEFT);
		this.background.setHeight(29).setWidth(100).shiftYPos(-34).setPriority(RenderPriority.High);

		player.getMainScreen().attachWidgets(Main.getInstance(), background, moneyLabel);
	}

	public void setMoney(int money) {
		this.money = money;
		this.moneyLabel.setText("$ " + money);
		this.background.setHeight(30).setWidth(this.moneyLabel.getWidth() + 20);
	}

	public int getMoney() {
		return money;
	}

	public void remove(SpoutPlayer player) {
		player.getMainScreen().removeWidget(moneyLabel);
	}

}
