package burst.web.dispatcher;

import burst.web.IAction;
import burst.web.enums.HttpMethod;
import org.springframework.web.context.WebApplicationContext;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Burst
 *         13-12-3 下午11:59
 */
public interface IDispatcher {

    IAction dispatch(WebApplicationContext context, HttpServletRequest request, HttpServletResponse response, HttpMethod httpMethod);
}
