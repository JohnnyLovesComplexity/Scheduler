package fr.jlc.polytech.scheduler.ai;

import fr.jlc.polytech.scheduler.core.Box;
import fr.jlc.polytech.scheduler.core.Job;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

/**
 * Alpha is a basic managers for a box.
 */
public class Alpha implements Method {
	
	@NotNull
	private ArrayList<Job> assignedJobs;
	
	public Alpha() {
		setAssignedJobs(new ArrayList<>());
	}
	
	/**
	 * Manage a box by assigning to the machine a list of job to process.
	 * In this class, only the first cluster is used.
	 * @param box The box to manage. It contains a list of cluster and a list of jobs.
	 * @return Return the time in seconds to compute all the jobs
	 */
	@Override
	public float manage(@NotNull Box box) {
		checkBox(box);
		
		for (Job job : box.getJobs()) {
			// If job is not null and not empty...
			if (job != null && !job.isEmpty()) {
				//... Then assign this job to a machine
				/* TODO: Find a solution at the following problem :
				* A job contains tasks that have al a different type. We cannot assign a job to machine.
				*/
			}
		}
		
		return 0;
	}
	
	/* GETTERS & SETTERS */
	
	@NotNull
	public ArrayList<Job> getAssignedJobs() {
		if (assignedJobs == null)
			assignedJobs = new ArrayList<>();
		
		return assignedJobs;
	}
	
	public void setAssignedJobs(@NotNull ArrayList<Job> assignedJobs) {
		if (assignedJobs == null)
			throw new NullPointerException();
		
		this.assignedJobs = assignedJobs;
	}
}
