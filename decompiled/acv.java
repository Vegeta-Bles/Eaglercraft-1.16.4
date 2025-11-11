import com.google.gson.JsonObject;
import com.mojang.authlib.GameProfile;
import java.io.File;

public class acv extends acy<GameProfile, acw> {
   public acv(File var1) {
      super(_snowman);
   }

   @Override
   protected acx<GameProfile> a(JsonObject var1) {
      return new acw(_snowman);
   }

   @Override
   public String[] a() {
      String[] _snowman = new String[this.d().size()];
      int _snowmanx = 0;

      for (acx<GameProfile> _snowmanxx : this.d()) {
         _snowman[_snowmanx++] = _snowmanxx.g().getName();
      }

      return _snowman;
   }

   public boolean b(GameProfile var1) {
      acw _snowman = this.b(_snowman);
      return _snowman != null ? _snowman.b() : false;
   }

   protected String c(GameProfile var1) {
      return _snowman.getId().toString();
   }
}
