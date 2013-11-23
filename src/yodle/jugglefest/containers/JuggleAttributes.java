package yodle.jugglefest.containers;

/**
 * This interface describes the necessary attribute methods that should be implemented for all subclasses.
 *
 * @author Alex Kaszczuk
 *
 */
public interface JuggleAttributes {

	/**
	 * Gets the coordination attribute.
	 * 
	 * @return Returns a primitive int denoting the value for this attribute.
	 */
	public int getCoordination();
	
	/**
	 * Gets the endurance attribute.
	 * 
	 * @return Returns a primitive int denoting the value for this attribute.
	 */
	public int getEndurance();
	
	/**
	 * Gets the pizzaz attribute.
	 * 
	 * @return Returns a primitive int denoting the value for this attribute.
	 */
	public int getPizzaz();
}
