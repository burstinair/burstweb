package burst.web.framework;

import org.springframework.context.ApplicationEvent;

/**
 * @author zhongkai.zhao
 *         13-12-4 下午1:15
 */
public class DispatcherServletInitEvent extends ApplicationEvent {

    private DispatcherServlet dispatcherServlet;
    private String servletName;

    public DispatcherServletInitEvent(Object source) {
        super(source);

        this.dispatcherServlet = (DispatcherServlet) source;
    }

    public DispatcherServletInitEvent(DispatcherServlet source, String servletName) {
        super(source);

        this.dispatcherServlet = source;
        this.servletName = servletName;
    }

    public DispatcherServlet getDispatcherServlet() {
        return dispatcherServlet;
    }

    public void setDispatcherServlet(DispatcherServlet dispatcherServlet) {
        this.dispatcherServlet = dispatcherServlet;
    }

    public String getServletName() {
        return servletName;
    }

    public void setServletName(String servletName) {
        this.servletName = servletName;
    }
}
