/*******************************************************************************
 * Copyright 2011-2014 Sergey Tarasevich
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *******************************************************************************/
package org.zhx.common.image.loader.http;

import android.annotation.TargetApi;
import android.content.ContentResolver;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Build;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.webkit.MimeTypeMap;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import org.zhx.common.image.io.ContentLengthInputStream;
import org.zhx.common.image.loader.ImageLoader;
import org.zhx.common.image.utils.IoUtils;
import org.zhx.common.image.utils.CLog;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

/**
 * uri for image scheme
 */
public class BaseImageDownloader implements ImageLoader {
    private String TAG = BaseImageDownloader.class.getSimpleName();
    /**
     * 连接超时 时间
     */
    public static final int DEFAULT_HTTP_CONNECT_TIMEOUT = 20 * 1000;
    /**
     * 读取超时时间
     */
    public static final int DEFAULT_HTTP_READ_TIMEOUT = 20 * 1000;
    /**
     * 读取大小
     */
    protected static final int BUFFER_SIZE = 32 * 1024; // 32 Kb
    /**
     * URL 关键字
     */
    protected static final String ALLOWED_URI_CHARS = "@#&=*+-_.,:!?()/~'%";
    /**
     * 最大重连次数
     */
    protected static final int MAX_REDIRECT_COUNT = 5;
    /**
     *
     */
    protected static final String CONTENT_CONTACTS_URI_PREFIX = "content://com.android.contacts/";

    private static final String ERROR_UNSUPPORTED_SCHEME = "scheme error";

    protected final Context context;
    protected final int connectTimeout;
    protected final int readTimeout;
    private X509TrustManager manager;
    private HostnameVerifier hostnameVerifier;
    SSLContext sslContext;

    public BaseImageDownloader(Context context) {
        this(context, DEFAULT_HTTP_CONNECT_TIMEOUT, DEFAULT_HTTP_READ_TIMEOUT);
    }

    public BaseImageDownloader(Context context, int connectTimeout, int readTimeout) {
        this.context = context.getApplicationContext();
        this.connectTimeout = connectTimeout;
        this.readTimeout = readTimeout;
    }

