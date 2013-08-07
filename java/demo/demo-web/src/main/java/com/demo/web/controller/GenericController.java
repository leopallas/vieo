package com.demo.web.controller;

import com.demo.constants.ServerErrorConstants;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletResponse;

public abstract class GenericController {
    private String TAG;

    protected GenericController(String TAG) {
        this.TAG = TAG;
    }

    protected void sendError(HttpServletResponse response, int errorCode, String... params) {
        sendServerError(response, true, errorCode, params);
    }

    protected void sendError(HttpServletResponse response, Throwable e) {
        sendServerError(response, true, ServerErrorConstants.SERVER_GENERIC_ERROR, e.toString());
    }

    protected void sendWarning(HttpServletResponse response, int errorCode, String... params) {
        sendServerError(response, false, errorCode, params);
    }

    private void sendServerError(HttpServletResponse response, boolean error, int errorCode, String... params) {
        response.setHeader(ServerErrorConstants.HEADER_ERROR_CODE, Integer.toString(errorCode));
        String description = ServerErrorConstants.getDescription(errorCode);
        for (int i = 0; i < params.length; i++) {
            description = description.replace("{" + i + "}", params[i]);
        }
        // response.setHeader(ServerErrorConstants.HEADER_ERROR_DESCRIPTION,
        // description);
        if (error) {
            Logger.getLogger(TAG).error(String.format("Error Code:%d Description:%s", errorCode, description));
        } else {
            Logger.getLogger(TAG).warn(String.format("Error Code:%d Description:%s", errorCode, description));
        }
    }
}
