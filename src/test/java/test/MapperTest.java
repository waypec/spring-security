package test;

import com.way.SecurityApplication;
import com.way.bean.User;
import com.way.mapper.UserMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

/**
 * @Author 三更  B站： https://space.bilibili.com/663528522
 */
@SpringBootTest(classes = SecurityApplication.class)
@RunWith(SpringRunner.class)
public class MapperTest {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    public  void test2() {
        //加密  跟MD5相似
        String encode = passwordEncoder.encode("1234");
        System.out.println(encode); //$2a$10$7A/DvbvUYZHBudlWU5LZUOkCrgnMmmpWOR1bratOVDnIEq90R8d/W
        //第一个参数：用户登录输入的密码   第二个密码数据存入加密的密码
        boolean matches = passwordEncoder.matches("1234", encode);
        System.out.println(matches);  //true

    }

    @Test
    public void testUserMapper(){
        List<User> users = userMapper.selectList(null);
        System.out.println(users);
    }
}
