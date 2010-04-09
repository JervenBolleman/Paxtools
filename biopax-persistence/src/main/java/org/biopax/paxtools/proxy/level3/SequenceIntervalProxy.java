/*
 * SequenceIntervalProxy.java
 *
 * 2007.12.04 Takeshi Yoneki
 * INOH project - http://www.inoh.org
 */

package org.biopax.paxtools.proxy.level3;

import org.biopax.paxtools.model.BioPAXElement;
import org.biopax.paxtools.model.level3.*;
import org.biopax.paxtools.proxy.BioPAXElementProxy;
import org.hibernate.search.annotations.Indexed;

import javax.persistence.*;
import javax.persistence.Entity;

/**
 * Proxy for sequenceInterval
 */
@Entity(name="l3sequenceinterval")
@Indexed(index=BioPAXElementProxy.SEARCH_INDEX_NAME)
public class SequenceIntervalProxy extends SequenceLocationProxy<SequenceInterval> 
	implements SequenceInterval 
{
	// Property SEQUENCE-INTERVAL-BEGIN

	@ManyToOne(cascade = {CascadeType.ALL}, targetEntity= SequenceSiteProxy.class)
	@JoinColumn(name="sequence_interval_begin_x")
	public SequenceSite getSequenceIntervalBegin() {
		return object.getSequenceIntervalBegin();
	}

	public void setSequenceIntervalBegin(SequenceSite SEQUENCE_INTERVAL_BEGIN) {
		object.setSequenceIntervalBegin(SEQUENCE_INTERVAL_BEGIN);
	}

    // Property SEQUENCE-INTERVAL-END

	@ManyToOne(cascade = {CascadeType.ALL}, targetEntity= SequenceSiteProxy.class)
	@JoinColumn(name="sequence_interval_end_x")
	public SequenceSite getSequenceIntervalEnd() {
		return object.getSequenceIntervalEnd();
	}

	public void setSequenceIntervalEnd(SequenceSite SEQUENCE_INTERVAL_END) {
		object.setSequenceIntervalEnd(SEQUENCE_INTERVAL_END);
	}
	
	@Transient
	public Class<? extends BioPAXElement> getModelInterface() {
		return SequenceInterval.class;
	}
}
