package fr.jlc.polytech.scheduler.core;

import fr.jlc.polytech.scheduler.io.FileGenerator;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GeneratorTest {

    @Test
    void generateBox() {
        System.out.println("test_generateContent> box:");
        System.out.println(FileGenerator.generateContent(Generator.generateBox()));
    }

}