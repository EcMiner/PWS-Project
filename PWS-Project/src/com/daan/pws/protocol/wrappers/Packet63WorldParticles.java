package com.daan.pws.protocol.wrappers;

import net.minecraft.server.v1_6_R3.Packet;

import com.daan.pws.particle.Particle;

public class Packet63WorldParticles extends AbstractPacket {

	public Packet63WorldParticles(Packet nmsPacket) {
		super(nmsPacket);
	}

	public Particle getParticle() {
		return Particle.fromName((String) getField("a"));
	}

	public void setParticle(Particle particle) {
		setField("a", particle.getName());
	}

	public float getX() {
		return getField("b");
	}

	public void setX(float value) {
		setField("b", value);
	}

	public float getY() {
		return getField("c");
	}

	public void setY(float value) {
		setField("c", value);
	}

	public float getZ() {
		return getField("d");
	}

	public void setZ(float value) {
		setField("d", value);
	}

	public float getOffsetX() {
		return getField("e");
	}

	public void setOffsetX(float value) {
		setField("e", value);
	}

	public float getOffsetY() {
		return getField("f");
	}

	public void setOffsetY(float value) {
		setField("f", value);
	}

	public float getOffsetZ() {
		return getField("g");
	}

	public void setOffsetZ(float value) {
		setField("g", value);
	}

	public float getSpeed() {
		return getField("h");
	}

	public void setSpeed(float value) {
		setField("h", value);
	}

	public int getAmount() {
		return getField("i");
	}

	public void setAmount(int value) {
		setField("i", value);
	}

}
