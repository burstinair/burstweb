package burst.web.errorhandle;

import burst.web.framework.RawContext;

import javax.servlet.http.HttpServletResponse;

/**
 * @author Burst
 *         13-12-4 上午12:30
 */
public class ServerErrorHandler implements IErrorHandler {

    public static final ServerErrorHandler INSTANCE = new ServerErrorHandler();

    private ServerErrorHandler() { }

    @Override
    public void handle(Object errorContext, RawContext rawContext) {
        rawContext.getResponse().setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
    }
}
