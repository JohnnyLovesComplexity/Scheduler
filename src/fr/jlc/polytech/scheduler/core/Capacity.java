package fr.jlc.polytech.scheduler.core;

public class Capacity {
	
	private long value;
	private char scale;
	
	public Capacity(long value, char scale) {
		setValue(value);
		setScale(scale);
	}
	public Capacity(long value) {
		this(value, ' ');
	}
	
	public long convertIntoTrueValue() {
		long trueValue;
		
		switch (getScale())
		{
			case 'K':
				trueValue = (long) (getValue() * Math.pow(10, 3));
				break;
			case 'M':
				trueValue = (long) (getValue() * Math.pow(10, 6));
				break;
			case 'G':
				trueValue = (long) (getValue() * Math.pow(10, 9));
				break;
			case 'T':
				trueValue = (long) (getValue() * Math.pow(10, 12));
				break;
			default:
				trueValue = getValue();
				break;
		}
		
		return trueValue;
	}

	public Capacity convertIntoCapacity(long value){

		double nb0 = Math.log10(value);
		Capacity c;
		int x =(int)nb0;
		if(x<6) {
			c = new Capacity(value/1000, 'K');
			return c;
		}
		else if (x>=6 && x<9) {
			c = new Capacity(value/1000000, 'M');
			return c;
		}
		else if (x>=9 && x<12){
			c = new Capacity(value/1000000000, 'G');
			return c;
		}
		else if (x>=12){
			c = new Capacity(value/1000000000000L, 'T');
			return c;
		}

		else {
			c = new Capacity (value);
			return c;
		}
	}


	
	/* GETTERS & SETTERS */
	
	public long getValue() {
		return value;
	}
	
	public void setValue(long value) {
		if (value <= 0)
			throw new IllegalArgumentException("Capacity value must be greater than 0");
		
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
		return Long.toString(getValue()) + Character.toString(Character.toUpperCase(getScale()));
	}
}
