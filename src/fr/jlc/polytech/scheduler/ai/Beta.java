package fr.jlc.polytech.scheduler.ai;

import fr.jlc.polytech.scheduler.core.Box;
import org.jetbrains.annotations.NotNull;

/**
 * Beta manage a box using an advanced method (compared to Alpha) to assign jobs for the machine
 */
public class Beta implements Method {
    public int time; //Total processing time of all Jobs
	
    public Beta(){
    
    }
	
	/**
	 * Manage a box by assigning to the machine a list of job to process
	 * @param box The box to manage. It contains a list of cluster and a list of jobs.
	 * @return Return the time in seconds to compute all the jobs
	 */
    @Override
    public float manage(@NotNull Box box) {
	    checkBox(box);
        return 0;
    }
}
