package burst.web;

import burst.web.enums.HttpMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Burst
 *         13-12-4 上午12:00
 */
public interface IResponse {

    void response(HttpServletRequest request, HttpServletResponse response, HttpMethod httpMethod);
}
