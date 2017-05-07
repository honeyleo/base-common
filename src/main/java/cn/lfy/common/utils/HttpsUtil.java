package cn.lfy.common.utils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.logging.Logger;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.StatusLine;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.config.SocketConfig;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.ssl.SSLContexts;
import org.apache.http.util.EntityUtils;

public class HttpsUtil {

	private static final Logger log = Logger.getLogger(HttpsUtil.class.getName());
	
	private int maxTotalPool = 200;
	private int maxConPerRoute = 20;
	private int socketTimeout = 3000;
	private int connectionRequestTimeout = 3000;
	private int connectTimeout = 3000;
	
	private PoolingHttpClientConnectionManager poolConnManager;
	
	private final static HttpsUtil httpsUtil = new HttpsUtil();
	
	private HttpsUtil() {
		init();
	}
	public static HttpsUtil getInstance() {
		return httpsUtil;
	}
	public void init() {  
         try {  
            SSLContext sslcontext = SSLContexts.custom().loadTrustMaterial(null,  
                            new TrustSelfSignedStrategy())  
                    .build();  
            HostnameVerifier hostnameVerifier = SSLConnectionSocketFactory.getDefaultHostnameVerifier();  
            SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslcontext, hostnameVerifier);  
            Registry<ConnectionSocketFactory> socketFactoryRegistry = RegistryBuilder.<ConnectionSocketFactory>create()  
                    .register("http", PlainConnectionSocketFactory.getSocketFactory())  
                    .register("https", sslsf)  
                    .build();  
            poolConnManager = new PoolingHttpClientConnectionManager(socketFactoryRegistry);  
            // Increase max total connection to 200  
            poolConnManager.setMaxTotal(maxTotalPool);  
            // Increase default max connection per route to 20  
            poolConnManager.setDefaultMaxPerRoute(maxConPerRoute);  
            SocketConfig socketConfig = SocketConfig.custom().setSoTimeout(socketTimeout).build();  
            poolConnManager.setDefaultSocketConfig(socketConfig);  
        } catch (Exception e) {  
            log.warning("InterfacePhpUtilManager init Exception"+e.toString());  
        }  
    }  
    public CloseableHttpClient getConnection() {  
        RequestConfig requestConfig = RequestConfig.custom().setConnectionRequestTimeout(connectionRequestTimeout)  
                .setConnectTimeout(connectTimeout).setSocketTimeout(socketTimeout).build();  
        CloseableHttpClient httpClient = HttpClients.custom()  
                    .setConnectionManager(poolConnManager).setDefaultRequestConfig(requestConfig).build();  
        if(poolConnManager!=null&&poolConnManager.getTotalStats() != null) {  
            log.info("now client pool "+poolConnManager.getTotalStats().toString());  
        }  
        return httpClient;  
    }  
    
    /**
     * 解析HTTP头信息
     * @param headers
     * @return
     */
    private static Header[] parseHeaders(Map<String, String> headers) {
        if(null == headers || headers.isEmpty()) {
            return getDefaultHeaders();
        }

        Header[] hs = new BasicHeader[headers.size()];
        int i = 0;
        for(String key : headers.keySet()) {
            hs[i] = new BasicHeader(key, headers.get(key));
            i++;
        }

        return hs;
    }

    /**
     * 获取默认HTTP头信息
     * @return
     */
    private static Header[] getDefaultHeaders() {
        Header[] hs = new BasicHeader[2];
        hs[0] = new BasicHeader("Content-Type", "application/x-www-form-urlencoded");
        hs[1] = new BasicHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.3; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/33.0.1750.146 Safari/537.36");
        return hs;
    }
    /**
     * HTTPS GET请求
     * @param url
     * @return
     */
    public HttpResponseEntity get(String url) {
    	return get(url, null);
    }
    /**
     * HTTPS GET 请求
     * @param url
     * @param headers
     * @return
     */
    public HttpResponseEntity get(String url, Map<String, String> headers) {
    	HttpResponseEntity responseEntity = null;
    	HttpGet httpGet = new HttpGet(url);
    	try {
    		httpGet.setHeaders(parseHeaders(headers));
    		CloseableHttpResponse response = getConnection().execute(httpGet);
    		
    		responseEntity = new HttpResponseEntity();
    		StatusLine statusLine = response.getStatusLine();
    		int status = statusLine.getStatusCode();  
    		responseEntity.setStatusCode(status);
            if (status >= 200 && status < 300) {  
                HttpEntity entity = response.getEntity();  
                String resopnse="";  
                if(entity != null) {  
                    resopnse= EntityUtils.toString(entity,"utf-8");
                    responseEntity.setResponseBody(resopnse);
                }
            } else {  
                HttpEntity entity = response.getEntity();  
                if(entity != null) {
                    responseEntity.setResponseBody(EntityUtils.toString(entity,"utf-8"));
                }
                httpGet.abort();
            } 
		} catch (IOException e) {
			log.info("request url=" + url + " error msg=" + e.getMessage());
		}
    	return responseEntity;
    }
    /**
     * HTTPS POST请求
     * @param url
     * @param params
     * @return
     */
    public HttpResponseEntity post(String url, Map<String, String> params) {
    	return post(url, null, params);
    }
    /**
     * HTTPS POST请求
     * @param url
     * @param headers
     * @param params
     * @return
     */
    public HttpResponseEntity post(String url, Map<String, String> headers, Map<String, String> params) {
    	HttpResponseEntity responseEntity = null;
    	HttpPost httpPost = new HttpPost(url);
    	httpPost.setHeaders(parseHeaders(headers));
    	try {
    		List <NameValuePair> nvps = new ArrayList <NameValuePair>();  
    		for(Entry<String, String> entry : params.entrySet()) {
    			nvps.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));  
    		}
    		
            httpPost.setEntity(new UrlEncodedFormEntity(nvps, "UTF-8")); 
            
    		CloseableHttpResponse response = getConnection().execute(httpPost);
    		
    		responseEntity = new HttpResponseEntity();
    		StatusLine statusLine = response.getStatusLine();
    		int status = statusLine.getStatusCode();  
    		responseEntity.setStatusCode(status);
            if (status >= 200 && status < 300) {  
                HttpEntity entity = response.getEntity();  
                String resopnse="";  
                if(entity != null) {  
                    resopnse= EntityUtils.toString(entity,"UTF-8");
                    responseEntity.setResponseBody(resopnse);
                }
            } else {  
                HttpEntity entity = response.getEntity();  
                if(entity != null) {
                    responseEntity.setResponseBody(EntityUtils.toString(entity,"UTF-8"));
                }
                httpPost.abort();
            } 
		} catch (IOException e) {
			log.info("request url=" + url + " error msg=" + e.getMessage());
		}
    	return responseEntity;
    }
}
