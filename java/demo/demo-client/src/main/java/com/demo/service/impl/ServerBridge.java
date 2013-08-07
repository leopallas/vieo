package com.demo.service.impl;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.net.MalformedURLException;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import com.demo.util.security.AuthenticationKey;
import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.conn.HttpHostConnectException;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SocketFactory;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.CoreProtocolPNames;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

import com.demo.constants.ClientErrorConstants;
import com.demo.constants.MimeTypeConstants;
import com.demo.constants.ServerErrorConstants;
import com.demo.constants.URIConstants;
import com.demo.exception.BadRequestException;
import com.demo.exception.LoginInvalidException;
import com.demo.service.ServerContext;
import com.demo.util.HashHelper;
import com.demo.util.security.AESEncryptHelper;
import com.demo.util.security.SignatureHelper;
import com.demo.vo.LoginResultVO;
import com.demo.vo.LoginVO;
import com.demo.vo.RegisterVO;

@SuppressWarnings("deprecation")
public class ServerBridge {
    protected String serverAddress;

    protected int serverPort;

    protected int serverSecretPort;

    protected boolean https;

    protected ServerContext context;

    protected ObjectMapper objectMapper = new ObjectMapper();

    private SecureRandom random;

    private SSLSocketFactory ssf;

    private HttpParams httpParams;

    public static final boolean PRINT_LOG = false;

    public static final String METHOD_GET = "GET";

    public static final String METHOD_POST = "POST";

    protected static final String SCHEME_HTTP = "http";

    protected static final String SCHEME_HTTPS = "https";

    protected static final String ENCODING = "UTF-8";

    protected static final int CONNECTION_TIMEOUT = 30000;

    protected static final int SOCKET_TIMEOUT     = 35000;

    public static final String CLIENT_GENERIC_ERROR = ClientErrorConstants
            .getDescription(ClientErrorConstants.CLIENT_GENERIC_ERROR);

