package com.daan.pws.gun;

public class Gun {

	private String name;
	private String URL;
	private GunItem gunItem;
	private RecoilPattern recoilPattern;
	private DamagePattern damagePattern;

	public Gun(String name, String URL) {
		this.name = name;
		this.URL = URL;
		this.gunItem = new GunItem(name, URL);
	}

	public String getName() {
		return name;
	}

	public String getURL() {
		return URL;
	}

	public GunItem getGunItem() {
		return gunItem;
	}

	public RecoilPattern getRecoilPattern() {
		return recoilPattern;
	}

	public void setRecoilPattern(RecoilPattern recoilPattern) {
		this.recoilPattern = recoilPattern;
	}

	public DamagePattern getDamagePattern() {
		return damagePattern;
	}

	public void setDamagePattern(DamagePattern damagePattern) {
		this.damagePattern = damagePattern;
	}

}