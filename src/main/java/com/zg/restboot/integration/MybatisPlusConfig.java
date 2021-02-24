package com.zg.restboot.integration;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Instant;

@Configuration
public class MybatisPlusConfig {

    /**
     * 新的分页插件,一缓和二缓遵循mybatis的规则,需要设置 MybatisConfiguration#useDeprecatedExecutor = false 避免缓存出现问题
     */
    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor() {
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        // 注册分页插件
        interceptor.addInnerInterceptor(new PaginationInnerInterceptor(DbType.MYSQL));
        return interceptor;
    }

    @Bean
    public MetaObjectHandler metaObjectHandler(){
        return new MetaObjectHandler(){
            /**
             *  insert 时为通用字段设置默认值
             *  */
            @Override
            public void insertFill(MetaObject metaObject) {
                Instant now = Instant.now();

                this.setFieldValByName("deleted",false,metaObject);
                this.setFieldValByName("createTime", now, metaObject);
                this.setFieldValByName( "updateTime", now, metaObject);
            }

            /**
             *  update 时为通用字段设置默认值
             *  */
            @Override
            public void updateFill(MetaObject metaObject) {
                this.setFieldValByName( "updateTime", Instant.now(), metaObject);
            }
        };
    }

}
