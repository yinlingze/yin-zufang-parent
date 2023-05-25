package org.example.interceptor;

import org.example.result.Result;
import org.example.result.ResultCodeEnum;
import org.example.util.WebUtil;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 前端登录拦截器
 */
@SuppressWarnings({"unchecked", "rawtypes"})
public class LoginInterceptor implements HandlerInterceptor {
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object object, Exception exception)
            throws Exception {

    }

    public void postHandle(HttpServletRequest request, HttpServletResponse response,
                           Object object, ModelAndView model) throws Exception {

    }

    //处理请求前
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object object) throws Exception {
        //获取用户登录信息
        Object userInfo = request.getSession().getAttribute("USER");
        //验证是否登录
        if(null == userInfo) {
            Result result = Result.build("未登陆", ResultCodeEnum.LOGIN_AUTH);
            //将数据响应到前端
            WebUtil.writeJSON(response, result);
            return false;
        }
        return true;
    }

}
