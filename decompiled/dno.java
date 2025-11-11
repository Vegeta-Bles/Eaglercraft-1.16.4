import javax.annotation.Nullable;

public class dno extends dot {
   @Nullable
   private final dot b;
   protected final dno.a a;
   private final nr c;
   private final boolean p;
   private dlu q = dlu.a;
   private dll r;

   public dno(@Nullable dot var1, dno.a var2, nr var3, nr var4, boolean var5) {
      super(_snowman);
      this.b = _snowman;
      this.a = _snowman;
      this.c = _snowman;
      this.p = _snowman;
   }

   @Override
   protected void b() {
      super.b();
      this.q = dlu.a(this.o, this.c, this.k - 50);
      int _snowman = (this.q.a() + 1) * 9;
      this.a(new dlj(this.k / 2 - 155, 100 + _snowman, 150, 20, new of("selectWorld.backupJoinConfirmButton"), var1x -> this.a.proceed(true, this.r.a())));
      this.a(new dlj(this.k / 2 - 155 + 160, 100 + _snowman, 150, 20, new of("selectWorld.backupJoinSkipButton"), var1x -> this.a.proceed(false, this.r.a())));
      this.a(new dlj(this.k / 2 - 155 + 80, 124 + _snowman, 150, 20, nq.d, var1x -> this.i.a(this.b)));
      this.r = new dll(this.k / 2 - 155 + 80, 76 + _snowman, 150, 20, new of("selectWorld.backupEraseCache"), false);
      if (this.p) {
         this.a(this.r);
      }
   }

   @Override
   public void a(dfm var1, int var2, int var3, float var4) {
      this.a(_snowman);
      a(_snowman, this.o, this.d, this.k / 2, 50, 16777215);
      this.q.a(_snowman, this.k / 2, 70);
      super.a(_snowman, _snowman, _snowman, _snowman);
   }

   @Override
   public boolean as_() {
      return false;
   }

   @Override
   public boolean a(int var1, int var2, int var3) {
      if (_snowman == 256) {
         this.i.a(this.b);
         return true;
      } else {
         return super.a(_snowman, _snowman, _snowman);
      }
   }

   public interface a {
      void proceed(boolean var1, boolean var2);
   }
}
