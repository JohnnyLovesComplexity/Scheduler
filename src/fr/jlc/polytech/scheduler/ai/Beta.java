package fr.jlc.polytech.scheduler.ai;

import fr.jlc.polytech.scheduler.core.Box;
import fr.jlc.polytech.scheduler.core.Machine;
import fr.jlc.polytech.scheduler.core.Task;
import fr.jlc.polytech.scheduler.core.timeline.EventBuilder;
import fr.jlc.polytech.scheduler.core.timeline.Timeline;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Set;

/**
 * Beta manage a box using an advanced method (compared to Alpha) to assign jobs for the machine
 */
public class Beta extends Scheduling implements Method {

    public Beta(){

    }
	
	/**
	 * Manage a box by assigning to the machine a list of job to process
	 * @param box The box to manage. It contains a list of cluster and a list of jobs.
	 * @return Return the time in seconds to compute all the jobs
	 */
    @Override
    public float manage(@NotNull Box box) {
	    checkBox(box);

	    box.fillAccumulateTime(); // compute priorities
        initMachineTimeline(box); // associates machines with timeline lines

        while(!box.getAccumulateTime().isEmpty()){
            //We treat the task with the longest accumulate time (priority)
            Task taskToTreat = maxTask(box.getAccumulateTime());
            treatTask(box, taskToTreat);
            box.getAccumulateTime().remove(taskToTreat); //We remove the task that we have treated
        }


        //afficher la timeline
        System.out.println(timeline.toString("Beta Version : "));
        System.out.println(timeline.toStringWithTasks());
        System.out.println("Total time = " + getTime());

        return getTime();
    }


    /**
     * The Task with the maximum compute time of our list.
     * @param map HashMap of our tasks as key with their compute time as value.
     * @return Task
     */
    private Task maxTask(HashMap<Task, Float> map){
        float maxValueInMap=(Collections.max(map.values()));
        Set cles = map.keySet();
        Task maxTask = null;
        for (Object cle : cles) {
            if(map.get(cle) == maxValueInMap){
                maxTask = (Task) cle;
                break;
            }
        }
        return maxTask;
    }


    public Timeline getTimeline() {
        return timeline;
    }
}
