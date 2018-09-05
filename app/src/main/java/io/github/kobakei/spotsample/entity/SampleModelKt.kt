package io.github.kobakei.spotsample.entity

import io.github.kobakei.spot.annotation.Pref
import io.github.kobakei.spot.annotation.PrefField
import io.github.kobakei.spotsample.converter.DateTypeConverter
import java.util.*

@Pref(name = "sample_kt")
data class SampleModelKt (
    @PrefField(name = "number_long", useGetter = true, useSetter = true)
    var numberLong: Long = 10000L,

    @PrefField(name = "number_int", useGetter = true, useSetter = true)
    var numberInt: Int = 100,

    @PrefField(name = "number_float", useGetter = true, useSetter = true)
    var numberFloat: Float = 1.2f,

    @PrefField(name = "is_enabled", useGetter = true, useSetter = true)
    var isEnabled: Boolean = true,

    @PrefField(name = "contains", useGetter = true, useSetter = true)
    var contains: Boolean = true,

    @PrefField(name = "can_read", useGetter = true, useSetter = true)
    var canRead: Boolean = true,

    @PrefField(name = "has_apple", useGetter = true, useSetter = true)
    var hasApple: Boolean = true,

    @PrefField(name = "has_apple", useGetter = true, useSetter = true)
    var issued: Boolean = true,

    @PrefField(name = "text", useGetter = true, useSetter = true)
    var text: String = "init",

    @PrefField(name = "text_set", useGetter = true, useSetter = true)
    var textSet: Set<String>? = null,

    @PrefField(name = "date", converter = DateTypeConverter::class, useGetter = true, useSetter = true)
    var date: Date = Date(0L),

    @PrefField(name = "boxed_int", useGetter = true, useSetter = true)
    var boxedInt: Int? = 1
)