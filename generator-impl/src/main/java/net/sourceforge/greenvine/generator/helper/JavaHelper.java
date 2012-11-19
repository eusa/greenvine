package net.sourceforge.greenvine.generator.helper;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import net.sourceforge.greenvine.generator.runner.impl.SourceConfig;
import net.sourceforge.greenvine.model.api.ColumnField;
import net.sourceforge.greenvine.model.api.ComponentField;
import net.sourceforge.greenvine.model.api.ComponentIdentity;
import net.sourceforge.greenvine.model.api.Entity;
import net.sourceforge.greenvine.model.api.Identity;
import net.sourceforge.greenvine.model.api.NaturalIdentity;
import net.sourceforge.greenvine.model.api.OneToOneChildIdentity;
import net.sourceforge.greenvine.model.api.OneToOneChildNaturalIdentity;
import net.sourceforge.greenvine.model.api.PropertyType;
import net.sourceforge.greenvine.model.api.RelationField;
import net.sourceforge.greenvine.model.api.SimpleIdentity;
import net.sourceforge.greenvine.model.api.SimpleNaturalIdentity;
import net.sourceforge.greenvine.model.api.SimpleProperty;
import net.sourceforge.greenvine.model.api.impl.ComponentIdentityImpl;
import net.sourceforge.greenvine.model.api.impl.ComponentNaturalIdentityImpl;
import net.sourceforge.greenvine.model.api.impl.ManyToOneIdentityField;
import net.sourceforge.greenvine.model.api.impl.OneToOneChildIdentityImpl;
import net.sourceforge.greenvine.model.api.impl.SimpleIdentityImpl;
import net.sourceforge.greenvine.model.api.impl.SimpleNaturalIdentityImpl;
import net.sourceforge.greenvine.model.naming.EntityName;
import net.sourceforge.greenvine.model.naming.Name;
import net.sourceforge.greenvine.model.naming.impl.EntityNameImpl;
import net.sourceforge.greenvine.model.naming.impl.FieldNameImpl;


public class JavaHelper {
    
    private final SourceConfig sourceConfig;
    
    public JavaHelper(SourceConfig sourceConfig) {
        this.sourceConfig = sourceConfig;
    }

	public String firstToUpperCase(CharSequence chars) {
	    String string = chars.toString();
		String post = string.substring(1, string.length());
		String first = ("" + string.charAt(0)).toUpperCase();
		return first + post;
	}

	public String firstToLowerCase(CharSequence chars) {
	    String string = chars.toString();
		String post = string.substring(1, string.length());
		String first = ("" + string.charAt(0)).toLowerCase();
		return first + post;
	}

	public String toLowerCase(String string) {
		return string.toLowerCase();
	}
	
	public String toCamelCase(String string) {
		// String to return
		String camel = "";
		// Strip trailing and leading space
		String trimmed = string.trim();
		// Split up at the spaces
		String[] atoms = trimmed.split(" ");
		// If there is one atom, return it in lower case
		if (atoms.length == 1) {
			camel = atoms[0].toLowerCase();
			return camel;
		} else {
			// Loop through atoms and create the camel case string
			for (int i=0;i<atoms.length;i++) {
				if (i==0) {
					camel = camel + atoms[i].toLowerCase();
				} else {
					String word = atoms[i].toLowerCase();
					camel = camel + firstToUpperCase(word);
				}
			}
			return camel;
		}
	}
	
	public String stripSpaces(String string) {
		return string.replace(" ", "");
	}
	
	public  String stripSpacesToLowerCase(String string) {
		return stripSpaces(string).toLowerCase();
	}
	
	public String toUpperCase(String string) {
		return string.toUpperCase();
	}

	public String packageToFolder(String string) {
		return string.replace('.', File.separatorChar);
	}
	
	public String packageToUnixPath(String string) {
		return string.replace('.', '/');
	}

	public String getClassFromFQName(String fqName) {
		String className = fqName.substring(fqName.lastIndexOf(".")+1);
		return className;
	}
	
	public String getPackageFromFQName(String fqName) {
		String packageName = fqName.substring(0, fqName.lastIndexOf("."));
		return packageName;
	}
	
	public long generateSerialVersionUID() {
		Random random = new Random();
		return random.nextLong();
	}
	
