import com.mojang.blaze3d.systems.RenderSystem;

public class dlr extends dlj {
   private final vk a;
   private final int b;
   private final int c;
   private final int d;
   private final int e;
   private final int v;

   public dlr(int var1, int var2, int var3, int var4, int var5, int var6, int var7, vk var8, dlj.a var9) {
      this(_snowman, _snowman, _snowman, _snowman, _snowman, _snowman, _snowman, _snowman, 256, 256, _snowman);
   }

   public dlr(int var1, int var2, int var3, int var4, int var5, int var6, int var7, vk var8, int var9, int var10, dlj.a var11) {
      this(_snowman, _snowman, _snowman, _snowman, _snowman, _snowman, _snowman, _snowman, _snowman, _snowman, _snowman, oe.d);
   }

   public dlr(int var1, int var2, int var3, int var4, int var5, int var6, int var7, vk var8, int var9, int var10, dlj.a var11, nr var12) {
      this(_snowman, _snowman, _snowman, _snowman, _snowman, _snowman, _snowman, _snowman, _snowman, _snowman, _snowman, s, _snowman);
   }

   public dlr(int var1, int var2, int var3, int var4, int var5, int var6, int var7, vk var8, int var9, int var10, dlj.a var11, dlj.b var12, nr var13) {
      super(_snowman, _snowman, _snowman, _snowman, _snowman, _snowman, _snowman);
      this.e = _snowman;
      this.v = _snowman;
      this.b = _snowman;
      this.c = _snowman;
      this.d = _snowman;
      this.a = _snowman;
   }

   public void a(int var1, int var2) {
      this.l = _snowman;
      this.m = _snowman;
   }

   @Override
   public void b(dfm var1, int var2, int var3, float var4) {
      djz _snowman = djz.C();
      _snowman.M().a(this.a);
      int _snowmanx = this.c;
      if (this.g()) {
         _snowmanx += this.d;
      }

      RenderSystem.enableDepthTest();
      a(_snowman, this.l, this.m, (float)this.b, (float)_snowmanx, this.j, this.k, this.e, this.v);
      if (this.g()) {
         this.a(_snowman, _snowman, _snowman);
      }
   }
}
