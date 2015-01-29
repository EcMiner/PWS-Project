package com.daan.pws.listeners;

import org.getspout.spoutapi.SpoutManager;
import org.getspout.spoutapi.event.input.KeyBindingEvent;
import org.getspout.spoutapi.keyboard.BindingExecutionDelegate;
import org.getspout.spoutapi.keyboard.Keyboard;
import org.getspout.spoutapi.player.SpoutPlayer;

import com.daan.pws.Main;
import com.daan.pws.guis.MainBuyGUI;
import com.daan.pws.match.Competitive;
import com.daan.pws.match.CompetitivePlayer;
import com.daan.pws.match.enums.NotificationPriority;
import com.daan.pws.match.enums.TeamEnum;
import com.daan.pws.match.hud.NotificationHud;

public class BuyButtonListener implements BindingExecutionDelegate {

	public BuyButtonListener() {
		SpoutManager.getKeyBindingManager().registerBinding("Open Buy Menu", Keyboard.KEY_B, "This will open the buy meny if possible", this, Main.getInstance());
	}

	@Override
	public void keyPressed(KeyBindingEvent evt) {
	}

	@Override
	public void keyReleased(KeyBindingEvent evt) {
		SpoutPlayer player = evt.getPlayer();
		if (Competitive.isInMatch(player)) {
			Competitive comp = Competitive.getMatch(player);
			CompetitivePlayer cPlayer = comp.getCompetitivePlayer(player);
			if (comp.isGameStarted() && comp.getTimer().isBuyTime() && cPlayer.isAlive()) {
				if (cPlayer.getTeam() == TeamEnum.COUNTER_TERRORISTS) {
					if (comp.getMap().getCounterTerroristsBuyZone().isInRegion(player)) {
						new MainBuyGUI(cPlayer).render();
					} else {
						NotificationHud.showAlert(player, "You're not in the buyzone", 60, NotificationPriority.NORMAL);
					}
				} else if (cPlayer.getTeam() == TeamEnum.TERRORISTS) {
					if (comp.getMap().getTerroristsBuyZone().isInRegion(player)) {
						new MainBuyGUI(cPlayer).render();
					} else {
						NotificationHud.showAlert(player, "You're not in the buyzone", 60, NotificationPriority.NORMAL);
					}
				}
			}
		}
	}
}
