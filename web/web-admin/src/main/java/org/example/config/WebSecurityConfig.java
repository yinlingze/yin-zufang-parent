package org.example.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration //声明当前类为配置类
@EnableWebSecurity  //@EnableWebSecurity是开启SpringSecurity的默认行为,会生成登录页面
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    //在内存中设置一个认证管理的用户名和密码
//    @Override
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth.inMemoryAuthentication()
//                .withUser("admin")
//                .password(new BCryptPasswordEncoder().encode("123456"))
//                .roles("");
//    }


    /**
     * 必须指定加密方式，上下加密方式要一致
     * @return
     */
    @Bean
    public PasswordEncoder getPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //必须调用父类的方法，否则就不需要认证即可访问
        //super.configure(http);
        //允许iframe嵌套显示
        //http.headers().frameOptions().disable();
        //允许iframe显示
        http.headers().frameOptions().sameOrigin();
        //登录设置 配置可以匿名访问的资源
        http.authorizeRequests().antMatchers("/static/**","/login")//允许匿名用户访问的路径
                .permitAll().anyRequest().authenticated()// 其它页面全部需要验证
                .and().formLogin()
                .loginPage("/login")////用户未登录时，访问任何需要权限的资源都转跳到该路径，即登录页面，此时登陆成功后会继续跳转到第一次访问的资源页面（相当于被过滤了一下）
                .defaultSuccessUrl("/") //登录认证成功后默认转跳的路径，意思时admin登录后也跳转到/user
                .and()
                .logout()
                .logoutUrl("/logout")   //退出登陆的路径，指定spring security拦截的注销url,退出功能是security提供的
                .logoutSuccessUrl("/login")//用户退出后要被重定向的url
                .and()
                .csrf().disable();//关闭跨域请求伪造功能

        //配置自定义无权限异常入口
        http.exceptionHandling().accessDeniedHandler(new CustomAccessDeineHandler());
    }

}
