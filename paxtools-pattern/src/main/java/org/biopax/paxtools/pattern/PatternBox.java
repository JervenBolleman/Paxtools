package org.biopax.paxtools.pattern;

import org.biopax.paxtools.model.level3.*;
import org.biopax.paxtools.pattern.c.*;

import java.util.Map;
import java.util.Set;

/**
 * This class contains several pre-defined patterns.
 *
 * @author Ozgun Babur
 */
public class PatternBox
{
	public static Pattern inSameComplex()
	{
		Pattern p = new Pattern(7, EntityReference.class, "ER 1");
		int i = 0;
		p.addConstraint(ConBox.erToPE(), i, ++i);
		p.addConstraint(ConBox.genericEquiv(), i, ++i);
		p.addConstraint(ConBox.complexes(), i, ++i);
		p.addConstraint(ConBox.complexMembers(), i, ++i);
		p.addConstraint(ConBox.genericEquiv(), i, ++i);
		p.addConstraint(new Type(SimplePhysicalEntity.class), i);
		p.addConstraint(new PEChainsIntersect(false), i-4, i-3, i-1, i);
		p.addConstraint(ConBox.peToER(), "ER 2", i, ++i);
		p.addConstraint(new Equality(false), 0, i);
		return p;
	}

	public static Pattern inSameActiveComplex()
	{
		Pattern p = inSameComplex();
		p.addConstraint(new ActivityConstraint(true), 3);
		return p;
	}
	
	public static Pattern inSameComplexHavingTransActivity()
	{
		Pattern p = inSameComplex();
		int i = p.getLastIndex();
		p.increaseVariableSizeBy(2);

		p.addConstraint(ConBox.peToControl(), 3, ++i);
		p.addConstraint(ConBox.controlToTempReac(), "TemplateReaction", i, ++i);
		p.addConstraint(new NOT(ConBox.participantER()), i, 0);
		p.addConstraint(new NOT(ConBox.participantER()), i, 6);
		return p;
	}

	public static Pattern inSameComplexEffectingConversion()
	{
		Pattern p = inSameComplex();
		int i = p.getLastIndex();
		p.increaseVariableSizeBy(2);

		p.addConstraint(ConBox.peToControl(), 3, ++i);
		p.addConstraint(ConBox.controlToConv(), "Conversion", i, ++i);
		p.addConstraint(new NOT(ConBox.participantER()), i, 0);
		p.addConstraint(new NOT(ConBox.participantER()), i, 6);
		return p;
	}

	public static Pattern controlsStateChange(boolean considerGenerics)
	{
		Pattern p = new Pattern(5, EntityReference.class, "ER controller");
		int i = 0;
		p.addConstraint(ConBox.erToPE(), i, ++i);
		if (considerGenerics) p.addConstraint(new LinkedPE(LinkedPE.Type.TO_COMPLEX), i, ++i);
		else p.addConstraint(ConBox.withComplexes(), i, ++i);
		p.addConstraint(ConBox.peToControl(), i, ++i);
		p.addConstraint(ConBox.controlToConv(), i, ++i);

		Pattern p2 = stateChange(considerGenerics);
		p.addPattern(p2, i);

		return p;
	}

	public static Pattern stateChange(boolean considerGenerics)
	{
		Pattern p = new Pattern(7, Conversion.class, "Conversion");
		int i = 0;

		p.addConstraint(ConBox.left(), "left PE", i, ++i);
		if (considerGenerics) p.addConstraint(new LinkedPE(LinkedPE.Type.TO_MEMBER), i, ++i);
		else p.addConstraint(ConBox.withSimpleMembers(), i, ++i);
		p.addConstraint(ConBox.peToER(), "ER left", i, ++i);
		p.addConstraint(ConBox.right(), "right PE", p.indexOf("Conversion"), ++i);
		if (considerGenerics) p.addConstraint(new LinkedPE(LinkedPE.Type.TO_MEMBER), i, ++i);
		else p.addConstraint(ConBox.withSimpleMembers(), i, ++i);
		p.addConstraint(ConBox.peToER(), "ER right", i, ++i);
		p.addConstraint(new Equality(false), p.indexOf("left PE"), p.indexOf("right PE"));
		return p;
	}


