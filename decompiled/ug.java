import com.mojang.authlib.GameProfile;
import java.io.IOException;

public class ug implements oj<ue> {
   private GameProfile a;

   public ug() {
   }

   public ug(GameProfile var1) {
      this.a = _snowman;
   }

   @Override
   public void a(nf var1) throws IOException {
      this.a = new GameProfile(null, _snowman.e(16));
   }

   @Override
   public void b(nf var1) throws IOException {
      _snowman.a(this.a.getName());
   }

   public void a(ue var1) {
      _snowman.a(this);
   }

   public GameProfile b() {
      return this.a;
   }
}
