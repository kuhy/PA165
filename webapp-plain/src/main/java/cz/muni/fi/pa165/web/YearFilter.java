package cz.muni.fi.pa165.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Calendar;

/**
 * Filter that sets current year.
 */
@WebFilter("/*")
public class YearFilter implements Filter {

    private final static Logger log = LoggerFactory.getLogger(YearFilter.class);

    public void doFilter(ServletRequest r, ServletResponse s, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) r;
        log.info("Requested url: " + request.getRequestURL().toString());

        request.setAttribute("currentyear", Calendar.getInstance().get(Calendar.YEAR));

        HttpServletResponse response = (HttpServletResponse) s;
        filterChain.doFilter(request, response);
    }

    public void init(FilterConfig filterConfig) {
        log.debug("filter initialized ...");
    }

    public void destroy() {
    }
}
