import java.util.Locale;
import javax.annotation.Nullable;

public class baw extends azy {
   private static final us<Integer> c = uv.a(baw.class, uu.b);
   private static final vk[] d = new vk[]{new vk("textures/entity/fish/tropical_a.png"), new vk("textures/entity/fish/tropical_b.png")};
   private static final vk[] bo = new vk[]{
      new vk("textures/entity/fish/tropical_a_pattern_1.png"),
      new vk("textures/entity/fish/tropical_a_pattern_2.png"),
      new vk("textures/entity/fish/tropical_a_pattern_3.png"),
      new vk("textures/entity/fish/tropical_a_pattern_4.png"),
      new vk("textures/entity/fish/tropical_a_pattern_5.png"),
      new vk("textures/entity/fish/tropical_a_pattern_6.png")
   };
   private static final vk[] bp = new vk[]{
      new vk("textures/entity/fish/tropical_b_pattern_1.png"),
      new vk("textures/entity/fish/tropical_b_pattern_2.png"),
      new vk("textures/entity/fish/tropical_b_pattern_3.png"),
      new vk("textures/entity/fish/tropical_b_pattern_4.png"),
      new vk("textures/entity/fish/tropical_b_pattern_5.png"),
      new vk("textures/entity/fish/tropical_b_pattern_6.png")
   };
   public static final int[] b = new int[]{
      a(baw.a.h, bkx.b, bkx.h),
      a(baw.a.g, bkx.h, bkx.h),
      a(baw.a.g, bkx.h, bkx.l),
      a(baw.a.l, bkx.a, bkx.h),
      a(baw.a.b, bkx.l, bkx.h),
      a(baw.a.a, bkx.b, bkx.a),
      a(baw.a.f, bkx.g, bkx.d),
      a(baw.a.j, bkx.k, bkx.e),
      a(baw.a.l, bkx.a, bkx.o),
      a(baw.a.f, bkx.a, bkx.e),
      a(baw.a.i, bkx.a, bkx.h),
      a(baw.a.l, bkx.a, bkx.b),
      a(baw.a.d, bkx.j, bkx.g),
      a(baw.a.e, bkx.f, bkx.d),
      a(baw.a.k, bkx.o, bkx.a),
      a(baw.a.c, bkx.h, bkx.o),
      a(baw.a.j, bkx.o, bkx.a),
      a(baw.a.g, bkx.a, bkx.e),
      a(baw.a.a, bkx.o, bkx.a),
      a(baw.a.b, bkx.h, bkx.a),
      a(baw.a.d, bkx.j, bkx.e),
      a(baw.a.g, bkx.e, bkx.e)
   };
   private boolean bq = true;

   private static int a(baw.a var0, bkx var1, bkx var2) {
      return _snowman.a() & 0xFF | (_snowman.b() & 0xFF) << 8 | (_snowman.b() & 0xFF) << 16 | (_snowman.b() & 0xFF) << 24;
   }

   public baw(aqe<? extends baw> var1, brx var2) {
      super(_snowman, _snowman);
   }

   public static String b(int var0) {
      return "entity.minecraft.tropical_fish.predefined." + _snowman;
   }

   public static bkx s(int var0) {
      return bkx.a(x(_snowman));
   }

   public static bkx t(int var0) {
      return bkx.a(y(_snowman));
   }

   public static String u(int var0) {
      int _snowman = w(_snowman);
      int _snowmanx = z(_snowman);
      return "entity.minecraft.tropical_fish.type." + baw.a.a(_snowman, _snowmanx);
   }

   @Override
   protected void e() {
      super.e();
      this.R.a(c, 0);
   }

   @Override
   public void b(md var1) {
      super.b(_snowman);
      _snowman.b("Variant", this.eU());
   }

   @Override
   public void a(md var1) {
      super.a(_snowman);
      this.v(_snowman.h("Variant"));
   }

   public void v(int var1) {
      this.R.b(c, _snowman);
   }

   @Override
   public boolean c(int var1) {
      return !this.bq;
   }

   public int eU() {
      return this.R.a(c);
   }

