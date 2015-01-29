package com.daan.pws.runnables;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.scheduler.BukkitRunnable;

import com.daan.pws.Main;
import com.daan.pws.match.CompetitivePlayer;
import com.daan.pws.match.enums.NotificationPriority;
import com.daan.pws.match.hud.NotificationHud;

public class BombDefuseTimer extends BukkitRunnable {

	public static final Map<String, BombDefuseTimer> map = new HashMap<String, BombDefuseTimer>();

	private final CompetitivePlayer player;
	private final boolean hasDefuseKit;
	private final int defuseTime;
	private int x;

	public BombDefuseTimer(CompetitivePlayer player) {
		this.player = player;
		this.hasDefuseKit = player.getLoadout().hasDefuseKit();
		this.defuseTime = hasDefuseKit ? 100 : 200;
		map.put(player.getPlayer().getName(), this);
		runTaskTimer(Main.getInstance(), 0, 1);
	}

	@SuppressWarnings("deprecation")
	@Override
	public void run() {
		Block inSight = player.getPlayer().getTargetBlock(null, 4);
		if (inSight != null && inSight.getType() == Material.DAYLIGHT_DETECTOR && player.getMatch().getBombBlock() != null && (player.getMatch().getBombBlock().getX() == inSight.getX() && player.getMatch().getBombBlock().getY() == inSight.getY() && player.getMatch().getBombBlock().getZ() == inSight.getZ())) {
			if (x <= defuseTime) {
				NotificationHud.showProgressBar(player.getPlayer(), "Defusing bomb", 2, ((defuseTime - x) / 20) + 1, 100 - ((int) Math.round((Double.valueOf(100d) / Double.valueOf(defuseTime + "")) * Double.valueOf(x + ""))), NotificationPriority.HIGHEST);
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
		map.remove(player.getPlayer().getName());
		super.cancel();
	}

}
