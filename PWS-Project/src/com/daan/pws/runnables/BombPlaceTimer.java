package com.daan.pws.runnables;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.scheduler.BukkitRunnable;
import org.getspout.spoutapi.gui.Color;
import org.getspout.spoutapi.gui.GenericGradient;
import org.getspout.spoutapi.gui.WidgetAnchor;
import org.getspout.spoutapi.player.SpoutPlayer;

import com.daan.pws.Main;

public class BombPlaceTimer extends BukkitRunnable {

	public static Map<String, BombPlaceTimer> map = new HashMap<String, BombPlaceTimer>();

	private SpoutPlayer player;
	private GenericGradient progressBar;
	private int x;

	public BombPlaceTimer(SpoutPlayer player) {
		this.player = player;
		this.progressBar = new GenericGradient(new Color(20, 250, 30));
		this.progressBar.setAnchor(WidgetAnchor.CENTER_CENTER);
		this.progressBar.shiftXPos(-48);
		this.progressBar.setHeight(6).setWidth(0);
		this.player.getMainScreen().attachWidget(Main.getInstance(), progressBar);
		map.put(player.getName(), this);
		runTaskTimer(Main.getInstance(), 0, 5);
	}

	public void run() {
		if (player.getItemInHand() != null && player.getItemInHand().hasItemMeta() && player.getItemInHand().getItemMeta().getDisplayName().equalsIgnoreCase("Bomb")) {
			if (x <= 12) {
				this.progressBar.setWidth(this.progressBar.getWidth() + 8);
			} else {
				cancel();
			}
			x++;
		} else {
			cancel();
		}
	}

	@Override
	public synchronized void cancel() throws IllegalStateException {
		map.remove(player.getName());
		player.getMainScreen().removeWidget(progressBar);
		super.cancel();
	}
}
