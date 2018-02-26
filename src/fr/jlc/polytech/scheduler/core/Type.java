package fr.jlc.polytech.scheduler.core;

public enum Type {
	CPU,
	GPU,
	IO;
	
	@Override
	public String toString() {
		switch (this)
		{
			case CPU:
				return "CPU";
			case GPU:
				return "GPU";
			case IO:
				return "I/O";
			default:
				return this.name().toUpperCase();
				
		}
	}
}
