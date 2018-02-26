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

	public ArrayList<Task> create(){
        Random r = new Random();
        ArrayList<Task> list_task = new ArrayList<Task>();
        int nb_task = r.nextInt(10000);
        for (int i = 0; i <nb_task ; i++) {
            Type type = Type.values()[r.nextInt(Type.values().length)];
            int capacity =
            predecessor = list_task.get(r.nextInt(list_task.size()));
            Task task = new Task(, )
        }
    }
}