    @Override
    public void initHttps(X509TrustManager manager, HostnameVerifier hostnameVerifier) {
        this.hostnameVerifier = hostnameVerifier;
        this.manager = manager;
        try {
            sslContext = SSLContext.getInstance("SSL");
            sslContext.init(null, new TrustManager[]{manager}, new SecureRandom());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public InputStream getStream(String imageUri, Object extra) throws IOException {
        switch (Scheme.ofUri(imageUri)) {
            case HTTP:
                return getStreamFromNetwork(imageUri, extra);
            case HTTPS:
                return getStreamFromNetworkHttps(imageUri, extra);
            case FILE:
                return getStreamFromFile(imageUri, extra);
            case CONTENT:
                return getStreamFromContent(imageUri, extra);
            case ASSETS:
                return getStreamFromAssets(imageUri, extra);
            case DRAWABLE:
                return getStreamFromDrawable(imageUri, extra);
            case UNKNOWN:
                CLog.e(TAG, imageUri + "");
            default:
                return getStreamFromOtherSource(imageUri, extra);
        }
    }

    private InputStream getStreamFromNetworkHttps(String imageUri, Object extra) {
        HttpsURLConnection conn = null;
        try {
            URL serverUrl = new URL(Uri.encode(imageUri, ALLOWED_URI_CHARS));
            conn = (HttpsURLConnection) serverUrl.openConnection();
            conn.setReadTimeout(readTimeout);
            conn.setConnectTimeout(connectTimeout);
            SSLSocketFactory factory = sslContext.getSocketFactory();
            HttpsURLConnection.setDefaultSSLSocketFactory(factory);
            conn.setSSLSocketFactory(factory);
            conn.setHostnameVerifier(hostnameVerifier);

            conn.setInstanceFollowRedirects(false);
            conn.setDoInput(true);
            conn.setRequestProperty("Content-Type", "application/octet-stream");
            conn.setRequestProperty("Connection", "Keep-Alive");
            InputStream imageStream;
           int code= conn.getResponseCode();
           CLog.e(code+" "+imageUri);
            try {
                imageStream = conn.getInputStream();
            } catch (IOException e) {
                e.printStackTrace();
                IoUtils.readAndCloseStream(conn.getErrorStream());
                throw e;
            }
            if (!shouldBeProcessed(conn)) {
                IoUtils.closeSilently(imageStream);
                throw new IOException("Image request failed with response code " + conn.getResponseCode());
            }

            return new ContentLengthInputStream(new BufferedInputStream(imageStream, BUFFER_SIZE), conn.getContentLength());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * Trust every server - dont check for any certificate
     */
    private static void trustAllHosts() {
        final String TAG = "trustAllHosts";
        // Create a trust manager that does not validate certificate chains
        TrustManager[] trustAllCerts = new TrustManager[]{
                new X509TrustManager() {
                    public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                        return new java.security.cert.X509Certificate[]{};
                    }

                    public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
                        android.util.Log.i(TAG, "checkClientTrusted");
                    }

                    public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
                        android.util.Log.i(TAG, "checkServerTrusted");
                    }
                }

        };

        // Install the all-trusting trust manager
        try {
            SSLContext sc = SSLContext.getInstance("TLS");
            sc.init(null, trustAllCerts, new java.security.SecureRandom());
            HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Retrieves {@link InputStream} of image by URI (image is located in the network).
     *
     * @param imageUri Image URI
     * @param extra
     * @return {@link InputStream} of image
     * @throws IOException if some I/O error occurs during network request or if no InputStream could be created for
     *                     URL.
     */
    protected InputStream getStreamFromNetwork(String imageUri, Object extra) throws IOException {
        HttpURLConnection conn = createConnection(imageUri, extra);

        int redirectCount = 0;
        while (conn.getResponseCode() / 100 == 3 && redirectCount < MAX_REDIRECT_COUNT) {
            conn = createConnection(conn.getHeaderField("Location"), extra);
            redirectCount++;
        }

        InputStream imageStream;
        try {
            imageStream = conn.getInputStream();
        } catch (IOException e) {
            e.printStackTrace();
            IoUtils.readAndCloseStream(conn.getErrorStream());
            throw e;
        }
        if (!shouldBeProcessed(conn)) {
            IoUtils.closeSilently(imageStream);
            throw new IOException("Image request failed with response code " + conn.getResponseCode());
        }

        return new ContentLengthInputStream(new BufferedInputStream(imageStream, BUFFER_SIZE), conn.getContentLength());
    }

    /**
     * @param conn Opened request connection (response code is available)
     * @return <b>true</b> - if data from connection is correct and should be read and processed;
     * <b>false</b> - if response contains irrelevant data and shouldn't be processed
     * @throws IOException
     */
    protected boolean shouldBeProcessed(HttpURLConnection conn) throws IOException {
        return conn.getResponseCode() == 200;
    }

    /**
     * Create {@linkplain HttpURLConnection HTTP connection} for incoming URL
     *
     * @param url   URL to connect to
     * @param extra
     * @return {@linkplain HttpURLConnection Connection} for incoming URL. Connection isn't established so it still configurable.
     * @throws IOException if some I/O error occurs during network request or if no InputStream could be created for
     *                     URL.
     */
    protected HttpURLConnection createConnection(String url, Object extra) throws IOException {
        String encodedUrl = Uri.encode(url, ALLOWED_URI_CHARS);
        HttpURLConnection conn = (HttpURLConnection) new URL(encodedUrl).openConnection();
        conn.setConnectTimeout(connectTimeout);
        conn.setReadTimeout(readTimeout);
        return conn;
    }

    /**
     * Retrieves {@link InputStream} of image by URI (image is located on the local file system or SD card).
     *
     * @param imageUri Image URI
     * @param extra
     * @return {@link InputStream} of image
     * @throws IOException if some I/O error occurs reading from file system
     */
    protected InputStream getStreamFromFile(String imageUri, Object extra) throws IOException {
        String filePath = Scheme.FILE.crop(imageUri);
        if (isVideoFileUri(imageUri)) {
            return getVideoThumbnailStream(filePath);
        } else {
            BufferedInputStream imageStream = new BufferedInputStream(new FileInputStream(filePath), BUFFER_SIZE);
            return new ContentLengthInputStream(imageStream, (int) new File(filePath).length());
        }
    }

    @TargetApi(Build.VERSION_CODES.FROYO)
    private InputStream getVideoThumbnailStream(String filePath) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.FROYO) {
            Bitmap bitmap = ThumbnailUtils
                    .createVideoThumbnail(filePath, MediaStore.Images.Thumbnails.FULL_SCREEN_KIND);
            if (bitmap != null) {
                ByteArrayOutputStream bos = new ByteArrayOutputStream();
                bitmap.compress(CompressFormat.PNG, 0, bos);
                return new ByteArrayInputStream(bos.toByteArray());
            }
        }
        return null;
    }

    /**
     * Retrieves {@link InputStream} of image by URI (image is accessed using {@link ContentResolver}).
     *
     * @param imageUri Image URI
     * @param extra
     * @return {@link InputStream} of image
     * @throws FileNotFoundException if the provided URI could not be opened
     */
    protected InputStream getStreamFromContent(String imageUri, Object extra) throws FileNotFoundException {
        ContentResolver res = context.getContentResolver();

        Uri uri = Uri.parse(imageUri);
        if (isVideoContentUri(uri)) { // video thumbnail
            Long origId = Long.valueOf(uri.getLastPathSegment());
            Bitmap bitmap = MediaStore.Video.Thumbnails
                    .getThumbnail(res, origId, MediaStore.Images.Thumbnails.MINI_KIND, null);
            if (bitmap != null) {
                ByteArrayOutputStream bos = new ByteArrayOutputStream();
                bitmap.compress(CompressFormat.PNG, 0, bos);
                return new ByteArrayInputStream(bos.toByteArray());
            }
        } else if (imageUri.startsWith(CONTENT_CONTACTS_URI_PREFIX)) { // contacts photo
            return getContactPhotoStream(uri);
        }

        return res.openInputStream(uri);
    }

    @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
    protected InputStream getContactPhotoStream(Uri uri) {
        ContentResolver res = context.getContentResolver();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
            return ContactsContract.Contacts.openContactPhotoInputStream(res, uri, true);
        } else {
            return ContactsContract.Contacts.openContactPhotoInputStream(res, uri);
        }
    }

