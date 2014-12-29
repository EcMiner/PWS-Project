package com.daan.pws.scheduler;

import java.util.ArrayList;
import java.util.List;

import com.daan.pws.Main;

public class Scheduler {

	private static List<ERunnable> runnables = new ArrayList<ERunnable>();

	public static void scheduleRepeatingTask(final ERunnable run, final long startdelay, final long delay) {
		Runnable runnable = new Runnable() {

			boolean first = false;

			@Override
			public void run() {
				while (run.isRunning() && Main.getInstance().isEnabled()) {
					if (!first) {
						try {
							Thread.sleep(startdelay);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
						first = true;
					}
					run.run();
					try {
						Thread.sleep(delay);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				Scheduler.removeTask(run);
			}

		};
		new Thread(runnable).start();
		runnables.add(run);
	}

	public static void scheduleRepeatingBukkitTask(final ERunnable run, final long startdelay, final long delay) {
		Runnable runnable = new Runnable() {

			boolean first = false;

			@Override
			public void run() {
				while (run.isRunning() && Main.getInstance().isEnabled()) {
					if (!first) {
						try {
							Thread.sleep(startdelay * 50);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
						first = true;
					}
					run.run();
					try {
						Thread.sleep(delay * 50);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				Scheduler.removeTask(run);
			}

		};
		new Thread(runnable).start();
		runnables.add(run);
	}

	public static void scheduleDelayedTask(final ERunnable run, final long delay) {
		Runnable runnable = new Runnable() {

			@Override
			public void run() {
				try {
					Thread.sleep(delay);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				if (run.isRunning()) {
					run.run();
				}
				Scheduler.removeTask(run);
			}

		};
		new Thread(runnable).start();
		runnables.add(run);
	}

	public static void scheduleDelayedBukkitTask(final ERunnable run, final long delay) {
		Runnable runnable = new Runnable() {

			@Override
			public void run() {
				try {
					Thread.sleep(delay * 50);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				if (run.isRunning()) {
					run.run();
				}
				Scheduler.removeTask(run);
			}

		};
		new Thread(runnable).start();
		runnables.add(run);
	}

	public static void removeTask(ERunnable run) {
		run.cancel();
		runnables.remove(run);
	}

	public static void cancelAlTasks() {
		for (ERunnable run : runnables) {
			run.cancel();
		}
		runnables.clear();
	}

}
