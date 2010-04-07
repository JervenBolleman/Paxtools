/*
 * SequenceLocationProxy.java
 *
 * 2007.04.02 Takeshi Yoneki
 * INOH project - http://www.inoh.org
 */

package org.biopax.paxtools.proxy.level3;

import org.biopax.paxtools.model.BioPAXElement;
import org.biopax.paxtools.model.level3.*;
import org.biopax.paxtools.proxy.BioPAXElementProxy;
import org.hibernate.search.annotations.Indexed;

import javax.persistence.Entity;
import javax.persistence.Transient;

import java.util.Set;
import javax.persistence.*;

/**
 * Proxy for sequenceLocation
 */
@Entity(name="l3sequencelocation")
@Indexed(index=BioPAXElementProxy.SEARCH_INDEX_NAME)
public class SequenceLocationProxy extends Level3ElementProxy 
	implements SequenceLocation 
{

	// Property LOCATION-TYPE

	@ManyToMany(cascade = {CascadeType.ALL}, targetEntity = SequenceRegionVocabularyProxy.class)
	@JoinTable(name="l3seqloc_region_type")
	public Set<SequenceRegionVocabulary> getRegionType() {
		return ((SequenceLocation)object).getRegionType();
	}

	public void addRegionType(SequenceRegionVocabulary regionType) {
		((SequenceLocation)object).addRegionType(regionType);
	}

	public void removeRegionType(SequenceRegionVocabulary regionType) {
		((SequenceLocation)object).removeRegionType(regionType);
	}

	public void setRegionType(Set<SequenceRegionVocabulary> regionType) {
		((SequenceLocation)object).setRegionType(regionType);
	}
	
	@Transient
	public Class<? extends BioPAXElement> getModelInterface() {
		return SequenceLocation.class;
	}
}
