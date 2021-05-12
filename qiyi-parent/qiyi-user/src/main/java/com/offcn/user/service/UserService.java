package com.offcn.user.service;

import com.offcn.user.pojo.TMember;
import com.offcn.user.pojo.TMemberAddress;

import java.util.List;

public interface UserService {

    /**
     * 用户注册
     * @param member
     */
    public void registerUser(TMember member);

    /**
     * 用户登录
     * @param username
     * @param password
     * @return
     */
    public TMember login(String username,String password);

    /**
     * 获取用户基本信息
     * @param id
     * @return
     */
    public TMember findMemberById(Integer id);

    /**
     * 获取用户收货地址
     * @param memberId
     * @return
     */
    public List<TMemberAddress> findAddressList(Integer memberId);

}