	public BasicJavaType getQualifiedJavaType(ColumnField property) { 
        BasicJavaType javaType;
        switch (property.getPropertyType()) {
        case BIG_DECIMAL:
            javaType = new BigDecimalJavaType(property);
            break;
        case BINARY:
            javaType = new BinaryJavaType(property);
            break;
        case BLOB:
            javaType = new BinaryJavaType(property);
            break;
        case BOOLEAN:
            javaType = new BooleanJavaType(property);
            break;
        case BYTE:
            javaType = new ByteJavaType(property);
            break;
        case CHARACTER:
            javaType = new CharacterJavaType(property);
            break;
        case CLOB:
            javaType = new ClobJavaType(property);
            break;
        case CURRENCY:
            javaType = new BigDecimalJavaType(property);
            break;
        case DATE:
            javaType = new DateJavaType(property);
            break;
        case DOUBLE:
            javaType = new DoubleJavaType(property);
            break;
        case FLOAT:
            javaType = new FloatJavaType(property);
            break;
        case INTEGER:
            javaType = new IntegerJavaType(property);
            break;
        case LONG:
            javaType = new LongJavaType(property);
            break;
        case SHORT:
            javaType = new ShortJavaType(property);
            break;
        case STRING:
            javaType = new StringJavaType(property);
            break;
        case TEXT:
            javaType = new ClobJavaType(property);
            break;
        case TIME:
            javaType = new TimeJavaType(property);
            break;
        case TIMESTAMP:
            javaType = new TimestampJavaType(property);
            break;
        default:
            throw new IllegalArgumentException("Unrecognised type for property: " + property);
        }
        return javaType;
    }
	
	public JavaType getUltimateNonConstrainedIdentityType(Entity entity) {
        JavaType identityType = null;
        Identity identity = entity.getIdentity();
        if (identity instanceof ComponentIdentityImpl) {
            String pack = getEntityPackage(entity);
            String name = firstToUpperCase(identity.getName().toString());
            identityType = new ReferenceJavaType(pack + "." + name);
        } else if (identity instanceof SimpleIdentity) {
            SimpleIdentityImpl simpleIdentity = (SimpleIdentityImpl)identity;
            identityType = getQualifiedJavaType(simpleIdentity);
        } else {
            OneToOneChildIdentityImpl constrainedIdentity = (OneToOneChildIdentityImpl)identity;
            Entity related = constrainedIdentity.getRelation().getParentEntity();
            identityType = getUltimateNonConstrainedIdentityType(related);
        }
        return identityType;
    }
	
	public JavaType getNaturalIdentityType(Entity entity) {
        JavaType naturalIdentityType = null;
        NaturalIdentity naturalIdentity = entity.getNaturalIdentity();
        if (naturalIdentity != null) {
            if (naturalIdentity instanceof ComponentNaturalIdentityImpl) {
                String pack = getEntityPackage(entity);
                String name = firstToUpperCase(naturalIdentity.getName().toString());
                naturalIdentityType = new ReferenceJavaType(pack + "." + name);
            } else if (naturalIdentity instanceof SimpleNaturalIdentity) {
                SimpleNaturalIdentityImpl simpleIdentity = (SimpleNaturalIdentityImpl)naturalIdentity;
                naturalIdentityType = getQualifiedJavaType(simpleIdentity);
            } else {
                OneToOneChildNaturalIdentity oneToOneNaturalIdentity = (OneToOneChildNaturalIdentity)naturalIdentity;
                Entity related = oneToOneNaturalIdentity.getRelation().getParentEntity();
                naturalIdentityType = getEntityType(related);
            }
        }
        return naturalIdentityType;
    }
	
	public Identity getUltimateNonConstrainedIdentity(Entity entity) {
        Identity identity = entity.getIdentity();
        if (identity instanceof OneToOneChildIdentity) {
            RelationField field = (OneToOneChildIdentityImpl)identity;
            Entity constrained = getRelatedEntity(field);
            return getUltimateNonConstrainedIdentity(constrained);
        } else {
            return identity;
        }
    }
	
    private String getRootPackage(Entity entity) {
        String basePackage = sourceConfig.getBasePackage();
	    String dataPackage = sourceConfig.getDataPackage();
	    // TODO experimenting with removing catalog name from package
	    //String catalogPackage = entity.getCatalog().getName().toString().toLowerCase();
	    //String pack = basePackage + "." + dataPackage + "." + catalogPackage;
	    String pack = basePackage + "." + dataPackage;
        return pack;
    }

