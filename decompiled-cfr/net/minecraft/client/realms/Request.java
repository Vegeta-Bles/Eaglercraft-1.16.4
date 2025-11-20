/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client.realms;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.Proxy;
import java.net.URL;
import net.minecraft.client.realms.RealmsClientConfig;
import net.minecraft.client.realms.exception.RealmsHttpException;

public abstract class Request<T extends Request<T>> {
    protected HttpURLConnection connection;
    private boolean connected;
    protected String url;

    public Request(String url, int connectTimeout, int readTimeout) {
        try {
            this.url = url;
            Proxy proxy = RealmsClientConfig.getProxy();
            this.connection = proxy != null ? (HttpURLConnection)new URL(url).openConnection(proxy) : (HttpURLConnection)new URL(url).openConnection();
            this.connection.setConnectTimeout(connectTimeout);
            this.connection.setReadTimeout(readTimeout);
        }
        catch (MalformedURLException malformedURLException) {
            throw new RealmsHttpException(malformedURLException.getMessage(), malformedURLException);
        }
        catch (IOException iOException) {
            throw new RealmsHttpException(iOException.getMessage(), iOException);
        }
    }

    public void cookie(String key, String value) {
        Request.cookie(this.connection, key, value);
    }

    public static void cookie(HttpURLConnection connection, String key, String value) {
        String string = connection.getRequestProperty("Cookie");
        if (string == null) {
            connection.setRequestProperty("Cookie", key + "=" + value);
        } else {
            connection.setRequestProperty("Cookie", string + ";" + key + "=" + value);
        }
    }

    public int getRetryAfterHeader() {
        return Request.getRetryAfterHeader(this.connection);
    }

    public static int getRetryAfterHeader(HttpURLConnection connection) {
        String string = connection.getHeaderField("Retry-After");
        try {
            return Integer.valueOf(string);
        }
        catch (Exception _snowman2) {
            return 5;
        }
    }

    public int responseCode() {
        try {
            this.connect();
            return this.connection.getResponseCode();
        }
        catch (Exception exception) {
            throw new RealmsHttpException(exception.getMessage(), exception);
        }
    }

    public String text() {
        try {
            this.connect();
            String string = null;
            string = this.responseCode() >= 400 ? this.read(this.connection.getErrorStream()) : this.read(this.connection.getInputStream());
            this.dispose();
            return string;
        }
        catch (IOException iOException) {
            throw new RealmsHttpException(iOException.getMessage(), iOException);
        }
    }

