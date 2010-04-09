/*
 * InteractionVocabularyProxy.java
 *
 * 2007.12.03 Takeshi Yoneki
 * INOH project - http://www.inoh.org
 */

package org.biopax.paxtools.proxy.level3;

import org.biopax.paxtools.model.BioPAXElement;
import org.biopax.paxtools.model.level3.*;
import org.biopax.paxtools.proxy.BioPAXElementProxy;
import org.hibernate.search.annotations.Indexed;

import javax.persistence.Entity;
import javax.persistence.Transient;

/**
 * Proxy for interactionVocabulary
 */
@Entity(name="l3interactionVocabulary")
@Indexed(index=BioPAXElementProxy.SEARCH_INDEX_NAME)
public class InteractionVocabularyProxy extends ControlledVocabularyProxy<InteractionVocabulary>
	implements InteractionVocabulary 
{
	@Transient
	public Class<? extends BioPAXElement> getModelInterface() {
		return InteractionVocabulary.class;
	}
}
