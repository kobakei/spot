package io.github.kobakei.spot.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

import io.github.kobakei.spot.converter.LongTypeConverter;
import io.github.kobakei.spot.converter.TypeConverter;

/**
 * Created by keisukekobayashi on 16/03/31.
 */
@Target(ElementType.FIELD)
public @interface PrefLong {
    String name();
    long defaultValue() default 0;
    Class<? extends TypeConverter> converter() default LongTypeConverter.class;
    boolean useSetter() default false;
}
