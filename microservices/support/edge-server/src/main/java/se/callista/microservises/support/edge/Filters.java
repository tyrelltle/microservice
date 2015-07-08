package se.callista.microservises.support.edge;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class Filters implements Filter {

    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
        HttpServletResponse response = (HttpServletResponse) res;
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE");
        response.setHeader("Access-Control-Max-Age", "3600");
        response.setHeader("Access-Control-Allow-Headers", "Accept, Content-Type, Authorization, Content-Length, X-Requested-With");//x-requested-with");

        if(!((HttpServletRequest)req).getMethod().equalsIgnoreCase("OPTIONS")) {
            chain.doFilter(req, res);
        }else{
            ((HttpServletResponse) res).setStatus(HttpServletResponse.SC_OK);
        }
    }

    public void init(FilterConfig filterConfig) {}

    public void destroy() {}

}