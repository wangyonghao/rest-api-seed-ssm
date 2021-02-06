package com.zg.restboot.common.page;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 分页查询
 *
 * @author edz
 * @date 2020/12/31
 */
@ApiModel(description = "分页查询结果")
@Getter
@Setter
public class PageResult<T>{
    @ApiModelProperty("总数")
    private long total;
    @ApiModelProperty("当前页数据")
    private List<T> data;

    public PageResult(long total, List<T> data) {
        this.total = total;
        this.data = data;
    }

    /**
     * 将总数添加到 response 的 headers 中
     *
     * @param response
     * @return
     */
    @CanIgnoreReturnValue
    public PageResult<T> addToHeader(HttpServletResponse response) {
        response.addHeader("x-page-total", String.valueOf(this.getTotal()));
        return this;
    }

    /**
     * 包装分页结果
     *
     * @param page            MybatisPlus 分页结果类
     * @param convertFunction Entity 类转换 VO类函数
     * @param <E>             Entity
     * @param <V>             VO
     * @return 返回分页结果集 PageResult<VO>
     */
    public static <E, V> PageResult<V> wrapIPage(IPage<E> page, Function<E, V> convertFunction) {
        List<V> data = page.getRecords().stream().map(convertFunction).collect(Collectors.toList());
        return new PageResult<V>(page.getTotal(), data);
    }
}
