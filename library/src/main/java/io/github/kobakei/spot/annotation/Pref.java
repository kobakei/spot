package io.github.kobakei.spot.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

/**
 * Annotation for class
 * Created by keisukekobayashi on 16/03/31.
 */
@Target(ElementType.TYPE)
public @interface Pref {
    String name();
}