   @Override
   protected void k(bmb var1) {
      super.k(_snowman);
      md _snowman = _snowman.p();
      _snowman.b("BucketVariantTag", this.eU());
   }

   @Override
   protected bmb eK() {
      return new bmb(bmd.lX);
   }

   @Override
   protected adp I() {
      return adq.pp;
   }

   @Override
   protected adp dq() {
      return adq.pq;
   }

   @Override
   protected adp e(apk var1) {
      return adq.ps;
   }

   @Override
   protected adp eM() {
      return adq.pr;
   }

   private static int x(int var0) {
      return (_snowman & 0xFF0000) >> 16;
   }

   public float[] eV() {
      return bkx.a(x(this.eU())).e();
   }

   private static int y(int var0) {
      return (_snowman & 0xFF000000) >> 24;
   }

   public float[] eW() {
      return bkx.a(y(this.eU())).e();
   }

   public static int w(int var0) {
      return Math.min(_snowman & 0xFF, 1);
   }

   public int eX() {
      return w(this.eU());
   }

   private static int z(int var0) {
      return Math.min((_snowman & 0xFF00) >> 8, 5);
   }

   public vk eY() {
      return w(this.eU()) == 0 ? bo[z(this.eU())] : bp[z(this.eU())];
   }

   public vk eZ() {
      return d[w(this.eU())];
   }

   @Nullable
   @Override
   public arc a(bsk var1, aos var2, aqp var3, @Nullable arc var4, @Nullable md var5) {
      _snowman = super.a(_snowman, _snowman, _snowman, _snowman, _snowman);
      if (_snowman != null && _snowman.c("BucketVariantTag", 3)) {
         this.v(_snowman.h("BucketVariantTag"));
         return _snowman;
      } else {
         int _snowman;
         int _snowmanx;
         int _snowmanxx;
         int _snowmanxxx;
         if (_snowman instanceof baw.b) {
            baw.b _snowmanxxxx = (baw.b)_snowman;
            _snowman = _snowmanxxxx.b;
            _snowmanx = _snowmanxxxx.c;
            _snowmanxx = _snowmanxxxx.d;
            _snowmanxxx = _snowmanxxxx.e;
         } else if ((double)this.J.nextFloat() < 0.9) {
            int _snowmanxxxx = x.a(b, this.J);
            _snowman = _snowmanxxxx & 0xFF;
            _snowmanx = (_snowmanxxxx & 0xFF00) >> 8;
            _snowmanxx = (_snowmanxxxx & 0xFF0000) >> 16;
            _snowmanxxx = (_snowmanxxxx & 0xFF000000) >> 24;
            _snowman = new baw.b(this, _snowman, _snowmanx, _snowmanxx, _snowmanxxx);
         } else {
            this.bq = false;
            _snowman = this.J.nextInt(2);
            _snowmanx = this.J.nextInt(6);
            _snowmanxx = this.J.nextInt(15);
            _snowmanxxx = this.J.nextInt(15);
         }

         this.v(_snowman | _snowmanx << 8 | _snowmanxx << 16 | _snowmanxxx << 24);
         return _snowman;
      }
   }

   static enum a {
      a(0, 0),
      b(0, 1),
      c(0, 2),
      d(0, 3),
      e(0, 4),
      f(0, 5),
      g(1, 0),
      h(1, 1),
      i(1, 2),
      j(1, 3),
      k(1, 4),
      l(1, 5);

      private final int m;
      private final int n;
      private static final baw.a[] o = values();

      private a(int var3, int var4) {
         this.m = _snowman;
         this.n = _snowman;
      }

      public int a() {
         return this.m;
      }

      public int b() {
         return this.n;
      }

      public static String a(int var0, int var1) {
         return o[_snowman + 6 * _snowman].c();
      }

      public String c() {
         return this.name().toLowerCase(Locale.ROOT);
      }
   }

   static class b extends azy.a {
      private final int b;
      private final int c;
      private final int d;
      private final int e;

      private b(baw var1, int var2, int var3, int var4, int var5) {
         super(_snowman);
         this.b = _snowman;
         this.c = _snowman;
         this.d = _snowman;
         this.e = _snowman;
      }
   }
}
