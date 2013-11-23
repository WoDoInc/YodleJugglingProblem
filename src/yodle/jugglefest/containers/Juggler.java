package yodle.jugglefest.containers;

final public class Juggler implements JuggleAttributes {
	
	final private int juggler;
	final private int coordination;
	final private int endurance;
	final private int pizzaz;
	private Integer[] preferences;
	
	public Juggler (final int juggler, final int coordination, final int endurance, final int pizzaz) {
		this.juggler = juggler;
		this.coordination = coordination;
		this.endurance = endurance;
		this.pizzaz = pizzaz;
		this.preferences = new Integer[] {-1,-1,-1,-1,-1,-1,-1,-1,-1,-1};
	}
	
	final public int getJuggler() {
		return juggler;
	}
	
	@Override
	final public int getCoordination() {
		return coordination;
	}

	@Override
	final public int getEndurance() {
		return endurance;
	}

	@Override
	final public int getPizzaz() {
		return pizzaz;
	}
	
	final public void setPreferences(Integer[] preferences) {
		this.preferences = preferences;
	}
	
	final public Integer[] getPreferences() {
		return preferences;
	}
	
	@Override
	final public String toString() {
		return new String("J" + juggler);
	}
}