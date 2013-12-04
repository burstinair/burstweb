package burst.web.framework;

import burst.web.*;
import burst.web.enums.ErrorCode;
import burst.web.contextbuilder.DefaultContextBuilder;
import burst.web.contextbuilder.IContextBuilder;
import burst.web.contextbuilder.IContextBuilderRegistry;
import burst.web.dispatcher.DefaultDispatcher;
import burst.web.dispatcher.IDispatcher;
import burst.web.dispatcher.IDispatcherRegistry;
import burst.web.enums.HttpMethod;
import burst.web.errorhandle.IErrorHandler;
import burst.web.errorhandle.IErrorHandlerRegistry;
import burst.web.errorhandle.NotFoundHandler;
import burst.web.errorhandle.ServerErrorHandler;
import burst.web.requestbuilder.DefaultRequestBuilder;
import burst.web.requestbuilder.IRequestBuilder;
import burst.web.requestbuilder.IRequestBuilderRegistry;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

/**
 * 入口Servlet
 *
 * @author Burst
 *         13-12-3 下午11:48
 */
public class DispatcherServlet extends HttpServlet implements IDispatcherRegistry, IRequestBuilderRegistry, IContextBuilderRegistry, IErrorHandlerRegistry {

    private WebApplicationContext webApplicationContext;

    @Override
    public void init() {

        if(getServletContext().getAttribute(WebApplicationContext.ROOT_WEB_APPLICATION_CONTEXT_ATTRIBUTE) != null) {
            webApplicationContext = WebApplicationContextUtils.getWebApplicationContext(getServletContext());
        } else {
            ContextLoader contextLoader = new ContextLoader();
            webApplicationContext = contextLoader.initWebApplicationContext(getServletContext());
        }

        DISPATCHER = DefaultDispatcher.INSTANCE;
        REQUEST_BUILDER = DefaultRequestBuilder.INSTANCE;
        CONTEXT_BUILDER = DefaultContextBuilder.INSTANCE;

        ERROR_HANDLER_MAP = new HashMap<String, IErrorHandler>();
        ERROR_HANDLER_MAP.put(ErrorCode.NOT_FOUND, NotFoundHandler.INSTANCE);
        ERROR_HANDLER_MAP.put(ErrorCode.SERVER_ERROR, ServerErrorHandler.INSTANCE);

        DispatcherServletInitEvent dispatcherServletInitEvent = new DispatcherServletInitEvent(this, getServletConfig().getServletName());
        webApplicationContext.publishEvent(dispatcherServletInitEvent);
    }

    private IDispatcher DISPATCHER;

    @Override
    public void setDispatcher(IDispatcher dispatcher) {
        DISPATCHER = dispatcher;
    }

    private IRequestBuilder REQUEST_BUILDER;

    @Override
    public void setRequestBuilder(IRequestBuilder requestBuilder) {
        REQUEST_BUILDER = requestBuilder;
    }

    private IContextBuilder CONTEXT_BUILDER;

    @Override
    public void setContextBuilder(IContextBuilder contextBuilder) {
        CONTEXT_BUILDER = contextBuilder;
    }

    private Map<String, IErrorHandler> ERROR_HANDLER_MAP;

    @Override
    public void setErrorHandlerMap(Map<String, IErrorHandler> errorHandlerMap) {
        ERROR_HANDLER_MAP.putAll(errorHandlerMap);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException {
        handleRequest(request, response, HttpMethod.GET);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException {
        handleRequest(request, response, HttpMethod.POST);
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException {
        handleRequest(request, response, HttpMethod.PUT);
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException {
        handleRequest(request, response, HttpMethod.DELETE);
    }

    protected boolean tryHandleError(String code, Object errorContext, HttpServletRequest rawRequest, HttpServletResponse rawResponse, HttpMethod httpMethod) {
        IErrorHandler handler = ERROR_HANDLER_MAP.get(code);
        if(handler != null) {
            handler.handle(errorContext, rawRequest, rawResponse, httpMethod);
            return true;
        }
        return false;
    }

    protected <REQUEST extends IRequest> Class<REQUEST> getRequestClass(Class<? extends IAction<REQUEST>> actionClass) {
        ParameterizedType actionInterface = null;
        for(Type type : actionClass.getGenericInterfaces()) {
            if(IAction.class.equals(((ParameterizedType)type).getRawType())) {
                actionInterface = (ParameterizedType)type;
            }
        }
        return (Class) actionInterface.getActualTypeArguments()[0];
    }

    protected void handleRequest(HttpServletRequest rawRequest, HttpServletResponse rawResponse, HttpMethod httpMethod) throws ServletException{
        IAction action = DISPATCHER.dispatch(webApplicationContext, rawRequest, rawResponse, httpMethod);
        if(action == null) {
            tryHandleError(ErrorCode.NOT_FOUND, null, rawRequest, rawResponse, httpMethod);
            return;
        }

        try {
            IRequest request = REQUEST_BUILDER.build(getRequestClass(action.getClass()), rawRequest, rawResponse, httpMethod);
            IContext context = CONTEXT_BUILDER.build(rawRequest, rawResponse, httpMethod);
            IResponse response = action.execute(request, context);
            if(response.getErrorCode() != null) {
                if(tryHandleError(response.getErrorCode(), null, rawRequest, rawResponse, httpMethod)) {
                    return;
                }
            }
            response.response(rawRequest, rawResponse, httpMethod);
        } catch (Throwable ex) {
            if(tryHandleError(ErrorCode.SERVER_ERROR, ex, rawRequest, rawResponse, httpMethod)) {
                throw new ServletException("burst web: DispatcherServlet, unhandled error", ex);
            }
        }
    }
}
