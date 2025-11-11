import com.mojang.authlib.GameProfile;
import java.io.IOException;
import java.util.UUID;

public class ua implements oj<ty> {
   private GameProfile a;

   public ua() {
   }

   public ua(GameProfile var1) {
      this.a = _snowman;
   }

   @Override
   public void a(nf var1) throws IOException {
      int[] _snowman = new int[4];

      for (int _snowmanx = 0; _snowmanx < _snowman.length; _snowmanx++) {
         _snowman[_snowmanx] = _snowman.readInt();
      }

      UUID _snowmanx = gq.a(_snowman);
      String _snowmanxx = _snowman.e(16);
      this.a = new GameProfile(_snowmanx, _snowmanxx);
   }

   @Override
   public void b(nf var1) throws IOException {
      for (int _snowman : gq.a(this.a.getId())) {
         _snowman.writeInt(_snowman);
      }

      _snowman.a(this.a.getName());
   }

   public void a(ty var1) {
      _snowman.a(this);
   }

   public GameProfile b() {
      return this.a;
   }
}
