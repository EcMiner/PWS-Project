package com.daan.pws.entities;

import java.lang.reflect.Field;
import java.util.List;

import net.minecraft.server.v1_6_R3.Block;
import net.minecraft.server.v1_6_R3.Entity;
import net.minecraft.server.v1_6_R3.EntityHuman;
import net.minecraft.server.v1_6_R3.EntityZombie;
import net.minecraft.server.v1_6_R3.PathfinderGoalLookAtPlayer;
import net.minecraft.server.v1_6_R3.PathfinderGoalSelector;
import net.minecraft.server.v1_6_R3.World;

public class StatusZombie extends EntityZombie {

	public StatusZombie(World world) {
		super(world);
		clearPathFinders(goalSelector);
		clearPathFinders(targetSelector);
		this.goalSelector.a(0, new PathfinderGoalLookAtPlayer(this, EntityHuman.class, 8f));
	}

	@SuppressWarnings("rawtypes")
	private void clearPathFinders(PathfinderGoalSelector pathFinder) {
		try {
			Field b = pathFinder.getClass().getDeclaredField("b");
			Field c = pathFinder.getClass().getDeclaredField("c");

			b.setAccessible(true);
			c.setAccessible(true);

			List bList = (List) b.get(pathFinder);
			List cList = (List) c.get(pathFinder);
			bList.clear();
			cList.clear();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	protected void a(int i, int j, int k, Block block) {
	}

	@Override
	public void collide(Entity entity) {
		g(0d, 0d, 0d);
	}

}
