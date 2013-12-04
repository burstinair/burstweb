package burst.web.dispatcher;

import burst.web.IAction;
import burst.web.framework.RawContext;

/**
 * @author Burst
 *         13-12-3 下午11:59
 */
public interface IDispatcher<CONTEXT> {

    IAction dispatch(CONTEXT actionContext, RawContext rawContext);
}
