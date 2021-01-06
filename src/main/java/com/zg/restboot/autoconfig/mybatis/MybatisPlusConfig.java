package com.zg.restboot.autoconfig.mybatis;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import org.apache.ibatis.reflection.MetaObject;
import org.mybatis.spring.mapper.MapperScannerConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Instant;

@Configuration
public class MybatisPlusConfig {

    @Bean
    public MapperScannerConfigurer mapperScannerConfigurer(){
        MapperScannerConfigurer scannerConfigurer = new MapperScannerConfigurer();
        //可以通过环境变量获取你的mapper路径,这样mapper扫描可以通过配文件配置了
        scannerConfigurer.setBasePackage("com.zg.restboot.**.dao");
        return scannerConfigurer;
    }

    /**
     * 新的分页插件,一缓和二缓遵循mybatis的规则,需要设置 MybatisConfiguration#useDeprecatedExecutor = false 避免缓存出现问题
     */
    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor() {
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        interceptor.addInnerInterceptor(new PaginationInnerInterceptor(DbType.MYSQL));
        return interceptor;
    }

    @Bean
    public MetaObjectHandler metaObjectHandler(){
        return new MetaObjectHandler(){
            @Override
            public void insertFill(MetaObject metaObject) {
                Instant now = Instant.now();
                this.setFieldValByName("deleted",false,metaObject);
                this.setFieldValByName("createTime", now, metaObject);
                this.setFieldValByName("createUser", 12L, metaObject); // todo 获取当前用户
                this.setFieldValByName( "updateTime", now, metaObject);
                this.setFieldValByName("updateUser", 12L, metaObject); // todo 获取当前用户
            }

            @Override
            public void updateFill(MetaObject metaObject) {
                this.setFieldValByName( "updateTime", Instant.now(), metaObject);
                this.setFieldValByName("updateUser", 12L, metaObject); // todo 获取当前用户
            }
        };
    }

}
