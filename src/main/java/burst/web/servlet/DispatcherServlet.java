package burst.web.servlet;

import burst.web.IAction;
import burst.web.IContext;
import burst.web.IRequest;
import burst.web.IResponse;
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

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * 入口Servlet
 *
 * @author Burst
 *         13-12-3 下午11:48
 */
public class DispatcherServlet extends HttpServlet implements IDispatcherRegistry, IRequestBuilderRegistry, IContextBuilderRegistry, IErrorHandlerRegistry {

    public static DispatcherServlet INSTANCE;

    private DispatcherServlet() {
        INSTANCE = this;
    }

    private static IDispatcher DISPATCHER = DefaultDispatcher.INSTANCE;

    @Override
    public void setDispatcher(IDispatcher dispatcher) {
        DISPATCHER = dispatcher;
    }

    private static IRequestBuilder REQUEST_BUILDER = DefaultRequestBuilder.INSTANCE;

    @Override
    public void setRequestBuilder(IRequestBuilder requestBuilder) {
        REQUEST_BUILDER = requestBuilder;
    }

    private static IContextBuilder CONTEXT_BUILDER = DefaultContextBuilder.INSTANCE;

    @Override
    public void setContextBuilder(IContextBuilder contextBuilder) {
        CONTEXT_BUILDER = contextBuilder;
    }

    private static Map<String, IErrorHandler> ERROR_HANDLER_MAP;
    static {
        ERROR_HANDLER_MAP = new HashMap<String, IErrorHandler>();
        ERROR_HANDLER_MAP.put(ErrorCode.NOT_FOUND, NotFoundHandler.INSTANCE);
        ERROR_HANDLER_MAP.put(ErrorCode.SERVER_ERROR, ServerErrorHandler.INSTANCE);
    }

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
        IErrorHandler handler = ERROR_HANDLER_MAP.get(ErrorCode.NOT_FOUND);
        if(handler != null) {
            handler.handle(errorContext, rawRequest, rawResponse, httpMethod);
            return true;
        }
        return false;
    }

    private static final String MSG_UNHANDLED_ERROR = "burst web: DispatcherServlet, unhandled error";

    protected void handleRequest(HttpServletRequest rawRequest, HttpServletResponse rawResponse, HttpMethod httpMethod) throws ServletException{
        IAction action = DISPATCHER.dispatch(rawRequest, rawResponse, httpMethod);
        if(action == null) {
            tryHandleError(ErrorCode.NOT_FOUND, null, rawRequest, rawResponse, httpMethod);
            return;
        }

        try {
            IRequest request = REQUEST_BUILDER.build(rawRequest, rawResponse, httpMethod);
            IContext context = CONTEXT_BUILDER.build(rawRequest, rawResponse, httpMethod);
            IResponse response = action.execute(request, context);
            response.response(rawRequest, rawResponse, httpMethod);
        } catch (Throwable ex) {
            if(tryHandleError(ErrorCode.SERVER_ERROR, ex, rawRequest, rawResponse, httpMethod)) {
                throw new ServletException(MSG_UNHANDLED_ERROR, ex);
            }
        }
    }
}
