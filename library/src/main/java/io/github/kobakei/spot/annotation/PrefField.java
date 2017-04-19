package io.github.kobakei.spot.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

import io.github.kobakei.spot.converter.DefaultTypeConverter;
import io.github.kobakei.spot.converter.TypeConverter;

/**
 * Annotation for field
 */
@Target(ElementType.FIELD)
public @interface PrefField {
    String name();
    Class<? extends TypeConverter> converter() default DefaultTypeConverter.class;
    boolean useSetter() default false;
}
