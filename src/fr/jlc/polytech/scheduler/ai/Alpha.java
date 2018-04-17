package fr.jlc.polytech.scheduler.ai;

import fr.jlc.polytech.scheduler.core.Box;
import fr.jlc.polytech.scheduler.core.Job;
import fr.jlc.polytech.scheduler.core.Machine;
import fr.jlc.polytech.scheduler.core.Task;
import fr.jlc.polytech.scheduler.core.timeline.EventBuilder;
import fr.jlc.polytech.scheduler.core.timeline.Timeline;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Alpha is a basic managers for a box.
 */
public class Alpha implements Method {
	
	@NotNull
	private ArrayList<Job> assignedJobs;

	private Timeline timeline;
	private HashMap<Integer,Machine> machines = new HashMap<>();
	
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

		//initMachineTimeline(box); // associates machines with timeline lines


		//Alpha : Our task is a priority task: in this case we must treat its dependencies if they have not been processed yet.
            /*if(!dependenciesDone(taskToTreat))
                treatDependencies(taskToTreat,box);*/
		
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


	/**
	 * Return the best line of the timeline where we can put our task depending on its compute time. The shorter time.
	 * @param task
	 * @return int Index of the line.
	 */
	/*private int bestLineTimeline(Task task){
		int indexMin = 0;
		if(!timeline.getEvents().isEmpty()){
			float min = 50000;
			for (int i = 0; i < this.timeline.getEvents().size() ; i++) {
				if(this.machines.get(i).getType() != task.getType())
					continue;

				float newTime = getLineTime(task, i);
				if(newTime < min){
					indexMin = i;
					min = newTime;
				}
			}
		}
		return indexMin;
	}

	private float getLineTime(Task task, int i) {
		int size = this.timeline.getEvents().get(i).size();
		float computeTime = this.machines.get(i).computeTimeOnMachine(task); //compute time of our task on the machine corresponding to the line
		return (this.timeline.getEvents().get(i).isEmpty())? 0: this.timeline.getEvents().get(i).get(size-1).getEnd()+computeTime;
	}


	/**
	 * Return true if all the task that our task depends of have been done.
	 * @param task
	 * @return
	 */
	/*private boolean dependenciesDone(Task task){
		for (Task taskPred: task.getDependencies()) {
			boolean trouve = false;
			for (int i = 0; i < this.timeline.getEvents().size() ; i++) {
				for (int j = 0; j < this.timeline.getEvents().get(i).size(); j++) {
					if(this.timeline.getEvents().get(i).get(j).getTask().equals(taskPred))
						trouve = true;
				}
			}
			if(!trouve)
				return false;
		}
		return true;
	}

	private void treatDependencies(Task task, Box box){

		for (Task taskPred: task.getDependencies()) {
			int lineToPut = bestLineTimeline(taskPred);
			float timeToCompute = this.machines.get(lineToPut).computeTimeOnMachine(taskPred);

			//We add a new event cooresponding to the current task
			float start = (this.timeline.getEvents().get(lineToPut).isEmpty())? 0:this.timeline.getEvents().get(lineToPut).get(this.timeline.getEvents().get(lineToPut).size()-1).getEnd();

			this.timeline.addEvent(lineToPut, new EventBuilder<String>()
					.setStart(start)
					.setEnd(start + timeToCompute)
					.setDuration(timeToCompute)
					.setTask(taskPred)
					.createEvent());

			box.getAccumulateTime().remove(taskPred); //We remove the task
		}
	}

	/**
	 * Initialize the timeline and the correspondance between the lines of the timeliens and the machines of the cluster.
	 * @param box The box that we consider.
	 */
	/*private void initMachineTimeline(Box box){
		this.timeline = new Timeline();
		for (int i = 0; i < box.getClusters().get(0).size(); i++) {
			this.machines.put(i,box.getClusters().get(0).get(i));
			this.timeline.addTimeline();
		}
	}*/

}
