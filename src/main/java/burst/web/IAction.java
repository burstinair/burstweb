package burst.web;

/**
 * @author Burst
 *         13-12-4 上午12:00
 */
public interface IAction {

    IResponse execute(IRequest request, IContext context) throws Throwable;
}
