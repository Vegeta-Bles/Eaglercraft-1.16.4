import java.util.List;
import java.util.Random;

public class crk {
   private static final vk[] a = new vk[]{
      new vk("nether_fossils/fossil_1"),
      new vk("nether_fossils/fossil_2"),
      new vk("nether_fossils/fossil_3"),
      new vk("nether_fossils/fossil_4"),
      new vk("nether_fossils/fossil_5"),
      new vk("nether_fossils/fossil_6"),
      new vk("nether_fossils/fossil_7"),
      new vk("nether_fossils/fossil_8"),
      new vk("nether_fossils/fossil_9"),
      new vk("nether_fossils/fossil_10"),
      new vk("nether_fossils/fossil_11"),
      new vk("nether_fossils/fossil_12"),
      new vk("nether_fossils/fossil_13"),
      new vk("nether_fossils/fossil_14")
   };

   public static void a(csw var0, List<cru> var1, Random var2, fx var3) {
      bzm _snowman = bzm.a(_snowman);
      _snowman.add(new crk.a(_snowman, x.a(a, _snowman), _snowman, _snowman));
   }

   public static class a extends crx {
      private final vk d;
      private final bzm e;

      public a(csw var1, vk var2, fx var3, bzm var4) {
         super(clb.ac, 0);
         this.d = _snowman;
         this.c = _snowman;
         this.e = _snowman;
         this.a(_snowman);
      }

      public a(csw var1, md var2) {
         super(clb.ac, _snowman);
         this.d = new vk(_snowman.l("Template"));
         this.e = bzm.valueOf(_snowman.l("Rot"));
         this.a(_snowman);
      }

      private void a(csw var1) {
         ctb _snowman = _snowman.a(this.d);
         csx _snowmanx = new csx().a(this.e).a(byg.a).a(cse.d);
         this.a(_snowman, this.c, _snowmanx);
      }

      @Override
      protected void a(md var1) {
         super.a(_snowman);
         _snowman.a("Template", this.d.toString());
         _snowman.a("Rot", this.e.name());
      }

      @Override
      protected void a(String var1, fx var2, bsk var3, Random var4, cra var5) {
      }

      @Override
      public boolean a(bsr var1, bsn var2, cfy var3, Random var4, cra var5, brd var6, fx var7) {
         _snowman.c(this.a.b(this.b, this.c));
         return super.a(_snowman, _snowman, _snowman, _snowman, _snowman, _snowman, _snowman);
      }
   }
}
