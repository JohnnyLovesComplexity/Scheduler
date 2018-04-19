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
import java.util.List;
import java.util.Random;

/**
 * Alpha is a basic managers for a box.
 */
public class Alpha extends Scheduling implements Method {
	
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

		box.initAccumulateTime();
		initMachineTimeline(box); // associates machines with timeline lines

		Random random = new Random();

		while(!box.getAccumulateTime().isEmpty()){

			List<Task> keys      = new ArrayList<Task>(box.getAccumulateTime().keySet());
			Task taskToTreat = keys.get( random.nextInt(keys.size()) );
            //Alpha : We must treat its dependencies if they have not been processed yet.
            if(!dependenciesDone(taskToTreat))
				treatDependencies(taskToTreat,box);
            treatTask(box, taskToTreat);
            box.getAccumulateTime().remove(taskToTreat); //We remove the task that we have treated
        }


		/*for (Job job : box.getJobs()) {
			// If job is not null and not empty...
			if (job != null && !job.isEmpty()) {
				//... Then assign this job to a machine
				/* TODO: Find a solution at the following problem :
				* A job contains tasks that have al a different type. We cannot assign a job to machine.
				*/
		//	}
		//}

        //afficher la timeline
        System.out.println(timeline.toString("Beta Version : "));
        System.out.println(timeline.toStringWithTasks());
        System.out.println("Total time = " + getTime());
		
		return getTime();
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
	 * Return true if all the task that our task depends of have been done.
	 * @param task
	 * @return
	 */
	private boolean dependenciesDone(Task task){
		for (Task taskPred: task.getDependencies()) {
			boolean trouve = false;
			for (int i = 0; i < this.timeline.getEvents().size() ; i++) {
				for (int j = 0; j < this.timeline.getEvents().get(i).size(); j++) {
					if(taskPred.equals(this.timeline.getEvents().get(i).get(j).getData()))
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


			this.timeline.addEvent(lineToPut, new EventBuilder<Task>()
					.setStart(start)
					.setEnd(start + timeToCompute)
					.setDuration(timeToCompute)
					.setData(taskPred)
					.createEvent());

			box.getAccumulateTime().remove(taskPred); //We remove the task
		}
	}

}
