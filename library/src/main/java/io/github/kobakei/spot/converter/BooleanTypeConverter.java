package io.github.kobakei.spot.converter;

/**
 * Created by keisuke on 16/04/03.
 */
public class BooleanTypeConverter extends TypeConverter<Boolean, Boolean> {
    @Override
    public Boolean convertFromSupportedType(Boolean t) {
        return t;
    }

    @Override
    public Boolean convertToSupportedType(Boolean t) {
        return t;
    }
}
