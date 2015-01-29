package com.daan.pws.entities;

import net.minecraft.server.v1_6_R3.DamageSource;
import net.minecraft.server.v1_6_R3.EntityItem;
import net.minecraft.server.v1_6_R3.ItemStack;
import net.minecraft.server.v1_6_R3.World;

public class EntityCustomItem extends EntityItem {

	// Deze Custom entity heb ik gemaakt omdat items in vuur verwijderd worden, maar dit gebeurt dan ook als een flashbang, of een andere granaat in het vuur
	// Van een molotov komt, dit wil ik natuurlijk niet hebben.
	public EntityCustomItem(World world) {
		super(world);
	}

	public EntityCustomItem(World world, double d0, double d1, double d2) {
		super(world, d0, d1, d2);
	}

	public EntityCustomItem(World world, double d0, double d1, double d2, ItemStack itemstack) {
		super(world, d0, d1, d2, itemstack);
	}

	@Override
	protected void burn(float i) {
	}

	@Override
	protected void burn(int i) {
	}

	@Override
	public boolean damageEntity(DamageSource damagesource, float f) {
		return false;
	}

}
