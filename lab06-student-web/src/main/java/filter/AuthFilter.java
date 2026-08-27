package filter;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebFilter(urlPatterns = {"/students", "/student-form.jsp", "/welcome.jsp", "/dashboard"})
public class AuthFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) {
        System.out.println("AuthFilter initialized");
    }

    @Override
    public void doFilter(ServletRequest request,
                         ServletResponse response,
                         FilterChain chain)
            throws IOException, ServletException {
        
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;

        HttpSession session = req.getSession(false);
        boolean loggedIn = session != null && session.getAttribute("username") != null;

        if (loggedIn) {
            String role = (String) session.getAttribute("role");
            String uri = req.getRequestURI();
            String action = req.getParameter("action");
            String method = req.getMethod();
            
            boolean isSensitiveAction = uri.endsWith("/student-form.jsp") || 
                                        (uri.endsWith("/students") && "POST".equalsIgnoreCase(method)) ||
                                        (uri.endsWith("/students") && ("edit".equals(action) || "delete".equals(action)));
            
            if (isSensitiveAction && !"admin".equals(role)) {
                resp.sendRedirect(req.getContextPath() + "/403.jsp");
            } else {
                chain.doFilter(request, response);
            }
        } else {
            resp.sendRedirect(req.getContextPath() + "/login.jsp");
        }
    }

    @Override
    public void destroy() {
        System.out.println("AuthFilter destroyed");
    }
}
