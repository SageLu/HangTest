package com.lizi.fast_fragment.constants;

/**
 * Created by lizi on 2016/11/4.
 */

public class ConstantValues {
    public static final int STATUS_FORCE_KILLED = -1;//被强杀
    public static final int STATUS_LOGOUT = 0;//注销
    public static final int STATUS_OFFLINE = 1;//未登录
    public static final int STATUS_ONLINE = 2;//登陆
    public static final int STATUS_KICK_OUT = 3;//token失效，账号在另一台设备登陆，被挤下线

    public static final String KEY_HOME_ACTION = "key_home_action";
    public static final int ACTION_BACK_TO_HOME = 0;
    public static final int ACTION_RESTART_APP = 1;
    public static final int ACTION_LOGOUT = 2;
    public static final int ACTION_KICK_OUT = 3;
}
