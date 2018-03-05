package fr.jlc.polytech.scheduler.io;

import fr.jlc.polytech.scheduler.core.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FileGeneratorTest {
	
	Box box;
	
	@BeforeEach
	void setup() {
		box = new Box();
		box.getClusters().add(new Cluster(
				new Machine(Type.CPU, new Capacity(40, 'G')),
				new Machine(Type.CPU, new Capacity(10, 'G')),
				new Machine(Type.CPU, new Capacity(8, 'G')),
				
				new Machine(Type.GPU, new Capacity(25, 'T')),
				new Machine(Type.GPU, new Capacity(11, 'T')),
				
				new Machine(Type.IO, new Capacity(2, 'G')),
				new Machine(Type.IO, new Capacity(2, 'G')),
				new Machine(Type.IO, new Capacity(1, 'G'))
		));
		
		Task t11 = new Task(Type.CPU, new Capacity(400, 'G'));
		Task t12 = new Task(Type.CPU, new Capacity(9, 'T'));
		Task t13 = new Task(Type.GPU, new Capacity(500, 'T'), t11, t12);
		Task t14 = new Task(Type.IO, new Capacity(4, 'G'), t13);
		
		Job job1 = new Job(
				t11,
				t12,
				t13,
				t14
		);
		
		Task t21 = new Task(Type.GPU, new Capacity(800, 'G'));
		Task t22 = new Task(Type.CPU, new Capacity(1, 'T'), t21);
		Task t23 = new Task(Type.IO, new Capacity(4, 'G'), t22);
		
		Job job2 = new Job(
				t21,
				t22,
				t23
		);
		
		box.addJobs(job1, job2);
		
		System.out.println("FileGeneratorTest.setup> box: " + box.toString());
	}
	
	@Test
	void test_generateContent() {
		System.out.println("test_generateContent> box:");
		System.out.println(FileGenerator.generateContent(box));
	}
	
	@Test
	void test_generateFile() {
		FileGenerator.generateFile(box);
	}
	
	@Test
	void test_readContent() {
		System.out.println("test_readContent>\n" + FileGenerator.readContent());
	}
}