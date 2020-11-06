package com.github.disino125;

import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.handler.AbstractHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Serializable;
import java.util.Date;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

public class HelloHandler extends AbstractHandler {
    final String greeting;
    final String body;

    public HelloHandler() {
        this("Hello World");
    }

    public HelloHandler(String greeting) {
        this(greeting, null);
    }

    public HelloHandler(String greeting, String body) {
        this.greeting = greeting;
        this.body = body;
    }

    @Override
    public void handle(String s,
                       Request baseRequest,
                       HttpServletRequest request,
                       HttpServletResponse response) throws IOException,
            ServletException {
        response.setContentType("text/html; charset=utf-8");
        response.setStatus(HttpServletResponse.SC_OK);


        String name = request.getParameter("name");
        PrintWriter out = response.getWriter();


        Operate operate1 = () -> out.println(name);
        Operate operate2 = () -> out.println("IMPRINT");
        Operate operate3 = () -> out.println(new Date());
        Operate operate4 = () -> {
            try {
                response.sendError(404);
            } catch (IOException e) {
                e.printStackTrace();
            }
        };

        Map<String, Operate> map = Map.of("/greeting", operate1, "/imprint", operate2, "/healthcheck", operate3);
        Optional.ofNullable(map.get(s)).orElse(operate4).handle();

        if (body != null) {
            out.println(body);
        }

        baseRequest.setHandled(true);
    }
}

