import net.minecraft.server.MinecraftServer;

public class aaw implements tw {
   private final MinecraftServer a;
   private final nd b;

   public aaw(MinecraftServer var1, nd var2) {
      this.a = _snowman;
      this.b = _snowman;
   }

   @Override
   public void a(tv var1) {
      this.b.a(_snowman.b());
      this.b.a(new aba(this.a, this.b));
   }

   @Override
   public void a(nr var1) {
   }

   @Override
   public nd a() {
      return this.b;
   }
}
