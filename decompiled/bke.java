import java.util.List;
import javax.annotation.Nullable;
import org.apache.commons.lang3.Validate;

public class bke extends bnd {
   public bke(buo var1, buo var2, blx.a var3) {
      super(_snowman, _snowman, _snowman);
      Validate.isInstanceOf(btm.class, _snowman);
      Validate.isInstanceOf(btm.class, _snowman);
   }

   public static void a(bmb var0, List<nr> var1) {
      md _snowman = _snowman.b("BlockEntityTag");
      if (_snowman != null && _snowman.e("Patterns")) {
         mj _snowmanx = _snowman.d("Patterns", 10);

         for (int _snowmanxx = 0; _snowmanxx < _snowmanx.size() && _snowmanxx < 6; _snowmanxx++) {
            md _snowmanxxx = _snowmanx.a(_snowmanxx);
            bkx _snowmanxxxx = bkx.a(_snowmanxxx.h("Color"));
            ccb _snowmanxxxxx = ccb.a(_snowmanxxx.l("Pattern"));
            if (_snowmanxxxxx != null) {
               _snowman.add(new of("block.minecraft.banner." + _snowmanxxxxx.a() + '.' + _snowmanxxxx.c()).a(k.h));
            }
         }
      }
   }

   public bkx b() {
      return ((btm)this.e()).b();
   }

   @Override
   public void a(bmb var1, @Nullable brx var2, List<nr> var3, bnl var4) {
      a(_snowman, _snowman);
   }
}
