import com.google.common.base.CharMatcher;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.function.Predicate;
import javax.annotation.Nullable;
import org.apache.commons.io.filefilter.DirectoryFileFilter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class abi extends abg {
   private static final Logger b = LogManager.getLogger();
   private static final boolean c = x.i() == x.b.c;
   private static final CharMatcher d = CharMatcher.is('\\');

   public abi(File var1) {
      super(_snowman);
   }

   public static boolean a(File var0, String var1) throws IOException {
      String _snowman = _snowman.getCanonicalPath();
      if (c) {
         _snowman = d.replaceFrom(_snowman, '/');
      }

      return _snowman.endsWith(_snowman);
   }

   @Override
   protected InputStream a(String var1) throws IOException {
      File _snowman = this.e(_snowman);
      if (_snowman == null) {
         throw new abl(this.a, _snowman);
      } else {
         return new FileInputStream(_snowman);
      }
   }

   @Override
   protected boolean c(String var1) {
      return this.e(_snowman) != null;
   }

   @Nullable
   private File e(String var1) {
      try {
         File _snowman = new File(this.a, _snowman);
         if (_snowman.isFile() && a(_snowman, _snowman)) {
            return _snowman;
         }
      } catch (IOException var3) {
      }

      return null;
   }

   @Override
   public Set<String> a(abk var1) {
      Set<String> _snowman = Sets.newHashSet();
      File _snowmanx = new File(this.a, _snowman.a());
      File[] _snowmanxx = _snowmanx.listFiles(DirectoryFileFilter.DIRECTORY);
      if (_snowmanxx != null) {
         for (File _snowmanxxx : _snowmanxx) {
            String _snowmanxxxx = a(_snowmanx, _snowmanxxx);
            if (_snowmanxxxx.equals(_snowmanxxxx.toLowerCase(Locale.ROOT))) {
               _snowman.add(_snowmanxxxx.substring(0, _snowmanxxxx.length() - 1));
            } else {
               this.d(_snowmanxxxx);
            }
         }
      }

      return _snowman;
   }

   @Override
   public void close() {
   }

   @Override
   public Collection<vk> a(abk var1, String var2, String var3, int var4, Predicate<String> var5) {
      File _snowman = new File(this.a, _snowman.a());
      List<vk> _snowmanx = Lists.newArrayList();
      this.a(new File(new File(_snowman, _snowman), _snowman), _snowman, _snowman, _snowmanx, _snowman + "/", _snowman);
      return _snowmanx;
   }

   private void a(File var1, int var2, String var3, List<vk> var4, String var5, Predicate<String> var6) {
      File[] _snowman = _snowman.listFiles();
      if (_snowman != null) {
         for (File _snowmanx : _snowman) {
            if (_snowmanx.isDirectory()) {
               if (_snowman > 0) {
                  this.a(_snowmanx, _snowman - 1, _snowman, _snowman, _snowman + _snowmanx.getName() + "/", _snowman);
               }
            } else if (!_snowmanx.getName().endsWith(".mcmeta") && _snowman.test(_snowmanx.getName())) {
               try {
                  _snowman.add(new vk(_snowman, _snowman + _snowmanx.getName()));
               } catch (v var13) {
                  b.error(var13.getMessage());
               }
            }
         }
      }
   }
}
