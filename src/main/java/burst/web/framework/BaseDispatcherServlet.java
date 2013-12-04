package burst.web.framework;

import burst.web.IAction;
import burst.web.IContext;
import burst.web.IRequest;
import burst.web.IResponse;
import burst.web.contextbuilder.DefaultContextBuilder;
import burst.web.contextbuilder.IContextBuilder;
import burst.web.contextbuilder.IContextBuilderRegistry;
import burst.web.dispatcher.DefaultDispatcher;
import burst.web.dispatcher.IDispatcher;
import burst.web.dispatcher.IDispatcherRegistry;
import burst.web.enums.ErrorCode;
import burst.web.enums.HttpMethod;
import burst.web.errorhandle.IErrorHandler;
import burst.web.errorhandle.IErrorHandlerRegistry;
import burst.web.errorhandle.NotFoundHandler;
import burst.web.errorhandle.ServerErrorHandler;
import burst.web.requestbuilder.DefaultRequestBuilder;
import burst.web.requestbuilder.IRequestBuilder;
import burst.web.requestbuilder.IRequestBuilderRegistry;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

/**
 * @author zhongkai.zhao
 *         13-12-4 下午7:41
 */
public abstract class BaseDispatcherServlet extends HttpServlet implements IDispatcherRegistry, IRequestBuilderRegistry, IContextBuilderRegistry, IErrorHandlerRegistry {

    private Object actionContext;

    protected Object getActionContext() {
        return actionContext;
    }

    protected void setActionContext(Object actionContext) {
        this.actionContext = actionContext;
    }

    @Override
    public void init() {

        DISPATCHER = DefaultDispatcher.INSTANCE;
        REQUEST_BUILDER = DefaultRequestBuilder.INSTANCE;
        CONTEXT_BUILDER = DefaultContextBuilder.INSTANCE;

        ERROR_HANDLER_MAP = new HashMap<String, IErrorHandler>();
        ERROR_HANDLER_MAP.put(ErrorCode.NOT_FOUND, NotFoundHandler.INSTANCE);
        ERROR_HANDLER_MAP.put(ErrorCode.SERVER_ERROR, ServerErrorHandler.INSTANCE);
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
        handleRequest(new RawContext(request, response, HttpMethod.GET));
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException {
        handleRequest(new RawContext(request, response, HttpMethod.POST));
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException {
        handleRequest(new RawContext(request, response, HttpMethod.PUT));
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException {
        handleRequest(new RawContext(request, response, HttpMethod.DELETE));
    }

    protected boolean tryHandleError(String code, Object errorContext, RawContext rawContext) {
        IErrorHandler handler = ERROR_HANDLER_MAP.get(code);
        if(handler != null) {
            handler.handle(errorContext, rawContext);
            return true;
        }
        return false;
    }

    protected <REQUEST extends IRequest> Class<REQUEST> getRequestClass(Class<? extends IAction> actionClass) {
        ParameterizedType actionInterface = null;
        for(Type type : actionClass.getGenericInterfaces()) {
            if(IAction.class.equals(((ParameterizedType)type).getRawType())) {
                actionInterface = (ParameterizedType)type;
            }
        }
        return (Class) actionInterface.getActualTypeArguments()[0];
    }

    protected void handleRequest(RawContext rawContext) throws ServletException{
        IAction action = DISPATCHER.dispatch(actionContext, rawContext);
        if(action == null) {
            tryHandleError(ErrorCode.NOT_FOUND, null, rawContext);
            return;
        }

        IResponse response = null;
        try {
            Class<? extends IRequest> requestClass = getRequestClass(action.getClass());
            IRequest request = REQUEST_BUILDER.build(requestClass, rawContext);
            IContext context = CONTEXT_BUILDER.build(rawContext);
            response = action.execute(request, context);
        } catch (Throwable ex) {
            if(tryHandleError(ErrorCode.SERVER_ERROR, ex, rawContext)) {
                throw new ServletException("burst web: DispatcherServlet, in build request, build context, execute, unhandled error", ex);
            }
        }
        if(response == null) {
            throw new ServletException("burst web: DispatcherServlet, response gets null");
        }
        if(response.getErrorCode() != null) {
            if(tryHandleError(response.getErrorCode(), null, rawContext)) {
                return;
            }
        }
        try {
            response.response(rawContext);
        } catch (Throwable ex) {
            if(tryHandleError(ErrorCode.SERVER_ERROR, ex, rawContext)) {
                throw new ServletException("burst web: DispatcherServlet, in response, unhandled error", ex);
            }
        }
    }
}
