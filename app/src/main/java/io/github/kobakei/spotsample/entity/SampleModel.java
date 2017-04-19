package io.github.kobakei.spotsample.entity;

import java.util.Date;
import java.util.Set;

import io.github.kobakei.spot.annotation.Pref;
import io.github.kobakei.spot.annotation.PrefField;
import io.github.kobakei.spotsample.converter.DateTypeConverter;

/**
 * Sample entity class.
 * POJO with annotations.
 * Created by keisukekobayashi on 16/03/31.
 */
@Pref(name = "sample")
public class SampleModel {
    @PrefField(name = "number_long")
    public long numberLong = 10000L;

    @PrefField(name = "number_int")
    public int numberInt = 100;

    @PrefField(name = "number_float")
    public float numberFloat = 1.2f;

    @PrefField(name = "is_enabled")
    public boolean isEnabled = true;

    @PrefField(name = "text")
    public String text = "init";

    @PrefField(name = "text_set")
    public Set<String> textSet;

    @PrefField(name = "date", converter = DateTypeConverter.class)
    public Date date;

    @PrefField(name = "boxed_int")
    public Integer boxedInt;

    public SampleModel() {

    }
}
