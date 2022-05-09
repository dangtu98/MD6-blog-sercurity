package com.codegym.config;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Component
public class CustomSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
    private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

    @Override
    protected void handle(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
            throws IOException {
        String targetUrl = determineTargetUrl(authentication);

        if (response.isCommitted()) {
            System.out.println("Can't redirect");
            return;
        }

        redirectStrategy.sendRedirect(request, response, targetUrl);
    }

    /*
     * Phương thức này được sử dụng để lấy ra các role của user hiện tại đang đăng nhập
     * và trả về URL tương ứng
     */
    protected String determineTargetUrl(Authentication authentication) {
        String url = "";

        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();

        List<String> roles = new ArrayList<String>();

        for (GrantedAuthority a : authorities) {
            roles.add(a.getAuthority());
        }

        if (isAdmin(roles)) {
            //Nếu là tài khoản đăng nhập có role là ADMIN thì điều hướng đến /admin
            url = "/admin";
        } else if (isStudent(roles)) {
            //Nếu là tài khoản đăng nhập có role là USER thì điều hướng đến /home
            url = "/students";
        } else if (isOfficer(roles)) {
            url = "/officers";
        }else if (isTeacher(roles)) {
            url = "/teachers";
        } else {
            //Nếu tài khoản đăng nhập không có quyền truy cập sẽ điều hướng tới /accessDenied
            url = "/accessDenied";
        }

        return url;
    }

    private boolean isStudent(List<String> roles) {
        if (roles.contains("STUDENT")) {
            return true;
        }
        return false;
    }

    private boolean isAdmin(List<String> roles) {
        if (roles.contains("ADMIN")) {
            return true;
        }
        return false;
    }

    private boolean isTeacher(List<String> roles) {
        if (roles.contains("TEACHER")) {
            return true;
        }
        return false;
    }

    private boolean isOfficer(List<String> roles) {
        if (roles.contains("OFFICER")) {
            return true;
        }
        return false;
    }



    public void setRedirectStrategy(RedirectStrategy redirectStrategy) {
        this.redirectStrategy = redirectStrategy;
    }

    protected RedirectStrategy getRedirectStrategy() {
        return redirectStrategy;
    }
}
