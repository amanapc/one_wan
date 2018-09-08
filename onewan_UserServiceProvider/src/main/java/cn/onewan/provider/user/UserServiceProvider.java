package cn.onewan.provider.user;

import cn.onewan.common.result.R;
import cn.onewan.domain.User;
import cn.onewan.mapper.user.UserMapper;
import cn.onewan.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class UserServiceProvider implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public R save(User user) {
        int row = userMapper.insert(user);
        return row > 0 ? R.setOK() : R.setERROR();
    }
}
