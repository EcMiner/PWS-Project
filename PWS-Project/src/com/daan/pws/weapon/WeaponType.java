package com.daan.pws.weapon;

public enum WeaponType {

	PISTOLS(1), RIFLES(0), GRENADES(3, 4, 5, 6), HEAVY(0), SMG(0), KNIFE(2);

	private int mainSlot;
	private int[] reserveSlots;

	private WeaponType(int mainSlot, int... reserveSlots) {
		this.mainSlot = mainSlot;
		this.reserveSlots = reserveSlots;
	}

	public int getMainSlot() {
		return mainSlot;
	}

	public int[] getReserveSlots() {
		return reserveSlots;
	}

}
