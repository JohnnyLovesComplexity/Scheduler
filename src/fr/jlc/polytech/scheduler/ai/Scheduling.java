package fr.jlc.polytech.scheduler.ai;

import fr.jlc.polytech.scheduler.core.Box;
import fr.jlc.polytech.scheduler.core.Machine;
import fr.jlc.polytech.scheduler.core.Task;
import fr.jlc.polytech.scheduler.core.timeline.EventBuilder;
import fr.jlc.polytech.scheduler.core.timeline.Timeline;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;

public class Scheduling {
    protected Timeline timeline;
    protected HashMap<Integer,Machine> machines = new HashMap<>();



    /**
     * Return the best line of the timeline where we can put our task depending on its compute time. The shorter time.
     * @param task
     * @return int Index of the line.
     */
    protected int bestLineTimeline(Task task){
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

    protected float getLineTime(Task task, int i) {
        int size = this.timeline.getEvents().get(i).size();
        float computeTime = this.machines.get(i).computeTimeOnMachine(task); //compute time of our task on the machine corresponding to the line
        return (this.timeline.getEvents().get(i).isEmpty())? 0: this.timeline.getEvents().get(i).get(size-1).getEnd()+computeTime;
    }


    /**
     * Initialize the timeline and the correspondance between the lines of the timeliens and the machines of the cluster.
     * @param box The box that we consider.
     */
    protected void initMachineTimeline(Box box){
        this.timeline = new Timeline();
        for (int i = 0; i < box.getClusters().get(0).size(); i++) {
            this.machines.put(i,box.getClusters().get(0).get(i));
            this.timeline.addTimeline();
        }
    }


    /**
     * The task has to begin after its dependencies are finished. In this case we find and return the end of the longest dependence task.
     * @param task The task that we consider.
     * @return float[] max et ligne de la timeline du max
     */
    protected float maxTimeDependencies(Task task){
        float max = 0;
        float end;
        for (int i = 0; i < this.timeline.getEvents().size() ; i++) {
            for (int j = 0; j < this.timeline.getEvents().get(i).size(); j++) {
                if(task.getDependencies().contains((Task) this.timeline.getEvents().get(i).get(j).getData())) {
                    end = this.timeline.getEvents().get(i).get(j).getEnd();
                    if(end > max)
                        max = end;
                }

            }
        }
        return max;
    }

    /**
     * Total processing time of all jobs.
     * @return float
     */
    protected float getTime(){
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

    protected int maxLineDependencies(float max){
        for (int i = 0; i < this.timeline.getEvents().size() ; i++) {
            for (int j = 0; j < this.timeline.getEvents().get(i).size(); j++) {
                if(this.timeline.getEvents().get(i).get(j).getEnd() == max)
                    return i;
            }
        }
        return -1;
    }

    protected void treatTask(@NotNull Box box, Task taskToTreat) {
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
        this.timeline.addEvent(lineToPut, new EventBuilder<Task>()
                .setStart(start)
                .setEnd(start + timeToCompute)
                .setDuration(timeToCompute)
                .setData(taskToTreat)
                .createEvent());

        box.getAccumulateTime().remove(taskToTreat); //We remove the task that we have treated
    }


}
