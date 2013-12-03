package burst.web.context;

import burst.web.IContext;

import java.util.Map;
import java.util.Set;

/**
 * @author Burst
 *         13-12-4 上午12:57
 */
public class DefaultContext implements IContext {

    private Map<String, String> rawParameters;

    public String getParameter(String key) {
        return rawParameters.get(key);
    }

    public Set<String> getParameterKeySet() {
        return rawParameters.keySet();
    }
}
