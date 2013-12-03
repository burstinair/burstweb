package burst.web;

import burst.web.contextbuilder.IContextBuilder;
import burst.web.contextbuilder.IContextBuilderRegistry;
import burst.web.dispatcher.IDispatcher;
import burst.web.dispatcher.IDispatcherRegistry;
import burst.web.errorhandle.IErrorHandler;
import burst.web.errorhandle.IErrorHandlerRegistry;
import burst.web.requestbuilder.IRequestBuilder;
import burst.web.requestbuilder.IRequestBuilderRegistry;
import burst.web.servlet.DispatcherServlet;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import java.util.Map;

/**
 * 核心配置类，需将该类置入Spring容器中
 *
 * @author Burst
 *         13-12-4 上午12:42
 */
public class BurstWebCore implements ApplicationContextAware, IDispatcherRegistry, IRequestBuilderRegistry, IContextBuilderRegistry, IErrorHandlerRegistry {

    public static BurstWebCore INSTANCE;

    private BurstWebCore() {
        INSTANCE = this;
    }

    @Override
    public void setDispatcher(IDispatcher dispatcher) {
        DispatcherServlet.INSTANCE.setDispatcher(dispatcher);
    }

    @Override
    public void setRequestBuilder(IRequestBuilder requestBuilder) {
        DispatcherServlet.INSTANCE.setRequestBuilder(requestBuilder);
    }

    @Override
    public void setContextBuilder(IContextBuilder contextBuilder) {
        DispatcherServlet.INSTANCE.setContextBuilder(contextBuilder);
    }

    @Override
    public void setErrorHandlerMap(Map<String, IErrorHandler> errorHandlerMap) {
        DispatcherServlet.INSTANCE.setErrorHandlerMap(errorHandlerMap);
    }

    private ApplicationContext applicationContext;

    public ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
