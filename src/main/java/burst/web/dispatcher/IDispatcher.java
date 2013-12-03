package burst.web.dispatcher;

import burst.web.IAction;
import burst.web.enums.HttpMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Burst
 *         13-12-3 下午11:59
 */
public interface IDispatcher {

    IAction dispatch(HttpServletRequest request, HttpServletResponse response, HttpMethod httpMethod);
}
