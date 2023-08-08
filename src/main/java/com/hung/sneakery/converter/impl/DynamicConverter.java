package com.hung.sneakery.converter.impl;


import com.hung.sneakery.converter.ConversionException;
import com.hung.sneakery.converter.ConverterCacheKey;
import com.hung.sneakery.converter.Populator;
import lombok.RequiredArgsConstructor;
import org.hibernate.Hibernate;
import org.hibernate.proxy.HibernateProxy;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.lang.reflect.InvocationTargetException;
import java.util.*;

@RequiredArgsConstructor
@Component
public class DynamicConverter {

    private final Map<ConverterCacheKey, Set<Populator<Object, Object>>> populatorMap;

    /**
     * Convert
     *
     * @param source      S
     * @param targetClass Class<T>
     * @param <S>         S
     * @param <T>         T
     * @return T
     */
    public <S, T> T convert(final S source, final Class<T> targetClass) {
        if (Objects.isNull(source)) {
            return null;
        }
        S populateSource = source;
        if (populateSource instanceof HibernateProxy) {
            populateSource = (S) Hibernate.unproxy(source);
        }
        Set<Populator<Object, Object>> populators = getPopulators(populateSource.getClass(), targetClass);

        if (CollectionUtils.isEmpty(populators)) {
            throw new ConversionException("No populator found for [" + populateSource.getClass() + ", " + targetClass + "]");
        }
        T target;
        try {
            target = targetClass.getDeclaredConstructor().newInstance();
        } catch (InstantiationException | IllegalAccessException | NoSuchMethodException |
                InvocationTargetException e) {
            throw new ConversionException(e.getMessage());
        }
        for (final Populator<Object, Object> populator : populators) {
            if (populator != null) {
                populator.populate(populateSource, target);
            }
        }
        return target;
    }

    /**
     * Convert
     *
     * @param source
     * @param target
     * @param <S>
     * @param <T>
     * @return
     */
    public <S, T> T convert(final S source, final T target) {
        if (Objects.isNull(source)) {
            return null;
        }
        S populateSource = source;
        if (populateSource instanceof HibernateProxy) {
            populateSource = (S) Hibernate.unproxy(source);
        }

        T populateTarget = target;
        if (populateTarget instanceof HibernateProxy) {
            populateTarget = (T) Hibernate.unproxy(target);
        }

        Class<?> targetClass = target.getClass();

        Set<Populator<Object, Object>> populators = getPopulators(populateSource.getClass(), targetClass);

        if (CollectionUtils.isEmpty(populators)) {
            throw new ConversionException("No populator found for [" + populateSource.getClass() + ", " + targetClass + "]");
        }
        for (final Populator<Object, Object> populator : populators) {
            if (populator != null) {
                populator.populate(populateSource, populateTarget);
            }
        }
        return populateTarget;
    }

    /**
     * Convert all
     *
     * @param sources     Collection<S>
     * @param targetClass Class<T>
     * @param <S>         S
     * @param <T>         T
     * @return List<T>
     */
    public <S, T> List<T> convertAll(final Collection<S> sources, final Class<T> targetClass) {
        if (CollectionUtils.isEmpty(sources)) {
            return new ArrayList<>();
        } else {
            List<T> targets = new ArrayList<>();
            for (S source : sources) {
                targets.add(convert(source, targetClass));
            }
            return targets;
        }
    }

    private Set<Populator<Object, Object>> getPopulators(final Class<?> sourceClass, final Class<?> targetClass) {
        Set<Populator<Object, Object>> populators = populatorMap.get(new ConverterCacheKey(sourceClass, targetClass));
        if (CollectionUtils.isEmpty(populators)) {
            populators = new LinkedHashSet<>();
            for (Map.Entry<ConverterCacheKey, Set<Populator<Object, Object>>> entry : populatorMap.entrySet()) {
                if (entry.getKey().getSourceClass().isAssignableFrom(sourceClass)
                        && entry.getKey().getTargetClass().isAssignableFrom(targetClass)) {
                    populators.addAll(entry.getValue());
                }
            }
            populatorMap.put(new ConverterCacheKey(sourceClass, targetClass), populators);
        }
        return populators;
    }

}
