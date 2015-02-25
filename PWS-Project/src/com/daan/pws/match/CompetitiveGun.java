package com.daan.pws.match;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.bukkit.craftbukkit.v1_6_R3.CraftWorld;
import org.bukkit.craftbukkit.v1_6_R3.inventory.CraftItemStack;
import org.bukkit.entity.Item;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.getspout.spoutapi.SpoutManager;
import org.getspout.spoutapi.inventory.SpoutItemStack;
import org.getspout.spoutapi.material.item.GenericCustomTool;
import org.getspout.spoutapi.player.SpoutPlayer;

import com.daan.pws.Main;
import com.daan.pws.entities.EntityCustomItem;
import com.daan.pws.utilities.ItemUtil;
import com.daan.pws.utilities.NumberUtil;
import com.daan.pws.utilities.PlayerUtil;
import com.daan.pws.weapon.Gun;
import com.daan.pws.weapon.WeaponManager;

public class CompetitiveGun {

	private static final Map<Integer, CompetitiveGun> competitiveGuns = new HashMap<Integer, CompetitiveGun>();
	private static final Random random = new Random();

	public static boolean isCompetitiveGun(ItemStack item) {
		int id = getId(item);
		if (id != -1) {
			return competitiveGuns.containsKey(id);
		}
		return false;
	}

	public static CompetitiveGun getCompetitiveGun(ItemStack item) {
		int id = getId(item);
		if (id != -1) {
			return competitiveGuns.get(id);
		}
		return null;
	}

	private static int getId(ItemStack item) {
		if (item != null && item.hasItemMeta() && item.getItemMeta().getLore() != null && item.getItemMeta().getLore().size() >= 1) {
			if (NumberUtil.isInteger(item.getItemMeta().getLore().get(0))) {
				return Integer.valueOf(item.getItemMeta().getLore().get(0));
			}
		}
		return -1;
	}

	public static int generateId() {
		int id = random.nextInt(1000000);
		while (competitiveGuns.containsKey(id)) {
			id = random.nextInt(1000000);
		}
		return id;
	}

	private final ItemStack gunItem;
	private final Gun gun;
	private final Competitive match;

	private int bulletsInMagazine;
	private int bulletsReserve;

	private final int id;

	private BukkitRunnable run;
	private int bullet = 1;

	public CompetitiveGun(Gun gun, Competitive match) {
		this.gun = gun;
		this.gunItem = ItemUtil.setItemMeta(new SpoutItemStack(gun.getGunItem()), gun.getName(), generateId() + "");
		this.bulletsInMagazine = gun.getBulletsInRound();
		this.bulletsReserve = gun.getTotalAmmo() - gun.getBulletsInRound();
		this.match = match;

		int id = getId(gunItem);
		if (id != -1) {
			this.id = id;
			competitiveGuns.put(id, this);
		} else {
			throw new IllegalArgumentException("The id returned was -1, either something went wrong with generating the id or the ItemStack does not have a competitive ID.");
		}
	}

	public ItemStack getGunItem() {
		return gunItem;
	}

	public int getBulletsInMagazine() {
		return bulletsInMagazine;
	}

	public int getBulletsReserve() {
		return bulletsReserve;
	}

	public Competitive getMatch() {
		return match;
	}

	public void shoot() {
		if (bulletsInMagazine > 0) {
			--bulletsInMagazine;
			++bullet;
		}
	}

	public Gun getGun() {
		return gun;
	}

	public String getGunName() {
		return gun.getName();
	}

	public void reload() {
		int reserve = bulletsReserve;
		int toReload = gun.getBulletsInRound() - bulletsInMagazine;
		bulletsReserve = bulletsReserve >= toReload ? bulletsReserve - toReload : 0;
		bulletsInMagazine = reserve >= toReload ? gun.getBulletsInRound() : bulletsInMagazine + reserve;
	}

	public void drop(CompetitivePlayer player) {
		int slot = gun.getWeaponType().getMainSlot();
		SpoutPlayer p = player.getPlayer();
		p.removePotionEffect(PotionEffectType.FAST_DIGGING);

		GenericCustomTool.setDurability(gunItem, (short) 0);
		EntityCustomItem eItem = new EntityCustomItem(((CraftWorld) p.getWorld()).getHandle(), p.getEyeLocation().getX(), p.getEyeLocation().getY(), p.getLocation().getZ(), CraftItemStack.asNMSCopy(gunItem));
		eItem.pickupDelay = 10;
		eItem.getBukkitEntity().setVelocity(p.getEyeLocation().getDirection().multiply(.4));
		((CraftWorld) p.getWorld()).getHandle().addEntity(eItem);
		player.getMatch().addDroppedItem((Item) eItem.getBukkitEntity());

		if (gun.getWeaponType().isPrimary()) {
			player.getLoadout().setPrimary(null);
		} else if (gun.getWeaponType().isSecondary()) {
			player.getLoadout().setSecondary(null);
		}

		p.getInventory().setItem(slot, null);
		if (p.getInventory().getHeldItemSlot() == slot) {
			com.daan.pws.match.hud.GunHud.removeGunHud(p);
			PlayerUtil.removePotionEffectSafe(p, 3, PotionEffectType.FAST_DIGGING);
			player.getPlayer().setWalkSpeed(0.3F);
			if (PlayerUtil.isZoomedIn(player.getPlayer())) {
				PlayerUtil.zoomOut(player.getPlayer());
			}
		}
		if (player.getLoadout().getPrimary() == this) {
			player.getLoadout().setPrimary(null);
		} else if (player.getLoadout().getSecondary() == this) {
			player.getLoadout().setSecondary(this);
		}
	}

	public void pickup(CompetitivePlayer player) {
		int slot = gun.getWeaponType().getMainSlot();

		player.getPlayer().getInventory().setItem(slot, gunItem);

		if (slot == player.getPlayer().getInventory().getHeldItemSlot()) {
			com.daan.pws.match.hud.GunHud.updateGunHud(player.getPlayer(), this);
			PlayerUtil.addPotionEffectNoParticles(player.getPlayer(), 3, 1029389, 100);
			player.getPlayer().setWalkSpeed((gun.getMovementSpeed() * 0.3F) / 250);
		}

		if (gun.getWeaponType().isPrimary()) {
			player.getLoadout().setPrimary(this);
		} else if (gun.getWeaponType().isSecondary()) {
			player.getLoadout().setSecondary(this);
		}
	}

	public void destroy() {
		competitiveGuns.remove(this.id);
	}

	public void reset() {
		this.bulletsInMagazine = gun.getBulletsInRound();
		this.bulletsReserve = gun.getTotalAmmo() - gun.getBulletsInRound();
	}

	public int getBullet() {
		return bullet;
	}

	public void startShooting() {
		if (run != null) {
			run.cancel();
		}
	}

	public void stopShooting() {
		if (run != null) {
			run.cancel();
		}
		run = new BukkitRunnable() {

			@Override
			public void run() {
				System.out.println(bullet);
				if (bullet > 1) {
					--bullet;
				} else {
					cancel();
					run = null;
				}
			}
		};
		run.runTaskTimer(Main.getInstance(), 1, 1);
	}

}
