package com.fzubb.user.service;


import com.fzubb.user.model.User;

/**个人信息接口*/
public interface UserService<T extends User> {
     /**绑定*/
     T bind(T user);
     /**接触绑定*/
     T unbind(T user);
     /**查询信息*/
     T query(T user);
}