    public ServerBridge(String serverAddress, int serverPort, int secretServerPort, boolean https) {
        AuthenticationKey key = AuthenticationKey.generateAuthenticationKey();
        this.context = new ServerContext(key.getSignatureKey(), key.getSecretToken());

        this.serverAddress = serverAddress;
        this.serverPort = serverPort;
        this.serverSecretPort = secretServerPort;
        this.https = https;
        this.random = new SecureRandom();
        this.random.setSeed(System.currentTimeMillis());
        this.httpParams = new BasicHttpParams();
        HttpConnectionParams.setConnectionTimeout(httpParams, CONNECTION_TIMEOUT);
        HttpConnectionParams.setSoTimeout(httpParams, SOCKET_TIMEOUT);

        if (this.https) {
            try {
                final SSLContext ctx = SSLContext.getInstance("TLS");
                X509TrustManager tm = new X509TrustManager() {

                    public void checkClientTrusted(X509Certificate[] xcs, String string) throws CertificateException {
                    }

                    public void checkServerTrusted(X509Certificate[] xcs, String string) throws CertificateException {
                    }

                    public X509Certificate[] getAcceptedIssuers() {
                        return null;
                    }
                };

                ctx.init(null, new TrustManager[]{tm}, null);
                try {
                    Constructor<SSLSocketFactory> constructor = SSLSocketFactory.class.getConstructor(SSLContext.class);
                    ssf = constructor.newInstance(ctx);
                } catch (NoSuchMethodException e) {
                    e.printStackTrace();
                } catch (SecurityException e) {
                    e.printStackTrace();
                } catch (InstantiationException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (IllegalArgumentException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }

                if (ssf == null) {
                    try {
                        Class.forName("android.os.Build");
                        KeyStore trustStore = KeyStore.getInstance(KeyStore.getDefaultType());
                        ssf = new SSLSocketFactory(trustStore) {
                            @Override
                            public Socket createSocket(Socket socket, String host, int port, boolean autoClose)
                                    throws IOException {
                                return ctx.getSocketFactory().createSocket(socket, host, port, autoClose);
                            }

                            @Override
                            public Socket createSocket() throws IOException {
                                return ctx.getSocketFactory().createSocket();
                            }
                        };
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    } catch (KeyManagementException e) {
                        e.printStackTrace();
                    } catch (UnrecoverableKeyException e) {
                        e.printStackTrace();
                    } catch (KeyStoreException e) {
                        e.printStackTrace();
                    }
                }
                if (ssf == null) {
                    throw new IllegalArgumentException("can not create ssl factory");
                } else {
                    ssf.setHostnameVerifier(SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
                }
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            } catch (KeyManagementException e) {
                e.printStackTrace();
            }
        }
    }

    private byte[] input2byte(InputStream is) throws IOException {
        ByteArrayOutputStream byteStream = new ByteArrayOutputStream();

        // byte[] buff = new byte[1024];
        // int rc = 0;
        // while ((rc = is.read(buff, 0, buff.length)) > 0) {
        // byteStream.write(buff, 0, rc);
        // }

        int ch;
        while ((ch = is.read()) != -1) {
            byteStream.write(ch);
        }

        byte[] in2b = byteStream.toByteArray();
        byteStream.close();
        return in2b;
    }

    public byte[] doGetResource(URL url) throws BadRequestException {
        HttpClient client = new DefaultHttpClient();
        System.out.println(url);
        try {
            client.getParams().setParameter(CoreProtocolPNames.PROTOCOL_VERSION, HttpVersion.HTTP_1_1);
            HttpGet get = new HttpGet(url.toString());
            HttpResponse response = client.execute(get);
            int status = response.getStatusLine().getStatusCode();
            if (status == 404) {
                throw new BadRequestException(status,
                        ClientErrorConstants.getDescription(ClientErrorConstants.REQUEST_RESOURCE_NOT_EXISTED));
            }
            if (status != 200 && status != 503) {
                throw new BadRequestException(status, EntityUtils.toString(response.getEntity()));
            }
            return input2byte(response.getEntity().getContent());
        } catch (HttpHostConnectException e) {
            e.printStackTrace();
            throw new BadRequestException(
                    ClientErrorConstants.getDescription(ClientErrorConstants.HOST_CONNECTION_ERROR), e, PRINT_LOG);
        } catch (SocketTimeoutException e) {
            e.printStackTrace();
            throw new BadRequestException(
                    ClientErrorConstants.getDescription(ClientErrorConstants.SOCKET_TIMEOUT_ERROR), e, PRINT_LOG);
        } catch (ConnectTimeoutException e) {
            e.printStackTrace();
            throw new BadRequestException(
                    ClientErrorConstants.getDescription(ClientErrorConstants.CONNECT_TIMEOUT_ERROR), e, PRINT_LOG);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            throw new BadRequestException(CLIENT_GENERIC_ERROR, e, PRINT_LOG);
        } catch (ParseException e) {
            e.printStackTrace();
            throw new BadRequestException(CLIENT_GENERIC_ERROR, e, PRINT_LOG);
        } catch (IOException e) {
            e.printStackTrace();
            throw new BadRequestException(CLIENT_GENERIC_ERROR, e, PRINT_LOG);
        } finally {
            client.getConnectionManager().shutdown();
        }
    }

    public String doGet(URL url) throws BadRequestException {
        HttpClient client = new DefaultHttpClient(this.httpParams);
        if (this.https) {
            addHttpsSchema(url, client);
        }
        System.out.println(url);
        try {
            client.getParams().setParameter(CoreProtocolPNames.PROTOCOL_VERSION, HttpVersion.HTTP_1_1);
            HttpGet get = new HttpGet(url.toString());
            // long startTime = System.currentTimeMillis();
            HttpResponse response = client.execute(get);
            int status = response.getStatusLine().getStatusCode();
            // System.out.println("total spent time :::::: " +
            // (System.currentTimeMillis() - startTime));
            return checkResponse(response, status);
        } catch (HttpHostConnectException e) {
            throw new BadRequestException(
                    ClientErrorConstants.getDescription(ClientErrorConstants.HOST_CONNECTION_ERROR), e, PRINT_LOG);
        } catch (SocketTimeoutException e) {
            throw new BadRequestException(
                    ClientErrorConstants.getDescription(ClientErrorConstants.SOCKET_TIMEOUT_ERROR), e, PRINT_LOG);
        } catch (ConnectTimeoutException e) {
            throw new BadRequestException(
                    ClientErrorConstants.getDescription(ClientErrorConstants.CONNECT_TIMEOUT_ERROR), e, PRINT_LOG);
        } catch (UnsupportedEncodingException e) {
            throw new BadRequestException(CLIENT_GENERIC_ERROR, e, PRINT_LOG);
        } catch (ParseException e) {
            throw new BadRequestException(CLIENT_GENERIC_ERROR, e, PRINT_LOG);
        } catch (IOException e) {
            throw new BadRequestException(CLIENT_GENERIC_ERROR, e, PRINT_LOG);
        } finally {
            client.getConnectionManager().shutdown();
        }
    }

    public String doPost(URL url) throws BadRequestException {
        return this.doPost(url, null, false);
    }

    public String doPost(URL url, String postEntity, boolean json) throws BadRequestException {
        HttpClient client = new DefaultHttpClient(this.httpParams);
        if (this.https) {
            addHttpsSchema(url, client);
        }
        System.out.println(url);
        try {
            client.getParams().setParameter(CoreProtocolPNames.PROTOCOL_VERSION, HttpVersion.HTTP_1_1);
            HttpPost post = new HttpPost(url.toString());

            if (!StringUtils.isEmpty(postEntity)) {
                // postEntity = "json="+postEntity;
                StringEntity entity = new StringEntity(postEntity, ENCODING);
                entity.setContentType(json ? MimeTypeConstants.APPLICATION_JSON : MimeTypeConstants.TXT_PLAIN);
                entity.setContentEncoding(ENCODING);
                post.setEntity(entity);

            }
            long startTime = System.currentTimeMillis();
            HttpResponse response = client.execute(post);
            int status = response.getStatusLine().getStatusCode();
            // System.out.println("total spent time :::::: " +
            // (System.currentTimeMillis() - startTime));
            return checkResponse(response, status);
        } catch (HttpHostConnectException e) {
            e.printStackTrace();
            throw new BadRequestException(
                    ClientErrorConstants.getDescription(ClientErrorConstants.HOST_CONNECTION_ERROR), e, PRINT_LOG);
        } catch (SocketTimeoutException e) {
            e.printStackTrace();
            throw new BadRequestException(
                    ClientErrorConstants.getDescription(ClientErrorConstants.SOCKET_TIMEOUT_ERROR), e, PRINT_LOG);
        } catch (ConnectTimeoutException e) {
            e.printStackTrace();
            throw new BadRequestException(
                    ClientErrorConstants.getDescription(ClientErrorConstants.CONNECT_TIMEOUT_ERROR), e, PRINT_LOG);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            throw new BadRequestException(CLIENT_GENERIC_ERROR, e, PRINT_LOG);
        } catch (ParseException e) {
            e.printStackTrace();
            throw new BadRequestException(CLIENT_GENERIC_ERROR, e, PRINT_LOG);
        } catch (IOException e) {
            e.printStackTrace();
            throw new BadRequestException(CLIENT_GENERIC_ERROR, e, PRINT_LOG);
        } finally {
            client.getConnectionManager().shutdown();
        }
    }

    public String doPostMultiForm(URL url, String parms, File file, String mimeType) throws BadRequestException,
            IOException {
        HttpClient client = new DefaultHttpClient(this.httpParams);
        if (this.https) {
            addHttpsSchema(url, client);
        }
        System.out.println(url);
        try {
            client.getParams().setParameter(CoreProtocolPNames.PROTOCOL_VERSION, HttpVersion.HTTP_1_1);
            HttpPost post = new HttpPost(url.toURI());
            MultipartEntity entity = new MultipartEntity();
            if (file != null) {
                FileBody fileBody;
                if (mimeType != null) {
                    fileBody = new FileBody(file, mimeType);
                } else {
                    fileBody = new FileBody(file);
                }
                entity.addPart("file", fileBody);
            }
            entity.addPart("json-data",
                    new StringBody(parms, MimeTypeConstants.APPLICATION_JSON, Charset.forName(ENCODING)));

            post.setEntity(entity);
            HttpResponse response = client.execute(post);
            int status = response.getStatusLine().getStatusCode();
            return checkResponse(response, status);
        } catch (HttpHostConnectException e) {
            e.printStackTrace();
            throw new BadRequestException(
                    ClientErrorConstants.getDescription(ClientErrorConstants.HOST_CONNECTION_ERROR), e, PRINT_LOG);
        } catch (SocketTimeoutException e) {
            e.printStackTrace();
            throw new BadRequestException(
                    ClientErrorConstants.getDescription(ClientErrorConstants.SOCKET_TIMEOUT_ERROR), e, PRINT_LOG);
        } catch (ConnectTimeoutException e) {
            e.printStackTrace();
            throw new BadRequestException(
                    ClientErrorConstants.getDescription(ClientErrorConstants.CONNECT_TIMEOUT_ERROR), e, PRINT_LOG);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            throw new BadRequestException(CLIENT_GENERIC_ERROR, e, PRINT_LOG);
        } catch (ParseException e) {
            e.printStackTrace();
            throw new BadRequestException(CLIENT_GENERIC_ERROR, e, PRINT_LOG);
        } catch (URISyntaxException e) {
            e.printStackTrace();
            throw new BadRequestException(CLIENT_GENERIC_ERROR, e, PRINT_LOG);
        } catch (ClientProtocolException e) {
            e.printStackTrace();
            throw new BadRequestException(CLIENT_GENERIC_ERROR, e, PRINT_LOG);
        } catch (IOException e) {
            e.printStackTrace();
            throw new BadRequestException(CLIENT_GENERIC_ERROR, e, PRINT_LOG);
        } finally {
            client.getConnectionManager().shutdown();
        }
    }

    protected String checkResponse(HttpResponse response, int status) throws IOException, BadRequestException {
        String result = EntityUtils.toString(response.getEntity());
        if (response.getHeaders(ServerErrorConstants.HEADER_ERROR_CODE).length > 0) {
            int errorCode = Integer.parseInt(response.getHeaders(ServerErrorConstants.HEADER_ERROR_CODE)[0].getValue());
            System.out.println("-----result-errorCode:" + errorCode);
            if (errorCode == ServerErrorConstants.INVALID_URL || errorCode == ServerErrorConstants.INVALID_DEVICE) {
                throw new LoginInvalidException(errorCode, ServerErrorConstants.getDescription(errorCode));
            } else {
                String errorDescription = ServerErrorConstants.getDescription(errorCode);
                throw new BadRequestException(errorCode, errorDescription);
            }
        }
        if (status == 404) {
            throw new BadRequestException(status,
                    ServerErrorConstants.getDescription(ServerErrorConstants.REQUEST_RESOURCE_NOT_EXISTED));
        }
        if (status != 200 && status != 503) {
            System.out.println("-----result:" + result);
            String serverErrorDesc = ServerErrorConstants.getDescription(ServerErrorConstants.SERVER_GENERIC_ERROR);
            throw new BadRequestException(status, serverErrorDesc);
        }

        return result;
    }

    private void addHttpsSchema(URL url, HttpClient client) {
        Scheme scheme = null;
        try {
            Class<?> cls = Class.forName("org.apache.http.conn.scheme.SchemeSocketFactory");
            if (cls != null) {
                Constructor<Scheme> constructor = Scheme.class.getConstructor(String.class, int.class, cls);
                scheme = constructor.newInstance(SCHEME_HTTPS, url.getPort(), ssf);
            }
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (SecurityException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        if (scheme == null) {
            try {
                Class.forName("android.os.Build");
                Constructor<Scheme> constructor = Scheme.class.getConstructor(String.class, SocketFactory.class,
                        int.class);
                scheme = constructor.newInstance(SCHEME_HTTPS, ssf, url.getPort());
                HttpConnectionParams.setConnectionTimeout(client.getParams(), CONNECTION_TIMEOUT);
                HttpConnectionParams.setSoTimeout(client.getParams(), SOCKET_TIMEOUT);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            }
        }
        if (scheme == null) {
            throw new IllegalArgumentException("can not create scheme");
        }

        client.getConnectionManager().getSchemeRegistry().register(scheme);
    }

    public URL generateAuthenticationRequestURL(String basePath, String[][] params, String method) throws IOException {
        return generateURL(this.serverAddress, https ? this.serverSecretPort : this.serverPort, basePath, params,
                method, this.https, true, this.context.getSecretToken(), this.context.getSignatureKey());
    }

    public URL generateAuthenticationRequestURL(String basePath, String[][] params, String method, String secretToken,
            String signatureKey) throws IOException {
        return generateURL(this.serverAddress, https ? this.serverSecretPort : this.serverPort, basePath, params,
                method, this.https, true, secretToken, signatureKey);
    }

    public URL generateURL(String basePath, String[][] params, String method) throws IOException {
        return generateURL(this.serverAddress, https ? this.serverSecretPort : this.serverPort, basePath, params,
                method, this.https, false, null, null);
    }

    // private URL generateURL(String address, int port, String basePath,
    // String[][] params, String method, boolean https)
    // throws IOException {
    // return generateURL(address, port, basePath, params, method, https, true,
    // this.context.getSecretToken(),
    // this.context.getSignatureKey());
    // }

    private URL generateURL(String address, int port, String basePath, String[][] params, String method, boolean https,
            boolean isAuth, String secretToken, String signatureKey) throws IOException {
        StringBuilder sb = new StringBuilder(basePath);
        sb.append('?');
        for (String[] param : params) {
            if (param[1] != null) {
                sb.append(param[0]);
                sb.append('=');
                sb.append(URLEncoder.encode(param[1], ENCODING));
                sb.append('&');
            }
        }
        sb.append("tm=");
        sb.append(System.currentTimeMillis());
        sb.append("&nonce=");
        sb.append(this.random.nextInt());
        String path = sb.toString();
        URL url = new URL(https ? SCHEME_HTTPS : SCHEME_HTTP, address, port, path);
        if (isAuth) {
            String signature = SignatureHelper.generateSignature(url, method, signatureKey);
            sb.append("&au=");
            sb.append(URLEncoder.encode(signature, ENCODING));
            sb.append("&tkn=");
            sb.append(URLEncoder.encode(secretToken, ENCODING));
            url = new URL(https ? SCHEME_HTTPS : SCHEME_HTTP, address, port, sb.toString());
        }
        return url;
    }

    public String encryptPassword(String password, String key) {
        String encryptPwd = HashHelper.getSHA1(HashHelper.getSHA1(password) + key);
        return AESEncryptHelper.encrypt(encryptPwd, key);
    }

    public void setContext(ServerContext context) {
        this.context = context;
    }

    public ServerContext getContext() {
        return this.context;
    }

    protected LoginResultVO login(LoginVO loginVO) throws BadRequestException {
        URL url;
        try {
            url = new URL(this.https ? SCHEME_HTTPS : SCHEME_HTTP, this.serverAddress,
                    this.https ? this.serverSecretPort : this.serverPort, URIConstants.PDA_URI_LOGIN);
        } catch (MalformedURLException e) {
            throw new BadRequestException(CLIENT_GENERIC_ERROR, e, PRINT_LOG);
        }

        // String password = encryptPassword(loginVO.getPwd(),
        // loginVO.getSerialNo());
        // loginVO.setPwd(password);
        LoginResultVO resultVO;
        try {
            String entity = this.objectMapper.writeValueAsString(loginVO);
            String result = doPost(url, entity, true);
            resultVO = this.objectMapper.readValue(result, LoginResultVO.class);
            if (StringUtils.isEmpty(result)) {
                return new LoginResultVO();
            }
            this.context = new ServerContext(resultVO.getSignatureKey(), resultVO.getSecretToken());
        } catch (JsonGenerationException e) {
            throw new BadRequestException(CLIENT_GENERIC_ERROR, e, PRINT_LOG);
        } catch (JsonMappingException e) {
            throw new BadRequestException(CLIENT_GENERIC_ERROR, e, PRINT_LOG);
        } catch (IOException e) {
            throw new BadRequestException(CLIENT_GENERIC_ERROR, e, PRINT_LOG);
        }
        return resultVO;
    }

    protected boolean isLogin() {
//        if (this.context == null) {
//            throw new LoginInvalidException(ServerErrorConstants.PDA_NOT_LOGIN,
//                    ServerErrorConstants.getDescription(ServerErrorConstants.PDA_NOT_LOGIN));
//        }

        if (this.context == null) {
            AuthenticationKey key = AuthenticationKey.generateAuthenticationKey();
            this.context = new ServerContext(key.getSignatureKey(), key.getSecretToken());
        }
        return true;
    }

    protected boolean register(RegisterVO registerVO) throws BadRequestException {
        URL url;
        try {
            url = new URL(this.https ? SCHEME_HTTPS : SCHEME_HTTP, this.serverAddress,
                    this.https ? this.serverSecretPort : this.serverPort, URIConstants.PDA_URI_REGISTER);
        } catch (MalformedURLException e) {
            throw new BadRequestException(CLIENT_GENERIC_ERROR, e, PRINT_LOG);
        }

        try {
            String entity = this.objectMapper.writeValueAsString(registerVO);
            doPost(url, entity, true);
        } catch (JsonGenerationException e) {
            throw new BadRequestException(CLIENT_GENERIC_ERROR, e, PRINT_LOG);
        } catch (JsonMappingException e) {
            throw new BadRequestException(CLIENT_GENERIC_ERROR, e, PRINT_LOG);
        } catch (IOException e) {
            throw new BadRequestException(CLIENT_GENERIC_ERROR, e, PRINT_LOG);
        }
        return true;
    }

    // protected void notifyLoginSuccess() throws BadRequestException {
    // if (!isLogin()) {
    // throw new
    // BadRequestException(ServerErrorConstants.getDescription(ServerErrorConstants.PDA_NOT_LOGIN));
    // }
    //
    // URL url;
    // try {
    // url =
    // generateAuthenticationRequestURL(URIConstants.PDA_URI_NOTIFY_LOGIN_SUCCESSFUL,
    // new String[][] {},
    // METHOD_GET);
    // } catch (MalformedURLException e) {
    // throw new BadRequestException(CLIENT_GENERIC_ERROR, e, PRINT_LOG);
    // } catch (IOException e) {
    // throw new BadRequestException(CLIENT_GENERIC_ERROR, e, PRINT_LOG);
    // }
    // doGet(url);
    // }

    protected boolean logout() throws BadRequestException {
        if (!isLogin()) {
            throw new BadRequestException(ServerErrorConstants.getDescription(ServerErrorConstants.PDA_NOT_LOGIN));
        }

        URL url;
        try {
            url = generateAuthenticationRequestURL(URIConstants.PDA_URI_LOGOUT, new String[][] {}, METHOD_GET);
        } catch (MalformedURLException e) {
            throw new BadRequestException(CLIENT_GENERIC_ERROR, e, PRINT_LOG);
        } catch (IOException e) {
            throw new BadRequestException(CLIENT_GENERIC_ERROR, e, PRINT_LOG);
        }
        doGet(url);
        this.context = null;
        return true;
    }
}
