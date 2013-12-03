package burst.web.errorhandle;

import burst.web.enums.HttpMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Burst
 *         13-12-4 上午12:12
 */
public interface IErrorHandler {

    void handle(Object errorContext, HttpServletRequest request, HttpServletResponse response, HttpMethod httpMethod);
}
