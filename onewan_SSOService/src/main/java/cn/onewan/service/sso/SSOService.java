package cn.onewan.service.sso;

import cn.onewan.common.result.R;

public interface SSOService {

    R login(String username, String password);

    R loginCheck(String token);

    R logout(String token);
}
