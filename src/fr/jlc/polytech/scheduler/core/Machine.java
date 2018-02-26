package fr.jlc.polytech.scheduler.core;

import com.sun.istack.internal.NotNull;
import com.sun.istack.internal.Nullable;

import java.io.Serializable;

public class Machine implements Serializable {
	
	public static final int CAPACITY_MIN = 0;
	public static final int CAPACITY_MAX = 0;
	
	@NotNull
	private Type type;
	
	private int capacity;
	
	@Nullable
	private Job currentJob;
	
	/* CONSTRUCTORS */
	
	public Machine(@NotNull Type type, int capacity, @Nullable Job currentJob) {
		setType(type);
		setCapacity(capacity);
		setCurrentJob(currentJob);
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
	
	public int getCapacity() {
		return capacity;
	}
	
	protected void setCapacity(int capacity) {
		if (capacity < 0)
			throw new IllegalArgumentException("Capacity must be positive or null.");
		
		this.capacity = capacity;
	}
	
	public @Nullable Job getCurrentJob() {
		return currentJob;
	}
	
	public void setCurrentJob(@Nullable Job currentJob) {
		this.currentJob = currentJob;
	}
}
