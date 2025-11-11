import com.google.common.collect.Maps;
import java.util.List;
import java.util.Map;
import javax.annotation.Nullable;

public class bmq extends blx {
   private static final Map<adp, bmq> a = Maps.newHashMap();
   private final int b;
   private final adp c;

   protected bmq(int var1, adp var2, blx.a var3) {
      super(_snowman);
      this.b = _snowman;
      this.c = _snowman;
      a.put(this.c, this);
   }

   @Override
   public aou a(boa var1) {
      brx _snowman = _snowman.p();
      fx _snowmanx = _snowman.a();
      ceh _snowmanxx = _snowman.d_(_snowmanx);
      if (_snowmanxx.a(bup.cI) && !_snowmanxx.c(bxs.a)) {
         bmb _snowmanxxx = _snowman.m();
         if (!_snowman.v) {
            ((bxs)bup.cI).a(_snowman, _snowmanx, _snowmanxx, _snowmanxxx);
            _snowman.a(null, 1010, _snowmanx, blx.a(this));
            _snowmanxxx.g(1);
            bfw _snowmanxxxx = _snowman.n();
            if (_snowmanxxxx != null) {
               _snowmanxxxx.a(aea.ak);
            }
         }

         return aou.a(_snowman.v);
      } else {
         return aou.c;
      }
   }

   public int f() {
      return this.b;
   }

   @Override
   public void a(bmb var1, @Nullable brx var2, List<nr> var3, bnl var4) {
      _snowman.add(this.g().a(k.h));
   }

   public nx g() {
      return new of(this.a() + ".desc");
   }

   @Nullable
   public static bmq a(adp var0) {
      return a.get(_snowman);
   }

   public adp v() {
      return this.c;
   }
}