	public static Pattern consecutiveCatalysis(Set<String> ubiqueIDs)
	{
		Pattern p = new Pattern(11, EntityReference.class, "ER 1");
		int i = 0;
		p.addConstraint(ConBox.erToPE(), i, ++i);
		p.addConstraint(new LinkedPE(LinkedPE.Type.TO_COMPLEX), i, ++i);
		p.addConstraint(ConBox.peToControl(), i, ++i);
		p.addConstraint(ConBox.controlToConv(), "conv1", i, ++i);
		p.addConstraint(new ParticipatingPE(RelType.OUTPUT, false), i-1, i, ++i);
		if (ubiqueIDs != null) p.addConstraint(ConBox.notUbique(ubiqueIDs), i);
		p.addConstraint(new ParticipatesInConv(RelType.INPUT, false), "conv2", i, ++i);
		p.addConstraint(new RelatedControl(RelType.INPUT), i-1, i, ++i);
		p.addConstraint(ConBox.controllerPE(), i, ++i);
		p.addConstraint(new NOT(ConBox.compToER()), i, 0);
		p.addConstraint(new LinkedPE(LinkedPE.Type.TO_MEMBER), i, ++i);
		p.addConstraint(ConBox.peToER(), "ER 2", i, ++i);
		return p;
	}

	public static Pattern basicEffection(boolean active,
		Map<EntityReference, Set<ModificationFeature>> activityFeat,
		Map<EntityReference, Set<ModificationFeature>> inactivityFeat)
	{
		Pattern p = controlsStateChange(true);
		int i = p.getVariableSize() - 1; // index of last variable
		p.addConstraint(new OR(
			new MappedConst(ConBox.differentialActivity(active), 0, 1),
			new MappedConst(new ModificationChangeConstraint(active, activityFeat, inactivityFeat), 
				2, 3)),
			i-7, i-6, i-3, i-2);
		return p;
	}

	public static Pattern peInOut()
	{
		Pattern p = new Pattern(7, EntityReference.class);
		int i = 0;
		p.addConstraint(ConBox.erToPE(), i, ++i);
		p.addConstraint(new LinkedPE(LinkedPE.Type.TO_COMPLEX), i, ++i);
		p.addConstraint(new ParticipatesInConv(RelType.INPUT, true), i, ++i);
		p.addConstraint(new OtherSide(), i-1, i, ++i);
		p.addConstraint(new Equality(false), i-2, i);
		p.addConstraint(new LinkedPE(LinkedPE.Type.TO_MEMBER), i, ++i);
		p.addConstraint(ConBox.peToER(), i, ++i);
		p.addConstraint(new Equality(true), 0, i);
		return p;
	}

	public static Pattern modifiedPESimple()
	{
		Pattern p = new Pattern(4, EntityReference.class);
		int i = 0;
		p.addConstraint(ConBox.erToPE(), i, ++i);
		p.addConstraint(ConBox.participatesInConv(), i, ++i);
		p.addConstraint(new OtherSide(), i-1, i, ++i);
		p.addConstraint(new Equality(false), i-2, i);
		p.addConstraint(ConBox.peToER(), i, 0);
		return p;
	}

	public static Pattern actChange(boolean activating,
		Map<EntityReference, Set<ModificationFeature>> activityFeat,
		Map<EntityReference, Set<ModificationFeature>> inactivityFeat)
	{
		Pattern p = peInOut();
		p.addConstraint(new OR(
			new MappedConst(ConBox.differentialActivity(activating), 0, 1),
			new MappedConst(new ModificationChangeConstraint(activating, activityFeat, inactivityFeat), 0, 1)),
			1, 7);
		return p;
	}	

	public static Pattern modifierConv()
	{
		Pattern p = new Pattern(5, EntityReference.class);
		int i = 0;
		p.addConstraint(ConBox.erToPE(), i, ++i);
		p.addConstraint(ConBox.genericEquiv(), i, ++i);
		p.addConstraint(ConBox.withComplexes(), i, ++i);
		p.addConstraint(ConBox.inOrOutConv(), i, ++i);
		return p;
	}

	public static Pattern hasNonSelfEffect()
	{
		Pattern p = new Pattern(6, PhysicalEntity.class);
		int i = 0;
		p.addConstraint(ConBox.peToER(), i, ++i);
		p.addConstraint(ConBox.genericEquiv(), i-1, ++i);
		p.addConstraint(ConBox.withComplexes(), i, ++i);
		p.addConstraint(ConBox.peToControl(), i, ++i);
		p.addConstraint(ConBox.controlToInter(), i, ++i);
		p.addConstraint(new NOT(ConBox.participantER()), i, 1);
		return p;
	}

	// Patterns for PSB

