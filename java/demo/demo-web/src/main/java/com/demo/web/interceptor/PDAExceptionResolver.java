package com.demo.web.interceptor;

import com.demo.constants.ServerErrorConstants;
import com.demo.web.controller.GenericController;
import com.demo.web.exception.PDAException;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.UrlPathHelper;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class PDAExceptionResolver extends GenericController implements HandlerExceptionResolver {
    private static final String TAG           = PDAExceptionResolver.class.getName();

    private String              defaultPathPattern;

    private PathMatcher         pathMatcher   = new AntPathMatcher();

    private UrlPathHelper       urlPathHelper = new UrlPathHelper();

    public PDAExceptionResolver() {
        super(TAG);
    }

    public boolean matches(String lookupPath, PathMatcher pathMatcher) {
        return defaultPathPattern == null || pathMatcher.match(defaultPathPattern, lookupPath);
    }

    public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler,
            Exception ex) {
        if (StringUtils.isEmpty(defaultPathPattern)) {
            Logger.getLogger(TAG).warn("Default Path Pattern is empty");
            return null;
        }

        urlPathHelper.setAlwaysUseFullPath(true);
        String lookupPath = urlPathHelper.getLookupPathForRequest(request);

        if (!matches(lookupPath, pathMatcher)) {
            return null;
        }

        if (ex instanceof MaxUploadSizeExceededException) {
            sendError(response, ServerErrorConstants.MAX_UPLOAD_FILE_SIZE_EXCEEDED);
        } else
            if (ex instanceof PDAException) {
                sendError(response, ((PDAException) ex).getErrorCode());
            } else {
                sendError(response, ex);
                ex.printStackTrace();
            }
        return new ModelAndView();
    }

    public void setDefaultPathPattern(String defaultPathPattern) {
        this.defaultPathPattern = defaultPathPattern;
    }
}
