package fr.jlc.polytech.scheduler.io;

import com.sun.istack.internal.NotNull;
import com.sun.istack.internal.Nullable;
import fr.jlc.polytech.scheduler.core.*;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;

public class FileGenerator {
	
	public static final String path = "input_scheduler.txt";
	
	private FileGenerator() { }
	
	@SuppressWarnings("Duplicates")
	public static boolean generateFile(@NotNull Box box) {
		if (box == null)
			throw new NullPointerException();
		
		FileWriter fw = null;
		BufferedWriter bw = null;
		
		boolean problem = false;
		
		try {
			fw = new FileWriter(path);
			bw = new BufferedWriter(fw);
			
			bw.write(generateContent(box));
		} catch (IOException ex) {
			ex.printStackTrace();
			problem = true;
		} finally {
			try {
				if (bw != null)
					bw.close();
			} catch (IOException ex) {
				ex.printStackTrace();
				problem = true;
			}
			
			try {
				if (fw != null)
					fw.close();
			} catch (IOException ex) {
				ex.printStackTrace();
				problem = true;
			}
		}
		
		return !problem;
	}
	
	public static String generateContent(@NotNull Box box) {
		if (box == null)
			throw new NullPointerException();
		
		if (box.getClusters().isEmpty())
			throw new IllegalArgumentException("There is no cluster in the box.");
		if (box.getJobs().isEmpty())
			throw new IllegalArgumentException("There is no jobs in the box.");

		StringBuilder build = new StringBuilder();
		
		build.append("Servers\n");
		
		for (Type type : Type.values()) {
			build.append("\t")
				 .append(type.toString())
				 .append(" = [");
			
			for (Cluster cluster : box.getClusters()) {
				ArrayList<Machine> machineType = cluster.getAll(type);
				
				for (Machine machine : machineType) {
					build.append(machine.getCapacity().toString())
						 .append(", ");
				}
			}
			
			// Delete last ", "
			build.deleteCharAt(build.length()-1);
			build.deleteCharAt(build.length()-1);
			
			build.append("]\n");
		}
		
		int jobNumber = 1;
		int taskNumber;
		for (Job job : box.getJobs()) {
			build.append("Job ")
				 .append(jobNumber)
				 .append(" = [");
			
			
			// Each task will be affacted to a value, such as 'T1', 't2', ...
			HashMap<Task, String> tasks = new HashMap<>();
			
			taskNumber = 1;
			for (Task task : job) {
				tasks.put(task, "T" + taskNumber);
				taskNumber++;
			}
			
			for (String key : tasks.values()) {
				build.append(key)
					 .append(", ");
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
				
				
				for (Task predecessor : task.getDependencies()) {
					build.append(tasks.get(predecessor))
						 .append(", ");
				}
				
				if (task.getDependencies().size() > 0) {
					// Delete last ", "
					build.deleteCharAt(build.length() - 1);
					build.deleteCharAt(build.length() - 1);
				}
				
				build.append("]\n");
				
				// TODO: Finish that
				
				taskNumber++;
			}
			
			jobNumber++;
		}
		
		return build.toString();
	}
	
	
	public static @Nullable Box readBox() {
		System.out.println(readContent());
		return null;
	}
	
	public static @NotNull String readContent() {
		StringBuilder content = new StringBuilder();
		
		FileReader fr = null;
		BufferedReader br = null;
		
		try {
			fr = new FileReader(path);
			br = new BufferedReader(fr);
			
			String line;
			while ((line = br.readLine()) != null)
				content.append(line)
						.append("\n");
		} catch (IOException ex) {
			ex.printStackTrace();
		} finally {
			try {
				if (br != null)
					br.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
			
			try {
				if (fr != null)
					fr.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
		
		return content.toString();
	}
}
