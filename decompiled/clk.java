import com.google.common.collect.Lists;
import com.mojang.serialization.Codec;
import java.util.List;
import java.util.Random;

public class clk extends cla<cmh> {
   public clk(Codec<cmh> var1) {
      super(_snowman);
   }

   @Override
   protected boolean b() {
      return false;
   }

   protected boolean a(cfy var1, bsy var2, long var3, chx var5, int var6, int var7, bsv var8, brd var9, cmh var10) {
      for (bsv _snowman : _snowman.a(_snowman * 16 + 9, _snowman.f(), _snowman * 16 + 9, 32)) {
         if (!_snowman.e().a(this)) {
            return false;
         }
      }

      return true;
   }

   @Override
   public cla.a<cmh> a() {
      return clk.a::new;
   }

   public static class a extends crv<cmh> {
      public a(cla<cmh> var1, int var2, int var3, cra var4, int var5, long var6) {
         super(_snowman, _snowman, _snowman, _snowman, _snowman, _snowman);
      }

      public void a(gn var1, cfy var2, csw var3, int var4, int var5, bsv var6, cmh var7) {
         bzm _snowman = bzm.a(this.d);
         int _snowmanx = 5;
         int _snowmanxx = 5;
         if (_snowman == bzm.b) {
            _snowmanx = -5;
         } else if (_snowman == bzm.c) {
            _snowmanx = -5;
            _snowmanxx = -5;
         } else if (_snowman == bzm.d) {
            _snowmanxx = -5;
         }

         int _snowmanxxx = (_snowman << 4) + 7;
         int _snowmanxxxx = (_snowman << 4) + 7;
         int _snowmanxxxxx = _snowman.c(_snowmanxxx, _snowmanxxxx, chn.a.a);
         int _snowmanxxxxxx = _snowman.c(_snowmanxxx, _snowmanxxxx + _snowmanxx, chn.a.a);
         int _snowmanxxxxxxx = _snowman.c(_snowmanxxx + _snowmanx, _snowmanxxxx, chn.a.a);
         int _snowmanxxxxxxxx = _snowman.c(_snowmanxxx + _snowmanx, _snowmanxxxx + _snowmanxx, chn.a.a);
         int _snowmanxxxxxxxxx = Math.min(Math.min(_snowmanxxxxx, _snowmanxxxxxx), Math.min(_snowmanxxxxxxx, _snowmanxxxxxxxx));
         if (_snowmanxxxxxxxxx >= 60) {
            fx _snowmanxxxxxxxxxx = new fx(_snowman * 16 + 8, _snowmanxxxxxxxxx + 1, _snowman * 16 + 8);
            List<cry.i> _snowmanxxxxxxxxxxx = Lists.newLinkedList();
            cry.a(_snowman, _snowmanxxxxxxxxxx, _snowman, _snowmanxxxxxxxxxxx, this.d);
            this.b.addAll(_snowmanxxxxxxxxxxx);
            this.b();
         }
      }

      @Override
      public void a(bsr var1, bsn var2, cfy var3, Random var4, cra var5, brd var6) {
         super.a(_snowman, _snowman, _snowman, _snowman, _snowman, _snowman);
         int _snowman = this.c.b;

         for (int _snowmanx = _snowman.a; _snowmanx <= _snowman.d; _snowmanx++) {
            for (int _snowmanxx = _snowman.c; _snowmanxx <= _snowman.f; _snowmanxx++) {
               fx _snowmanxxx = new fx(_snowmanx, _snowman, _snowmanxx);
               if (!_snowman.w(_snowmanxxx) && this.c.b(_snowmanxxx)) {
                  boolean _snowmanxxxx = false;

                  for (cru _snowmanxxxxx : this.b) {
                     if (_snowmanxxxxx.g().b(_snowmanxxx)) {
                        _snowmanxxxx = true;
                        break;
                     }
                  }

                  if (_snowmanxxxx) {
                     for (int _snowmanxxxxxx = _snowman - 1; _snowmanxxxxxx > 1; _snowmanxxxxxx--) {
                        fx _snowmanxxxxxxx = new fx(_snowmanx, _snowmanxxxxxx, _snowmanxx);
                        if (!_snowman.w(_snowmanxxxxxxx) && !_snowman.d_(_snowmanxxxxxxx).c().a()) {
                           break;
                        }

                        _snowman.a(_snowmanxxxxxxx, bup.m.n(), 2);
                     }
                  }
               }
            }
         }
      }
   }
}
