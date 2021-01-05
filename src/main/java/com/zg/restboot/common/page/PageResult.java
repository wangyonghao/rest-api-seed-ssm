package com.zg.restboot.common.page;

import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.Collection;

/**
 * 分页查询
 * @author edz
 * @date 2020/12/31
 */
@ApiModel(value = "PageVO",description = "分页查询结果")
@Getter
@Setter
public class PageResult<T>{
    @ApiModelProperty("总数")
    private long count;
    @ApiModelProperty("分页数据集合")
    private Collection<T> data;

    public PageResult(long count, Collection<T> data){
        this.count = count;
        this.data  = data;
    }
}
