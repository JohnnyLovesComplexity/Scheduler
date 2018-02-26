package fr.jlc.polytech.scheduler.core;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Random;

public class Generator {

    private int counterTask = 0;

    public Cluster generateCluster(){
        return (Cluster) generateMachines();
    }

    public ArrayList<Machine> generateMachines() {
        Random nb = new Random();
        ArrayList<Machine> list_machine = new ArrayList<Machine>();
        for (Type type : Type.values()) {
            int nbMachine = nb.nextInt(Cluster.MAX_MACHINE);
            for (int i = 0; i < nbMachine ; i++) {
                Random ct = new Random();
                /*
                TODO: PHILIPPINE !!! CHANGE CA !! CA BUUUUG
                int capacity = ct.nextInt() % (CAPACITY_MAX + 1 - CAPACITY_MIN) + CAPACITY_MIN;
                Machine machine = new Machine(type, new Capacity(capacity));
                list_machine.add(machine);
                */
            }
        }
        return list_machine;
    }

    public ArrayList<Task> generateTask(Cluster list_machine){
        Random r = new Random();
        ArrayList<Task> list_task = new ArrayList<Task>();
        int nb_task = r.nextInt(10000);
        for (int i = 0; i <nb_task ; i++) {
            Machine machine = list_machine.get(r.nextInt(list_machine.size()));
            Type type = machine.getType();
            Capacity capacity = generateCapacity(machine);
            ArrayList<Task> predecessor = new ArrayList<>();
            for (int j = 0; j < r.nextInt(list_task.size()) ; j++) {
                Task task = list_task.get(r.nextInt(list_task.size()));
                if(!predecessor.contains(task))
                    predecessor.add(task);
            }

            Task task = new Task(type,capacity,predecessor);
            list_task.add(task);
        }
        return list_task;
    }

    public ArrayList<Job> generateJob(Cluster list_machine){
        int counterTask = 0;
        ArrayList<Job> list_job = new ArrayList<>();
        while(counterTask < 10000){

        }

        return list_job;
    }

    public Box generateBox(){
        Cluster cluster = generateCluster();


        Box box = new Box();
        return box;
    }

    public Capacity generateCapacity(Machine machine){
        Random rd = new Random();
        int value = rd.nextInt() % (machine.getCapacity().getValue()*10 + 1);
        char scale = machine.getCapacity().getScale();
        return new Capacity(value,scale);
    }

}
