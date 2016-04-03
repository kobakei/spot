package io.github.kobakei.spot.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

import io.github.kobakei.spot.converter.FloatTypeConverter;
import io.github.kobakei.spot.converter.TypeConverter;

/**
 * Created by keisukekobayashi on 16/03/31.
 */
@Target(ElementType.FIELD)
public @interface PrefFloat {
    String name();
    float defaultValue() default 0.0f;
    Class<? extends TypeConverter> converter() default FloatTypeConverter.class;
}
