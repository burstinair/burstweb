package burst.web;

import java.util.Set;

/**
 * @author Burst
 *         13-12-4 上午12:07
 */
public interface IContext {

    String getParameter(String key);

    Set<String> getParameterKeySet();
}
