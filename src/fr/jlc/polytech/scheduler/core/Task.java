package fr.jlc.polytech.scheduler.core;

import java.io.Serializable;
import java.util.ArrayList;

public class Task implements Serializable {
    private String type;
    private int capacity;
    private ArrayList<Task> dependencies;

    public Task(String type, int capacity, ArrayList<Task> dependencies) {
        this.type = type;
        this.capacity = capacity;
        this.dependencies = dependencies;
    }

    public boolean detectInfiniteLoops(Task t, ArrayList<Task> visited){
        if (visited.contains(t)){
            return true;
        }
        visited.add(t);
        for (Task p : t.dependencies) {
            if (detectInfiniteLoops(p, visited)){
                return true;
            }
        }
        return false;
    }
}
