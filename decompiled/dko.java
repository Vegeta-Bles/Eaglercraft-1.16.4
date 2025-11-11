import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Maps;
import java.util.Map;
import java.util.Set;
import javax.annotation.Nullable;

public class dko {
   private final gh<dkn> a = new gh<>(32);
   private final Map<buo, Set<cfj<?>>> b = Maps.newHashMap();

   public dko() {
   }

   public static dko a() {
      dko _snowman = new dko();
      _snowman.a((var0x, var1, var2, var3) -> var1 != null && var2 != null ? dzr.a(var1, var0x.c(bwd.a) == cfd.a ? var2.c() : var2) : -1, bup.gZ, bup.gY);
      _snowman.a(bwd.a, bup.gZ, bup.gY);
      _snowman.a((var0x, var1, var2, var3) -> var1 != null && var2 != null ? dzr.a(var1, var2) : brv.a(0.5, 1.0), bup.i, bup.aS, bup.aR, bup.eC);
      _snowman.a((var0x, var1, var2, var3) -> brr.a(), bup.ai);
      _snowman.a((var0x, var1, var2, var3) -> brr.b(), bup.aj);
      _snowman.a((var0x, var1, var2, var3) -> var1 != null && var2 != null ? dzr.b(var1, var2) : brr.c(), bup.ah, bup.ak, bup.al, bup.am, bup.dP);
      _snowman.a((var0x, var1, var2, var3) -> var1 != null && var2 != null ? dzr.c(var1, var2) : -1, bup.A, bup.lc, bup.eb);
      _snowman.a((var0x, var1, var2, var3) -> bzd.b(var0x.c(bzd.e)), bup.bS);
      _snowman.a(bzd.e, bup.bS);
      _snowman.a((var0x, var1, var2, var3) -> var1 != null && var2 != null ? dzr.a(var1, var2) : -1, bup.cH);
      _snowman.a((var0x, var1, var2, var3) -> 14731036, bup.dM, bup.dL);
      _snowman.a((var0x, var1, var2, var3) -> {
         int _snowmanx = var0x.c(cam.a);
         int _snowmanx = _snowmanx * 32;
         int _snowmanxx = 255 - _snowmanx * 8;
         int _snowmanxxx = _snowmanx * 4;
         return _snowmanx << 16 | _snowmanxx << 8 | _snowmanxxx;
      }, bup.dO, bup.dN);
      _snowman.a(cam.a, bup.dO, bup.dN);
      _snowman.a((var0x, var1, var2, var3) -> var1 != null && var2 != null ? 2129968 : 7455580, bup.dU);
      return _snowman;
   }

   public int a(ceh var1, brx var2, fx var3) {
      dkn _snowman = this.a.a(gm.Q.a(_snowman.b()));
      if (_snowman != null) {
         return _snowman.getColor(_snowman, null, null, 0);
      } else {
         cvb _snowmanx = _snowman.d(_snowman, _snowman);
         return _snowmanx != null ? _snowmanx.ai : -1;
      }
   }

   public int a(ceh var1, @Nullable bra var2, @Nullable fx var3, int var4) {
      dkn _snowman = this.a.a(gm.Q.a(_snowman.b()));
      return _snowman == null ? -1 : _snowman.getColor(_snowman, _snowman, _snowman, _snowman);
   }

   public void a(dkn var1, buo... var2) {
      for (buo _snowman : _snowman) {
         this.a.a(_snowman, gm.Q.a(_snowman));
      }
   }

   private void a(Set<cfj<?>> var1, buo... var2) {
      for (buo _snowman : _snowman) {
         this.b.put(_snowman, _snowman);
      }
   }

   private void a(cfj<?> var1, buo... var2) {
      this.a(ImmutableSet.of(_snowman), _snowman);
   }

   public Set<cfj<?>> a(buo var1) {
      return this.b.getOrDefault(_snowman, ImmutableSet.of());
   }
}
