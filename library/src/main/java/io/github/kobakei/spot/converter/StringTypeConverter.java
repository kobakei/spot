package io.github.kobakei.spot.converter;

/**
 * Created by keisuke on 16/04/03.
 */
public class StringTypeConverter extends TypeConverter<String, String> {
    @Override
    public String convertFromSupportedType(String t) {
        return t;
    }

    @Override
    public String convertToSupportedType(String t) {
        return t;
    }
}
