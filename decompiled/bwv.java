import com.google.common.collect.Maps;
import java.util.Map;

public class bwv extends buo {
   private static final Map<buo, buo> b = Maps.newHashMap();
   protected static final ddh a = buo.a(5.0, 0.0, 5.0, 11.0, 6.0, 11.0);
   private final buo c;

   public bwv(buo var1, ceg.c var2) {
      super(_snowman);
      this.c = _snowman;
      b.put(_snowman, this);
   }

   @Override
   public ddh b(ceh var1, brc var2, fx var3, dcs var4) {
      return a;
   }

   @Override
   public bzh b(ceh var1) {
      return bzh.c;
   }

   @Override
   public aou a(ceh var1, brx var2, fx var3, bfw var4, aot var5, dcj var6) {
      bmb _snowman = _snowman.b(_snowman);
      blx _snowmanx = _snowman.b();
      buo _snowmanxx = _snowmanx instanceof bkh ? b.getOrDefault(((bkh)_snowmanx).e(), bup.a) : bup.a;
      boolean _snowmanxxx = _snowmanxx == bup.a;
      boolean _snowmanxxxx = this.c == bup.a;
      if (_snowmanxxx != _snowmanxxxx) {
         if (_snowmanxxxx) {
            _snowman.a(_snowman, _snowmanxx.n(), 3);
            _snowman.a(aea.ag);
            if (!_snowman.bC.d) {
               _snowman.g(1);
            }
         } else {
            bmb _snowmanxxxxx = new bmb(this.c);
            if (_snowman.a()) {
               _snowman.a(_snowman, _snowmanxxxxx);
            } else if (!_snowman.g(_snowmanxxxxx)) {
               _snowman.a(_snowmanxxxxx, false);
            }

            _snowman.a(_snowman, bup.ev.n(), 3);
         }

         return aou.a(_snowman.v);
      } else {
         return aou.b;
      }
   }

   @Override
   public bmb a(brc var1, fx var2, ceh var3) {
      return this.c == bup.a ? super.a(_snowman, _snowman, _snowman) : new bmb(this.c);
   }

   @Override
   public ceh a(ceh var1, gc var2, ceh var3, bry var4, fx var5, fx var6) {
      return _snowman == gc.a && !_snowman.a(_snowman, _snowman) ? bup.a.n() : super.a(_snowman, _snowman, _snowman, _snowman, _snowman, _snowman);
   }

   public buo c() {
      return this.c;
   }

   @Override
   public boolean a(ceh var1, brc var2, fx var3, cxe var4) {
      return false;
   }
}
