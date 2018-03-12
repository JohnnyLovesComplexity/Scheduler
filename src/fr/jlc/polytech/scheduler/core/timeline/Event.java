package fr.jlc.polytech.scheduler.core.timeline;

import org.jetbrains.annotations.NotNull;
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
	
	public static boolean isValid(@NotNull Event<?> event) {
		if (event == null)
			throw new NullPointerException();
		
		return event.getStart() <= event.getEnd();
	}
	public boolean isValid() {
		return isValid(this);
	}
	
	public static boolean areOverlapping(@NotNull Event<?> e1, @NotNull Event<?> e2) {
		if (e1 == null || e2 == null)
			throw new NullPointerException();
		
		if (!e1.isValid())
			throw new EventNotValidException(e1);
		
		if (!e2.isValid())
			throw new EventNotValidException(e2);
		
		/* Check that configuration :
		e1  ----
		e2   ----
		 */
		if (e1.getStart() < e2.getStart() && e2.getStart() < e1.getEnd() && e1.getEnd() < e2.getEnd())
			return true;
		
		/* Check that configuration :
		e1    ----
		e2 ----
		 */
		else if (e1.getStart() > e2.getStart() && e1.getStart() < e2.getEnd() && e1.getEnd() > e2.getEnd())
			return true;
		
		/* Check that configuration :
		e1    ----
		e2 --------
		 */
		else if (e1.getStart() > e2.getStart() && e1.getStart() < e2.getEnd() && e1.getEnd() < e2.getEnd())
			return true;
		
		/* Check that configuration :
		e1  --------
		e2    ----
		 */
		else if (e1.getStart() < e2.getStart() && e2.getStart() < e1.getEnd() && e1.getEnd() > e2.getEnd())
			return true;
		
		/* Check that configuration :
		e1  ----
		e2  ----
		 */
		else if (e1.getStart() == e2.getStart() && e1.getEnd() == e2.getEnd())
			return true;
		
		// Otherwise, the two events do not overlap
		else
			return false;
	}
	public boolean areOverlapping(@NotNull Event<?> event) {
		return areOverlapping(this, event);
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
		this.start = start;
	}
	
	public float getEnd() {
		return end;
	}
	
	public void setEnd(float end) {
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
