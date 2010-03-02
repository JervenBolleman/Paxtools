/*
 * EntityReferenceTypeVocabularyProxy.java
 *
 * 2008.02.26 Takeshi Yoneki
 * INOH project - http://www.inoh.org
 */

package org.biopax.paxtools.proxy.level3;

import org.biopax.paxtools.model.BioPAXElement;
import org.biopax.paxtools.model.level3.*;
import org.hibernate.search.annotations.Indexed;

import javax.persistence.Entity;
import javax.persistence.Transient;

/**
 * Proxy for EntityReferenceTypeVocabulary
 */
@Entity(name="l3entityreferencetypevocab")
@Indexed(index=BioPAXElementProxy.SEARCH_INDEX_NAME)
public class EntityReferenceTypeVocabularyProxy extends ControlledVocabularyProxy 
	implements EntityReferenceTypeVocabulary 
{
	public EntityReferenceTypeVocabularyProxy() {
	}
	
	@Transient
	public Class<? extends BioPAXElement> getModelInterface() {
		return EntityReferenceTypeVocabulary.class;
	}
}
