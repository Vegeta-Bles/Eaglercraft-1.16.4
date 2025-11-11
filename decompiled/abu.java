import com.mojang.brigadier.arguments.StringArgumentType;
import java.io.IOException;
import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;
import javax.annotation.Nullable;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class abu implements AutoCloseable {
   private static final Logger a = LogManager.getLogger();
   private static final abo b = new abo(new of("resourcePack.broken_assets").a(new k[]{k.m, k.u}), w.a().getPackVersion());
   private final String c;
   private final Supplier<abj> d;
   private final nr e;
   private final nr f;
   private final abv g;
   private final abu.b h;
   private final boolean i;
   private final boolean j;
   private final abx k;

   @Nullable
   public static abu a(String var0, boolean var1, Supplier<abj> var2, abu.a var3, abu.b var4, abx var5) {
      try (abj _snowman = _snowman.get()) {
         abo _snowmanx = _snowman.a(abo.a);
         if (_snowman && _snowmanx == null) {
            a.error(
               "Broken/missing pack.mcmeta detected, fudging it into existance. Please check that your launcher has downloaded all assets for the game correctly!"
            );
            _snowmanx = b;
         }

         if (_snowmanx != null) {
            return _snowman.create(_snowman, _snowman, _snowman, _snowman, _snowmanx, _snowman, _snowman);
         }

         a.warn("Couldn't find pack meta for pack {}", _snowman);
      } catch (IOException var22) {
         a.warn("Couldn't get pack info for: {}", var22.toString());
      }

      return null;
   }

   public abu(String var1, boolean var2, Supplier<abj> var3, nr var4, nr var5, abv var6, abu.b var7, boolean var8, abx var9) {
      this.c = _snowman;
      this.d = _snowman;
      this.e = _snowman;
      this.f = _snowman;
      this.g = _snowman;
      this.i = _snowman;
      this.h = _snowman;
      this.j = _snowman;
      this.k = _snowman;
   }

   public abu(String var1, boolean var2, Supplier<abj> var3, abj var4, abo var5, abu.b var6, abx var7) {
      this(_snowman, _snowman, _snowman, new oe(_snowman.a()), _snowman.a(), abv.a(_snowman.b()), _snowman, false, _snowman);
   }

   public nr a() {
      return this.e;
   }

   public nr b() {
      return this.f;
   }

   public nr a(boolean var1) {
      return ns.a(this.k.decorate(new oe(this.c)))
         .a(var2 -> var2.a(_snowman ? k.k : k.m).a(StringArgumentType.escapeIfRequired(this.c)).a(new nv(nv.a.a, new oe("").a(this.e).c("\n").a(this.f))));
   }

   public abv c() {
      return this.g;
   }

   public abj d() {
      return this.d.get();
   }

   public String e() {
      return this.c;
   }

   public boolean f() {
      return this.i;
   }

   public boolean g() {
      return this.j;
   }

   public abu.b h() {
      return this.h;
   }

   public abx i() {
      return this.k;
   }

   @Override
   public boolean equals(Object var1) {
      if (this == _snowman) {
         return true;
      } else if (!(_snowman instanceof abu)) {
         return false;
      } else {
         abu _snowman = (abu)_snowman;
         return this.c.equals(_snowman.c);
      }
   }

   @Override
   public int hashCode() {
      return this.c.hashCode();
   }

   @Override
   public void close() {
   }

   @FunctionalInterface
   public interface a {
      @Nullable
      abu create(String var1, boolean var2, Supplier<abj> var3, abj var4, abo var5, abu.b var6, abx var7);
   }

   public static enum b {
      a,
      b;

      private b() {
      }

      public <T> int a(List<T> var1, T var2, Function<T, abu> var3, boolean var4) {
         abu.b _snowman = _snowman ? this.a() : this;
         if (_snowman == b) {
            int _snowmanx;
            for (_snowmanx = 0; _snowmanx < _snowman.size(); _snowmanx++) {
               abu _snowmanxx = _snowman.apply(_snowman.get(_snowmanx));
               if (!_snowmanxx.g() || _snowmanxx.h() != this) {
                  break;
               }
            }

            _snowman.add(_snowmanx, _snowman);
            return _snowmanx;
         } else {
            int _snowmanx;
            for (_snowmanx = _snowman.size() - 1; _snowmanx >= 0; _snowmanx--) {
               abu _snowmanxx = _snowman.apply(_snowman.get(_snowmanx));
               if (!_snowmanxx.g() || _snowmanxx.h() != this) {
                  break;
               }
            }

            _snowman.add(_snowmanx + 1, _snowman);
            return _snowmanx + 1;
         }
      }

      public abu.b a() {
         return this == a ? b : a;
      }
   }
}
