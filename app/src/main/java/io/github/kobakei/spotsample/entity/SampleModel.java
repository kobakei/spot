package io.github.kobakei.spotsample.entity;

import java.util.Date;
import java.util.Set;

import io.github.kobakei.spot.annotation.Pref;
import io.github.kobakei.spot.annotation.PrefBoolean;
import io.github.kobakei.spot.annotation.PrefFloat;
import io.github.kobakei.spot.annotation.PrefInt;
import io.github.kobakei.spot.annotation.PrefLong;
import io.github.kobakei.spot.annotation.PrefString;
import io.github.kobakei.spot.annotation.PrefStringSet;
import io.github.kobakei.spotsample.converter.DateTypeConverter;

/**
 * Sample entity class.
 * POJO with annotations.
 * Created by keisukekobayashi on 16/03/31.
 */
@Pref(name = "sample")
public class SampleModel {
    @PrefLong(name = "number_long", useSetter = true)
    public long numberLong = 10000L;

    @PrefInt(name = "number_int", useSetter = true)
    public int numberInt = 100;

    @PrefFloat(name = "number_float", useSetter = true)
    public float numberFloat = 1.2f;

    @PrefBoolean(name = "is_enabled", useSetter = true)
    public boolean isEnabled = true;

    @PrefString(name = "text", useSetter = true)
    public String text = "init";

    @PrefStringSet(name = "text_set", useSetter = true)
    public Set<String> textSet;

    @PrefLong(name = "date", converter = DateTypeConverter.class, useSetter = true)
    public Date date;

    public SampleModel() {

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
}
