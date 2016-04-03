package io.github.kobakei.spot.converter;

import java.util.Set;

/**
 * Created by keisuke on 16/04/03.
 */
public class StringSetTypeConverter extends TypeConverter<Set<String>, Set<String>> {
    @Override
    public Set<String> convertFromSupportedType(Set<String> t) {
        return t;
    }

    @Override
    public Set<String> convertToSupportedType(Set<String> t) {
        return t;
    }
}
