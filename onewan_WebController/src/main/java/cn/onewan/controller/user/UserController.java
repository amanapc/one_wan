package cn.onewan.controller.user;

import cn.onewan.common.result.R;
import cn.onewan.domain.User;
import cn.onewan.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("register.do")
    public R register(User user) {
        R result = userService.save(user);

        System.out.println(user);

        return result;

    }


}
