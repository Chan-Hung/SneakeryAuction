package com.hung.sneakery.converter;

/**
 * Interface for a populator.
 * A populator sets values in a target instance based on values in the source instance.
 * Populators are similar to converters except that unlike converters the target instance must already exist.
 *
 * @param <S> the type of the source object
 * @param <T> the type of the destination object
 */
public interface Populator<S, T> {
    /**
     * Populate the target instance with values from the source instance.
     *
     * @param source the source object
     * @param target the target to fill
     * @throws ConversionException if an error occurs
     */
    void populate(S source, T target) throws ConversionException;
}
