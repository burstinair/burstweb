package burst.web.dispatcher;

import burst.web.BurstWebCore;
import burst.web.IAction;
import burst.web.enums.HttpMethod;
import org.springframework.context.ApplicationContext;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Burst
 *         13-12-4 上午12:04
 */
public class DefaultDispatcher implements IDispatcher {

    public static final IDispatcher INSTANCE = new DefaultDispatcher();

    private DefaultDispatcher() { }

    @Override
    public IAction dispatch(HttpServletRequest request, HttpServletResponse response, HttpMethod httpMethod) {
        ApplicationContext context = BurstWebCore.INSTANCE.getApplicationContext();
        String contextPath = request.getRequestURI().substring(request.getContextPath().length());
        return context.getBean(contextPath, IAction.class);
    }
}
