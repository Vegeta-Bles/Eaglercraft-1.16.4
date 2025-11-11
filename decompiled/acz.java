import com.google.gson.JsonObject;
import com.mojang.authlib.GameProfile;
import java.io.File;

public class acz extends acy<GameProfile, ada> {
   public acz(File var1) {
      super(_snowman);
   }

   @Override
   protected acx<GameProfile> a(JsonObject var1) {
      return new ada(_snowman);
   }

   public boolean a(GameProfile var1) {
      return this.d(_snowman);
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

   protected String b(GameProfile var1) {
      return _snowman.getId().toString();
   }
}
