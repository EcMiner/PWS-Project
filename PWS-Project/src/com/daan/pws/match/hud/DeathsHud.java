package com.daan.pws.match.hud;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.scheduler.BukkitRunnable;
import org.getspout.spoutapi.gui.Color;
import org.getspout.spoutapi.gui.GenericLabel;
import org.getspout.spoutapi.gui.GenericTexture;
import org.getspout.spoutapi.gui.Widget;
import org.getspout.spoutapi.gui.WidgetAnchor;
import org.getspout.spoutapi.gui.WidgetAnim;
import org.getspout.spoutapi.player.SpoutPlayer;

import com.daan.pws.Main;
import com.daan.pws.weapon.Gun;

public class DeathsHud {

	private List<SpoutPlayer> players = new ArrayList<SpoutPlayer>();
	private List<Death> deaths = new ArrayList<Death>();
	private BukkitRunnable runnable;

	public DeathsHud(SpoutPlayer... players) {
		this.players.addAll(Arrays.asList(players));
	}

	public void addReceiever(SpoutPlayer... players) {
		this.players.addAll(Arrays.asList(players));
	}

	public void removeReceiver(SpoutPlayer player) {
		this.players.remove(player);
	}

	public void addDeath(String killers, String killed, Gun gun, boolean headshot) {
		int index = deaths.size();
		deaths.add(new Death(killers, killed, gun.getIconUrl(), gun.getIconWidth(), gun.getIconHeight(), headshot, index).display(players));
		if (deaths.size() == 1) {
			runnable = new BukkitRunnable() {

				@Override
				public void run() {
					if (deaths.size() > 0) {
						Death toRemove = null;

						for (Death death : deaths) {
							if (death.getIndex() == 0) {
								toRemove = death;
								death.remove(players);
							} else {
								death.animateYAdd(-1, 13, 1);
								death.setIndex(death.getIndex() - 1);
							}
						}

						for (Death death : deaths) {
							death.setIndex(death.getIndex());
						}

						if (toRemove != null) {
							deaths.remove(toRemove);
						}
						if (deaths.size() == 0) {
							runnable = null;
							this.cancel();
						}
					}
				}
			};
			runnable.runTaskTimer(Main.getInstance(), 100, 100);
		}
	}

	protected static class Death {

		private GenericLabel killer;
		private GenericLabel died;
		private GenericTexture weapon;
		private GenericTexture headshot;

		private int index;

		public Death(String killer, String died, String iconUrl, int iconWidth, int iconHeight, boolean headshot, int index) {
			System.out.println(iconUrl);
			this.index = index;
			this.died = new GenericLabel();
			this.died.setAnchor(WidgetAnchor.TOP_RIGHT);
			this.died.setAlign(WidgetAnchor.TOP_RIGHT);
			this.died.setTextColor(died.startsWith("{t}") ? new Color(248, 217, 89) : new Color(129, 161, 230));
			this.died.shiftYPos(40 + (index * 13)).shiftXPos(-5);
			this.died.setText(died.replace("{t}", ""));

			if (headshot) {
				this.headshot = new GenericTexture("http://panisme.nl/csgo/icons/headshot.png");
				this.headshot.setAnchor(WidgetAnchor.TOP_RIGHT);
				this.headshot.setWidth(13).setHeight(11);
				this.headshot.shiftYPos((40 + (index * 13)) - 1).shiftXPos(-(13 + (died.length() * 6) + 2));
			}

			this.weapon = new GenericTexture(iconUrl);
			this.weapon.setAnchor(WidgetAnchor.TOP_RIGHT);
			this.weapon.setWidth(calculateWidth(this.weapon, iconWidth)).setHeight(10);
			this.weapon.shiftYPos((40 + (index * 13)) - 1);
			if (this.headshot == null) {
				this.weapon.shiftXPos(-(this.weapon.getWidth() + (died.length() * 6) + 5));
			} else {
				this.weapon.setX(this.headshot.getX() - 3 - this.weapon.getWidth());
			}

			this.killer = new GenericLabel();
			this.killer.setAnchor(WidgetAnchor.TOP_RIGHT);
			this.killer.setAlign(WidgetAnchor.TOP_RIGHT);
			this.killer.setX(this.weapon.getX() - 3).shiftYPos(40 + (index * 13));
			this.killer.setTextColor(killer.startsWith("{t}") ? new Color(248, 217, 89) : new Color(129, 161, 230));
			this.killer.setText(killer.replace("{t}", ""));
		}

		public int getIndex() {
			return index;
		}

		public void setIndex(int index) {
			this.index = index;
		}

		public void animateYAdd(final int toAddPerTime, final int totalTimes, int ticks) {
			new BukkitRunnable() {

				int i = 0;

				@Override
				public void run() {
					if (i < totalTimes) {
						killer.setY(killer.getY() + toAddPerTime);
						died.setY(died.getY() + toAddPerTime);
						weapon.setY(weapon.getY() + toAddPerTime);
						if (headshot != null) {
							headshot.setY(headshot.getY() + toAddPerTime);
						}
						i++;
					} else {
						cancel();
					}
				}

			}.runTaskTimer(Main.getInstance(), 0, ticks);
		}

		public void animate(WidgetAnim animation, float value, int count, int ticks, boolean repeat, boolean reset) {
			this.killer.animate(animation, value, (short) count, (short) ticks, repeat, reset);
			this.died.animate(animation, value, (short) count, (short) ticks, repeat, reset);
			this.weapon.animate(animation, value, (short) count, (short) ticks, repeat, reset);

			this.killer.animateStart();
			this.died.animateStart();
			this.weapon.animateStart();

			if (headshot != null) {
				this.headshot.animate(animation, value, (short) count, (short) ticks, repeat, reset);
				this.headshot.animateStart();
			}
		}

		public Death display(List<SpoutPlayer> players) {
			for (SpoutPlayer player : players) {
				player.getMainScreen().attachWidgets(Main.getInstance(), killer, died, weapon);
				if (headshot != null) {
					player.getMainScreen().attachWidget(Main.getInstance(), headshot);
				}
			}
			return this;
		}

		public Death remove(List<SpoutPlayer> players) {
			for (SpoutPlayer player : players) {
				player.getMainScreen().removeWidget(died);
				player.getMainScreen().removeWidget(killer);
				player.getMainScreen().removeWidget(weapon);
				if (headshot != null) {
					player.getMainScreen().removeWidget(headshot);
				}
			}
			return this;
		}

		private int calculateWidth(Widget widget, int width) {
			int toDivide = (int) Math.round((double) (widget.getHeight() / 11));
			return width / (toDivide * 2);
		}

	}

}
