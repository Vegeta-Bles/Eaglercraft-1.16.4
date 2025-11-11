import net.minecraft.server.MinecraftServer;

public class dcc implements dcd<MinecraftServer> {
   private final vk a;

   public dcc(vk var1) {
      this.a = _snowman;
   }

   public void a(MinecraftServer var1, dcf<MinecraftServer> var2, long var3) {
      vx _snowman = _snowman.aB();
      ael<cy> _snowmanx = _snowman.b(this.a);

      for (cy _snowmanxx : _snowmanx.b()) {
         _snowman.a(_snowmanxx, _snowman.e());
      }
   }

   public static class a extends dcd.a<MinecraftServer, dcc> {
      public a() {
         super(new vk("function_tag"), dcc.class);
      }

      public void a(md var1, dcc var2) {
         _snowman.a("Name", _snowman.a.toString());
      }

      public dcc a(md var1) {
         vk _snowman = new vk(_snowman.l("Name"));
         return new dcc(_snowman);
      }
   }
}
