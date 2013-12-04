package burst.web.framework;

import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.util.Log4jWebConfigurer;

/**
 * 入口Servlet
 *
 * @author Burst
 *         13-12-3 下午11:48
 */
public class DispatcherServlet extends BaseDispatcherServlet {

    @Override
    public void init() {

        super.init();

        WebApplicationContext context;

        if(getServletContext().getAttribute(WebApplicationContext.ROOT_WEB_APPLICATION_CONTEXT_ATTRIBUTE) != null) {
            context = WebApplicationContextUtils.getWebApplicationContext(getServletContext());
        } else {
            ContextLoader contextLoader = new ContextLoader();
            context = contextLoader.initWebApplicationContext(getServletContext());

            Log4jWebConfigurer.initLogging(getServletContext());
        }

        DispatcherServletInitEvent dispatcherServletInitEvent = new DispatcherServletInitEvent(this, getServletConfig().getServletName());
        context.publishEvent(dispatcherServletInitEvent);

        setActionContext(context);
    }

}
