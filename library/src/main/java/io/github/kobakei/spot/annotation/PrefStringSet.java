package io.github.kobakei.spot.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;
import java.util.Set;

import io.github.kobakei.spot.converter.StringSetTypeConverter;
import io.github.kobakei.spot.converter.TypeConverter;

/**
 * Created by keisukekobayashi on 16/03/31.
 */
@Target(ElementType.FIELD)
public @interface PrefStringSet {
    String name();
    Class<? extends TypeConverter> converter() default StringSetTypeConverter.class;
    boolean useSetter() default false;
}
