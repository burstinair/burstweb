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

    <REQUEST extends IRequest> REQUEST build(Class<REQUEST> requestClass, HttpServletRequest rawRequest, HttpServletResponse rawResponse, HttpMethod method) throws Throwable;
}
