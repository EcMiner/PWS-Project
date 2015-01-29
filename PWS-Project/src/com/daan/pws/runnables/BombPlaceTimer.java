package com.daan.pws.runnables;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.scheduler.BukkitRunnable;

import com.daan.pws.Main;
import com.daan.pws.match.CompetitivePlayer;
import com.daan.pws.match.enums.NotificationPriority;
import com.daan.pws.match.enums.TeamEnum;
import com.daan.pws.match.hud.NotificationHud;
import com.daan.pws.match.items.Bomb;
import com.daan.pws.utilities.PlayerUtil;

public class BombPlaceTimer extends BukkitRunnable {

	public static Map<String, BombPlaceTimer> map = new HashMap<String, BombPlaceTimer>();

	private CompetitivePlayer player;
	private int x;

	public BombPlaceTimer(CompetitivePlayer player) {
		this.player = player;
		PlayerUtil.freeze(player.getPlayer());
		map.put(player.getPlayer().getName(), this);
		runTaskTimer(Main.getInstance(), 0, 1);
	}

	public void run() {
		if (player.getPlayer().getItemInHand() != null && Bomb.isBomb(player.getPlayer().getItemInHand()) && player.getTeam() == TeamEnum.TERRORISTS && (player.getMatch().getMap().getABombSite().isInRegion(player.getPlayer()) || player.getMatch().getMap().getBBombSite().isInRegion(player.getPlayer()))) {
			if (x <= 100) {
				NotificationHud.showProgressBar(player.getPlayer(), "Planting bomb", 2, ((100 - x) / 20) + 1, x, NotificationPriority.HIGHEST);
			} else {
				player.getMatch().plantBomb(player);
				cancel();
				return;
			}
			x++;
		} else {
			cancel();
		}
	}

	@Override
	public synchronized void cancel() throws IllegalStateException {
		map.remove(player.getPlayer().getName());
		PlayerUtil.unfreeze(player.getPlayer());
		super.cancel();
	}
}
