import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.function.Predicate;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import org.apache.commons.io.IOUtils;

public class abh extends abg {
   public static final Splitter b = Splitter.on('/').omitEmptyStrings().limit(3);
   private ZipFile c;

   public abh(File var1) {
      super(_snowman);
   }

   private ZipFile b() throws IOException {
      if (this.c == null) {
         this.c = new ZipFile(this.a);
      }

      return this.c;
   }

   @Override
   protected InputStream a(String var1) throws IOException {
      ZipFile _snowman = this.b();
      ZipEntry _snowmanx = _snowman.getEntry(_snowman);
      if (_snowmanx == null) {
         throw new abl(this.a, _snowman);
      } else {
         return _snowman.getInputStream(_snowmanx);
      }
   }

   @Override
   public boolean c(String var1) {
      try {
         return this.b().getEntry(_snowman) != null;
      } catch (IOException var3) {
         return false;
      }
   }

   @Override
   public Set<String> a(abk var1) {
      ZipFile _snowman;
      try {
         _snowman = this.b();
      } catch (IOException var9) {
         return Collections.emptySet();
      }

      Enumeration<? extends ZipEntry> _snowmanx = _snowman.entries();
      Set<String> _snowmanxx = Sets.newHashSet();

      while (_snowmanx.hasMoreElements()) {
         ZipEntry _snowmanxxx = _snowmanx.nextElement();
         String _snowmanxxxx = _snowmanxxx.getName();
         if (_snowmanxxxx.startsWith(_snowman.a() + "/")) {
            List<String> _snowmanxxxxx = Lists.newArrayList(b.split(_snowmanxxxx));
            if (_snowmanxxxxx.size() > 1) {
               String _snowmanxxxxxx = _snowmanxxxxx.get(1);
               if (_snowmanxxxxxx.equals(_snowmanxxxxxx.toLowerCase(Locale.ROOT))) {
                  _snowmanxx.add(_snowmanxxxxxx);
               } else {
                  this.d(_snowmanxxxxxx);
               }
            }
         }
      }

      return _snowmanxx;
   }

   @Override
   protected void finalize() throws Throwable {
      this.close();
      super.finalize();
   }

   @Override
   public void close() {
      if (this.c != null) {
         IOUtils.closeQuietly(this.c);
         this.c = null;
      }
   }

   @Override
   public Collection<vk> a(abk var1, String var2, String var3, int var4, Predicate<String> var5) {
      ZipFile _snowman;
      try {
         _snowman = this.b();
      } catch (IOException var15) {
         return Collections.emptySet();
      }

      Enumeration<? extends ZipEntry> _snowmanx = _snowman.entries();
      List<vk> _snowmanxx = Lists.newArrayList();
      String _snowmanxxx = _snowman.a() + "/" + _snowman + "/";
      String _snowmanxxxx = _snowmanxxx + _snowman + "/";

      while (_snowmanx.hasMoreElements()) {
         ZipEntry _snowmanxxxxx = _snowmanx.nextElement();
         if (!_snowmanxxxxx.isDirectory()) {
            String _snowmanxxxxxx = _snowmanxxxxx.getName();
            if (!_snowmanxxxxxx.endsWith(".mcmeta") && _snowmanxxxxxx.startsWith(_snowmanxxxx)) {
               String _snowmanxxxxxxx = _snowmanxxxxxx.substring(_snowmanxxx.length());
               String[] _snowmanxxxxxxxx = _snowmanxxxxxxx.split("/");
               if (_snowmanxxxxxxxx.length >= _snowman + 1 && _snowman.test(_snowmanxxxxxxxx[_snowmanxxxxxxxx.length - 1])) {
                  _snowmanxx.add(new vk(_snowman, _snowmanxxxxxxx));
               }
            }
         }
      }

      return _snowmanxx;
   }
}
