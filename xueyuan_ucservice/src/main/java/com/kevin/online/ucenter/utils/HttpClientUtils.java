package com.kevin.online.ucenter.utils;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.http.Consts;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.config.RequestConfig.Builder;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContextBuilder;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.conn.ssl.X509HostnameVerifier;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;

import java.net.SocketTimeoutException;
import java.util.*;

public class HttpClientUtils {

    public static final int connTimeout = 10000;
    public static final int readTimeout = 10000;
    public static final String charset = "UTF-8";
    public static HttpClient client = null;


    static {
        PoolingHttpClientConnectionManager cm = new PoolingHttpClientConnectionManager();
        cm.setMaxTotal(128);
        cm.setDefaultMaxPerRoute(128);
        client = HttpClients.custom().setConnectionManager(cm).build();
    }

    public static String postParameters(String url,String parameterStr) throws ConnectTimeoutException,Exception {
        return post(url,parameterStr,"application/x-www-form-urlencoded",charset,connTimeout,readTimeout);
    }

    public static String postParameters(String url,String parameterStr,String charset,Integer connTimeout,Integer readTimeout) throws ConnectTimeoutException,Exception {
        return post(url,parameterStr,"application/x-www-form-urlencoded",charset,connTimeout,readTimeout);
    }

    public static String postParameters(String url, Map<String,String> params) throws ConnectTimeoutException, SocketTimeoutException,Exception {
        return postForm(url,params,null,connTimeout,readTimeout);
    }

    public static String postParameters(String url,Map<String,String> params,Integer connTimeout,Integer readTimeout) throws ConnectTimeoutException, SocketTimeoutException,Exception {
        return postForm(url,params,null,connTimeout,readTimeout);
    }

    public static String get(String url) throws Exception {
        return get(url,charset,null,null);
    }
    public static String get(String url,String charset) throws Exception {
        return get(url,charset,connTimeout,readTimeout);
    }

    /**
     * 发送一个post请求，使用指定的字符编码
     */
    public static String post(String url, String body, String mimeType, String charset, Integer connTimeout, Integer readTimeout) throws
            ConnectTimeoutException,SocketTimeoutException,Exception {
        HttpClient client = null;
        HttpPost post = new HttpPost(url);
        String result = "";
        try {
            if (StringUtils.isNotBlank(body)) {
                HttpEntity entity = new StringEntity(body, ContentType.create(mimeType,charset));
                post.setEntity(entity);
                //设置参数
                Builder customReqConf = RequestConfig.custom();
                if (connTimeout != null) {
                    customReqConf.setConnectTimeout(connTimeout);
                }
                if (readTimeout != null) {
                    customReqConf.setSocketTimeout(readTimeout);
                }
                post.setConfig(customReqConf.build());
                HttpResponse res;
                if (url.startsWith("https")) {
                    //执行HTTPS
                    client = createSSLInsecureClient();
                    res = client.execute(post);
                }else {
                    //执行http请求
                    client = HttpClientUtils.client;
                    res = client.execute(post);
                }
                result = IOUtils.toString(res.getEntity().getContent(),charset);
            }
        } finally {
            post.releaseConnection();
            if (url.startsWith("https") && client != null && client instanceof CloseableHttpClient) {
                ((CloseableHttpClient) client).close();
            }
        }
        return result;

    }


    public static String postForm(String url,Map<String,String> params,Map<String,String> headers,
                                  Integer connTimeout,Integer readTimeout) throws SocketTimeoutException,Exception {
        HttpClient client = null;
        HttpPost post = new HttpPost(url);
        try {
            if (params != null && !params.isEmpty()) {
                List<NameValuePair> formParams = new ArrayList<>();
                Set<Map.Entry<String,String>> entrySet = params.entrySet();
                for (Map.Entry<String,String> entry : entrySet) {
                    formParams.add(new BasicNameValuePair(entry.getKey(),entry.getValue()));
                }
                UrlEncodedFormEntity entity = new UrlEncodedFormEntity(formParams, Consts.UTF_8);
                post.setEntity(entity);
            }
            if (headers != null && !headers.isEmpty()) {
                for (Map.Entry<String,String> entry : headers.entrySet()) {
                    post.addHeader(entry.getKey(),entry.getValue());
                }
            }
            //设置参数
            Builder customReqConf = RequestConfig.custom();
            if (connTimeout != null) {
                customReqConf.setConnectTimeout(connTimeout);
            }
            if (readTimeout != null) {
                customReqConf.setSocketTimeout(readTimeout);
            }
            post.setConfig(customReqConf.build());
            HttpResponse res = null;
            if (url.startsWith("https")) {
                client = createSSLInsecureClient();
                res = client.execute(post);
            }else {
                client = HttpClientUtils.client;
                res = client.execute(post);
            }
            return IOUtils.toString(res.getEntity().getContent(),"UTF-8");
        }finally {
            post.releaseConnection();
            if (url.startsWith("https") && client != null && client instanceof CloseableHttpClient) {
                ((CloseableHttpClient) client).close();
            }
        }
    }

    public static String get(String url,String charset,Integer connTimeout,Integer readTimeout) throws
            ConnectTimeoutException,SocketTimeoutException,Exception {
        HttpClient client = null;
        HttpGet get = new HttpGet(url);
        String result = "";
        try {
            //设置参数
            Builder customReqConf = RequestConfig.custom();
            if (connTimeout != null) {
                customReqConf.setConnectTimeout(connTimeout);
            }
            if (readTimeout != null) {
                customReqConf.setSocketTimeout(readTimeout);
            }
            get.setConfig(customReqConf.build());
            HttpResponse res = null;
            if (url.startsWith("https")) {
                client = createSSLInsecureClient();
                res = client.execute(get);
            }else {
                client = HttpClientUtils.client;
                res = client.execute(get);
            }
            result = IOUtils.toString(res.getEntity().getContent(),charset);
        }finally {
            get.releaseConnection();
            if (url.startsWith("https") && client != null && client instanceof CloseableHttpClient) {
                ((CloseableHttpClient) client).close();
            }
        }
        return result;
    }

    private static HttpClient createSSLInsecureClient() {
        return null;
    }


}