    public String getEntityPackage(Entity entity) {
        String pack = getRootPackage(entity);
        pack = pack + ".entity";
        EntityName name = entity.getName();
        
        CharSequence schemaPackage = name.getNamespace();
        if (schemaPackage != null) {
            pack = pack + "." + schemaPackage;
        }
        return pack;
    }
    
    public String getDaoPackage(Entity entity) {
        String pack = getRootPackage(entity);
        pack = pack + ".dao";
        CharSequence schemaPackage = entity.getName().getNamespace();
        if (schemaPackage != null) {
            pack = pack + "." + schemaPackage;
        }
        return pack;
    }
    
    public AbstractJavaType getEntityType(Entity entity) {
        String pack = getEntityPackage(entity);
        String name = firstToUpperCase(entity.getName().getObjectName().toString());
        AbstractJavaType type = new ReferenceJavaType(pack + "." + name);
        return type;
    }
    
    public JavaType getComponentType(ComponentField component) {
        Entity entity = component.getFieldCollection();
        String pack = getEntityPackage(entity);
        String name = firstToUpperCase(component.getName().toString());
        JavaType type = new ReferenceJavaType(pack + "." + name);
        return type;
    }

    public JavaType getDaoType(Entity entity) {
        String pack = getDaoPackage(entity);
        String name = firstToUpperCase(entity.getName().getObjectName().toString());
        JavaType type = new ReferenceJavaType(pack + "." + name + sourceConfig.getDaoSuffix());
        return type;
    }
    
    public JavaType getDaoImplType(Entity entity, String implementation) {
        String pack = getDaoPackage(entity);
        String name = firstToUpperCase(entity.getName().getObjectName().toString());
        JavaType daoImplType = new ReferenceJavaType(pack + ".impl." + implementation +"." + name + sourceConfig.getDaoSuffix() + "Impl");
        return daoImplType;
    }
    
    public String getAccessorForField(Name beanName, FieldNameImpl fieldName) {
        CharSequence name = beanName;
        if (beanName instanceof EntityName) {
            name = ((EntityNameImpl)beanName).getObjectName();
        }
        return name + ".get" + firstToUpperCase(fieldName) + "()";
    }
    
    public String getDefaultDataForProperty(ColumnField property) throws Exception {
        return getQualifiedJavaType(property).getCreateDataLiteral();
    }
    
    public String getUpdateDataForProperty(ColumnField property) throws Exception {
        return getQualifiedJavaType(property).getUpdateDataLiteral();
    }

    public Entity getRelatedEntity(RelationField field) {
        if (field.getFieldCollection().equals(field.getRelation().getParentEntity())) {
            return field.getRelation().getChildEntity();
        } else {
            return field.getRelation().getParentEntity();
        }
    }
    
    public JavaType getRelatedEntityType(RelationField field) {
        return getEntityType(getRelatedEntity(field));
    }
    
    public RelationField getRelatedField(RelationField field) {
        if (field.equals(field.getRelation().getParentField())) {
            return field.getRelation().getChildField();
        } else {
            return field.getRelation().getParentField();
        }
    }
    
    public List<String> getRelatedFieldPathNames(RelationField field) {
        List<String> pathNames = new ArrayList<String>();
        RelationField related = getRelatedField(field);
        if (related.getFieldCollection() instanceof ComponentField) {
            pathNames.add(related.getFieldCollection().getName().toString());
            pathNames.add(related.getName().toString());
        } else {
            pathNames.add(related.getName().toString());
        }
        return pathNames;
    }

    public SortedSet<JavaType> getImportsForIntegrationTest(Entity entity) {
        // All SimpleProperty imports for the class
        SortedSet<JavaType> imports = getSimplePropertyImportsForEntity(entity);
        
        // Add the Identity type if it's a ComplexIdentity
        if (entity.getComponentIdentity() != null) {
            imports.add(getUltimateNonConstrainedIdentityType(entity));
        }
        return imports;
    }
    
    public SortedSet<JavaType> getAllImportsForEntity(Entity entity) {
        
        // Set of all properties
        SortedSet<JavaType> props = new TreeSet<JavaType>();
        
        // Get the Identity property
        JavaType identity = getUltimateNonConstrainedIdentityType(entity);
        props.add(identity);
        
        // Get the SimpleProperties
        props.addAll(getSimplePropertyImportsForEntity(entity));
        
        // Get imports for related entities
        props.addAll(getRelatedEntityImportsForEntity(entity));
        
        return props;
        
    }

