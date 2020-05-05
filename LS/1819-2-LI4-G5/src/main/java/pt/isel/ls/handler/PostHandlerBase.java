package pt.isel.ls.handler;

import pt.isel.ls.request.Method;

public abstract class PostHandlerBase implements CommandHandler {

    @Override
    public Method getMethod() {
        return Method.POST;
    }


}
