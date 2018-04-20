package fr.jlc.polytech.scheduler.ai;

import fr.jlc.polytech.scheduler.io.FileGenerator;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AlphaTest extends SchedulingTest{

    private Alpha alpha;

    @Test
    void test_manage() {
        alpha = new Alpha();

        //box = Generator.generateBox();

        System.out.println(FileGenerator.generateContent(box));

        long debut = System.currentTimeMillis();
        alpha.manage(box);
        System.out.println("Execution Time Beta : " + (System.currentTimeMillis() - debut) + " ms");
    }

}