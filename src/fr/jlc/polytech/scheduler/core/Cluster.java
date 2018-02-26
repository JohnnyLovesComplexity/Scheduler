package fr.jlc.polytech.scheduler.core;

import com.sun.istack.internal.NotNull;
import com.sun.istack.internal.Nullable;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Random;

import static fr.jlc.polytech.scheduler.core.Machine.CAPACITY_MAX;
import static fr.jlc.polytech.scheduler.core.Machine.CAPACITY_MIN;

public class Cluster extends ArrayList<Machine> implements Serializable {

    public static final int MAX_MACHINE = 100;
	
	public Cluster(@Nullable Machine... machines) {
		super(machines != null ? machines.length : 3);
		
		if (machines != null)
			for (Machine machine : machines)
				if (machine != null)
					add(machine);
	}
	public Cluster(int initialCapacity) {
		super(initialCapacity);
	}
}
