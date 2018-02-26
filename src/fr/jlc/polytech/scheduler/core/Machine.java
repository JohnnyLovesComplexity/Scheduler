package fr.jlc.polytech.scheduler.core;

import com.sun.istack.internal.NotNull;
import com.sun.istack.internal.Nullable;

import java.io.Serializable;

public class Machine implements Serializable {
	
	public static final int CAPACITY_MIN = 0;
	public static final int CAPACITY_MAX = 100;
	
	@NotNull
	private Type type;
	
	@NotNull
	private Capacity capacity;
	
	@Nullable
	private Job currentJob;
	
	/* CONSTRUCTORS */
	
	public Machine(@NotNull Type type, @NotNull Capacity capacity, @Nullable Job currentJob) {
		setType(type);
		setCapacity(capacity);
		setCurrentJob(currentJob);
	}
	public Machine(@NotNull Type type, @NotNull Capacity capacity) {
		this(type, capacity, null);
	}
	
	public boolean isOccupied() {
		if (currentJob == null)
			return true;
		
		if (currentJob.isEmpty())
			return true;
		
		return false;
	}
	
	public boolean process() {
		if (currentJob != null && !currentJob.isEmpty()) {
			// TODO: Process the current job and empty it
			currentJob.clear();
			return true;
		}
		else
			return false;
	}
	
	/* GETTERS & SETTERS */
	
	public @NotNull Type getType() {
		return type;
	}
	
	protected void setType(@NotNull Type type) {
		if (type == null)
			throw new NullPointerException();
		
		this.type = type;
	}
	
	public @NotNull Capacity getCapacity() {
		return capacity;
	}
	
	protected void setCapacity(@NotNull Capacity capacity) {
		if (capacity == null)
			throw new NullPointerException();
		
		this.capacity = capacity;
	}
	
	public @Nullable Job getCurrentJob() {
		return currentJob;
	}
	
	public void setCurrentJob(@Nullable Job currentJob) {
		this.currentJob = currentJob;
	}
}
