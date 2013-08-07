package com.demo.web.constants;

public class MessageConstants {
    public final static class Error {
        public final static String COMMON_SERVER_EXCEPTION   = "err.common.serverException";

        public final static String COMMON_SESSION_EXPIRED    = "err.common.sessionExpired";

        public final static String LOGIN_USER_EMPTY          = "err.login.user.empty";

        public final static String LOGIN_PASSWORD_LENGTH     = "err.login.password.length";

        public final static String RANDOM_SELECT_STOCK_TRACE = "err.stock.trace.random.fail";

        public final static String LOGIN_PASSWORD            = "err.login.password";
    }

    public final static class Message {
        public final static String COMMON_SERVER_BUSY    = "msg.common.serverBusy";

        public final static String COMMON_SUBMIT_SUCCESS = "msg.common.submitSuccess";
    }

}
