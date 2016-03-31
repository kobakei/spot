package io.github.kobakei.spot.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

/**
 * Created by keisukekobayashi on 16/03/31.
 */
@Target(ElementType.FIELD)
public @interface PrefString {
    String name();
    String defaultValue() default "";
}