	public static Pattern changesStateOf()
	{
		Pattern p = new Pattern(11, ProteinReference.class);
		int i = 0;
		p.addConstraint(ConBox.erToPE(), i, ++i);
		p.addConstraint(new LinkedPE(LinkedPE.Type.TO_COMPLEX), i, ++i);
		p.addConstraint(ConBox.peToControl(), i, ++i);
		p.addConstraint(ConBox.controlToConv(), i, ++i);
		p.addConstraint(ConBox.left(), i, ++i); // PE_L
		p.addConstraint(ConBox.right(), i-1, ++i); // PE_R
		p.addConstraint(new Equality(false), i-1, i);
		p.addConstraint(new Equality(false), i-1, 1);
		p.addConstraint(new Equality(false), i-1, 2);
		p.addConstraint(new Equality(false), i, 1);
		p.addConstraint(new Equality(false), i, 2);
		p.addConstraint(new LinkedPE(LinkedPE.Type.TO_MEMBER), i-1, ++i);
		p.addConstraint(new LinkedPE(LinkedPE.Type.TO_MEMBER), i-1, ++i);
		p.addConstraint(new Equality(false), i-1, 1);
		p.addConstraint(new Equality(false), i-1, 2);
		p.addConstraint(new Equality(false), i, 1);
		p.addConstraint(new Equality(false), i, 2);
		p.addConstraint(ConBox.peToER(), i-1, ++i);
		p.addConstraint(ConBox.peToER(), i-1, ++i);
		p.addConstraint(new Equality(true), i-1, i);
		p.addConstraint(new Equality(false), 0, i);
		return p;
	}

	public static Pattern bindsTo()
	{
		Pattern p = new Pattern(7, ProteinReference.class);
		int i = 0;
		p.addConstraint(ConBox.erToPE(), i, ++i);
		p.addConstraint(new LinkedPE(LinkedPE.Type.TO_COMPLEX), i, ++i);
		p.addConstraint(ConBox.complexes(), i, ++i);
		p.addConstraint(ConBox.complexMembers(), i, ++i);
		p.addConstraint(new LinkedPE(LinkedPE.Type.TO_MEMBER), i, ++i);
		p.addConstraint(ConBox.peToER(), i, ++i);
		p.addConstraint(new Equality(false), 0, i);
		return p;
	}

	public static Pattern physicallyInteracts()
	{
		Pattern p = new Pattern(7, ProteinReference.class);
		int i = 0;
		p.addConstraint(ConBox.erToPE(), i, ++i);
		p.addConstraint(new LinkedPE(LinkedPE.Type.TO_COMPLEX), i, ++i);
		p.addConstraint(ConBox.molecularInteraction(), i, ++i);
		p.addConstraint(ConBox.participant(), i, ++i);
		p.addConstraint(new LinkedPE(LinkedPE.Type.TO_MEMBER), i, ++i);
		p.addConstraint(ConBox.peToER(), i, ++i);
		p.addConstraint(new Equality(false), 0, i);
		return p;
	}

	public static Pattern transcriptionWithTemplateReac()
	{
		Pattern p = new Pattern(8, ProteinReference.class);
		int i = 0;
		p.addConstraint(ConBox.erToPE(), i, ++i);
		p.addConstraint(new LinkedPE(LinkedPE.Type.TO_COMPLEX), i, ++i);
		p.addConstraint(ConBox.peToControl(), i, ++i);
		p.addConstraint(ConBox.controlToTempReac(), i, ++i);
		p.addConstraint(ConBox.product(), i, ++i);
		p.addConstraint(new LinkedPE(LinkedPE.Type.TO_MEMBER), i, ++i);
		p.addConstraint(ConBox.peToER(), i, ++i);
		p.addConstraint(new Equality(false), 0, i);
		return p;
	}

	public static Pattern transcriptionWithConversion()
	{
		Pattern p = new Pattern(8, ProteinReference.class);
		int i = 0;
		p.addConstraint(ConBox.erToPE(), i, ++i);
		p.addConstraint(new LinkedPE(LinkedPE.Type.TO_COMPLEX), i, ++i);
		p.addConstraint(ConBox.peToControl(), i, ++i);
		p.addConstraint(ConBox.controlToConv(), i, ++i);
		p.addConstraint(new Empty(ConBox.left()), i);
		p.addConstraint(ConBox.right(), i, ++i);
		p.addConstraint(new LinkedPE(LinkedPE.Type.TO_MEMBER), i, ++i);
		p.addConstraint(ConBox.peToER(), i, ++i);
		p.addConstraint(new Equality(false), 0, i);
		return p;
	}

	public static Pattern degradation()
	{
		Pattern p = new Pattern(8, ProteinReference.class);
		int i = 0;
		p.addConstraint(ConBox.erToPE(), i, ++i);
		p.addConstraint(new LinkedPE(LinkedPE.Type.TO_COMPLEX), i, ++i);
		p.addConstraint(ConBox.peToControl(), i, ++i);
		p.addConstraint(ConBox.controlToConv(), i, ++i);
		p.addConstraint(new Empty(ConBox.right()), i);
		p.addConstraint(ConBox.left(), i, ++i);
		p.addConstraint(new LinkedPE(LinkedPE.Type.TO_MEMBER), i, ++i);
		p.addConstraint(ConBox.peToER(), i, ++i);
		p.addConstraint(new Equality(false), 0, i);
		return p;
	}
}
