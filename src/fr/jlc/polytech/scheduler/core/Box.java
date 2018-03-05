package fr.jlc.polytech.scheduler.core;

import com.sun.istack.internal.NotNull;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

/**
 * Contains every machine (in cluster) and every task (in job) of the current situation
 */
public class Box {
	
	@NotNull
	private ArrayList<Cluster> clusters;
	
	@NotNull
	private ArrayList<Job> jobs;
	
	/* CONSTRUCTORS */
	
	public Box(@NotNull ArrayList<Cluster> clusters, @NotNull ArrayList<Job> jobs) {
		setClusters(clusters);
		setJobs(jobs);
	}
	public Box() {
		this(new ArrayList<>(), new ArrayList<>());
	}
	
	/* GETTERS & SETTERS */
	
	public @NotNull ArrayList<Cluster> getClusters() {
		if (this.clusters == null)
			this.clusters = new ArrayList<>();
		
		return this.clusters;
	}
	
	public void setClusters(@NotNull ArrayList<Cluster> clusters) {
		if (clusters == null)
			throw new NullPointerException();
		
		this.clusters = clusters;
	}
	
	public void addClusters(@NotNull Cluster... clusters) {
		if (clusters == null)
			throw new NullPointerException();
		
		for (Cluster cluster : clusters)
			if (cluster != null)
				getClusters().add(cluster);
	}
	public void addClusters(@NotNull ArrayList<Cluster> clusters) {
		if (clusters == null)
			throw new NullPointerException();
		
		getClusters().addAll(clusters);
	}
	
	public void addCluster(@NotNull Cluster cluster) {
		if (cluster == null)
			throw new NullPointerException();
		
		getClusters().add(cluster);
	}
	
	/**
	 * Add machines to the last cluster, or create a new cluster if the cluster list is empty
	 */
	public void addMachines(@NotNull Machine... machines) {
		if (machines == null)
			throw new NullPointerException();
		
		if (machines.length > 0) {
			if (getClusters().isEmpty())
				getClusters().add(new Cluster(machines));
			else
				getClusters().get(getClusters().size()-1).addAll(Arrays.asList(machines));
		}
	}
	public void addMachines(@NotNull ArrayList<Machine> machines) {
		if (machines == null)
			throw new NullPointerException();
		
		if (machines.size() > 0) {
			if (getClusters().isEmpty())
				getClusters().add(new Cluster(machines));
			else
				getClusters().get(getClusters().size()-1).addAll(machines);
		}
	}
	
	public void addMachine(@NotNull Machine machine) {
		if (machine == null)
			throw new NullPointerException();
		
		if (getClusters().isEmpty())
			getClusters().add(new Cluster(machine));
		else
			getClusters().get(getClusters().size()-1).add(machine);
	}
	
	public @NotNull ArrayList<Job> getJobs() {
		if (this.jobs == null)
			this.jobs = new ArrayList<>();
		
		return this.jobs;
	}
	
	public void setJobs(@NotNull ArrayList<Job> jobs) {
		if (jobs == null)
			throw new NullPointerException();
		
		this.jobs = jobs;
	}
	
	public void addJobs(@NotNull Job... jobs) {
		if (jobs == null)
			throw new NullPointerException();
		
		for (Job job : jobs)
			if (job != null)
				getJobs().add(job);
	}
	public void addJobs(@NotNull ArrayList<Job> jobs) {
		if (jobs == null)
			throw new NullPointerException();
		
		getJobs().addAll(jobs);
	}
	
	public void addJob(@NotNull Job job) {
		if (job == null)
			throw new NullPointerException();
		
		getJobs().add(job);
	}
	
	/**
	 * Add tasks to the last job, or create a new job if the job list is empty
	 */
	public void addTasks(@NotNull Task... tasks) {
		if (tasks == null)
			throw new NullPointerException();
		
		if (tasks.length > 0) {
			if (getJobs().isEmpty())
				getJobs().add(new Job(tasks));
			else
				getJobs().get(getJobs().size()-1).addAll(Arrays.asList(tasks));
		}
	}
	public void addTasks(@NotNull ArrayList<Task> tasks) {
		if (tasks == null)
			throw new NullPointerException();
		
		if (tasks.size() > 0) {
			if (getJobs().isEmpty())
				getJobs().add(new Job(tasks));
			else
				getJobs().get(getJobs().size()-1).addAll(tasks);
		}
	}
	
	public void addTask(@NotNull Task task) {
		if (task == null)
			throw new NullPointerException();
		
		if (getJobs().isEmpty())
			getJobs().add(new Job(task));
		else
			getJobs().get(getJobs().size()-1).add(task);
	}
	
	/* OVERRIDES */
	
	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof Box)) return false;
		Box box = (Box) o;
		return Objects.equals(getClusters(), box.getClusters()) &&
				Objects.equals(getJobs(), box.getJobs());
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(getClusters(), getJobs());
	}
	
	@Override
	public String toString() {
		return "Box{" +
				"clusters=" + clusters +
				", jobs=" + jobs +
				'}';
	}
}
