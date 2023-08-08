package com.hung.sneakery.converter;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface After {

    /**
     * Name of populator bean
     *
     * @return String[]
     */
    String[] value();

}
