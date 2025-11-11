import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Lists;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Stream;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class acb implements ach {
   private static final Logger b = LogManager.getLogger();
   protected final List<abj> a = Lists.newArrayList();
   private final abk c;
   private final String d;

   public acb(abk var1, String var2) {
      this.c = _snowman;
      this.d = _snowman;
   }

   public void a(abj var1) {
      this.a.add(_snowman);
   }

   @Override
   public Set<String> a() {
      return ImmutableSet.of(this.d);
   }

   @Override
   public acg a(vk var1) throws IOException {
      this.e(_snowman);
      abj _snowman = null;
      vk _snowmanx = d(_snowman);

      for (int _snowmanxx = this.a.size() - 1; _snowmanxx >= 0; _snowmanxx--) {
         abj _snowmanxxx = this.a.get(_snowmanxx);
         if (_snowman == null && _snowmanxxx.b(this.c, _snowmanx)) {
            _snowman = _snowmanxxx;
         }

         if (_snowmanxxx.b(this.c, _snowman)) {
            InputStream _snowmanxxxx = null;
            if (_snowman != null) {
               _snowmanxxxx = this.a(_snowmanx, _snowman);
            }

            return new acn(_snowmanxxx.a(), _snowman, this.a(_snowman, _snowmanxxx), _snowmanxxxx);
         }
      }

      throw new FileNotFoundException(_snowman.toString());
   }

   @Override
   public boolean b(vk var1) {
      if (!this.f(_snowman)) {
         return false;
      } else {
         for (int _snowman = this.a.size() - 1; _snowman >= 0; _snowman--) {
            abj _snowmanx = this.a.get(_snowman);
            if (_snowmanx.b(this.c, _snowman)) {
               return true;
            }
         }

         return false;
      }
   }

   protected InputStream a(vk var1, abj var2) throws IOException {
      InputStream _snowman = _snowman.a(this.c, _snowman);
      return (InputStream)(b.isDebugEnabled() ? new acb.a(_snowman, _snowman, _snowman.a()) : _snowman);
   }

   private void e(vk var1) throws IOException {
      if (!this.f(_snowman)) {
         throw new IOException("Invalid relative path to resource: " + _snowman);
      }
   }

   private boolean f(vk var1) {
      return !_snowman.a().contains("..");
   }

   @Override
   public List<acg> c(vk var1) throws IOException {
      this.e(_snowman);
      List<acg> _snowman = Lists.newArrayList();
      vk _snowmanx = d(_snowman);

      for (abj _snowmanxx : this.a) {
         if (_snowmanxx.b(this.c, _snowman)) {
            InputStream _snowmanxxx = _snowmanxx.b(this.c, _snowmanx) ? this.a(_snowmanx, _snowmanxx) : null;
            _snowman.add(new acn(_snowmanxx.a(), _snowman, this.a(_snowman, _snowmanxx), _snowmanxxx));
         }
      }

      if (_snowman.isEmpty()) {
         throw new FileNotFoundException(_snowman.toString());
      } else {
         return _snowman;
      }
   }

   @Override
   public Collection<vk> a(String var1, Predicate<String> var2) {
      List<vk> _snowman = Lists.newArrayList();

      for (abj _snowmanx : this.a) {
         _snowman.addAll(_snowmanx.a(this.c, this.d, _snowman, Integer.MAX_VALUE, _snowman));
      }

      Collections.sort(_snowman);
      return _snowman;
   }

   @Override
   public Stream<abj> b() {
      return this.a.stream();
   }

   static vk d(vk var0) {
      return new vk(_snowman.b(), _snowman.a() + ".mcmeta");
   }

   static class a extends FilterInputStream {
      private final String a;
      private boolean b;

      public a(InputStream var1, vk var2, String var3) {
         super(_snowman);
         ByteArrayOutputStream _snowman = new ByteArrayOutputStream();
         new Exception().printStackTrace(new PrintStream(_snowman));
         this.a = "Leaked resource: '" + _snowman + "' loaded from pack: '" + _snowman + "'\n" + _snowman;
      }

      @Override
      public void close() throws IOException {
         super.close();
         this.b = true;
      }

      @Override
      protected void finalize() throws Throwable {
         if (!this.b) {
            acb.b.warn(this.a);
         }

         super.finalize();
      }
   }
}
