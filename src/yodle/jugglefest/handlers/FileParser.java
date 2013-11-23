package yodle.jugglefest.handlers;

import java.io.IOException;
import java.util.Scanner;
import java.util.regex.MatchResult;
import yodle.jugglefest.containers.Circuit;
import yodle.jugglefest.containers.Juggler;

public class FileParser {

	/**
	 * Parses a line for the supported format denoting a circuit.
	 * Returns a Circuit object based on the found attributes.
	 * The circuit number will be parsed as an int primitive instead of a String object.
	 * Thus, "C0" will become "0". Correlation can be maintained more easily using raw primitives.  
	 * 
	 * Format Example:
	 * C C0 H:7 E:7 P:10
	 * C C1 H:2 E:1 P:1
	 * C C2 H:7 E:6 P:4
	 * 
	 * @param line A line from the parsed file as a String.
	 */
	public static Circuit parseCircuits(final String line)
    {
		final Scanner scanner = new Scanner(line);
		scanner.findInLine("C C(\\d+) H:(\\d+) E:(\\d+) P:(\\d+)");
	    final MatchResult result = scanner.match();
	    scanner.close(); 
		return new Circuit(Integer.parseInt(result.group(1)), Integer.parseInt(result.group(2)), Integer.parseInt(result.group(3)), Integer.parseInt(result.group(4)));
    }
   
	/**
	 * Parses a line for the supported format denoting a juggler.
	 * Returns a Juggler object based on the found attributes.
	 * The circuit number will be parsed as an int primitive instead of a String object.
	 * The juggler number will also be parsed as an int primitive instead of a String object.
	 * Thus, "C0" will become "0" and "J0" will become "0". Correlation can be maintained more
	 * easily using raw primitives.
	 * 
	 * Format Example:
	 * J J11997 H:7 E:6 P:2 C1123,C248,C1069,C1342,C485,C993,C707,C1321,C1559,C1543
	 * J J11998 H:0 E:4 P:8 C1927,C351,C81,C271,C1630,C1287,C1314,C69,C127,C1797
	 * J J11999 H:5 E:6 P:0 C1608,C1667,C1162,C1798,C1714,C785,C1838,C430,C441,C1539
	 * 
	 * @param line A line from the parsed file as a String.
	 * @return
	 * @throws IOException
	 */
    public static Juggler parseJugglers(final String line) throws IOException
    {
    	final Scanner scanner = new Scanner(line);
	    final Integer[] preferences = new Integer[10];
		scanner.findInLine("J J(\\d+) H:(\\d+) E:(\\d+) P:(\\d+)");
	    MatchResult result = scanner.match();
	    final Juggler juggler = new Juggler(Integer.parseInt(result.group(1)), Integer.parseInt(result.group(2)), Integer.parseInt(result.group(3)), Integer.parseInt(result.group(4)));
	    scanner.findInLine("C(\\d+),C(\\d+),C(\\d+),C(\\d+),C(\\d+),C(\\d+),C(\\d+),C(\\d+),C(\\d+),C(\\d+)");
	    result = scanner.match();
	    for (int i = 1; i <= result.groupCount(); i++) {
	    	preferences[i-1] = Integer.parseInt(result.group(i));
	    }
	    juggler.setPreferences(preferences);
	    scanner.close();
    	return juggler;
    }
		
}
