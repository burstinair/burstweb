package burst.web.errorhandle;

import burst.web.enums.HttpMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Burst
 *         13-12-4 上午12:30
 */
public class ServerErrorHandler implements IErrorHandler {

    public static final ServerErrorHandler INSTANCE = new ServerErrorHandler();

    private ServerErrorHandler() { }

    @Override
    public void handle(Object errorContext, HttpServletRequest request, HttpServletResponse response, HttpMethod httpMethod) {
        response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
    }
}
