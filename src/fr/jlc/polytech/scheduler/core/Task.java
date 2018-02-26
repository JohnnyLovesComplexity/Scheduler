package fr.jlc.polytech.scheduler.core;

import com.sun.istack.internal.NotNull;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;

public class Task implements Serializable {
	
	@NotNull
    private Type type;
	@NotNull
    private Capacity capacity;
    @NotNull
    private ArrayList<Task> dependencies;

    public Task(@NotNull Type type, @NotNull Capacity capacity, @NotNull ArrayList<Task> dependencies) {
    	setType(type);
    	setCapacity(capacity);
    	setDependencies(dependencies);
    }
    public Task(@NotNull Type type, @NotNull Capacity capacity, @NotNull Task... dependencies) {
    	if (dependencies == null)
    		throw new NullPointerException();
    	
	    ArrayList<Task> list = new ArrayList<>(dependencies.length);
	
	    if (dependencies.length > 0)
	    	list.addAll(Arrays.asList(dependencies));
		
	    setType(type);
	    setCapacity(capacity);
	    setDependencies(list);
    }
	public Task(@NotNull Type type, @NotNull Capacity capacity) {
    	this(type, capacity, new ArrayList<>());
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
    
    /* GETTERS & SETTERS */
	
	public @NotNull Type getType() {
		return this.type;
	}
	
	public void setType(@NotNull Type type) {
		if (type == null)
			throw new NullPointerException();
		
		this.type = type;
	}
	
	public @NotNull Capacity getCapacity() {
		return capacity;
	}
	
	public void setCapacity(@NotNull Capacity capacity) {
		if (capacity == null)
			throw new NullPointerException();
		
		this.capacity = capacity;
	}
	
	public @NotNull ArrayList<Task> getDependencies() {
		if (this.dependencies == null)
			this.dependencies = new ArrayList<>();
		
		return this.dependencies;
	}
	
	public void setDependencies(@NotNull ArrayList<Task> dependencies) {
		if (dependencies == null)
			throw new NullPointerException();
		
		this.dependencies = dependencies;
	}
}
