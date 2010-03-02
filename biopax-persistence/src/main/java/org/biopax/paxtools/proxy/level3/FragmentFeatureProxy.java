package org.biopax.paxtools.proxy.level3;

import javax.persistence.Entity;
import javax.persistence.Transient;

import org.biopax.paxtools.model.BioPAXElement;
import org.biopax.paxtools.model.level3.FragmentFeature;
import org.hibernate.search.annotations.Indexed;

/**
 * Created by IntelliJ IDEA.
 * User: demir
 * Date: Aug 14, 2008
 * Time: 7:48:12 PM
 */
@Entity(name="l3fragmentfeature")
@Indexed(index=BioPAXElementProxy.SEARCH_INDEX_NAME)
public class FragmentFeatureProxy extends EntityFeatureProxy 
	implements FragmentFeature
{
	@Transient
	public Class<? extends BioPAXElement> getModelInterface() {
		return FragmentFeature.class;
	}
}
