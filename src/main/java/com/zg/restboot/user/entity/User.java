package com.zg.restboot.user.entity;

import com.sun.istack.internal.NotNull;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Past;
import java.time.LocalDate;

/**
 * @author wangyonghao
 * @date 12/15/20
 */
@Getter
@Setter
public class User {
    private String id;
    @NotBlank(message="密码不能为空")
    private String name;
    @Past(message = "生日必须是过去日期")
    private LocalDate birthday;
    private Integer score;
}
