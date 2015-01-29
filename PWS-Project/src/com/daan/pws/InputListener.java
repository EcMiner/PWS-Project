package com.daan.pws;

import org.getspout.spoutapi.event.input.KeyPressedEvent;
import org.getspout.spoutapi.event.input.KeyReleasedEvent;

public abstract interface InputListener {

	public abstract void keyPressed(KeyPressedEvent evt);

	public abstract void keyReleased(KeyReleasedEvent evt);

}
