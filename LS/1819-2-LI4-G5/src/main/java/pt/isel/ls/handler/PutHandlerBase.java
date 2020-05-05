package pt.isel.ls.handler;

import pt.isel.ls.request.Method;

public abstract class PutHandlerBase implements CommandHandler {

    @Override
    public Method getMethod() {
        return Method.PUT;
    }
}
