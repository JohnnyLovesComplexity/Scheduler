package fr.jlc.polytech.scheduler.core;

import fr.jlc.polytech.scheduler.io.FileGenerator;
import org.junit.jupiter.api.Test;

class GeneratorTest {

    @Test
    void test_generateBox() {
        System.out.println("test_generateBox> box:");
        Box box = Generator.generateBox();
        System.out.println(FileGenerator.generateContent(box));
    }

}