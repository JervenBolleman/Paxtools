/*
 * ProteinProxy.java
 *
 * 2007.12.04 Takeshi Yoneki
 * INOH project - http://www.inoh.org
 */

package org.biopax.paxtools.proxy.level3;

import org.biopax.paxtools.model.BioPAXElement;
import org.biopax.paxtools.model.level3.*;
import org.hibernate.search.annotations.Indexed;

import javax.persistence.Entity;
import javax.persistence.Transient;
import javax.xml.bind.annotation.*;

/**
 * Proxy for protein
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.PUBLIC_MEMBER)
@Entity(name="l3protein")
@Indexed(index=BioPAXElementProxy.SEARCH_INDEX_NAME)
public class ProteinProxy extends SimplePhysicalEntityProxy implements Protein {
	public ProteinProxy() {
	}

	@Transient
    public Class<? extends PhysicalEntity> getPhysicalEntityClass() {
        return Protein.class;

    }
	
	@Transient
	public Class<? extends BioPAXElement> getModelInterface() {
		return Protein.class;
	}
}


