package fr.jlc.polytech.scheduler.ai;

import fr.jlc.polytech.scheduler.core.*;
import fr.jlc.polytech.scheduler.io.FileGenerator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BetaTest extends SchedulingTest{

    private Beta beta;

    @Test
    void test_manage() {
        beta = new Beta();

        //box = Generator.generateBox();

        System.out.println(FileGenerator.generateContent(box));


        long debut = System.currentTimeMillis();
        beta.manage(box);
        System.out.println("Execution Time Beta : " + (System.currentTimeMillis()-debut) + " ms");
    }

}