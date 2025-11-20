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
import net.minecraft.client.realms.exception.RealmsHttpException;

public abstract class Request<T extends Request<T>> {
   protected HttpURLConnection connection;
   private boolean connected;
   protected String url;

   public Request(String url, int connectTimeout, int readTimeout) {
      try {
         this.url = url;
         Proxy _snowman = RealmsClientConfig.getProxy();
         if (_snowman != null) {
            this.connection = (HttpURLConnection)new URL(url).openConnection(_snowman);
         } else {
            this.connection = (HttpURLConnection)new URL(url).openConnection();
         }

         this.connection.setConnectTimeout(connectTimeout);
         this.connection.setReadTimeout(readTimeout);
      } catch (MalformedURLException var5) {
         throw new RealmsHttpException(var5.getMessage(), var5);
      } catch (IOException var6) {
         throw new RealmsHttpException(var6.getMessage(), var6);
      }
   }

   public void cookie(String key, String value) {
      cookie(this.connection, key, value);
   }

   public static void cookie(HttpURLConnection connection, String key, String value) {
      String _snowman = connection.getRequestProperty("Cookie");
      if (_snowman == null) {
         connection.setRequestProperty("Cookie", key + "=" + value);
      } else {
         connection.setRequestProperty("Cookie", _snowman + ";" + key + "=" + value);
      }
   }

   public int getRetryAfterHeader() {
      return getRetryAfterHeader(this.connection);
   }

   public static int getRetryAfterHeader(HttpURLConnection connection) {
      String _snowman = connection.getHeaderField("Retry-After");

      try {
         return Integer.valueOf(_snowman);
      } catch (Exception var3) {
         return 5;
      }
   }

   public int responseCode() {
      try {
         this.connect();
         return this.connection.getResponseCode();
      } catch (Exception var2) {
         throw new RealmsHttpException(var2.getMessage(), var2);
      }
   }

   public String text() {
      try {
         this.connect();
         String _snowman = null;
         if (this.responseCode() >= 400) {
            _snowman = this.read(this.connection.getErrorStream());
         } else {
            _snowman = this.read(this.connection.getInputStream());
         }

         this.dispose();
         return _snowman;
      } catch (IOException var2) {
         throw new RealmsHttpException(var2.getMessage(), var2);
      }
   }

   private String read(InputStream in) throws IOException {
      if (in == null) {
         return "";
      } else {
         InputStreamReader _snowman = new InputStreamReader(in, "UTF-8");
         StringBuilder _snowmanx = new StringBuilder();

         for (int _snowmanxx = _snowman.read(); _snowmanxx != -1; _snowmanxx = _snowman.read()) {
            _snowmanx.append((char)_snowmanxx);
         }

         return _snowmanx.toString();
      }
   }

   private void dispose() {
      byte[] _snowman = new byte[1024];

      try {
         InputStream _snowmanx = this.connection.getInputStream();

         while (_snowmanx.read(_snowman) > 0) {
         }

         _snowmanx.close();
         return;
      } catch (Exception var9) {
         try {
            InputStream _snowmanx = this.connection.getErrorStream();
            if (_snowmanx != null) {
               while (_snowmanx.read(_snowman) > 0) {
               }

               _snowmanx.close();
               return;
            }
         } catch (IOException var8) {
            return;
         }
      } finally {
         if (this.connection != null) {
            this.connection.disconnect();
         }
      }
   }

   protected T connect() {
      if (this.connected) {
         return (T)this;
      } else {
         T _snowman = this.doConnect();
         this.connected = true;
         return _snowman;
      }
   }

   protected abstract T doConnect();

   public static Request<?> get(String url) {
      return new Request.Get(url, 5000, 60000);
   }

   public static Request<?> get(String url, int connectTimeoutMillis, int readTimeoutMillis) {
      return new Request.Get(url, connectTimeoutMillis, readTimeoutMillis);
   }

   public static Request<?> post(String uri, String content) {
      return new Request.Post(uri, content, 5000, 60000);
   }

   public static Request<?> post(String uri, String content, int connectTimeoutMillis, int readTimeoutMillis) {
      return new Request.Post(uri, content, connectTimeoutMillis, readTimeoutMillis);
   }

   public static Request<?> delete(String url) {
      return new Request.Delete(url, 5000, 60000);
   }

   public static Request<?> put(String url, String content) {
      return new Request.Put(url, content, 5000, 60000);
   }

   public static Request<?> put(String url, String content, int connectTimeoutMillis, int readTimeoutMillis) {
      return new Request.Put(url, content, connectTimeoutMillis, readTimeoutMillis);
   }

   public String getHeader(String header) {
      return getHeader(this.connection, header);
   }

   public static String getHeader(HttpURLConnection connection, String header) {
      try {
         return connection.getHeaderField(header);
      } catch (Exception var3) {
         return "";
      }
   }

   public static class Delete extends Request<Request.Delete> {
      public Delete(String _snowman, int _snowman, int _snowman) {
         super(_snowman, _snowman, _snowman);
      }

      public Request.Delete doConnect() {
         try {
            this.connection.setDoOutput(true);
            this.connection.setRequestMethod("DELETE");
            this.connection.connect();
            return this;
         } catch (Exception var2) {
            throw new RealmsHttpException(var2.getMessage(), var2);
         }
      }
   }

   public static class Get extends Request<Request.Get> {
      public Get(String _snowman, int _snowman, int _snowman) {
         super(_snowman, _snowman, _snowman);
      }

      public Request.Get doConnect() {
         try {
            this.connection.setDoInput(true);
            this.connection.setDoOutput(true);
            this.connection.setUseCaches(false);
            this.connection.setRequestMethod("GET");
            return this;
         } catch (Exception var2) {
            throw new RealmsHttpException(var2.getMessage(), var2);
         }
      }
   }

   public static class Post extends Request<Request.Post> {
      private final String content;

      public Post(String uri, String content, int connectTimeout, int readTimeout) {
         super(uri, connectTimeout, readTimeout);
         this.content = content;
      }

      public Request.Post doConnect() {
         try {
            if (this.content != null) {
               this.connection.setRequestProperty("Content-Type", "application/json; charset=utf-8");
            }

            this.connection.setDoInput(true);
            this.connection.setDoOutput(true);
            this.connection.setUseCaches(false);
            this.connection.setRequestMethod("POST");
            OutputStream _snowman = this.connection.getOutputStream();
            OutputStreamWriter _snowmanx = new OutputStreamWriter(_snowman, "UTF-8");
            _snowmanx.write(this.content);
            _snowmanx.close();
            _snowman.flush();
            return this;
         } catch (Exception var3) {
            throw new RealmsHttpException(var3.getMessage(), var3);
         }
      }
   }

   public static class Put extends Request<Request.Put> {
      private final String content;

      public Put(String uri, String content, int connectTimeout, int readTimeout) {
         super(uri, connectTimeout, readTimeout);
         this.content = content;
      }

      public Request.Put doConnect() {
         try {
            if (this.content != null) {
               this.connection.setRequestProperty("Content-Type", "application/json; charset=utf-8");
            }

            this.connection.setDoOutput(true);
            this.connection.setDoInput(true);
            this.connection.setRequestMethod("PUT");
            OutputStream _snowman = this.connection.getOutputStream();
            OutputStreamWriter _snowmanx = new OutputStreamWriter(_snowman, "UTF-8");
            _snowmanx.write(this.content);
            _snowmanx.close();
            _snowman.flush();
            return this;
         } catch (Exception var3) {
            throw new RealmsHttpException(var3.getMessage(), var3);
         }
      }
   }
}