    public SortedSet<JavaType> getSimplePropertyImportsForEntity(Entity entity) {
        
        // Set of all properties
        SortedSet<JavaType> props = new TreeSet<JavaType>();
        
        // Get simple properties
        for (ColumnField prop : entity.getSimpleProperties()) {
            JavaType javaType = getQualifiedJavaType(prop);
            if (javaType.requiresImport()) {
                props.add(javaType);
            }
        }
        
        return props;
    }
    
    public SortedSet<AbstractJavaType> getRelatedEntityImportsForEntity(
            Entity entity) {
        SortedSet<AbstractJavaType> props = new TreeSet<AbstractJavaType>();
        Set<? extends Entity> relations = entity.getAllRelatedEntities();
        for (Entity relation : relations) {
        	if (!namespacesEqual(entity.getName(), relation.getName())) {
            	props.add(getEntityType(relation));
            }
        }
        return props;
    }
    
    private boolean namespacesEqual(EntityName entity, EntityName relation) {
    	CharSequence entityNamespace = entity.getNamespace();
    	CharSequence relationNamespace = relation.getNamespace();
    	if (entityNamespace != null && relationNamespace != null) {
    		if (entityNamespace.equals(relationNamespace)) {
    			return true;
    		}
    	}
    	return false;
    }
    
    public SortedSet<JavaType> getDependentRelatedEntityImportsForEntity(
            Entity entity) {
        SortedSet<JavaType> props = new TreeSet<JavaType>();
        Set<? extends Entity> relations = entity.getUniqueDependentEntities();
        for (Entity relation : relations) {
        	if (!namespacesEqual(entity.getName(), relation.getName())) {
        		props.add(getEntityType(relation));
            }
        }
        return props;
    }
    
    /**
     * Return all imports for a ComponentIdentity
     * including SimpleProperty and RelationField
     * required imports
     * @param component
     * @return
     */
    public SortedSet<JavaType> getAllImportsForIdentityComponent(ComponentIdentity component) {
        SortedSet<JavaType> props = new TreeSet<JavaType>();
        
        // Get simple properties
        props.addAll(getSimplePropertyImportsForComponentIdentity(component));
        
        // Get imports for related entities
        props.addAll(getRelatedEntityImportsForComponentIdentity(component));
        return props;
    }

    /**
     * Returns just the SimpleProperty imports
     * for a ComponentIdentity
     * such as java.math.BigDecimal etc.
     * @param component
     * @return
     */
    public SortedSet<JavaType> getSimplePropertyImportsForComponentIdentity(
            ComponentIdentity component) {
        SortedSet<JavaType> props = new TreeSet<JavaType>();
        for (ColumnField prop : component.getSimpleProperties()) {
            JavaType javaType = getQualifiedJavaType(prop);
            if (javaType.requiresImport()) {
                props.add(javaType);
            }
        }
        return props;
    }
    
    /**
     * Returns the imports for a ComponentIdentity
     * needed by related entities through
     * ManyToOne relations
     * @param component
     * @return
     */
    public SortedSet<AbstractJavaType> getRelatedEntityImportsForComponentIdentity(
            ComponentIdentity component) {
        Entity entity = component.getFieldCollection();
        SortedSet<AbstractJavaType> props = new TreeSet<AbstractJavaType>();
        SortedSet<ManyToOneIdentityField> manyToOnes = component.getManyToOnes();
        for (ManyToOneIdentityField field : manyToOnes) {
            Entity relation = field.getRelatedEntity();
            if (!namespacesEqual(entity.getName(), relation.getName())) {
            	props.add(getEntityType(relation));
            }
        }
        return props;
    }
    
    /**
     * Returns true if the entity
     * contains fields that are mapped
     * to binary data in the database.
     * Needed to work-around a bug
     * in DbUnit which cannot compare
     * these values successfully
     * @param entity
     * @return
     */
    public boolean entityContainsBinaryField(Entity entity) {
        boolean containsBinary = false;
        for (SimpleProperty simple : entity.getSimpleProperties()) {
            if (simple.getPropertyType().equals(PropertyType.BINARY) ||
                    simple.getPropertyType().equals(PropertyType.BLOB) )
                containsBinary = true;
        }
        return containsBinary;
    }
    
}