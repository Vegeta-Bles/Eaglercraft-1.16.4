import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.Proxy;
import java.net.URL;

public abstract class dge<T extends dge<T>> {
   protected HttpURLConnection a;
   private boolean c;
   protected String b;

   public dge(String var1, int var2, int var3) {
      try {
         this.b = _snowman;
         Proxy _snowman = dgc.a();
         if (_snowman != null) {
            this.a = (HttpURLConnection)new URL(_snowman).openConnection(_snowman);
         } else {
            this.a = (HttpURLConnection)new URL(_snowman).openConnection();
         }

         this.a.setConnectTimeout(_snowman);
         this.a.setReadTimeout(_snowman);
      } catch (MalformedURLException var5) {
         throw new dhh(var5.getMessage(), var5);
      } catch (IOException var6) {
         throw new dhh(var6.getMessage(), var6);
      }
   }

   public void a(String var1, String var2) {
      a(this.a, _snowman, _snowman);
   }

   public static void a(HttpURLConnection var0, String var1, String var2) {
      String _snowman = _snowman.getRequestProperty("Cookie");
      if (_snowman == null) {
         _snowman.setRequestProperty("Cookie", _snowman + "=" + _snowman);
      } else {
         _snowman.setRequestProperty("Cookie", _snowman + ";" + _snowman + "=" + _snowman);
      }
   }

   public int a() {
      return a(this.a);
   }

   public static int a(HttpURLConnection var0) {
      String _snowman = _snowman.getHeaderField("Retry-After");

      try {
         return Integer.valueOf(_snowman);
      } catch (Exception var3) {
         return 5;
      }
   }

   public int b() {
      try {
         this.d();
         return this.a.getResponseCode();
      } catch (Exception var2) {
         throw new dhh(var2.getMessage(), var2);
      }
   }

   public String c() {
      try {
         this.d();
         String _snowman = null;
         if (this.b() >= 400) {
            _snowman = this.a(this.a.getErrorStream());
         } else {
            _snowman = this.a(this.a.getInputStream());
         }

         this.f();
         return _snowman;
      } catch (IOException var2) {
         throw new dhh(var2.getMessage(), var2);
      }
   }

   private String a(InputStream var1) throws IOException {
      if (_snowman == null) {
         return "";
      } else {
         InputStreamReader _snowman = new InputStreamReader(_snowman, "UTF-8");
         StringBuilder _snowmanx = new StringBuilder();

         for (int _snowmanxx = _snowman.read(); _snowmanxx != -1; _snowmanxx = _snowman.read()) {
            _snowmanx.append((char)_snowmanxx);
         }

         return _snowmanx.toString();
      }
   }

   private void f() {
      byte[] _snowman = new byte[1024];

      try {
         InputStream _snowmanx = this.a.getInputStream();

         while (_snowmanx.read(_snowman) > 0) {
         }

         _snowmanx.close();
         return;
      } catch (Exception var9) {
         try {
            InputStream _snowmanx = this.a.getErrorStream();
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
         if (this.a != null) {
            this.a.disconnect();
         }
      }
   }

   protected T d() {
      if (this.c) {
         return (T)this;
      } else {
         T _snowman = this.e();
         this.c = true;
         return _snowman;
      }
   }

   protected abstract T e();

   public static dge<?> a(String var0) {
      return new dge.b(_snowman, 5000, 60000);
   }

   public static dge<?> a(String var0, int var1, int var2) {
      return new dge.b(_snowman, _snowman, _snowman);
   }

   public static dge<?> b(String var0, String var1) {
      return new dge.c(_snowman, _snowman, 5000, 60000);
   }

   public static dge<?> a(String var0, String var1, int var2, int var3) {
      return new dge.c(_snowman, _snowman, _snowman, _snowman);
   }

   public static dge<?> b(String var0) {
      return new dge.a(_snowman, 5000, 60000);
   }

   public static dge<?> c(String var0, String var1) {
      return new dge.d(_snowman, _snowman, 5000, 60000);
   }

   public static dge<?> b(String var0, String var1, int var2, int var3) {
      return new dge.d(_snowman, _snowman, _snowman, _snowman);
   }

   public String c(String var1) {
      return a(this.a, _snowman);
   }

   public static String a(HttpURLConnection var0, String var1) {
      try {
         return _snowman.getHeaderField(_snowman);
      } catch (Exception var3) {
         return "";
      }
   }

   public static class a extends dge<dge.a> {
      public a(String var1, int var2, int var3) {
         super(_snowman, _snowman, _snowman);
      }

      public dge.a f() {
         try {
            this.a.setDoOutput(true);
            this.a.setRequestMethod("DELETE");
            this.a.connect();
            return this;
         } catch (Exception var2) {
            throw new dhh(var2.getMessage(), var2);
         }
      }
   }

   public static class b extends dge<dge.b> {
      public b(String var1, int var2, int var3) {
         super(_snowman, _snowman, _snowman);
      }

      public dge.b f() {
         try {
            this.a.setDoInput(true);
            this.a.setDoOutput(true);
            this.a.setUseCaches(false);
            this.a.setRequestMethod("GET");
            return this;
         } catch (Exception var2) {
            throw new dhh(var2.getMessage(), var2);
         }
      }
   }

   public static class c extends dge<dge.c> {
      private final String c;

      public c(String var1, String var2, int var3, int var4) {
         super(_snowman, _snowman, _snowman);
         this.c = _snowman;
      }

      public dge.c f() {
         try {
            if (this.c != null) {
               this.a.setRequestProperty("Content-Type", "application/json; charset=utf-8");
            }

            this.a.setDoInput(true);
            this.a.setDoOutput(true);
            this.a.setUseCaches(false);
            this.a.setRequestMethod("POST");
            OutputStream _snowman = this.a.getOutputStream();
            OutputStreamWriter _snowmanx = new OutputStreamWriter(_snowman, "UTF-8");
            _snowmanx.write(this.c);
            _snowmanx.close();
            _snowman.flush();
            return this;
         } catch (Exception var3) {
            throw new dhh(var3.getMessage(), var3);
         }
      }
   }

   public static class d extends dge<dge.d> {
      private final String c;

      public d(String var1, String var2, int var3, int var4) {
         super(_snowman, _snowman, _snowman);
         this.c = _snowman;
      }

      public dge.d f() {
         try {
            if (this.c != null) {
               this.a.setRequestProperty("Content-Type", "application/json; charset=utf-8");
            }

            this.a.setDoOutput(true);
            this.a.setDoInput(true);
            this.a.setRequestMethod("PUT");
            OutputStream _snowman = this.a.getOutputStream();
            OutputStreamWriter _snowmanx = new OutputStreamWriter(_snowman, "UTF-8");
            _snowmanx.write(this.c);
            _snowmanx.close();
            _snowman.flush();
            return this;
         } catch (Exception var3) {
            throw new dhh(var3.getMessage(), var3);
         }
      }
   }
}
