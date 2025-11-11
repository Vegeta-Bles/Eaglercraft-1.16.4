import java.io.IOException;

public class pk implements oj<om> {
   public static final vk a = new vk("brand");
   public static final vk b = new vk("debug/path");
   public static final vk c = new vk("debug/neighbors_update");
   public static final vk d = new vk("debug/caves");
   public static final vk e = new vk("debug/structures");
   public static final vk f = new vk("debug/worldgen_attempt");
   public static final vk g = new vk("debug/poi_ticket_count");
   public static final vk h = new vk("debug/poi_added");
   public static final vk i = new vk("debug/poi_removed");
   public static final vk j = new vk("debug/village_sections");
   public static final vk k = new vk("debug/goal_selector");
   public static final vk l = new vk("debug/brain");
   public static final vk m = new vk("debug/bee");
   public static final vk n = new vk("debug/hive");
   public static final vk o = new vk("debug/game_test_add_marker");
   public static final vk p = new vk("debug/game_test_clear");
   public static final vk q = new vk("debug/raids");
   private vk r;
   private nf s;

   public pk() {
   }

   public pk(vk var1, nf var2) {
      this.r = _snowman;
      this.s = _snowman;
      if (_snowman.writerIndex() > 1048576) {
         throw new IllegalArgumentException("Payload may not be larger than 1048576 bytes");
      }
   }

   @Override
   public void a(nf var1) throws IOException {
      this.r = _snowman.p();
      int _snowman = _snowman.readableBytes();
      if (_snowman >= 0 && _snowman <= 1048576) {
         this.s = new nf(_snowman.readBytes(_snowman));
      } else {
         throw new IOException("Payload may not be larger than 1048576 bytes");
      }
   }

   @Override
   public void b(nf var1) throws IOException {
      _snowman.a(this.r);
      _snowman.writeBytes(this.s.copy());
   }

   public void a(om var1) {
      _snowman.a(this);
   }

   public vk b() {
      return this.r;
   }

   public nf c() {
      return new nf(this.s.copy());
   }
}
