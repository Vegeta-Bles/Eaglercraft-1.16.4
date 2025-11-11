import net.minecraft.server.MinecraftServer;

public class abb implements uo {
   private static final nr a = new of("multiplayer.status.request_handled");
   private final MinecraftServer b;
   private final nd c;
   private boolean d;

   public abb(MinecraftServer var1, nd var2) {
      this.b = _snowman;
      this.c = _snowman;
   }

   @Override
   public void a(nr var1) {
   }

   @Override
   public nd a() {
      return this.c;
   }

   @Override
   public void a(uq var1) {
      if (this.d) {
         this.c.a(a);
      } else {
         this.d = true;
         this.c.a(new um(this.b.as()));
      }
   }

   @Override
   public void a(up var1) {
      this.c.a(new ul(_snowman.b()));
      this.c.a(a);
   }
}
