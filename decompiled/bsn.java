import com.mojang.datafixers.DataFixUtils;
import java.util.stream.Stream;
import javax.annotation.Nullable;

public class bsn {
   private final bry a;
   private final chw b;

   public bsn(bry var1, chw var2) {
      this.a = _snowman;
      this.b = _snowman;
   }

   public bsn a(aam var1) {
      if (_snowman.E() != this.a) {
         throw new IllegalStateException("Using invalid feature manager (source level: " + _snowman.E() + ", region: " + _snowman);
      } else {
         return new bsn(_snowman, this.b);
      }
   }

   public Stream<? extends crv<?>> a(gp var1, cla<?> var2) {
      return this.a
         .a(_snowman.a(), _snowman.c(), cga.c)
         .b(_snowman)
         .stream()
         .map(var0 -> gp.a(new brd(var0), 0))
         .map(var2x -> this.a(var2x, _snowman, this.a.a(var2x.a(), var2x.c(), cga.b)))
         .filter(var0 -> var0 != null && var0.e());
   }

   @Nullable
   public crv<?> a(gp var1, cla<?> var2, cgd var3) {
      return _snowman.a(_snowman);
   }

   public void a(gp var1, cla<?> var2, crv<?> var3, cgd var4) {
      _snowman.a(_snowman, _snowman);
   }

   public void a(gp var1, cla<?> var2, long var3, cgd var5) {
      _snowman.a(_snowman, _snowman);
   }

   public boolean a() {
      return this.b.b();
   }

   public crv<?> a(fx var1, boolean var2, cla<?> var3) {
      return (crv<?>)DataFixUtils.orElse(
         this.a(gp.a(_snowman), _snowman).filter(var1x -> var1x.c().b(_snowman)).filter(var2x -> !_snowman || var2x.d().stream().anyMatch(var1x -> var1x.g().b(_snowman))).findFirst(), crv.a
      );
   }
}
