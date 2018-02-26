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
		
		Job job1 = new Job(
				new Task()
		);
	}
	
	@Test
	void test_generateContent() {
		// TODO: Test
	}
}