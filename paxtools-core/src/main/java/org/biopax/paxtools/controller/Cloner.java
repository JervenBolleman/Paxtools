package org.biopax.paxtools.controller;

import org.biopax.paxtools.model.BioPAXElement;
import org.biopax.paxtools.model.BioPAXFactory;
import org.biopax.paxtools.model.Model;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Specifically "Clones" the BioPAX elements set
 * (traverses to obtain dependent elements), 
 * puts them to the new model using the visitor and traverser framework;
 * ignores elements that are not in the source list (compare to {@link Fetcher})
 *
 * @see org.biopax.paxtools.controller.Visitor
 * @see org.biopax.paxtools.controller.Traverser
 */
public class Cloner implements Visitor
{
	Traverser traverser;
	private BioPAXFactory factory;
	private Model targetModel;
	private Map<String, BioPAXElement> sourceMap;
	private Map<String, BioPAXElement> targetMap;

	public Cloner(EditorMap map, BioPAXFactory factory)
	{
		this.factory = factory;
		traverser = new Traverser(map, this);
		sourceMap = new HashMap<String, BioPAXElement>();
		targetMap = new HashMap<String, BioPAXElement>();
	}

	
	/**
	 * For each element from the 'toBeCloned' list,
	 * it creates a copy in the new model, setting all 
	 * the data properties; however, object property values
	 * that refer to BioPAX elements not in 'toBeCloned' list
	 * are ignored.
	 * 
	 * @param source
	 * @param toBeCloned
	 * @return
	 */
	public Model clone(Model source, Set<BioPAXElement> toBeCloned)
	{
		targetModel = factory.createModel();

		for (BioPAXElement bpe : toBeCloned)
		{
			sourceMap.put(bpe.getRDFId(), bpe);
		}

		for (BioPAXElement bpe : toBeCloned)
		{
			traverser.traverse(bpe, source);
		}

		return targetModel;
	}

// --------------------- Interface Visitor ---------------------

	public void visit(BioPAXElement domain, Object range, Model model, PropertyEditor editor)
	{
		if (!targetMap.containsKey(domain.getRDFId()))
		{
			BioPAXElement targetDomain = targetModel
				.addNew(domain.getModelInterface(), domain.getRDFId());
			targetMap.put(targetDomain.getRDFId(), targetDomain);
		}

		if (range instanceof BioPAXElement)
		{
			BioPAXElement bpe = (BioPAXElement) range;
			if (sourceMap.containsKey(bpe.getRDFId()))
			{
				if (!targetMap.containsKey(bpe.getRDFId()))
				{
					traverser.traverse(bpe, model);
				}

				editor.setValueToBean(
						targetMap.get(bpe.getRDFId()), targetMap.get(domain.getRDFId()));
			} else {
				// ignore the element that is not in the source list
			}
		}
		else
		{
			editor.setValueToBean(range, targetMap.get(domain.getRDFId()));
		}
	}
	
	
    /**
     * Returns a full copy (new object) of the existing biopax element
     * (if any) with all its properties set to the same values, 
     * except for possibly rdfid (can be the same as well).
     * The new object is not added to the model.
     * 
     * @param id
     * @param newId
     * @return
     */
    public static BioPAXElement copy(BioPAXElement bpe, String newId) {
    	throw new UnsupportedOperationException("not implemented yet.");
    	// TODO implement Cloner.copy method
    }
}



