package fr.jlc.polytech.scheduler.core;

import com.sun.istack.internal.Nullable;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Random;

public class Job extends ArrayList<Task> implements Serializable {

	
	public Job(@Nullable Task... tasks) {
		super(tasks != null ? tasks.length : 3);
		
		if (tasks != null)
			for (Task task : tasks)
				if (task != null)
					add(task);
	}
	public Job(int initialCapacity) {
		super(initialCapacity);
	}
	
	/* OVERRIDES */
	
	@Override
	public String toString() {
		return super.toString();
	}
}
