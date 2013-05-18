package com.litao.basic.innerclass;

import java.util.ArrayList;
import java.util.List;

abstract class Event {
	private long eventTime;
	protected final long delayTime;

	public Event(long delayTime) {
		this.delayTime = delayTime;
		start();
	}

	// when be called, will be restarting
	public void start() {
		this.eventTime = System.nanoTime() + this.delayTime;
	}

	public boolean ready() {
		return System.nanoTime() >= this.eventTime;
	}

	public abstract void action();
}

class Controller {
	private List<Event> eventList = new ArrayList<Event>();

	public void addEvent(Event event) {
		eventList.add(event);
	}

	public void run() {
		while (eventList.size() > 0) {
			for (Event event : new ArrayList<Event>(eventList)) {
				if (event.ready()) {
					System.out.println(event);
					event.action();
					eventList.remove(event);
				}
			}
		}
	}
}

public class GreenhouseControls extends Controller {
	private boolean light = false;

	private class LightOn extends Event {

		public LightOn(long delayTime) {
			super(delayTime);
		}

		@Override
		public void action() {
			light = true;
		}

		@Override
		public String toString() {
			return "Light is on";
		}

	}

	private class LightOff extends Event {

		public LightOff(long delayTime) {
			super(delayTime);
		}

		@Override
		public void action() {
			light = false;
		}

		@Override
		public String toString() {
			return "Light is off";
		}
	}

	private boolean water = false;

	private class WaterOn extends Event {

		public WaterOn(long delayTime) {
			super(delayTime);
		}

		@Override
		public void action() {
			water = true;
		}

		@Override
		public String toString() {
			return "water is on";
		}

	}

	private class WaterOff extends Event {

		public WaterOff(long delayTime) {
			super(delayTime);
		}

		@Override
		public void action() {
			water = false;
		}

		@Override
		public String toString() {
			return "water is off";
		}

	}

	private class Bell extends Event {

		public Bell(long delayTime) {
			super(delayTime);
		}

		@Override
		public void action() {
			addEvent(new Bell(delayTime));
		}

		@Override
		public String toString() {
			return "Bing!";
		}
	}

	private class Restart extends Event {
		private Event[] eventList;

		public Restart(long delayTime, Event[] eventList) {
			super(delayTime);
			this.eventList = eventList;
			for (Event event : eventList) {
				addEvent(event);
			}
		}

		@Override
		public void action() {
			for (Event event : eventList) {
				addEvent(event);
			}
			start();
			addEvent(this);
		}

		@Override
		public String toString() {
			return "Restarting system";
		}

	}

	private static class Terminate extends Event {

		public Terminate(long delayTime) {
			super(delayTime);
		}

		@Override
		public void action() {
			System.exit(0);
		}

		@Override
		public String toString() {
			return "Terminating";
		}

	}

	public static void main(String[] args) {
		GreenhouseControls gc = new GreenhouseControls();
		gc.addEvent(gc.new Bell(900));
		Event[] eventList = { gc.new LightOn(200), gc.new LightOff(400), gc.new WaterOn(600), gc.new WaterOff(800) };
		gc.addEvent(gc.new Restart(2000, eventList));
		gc.addEvent(new GreenhouseControls.Terminate(1));
		gc.run();
	}

}
