package net.sourceforge.greenvine.model.api;
/**
 * Cardinality of foreign key
 * is one-to-one if the referencing
 * columns are unique or primary.
 * Otherwise, one-to-many.
 *
 */
public enum Cardinality {
    OneToOne, OneToMany
}
