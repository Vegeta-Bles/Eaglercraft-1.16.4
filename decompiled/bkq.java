import java.util.Optional;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class bkq extends blx implements bno {
   private static final Logger a = LogManager.getLogger();

   public bkq(blx.a var1) {
      super(_snowman);
   }

   public static boolean d(bmb var0) {
      md _snowman = _snowman.o();
      return _snowman != null && (_snowman.e("LodestoneDimension") || _snowman.e("LodestonePos"));
   }

   @Override
   public boolean e(bmb var1) {
      return d(_snowman) || super.e(_snowman);
   }

   public static Optional<vj<brx>> a(md var0) {
      return brx.f.parse(mo.a, _snowman.c("LodestoneDimension")).result();
   }

   @Override
   public void a(bmb var1, brx var2, aqa var3, int var4, boolean var5) {
      if (!_snowman.v) {
         if (d(_snowman)) {
            md _snowman = _snowman.p();
            if (_snowman.e("LodestoneTracked") && !_snowman.q("LodestoneTracked")) {
               return;
            }

            Optional<vj<brx>> _snowmanx = a(_snowman);
            if (_snowmanx.isPresent() && _snowmanx.get() == _snowman.Y() && _snowman.e("LodestonePos") && !((aag)_snowman).y().a(azr.w, mp.b(_snowman.p("LodestonePos")))) {
               _snowman.r("LodestonePos");
            }
         }
      }
   }

   @Override
   public aou a(boa var1) {
      fx _snowman = _snowman.a();
      brx _snowmanx = _snowman.p();
      if (!_snowmanx.d_(_snowman).a(bup.no)) {
         return super.a(_snowman);
      } else {
         _snowmanx.a(null, _snowman, adq.hu, adr.h, 1.0F, 1.0F);
         bfw _snowmanxx = _snowman.n();
         bmb _snowmanxxx = _snowman.m();
         boolean _snowmanxxxx = !_snowmanxx.bC.d && _snowmanxxx.E() == 1;
         if (_snowmanxxxx) {
            this.a(_snowmanx.Y(), _snowman, _snowmanxxx.p());
         } else {
            bmb _snowmanxxxxx = new bmb(bmd.mh, 1);
            md _snowmanxxxxxx = _snowmanxxx.n() ? _snowmanxxx.o().g() : new md();
            _snowmanxxxxx.c(_snowmanxxxxxx);
            if (!_snowmanxx.bC.d) {
               _snowmanxxx.g(1);
            }

            this.a(_snowmanx.Y(), _snowman, _snowmanxxxxxx);
            if (!_snowmanxx.bm.e(_snowmanxxxxx)) {
               _snowmanxx.a(_snowmanxxxxx, false);
            }
         }

         return aou.a(_snowmanx.v);
      }
   }

   private void a(vj<brx> var1, fx var2, md var3) {
      _snowman.a("LodestonePos", mp.a(_snowman));
      brx.f.encodeStart(mo.a, _snowman).resultOrPartial(a::error).ifPresent(var1x -> _snowman.a("LodestoneDimension", var1x));
      _snowman.a("LodestoneTracked", true);
   }

   @Override
   public String f(bmb var1) {
      return d(_snowman) ? "item.minecraft.lodestone_compass" : super.f(_snowman);
   }
}
