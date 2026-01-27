package com.fs.common.result;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * 分页返回结果
 *
 * @param <T> 数据类型
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PageResult<T> implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 总记录数
     */
    private Long total;

    /**
     * 当前页码
     */
    private Long page;

    /**
     * 每页大小
     */
    private Long size;

    /**
     * 数据列表
     */
    private List<T> records;

    /**
     * 总页数
     */
    private Long pages;

    /**
     * 是否有下一页
     */
    private Boolean hasNext;

    /**
     * 是否有上一页
     */
    private Boolean hasPrevious;

    /**
     * 空分页结果
     */
    public static <T> PageResult<T> empty() {
        return PageResult.<T>builder()
                .total(0L)
                .page(1L)
                .size(10L)
                .records(List.of())
                .pages(0L)
                .hasNext(false)
                .hasPrevious(false)
                .build();
    }

    /**
     * 构建分页结果
     */
    public static <T> PageResult<T> of(List<T> records, Long total, Long page, Long size) {
        if (records == null) {
            records = List.of();
        }
        
        Long pages = (total + size - 1) / size; // 计算总页数
        Boolean hasNext = page < pages;
        Boolean hasPrevious = page > 1;

        return PageResult.<T>builder()
                .total(total)
                .page(page)
                .size(size)
                .records(records)
                .pages(pages)
                .hasNext(hasNext)
                .hasPrevious(hasPrevious)
                .build();
    }
}
