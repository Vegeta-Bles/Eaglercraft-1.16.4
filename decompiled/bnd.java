import java.util.Map;
import javax.annotation.Nullable;

public class bnd extends bkh {
   protected final buo a;

   public bnd(buo var1, buo var2, blx.a var3) {
      super(_snowman, _snowman);
      this.a = _snowman;
   }

   @Nullable
   @Override
   protected ceh c(bny var1) {
      ceh _snowman = this.a.a(_snowman);
      ceh _snowmanx = null;
      brz _snowmanxx = _snowman.p();
      fx _snowmanxxx = _snowman.a();

      for (gc _snowmanxxxx : _snowman.e()) {
         if (_snowmanxxxx != gc.b) {
            ceh _snowmanxxxxx = _snowmanxxxx == gc.a ? this.e().a(_snowman) : _snowman;
            if (_snowmanxxxxx != null && _snowmanxxxxx.a(_snowmanxx, _snowmanxxx)) {
               _snowmanx = _snowmanxxxxx;
               break;
            }
         }
      }

      return _snowmanx != null && _snowmanxx.a(_snowmanx, _snowmanxxx, dcs.a()) ? _snowmanx : null;
   }

   @Override
   public void a(Map<buo, blx> var1, blx var2) {
      super.a(_snowman, _snowman);
      _snowman.put(this.a, _snowman);
   }
}
