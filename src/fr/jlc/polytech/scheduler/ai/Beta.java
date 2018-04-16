package fr.jlc.polytech.scheduler.ai;

import fr.jlc.polytech.scheduler.core.Box;
import fr.jlc.polytech.scheduler.core.Cluster;
import fr.jlc.polytech.scheduler.core.Machine;
import fr.jlc.polytech.scheduler.core.Task;
import fr.jlc.polytech.scheduler.core.timeline.Event;
import fr.jlc.polytech.scheduler.core.timeline.EventBuilder;
import fr.jlc.polytech.scheduler.core.timeline.Timeline;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

/**
 * Beta manage a box using an advanced method (compared to Alpha) to assign jobs for the machine
 */
public class Beta implements Method {
    private float time; //Total processing time of all Jobs
    private Timeline timeline;
    private HashMap<Integer,Machine> machines = new HashMap<>();

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
	    box.fillAccumulateTime();
        initMachineTimeline(box);

        //We consider only the first cluster
        //this.cluster = box.getClusters().get(0);
        while(!box.getAccumulateTime().isEmpty()){
            Task taskToTreat = maxTask(box.getAccumulateTime());
            int lineToPut = bestLineTimeline(taskToTreat);
            float timeToCompute = this.machines.get(lineToPut).computeTimeOnMachine(taskToTreat);

            //We add a new event cooresponding to the current task
            float start = (this.timeline.getEvents().get(lineToPut).isEmpty())? 0:this.timeline.getEvents().get(lineToPut).get(this.timeline.getEvents().get(lineToPut).size()-1).getEnd();

            this.timeline.addEvent(lineToPut, new EventBuilder<String>()
                    .setStart(start)
                    .setEnd(start + timeToCompute)
                    .setDuration(timeToCompute)
                    .setTask(taskToTreat)
                    .createEvent());

            box.getAccumulateTime().remove(taskToTreat); //We remove the task that we have treated
        }


        //afficher la timeline
        System.out.println(timeline.toString("Version Beta : "));
        System.out.println(timeline.toString(true));

        //Modfier le temps = prendre la timeline la plus longue et retourner sa fin
        return 0;
    }

    /**
     * Return the best line of the timeline where we can put our task depending on its compute time. The shorter time.
     * @param task
     * @return int Index of the line.
     */
    private int bestLineTimeline(Task task){
        int indexMin = 0;
        if(!timeline.getEvents().isEmpty()){
            float min = (this.timeline.getEvents().get(0).isEmpty())?
                    0:this.timeline.getEvents().get(0).get(this.timeline.getEvents().get(0).size()-1).getEnd() + this.machines.get(0).computeTimeOnMachine(task);
            for (int i = 1; i < this.timeline.getEvents().size() ; i++) {
                if(this.machines.get(i).getType() != task.getType())
                    continue;
                int size = this.timeline.getEvents().get(i).size();
                float computeTime = this.machines.get(i).computeTimeOnMachine(task); //compute time of our task on the machine corresponding to the line
                float newTime = (this.timeline.getEvents().get(i).isEmpty())? 0:this.timeline.getEvents().get(i).get(size-1).getEnd()+computeTime;
                if(newTime < min){
                    indexMin = i;
                    min = newTime;
                }
            }
        }
        return indexMin;
    }

    /**
     * The Task with the maximum compute time of our list.
     * @param map HashMap of our tasks as key with their compute time as value.
     * @return Task
     */
    private Task maxTask(HashMap<Task, Float> map){
        Set cles = map.keySet();
        Task maxTask = null;
        float max = 0;
        for (Object cle : cles) {
            if(map.get(cle)>max)
                maxTask = (Task) cle; // tu peux typer plus finement ici
        }
        return maxTask;
    }

    /**
     * Initialize the timeline and the correspondance between the lines of the timeliens and the machines of the cluster.
     * @param box The box that we consider.
     */
    private void initMachineTimeline(Box box){
        this.timeline = new Timeline();
        for (int i = 0; i < box.getClusters().get(0).size(); i++) {
            this.machines.put(i,box.getClusters().get(0).get(i));
            this.timeline.addTimeline();
        }
    }

}
