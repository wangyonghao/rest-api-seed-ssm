package com.zg.restboot.user.vo;

import com.zg.restboot.common.page.Pageable;
import lombok.Getter;
import lombok.Setter;

/**
 * 用户查询条件
 * @author wangyonghao
 * @date 12/15/20
 */
@Getter
@Setter
public class UserQuery extends Pageable {
    private String name;
    private Integer score;
}
