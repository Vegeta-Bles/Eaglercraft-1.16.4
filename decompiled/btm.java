import javax.annotation.Nullable;

public abstract class btm extends bud {
   private final bkx a;

   protected btm(bkx var1, ceg.c var2) {
      super(_snowman);
      this.a = _snowman;
   }

   @Override
   public boolean ai_() {
      return true;
   }

   @Override
   public ccj a(brc var1) {
      return new cca(this.a);
   }

   @Override
   public void a(brx var1, fx var2, ceh var3, @Nullable aqm var4, bmb var5) {
      if (_snowman.t()) {
         ccj _snowman = _snowman.c(_snowman);
         if (_snowman instanceof cca) {
            ((cca)_snowman).a(_snowman.r());
         }
      }
   }

   @Override
   public bmb a(brc var1, fx var2, ceh var3) {
      ccj _snowman = _snowman.c(_snowman);
      return _snowman instanceof cca ? ((cca)_snowman).a(_snowman) : super.a(_snowman, _snowman, _snowman);
   }

   public bkx b() {
      return this.a;
   }
}
