import com.google.common.hash.Hashing;
import com.google.common.io.Files;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Path;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.commons.compress.archivers.tar.TarArchiveEntry;
import org.apache.commons.compress.archivers.tar.TarArchiveInputStream;
import org.apache.commons.compress.compressors.gzip.GzipCompressorInputStream;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.io.output.CountingOutputStream;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class dfy {
   private static final Logger a = LogManager.getLogger();
   private volatile boolean b;
   private volatile boolean c;
   private volatile boolean d;
   private volatile boolean e;
   private volatile File f;
   private volatile File g;
   private volatile HttpGet h;
   private Thread i;
   private final RequestConfig j = RequestConfig.custom().setSocketTimeout(120000).setConnectTimeout(120000).build();
   private static final String[] k = new String[]{
      "CON",
      "COM",
      "PRN",
      "AUX",
      "CLOCK$",
      "NUL",
      "COM1",
      "COM2",
      "COM3",
      "COM4",
      "COM5",
      "COM6",
      "COM7",
      "COM8",
      "COM9",
      "LPT1",
      "LPT2",
      "LPT3",
      "LPT4",
      "LPT5",
      "LPT6",
      "LPT7",
      "LPT8",
      "LPT9"
   };

   public dfy() {
   }

   public long a(String var1) {
      CloseableHttpClient _snowman = null;
      HttpGet _snowmanx = null;

      long var5;
      try {
         _snowmanx = new HttpGet(_snowman);
         _snowman = HttpClientBuilder.create().setDefaultRequestConfig(this.j).build();
         CloseableHttpResponse _snowmanxx = _snowman.execute(_snowmanx);
         return Long.parseLong(_snowmanxx.getFirstHeader("Content-Length").getValue());
      } catch (Throwable var16) {
         a.error("Unable to get content length for download");
         var5 = 0L;
      } finally {
         if (_snowmanx != null) {
            _snowmanx.releaseConnection();
         }

         if (_snowman != null) {
            try {
               _snowman.close();
            } catch (IOException var15) {
               a.error("Could not close http client", var15);
            }
         }
      }

      return var5;
   }

   public void a(dhd var1, String var2, dhv.a var3, cyg var4) {
      if (this.i == null) {
         this.i = new Thread(() -> {
            CloseableHttpClient _snowman = null;

            try {
               this.f = File.createTempFile("backup", ".tar.gz");
               this.h = new HttpGet(_snowman.a);
               _snowman = HttpClientBuilder.create().setDefaultRequestConfig(this.j).build();
               HttpResponse _snowmanx = _snowman.execute(this.h);
               _snowman.b = Long.parseLong(_snowmanx.getFirstHeader("Content-Length").getValue());
               if (_snowmanx.getStatusLine().getStatusCode() == 200) {
                  OutputStream _snowmanxx = new FileOutputStream(this.f);
                  dfy.b _snowmanxxx = new dfy.b(_snowman.trim(), this.f, _snowman, _snowman);
                  dfy.a _snowmanxxxx = new dfy.a(_snowmanxx);
                  _snowmanxxxx.a(_snowmanxxx);
                  IOUtils.copy(_snowmanx.getEntity().getContent(), _snowmanxxxx);
                  return;
               }

               this.d = true;
               this.h.abort();
            } catch (Exception var93) {
               a.error("Caught exception while downloading: " + var93.getMessage());
               this.d = true;
               return;
            } finally {
               this.h.releaseConnection();
               if (this.f != null) {
                  this.f.delete();
               }

               if (!this.d) {
                  if (!_snowman.b.isEmpty() && !_snowman.c.isEmpty()) {
                     try {
                        this.f = File.createTempFile("resources", ".tar.gz");
                        this.h = new HttpGet(_snowman.b);
                        HttpResponse _snowmanx = _snowman.execute(this.h);
                        _snowman.b = Long.parseLong(_snowmanx.getFirstHeader("Content-Length").getValue());
                        if (_snowmanx.getStatusLine().getStatusCode() != 200) {
                           this.d = true;
                           this.h.abort();
                           return;
                        }

                        OutputStream _snowmanxx = new FileOutputStream(this.f);
                        dfy.c _snowmanxxx = new dfy.c(this.f, _snowman, _snowman);
                        dfy.a _snowmanxxxx = new dfy.a(_snowmanxx);
                        _snowmanxxxx.a(_snowmanxxx);
                        IOUtils.copy(_snowmanx.getEntity().getContent(), _snowmanxxxx);
                     } catch (Exception var91) {
                        a.error("Caught exception while downloading: " + var91.getMessage());
                        this.d = true;
                     } finally {
                        this.h.releaseConnection();
                        if (this.f != null) {
                           this.f.delete();
                        }
                     }
                  } else {
                     this.c = true;
                  }
               }

               if (_snowman != null) {
                  try {
                     _snowman.close();
                  } catch (IOException var90) {
                     a.error("Failed to close Realms download client");
                  }
               }
            }
         });
         this.i.setUncaughtExceptionHandler(new dhg(a));
         this.i.start();
      }
   }

   public void a() {
      if (this.h != null) {
         this.h.abort();
      }

      if (this.f != null) {
         this.f.delete();
      }

      this.b = true;
   }

   public boolean b() {
      return this.c;
   }

   public boolean c() {
      return this.d;
   }

   public boolean d() {
      return this.e;
   }

   public static String b(String var0) {
      _snowman = _snowman.replaceAll("[\\./\"]", "_");

      for (String _snowman : k) {
         if (_snowman.equalsIgnoreCase(_snowman)) {
            _snowman = "_" + _snowman + "_";
         }
      }

      return _snowman;
   }

   private void a(String var1, File var2, cyg var3) throws IOException {
      Pattern _snowman = Pattern.compile(".*-([0-9]+)$");
      int _snowmanx = 1;

      for (char _snowmanxx : w.e) {
         _snowman = _snowman.replace(_snowmanxx, '_');
      }

      if (StringUtils.isEmpty(_snowman)) {
         _snowman = "Realm";
      }

      _snowman = b(_snowman);

      try {
         for (cyh _snowmanxx : _snowman.b()) {
            if (_snowmanxx.a().toLowerCase(Locale.ROOT).startsWith(_snowman.toLowerCase(Locale.ROOT))) {
               Matcher _snowmanxxx = _snowman.matcher(_snowmanxx.a());
               if (_snowmanxxx.matches()) {
                  if (Integer.valueOf(_snowmanxxx.group(1)) > _snowmanx) {
                     _snowmanx = Integer.valueOf(_snowmanxxx.group(1));
                  }
               } else {
                  _snowmanx++;
               }
            }
         }
      } catch (Exception var128) {
         a.error("Error getting level list", var128);
         this.d = true;
         return;
      }

      String _snowmanxxx;
      if (_snowman.a(_snowman) && _snowmanx <= 1) {
         _snowmanxxx = _snowman;
      } else {
         _snowmanxxx = _snowman + (_snowmanx == 1 ? "" : "-" + _snowmanx);
         if (!_snowman.a(_snowmanxxx)) {
            boolean _snowmanxxxx = false;

            while (!_snowmanxxxx) {
               _snowmanx++;
               _snowmanxxx = _snowman + (_snowmanx == 1 ? "" : "-" + _snowmanx);
               if (_snowman.a(_snowmanxxx)) {
                  _snowmanxxxx = true;
               }
            }
         }
      }

      TarArchiveInputStream _snowmanxxxx = null;
      File _snowmanxxxxx = new File(djz.C().n.getAbsolutePath(), "saves");

      try {
         _snowmanxxxxx.mkdir();
         _snowmanxxxx = new TarArchiveInputStream(new GzipCompressorInputStream(new BufferedInputStream(new FileInputStream(_snowman))));

         for (TarArchiveEntry _snowmanxxxxxx = _snowmanxxxx.getNextTarEntry(); _snowmanxxxxxx != null; _snowmanxxxxxx = _snowmanxxxx.getNextTarEntry()) {
            File _snowmanxxxxxxx = new File(_snowmanxxxxx, _snowmanxxxxxx.getName().replace("world", _snowmanxxx));
            if (_snowmanxxxxxx.isDirectory()) {
               _snowmanxxxxxxx.mkdirs();
            } else {
               _snowmanxxxxxxx.createNewFile();

               try (FileOutputStream _snowmanxxxxxxxx = new FileOutputStream(_snowmanxxxxxxx)) {
                  IOUtils.copy(_snowmanxxxx, _snowmanxxxxxxxx);
               }
            }
         }
      } catch (Exception var126) {
         a.error("Error extracting world", var126);
         this.d = true;
      } finally {
         if (_snowmanxxxx != null) {
            _snowmanxxxx.close();
         }

         if (_snowman != null) {
            _snowman.delete();
         }

         try (cyg.a _snowmanxxxxxxx = _snowman.c(_snowmanxxx)) {
            _snowmanxxxxxxx.a(_snowmanxxx.trim());
            Path _snowmanxxxxxxxx = _snowmanxxxxxxx.a(cye.e);
            a(_snowmanxxxxxxxx.toFile());
         } catch (IOException var124) {
            a.error("Failed to rename unpacked realms level {}", _snowmanxxx, var124);
         }

         this.g = new File(_snowmanxxxxx, _snowmanxxx + File.separator + "resources.zip");
      }
   }

   private static void a(File var0) {
      if (_snowman.exists()) {
         try {
            md _snowman = mn.a(_snowman);
            md _snowmanx = _snowman.p("Data");
            _snowmanx.r("Player");
            mn.a(_snowman, _snowman);
         } catch (Exception var3) {
            var3.printStackTrace();
         }
      }
   }

   class a extends CountingOutputStream {
      private ActionListener b;

      public a(OutputStream var2) {
         super(_snowman);
      }

      public void a(ActionListener var1) {
         this.b = _snowman;
      }

      protected void afterWrite(int var1) throws IOException {
         super.afterWrite(_snowman);
         if (this.b != null) {
            this.b.actionPerformed(new ActionEvent(this, 0, null));
         }
      }
   }

   class b implements ActionListener {
      private final String b;
      private final File c;
      private final cyg d;
      private final dhv.a e;

      private b(String var2, File var3, cyg var4, dhv.a var5) {
         this.b = _snowman;
         this.c = _snowman;
         this.d = _snowman;
         this.e = _snowman;
      }

      @Override
      public void actionPerformed(ActionEvent var1) {
         this.e.a = ((dfy.a)_snowman.getSource()).getByteCount();
         if (this.e.a >= this.e.b && !dfy.this.b && !dfy.this.d) {
            try {
               dfy.this.e = true;
               dfy.this.a(this.b, this.c, this.d);
            } catch (IOException var3) {
               dfy.a.error("Error extracting archive", var3);
               dfy.this.d = true;
            }
         }
      }
   }

   class c implements ActionListener {
      private final File b;
      private final dhv.a c;
      private final dhd d;

      private c(File var2, dhv.a var3, dhd var4) {
         this.b = _snowman;
         this.c = _snowman;
         this.d = _snowman;
      }

      @Override
      public void actionPerformed(ActionEvent var1) {
         this.c.a = ((dfy.a)_snowman.getSource()).getByteCount();
         if (this.c.a >= this.c.b && !dfy.this.b) {
            try {
               String _snowman = Hashing.sha1().hashBytes(Files.toByteArray(this.b)).toString();
               if (_snowman.equals(this.d.c)) {
                  FileUtils.copyFile(this.b, dfy.this.g);
                  dfy.this.c = true;
               } else {
                  dfy.a.error("Resourcepack had wrong hash (expected " + this.d.c + ", found " + _snowman + "). Deleting it.");
                  FileUtils.deleteQuietly(this.b);
                  dfy.this.d = true;
               }
            } catch (IOException var3) {
               dfy.a.error("Error copying resourcepack file", var3.getMessage());
               dfy.this.d = true;
            }
         }
      }
   }
}
