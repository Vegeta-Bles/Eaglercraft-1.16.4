import java.util.List;
import javax.annotation.Nullable;

public class bns extends blx {
   public bns(blx.a var1) {
      super(_snowman);
   }

   public static boolean a(@Nullable md var0) {
      if (!bnr.a(_snowman)) {
         return false;
      } else if (!_snowman.c("title", 8)) {
         return false;
      } else {
         String _snowman = _snowman.l("title");
         return _snowman.length() > 32 ? false : _snowman.c("author", 8);
      }
   }

   public static int d(bmb var0) {
      return _snowman.o().h("generation");
   }

   public static int g(bmb var0) {
      md _snowman = _snowman.o();
      return _snowman != null ? _snowman.d("pages", 8).size() : 0;
   }

   @Override
   public nr h(bmb var1) {
      if (_snowman.n()) {
         md _snowman = _snowman.o();
         String _snowmanx = _snowman.l("title");
         if (!aft.b(_snowmanx)) {
            return new oe(_snowmanx);
         }
      }

      return super.h(_snowman);
   }

   @Override
   public void a(bmb var1, @Nullable brx var2, List<nr> var3, bnl var4) {
      if (_snowman.n()) {
         md _snowman = _snowman.o();
         String _snowmanx = _snowman.l("author");
         if (!aft.b(_snowmanx)) {
            _snowman.add(new of("book.byAuthor", _snowmanx).a(k.h));
         }

         _snowman.add(new of("book.generation." + _snowman.h("generation")).a(k.h));
      }
   }

   @Override
   public aou a(boa var1) {
      brx _snowman = _snowman.p();
      fx _snowmanx = _snowman.a();
      ceh _snowmanxx = _snowman.d_(_snowmanx);
      if (_snowmanxx.a(bup.lY)) {
         return bxy.a(_snowman, _snowmanx, _snowmanxx, _snowman.m()) ? aou.a(_snowman.v) : aou.c;
      } else {
         return aou.c;
      }
   }

   @Override
   public aov<bmb> a(brx var1, bfw var2, aot var3) {
      bmb _snowman = _snowman.b(_snowman);
      _snowman.a(_snowman, _snowman);
      _snowman.b(aea.c.b(this));
      return aov.a(_snowman, _snowman.s_());
   }

   public static boolean a(bmb var0, @Nullable db var1, @Nullable bfw var2) {
      md _snowman = _snowman.o();
      if (_snowman != null && !_snowman.q("resolved")) {
         _snowman.a("resolved", true);
         if (!a(_snowman)) {
            return false;
         } else {
            mj _snowmanx = _snowman.d("pages", 8);

            for (int _snowmanxx = 0; _snowmanxx < _snowmanx.size(); _snowmanxx++) {
               String _snowmanxxx = _snowmanx.j(_snowmanxx);

               nr _snowmanxxxx;
               try {
                  _snowmanxxxx = nr.a.b(_snowmanxxx);
                  _snowmanxxxx = ns.a(_snowman, _snowmanxxxx, _snowman, 0);
               } catch (Exception var9) {
                  _snowmanxxxx = new oe(_snowmanxxx);
               }

               _snowmanx.d(_snowmanxx, ms.a(nr.a.a(_snowmanxxxx)));
            }

            _snowman.a("pages", _snowmanx);
            return true;
         }
      } else {
         return false;
      }
   }

   @Override
   public boolean e(bmb var1) {
      return true;
   }
}
