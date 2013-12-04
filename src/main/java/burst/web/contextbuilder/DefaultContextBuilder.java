package burst.web.contextbuilder;

import burst.web.IContext;
import burst.web.context.DefaultContext;
import burst.web.enums.HttpMethod;
import org.springframework.beans.factory.FactoryBean;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Burst
 *         13-12-4 上午12:32
 */
public class DefaultContextBuilder implements IContextBuilder {

    public static final DefaultContextBuilder INSTANCE = new DefaultContextBuilder();

    private DefaultContextBuilder() { }

    @Override
    public IContext build(HttpServletRequest request, HttpServletResponse response, HttpMethod httpMethod) {
        Map<String, String> innerContext = new HashMap<String, String>();
        Enumeration<String> parameterNames = request.getParameterNames();
        while(parameterNames.hasMoreElements()) {
            String parameterName = parameterNames.nextElement();
            innerContext.put(parameterName, request.getParameter(parameterName));
        }
        return new DefaultContext(innerContext);
    }
}
