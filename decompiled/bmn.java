import java.util.List;
import javax.annotation.Nullable;

public class bmn extends blx {
   public bmn(blx.a var1) {
      super(_snowman);
   }

   @Override
   public bmb r() {
      return bnv.a(super.r(), bnw.b);
   }

   @Override
   public bmb a(bmb var1, brx var2, aqm var3) {
      bfw _snowman = _snowman instanceof bfw ? (bfw)_snowman : null;
      if (_snowman instanceof aah) {
         ac.z.a((aah)_snowman, _snowman);
      }

      if (!_snowman.v) {
         for (apu _snowmanx : bnv.a(_snowman)) {
            if (_snowmanx.a().a()) {
               _snowmanx.a().a(_snowman, _snowman, _snowman, _snowmanx.c(), 1.0);
            } else {
               _snowman.c(new apu(_snowmanx));
            }
         }
      }

      if (_snowman != null) {
         _snowman.b(aea.c.b(this));
         if (!_snowman.bC.d) {
            _snowman.g(1);
         }
      }

      if (_snowman == null || !_snowman.bC.d) {
         if (_snowman.a()) {
            return new bmb(bmd.nw);
         }

         if (_snowman != null) {
            _snowman.bm.e(new bmb(bmd.nw));
         }
      }

      return _snowman;
   }

   @Override
   public int e_(bmb var1) {
      return 32;
   }

   @Override
   public bnn d_(bmb var1) {
      return bnn.c;
   }

   @Override
   public aov<bmb> a(brx var1, bfw var2, aot var3) {
      return bmc.a(_snowman, _snowman, _snowman);
   }

   @Override
   public String f(bmb var1) {
      return bnv.d(_snowman).b(this.a() + ".effect.");
   }

   @Override
   public void a(bmb var1, @Nullable brx var2, List<nr> var3, bnl var4) {
      bnv.a(_snowman, _snowman, 1.0F);
   }

   @Override
   public boolean e(bmb var1) {
      return super.e(_snowman) || !bnv.a(_snowman).isEmpty();
   }

   @Override
   public void a(bks var1, gj<bmb> var2) {
      if (this.a(_snowman)) {
         for (bnt _snowman : gm.U) {
            if (_snowman != bnw.a) {
               _snowman.add(bnv.a(new bmb(this), _snowman));
            }
         }
      }
   }
}
