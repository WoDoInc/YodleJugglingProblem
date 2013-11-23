package yodle.jugglefest.containers;

import java.util.HashSet;

final public class Circuit implements JuggleAttributes {

	final private int circuit;
	final private int coordination;
	final private int endurance;
	final private int pizzaz;
	final private HashSet<Juggler> allocatedJugglers = new HashSet<>();
	private Juggler weakestJuggler = null;
	
	public Circuit (final int circuit, final int coordination, final int endurance, final int pizzaz) {
		this.circuit = circuit;
		this.coordination = coordination;
		this.endurance = endurance;
		this.pizzaz = pizzaz;
	}
	
	final public int getCircuit() {
		return circuit;
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
	
	final public HashSet<Juggler> getAllocatedJugglers() {
		return allocatedJugglers;
	}
	
	final public boolean addJuggler(Juggler juggler) {
		return allocatedJugglers.add(juggler) ? true : false;
			
	}
	
	final public boolean removeAllocatedJuggler(Juggler juggler) {
		return allocatedJugglers.remove(juggler) ? true : false;
	}
	
	final public Juggler getWeakestJuggler() {
		return weakestJuggler;
	}
	
	final public void setWeakestJuggler(Juggler juggler) {
		weakestJuggler = juggler;
	}
	
	@Override
	final public String toString() {
		return "C" + circuit + " " + allocatedJugglers.toString();
	}
}
