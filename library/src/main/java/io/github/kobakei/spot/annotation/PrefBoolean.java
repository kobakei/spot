package io.github.kobakei.spot.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

import io.github.kobakei.spot.converter.BooleanTypeConverter;
import io.github.kobakei.spot.converter.TypeConverter;

/**
 * Created by keisukekobayashi on 16/03/31.
 */
@Target(ElementType.FIELD)
public @interface PrefBoolean {
    String name();
    boolean defaultValue() default false;
    Class<? extends TypeConverter> converter() default BooleanTypeConverter.class;
    boolean useSetter() default false;
}
