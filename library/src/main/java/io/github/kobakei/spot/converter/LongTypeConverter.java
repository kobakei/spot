package io.github.kobakei.spot.converter;

/**
 * Created by keisuke on 16/04/03.
 */
public class LongTypeConverter extends TypeConverter<Long, Long> {
    @Override
    public Long convertFromSupportedType(Long t) {
        return t;
    }

    @Override
    public Long convertToSupportedType(Long t) {
        return t;
    }
}
