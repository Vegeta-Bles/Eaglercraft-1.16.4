public class dlj extends dld {
   public static final dlj.b s = (var0, var1, var2, var3) -> {
   };
   protected final dlj.a t;
   protected final dlj.b u;

   public dlj(int var1, int var2, int var3, int var4, nr var5, dlj.a var6) {
      this(_snowman, _snowman, _snowman, _snowman, _snowman, _snowman, s);
   }

   public dlj(int var1, int var2, int var3, int var4, nr var5, dlj.a var6, dlj.b var7) {
      super(_snowman, _snowman, _snowman, _snowman, _snowman);
      this.t = _snowman;
      this.u = _snowman;
   }

   @Override
   public void b() {
      this.t.onPress(this);
   }

   @Override
   public void b(dfm var1, int var2, int var3, float var4) {
      super.b(_snowman, _snowman, _snowman, _snowman);
      if (this.g()) {
         this.a(_snowman, _snowman, _snowman);
      }
   }

   @Override
   public void a(dfm var1, int var2, int var3) {
      this.u.onTooltip(this, _snowman, _snowman, _snowman);
   }

   public interface a {
      void onPress(dlj var1);
   }

   public interface b {
      void onTooltip(dlj var1, dfm var2, int var3, int var4);
   }
}
