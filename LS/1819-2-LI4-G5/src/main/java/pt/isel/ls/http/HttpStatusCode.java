package pt.isel.ls.http;

public enum HttpStatusCode {
    Ok(200),
    Created(201),
    SeeOther(303),
    BadRequest(400),
    NotFound(404),
    MethodNotAllowed(405),
    InternalServerError(500);

    private final int code;

    HttpStatusCode(int code) {
        this.code = code;
    }

    public int value() {
        return code;
    }
}
