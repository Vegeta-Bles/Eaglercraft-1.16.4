import javax.annotation.Nullable;

public class caq extends bud {
   public static final cfe<cfo> a = cex.aM;

   protected caq(ceg.c var1) {
      super(_snowman);
   }

   @Override
   public ccj a(brc var1) {
      return new cdj();
   }

   @Override
   public aou a(ceh var1, brx var2, fx var3, bfw var4, aot var5, dcj var6) {
      ccj _snowman = _snowman.c(_snowman);
      if (_snowman instanceof cdj) {
         return ((cdj)_snowman).a(_snowman) ? aou.a(_snowman.v) : aou.c;
      } else {
         return aou.c;
      }
   }

   @Override
   public void a(brx var1, fx var2, ceh var3, @Nullable aqm var4, bmb var5) {
      if (!_snowman.v) {
         if (_snowman != null) {
            ccj _snowman = _snowman.c(_snowman);
            if (_snowman instanceof cdj) {
               ((cdj)_snowman).a(_snowman);
            }
         }
      }
   }

   @Override
   public bzh b(ceh var1) {
      return bzh.c;
   }

   @Override
   public ceh a(bny var1) {
      return this.n().a(a, cfo.d);
   }

   @Override
   protected void a(cei.a<buo, ceh> var1) {
      _snowman.a(a);
   }

   @Override
   public void a(ceh var1, brx var2, fx var3, buo var4, fx var5, boolean var6) {
      if (_snowman instanceof aag) {
         ccj _snowman = _snowman.c(_snowman);
         if (_snowman instanceof cdj) {
            cdj _snowmanx = (cdj)_snowman;
            boolean _snowmanxx = _snowman.r(_snowman);
            boolean _snowmanxxx = _snowmanx.G();
            if (_snowmanxx && !_snowmanxxx) {
               _snowmanx.c(true);
               this.a((aag)_snowman, _snowmanx);
            } else if (!_snowmanxx && _snowmanxxx) {
               _snowmanx.c(false);
            }
         }
      }
   }

   private void a(aag var1, cdj var2) {
      switch (_snowman.x()) {
         case a:
            _snowman.b(false);
            break;
         case b:
            _snowman.a(_snowman, false);
            break;
         case c:
            _snowman.E();
         case d:
      }
   }
}
