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
        alpha.manage(box);
    }

}