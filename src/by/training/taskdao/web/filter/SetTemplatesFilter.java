package by.training.taskdao.web.filter;

import by.training.taskdao.utils.FileReaderUtil;
import by.training.taskdao.web.config.TemplateFolderManager;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@WebFilter(filterName = "SetTemplatesFilter")
public class SetTemplatesFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        String path = TemplateFolderManager.getInstance().getPathInfo();
        if (request.getSession().getAttribute("template") == null) {
            String html = FileReaderUtil.readAllFromFile(path, "template.html");
            request.getSession().setAttribute("template", html);
        }
        String requestPage = request.getServletPath();
        String control = FileReaderUtil.readAllFromFile(path + requestPage + "/", requestPage + "Control.html");
        String headers = FileReaderUtil.readAllFromFile(path + requestPage + "/", requestPage + "Headers.html");
        request.setAttribute("controls", control);
        request.setAttribute("headers", headers);
        filterChain.doFilter(request, servletResponse);
    }

    @Override
    public void destroy() {

    }
}
