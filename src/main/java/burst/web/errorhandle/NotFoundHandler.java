package burst.web.errorhandle;

import burst.web.framework.RawContext;

import javax.servlet.http.HttpServletResponse;

/**
 * @author Burst
 *         13-12-4 上午12:13
 */
public class NotFoundHandler implements IErrorHandler {

    public static final NotFoundHandler INSTANCE = new NotFoundHandler();

    private NotFoundHandler() { }

    @Override
    public void handle(Object errorContext, RawContext rawContext) {
        rawContext.getResponse().setStatus(HttpServletResponse.SC_NOT_FOUND);
    }
}
