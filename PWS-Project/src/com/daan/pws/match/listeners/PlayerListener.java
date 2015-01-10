package com.daan.pws.match.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.getspout.spoutapi.player.SpoutPlayer;
import org.kitteh.tag.PlayerReceiveNameTagEvent;

import com.daan.pws.match.Competitive;

public class PlayerListener implements Listener {

	@EventHandler
	public void onNameTagReceive(PlayerReceiveNameTagEvent evt) {
		Player r = evt.getPlayer();
		Player s = evt.getNamedPlayer();
		if (s instanceof SpoutPlayer) {
			SpoutPlayer sender = (SpoutPlayer) s;
			if (Competitive.isInMatch(sender)) {
				if (r instanceof SpoutPlayer) {
					SpoutPlayer receiver = (SpoutPlayer) r;
					if (Competitive.isInMatch(receiver)) {
						if (Competitive.getMatch(receiver).equals(Competitive.getMatch(sender))) {
							if (Competitive.getMatch(receiver).getTeam(receiver) == Competitive.getMatch(sender).getTeam(sender)) {
								return;
							}
						}
					}
					sender.hideTitleFrom(receiver);
				}
			}
		}
	}
}
