import com.mojang.blaze3d.systems.RenderSystem;

public class dfo {
   private final dfh a;
   private static final dfo b = new dfo();

   public static dfo a() {
      RenderSystem.assertThread(RenderSystem::isOnGameThreadOrInit);
      return b;
   }

   public dfo(int var1) {
      this.a = new dfh(_snowman);
   }

   public dfo() {
      this(2097152);
   }

   public void b() {
      this.a.c();
      dfi.a(this.a);
   }

   public dfh c() {
      return this.a;
   }
}
