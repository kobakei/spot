package io.github.kobakei.spotsample.entity;

import java.util.Date;
import java.util.Set;

import io.github.kobakei.spot.annotation.Pref;
import io.github.kobakei.spot.annotation.PrefField;
import io.github.kobakei.spotsample.converter.DateTypeConverter;

/**
 * Sample entity class.
 * POJO with annotations.
 * Created by keisukekobayashi on 17/04/19.
 */
@Pref(name = "sample2")
public class SampleModel2 {
    @PrefField(name = "number_long", useSetter = true, useGetter = true)
    public long numberLong = 10000L;

    @PrefField(name = "number_int", useSetter = true, useGetter = true)
    public int numberInt = 100;

    @PrefField(name = "number_float", useSetter = true, useGetter = true)
    public float numberFloat = 1.2f;

    @PrefField(name = "is_enabled", useSetter = true, useGetter = true)
    public boolean isEnabled = true;

    @PrefField(name = "text", useSetter = true, useGetter = true)
    public String text = "init";

    @PrefField(name = "text_set", useSetter = true, useGetter = true)
    public Set<String> textSet;

    @PrefField(name = "date", converter = DateTypeConverter.class, useSetter = true, useGetter = true)
    public Date date;

    @PrefField(name = "boxed_int", useSetter = true, useGetter = true)
    public Integer boxedInt;

    public SampleModel2() {

    }

    public long getNumberLong() {
        return numberLong;
    }

    public int getNumberInt() {
        return numberInt;
    }

    public float getNumberFloat() {
        return numberFloat;
    }

    public boolean getIsEnabled() {
        return isEnabled;
    }

    public String getText() {
        return text;
    }

    public Set<String> getTextSet() {
        return textSet;
    }

    public Date getDate() {
        return date;
    }

    public Integer getBoxedInt() {
        return boxedInt;
    }

    public void setNumberLong(long number) {
        this.numberLong = number;
    }

    public void setNumberInt(int number) {
        this.numberInt = number;
    }

    public void setNumberFloat(float number) {
        this.numberFloat = number;
    }

    public void setIsEnabled(boolean isEnabled) {
        this.isEnabled = isEnabled;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setTextSet(Set<String> set) {
        this.textSet = set;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setBoxedInt(int number) {
        this.boxedInt = number;
    }
}
