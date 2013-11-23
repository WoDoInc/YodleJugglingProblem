package yodle.jugglefest.calculate;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ListIterator;
import java.util.Scanner;
import yodle.jugglefest.handlers.FileParser;
import yodle.jugglefest.containers.Circuit;
import yodle.jugglefest.containers.Juggler;

/**
 * Primary class for solving the Yodle juggle fest. The idea is to populate the circuits based on the highest
 * talent displayed for a given preference of circuits by a juggler. If the circuit is available, add the
 * juggler and <strike>remove the circuit from their preferences<strike>. Otherwise, if the circuit is full,
 * see if the juggler beats the weakest juggler in the circuit. If so, move the weakest juggler back into
 * the juggler pool to be allocated just again based on their preferences.
 * 
 * @author Alex Kaszczuk
 *
 */
public class YodleJuggleFest {

	//private final Logger log = Logger.getLogger(YodleJuggleFest.class.getName());
	private final File file = new File("./resource/jugglefest.txt");
	private final ArrayList<Circuit> clist = new ArrayList<Circuit>();
	private final ArrayList<Juggler> jlist = new ArrayList<Juggler>();

	/**
	 * This method parses the file and allocates all attributes to jugglers and circuit objects.
	 * 
	 * @throws IOException Thrown if an IOException occurs while handling file.
	 */
	public void parseFile() throws IOException {
		final Scanner scanner = new Scanner(file);
        scanner.useDelimiter(System.getProperty("line.separator"));
        while(scanner.hasNext()) {
        	final String line = scanner.next();
        	if (line.startsWith("C"))
        		clist.add(FileParser.parseCircuits(line));
        	else if (line.startsWith("J"))
        		jlist.add(FileParser.parseJugglers(line));
    	}
    	scanner.close();
	}
	
	/**
	 * Deploys the teams into their appropriate circuit based on preference and best possible attributes for
	 * a given team.
	 */
	public void deployTeams()
	{
		if (jlist.size() % clist.size() != 0)
			throw new RuntimeException("Cannot evenly distribute teams.");
		
		final int maxTeamSize = jlist.size()/clist.size();
		final ListIterator<Juggler> jugglerList = jlist.listIterator();

		while(jugglerList.hasNext())
		{
			final Juggler juggler = jugglerList.next();
			final List<Integer> preferences = new ArrayList<>(Arrays.asList(juggler.getPreferences()));
			int maximumDotProduct = 0;
			Integer bestPreference = 0;

			for (Integer preference : preferences) {
				final Circuit circuit = clist.get(preference);
				final int potentialMaxProduct = calculateDotProduct(juggler, circuit);
				if (potentialMaxProduct >= maximumDotProduct) {
					maximumDotProduct = potentialMaxProduct;
					bestPreference = preference;
				}
			}
			preferences.remove(bestPreference);
			final Integer[] updatedPreferences = new Integer[preferences.size()];
			preferences.toArray(updatedPreferences);
			juggler.setPreferences(updatedPreferences);
			final Circuit circuit = clist.get(bestPreference);
			
			if (circuit.getAllocatedJugglers().size() < maxTeamSize) {
				if (circuit.getWeakestJuggler() == null || calculateDotProduct(juggler, circuit) < calculateDotProduct(circuit.getWeakestJuggler(), circuit))
					circuit.setWeakestJuggler(juggler);
				circuit.addJuggler(juggler);
			} else if (circuit.getAllocatedJugglers().size() == maxTeamSize) {
				for (final Juggler jugglerCompetitor : circuit.getAllocatedJugglers()) {
					if (calculateDotProduct(jugglerCompetitor, circuit) < calculateDotProduct(juggler, circuit)) {
						circuit.removeAllocatedJuggler(circuit.getWeakestJuggler());
						circuit.addJuggler(juggler);
						circuit.setWeakestJuggler(juggler);
						jugglerList.add(jugglerCompetitor);
						break;
					}
					if (calculateDotProduct(jugglerCompetitor, circuit) < calculateDotProduct(circuit.getWeakestJuggler(), circuit))
						circuit.setWeakestJuggler(jugglerCompetitor);
				}
			} else {
				jugglerList.add(juggler);
			}
		}
	}
	
	/**
	 * Calculates the dot product for a given juggler and circuit.
	 * 
	 * @param juggler The juggler who's attributes will be used.
	 * @param circuit The circuit with it's attributes to be used.
	 * @return Returns the dot product as an int primitive.
	 */
	private static int calculateDotProduct(Juggler juggler, Circuit circuit) {
		return juggler.getCoordination() * circuit.getCoordination()
			+ juggler.getEndurance() * circuit.getEndurance()
			+ juggler.getPizzaz() * circuit.getPizzaz();
	}
	
	/**
	 * Gets the composition for a given circuit -- this is all assigned jugglers for the given circuit.
	 * 
	 * @param circuitNumber The circuit to report.
	 * @return Returns a formatted String of the circuit composition.
	 */
	public String getCircuitComposition(int circuitNumber)
	{
		return clist.get(circuitNumber).toString();
	}
	
	/**
	 * Main method.
	 * @param args Command line arguments, unused.
	 */
	public static void main(String[] args) {
		YodleJuggleFest yjf = new YodleJuggleFest();
		try {
			yjf.parseFile();
			yjf.deployTeams();
			System.out.println("Circuit: " + yjf.getCircuitComposition(1970));
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(1);
		}
		
		System.exit(0);
	}
}
