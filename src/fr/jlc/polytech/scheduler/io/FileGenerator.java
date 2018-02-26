package fr.jlc.polytech.scheduler.io;

import com.sun.istack.internal.NotNull;
import fr.jlc.polytech.scheduler.core.*;

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
				 .append(" = [");
			
			for (Cluster cluster : box.getClusters()) {
				for (Machine machine : cluster) {
				
				}
			}
			
			build.append("]\n");
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
			
			// Delete last ", "
			build.deleteCharAt(build.length()-1);
			build.deleteCharAt(build.length()-1);
			
			build.append("]\n");
			
			taskNumber = 1;
			
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
