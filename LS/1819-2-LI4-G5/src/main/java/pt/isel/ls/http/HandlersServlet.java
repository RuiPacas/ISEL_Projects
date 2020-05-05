package pt.isel.ls.http;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.nio.charset.Charset;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import pt.isel.ls.handler.CommandHandler;
import pt.isel.ls.request.Headers;
import pt.isel.ls.request.Method;
import pt.isel.ls.request.Parameters;
import pt.isel.ls.request.Path;
import pt.isel.ls.request.Request;
import pt.isel.ls.result.Result;
import pt.isel.ls.router.Router;
import pt.isel.ls.router.Router.RouterResult;
import pt.isel.ls.utils.MapMerge;

public class HandlersServlet extends HttpServlet {

    private Router router;
    private ByteArrayOutputStream byteArrayOutputStream;
    private PrintStream ps;
    private byte[] respBodyBytes;
    private OutputStream outputStream;

    public HandlersServlet(Router router) {
        this.router = router;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        Path path = new Path(req.getRequestURI());
        Method method = Method.valueOf(req.getMethod());
        Headers accept = new Headers("Accept:" + req.getHeader("Accept").split(",")[0]);
        Request r = new Request(method, path, null, accept);
        executeRequest(resp, r);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        byte[] bytes = new byte[req.getContentLength()];
        req.getInputStream().read(bytes);
        Method method = Method.valueOf(req.getMethod());
        Path path = new Path(req.getRequestURI());
        Parameters p = new Parameters(new String(bytes));
        Request r = new Request(method, path, p, null);
        executeRequest(resp, r);
    }

    private void executeRequest(HttpServletResponse resp, Request r) throws IOException {
        try {
            RouterResult handler = router.getHandler(r);
            CommandHandler commandHandler = handler.getCommandHandler();
            Result result = commandHandler
                    .executeCommand(MapMerge.getMergedMap(r, handler));

            byteArrayOutputStream = new ByteArrayOutputStream();
            ps = new PrintStream(byteArrayOutputStream);

            if (r.getMethod() == Method.GET) {
                Headers accept = r.getHeaders();
                if (accept.getHeadersMap().get("Accept").equals("html")) {
                    result.showHtml(ps);
                } else {
                    result.showPlain(ps);
                }

                Charset utf8 = Charset.forName("utf-8");
                resp.setContentType(String.format(accept + "; charset=%s", utf8.name()));
            } else {
                result.showHtml(ps);
                resp.setHeader("Location", resp.encodeRedirectURL(r.getPath().toString()));
            }
            resp.setStatus(result.getStatusCode().value());


        } catch (Exception e) {
            byteArrayOutputStream = new ByteArrayOutputStream();
            ps = new PrintStream(byteArrayOutputStream);
            ps.print(e.getMessage());
            resp.setStatus(404);

        } finally {
            respBodyBytes = byteArrayOutputStream.toByteArray();
            resp.setContentLength(respBodyBytes.length);
            outputStream = resp.getOutputStream();
            outputStream.write(respBodyBytes);
            outputStream.close();
        }
    }
}