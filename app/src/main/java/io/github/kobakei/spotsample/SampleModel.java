package io.github.kobakei.spotsample;

import java.util.Set;

import io.github.kobakei.spot.annotation.PrefInt;
import io.github.kobakei.spot.annotation.PrefString;
import io.github.kobakei.spot.annotation.Table;

/**
 * Sample entity class.
 * POJO with annotations.
 * Created by keisukekobayashi on 16/03/31.
 */
@Table(name = "sample")
public class SampleModel {
    long numberLong;
    @PrefInt(name = "number_int", defaultValue = 100) int numberInt;
    float numberFloat;
    boolean isEnabled;
    @PrefString(name = "text") String text;
    Set<String> textSet;
}
