package burst.web;

/**
 * @author Burst
 *         13-12-4 上午12:00
 */
public interface IAction<REQUEST extends IRequest> {

    IResponse execute(REQUEST request, IContext context) throws Throwable;
}
