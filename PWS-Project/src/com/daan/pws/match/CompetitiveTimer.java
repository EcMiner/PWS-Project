package com.daan.pws.match;

import org.bukkit.scheduler.BukkitRunnable;

import com.daan.pws.Main;
import com.daan.pws.guis.BuyGUIPopup;
import com.daan.pws.match.enums.NotificationPriority;
import com.daan.pws.match.enums.TeamEnum;
import com.daan.pws.match.hud.GameHud;
import com.daan.pws.match.hud.NotificationHud;
import com.daan.pws.utilities.PlayerUtil;
import com.daan.pws.weapon.WeaponManager;

public class CompetitiveTimer extends BukkitRunnable {

	private Competitive match;
	private boolean started = false;
	private boolean paused = false;

	public CompetitiveTimer(Competitive match) {
		this.match = match;
	}

	int pregame = 15;
	int ingame = 180;
	int bombtimer = -1;

	@Override
	public void run() {
		if (!paused)
			if (bombtimer == -1) {
				if (pregame > 0) {
					updateTime(pregame);
					--pregame;
				} else {
					if (ingame > 0) {
						if (ingame == 180) {
							unfreezeAll();
						}
						if (ingame == 134) {
							for (CompetitivePlayer player : match.getCounterTerrorists().getPlayers()) {
								if (player.getPlayer().getMainScreen().getActivePopup() != null && player.getPlayer().getMainScreen().getActivePopup() instanceof BuyGUIPopup) {
									player.getPlayer().getMainScreen().closePopup();
								}
							}
							for (CompetitivePlayer player : match.getTerrorists().getPlayers()) {
								if (player.getPlayer().getMainScreen().getActivePopup() != null && player.getPlayer().getMainScreen().getActivePopup() instanceof BuyGUIPopup) {
									player.getPlayer().getMainScreen().closePopup();
									NotificationHud.showAlert(player.getPlayer(), "The buytime has expired", 60, NotificationPriority.NORMAL);
								}
							}
						}

						if (ingame == 156) {
							match.addDeath("EfficienyPvP", "{t}Kierano1000", WeaponManager.getGun("AWP"), false);
							match.addDeath("{t}JackoDEJ", "Scude2", WeaponManager.getGun("AK-47"), true);
							match.addDeath("{t}bball0928", "EfficiencyPvP", WeaponManager.getGun("AUG"), true);
						}

						updateTime(ingame);
						--ingame;
					} else {
						match.winRound(TeamEnum.COUNTER_TERRORISTS);
					}
				}
			} else {
				if (bombtimer > 0) {
					--bombtimer;
				} else {
					match.winRound(TeamEnum.TERRORISTS);
					match.explodeBomb();
				}
			}
	}

	public void reset() {
		updateTimeVisibility(true);
		updateBombActive(false);
		this.pregame = 15;
		this.ingame = 180;
		this.bombtimer = -1;
		match.spawnAllPlayers();
		freezeAll();
		setPaused(false);
	}

	public void updateBombActive(boolean active) {
		for (CompetitivePlayer ctPlayer : match.getCounterTerrorists().getPlayers()) {
			GameHud.updateBombActive(ctPlayer.getPlayer(), active);
		}
		for (CompetitivePlayer tPlayer : match.getTerrorists().getPlayers()) {
			GameHud.updateBombActive(tPlayer.getPlayer(), active);
		}
	}

	public void updateTimeVisibility(boolean visible) {
		for (CompetitivePlayer ctPlayer : match.getCounterTerrorists().getPlayers()) {
			GameHud.updateTimeVisibility(ctPlayer.getPlayer(), visible);
		}
		for (CompetitivePlayer tPlayer : match.getTerrorists().getPlayers()) {
			GameHud.updateTimeVisibility(tPlayer.getPlayer(), visible);
		}
	}

	private void updateTime(int time) {
		for (CompetitivePlayer ctPlayer : match.getCounterTerrorists().getPlayers()) {
			GameHud.updateTime(ctPlayer.getPlayer(), time);
		}
		for (CompetitivePlayer tPlayer : match.getTerrorists().getPlayers()) {
			GameHud.updateTime(tPlayer.getPlayer(), time);
		}
	}

	private void freezeAll() {
		for (CompetitivePlayer ctPlayer : match.getCounterTerrorists().getPlayers()) {
			PlayerUtil.freeze(ctPlayer.getPlayer());
		}
		for (CompetitivePlayer tPlayer : match.getTerrorists().getPlayers()) {
			PlayerUtil.freeze(tPlayer.getPlayer());
		}
	}

	private void unfreezeAll() {
		for (CompetitivePlayer ctPlayer : match.getCounterTerrorists().getPlayers()) {
			PlayerUtil.unfreeze(ctPlayer.getPlayer());
		}
		for (CompetitivePlayer tPlayer : match.getTerrorists().getPlayers()) {
			PlayerUtil.unfreeze(tPlayer.getPlayer());
		}
	}

	public void start() {
		if (!started) {
			freezeAll();
			this.started = true;
			runTaskTimer(Main.getInstance(), 0, 20);
		} else {
			try {
				throw new Exception("Timer already started!");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public void plantBomb() {
		this.bombtimer = 45;
		updateBombActive(true);
		updateTimeVisibility(false);
	}

	public boolean isPregame() {
		return pregame != 0;
	}

	public boolean isBombPlanted() {
		return bombtimer != -1;
	}

	public boolean isInGame() {
		return ingame != 0;
	}

	public boolean isBuyTime() {
		return pregame == 0 && ingame >= 135;
	}

	public boolean isPaused() {
		return paused;
	}

	public void setPaused(boolean paused) {
		this.paused = paused;
	}

}
