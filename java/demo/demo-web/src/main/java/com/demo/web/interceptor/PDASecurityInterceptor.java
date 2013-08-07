package com.demo.web.interceptor;

import com.demo.constants.ServerErrorConstants;
import com.demo.util.security.SignatureHelper;
import com.demo.web.controller.GenericController;
import com.demo.web.domain.User;
import com.demo.web.service.PDAService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class PDASecurityInterceptor extends GenericController implements HandlerInterceptor {
    private static final String TAG = PDASecurityInterceptor.class.getName();

    @Autowired
    private PDAService          PDAService;

    @Autowired
    private Long                urlTimeOffset;

    public PDASecurityInterceptor() {
        super(TAG);
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Logger.getLogger(TAG).debug("start android side interceptor");
        String secretToken = request.getParameter("tkn");
        String signature = request.getParameter("au");
        String offsetTimeStr = request.getParameter("tm");

        Logger.getLogger(TAG).warn("URL:" + request.getRequestURI());
        Logger.getLogger(TAG).debug("tkn:" + secretToken);
        Logger.getLogger(TAG).debug("au:" + signature);
        Logger.getLogger(TAG).debug("tm:" + offsetTimeStr);
        Logger.getLogger(TAG).debug("System.currentTimeMillis():" + System.currentTimeMillis());
        Logger.getLogger(TAG).debug("urlTimeOffset:" + urlTimeOffset);

        if (secretToken == null) {
            sendError(response, ServerErrorConstants.INVALID_REQUEST_PARAMETER, "tkn");
            return false;
        }
        if (signature == null) {
            sendError(response, ServerErrorConstants.INVALID_REQUEST_PARAMETER, "au");
            return false;
        }
        if (offsetTimeStr == null) {
            sendError(response, ServerErrorConstants.INVALID_REQUEST_PARAMETER, "tm");
            return false;
        }

        User user = PDAService.getUser(secretToken);
        if (user == null) {
            sendError(response, ServerErrorConstants.INVALID_URL);
            return false;
        }

        long offsetTime = Long.parseLong(offsetTimeStr);
        if (System.currentTimeMillis() - offsetTime >= urlTimeOffset) {
            sendError(response, ServerErrorConstants.INVALID_URL);
            return false;
        }

        if (!SignatureHelper.validateSignature(request, user.getSignatureKey())) {
            sendError(response, ServerErrorConstants.INVALID_SECRET_TOKEN);
            return false;
        }

        request.setAttribute("usrId", user.getUsrId());
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
            ModelAndView modelAndView) throws Exception {
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
            throws Exception {
        Logger.getLogger(TAG).debug("End android side interceptor");
    }
}
