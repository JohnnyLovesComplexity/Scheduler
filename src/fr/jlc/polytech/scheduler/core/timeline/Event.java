package fr.jlc.polytech.scheduler.core.timeline;

import org.jetbrains.annotations.Nullable;

import java.util.Objects;

public class Event<T> {
	
	@Nullable
	private T data;
	private float start;
	private float end;
	
	public Event(@Nullable T data, float start, float end) {
		setData(data);
		setStart(start);
		setEnd(end);
	}
	public Event(@Nullable T data) {
		this(data, 0, 0);
	}
	public Event(float start, float end) {
		this(null, start, end);
	}
	public Event() {
		this(null, 0, 0);
	}
	
	/* GETTERS & SETTERS */
	
	public @Nullable T getData() {
		return data;
	}
	
	public void setData(@Nullable T data) {
		this.data = data;
	}
	
	public float getStart() {
		return start;
	}
	
	public void setStart(float start) {
		if (start < 0)
			throw new IllegalArgumentException("Start must be greater or equal to 0.");
		
		if (start > end)
			throw new IllegalArgumentException("Start cannot be placed after End.");
		
		this.start = start;
	}
	
	public float getEnd() {
		return end;
	}
	
	public void setEnd(float end) {
		if (end < 0)
			throw new IllegalArgumentException("End must be greater or equal to 0.");
		
		if (end < start)
			throw new IllegalArgumentException("End cannot be placed before Start.");
		
		this.end = end;
	}
	
	public float getDuration() {
		return getEnd() - getStart();
	}
	
	public void setDuration(float duration) {
		setEnd(getStart() + duration);
	}
	
	/* OVERRIDES */
	
	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof Event)) return false;
		Event<?> event = (Event<?>) o;
		return getStart() == event.getStart() &&
				getEnd() == event.getEnd() &&
				Objects.equals(getData(), event.getData());
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(getData(), getStart(), getEnd());
	}
	
	@Override
	public String toString() {
		return "Event{" +
				"data=" + data +
				", start=" + start +
				", end=" + end +
				'}';
	}
}
