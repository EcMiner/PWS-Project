package com.daan.pws.match;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.getspout.spoutapi.player.SpoutPlayer;

import com.daan.pws.Main;
import com.daan.pws.events.PlayerDiedEvent;
import com.daan.pws.match.enums.DeathType;
import com.daan.pws.match.enums.TeamEnum;
import com.daan.pws.match.hud.GunHud;
import com.daan.pws.match.hud.HealthHud;
import com.daan.pws.match.items.Bomb;
import com.daan.pws.weapon.DamagePattern.PlayerHeight;

public class CompetitivePlayer {

	private final SpoutPlayer player;
	private TeamEnum team;
	private final CompetitiveLoadout loadout;
	private int money = 800, health = 100, armour = 0;
	private final Competitive match;
	private Map<String, Integer> damagers = new HashMap<String, Integer>();
	private String lastDamager;

	public CompetitivePlayer(SpoutPlayer player, TeamEnum team, Competitive match) {
		this.player = player;
		this.team = team;
		this.loadout = new CompetitiveLoadout(team, match);
		this.match = match;
	}

	public SpoutPlayer getPlayer() {
		return player;
	}

	public TeamEnum getTeam() {
		return team;
	}

	public void setTeam(TeamEnum team) {
		this.team = team;
	}

	public CompetitiveLoadout getLoadout() {
		return loadout;
	}

	public CompetitiveGun getInHand() {
		return CompetitiveGun.getCompetitiveGun(player.getItemInHand());
	}

	public int getMoney() {
		return money;
	}

	public void setMoney(int money) {
		this.money = money;
	}

	public int getHealth() {
		return health;
	}

	public void setHealth(int health) {
		this.health = health;
		HealthHud.updateHealth(player, health);
	}

	public int getArmour() {
		return armour;
	}

	public void setArmour(int armour) {
		this.armour = armour;
	}

	public Competitive getMatch() {
		return match;
	}

	public void damageArmour(int amount) {
		this.armour -= amount;
	}

	public void clearDamage() {
		damagers.clear();
	}

	public boolean isAlive() {
		return health > 0;
	}

	public void damageHealth(CompetitivePlayer player, int damage, PlayerHeight height) {
		if (health > 0) {
			if (player.getTeam() != getTeam()) {
				int dmg = damagers.containsKey(player.getPlayer().getName()) ? damagers.get(player.getPlayer().getName()) : 0;
				damagers.put(player.getPlayer().getName(), dmg + damage);
				this.lastDamager = player.getPlayer().getName();
			}

			setHealth(this.health - damage);
			if (this.health <= 0) {
				PlayerDiedEvent event = new PlayerDiedEvent(this, player, height == PlayerHeight.HEAD, DeathType.GUN);
				Main.getInstance().getServer().getPluginManager().callEvent(event);
			}
		}
	}

	public String getLastDamager() {
		return lastDamager;
	}

	public String[] getLastDamagers() {
		List<String> list = new ArrayList<String>();
		for (Entry<String, Integer> entry : damagers.entrySet()) {
			if (entry.getValue() >= 20) {
				list.add(entry.getKey());
			}
		}
		list.remove(lastDamager);

		Comparator<String> comparator = new Comparator<String>() {

			@Override
			public int compare(String s1, String s2) {
				int result = Integer.compare(damagers.get(s1), damagers.get(s2));
				return result == 1 ? -1 : (result == -1 ? 1 : 0);
			}
		};
		Collections.sort(list, comparator);
		if (list.size() > 0) {
			return new String[] { lastDamager, list.get(0) };
		}
		return new String[] { lastDamager };
	}

	public void resetPlayer() {
		this.setHealth(100);
		player.getInventory().clear();
		player.getInventory().setArmorContents(null);
		if (loadout.hasPrimary()) {
			loadout.getPrimary().reset();
			player.getInventory().setItem(loadout.getPrimary().getGun().getWeaponType().getMainSlot(), loadout.getPrimary().getGunItem());
		}
		if (loadout.hasSecondary()) {
			loadout.getSecondary().reset();
			player.getInventory().setItem(loadout.getSecondary().getGun().getWeaponType().getMainSlot(), loadout.getSecondary().getGunItem());
		}
		if (loadout.hasBomb()) {
			player.getInventory().setItem(8, Bomb.newBomb());
		}
		if (player.getItemInHand() != null) {
			if (CompetitiveGun.isCompetitiveGun(player.getItemInHand())) {
				GunHud.updateGunHud(player, CompetitiveGun.getCompetitiveGun(player.getItemInHand()));
			}
		}
	}

}
