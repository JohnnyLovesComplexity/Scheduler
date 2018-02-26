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
		if( nb0>1 ){
			switch ((int)nb0){
				case 3:
					c = new Capacity(value/1000, 'K');
					break;
				case 6:
					c = new Capacity(value/1000000, 'M');
					break;
				case 9:
					c = new Capacity(value/1000000000, 'G');
					break;
				case 12:
					c = new Capacity(value/1000000000000L, 'T');
					break;
				default :
					c = new Capacity(value);

			}
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
		return Integer.toString(Math.toIntExact(getValue())) + Character.toString(Character.toUpperCase(getScale()));
	}
}
