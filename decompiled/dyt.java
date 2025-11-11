import java.util.Random;

public class dyt extends dzb {
   private static final Random a = new Random();
   private final dyw b;

   private dyt(dwt var1, double var2, double var4, double var6, double var8, double var10, double var12, dyw var14) {
      super(_snowman, _snowman, _snowman, _snowman, 0.5 - a.nextDouble(), _snowman, 0.5 - a.nextDouble());
      this.b = _snowman;
      this.k *= 0.2F;
      if (_snowman == 0.0 && _snowman == 0.0) {
         this.j *= 0.1F;
         this.l *= 0.1F;
      }

      this.B *= 0.75F;
      this.t = (int)(8.0 / (Math.random() * 0.8 + 0.2));
      this.n = false;
      this.b(_snowman);
   }

   @Override
   public dyk b() {
      return dyk.c;
   }

   @Override
   public void a() {
      this.d = this.g;
      this.e = this.h;
      this.f = this.i;
      if (this.s++ >= this.t) {
         this.j();
      } else {
         this.b(this.b);
         this.k += 0.004;
         this.a(this.j, this.k, this.l);
         if (this.h == this.e) {
            this.j *= 1.1;
            this.l *= 1.1;
         }

         this.j *= 0.96F;
         this.k *= 0.96F;
         this.l *= 0.96F;
         if (this.m) {
            this.j *= 0.7F;
            this.l *= 0.7F;
         }
      }
   }

   public static class a implements dyj<hi> {
      private final dyw a;

      public a(dyw var1) {
         this.a = _snowman;
      }

      public dyg a(hi var1, dwt var2, double var3, double var5, double var7, double var9, double var11, double var13) {
         dyg _snowman = new dyt(_snowman, _snowman, _snowman, _snowman, _snowman, _snowman, _snowman, this.a);
         _snowman.e(0.15F);
         _snowman.a((float)_snowman, (float)_snowman, (float)_snowman);
         return _snowman;
      }
   }

   public static class b implements dyj<hi> {
      private final dyw a;

      public b(dyw var1) {
         this.a = _snowman;
      }

      public dyg a(hi var1, dwt var2, double var3, double var5, double var7, double var9, double var11, double var13) {
         return new dyt(_snowman, _snowman, _snowman, _snowman, _snowman, _snowman, _snowman, this.a);
      }
   }

   public static class c implements dyj<hi> {
      private final dyw a;

      public c(dyw var1) {
         this.a = _snowman;
      }

      public dyg a(hi var1, dwt var2, double var3, double var5, double var7, double var9, double var11, double var13) {
         dyg _snowman = new dyt(_snowman, _snowman, _snowman, _snowman, _snowman, _snowman, _snowman, this.a);
         _snowman.a((float)_snowman, (float)_snowman, (float)_snowman);
         return _snowman;
      }
   }

   public static class d implements dyj<hi> {
      private final dyw a;

      public d(dyw var1) {
         this.a = _snowman;
      }

      public dyg a(hi var1, dwt var2, double var3, double var5, double var7, double var9, double var11, double var13) {
         return new dyt(_snowman, _snowman, _snowman, _snowman, _snowman, _snowman, _snowman, this.a);
      }
   }

   public static class e implements dyj<hi> {
      private final dyw a;

      public e(dyw var1) {
         this.a = _snowman;
      }

      public dyg a(hi var1, dwt var2, double var3, double var5, double var7, double var9, double var11, double var13) {
         dyt _snowman = new dyt(_snowman, _snowman, _snowman, _snowman, _snowman, _snowman, _snowman, this.a);
         float _snowmanx = _snowman.t.nextFloat() * 0.5F + 0.35F;
         _snowman.a(1.0F * _snowmanx, 0.0F * _snowmanx, 1.0F * _snowmanx);
         return _snowman;
      }
   }
}
