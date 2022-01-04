package com.lanshu.community.interceptor;

import com.lanshu.community.mapper.UserMapper;
import com.lanshu.community.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class UserInterceptor implements HandlerInterceptor {

    @Autowired
    private UserMapper userMapper;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        Cookie[] cookies = request.getCookies();
        User user;
        if (cookies!=null){
            for (Cookie cookie: cookies) {
                //如果cookie中有key为token的value，取出并去mysql中查找用户信息
                //如果有匹配用户则放入session中，实现持久化登录状态
                if ("token".equals(cookie.getName())) {
                    user = userMapper.findByToken(cookie.getValue());
                    if (user != null)
                        request.getSession().setAttribute("user",user);
                    break;
                }
            }
        }
        /*if (user == null) {
            response.sendRedirect(request.getContextPath() + "/");
            return false;
        }*/
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response,
                           Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response,
                                Object handler, Exception ex) throws Exception {

    }
}
