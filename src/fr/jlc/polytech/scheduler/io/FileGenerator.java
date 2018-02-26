package fr.jlc.polytech.scheduler.io;

import com.sun.istack.internal.NotNull;
import fr.jlc.polytech.scheduler.core.Box;
import fr.jlc.polytech.scheduler.core.Job;
import fr.jlc.polytech.scheduler.core.Task;
import fr.jlc.polytech.scheduler.core.Type;

public class FileGenerator {
	
	public static final String path = "input_scheduler.txt";
	
	private FileGenerator() { }
	
	public static boolean generateFile(@NotNull Box box) {
		if (box == null)
			throw new NullPointerException();
		//
		return true;
	}
	
	public static String generateContent(@NotNull Box box) {
		if (box == null)
			throw new NullPointerException();
		
		if (box.getClusters().isEmpty() || box.getJobs().isEmpty())
			throw new IllegalArgumentException("There is no cluster nor jobs in the box.");
		
		StringBuilder build = new StringBuilder();
		
		build.append("Servers\n");
		
		for (Type type : Type.values()) {
			build.append("\t")
				 .append(type.toString())
				 .append(" = []\n");
		}
		
		int jobNumber = 1;
		int taskNumber;
		for (Job job : box.getJobs()) {
			build.append("Job ")
				 .append(jobNumber)
				 .append(" = [");
			
			taskNumber = 1;
			for (Task task : job) {
				build.append("T")
					 .append(taskNumber)
					 .append(", ");
				
				taskNumber++;
			}
			
			taskNumber = 1;
			
			build.append("\n");
			
			for (Task task : job) {
				build.append("\tT")
					 .append(taskNumber)
					 .append(" = ")
					 .append(task.getType().toString())
					 .append(", ")
					 .append(task.getCapacity().toString())
					 .append(", [");
				
				/*for (Task predecessor : task.getDependencies()) {
					build.append(predecessor)
				}*/
				build.append("]\n");
				
				// TODO: Finish that
				
				taskNumber++;
			}
			
			jobNumber++;
		}
		
		return build.toString();
	}
}
