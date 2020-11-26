package com.nvt.core.util;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

import static com.nvt.core.util.CollectionUtils.getOrElseEmpty;
import static java.util.Collections.emptyList;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Page<T> {

    private int offSet;

    private int limit;

    private long total;

    private List<T> data;

    public List<T> getData() {
        return getOrElseEmpty(data);
    }

    public static <T> Page<T> of(List<T> data, int offSet, int limit) {
        return new Page<>(offSet, limit, data.size(), data);
    }

    public static <T> Page<T> empty(int offSet, int limit) {
        return new Page<>(offSet, limit, 0, emptyList());
    }

}
