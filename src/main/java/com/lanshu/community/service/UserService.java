package com.lanshu.community.service;

import com.lanshu.community.dto.GithubUser;
import com.lanshu.community.mapper.UserMapper;
import com.lanshu.community.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UserService {
    @Autowired
    private UserMapper userMapper;

    public String CreatOrUpdate(GithubUser githubUser){
        //使用 AccountId 去数据库查找，AccountId 唯一
        User user = userMapper.findByAccountId(githubUser.getId());

        if (user == null){
            //数据库没有user，则添加
            user.setName(githubUser.getName());
            user.setAccountId(String.valueOf(githubUser.getId()));
            String token = UUID.randomUUID().toString();
            user.setToken(token);
            user.setGmtCreate(System.currentTimeMillis());
            user.setGmtModified(System.currentTimeMillis());
            user.setAvatarUrl(githubUser.getAvatarUrl());
            userMapper.insert(user);
        } else {
            //更新用户信息
            user.setName(githubUser.getName());
            user.setToken(UUID.randomUUID().toString());
            user.setGmtModified(System.currentTimeMillis());
            user.setAvatarUrl(githubUser.getAvatarUrl());
            userMapper.updateByPrimaryKeySelective(user);
        }
        return user.getToken();
    }
}
