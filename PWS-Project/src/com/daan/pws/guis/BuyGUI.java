package com.daan.pws.guis;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

import org.getspout.spoutapi.event.screen.ButtonClickEvent;
import org.getspout.spoutapi.event.screen.ScreenCloseEvent;
import org.getspout.spoutapi.gui.Color;
import org.getspout.spoutapi.gui.GenericButton;
import org.getspout.spoutapi.gui.GenericGradient;
import org.getspout.spoutapi.gui.GenericItemWidget;
import org.getspout.spoutapi.gui.GenericLabel;
import org.getspout.spoutapi.gui.GenericTexture;
import org.getspout.spoutapi.gui.RenderPriority;
import org.getspout.spoutapi.gui.Widget;
import org.getspout.spoutapi.gui.WidgetAnchor;
import org.getspout.spoutapi.inventory.SpoutItemStack;
import org.getspout.spoutapi.player.SpoutPlayer;

import com.daan.pws.Main;
import com.daan.pws.match.CompetitiveLoadout;
import com.daan.pws.match.CompetitivePlayer;
import com.daan.pws.weapon.Gun;
import com.daan.pws.weapon.WeaponManager;

public class BuyGUI {

	public static void openPage(CompetitivePlayer player, Class<? extends BuyGUI> toOpen) {
		try {
			player.getPlayer().getMainScreen().closePopup();
			BuyGUI buyGui = toOpen.getConstructor(CompetitivePlayer.class).newInstance(player);
			buyGui.render();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void fakeButtonClick(GenericButton button, SpoutPlayer player) {
		button.onButtonClick(new ButtonClickEvent(player, player.getCurrentScreen(), button));
	}

	private String menuName;
	private Class<? extends BuyGUI> parentPage;
	private BuyGUIPopup popup;
	private GenericLabel menuNameLabel;
	private GenericButton cancelButton;
	private List<BuyButton> buyButtons = new ArrayList<BuyButton>();
	private CompetitivePlayer player;

	private BuyButton selected;
	private Box box;

	private GunSpecs specs;
	private EquipmentView equipment;

	protected BuyGUI(String menuName, CompetitivePlayer player, Class<? extends BuyGUI> parentPage, String boxName) {
		this.menuName = menuName.toUpperCase();
		this.player = player;
		this.parentPage = parentPage;

		this.popup = new BuyGUIPopup(this) {

			@Override
			public void onScreenClose(ScreenCloseEvent e) {
				e.setCancelled(true);
				BuyGUI.this.close();
			}

		};

		this.menuNameLabel = new GenericLabel(this.menuName);
		this.menuNameLabel.setAnchor(WidgetAnchor.TOP_LEFT).shiftXPos(100).shiftYPos(50);
		this.menuNameLabel.setScale(2f);
		this.menuNameLabel.setTextColor(new Color(251, 189, 0));
		this.menuNameLabel.setShadow(false);

		this.cancelButton = new GenericButton("0 Cancel") {

			@Override
			public void onButtonClick(ButtonClickEvent event) {
				close();
			}

		};
		this.cancelButton.setAnchor(WidgetAnchor.BOTTOM_LEFT).shiftXPos(100).shiftYPos(-60).setWidth(100).setHeight(15);
		this.cancelButton.setShadow(false);
		this.cancelButton.setAlign(WidgetAnchor.TOP_LEFT);

		this.specs = new GunSpecs(boxName);
		this.equipment = new EquipmentView();

		popup.attachWidgets(Main.getInstance(), menuNameLabel, cancelButton);
	}

	public void addButton(String buttonName) {
		addButton(buttonName, true, null, null);
	}

	public void addButton(String buttonName, boolean immediate) {
		addButton(buttonName, immediate, null, null);
	}

	public void addButton(String buttonName, Runnable onClick) {
		addButton(buttonName, true, onClick);
	}

	public void addButton(String buttonName, Callable<Boolean> enable) {
		addButton(buttonName, true, null, enable);
	}

	public void addButton(String buttonName, boolean immediate, Callable<Boolean> enable) {
		addButton(buttonName, immediate, null, enable);
	}

	public void addButton(String buttonName, Runnable onClick, Callable<Boolean> enable) {
		addButton(buttonName, true, onClick, enable);
	}

	public void addButton(String buttonName, boolean immediate, Runnable onClick) {
		BuyButton button = new BuyButton(buttonName, buyButtons.size() + 1, onClick, immediate, null);
		buyButtons.add(button);
		popup.attachWidget(Main.getInstance(), button.getButton());
	}

	public void addButton(String buttonName, boolean immediate, Runnable onClick, Callable<Boolean> enable) {
		BuyButton button = new BuyButton(buttonName, buyButtons.size() + 1, onClick, immediate, enable);
		buyButtons.add(button);
		popup.attachWidget(Main.getInstance(), button.getButton());
	}

	public boolean hasButton(int buttonId) {
		return getButton(buttonId) != null;
	}

	public BuyButton getButton(int buttonId) {
		for (BuyButton button : buyButtons) {
			if (button.getKeyToPress() == buttonId) {
				return button;
			}
		}
		return null;
	}

	public CompetitivePlayer getPlayer() {
		return player;
	}

	public boolean hasButtonSelected() {
		return selected != null;
	}

	public BuyButton getButtonSelected() {
		return selected;
	}

	private void setSelected(BuyButton button) {
		if (this.box != null) {
			this.box.remove();
		}
		this.selected = button;
		this.box = new Box(99, 14, 100, 90 + (20 * (button.getKeyToPress() - 1)), 1, new Color(255, 0, 0), WidgetAnchor.TOP_LEFT, RenderPriority.Lowest);
		popup.attachWidgets(Main.getInstance(), this.box.getLines());
	}

	public void render() {
		player.getPlayer().getMainScreen().attachPopupScreen(popup);
	}

	public void close() {
		player.getPlayer().getMainScreen().closePopup();
		if (parentPage != null) {
			try {
				BuyGUI buyGUI = parentPage.getConstructor(CompetitivePlayer.class).newInstance(player);
				buyGUI.render();
			} catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException | SecurityException e) {
				e.printStackTrace();
			}
		}
	}

	public void update() {
		for (BuyButton button : buyButtons) {
			button.update();
		}
	}

	public class BuyButton {

		private String text;
		private int keyToPress;
		private GenericButton button;
		private Callable<Boolean> enable;

		public BuyButton(String text, int keyToPress, final Runnable onClick, final boolean immediate, Callable<Boolean> enable) {
			this.text = text;
			this.keyToPress = keyToPress;
			this.enable = enable;

			this.button = new GenericButton(keyToPress + " " + text) {

				@Override
				public void onButtonClick(ButtonClickEvent event) {
					if (isEnabled()) {
						if (!immediate)
							if (selected == null || selected != BuyButton.this) {
								specs.setGunSelected(WeaponManager.getGun(BuyButton.this.text));
								setSelected(BuyButton.this);
								return;
							}

						if (onClick != null) {
							onClick.run();

						}
					}
				}

			};
			this.button.setAnchor(WidgetAnchor.TOP_LEFT).shiftXPos(100).shiftYPos(90 + (20 * (keyToPress - 1))).setWidth(100).setHeight(15);
			this.button.setShadow(false);
			this.button.setAlign(WidgetAnchor.TOP_LEFT);

			update();
		}

		public String getText() {
			return text;
		}

		public int getKeyToPress() {
			return keyToPress;
		}

		public GenericButton getButton() {
			return button;
		}

		public void update() {
			if (enable != null) {
				try {
					boolean enabled = enable.call();
					this.button.setEnabled(enabled);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}

	}

	public class Box {

		private Color color;
		private int width;
		private int height;
		private int x;
		private int y;
		private int thickness;
		private WidgetAnchor anchor;
		private RenderPriority priority;

		private GenericGradient[] lines = new GenericGradient[4];

		public Box(int width, int height, int x, int y, int thickness, Color color, WidgetAnchor anchor) {
			this(width, height, x, y, thickness, color, anchor, RenderPriority.Normal);
		}

		public Box(int width, int height, int x, int y, int thickness, Color color, WidgetAnchor anchor, RenderPriority priority) {
			this.width = width;
			this.height = height;
			this.x = x;
			this.y = y;
			this.thickness = thickness;
			this.color = color;
			this.anchor = anchor;
			this.priority = priority;

			this.lines[0] = new GenericGradient(color);
			this.lines[1] = new GenericGradient(color);
			this.lines[2] = new GenericGradient(color);
			this.lines[3] = new GenericGradient(color);

			GenericGradient top = lines[0];
			top.setPriority(priority).setAnchor(anchor).shiftXPos(x).shiftYPos(y).setWidth(width).setHeight(thickness);

			GenericGradient left = lines[1];
			left.setPriority(priority).setAnchor(anchor).shiftXPos(x).shiftYPos(y).setWidth(thickness).setHeight(height);

			GenericGradient bottom = lines[2];
			bottom.setPriority(priority).setAnchor(anchor).shiftXPos(x).shiftYPos(y + height).setWidth(width).setHeight(thickness);

			GenericGradient right = lines[3];
			right.setPriority(priority).setAnchor(anchor).shiftXPos(x + width).shiftYPos(y).setWidth(thickness).setHeight(height + 1);
		}

		public Color getColor() {
			return color;
		}

		public int getWidth() {
			return width;
		}

		public int getHeight() {
			return height;
		}

		public int getX() {
			return x;
		}

		public int getY() {
			return y;
		}

		public int getThickness() {
			return thickness;
		}

		public WidgetAnchor getAnchor() {
			return anchor;
		}

		public GenericGradient[] getLines() {
			return lines;
		}

		public RenderPriority getPriority() {
			return priority;
		}

		public void remove() {
			for (Widget widget : lines)
				popup.removeWidget(widget);
		}
	}

	public class GunSpecs {

		private GenericGradient specsBox;
		private GenericLabel boxName;

		private GenericItemWidget gunPicture;
		private GenericLabel gunName;

		private GenericLabel costLabel;
		private GenericLabel costAmount;

		private GenericLabel ammoLabel;
		private GenericLabel ammoAmount;

		private GenericLabel killAwardLabel;
		private GenericLabel killAwardAmount;

		private GenericLabel fireRateLabel;
		private GenericLabel fireRateAmount;

		private GenericLabel armourPenetrationLabel;
		private GenericLabel armourPenetrationAmount;

		private GenericLabel walkSpeedLabel;
		private GenericLabel walkSpeedAmount;

		private GenericLabel recoilControlLabel;
		private GenericLabel recoilControlAmount;

		private GenericLabel reloadTimeLabel;
		private GenericLabel reloadTimeAmount;

		public GunSpecs(String boxName) {
			this.specsBox = new GenericGradient(new Color(0, 0, 0, 80));
			this.specsBox.setAnchor(WidgetAnchor.TOP_LEFT).shiftXPos(220).shiftYPos(70).setWidth(100).setHeight(230).setPriority(RenderPriority.High);

			this.boxName = new GenericLabel(boxName);
			this.boxName.setAnchor(WidgetAnchor.TOP_LEFT).shiftXPos(223).shiftYPos(73);
			this.boxName.setShadow(false);

			popup.attachWidgets(Main.getInstance(), this.boxName, this.specsBox);
		}

		private int calcShiftX(GenericLabel label) {
			return 320 + (label.getText().length() - 4);
		}

		public void setGunSelected(Gun gun) {
			if (gunPicture != null) {
				popup.removeWidget(gunPicture);
			}
			if (gunName != null) {
				popup.removeWidget(gunName);
			}
			if (costLabel != null) {
				popup.removeWidget(costLabel);
			}
			if (costAmount != null) {
				popup.removeWidget(costAmount);
			}
			if (ammoLabel != null) {
				popup.removeWidget(ammoLabel);
			}
			if (ammoAmount != null) {
				popup.removeWidget(ammoAmount);
			}
			if (killAwardLabel != null) {
				popup.removeWidget(killAwardLabel);
			}
			if (killAwardAmount != null) {
				popup.removeWidget(killAwardAmount);
			}
			if (fireRateLabel != null) {
				popup.removeWidget(fireRateLabel);
			}
			if (fireRateAmount != null) {
				popup.removeWidget(fireRateAmount);
			}
			if (armourPenetrationLabel != null) {
				popup.removeWidget(armourPenetrationLabel);
			}
			if (armourPenetrationAmount != null) {
				popup.removeWidget(armourPenetrationAmount);
			}
			if (walkSpeedLabel != null) {
				popup.removeWidget(walkSpeedLabel);
			}
			if (walkSpeedAmount != null) {
				popup.removeWidget(walkSpeedAmount);
			}
			if (recoilControlLabel != null) {
				popup.removeWidget(recoilControlLabel);
			}
			if (recoilControlAmount != null) {
				popup.removeWidget(recoilControlAmount);
			}
			if (reloadTimeLabel != null) {
				popup.removeWidget(reloadTimeLabel);
			}
			if (reloadTimeAmount != null) {
				popup.removeWidget(reloadTimeAmount);
			}
			reloadTimeLabel = null;
			reloadTimeAmount = null;
			recoilControlLabel = null;
			recoilControlAmount = null;
			walkSpeedLabel = null;
			walkSpeedAmount = null;
			gunPicture = null;
			gunName = null;
			costLabel = null;
			costAmount = null;
			ammoLabel = null;
			ammoAmount = null;
			killAwardLabel = null;
			killAwardAmount = null;
			fireRateLabel = null;
			fireRateAmount = null;
			armourPenetrationLabel = null;
			armourPenetrationAmount = null;

			if (gun != null) {
				gunPicture = new GenericItemWidget(new SpoutItemStack(gun.getGunItem()));
				gunPicture.setAnchor(WidgetAnchor.TOP_LEFT).shiftXPos(230).shiftYPos(85);

				gunName = new GenericLabel(gun.getName());
				gunName.setAnchor(WidgetAnchor.TOP_LEFT).shiftXPos(223).shiftYPos(85);
				gunName.setScale(gunName.getScale() - 0.2f);
				gunName.setShadow(false);

				costLabel = new GenericLabel("COST:");
				costLabel.setAnchor(WidgetAnchor.TOP_LEFT).shiftXPos(223).shiftYPos(140);
				costLabel.setScale(costLabel.getScale() - 0.2f);
				costLabel.setShadow(false);

				costAmount = new GenericLabel("$" + gun.getPrice());
				costAmount.setAnchor(WidgetAnchor.TOP_LEFT).shiftXPos(calcShiftX(costAmount)).shiftYPos(140);
				costAmount.setAlign(WidgetAnchor.TOP_RIGHT);
				costAmount.setScale(costAmount.getScale() - 0.2f).setFixed(true);
				costAmount.setShadow(false);

				ammoLabel = new GenericLabel("AMMO:");
				ammoLabel.setAnchor(WidgetAnchor.TOP_LEFT).shiftXPos(223).shiftYPos(149);
				ammoLabel.setScale(ammoLabel.getScale() - 0.2f);
				ammoLabel.setShadow(false);

				ammoAmount = new GenericLabel(gun.getBulletsInRound() + "/" + (gun.getTotalAmmo() - gun.getBulletsInRound()));
				ammoAmount.setAnchor(WidgetAnchor.TOP_LEFT).shiftXPos(calcShiftX(ammoAmount)).shiftYPos(149);
				ammoAmount.setAlign(WidgetAnchor.TOP_RIGHT);
				ammoAmount.setScale(ammoAmount.getScale() - 0.2f).setFixed(true);
				ammoAmount.setShadow(false);

				killAwardLabel = new GenericLabel("KILL AWARD:");
				killAwardLabel.setAnchor(WidgetAnchor.TOP_LEFT).shiftXPos(223).shiftYPos(158);
				killAwardLabel.setScale(killAwardLabel.getScale() - 0.2f);
				killAwardLabel.setShadow(false);

				killAwardAmount = new GenericLabel("150%");
				killAwardAmount.setAnchor(WidgetAnchor.TOP_LEFT).shiftXPos(calcShiftX(killAwardAmount)).shiftYPos(158);
				killAwardAmount.setAlign(WidgetAnchor.TOP_RIGHT);
				killAwardAmount.setScale(killAwardAmount.getScale() - 0.2f).setFixed(true);
				killAwardAmount.setShadow(false);

				fireRateLabel = new GenericLabel("FIRERATE:");
				fireRateLabel.setAnchor(WidgetAnchor.TOP_LEFT).shiftXPos(223).shiftYPos(167);
				fireRateLabel.setScale(fireRateLabel.getScale() - 0.2f);
				fireRateLabel.setShadow(false);

				fireRateAmount = new GenericLabel(gun.getRoundsPerMinute() + "RPM");
				fireRateAmount.setAnchor(WidgetAnchor.TOP_LEFT).shiftXPos(calcShiftX(fireRateAmount)).shiftYPos(167);
				fireRateAmount.setAlign(WidgetAnchor.TOP_RIGHT);
				fireRateAmount.setScale(fireRateAmount.getScale() - 0.2f).setFixed(true);
				fireRateAmount.setShadow(false);

				armourPenetrationLabel = new GenericLabel("PENETRATION:");
				armourPenetrationLabel.setAnchor(WidgetAnchor.TOP_LEFT).shiftXPos(223).shiftYPos(176);
				armourPenetrationLabel.setScale(armourPenetrationLabel.getScale() - 0.2f);
				armourPenetrationLabel.setShadow(false);

				armourPenetrationAmount = new GenericLabel(gun.getArmourPenetration() + "%");
				armourPenetrationAmount.setAnchor(WidgetAnchor.TOP_LEFT).shiftXPos(calcShiftX(armourPenetrationAmount)).shiftYPos(176);
				armourPenetrationAmount.setAlign(WidgetAnchor.TOP_RIGHT);
				armourPenetrationAmount.setScale(armourPenetrationAmount.getScale() - 0.2f).setFixed(true);
				armourPenetrationAmount.setShadow(false);

				walkSpeedLabel = new GenericLabel("WALK SPEED:");
				walkSpeedLabel.setAnchor(WidgetAnchor.TOP_LEFT).shiftXPos(223).shiftYPos(185);
				walkSpeedLabel.setScale(walkSpeedLabel.getScale() - 0.2f);
				walkSpeedLabel.setShadow(false);

				walkSpeedAmount = new GenericLabel((Math.round((Double.valueOf(gun.getMovementSpeed() + "") / Double.valueOf(250d)) * 100)) + "%");
				walkSpeedAmount.setAnchor(WidgetAnchor.TOP_LEFT).shiftXPos(calcShiftX(walkSpeedAmount)).shiftYPos(185);
				walkSpeedAmount.setAlign(WidgetAnchor.TOP_RIGHT);
				walkSpeedAmount.setScale(walkSpeedAmount.getScale() - 0.2f).setFixed(true);
				walkSpeedAmount.setShadow(false);

				recoilControlLabel = new GenericLabel("RECOIL CONTROL:");
				recoilControlLabel.setAnchor(WidgetAnchor.TOP_LEFT).shiftXPos(223).shiftYPos(195);
				recoilControlLabel.setScale(recoilControlLabel.getScale() - 0.2f);
				recoilControlLabel.setShadow(false);

				recoilControlAmount = new GenericLabel("100%");
				recoilControlAmount.setAnchor(WidgetAnchor.TOP_LEFT).shiftXPos(calcShiftX(recoilControlAmount)).shiftYPos(195);
				recoilControlAmount.setAlign(WidgetAnchor.TOP_RIGHT);
				recoilControlAmount.setScale(recoilControlAmount.getScale() - 0.2f).setFixed(true);
				recoilControlAmount.setShadow(false);

				reloadTimeLabel = new GenericLabel("RELOAD TIME:");
				reloadTimeLabel.setAnchor(WidgetAnchor.TOP_LEFT).shiftXPos(223).shiftYPos(204);
				reloadTimeLabel.setScale(reloadTimeLabel.getScale() - 0.2f);
				reloadTimeLabel.setShadow(false);

				reloadTimeAmount = new GenericLabel(gun.getReloadTime() + "s");
				reloadTimeAmount.setAnchor(WidgetAnchor.TOP_LEFT).shiftXPos(calcShiftX(reloadTimeAmount)).shiftYPos(204);
				reloadTimeAmount.setAlign(WidgetAnchor.TOP_RIGHT);
				reloadTimeAmount.setScale(reloadTimeAmount.getScale() - 0.2f).setFixed(true);
				reloadTimeAmount.setShadow(false);

				popup.attachWidgets(Main.getInstance(), gunPicture, gunName, costLabel, costAmount, ammoLabel, ammoAmount, killAwardLabel, killAwardAmount, fireRateLabel, fireRateAmount, armourPenetrationLabel, armourPenetrationAmount, walkSpeedLabel, walkSpeedAmount, recoilControlLabel, recoilControlAmount, reloadTimeLabel, reloadTimeAmount);
			}

		}
	}

	public class EquipmentView {

		private GenericGradient invBox;
		private GenericLabel invName;

		private List<Widget> currentWidgets = new ArrayList<Widget>();

		public EquipmentView() {
			this.invBox = new GenericGradient(new Color(0, 0, 0, 80));
			this.invBox.setAnchor(WidgetAnchor.TOP_LEFT).shiftXPos(325).shiftYPos(120).setWidth(80).setHeight(180).setPriority(RenderPriority.High);

			this.invName = new GenericLabel("Inventory");
			this.invName.setAnchor(WidgetAnchor.TOP_LEFT).shiftXPos(328).shiftYPos(123);
			this.invName.setShadow(false);

			popup.attachWidgets(Main.getInstance(), this.invName, this.invBox);

			updateEquipment();
		}

		public void updateEquipment() {
			for (Widget widget : currentWidgets) {
				popup.removeWidget(widget);
			}
			currentWidgets.clear();

			CompetitiveLoadout loadout = player.getLoadout();
			int y = 0;
			if (loadout.hasDefuseKit()) {
				GenericTexture picture = new GenericTexture("http://panisme.nl/csgo/icons/rescuekit_icon.png");
				picture.setWidth(17).setHeight(15).setAnchor(WidgetAnchor.TOP_LEFT).shiftXPos(330).shiftYPos(shiftY(y) - 16);

				GenericLabel name = new GenericLabel("Rescue Kit");
				name.setScale(name.getScale() - 0.25f).setAnchor(WidgetAnchor.TOP_LEFT).shiftXPos(330).shiftYPos(shiftY(y));

				popup.attachWidgets(Main.getInstance(), picture, name);
				currentWidgets.add(picture);
				currentWidgets.add(name);

				y += 28;
			}
			if (loadout.hasBomb()) {
				GenericTexture picture = new GenericTexture("http://panisme.nl/csgo/icons/bomb_icon.png");
				picture.setWidth(13).setHeight(15).setAnchor(WidgetAnchor.TOP_LEFT).shiftXPos(330).shiftYPos(shiftY(y) - 5);
				popup.attachWidgets(Main.getInstance(), picture);
				currentWidgets.add(picture);

				y += 16;
			}
			if (loadout.getSecondary() != null) {
				GenericTexture picture = new GenericTexture(loadout.getSecondary().getGun().getIconUrl());
				picture.setWidth((int) (calculateWidth(loadout.getSecondary().getGun(), 12) * 1.2)).setHeight(12).setAnchor(WidgetAnchor.TOP_LEFT).shiftXPos(330).shiftYPos(shiftY(y) - 13);

				GenericLabel name = new GenericLabel(loadout.getSecondary().getGunName());
				name.setScale(name.getScale() - 0.25f).setAnchor(WidgetAnchor.TOP_LEFT).shiftXPos(330).shiftYPos(shiftY(y));

				popup.attachWidgets(Main.getInstance(), picture, name);
				currentWidgets.add(picture);
				currentWidgets.add(name);

				y += 25;
			}
			if (loadout.getPrimary() != null) {
				GenericTexture picture = new GenericTexture(loadout.getPrimary().getGun().getIconUrl());
				picture.setWidth(calculateWidth(loadout.getPrimary().getGun(), 15)).setHeight(15).setAnchor(WidgetAnchor.TOP_LEFT).shiftXPos(330).shiftYPos(shiftY(y) - 16);

				GenericLabel name = new GenericLabel(loadout.getPrimary().getGunName());
				name.setScale(name.getScale() - 0.25f).setAnchor(WidgetAnchor.TOP_LEFT).shiftXPos(330).shiftYPos(shiftY(y));

				popup.attachWidgets(Main.getInstance(), picture, name);
				currentWidgets.add(picture);
				currentWidgets.add(name);
			}
		}

		private int shiftY(int y) {
			return 120 + (160 - y);
		}

		private int calculateWidth(Gun gun, int height) {
			double toMultiplyWith = (Double.valueOf(height + "") / Double.valueOf(gun.getIconHeight() + ""));
			return (int) Math.round(gun.getIconWidth() * toMultiplyWith);
		}
	}

}
