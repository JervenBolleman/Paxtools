package org.biopax.paxtools.impl;

import javax.persistence.*;

import org.biopax.paxtools.model.BioPAXElement;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(length=40)
@org.hibernate.annotations.Entity(dynamicUpdate = true, dynamicInsert = true)
@NamedQueries({
	@NamedQuery(name="org.biopax.paxtools.impl.elementByRdfId",
		query="from org.biopax.paxtools.model.BioPAXElement as el where upper(el.RDFId) = upper(:rdfid)"),
	@NamedQuery(name="org.biopax.paxtools.impl.elementByRdfIdEager",
		query="from org.biopax.paxtools.model.BioPAXElement as el fetch all properties where upper(el.RDFId) = upper(:rdfid)")

})
public abstract class BioPAXElementImpl implements BioPAXElement
{
	// ----------------- Index Names and Search Fields --------------------
	public final static String SEARCH_FIELD_SOURCE_NAME = "source_name";
	public final static String SEARCH_FIELD_KEYWORD ="keyword";
	public final static String SEARCH_FIELD_NAME = "name";
	public final static String SEARCH_FIELD_TERM = "term";
	public final static String SEARCH_FIELD_EC_NUMBER = "ec_number";
	public final static String SEARCH_FIELD_SEQUENCE = "sequence";
	public final static String SEARCH_FIELD_XREF_DB = "xref_db";
	public final static String SEARCH_FIELD_XREF_ID = "xref_id";
	public final static String SEARCH_FIELD_AVAILABILITY = "availability";
	public final static String SEARCH_FIELD_COMMENT = "comment";
	public final static String SEARCH_FIELD_NAMESPACE = "namespace";
	public final static String SEARCH_INDEX_FOR_ENTITY = "entities";
	public final static String SEARCH_INDEX_FOR_UTILILTY_CLASS = "utilityClasses";
	//public static final String SEARCH_FIELD_ID = "rdfid";
	
	// ------------------------------ FIELDS ------------------------------

	private String id;
	private Long proxyId = 0L;
	private Integer version;

	//@Id
	//@GeneratedValue(strategy=GenerationType.AUTO)
    @Transient
	public Long getProxyId() {
        return proxyId;
    }
    protected void setProxyId(Long value) {
        proxyId = value;
    }
    

    //@Version
    //@Column(name="OPTLOCK")
    @Transient
    public Integer getVersion() {
		return version;
	}
    protected void setVersion(Integer version) {
		this.version = version;
	}
    
    
	public BioPAXElementImpl(){};
	
	public BioPAXElementImpl(String uri){
		this.id = uri;
	};
	
	public int hashCode()
    {
        return id == null ? super.hashCode() : id.hashCode();
    }

    public boolean equals(Object o)
    {
        boolean value = false;

        if (this == o)
        {
            value = true;
        }
        else if (o != null && o instanceof BioPAXElement)
        {

            final BioPAXElement that = (BioPAXElement) o;
            value = this.getRDFId().equals(that.getRDFId());
        }
        return value;
    }

    @Transient
    public boolean isEquivalent(BioPAXElement element)
    {
        return this.equals(element) || this.getModelInterface().isInstance(element) &&
                this.semanticallyEquivalent(element);
    }

    protected boolean semanticallyEquivalent(BioPAXElement element)
    {
        return false;
    }

    public int equivalenceCode()
    {
        return hashCode();
    }

    @Id
    @Column(length=333)//, columnDefinition="BINARY(255)")
    //@Column(length=255, nullable=false)
    //@Column(unique=true, nullable=false)
    //@Field(name = BioPAXElementImpl.SEARCH_FIELD_ID) // full-text search better not to use rdfid!
    public String getRDFId()
    {
        return id;
    }

    protected void setRDFId(String id)
    {
        this.id = id;
//	    Set<Model> ownerModels = this.getOwnerModels();
//	    for (Model ownerModel : ownerModels)
//	    {
//		    ownerModel.notifyIdChange();
//	    }
    }

    public String toString()
    {
        return id;
    }
    
}

