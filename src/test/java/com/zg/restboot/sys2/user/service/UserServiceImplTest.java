package com.zg.restboot.sys2.user.service;

import com.zg.restboot.sys2.user.service.dto.UserInsertDTO;
import com.zg.restboot.sys2.user.service.vo.UserVO;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

/**
 * @author edz
 * @date 2020/12/31
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
class UserServiceImplTest {

    @Resource
    UserService service;

    @Test
    void CURD() {
        UserInsertDTO insertDTO = UserInsertDTO.builder().username("test").password("test").build();
        UserVO entity = service.save(insertDTO);
        Assertions.assertNotNull(entity);

        Long id = entity.getId();
        UserVO getVo = service.get(entity.getId());
        Assertions.assertEquals(entity.getUsername(),getVo.getUsername());
        Assertions.assertEquals(entity.getPassword(),getVo.getPassword());
        Assertions.assertFalse((Boolean) getVo.getDeleted());


        service.delete(id);
        getVo = service.get(entity.getId());
        Assertions.assertEquals(getVo.getDeleted(),0);


    }

    @Test
    void find() {
    }

    @Test
    void testFind() {
    }

    @Test
    void save() {

    }

    @Test
    void update() {
    }

    @Test
    void delete() {
    }

    @Test
    void testDelete() {
    }
}
