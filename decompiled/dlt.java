import com.mojang.blaze3d.systems.RenderSystem;

public class dlt extends dlj {
   private boolean a;

   public dlt(int var1, int var2, dlj.a var3) {
      super(_snowman, _snowman, 20, 20, new of("narrator.button.difficulty_lock"), _snowman);
   }

   @Override
   protected nx c() {
      return super.c().c(". ").a(this.a() ? new of("narrator.button.difficulty_lock.locked") : new of("narrator.button.difficulty_lock.unlocked"));
   }

   public boolean a() {
      return this.a;
   }

   public void e(boolean var1) {
      this.a = _snowman;
   }

   @Override
   public void b(dfm var1, int var2, int var3, float var4) {
      djz.C().M().a(dlj.i);
      RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
      dlt.a _snowman;
      if (!this.o) {
         _snowman = this.a ? dlt.a.c : dlt.a.f;
      } else if (this.g()) {
         _snowman = this.a ? dlt.a.b : dlt.a.e;
      } else {
         _snowman = this.a ? dlt.a.a : dlt.a.d;
      }

      this.b(_snowman, this.l, this.m, _snowman.a(), _snowman.b(), this.j, this.k);
   }

   static enum a {
      a(0, 146),
      b(0, 166),
      c(0, 186),
      d(20, 146),
      e(20, 166),
      f(20, 186);

      private final int g;
      private final int h;

      private a(int var3, int var4) {
         this.g = _snowman;
         this.h = _snowman;
      }

      public int a() {
         return this.g;
      }

      public int b() {
         return this.h;
      }
   }
}
