import javax.annotation.Nullable;

public class axk extends awt {
   protected final float h;

   public axk(aqu var1, double var2) {
      this(_snowman, _snowman, 0.001F);
   }

   public axk(aqu var1, double var2, float var4) {
      super(_snowman, _snowman);
      this.h = _snowman;
   }

   @Nullable
   @Override
   protected dcn g() {
      if (this.a.aH()) {
         dcn _snowman = azj.b(this.a, 15, 7);
         return _snowman == null ? super.g() : _snowman;
      } else {
         return this.a.cY().nextFloat() >= this.h ? azj.b(this.a, 10, 7) : super.g();
      }
   }
}
