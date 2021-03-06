package org.zhx.common.image.loader.https;

import org.zhx.common.image.utils.CLog;

import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.X509TrustManager;


/**
 * Name: CImageX509TrustManager
 * Author: zhouxue
 * Email: 194093798@qq.com
 * Comment: //TODO
 * Date: 2020-07-09 19:29
 */
public class CImageX509TrustManager implements X509TrustManager {
    public java.security.cert.X509Certificate[] getAcceptedIssuers() {
        return new java.security.cert.X509Certificate[]{};
    }

    public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
        CLog.e("checkClientTrusted");
    }

    public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
        CLog.e("checkServerTrusted");
    }
}
