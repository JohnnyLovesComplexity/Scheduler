package fr.jlc.polytech.scheduler.core;

public class Capacity {
	
	private int value;
	private char scale;
	
	public Capacity(int value, char scale) {
		setValue(value);
		setScale(scale);
	}
	public Capacity(int value) {
		this(value, ' ');
	}
	
	/* GETTERS & SETTERS */
	
	public int getValue() {
		return value;
	}
	
	public void setValue(int value) {
		if (value < 0)
			throw new IllegalArgumentException("Capacity value must be greater or equal to 0");
		
		this.value = value;
	}
	
	public char getScale() {
		return scale;
	}
	
	public void setScale(char scale) {
		scale = Character.toUpperCase(scale);
		
		if (scale == ' ' ||
			scale == 'K' ||
			scale == 'M' ||
			scale == 'G' ||
			scale == 'T')
			this.scale = scale;
		else
			throw new IllegalArgumentException("Scale can be ' ' (unit), 'K', 'M', 'G' or 'T'");
	}
}
