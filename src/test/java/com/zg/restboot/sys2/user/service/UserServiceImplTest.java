package com.zg.restboot.sys2.user.service;

import com.zg.restboot.sys.user.service.UserService;
import com.zg.restboot.sys.user.domain.vo.UserVO;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

/**
 * @author edz
 * @date 2020/12/31
 */
@SpringBootTest
@Slf4j
class UserServiceImplTest {

    @Resource
    UserService service;

    @Test
    void CURD() {
//        UserInsertDTO insertDTO = UserInsertDTO.builder().username("test").password("test").build();
//        // test insert
//        UserVO vo = service.save(insertDTO);
//        Assertions.assertNotNull(vo);
//
//        // test get
//        vo = service.get(vo.getId());
//        Assertions.assertNotNull(vo);
//
//        Assertions.assertEquals(vo.getUsername(),insertDTO.getUsername());
//        Assertions.assertEquals(vo.getPassword(),insertDTO.getPassword());
//        Assertions.assertFalse(vo.getDeleted());
//        Assertions.assertNotNull(vo.getCreateTime());
//        Assertions.assertNotNull(vo.getCreateUser());
//        Assertions.assertNotNull(vo.getUpdateTime());
//        Assertions.assertNotNull(vo.getUpdateUser());
//
//        // test delete
//        service.delete(vo.getId());
//        vo = service.get(vo.getId());
//        Assertions.assertNull(vo);
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
