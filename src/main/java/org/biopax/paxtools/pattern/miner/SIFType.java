package org.biopax.paxtools.pattern.miner;

/**
 * Enum for representing supported SIF edge types.
 *
 * @author Ozgun Babur
 */
public enum SIFType
{
	CONTROLS_STATE_CHANGE("First protein is controlling a reaction that changes the state of the " +
		"second protein.", true),
	CONTROLS_EXPRESSION("First protein is controlling a conversion or a template reaction that" +
		"changes expression of the second protein.", true),
	CONTROLS_DEGRADATION("First protein is controlling a reaction that degrades second protein," +
		" i.e. second protein is input to a reaction with no output", true),
	CONTROLS_METABOLIC_CATALYSIS("The protein is controlling a reaction of which the small " +
		"molecule is a participant.", true),
	CONSECUTIVE_CATALYSIS("First protein is controlling a reaction whose output molecule is input" +
		" to another reaction controlled by the second protein.", true),
	IN_SAME_COMPLEX("Proteins appear as members of the same complex.", false),
	CHEMICAL_AFFECTS_PROTEIN("A small molecule has an effect on a protein.", true),
	RELATED_THROUGH_INTERACTION("Proteins appear as participants or controllers of the same " +
		"interaction.", false);

	/**
	 * Constructor with parameters.
	 * @param description description of the edge type
	 * @param directed whether the edge type is directed
	 */
	private SIFType(String description, boolean directed)
	{
		this.description = description;
		this.directed = directed;
	}

	/**
	 * Description of the SIF type.
	 */
	private String description;

	/**
	 * Some SIF edges are directed and others are not.
	 */
	private boolean directed;


	/**
	 * Tag of a SIF type is derived from the enum name.
	 * @return tag
	 */
	public String getTag()
	{
		return name().toLowerCase().replaceAll("_", "-");
	}

	/**
	 * Asks if the edge is directed.
	 * @return true if directed
	 */
	public boolean isDirected()
	{
		return directed;
	}

	/**
	 * Gets the description of the SIF type.
	 * @return description
	 */
	public String getDescription()
	{
		return description;
	}
}