    /**
     * Retrieves {@link InputStream} of image by URI (image is located in assets of application).
     *
     * @param imageUri Image URI
     * @param extra
     * @return {@link InputStream} of image
     * @throws IOException if some I/O error occurs file reading
     */
    protected InputStream getStreamFromAssets(String imageUri, Object extra) throws IOException {
        String filePath = Scheme.ASSETS.crop(imageUri);
        return context.getAssets().open(filePath);
    }

    /**
     * Retrieves {@link InputStream} of image by URI (image is located in drawable resources of application).
     *
     * @param imageUri Image URI
     * @param extra
     * @return {@link InputStream} of image
     */
    protected InputStream getStreamFromDrawable(String imageUri, Object extra) {
        String drawableIdString = Scheme.DRAWABLE.crop(imageUri);
        int drawableId = Integer.parseInt(drawableIdString);
        return context.getResources().openRawResource(drawableId);
    }

    /**
     * Retrieves {@link InputStream} of image by URI from other source with unsupported scheme. Should be overriden by
     * successors to implement image downloading from special sources.<br />
     * This method is called only if image URI has unsupported scheme. Throws {@link UnsupportedOperationException} by
     * default.
     *
     * @param imageUri Image URI
     * @param extra
     * @return {@link InputStream} of image
     * @throws IOException                   if some I/O error occurs
     * @throws UnsupportedOperationException if image URI has unsupported scheme(protocol)
     */
    protected InputStream getStreamFromOtherSource(String imageUri, Object extra) throws IOException {
        throw new UnsupportedOperationException(String.format(ERROR_UNSUPPORTED_SCHEME, imageUri));
    }

    private boolean isVideoContentUri(Uri uri) {
        String mimeType = context.getContentResolver().getType(uri);
        return mimeType != null && mimeType.startsWith("video/");
    }

    private boolean isVideoFileUri(String uri) {
        String extension = MimeTypeMap.getFileExtensionFromUrl(uri);
        String mimeType = MimeTypeMap.getSingleton().getMimeTypeFromExtension(extension);
        return mimeType != null && mimeType.startsWith("video/");
    }
}
