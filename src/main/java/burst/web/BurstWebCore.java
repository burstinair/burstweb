package burst.web;

import burst.web.contextbuilder.IContextBuilder;
import burst.web.contextbuilder.IContextBuilderRegistry;
import burst.web.dispatcher.IDispatcher;
import burst.web.dispatcher.IDispatcherRegistry;
import burst.web.errorhandle.IErrorHandler;
import burst.web.errorhandle.IErrorHandlerRegistry;
import burst.web.framework.DispatcherServletInitEvent;
import burst.web.requestbuilder.IRequestBuilder;
import burst.web.requestbuilder.IRequestBuilderRegistry;
import burst.web.framework.DispatcherServlet;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationListener;
import org.springframework.web.context.WebApplicationContext;

import java.util.Map;

/**
 * 核心配置类，需将该类置入Spring容器中
 *
 * @author Burst
 *         13-12-4 上午12:42
 */
public class BurstWebCore implements ApplicationListener<DispatcherServletInitEvent>, IDispatcherRegistry, IRequestBuilderRegistry, IContextBuilderRegistry, IErrorHandlerRegistry {

    private DispatcherServlet servlet;

    private String servletName;

    public String getServletName() {
        return servletName;
    }

    @Required
    public void setServletName(String servletName) {
        this.servletName = servletName;
    }

    @Override
    public void onApplicationEvent(DispatcherServletInitEvent event) {
        if(event.getServletName() != null && event.getServletName().equals(this.servletName)) {
            this.servlet = event.getDispatcherServlet();
        }
    }

    @Override
    public void setDispatcher(IDispatcher dispatcher) {
        servlet.setDispatcher(dispatcher);
    }

    @Override
    public void setRequestBuilder(IRequestBuilder requestBuilder) {
        servlet.setRequestBuilder(requestBuilder);
    }

    @Override
    public void setContextBuilder(IContextBuilder contextBuilder) {
        servlet.setContextBuilder(contextBuilder);
    }

    @Override
    public void setErrorHandlerMap(Map<String, IErrorHandler> errorHandlerMap) {
        servlet.setErrorHandlerMap(errorHandlerMap);
    }
}
