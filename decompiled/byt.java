import com.mojang.authlib.GameProfile;
import javax.annotation.Nullable;
import org.apache.commons.lang3.StringUtils;

public class byt extends bzv {
   protected byt(ceg.c var1) {
      super(bzv.b.c, _snowman);
   }

   @Override
   public void a(brx var1, fx var2, ceh var3, @Nullable aqm var4, bmb var5) {
      super.a(_snowman, _snowman, _snowman, _snowman, _snowman);
      ccj _snowman = _snowman.c(_snowman);
      if (_snowman instanceof cdg) {
         cdg _snowmanx = (cdg)_snowman;
         GameProfile _snowmanxx = null;
         if (_snowman.n()) {
            md _snowmanxxx = _snowman.o();
            if (_snowmanxxx.c("SkullOwner", 10)) {
               _snowmanxx = mp.a(_snowmanxxx.p("SkullOwner"));
            } else if (_snowmanxxx.c("SkullOwner", 8) && !StringUtils.isBlank(_snowmanxxx.l("SkullOwner"))) {
               _snowmanxx = new GameProfile(null, _snowmanxxx.l("SkullOwner"));
            }
         }

         _snowmanx.a(_snowmanxx);
      }
   }
}
