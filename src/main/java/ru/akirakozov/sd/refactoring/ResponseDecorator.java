package ru.akirakozov.sd.refactoring;

import com.google.gson.JsonObject;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by creed on 26.11.16.
 */
public class ResponseDecorator {

    // 200
    public static HttpServletResponse responseJson(HttpServletResponse response, JsonObject object) {
        response.setContentType("application/json");
        response.setStatus(HttpServletResponse.SC_OK);
        try {
            response.getWriter().println(object.toString());
        } catch (IOException e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
        return response;
    }

    // 204
    public static HttpServletResponse responseNoContent(HttpServletResponse response) {
        response.setContentType("application/json");
        response.setStatus(HttpServletResponse.SC_NO_CONTENT);
        return response;
    }

    // 400
    public static HttpServletResponse responseBadRequest(HttpServletResponse response, String reason) {
        response.setContentType("application/json");
        response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        try {
            JsonObject error = new JsonObject();
            error.addProperty("error", reason);
            response.getWriter().println(error.toString());
        } catch (IOException e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
        return response;
    }

    // 500
    public static HttpServletResponse responseServerError(HttpServletResponse response, String error) {
        response.setContentType("application/json");
        response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        return response;
    }
}