    private String read(InputStream in) throws IOException {
        if (in == null) {
            return "";
        }
        InputStreamReader inputStreamReader = new InputStreamReader(in, "UTF-8");
        StringBuilder _snowman2 = new StringBuilder();
        int _snowman3 = inputStreamReader.read();
        while (_snowman3 != -1) {
            _snowman2.append((char)_snowman3);
            _snowman3 = inputStreamReader.read();
        }
        return _snowman2.toString();
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void dispose() {
        byte[] byArray = new byte[1024];
        try {
            InputStream inputStream = this.connection.getInputStream();
            while (inputStream.read(byArray) > 0) {
            }
            inputStream.close();
        }
        catch (Exception exception) {
            InputStream inputStream;
            block13: {
                inputStream = this.connection.getErrorStream();
                if (inputStream != null) break block13;
                return;
            }
            try {
                while (inputStream.read(byArray) > 0) {
                }
                inputStream.close();
            }
            catch (IOException iOException) {
                // empty catch block
            }
        }
        finally {
            if (this.connection != null) {
                this.connection.disconnect();
            }
        }
    }

    protected T connect() {
        if (this.connected) {
            return (T)this;
        }
        T t = this.doConnect();
        this.connected = true;
        return t;
    }

    protected abstract T doConnect();

    public static Request<?> get(String url) {
        return new Get(url, 5000, 60000);
    }

    public static Request<?> get(String url, int connectTimeoutMillis, int readTimeoutMillis) {
        return new Get(url, connectTimeoutMillis, readTimeoutMillis);
    }

    public static Request<?> post(String uri, String content) {
        return new Post(uri, content, 5000, 60000);
    }

    public static Request<?> post(String uri, String content, int connectTimeoutMillis, int readTimeoutMillis) {
        return new Post(uri, content, connectTimeoutMillis, readTimeoutMillis);
    }

    public static Request<?> delete(String url) {
        return new Delete(url, 5000, 60000);
    }

    public static Request<?> put(String url, String content) {
        return new Put(url, content, 5000, 60000);
    }

    public static Request<?> put(String url, String content, int connectTimeoutMillis, int readTimeoutMillis) {
        return new Put(url, content, connectTimeoutMillis, readTimeoutMillis);
    }

    public String getHeader(String header) {
        return Request.getHeader(this.connection, header);
    }

    public static String getHeader(HttpURLConnection connection, String header) {
        try {
            return connection.getHeaderField(header);
        }
        catch (Exception exception) {
            return "";
        }
    }

    public static class Post
    extends Request<Post> {
        private final String content;

        public Post(String uri, String content, int connectTimeout, int readTimeout) {
            super(uri, connectTimeout, readTimeout);
            this.content = content;
        }

        @Override
        public Post doConnect() {
            try {
                if (this.content != null) {
                    this.connection.setRequestProperty("Content-Type", "application/json; charset=utf-8");
                }
                this.connection.setDoInput(true);
                this.connection.setDoOutput(true);
                this.connection.setUseCaches(false);
                this.connection.setRequestMethod("POST");
                OutputStream outputStream = this.connection.getOutputStream();
                OutputStreamWriter _snowman2 = new OutputStreamWriter(outputStream, "UTF-8");
                _snowman2.write(this.content);
                _snowman2.close();
                outputStream.flush();
                return this;
            }
            catch (Exception exception) {
                throw new RealmsHttpException(exception.getMessage(), exception);
            }
        }

        @Override
        public /* synthetic */ Request doConnect() {
            return this.doConnect();
        }
    }

    public static class Put
    extends Request<Put> {
        private final String content;

        public Put(String uri, String content, int connectTimeout, int readTimeout) {
            super(uri, connectTimeout, readTimeout);
            this.content = content;
        }

        @Override
        public Put doConnect() {
            try {
                if (this.content != null) {
                    this.connection.setRequestProperty("Content-Type", "application/json; charset=utf-8");
                }
                this.connection.setDoOutput(true);
                this.connection.setDoInput(true);
                this.connection.setRequestMethod("PUT");
                OutputStream outputStream = this.connection.getOutputStream();
                OutputStreamWriter _snowman2 = new OutputStreamWriter(outputStream, "UTF-8");
                _snowman2.write(this.content);
                _snowman2.close();
                outputStream.flush();
                return this;
            }
            catch (Exception exception) {
                throw new RealmsHttpException(exception.getMessage(), exception);
            }
        }

        @Override
        public /* synthetic */ Request doConnect() {
            return this.doConnect();
        }
    }

    public static class Get
    extends Request<Get> {
        public Get(String string, int n, int n2) {
            super(string, n, n2);
        }

        @Override
        public Get doConnect() {
            try {
                this.connection.setDoInput(true);
                this.connection.setDoOutput(true);
                this.connection.setUseCaches(false);
                this.connection.setRequestMethod("GET");
                return this;
            }
            catch (Exception exception) {
                throw new RealmsHttpException(exception.getMessage(), exception);
            }
        }

        @Override
        public /* synthetic */ Request doConnect() {
            return this.doConnect();
        }
    }

    public static class Delete
    extends Request<Delete> {
        public Delete(String string, int n, int n2) {
            super(string, n, n2);
        }

        @Override
        public Delete doConnect() {
            try {
                this.connection.setDoOutput(true);
                this.connection.setRequestMethod("DELETE");
                this.connection.connect();
                return this;
            }
            catch (Exception exception) {
                throw new RealmsHttpException(exception.getMessage(), exception);
            }
        }

        @Override
        public /* synthetic */ Request doConnect() {
            return this.doConnect();
        }
    }
}

