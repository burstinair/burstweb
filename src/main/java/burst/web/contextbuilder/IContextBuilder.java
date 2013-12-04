package burst.web.contextbuilder;

import burst.web.IContext;
import burst.web.enums.HttpMethod;
import org.springframework.beans.factory.FactoryBean;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Burst
 *         13-12-4 上午12:31
 */
public interface IContextBuilder {

    IContext build(HttpServletRequest request, HttpServletResponse response, HttpMethod httpMethod);
}
