import java.util.UUID;
import net.minecraft.server.MinecraftServer;

public interface cym extends cyo {
   @Override
   String g();

   void a(boolean var1);

   int l();

   void f(int var1);

   void e(int var1);

   int j();

   @Override
   default void a(m var1) {
      cyo.super.a(_snowman);
      _snowman.a("Level name", this::g);
      _snowman.a("Level game mode", () -> String.format("Game mode: %s (ID %d). Hardcore: %b. Cheats: %b", this.m().b(), this.m().a(), this.n(), this.o()));
      _snowman.a("Level weather", () -> String.format("Rain time: %d (now: %b), thunder time: %d (now: %b)", this.l(), this.k(), this.j(), this.i()));
   }

   int h();

   void a(int var1);

   int v();

   void g(int var1);

   int w();

   void h(int var1);

   void a(UUID var1);

   bru m();

   void a(cfu.c var1);

   cfu.c r();

   boolean p();

   void c(boolean var1);

   boolean o();

   void a(bru var1);

   dcf<MinecraftServer> u();

   void a(long var1);

   void b(long var1);
}
