import com.mojang.authlib.GameProfile;
import org.apache.commons.lang3.StringUtils;

public class bmm extends bnd {
   public bmm(buo var1, buo var2, blx.a var3) {
      super(_snowman, _snowman, _snowman);
   }

   @Override
   public nr h(bmb var1) {
      if (_snowman.b() == bmd.pg && _snowman.n()) {
         String _snowman = null;
         md _snowmanx = _snowman.o();
         if (_snowmanx.c("SkullOwner", 8)) {
            _snowman = _snowmanx.l("SkullOwner");
         } else if (_snowmanx.c("SkullOwner", 10)) {
            md _snowmanxx = _snowmanx.p("SkullOwner");
            if (_snowmanxx.c("Name", 8)) {
               _snowman = _snowmanxx.l("Name");
            }
         }

         if (_snowman != null) {
            return new of(this.a() + ".named", _snowman);
         }
      }

      return super.h(_snowman);
   }

   @Override
   public boolean b(md var1) {
      super.b(_snowman);
      if (_snowman.c("SkullOwner", 8) && !StringUtils.isBlank(_snowman.l("SkullOwner"))) {
         GameProfile _snowman = new GameProfile(null, _snowman.l("SkullOwner"));
         _snowman = cdg.b(_snowman);
         _snowman.a("SkullOwner", mp.a(new md(), _snowman));
         return true;
      } else {
         return false;
      }
   }
}
