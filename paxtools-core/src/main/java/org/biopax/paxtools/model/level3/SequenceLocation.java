package org.biopax.paxtools.model.level3;

import java.util.Set;

/**
 * Definition: A location on a nucleotide or amino acid sequence.
 * <p/>
 * Usage: For most purposes it is more appropriate to use subclasses of this class. Direct instances
 * of SequenceLocation can be used for unknown locations that can not be classified neither as an
 * interval nor a site.
 */
public interface SequenceLocation extends UtilityClass
{

	Set<SequenceRegionVocabulary> getRegionType();

	void addRegionType(SequenceRegionVocabulary regionType);

	void removeRegionType(SequenceRegionVocabulary regionType);


}
