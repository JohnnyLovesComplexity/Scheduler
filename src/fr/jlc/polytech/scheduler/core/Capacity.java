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
	
	public int convertIntoTrueValue() {
		int trueValue;
		
		switch (getScale())
		{
			case 'K':
				trueValue = getValue() * (int) Math.pow(10, 3);
				break;
			case 'M':
				trueValue = getValue() * (int) Math.pow(10, 6);
				break;
			case 'G':
				trueValue = getValue() * (int) Math.pow(10, 9);
				break;
			case 'T':
				trueValue = getValue() * (int) Math.pow(10, 12);
				break;
			default:
				trueValue = getValue();
				break;
		}
		
		return trueValue;
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
	
	/* OVERRIDES */
	
	@Override
	public String toString() {
		return Integer.toString(getValue()) + Character.toString(Character.toUpperCase(getScale()));
	}
}
