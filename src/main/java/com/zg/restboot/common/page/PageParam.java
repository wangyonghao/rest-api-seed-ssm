package com.zg.restboot.common.page;

import com.baomidou.mybatisplus.core.metadata.IPage;

/**
 * 分页查询参数
 * @author wangyonghao
 * @date 12/15/20
 */
public class PageParam {
    static final int MAX_PERPAGE = 500; // 最大每页显示数量
    static final int MIN_PAGE = 1; // 最小页码

    Integer page = 1; // 页码
    Integer perpage = 500; // 每页显示数量,为-1
    String sort; // 排序，格式为field_asc or field_desc，多个排序字段以逗号分割

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        if(page < MIN_PAGE){
            this.page = MIN_PAGE;
        }
        this.page = page;
    }

    public Integer getPerpage() {
        return perpage;
    }

    public void setPerpage(Integer perpage) {
        if(perpage > MAX_PERPAGE){
            perpage = MAX_PERPAGE;
        }
        this.perpage = perpage;
    }

    /**
     * 将 PageQuery 转换为 IPage<T>
     * @param pageParam
     * @param <T>
     * @return
     */
    public static <T> IPage<T> toIPage(PageParam pageParam){
        IPage<T> page = new com.baomidou.mybatisplus.extension.plugins.pagination.Page<>();
        page.setCurrent(pageParam.getPage());
        page.setSize(pageParam.getPerpage());
        return page;
    }
}
