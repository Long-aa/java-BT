package filter;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.time.LocalDateTime;

@WebFilter(urlPatterns = "/*")
public class LogFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) {
        System.out.println("LogFilter initialized");
    }

    @Override
    public void doFilter(ServletRequest request,
                         ServletResponse response,
                         FilterChain chain)
            throws IOException, ServletException {
        
        HttpServletRequest req = (HttpServletRequest) request;
        HttpSession session = req.getSession(false);
        String username = "guest";
        
        if (session != null && session.getAttribute("username") != null) {
            username = (String) session.getAttribute("username");
        }

        String uri = req.getRequestURI();
        String method = req.getMethod();
        LocalDateTime time = LocalDateTime.now();

        System.out.println("[ACCESS LOG] Time: " + time + " | User: " + username + " | Method: " + method + " | URI: " + uri);

        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
        System.out.println("LogFilter destroyed");
    }
}
