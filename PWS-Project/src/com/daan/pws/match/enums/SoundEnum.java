package com.daan.pws.match.enums;

public enum SoundEnum {

	CT_WIN_ROUND(null), T_WIN_ROUND(null), BOMB_PLANTED(null), BOMB_DEFUSED(null);

	private String URL;

	private SoundEnum(String URL) {
		this.URL = URL;
	}

	public String getURL() {
		return URL;
	}

}
