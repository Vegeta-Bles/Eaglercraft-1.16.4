import com.google.common.collect.Maps;
import com.mojang.serialization.Codec;
import java.util.Map;

public class csc extends csy {
   public static final Codec<csc> a = Codec.unit(() -> csc.b);
   public static final csc b = new csc();
   private final Map<buo, buo> c = x.a(Maps.newHashMap(), var0 -> {
      var0.put(bup.m, bup.np);
      var0.put(bup.bJ, bup.np);
      var0.put(bup.b, bup.nt);
      var0.put(bup.du, bup.nu);
      var0.put(bup.dv, bup.nu);
      var0.put(bup.ci, bup.nq);
      var0.put(bup.lh, bup.nq);
      var0.put(bup.lj, bup.nB);
      var0.put(bup.dS, bup.ny);
      var0.put(bup.lf, bup.ny);
      var0.put(bup.hV, bup.ns);
      var0.put(bup.lv, bup.ns);
      var0.put(bup.hR, bup.nC);
      var0.put(bup.hQ, bup.nC);
      var0.put(bup.hX, bup.nx);
      var0.put(bup.lt, bup.nx);
      var0.put(bup.lJ, bup.nz);
      var0.put(bup.lH, bup.nz);
      var0.put(bup.et, bup.nr);
      var0.put(bup.eu, bup.nr);
      var0.put(bup.dx, bup.nw);
      var0.put(bup.dw, bup.nv);
      var0.put(bup.dH, bup.dI);
   });

   private csc() {
   }

   @Override
   public ctb.c a(brz var1, fx var2, fx var3, ctb.c var4, ctb.c var5, csx var6) {
      buo _snowman = this.c.get(_snowman.b.b());
      if (_snowman == null) {
         return _snowman;
      } else {
         ceh _snowmanx = _snowman.b;
         ceh _snowmanxx = _snowman.n();
         if (_snowmanx.b(cak.a)) {
            _snowmanxx = _snowmanxx.a(cak.a, _snowmanx.c(cak.a));
         }

         if (_snowmanx.b(cak.b)) {
            _snowmanxx = _snowmanxx.a(cak.b, _snowmanx.c(cak.b));
         }

         if (_snowmanx.b(bzw.a)) {
            _snowmanxx = _snowmanxx.a(bzw.a, _snowmanx.c(bzw.a));
         }

         return new ctb.c(_snowman.a, _snowmanxx, _snowman.c);
      }
   }

   @Override
   protected cta<?> a() {
      return cta.h;
   }
}
