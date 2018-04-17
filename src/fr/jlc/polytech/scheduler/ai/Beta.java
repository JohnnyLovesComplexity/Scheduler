package fr.jlc.polytech.scheduler.ai;

import fr.jlc.polytech.scheduler.core.Box;
import fr.jlc.polytech.scheduler.core.Machine;
import fr.jlc.polytech.scheduler.core.Task;
import fr.jlc.polytech.scheduler.core.timeline.EventBuilder;
import fr.jlc.polytech.scheduler.core.timeline.Timeline;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.HashMap;
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

	    box.fillAccumulateTime(); // compute priorities
        initMachineTimeline(box); // associates machines with timeline lines

        while(!box.getAccumulateTime().isEmpty()){
            //We treat the task with the longest accumulate time (priority)
            Task taskToTreat = maxTask(box.getAccumulateTime());

            //Now we can treat the task
            int lineToPut = bestLineTimeline(taskToTreat);
            float timeToCompute = this.machines.get(lineToPut).computeTimeOnMachine(taskToTreat);

            float start = (this.timeline.getEvents().get(lineToPut).isEmpty())? 0:this.timeline.getEvents().get(lineToPut).get(this.timeline.getEvents().get(lineToPut).size()-1).getEnd();
            //We compare this start time to the time that the dependencies need to finish
            float max = maxTimeDependencies(taskToTreat);
            start = Math.max(start,max +1);

            //Optimisation : if the place after the predecessor is empty we put the the task here.
            int line = maxLineDependencies(max);
            if(line != -1){
                if(start == max && machines.get(line).getType() == taskToTreat.getType())
                    lineToPut = line;
            }

            //We add a new event cooresponding to the current task
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
        System.out.println("Temps total = " + getTime());

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

    private float getTime(){
        float max = 0;
        for (int i = 0; i < this.timeline.getEvents().size() ; i++) {
            int size = this.timeline.getEvents().get(i).size();
            float lineTime = (this.timeline.getEvents().get(i).isEmpty())? 0: this.timeline.getEvents().get(i).get(size-1).getEnd();
            if(lineTime > max){
                max = lineTime;
            }
        }

        return max;
    }

    /**
     * The task has to begin after its dependencies are finished. In this case we find and return the end of the longest dependence task.
     * @param task
     * @return float[] max et ligne de la timeline du max
     */
    private float maxTimeDependencies(Task task){
        float max = 0;
        float end;
        for (int i = 0; i < this.timeline.getEvents().size() ; i++) {
            for (int j = 0; j < this.timeline.getEvents().get(i).size(); j++) {
                if(task.getDependencies().contains(this.timeline.getEvents().get(i).get(j).getTask())){
                    end = this.timeline.getEvents().get(i).get(j).getEnd();
                    if(end > max)
                        max = end;
                }

            }
        }
        return max;
    }

    private int maxLineDependencies(float max){
        for (int i = 0; i < this.timeline.getEvents().size() ; i++) {
            for (int j = 0; j < this.timeline.getEvents().get(i).size(); j++) {
                if(this.timeline.getEvents().get(i).get(j).getEnd() == max)
                    return i;
            }
        }
        return -1;
    }

}
