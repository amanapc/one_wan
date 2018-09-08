package cn.onewan.mapper.user;

import cn.onewan.domain.User;

public interface UserMapper {

    int insert(User user);

    User selectByUserName(String username);

}
