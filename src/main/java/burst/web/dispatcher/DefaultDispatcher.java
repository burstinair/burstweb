package burst.web.dispatcher;

import burst.web.IAction;
import burst.web.framework.RawContext;
import org.springframework.web.context.WebApplicationContext;

/**
 * @author Burst
 *         13-12-4 上午12:04
 */
public class DefaultDispatcher implements IDispatcher<WebApplicationContext> {

    public static final IDispatcher INSTANCE = new DefaultDispatcher();

    private DefaultDispatcher() { }

    @Override
    public IAction dispatch(WebApplicationContext context, RawContext rawContext) {
        String contextPath = rawContext.getRequest().getRequestURI()
                .substring(rawContext.getRequest().getContextPath().length());

        return context.getBean(contextPath, IAction.class);
    }
}
