package io.github.kobakei.spot.converter;

/**
 * Created by keisuke on 16/04/03.
 */
public class IntegerTypeConverter extends TypeConverter<Integer, Integer> {
    @Override
    public Integer convertFromSupportedType(Integer t) {
        return t;
    }

    @Override
    public Integer convertToSupportedType(Integer t) {
        return t;
    }
}
