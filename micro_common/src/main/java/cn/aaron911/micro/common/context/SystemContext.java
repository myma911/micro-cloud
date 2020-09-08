package cn.aaron911.micro.common.context;

import cn.aaron911.micro.common.pojo.User;

/**
 * @description:
 * @author:
 * @time: 2020/9/8 14:03
 */
public class SystemContext {

    private static ThreadLocal<User> userThreadLocal = new ThreadLocal<>();

    /**
     * 获取线程user
     * @return
     */
    public static User getUser(){
        return userThreadLocal.get();
    }

    /**
     * 设置线程user
     * @param user
     */
    public static void setUser(User user){
        if (null == user) {
            throw new IllegalArgumentException();
        }
        userThreadLocal.set(user);
    }

    /**
     * 清除
     */
    public static void remove(){
        userThreadLocal.remove();
    }
}
