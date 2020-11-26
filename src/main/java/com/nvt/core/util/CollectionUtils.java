package com.nvt.core.util;

import java.util.Collections;
import java.util.List;

import static java.util.Optional.ofNullable;

public class CollectionUtils {

    public static <T> List<T> getOrElseEmpty(List<T> list) {
        return ofNullable(list)
                .orElseGet(Collections::emptyList);
    }

}
