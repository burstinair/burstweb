package burst.web.requestbuilder;

import burst.web.IRequest;
import burst.web.enums.HttpMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Burst
 *         13-12-4 上午12:07
 */
public interface IRequestBuilder {

    IRequest build(HttpServletRequest rawRequest, HttpServletResponse rawResponse, HttpMethod method);
}
