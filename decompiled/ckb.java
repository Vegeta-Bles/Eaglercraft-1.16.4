import com.mojang.serialization.Codec;
import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

public class ckb extends cla<cme> {
   public ckb(Codec<cme> var1) {
      super(_snowman);
   }

   protected boolean a(cfy var1, bsy var2, long var3, chx var5, int var6, int var7, bsv var8, brd var9, cme var10) {
      _snowman.c(_snowman, _snowman, _snowman);
      double _snowman = (double)_snowman.b;
      return _snowman.nextDouble() < _snowman;
   }

   @Override
   public cla.a<cme> a() {
      return ckb.a::new;
   }

   public static class a extends crv<cme> {
      public a(cla<cme> var1, int var2, int var3, cra var4, int var5, long var6) {
         super(_snowman, _snowman, _snowman, _snowman, _snowman, _snowman);
      }

      public void a(gn var1, cfy var2, csw var3, int var4, int var5, bsv var6, cme var7) {
         crh.d _snowman = new crh.d(0, this.d, (_snowman << 4) + 2, (_snowman << 4) + 2, _snowman.c);
         this.b.add(_snowman);
         _snowman.a(_snowman, this.b, this.d);
         this.b();
         if (_snowman.c == ckb.b.b) {
            int _snowmanx = -5;
            int _snowmanxx = _snowman.f() - this.c.e + this.c.e() / 2 - -5;
            this.c.a(0, _snowmanxx, 0);

            for (cru _snowmanxxx : this.b) {
               _snowmanxxx.a(0, _snowmanxx, 0);
            }
         } else {
            this.a(_snowman.f(), this.d, 10);
         }
      }
   }

   public static enum b implements afs {
      a("normal"),
      b("mesa");

      public static final Codec<ckb.b> c = afs.a(ckb.b::values, ckb.b::a);
      private static final Map<String, ckb.b> d = Arrays.stream(values()).collect(Collectors.toMap(ckb.b::b, var0 -> (ckb.b)var0));
      private final String e;

      private b(String var3) {
         this.e = _snowman;
      }

      public String b() {
         return this.e;
      }

      private static ckb.b a(String var0) {
         return d.get(_snowman);
      }

      public static ckb.b a(int var0) {
         return _snowman >= 0 && _snowman < values().length ? values()[_snowman] : a;
      }

      @Override
      public String a() {
         return this.e;
      }
   }
}
