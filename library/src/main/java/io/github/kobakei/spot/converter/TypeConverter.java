package io.github.kobakei.spot.converter;

/**
 * Created by keisuke on 16/04/03.
 */
public abstract class TypeConverter<T1, T2> {
    public abstract T1 convertFromSupportedType(T2 t);
    public abstract T2 convertToSupportedType(T1 t);
}
