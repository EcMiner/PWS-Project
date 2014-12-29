package com.daan.pws.scheduler;

public abstract class ERunnable {

	private boolean running = true;

	public abstract void run();

	public void cancel() {
		this.running = false;
	}

	public boolean isRunning() {
		return running;
	}

	public void runTaskTimer(long delay, long period) {
		Scheduler.scheduleRepeatingTask(this, delay, period);
	}

	public void runTaskBukkitTimer(long delay, long period) {
		Scheduler.scheduleRepeatingBukkitTask(this, delay, period);
	}

	public void runTaskLater(long delay) {
		Scheduler.scheduleDelayedTask(this, delay);
	}

	public void runTaskBukkitLater(long delay) {
		Scheduler.scheduleDelayedBukkitTask(this, delay);
	}

}
