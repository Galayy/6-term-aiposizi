package com.iit.aiposizi.util;

import com.google.common.collect.Streams;
import lombok.experimental.UtilityClass;

import java.util.Arrays;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@UtilityClass
public class DatabaseUtils {

    private static final String EMPTY_ARRAY = "{}";

    public static String prepareArray(final Iterable<?> iterable) {
        return prepareArray(Streams.stream(iterable));
    }

    public static String prepareArray(final Object... objects) {
        if (objects == null) {
            return EMPTY_ARRAY;
        } else {
            return prepareArray(Arrays.stream(objects));
        }
    }

    public static String prepareArray(final Stream<?> stream) {
        return stream
                .distinct()
                .filter(Objects::nonNull)
                .map(String::valueOf)
                .collect(Collectors.joining(",", "{", "}"));
    }

}
