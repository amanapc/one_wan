package cn.onewan.provider.sso;

import cn.onewan.common.redis.JedisUtil;
import cn.onewan.common.result.R;
import cn.onewan.common.utils.EncrypUtil;
import cn.onewan.domain.User;
import cn.onewan.mapper.user.UserMapper;
import cn.onewan.service.sso.SSOService;
import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.concurrent.TimeUnit;


@Service("ssoServiceProvider")
public class SSOServiceProvider implements SSOService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private JedisUtil jedisUtil;


    @Override
    public R login(String username, String password) {

        User user = userMapper.selectByUserName(username);
        if (user != null) {
            if (Objects.equals(user.getPassword(), password)) {
                // create Token
                String token = EncrypUtil.md5Pass(user.getId().toString(), username);

                jedisUtil.addStr(token, JSON.toJSONString(user), TimeUnit.MINUTES, 30);

                return new R(1001, "login success", token);
            } else {
                return new R(1003, "password not match");
            }
        } else {
            return new R(1002, "username not exists");
        }

    }

    @Override
    public R loginCheck(String token) {
        if (jedisUtil.isKey(token)) {
            String jsonUser = jedisUtil.getStr(token);
            jedisUtil.expire(token, TimeUnit.MINUTES, 30);
            return new R(1001, "login on", JSON.parseObject(jsonUser, User.class));
        } else {
            return R.setERROR();
        }

    }

    @Override
    public R logout(String token) {

        jedisUtil.delKey(token);

        return R.setOK();
    }
}
