package io.github.kobakei.spotsample.converter;

import java.util.Date;

import io.github.kobakei.spot.converter.TypeConverter;

/**
 * Sample TypeConverter for java.util.Date.
 * Date is converted to Long in SharedPreferences.
 * Created by keisuke on 16/04/03.
 */
public class DateTypeConverter extends TypeConverter<Date, Long> {
    @Override
    public Date convertFromSupportedType(Long t) {
        return new Date(t);
    }

    @Override
    public Long convertToSupportedType(Date t) {
        return t.getTime();
    }
}
