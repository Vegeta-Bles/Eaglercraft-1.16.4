import java.util.List;
import javax.annotation.Nullable;

public class blo extends bko {
   private final aqe<?> a;

   public blo(aqe<?> var1, cuw var2, blx.a var3) {
      super(_snowman, _snowman);
      this.a = _snowman;
   }

   @Override
   public void a(brx var1, bmb var2, fx var3) {
      if (_snowman instanceof aag) {
         this.a((aag)_snowman, _snowman, _snowman);
      }
   }

   @Override
   protected void a(@Nullable bfw var1, bry var2, fx var3) {
      _snowman.a(_snowman, _snowman, adq.bk, adr.g, 1.0F, 1.0F);
   }

   private void a(aag var1, bmb var2, fx var3) {
      aqa _snowman = this.a.a(_snowman, _snowman, null, _snowman, aqp.l, true, false);
      if (_snowman != null) {
         ((azw)_snowman).t(true);
      }
   }

   @Override
   public void a(bmb var1, @Nullable brx var2, List<nr> var3, bnl var4) {
      if (this.a == aqe.aM) {
         md _snowman = _snowman.o();
         if (_snowman != null && _snowman.c("BucketVariantTag", 3)) {
            int _snowmanx = _snowman.h("BucketVariantTag");
            k[] _snowmanxx = new k[]{k.u, k.h};
            String _snowmanxxx = "color.minecraft." + baw.s(_snowmanx);
            String _snowmanxxxx = "color.minecraft." + baw.t(_snowmanx);

            for (int _snowmanxxxxx = 0; _snowmanxxxxx < baw.b.length; _snowmanxxxxx++) {
               if (_snowmanx == baw.b[_snowmanxxxxx]) {
                  _snowman.add(new of(baw.b(_snowmanxxxxx)).a(_snowmanxx));
                  return;
               }
            }

            _snowman.add(new of(baw.u(_snowmanx)).a(_snowmanxx));
            nx _snowmanxxxxxx = new of(_snowmanxxx);
            if (!_snowmanxxx.equals(_snowmanxxxx)) {
               _snowmanxxxxxx.c(", ").a(new of(_snowmanxxxx));
            }

            _snowmanxxxxxx.a(_snowmanxx);
            _snowman.add(_snowmanxxxxxx);
         }
      }
   }
}
