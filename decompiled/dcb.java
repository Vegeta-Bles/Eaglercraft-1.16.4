import net.minecraft.server.MinecraftServer;

public class dcb implements dcd<MinecraftServer> {
   private final vk a;

   public dcb(vk var1) {
      this.a = _snowman;
   }

   public void a(MinecraftServer var1, dcf<MinecraftServer> var2, long var3) {
      vx _snowman = _snowman.aB();
      _snowman.a(this.a).ifPresent(var1x -> _snowman.a(var1x, _snowman.e()));
   }

   public static class a extends dcd.a<MinecraftServer, dcb> {
      public a() {
         super(new vk("function"), dcb.class);
      }

      public void a(md var1, dcb var2) {
         _snowman.a("Name", _snowman.a.toString());
      }

      public dcb a(md var1) {
         vk _snowman = new vk(_snowman.l("Name"));
         return new dcb(_snowman);
      }
   }
}
