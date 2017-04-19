package io.github.kobakei.spot.converter;

import java.util.Set;

/**
 * Default type converter for supported types
 * Created by keisukekobayashi on 2017/04/19.
 */

public final class DefaultTypeConverter extends TypeConverter<Void, Void> {

    public int convertFromSupportedType(int t) {
        return t;
    }
    public int convertToSupportedType(int t) {
        return t;
    }

    public long convertFromSupportedType(long t) {
        return t;
    }
    public long convertToSupportedType(long t) {
        return t;
    }

    public float convertFromSupportedType(float t) {
        return t;
    }
    public float convertToSupportedType(float t) {
        return t;
    }

    public boolean convertFromSupportedType(boolean t) {
        return t;
    }
    public boolean convertToSupportedType(boolean t) {
        return t;
    }

    public String convertFromSupportedType(String t) {
        return t;
    }
    public String convertToSupportedType(String t) {
        return t;
    }

    public Set<String> convertFromSupportedType(Set<String> t) {
        return t;
    }
    public Set<String> convertToSupportedType(Set<String> t) {
        return t;
    }

    @Override
    public Void convertFromSupportedType(Void t) {
        return null;
    }

    @Override
    public Void convertToSupportedType(Void t) {
        return null;
    }
}
