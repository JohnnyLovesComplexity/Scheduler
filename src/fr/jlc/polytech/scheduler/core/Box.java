package fr.jlc.polytech.scheduler.core;

import com.sun.istack.internal.NotNull;

import java.util.ArrayList;
import java.util.Arrays;

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
}
