package fr.jlc.polytech.scheduler;

import fr.jlc.polytech.scheduler.ai.Alpha;
import fr.jlc.polytech.scheduler.ai.Beta;
import fr.jlc.polytech.scheduler.core.Box;
import fr.jlc.polytech.scheduler.core.Generator;
import fr.jlc.polytech.scheduler.io.FileGenerator;

public class SchedulerMain {

    private static Box box;
    public SchedulerMain() {

    }

    public static void main(String[] args) {
        box = Generator.generateBox();
        System.out.println(FileGenerator.generateContent(box));

        Beta beta = new Beta();
        Alpha alpha = new Alpha();

        long debut = System.currentTimeMillis();
        beta.manage(box);
        System.out.println("Execution Time Beta : " + (System.currentTimeMillis()-debut) + " ms");

        debut = System.currentTimeMillis();
        alpha.manage(box);
        System.out.println("Execution Time Alpha : " + (System.currentTimeMillis()-debut) + " ms");

    }
}
