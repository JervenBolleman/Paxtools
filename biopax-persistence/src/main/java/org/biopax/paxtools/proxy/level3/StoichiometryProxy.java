/*
 * StoichiometryProxy.java
 *
 * 2007.12.03 Takeshi Yoneki
 * INOH project - http://www.inoh.org
 */

package org.biopax.paxtools.proxy.level3;

import org.biopax.paxtools.model.BioPAXElement;
import org.biopax.paxtools.model.level3.*;
import org.biopax.paxtools.proxy.BioPAXElementProxy;
import org.hibernate.search.annotations.*;

import javax.persistence.*;
import javax.persistence.Entity;

/**
 * Proxy for Stoichiometry
 */
@Entity(name="l3stoichiometry")
@Indexed(index=BioPAXElementProxy.SEARCH_INDEX_NAME)
public class StoichiometryProxy extends Level3ElementProxy<Stoichiometry> 
	implements Stoichiometry 
{

	// Property PHYSICAL-ENTITY

	@ManyToOne(cascade = {CascadeType.ALL}, targetEntity = PhysicalEntityProxy.class)
	@JoinColumn(name="physical_entity_x")
	public PhysicalEntity getPhysicalEntity() {
		return object.getPhysicalEntity();
	}

	public void setPhysicalEntity(PhysicalEntity newPhysical_ENTITY) {
		object.setPhysicalEntity(newPhysical_ENTITY);
	}

	// Property STOICHIOMETRIC-COEFFICIENT

	@Basic @Column(name="stoichiometric_coefficient_x", columnDefinition="text")
	protected String getStoichiometricCoefficient_x() {
		return floatToString(object.getStoichiometricCoefficient());
	}

	protected void setStoichiometricCoefficient_x(String newSTOICHIOMETRIC_COEFFICIENT) {
		object.setStoichiometricCoefficient(stringToFloat(newSTOICHIOMETRIC_COEFFICIENT));
	}

	@Transient
	public float getStoichiometricCoefficient() {
		return stringToFloat(getStoichiometricCoefficient_x());
	}

	public void setStoichiometricCoefficient(float newSTOICHIOMETRIC_COEFFICIENT) {
		setStoichiometricCoefficient_x(floatToString(newSTOICHIOMETRIC_COEFFICIENT));
	}
	
	@Transient
	public Class<? extends BioPAXElement> getModelInterface() {
		return Stoichiometry.class;
	}
}

