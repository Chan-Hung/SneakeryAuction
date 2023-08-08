package com.hung.sneakery.converter;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Objects;

@Getter
@AllArgsConstructor
public class ConverterCacheKey {

    private final Class<?> sourceClass;
    private final Class<?> targetClass;

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ConverterCacheKey that = (ConverterCacheKey) o;
        return sourceClass.equals(that.sourceClass) &&
                targetClass.equals(that.targetClass);
    }

    @Override
    public int hashCode() {
        return Objects.hash(sourceClass, targetClass);
    }
}
