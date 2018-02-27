package fr.jlc.polytech.scheduler.core;

import jdk.nashorn.internal.scripts.JO;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Random;

public class Generator {

    private static int counterTask = 0;

    private static Cluster generateCluster(){
       Cluster cluster = new Cluster();
        /*
          TODO : VALENTIN : change it into sthg that fits with your Cluster Class because I don't get it well.
          I'd like to have a cluster created with the method generateMachines()
         */
       ArrayList<Machine> machineArray = generateMachines();
       cluster.addAll(machineArray);
       return cluster;
    }

    private static ArrayList<Machine> generateMachines() {
        Random nb = new Random();
        ArrayList<Machine> list_machine = new ArrayList<Machine>();
        for (Type type : Type.values()) {
            int nbMachine = nb.nextInt(Cluster.MAX_MACHINE);
            for (int i = 0; i < nbMachine ; i++) {
                Random ct = new Random();

                long capacityMax = type.getCapacityMax().convertIntoTrueValue();
                long capacityMin = type.getCapacityMin().convertIntoTrueValue();
                long capacityValue = ct.nextInt() % (capacityMax + 1 - capacityMin) + capacityMin;
                Capacity capacity = new Capacity(10);
                capacity = capacity.convertIntoCapacity(capacityValue);

                try{//temporary
                    Machine machine = new Machine(type, capacity);
                    list_machine.add(machine);
                }catch(java.lang.ArithmeticException e){
                    System.out.println(e.getMessage());
                }
            }
        }
        return list_machine;
    }

    private static ArrayList<Task> generateTask(Cluster list_machine){
        Random r = new Random();
        ArrayList<Task> list_task = new ArrayList<Task>();
        int nb_task = r.nextInt(10000);
        for (int i = 0; i <nb_task ; i++) {
            Machine machine = list_machine.get(r.nextInt(list_machine.size()));
            Type type = machine.getType();
            Capacity capacity = generateCapacity(machine);
            ArrayList<Task> predecessor = new ArrayList<>();
            int a = r.nextInt(list_task.size()+1); // +1 ?
            for (int j = 0; j < a ; j++) {
                Task task = list_task.get(r.nextInt(list_task.size()));
                if(!predecessor.contains(task))
                    predecessor.add(task);
            }
            Task task = new Task(type,capacity,predecessor);
            list_task.add(task);
        }
        return list_task;
    }

    private static ArrayList<Job> generateJobs(Cluster list_machine){
        ArrayList<Job> list_job = new ArrayList<>();
        ArrayList<Task> list_task;
        Random rand = new Random();
        int maxTask = rand.nextInt()%((10000 - 50000 +1)+50000);
        while(counterTask < maxTask){
            do{ //We can't overflow 10000 tasks for all the jobs
                list_task = generateTask(list_machine);

            }while(list_task.size() + counterTask >=maxTask);
            Job job = (Job) list_task;
            list_job.add(job);
            counterTask += list_task.size();
        }
        return list_job;
    }

    @NotNull
    public static Box generateBox(){
        //For now we generate only one cluster in a box
        Cluster cluster = generateCluster();
        ArrayList<Cluster> list_cluster = new ArrayList<>();
        list_cluster.add(cluster);

        //Jobs
        ArrayList<Job> list_job = generateJobs(cluster);

        return new Box(list_cluster,list_job);
    }

    @NotNull
    private static Capacity generateCapacity(Machine machine){
        Random rd = new Random();
        long value = rd.nextLong() % (machine.getCapacity().getValue()*10 + 1);
        char scale = machine.getCapacity().getScale();
        return new Capacity(value,scale);
    }

}
