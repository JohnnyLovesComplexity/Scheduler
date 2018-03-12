package fr.jlc.polytech.scheduler.core.timeline;

import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Objects;

public class Timeline {
	
	/**
	 * ArrayList of ArrayList of Event
	 */
	@NotNull
	private ArrayList<ArrayList<Event<?>>> events;
	
	public Timeline() {
		//
	}
	
	
	/* GETTERS & SETTERS */
	
	@NotNull
	protected ArrayList<ArrayList<Event<?>>> getEvents() {
		if (events == null)
			events = new ArrayList<>();
		
		return events;
	}
	
	protected void setEvents(@NotNull ArrayList<ArrayList<Event<?>>> events) {
		if (events == null)
			throw new NullPointerException();
		
		this.events = events;
	}
	
	public float getStart() {
		if (getEvents().isEmpty())
			return 0;
		
		if (getEvents().get(0).isEmpty())
			return 0;
		
		if (getEvents().get(0).get(0) == null)
			throw new NullPointerException("A null event has been found at (0 ; 0).");
		
		float min = getEvents().get(0).get(0).getStart();
		
		for (ArrayList<Event<?>> listEvent : events) {
			float result = getStart(listEvent);
			
			if (result < min)
				min = result;
		}
		
		return min;
	}
	protected float getStart(@NotNull ArrayList<Event<?>> events) {
		if (events == null)
			throw new NullPointerException();
		
		if (events.isEmpty())
			return 0;
		
		int min = 0;
		
		for (int i = 0; i < events.size(); i++)
			if (events.get(i).getStart() < events.get(min).getStart())
				min = i;
		
		return events.get(min).getStart();
	}
	
	/* OVERRIDES */
	
	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof Timeline)) return false;
		Timeline timeline = (Timeline) o;
		return Objects.equals(getEvents(), timeline.getEvents());
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(getEvents());
	}
	
	@Override
	public String toString() {
		return "Timeline{" +
				"events=" + events +
				'}';
	}
}
