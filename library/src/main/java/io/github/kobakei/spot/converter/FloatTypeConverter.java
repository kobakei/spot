package io.github.kobakei.spot.converter;

/**
 * Created by keisuke on 16/04/03.
 */
public class FloatTypeConverter extends TypeConverter<Float, Float> {
    @Override
    public Float convertFromSupportedType(Float t) {
        return t;
    }

    @Override
    public Float convertToSupportedType(Float t) {
        return t;
    }
}
