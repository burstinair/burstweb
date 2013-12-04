package burst.web.contextbuilder;

import burst.web.IContext;
import burst.web.framework.RawContext;

/**
 * @author Burst
 *         13-12-4 上午12:31
 */
public interface IContextBuilder {

    IContext build(RawContext rawContext) throws Throwable;
}
