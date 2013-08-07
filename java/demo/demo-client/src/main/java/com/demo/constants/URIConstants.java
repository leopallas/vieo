package com.demo.constants;

public class URIConstants {
    public static String PPM_WEB_CONTEXT_ROOT          = "";
    public static String PPM_WEB_UPLOAD_CONTEXT_ROOT   = "/upload";
    public static String PDA_URI_REQUEST_USERS         = PPM_WEB_CONTEXT_ROOT + "/client/pda/get-users";
    public static String PDA_URI_REGISTER              = PPM_WEB_CONTEXT_ROOT + "/client/pda/register";
    public static String PDA_URI_LOGIN                 = PPM_WEB_CONTEXT_ROOT + "/client/pda/login";
    public static String PDA_URI_LOGOUT                = PPM_WEB_CONTEXT_ROOT + "/client/pda/sync/logout";
    public static String PDA_URI_HEARTBEAT             = PPM_WEB_CONTEXT_ROOT + "/client/pda/sync/heartbeat";
    public static String PDA_URI_UPLOAD_PICTURE        = PPM_WEB_CONTEXT_ROOT + "/client/pda/sync/upload-picture";
    public static String PDA_URI_UPLOAD_PICTURE_FLAG   = PPM_WEB_CONTEXT_ROOT + "/client/pda/sync/upload-picture-flag";
    public static String PDA_URI_REQUEST_SYNC_PICTURES = PPM_WEB_CONTEXT_ROOT + "/client/pda/sync/get-sync-pictures";
}
