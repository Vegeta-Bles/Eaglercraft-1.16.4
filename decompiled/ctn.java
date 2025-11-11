import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.UnmodifiableIterator;
import com.google.common.collect.ImmutableMap.Builder;
import com.mojang.serialization.Codec;
import java.util.Comparator;
import java.util.Random;

public abstract class ctn extends ctt<ctu> {
   private long a;
   private ImmutableMap<ceh, cub> b = ImmutableMap.of();
   private ImmutableMap<ceh, cub> c = ImmutableMap.of();
   private cub d;

   public ctn(Codec<ctu> var1) {
      super(_snowman);
   }

   public void a(Random var1, cfw var2, bsv var3, int var4, int var5, int var6, double var7, ceh var9, ceh var10, int var11, long var12, ctu var14) {
      int _snowman = _snowman + 1;
      int _snowmanx = _snowman & 15;
      int _snowmanxx = _snowman & 15;
      int _snowmanxxx = (int)(_snowman / 3.0 + 3.0 + _snowman.nextDouble() * 0.25);
      int _snowmanxxxx = (int)(_snowman / 3.0 + 3.0 + _snowman.nextDouble() * 0.25);
      double _snowmanxxxxx = 0.03125;
      boolean _snowmanxxxxxx = this.d.a((double)_snowman * 0.03125, 109.0, (double)_snowman * 0.03125) * 75.0 + _snowman.nextDouble() > 0.0;
      ceh _snowmanxxxxxxx = (ceh)this.c
         .entrySet()
         .stream()
         .max(Comparator.comparing(var3x -> ((cub)var3x.getValue()).a((double)_snowman, (double)_snowman, (double)_snowman)))
         .get()
         .getKey();
      ceh _snowmanxxxxxxxx = (ceh)this.b
         .entrySet()
         .stream()
         .max(Comparator.comparing(var3x -> ((cub)var3x.getValue()).a((double)_snowman, (double)_snowman, (double)_snowman)))
         .get()
         .getKey();
      fx.a _snowmanxxxxxxxxx = new fx.a();
      ceh _snowmanxxxxxxxxxx = _snowman.d_(_snowmanxxxxxxxxx.d(_snowmanx, 128, _snowmanxx));

      for (int _snowmanxxxxxxxxxxx = 127; _snowmanxxxxxxxxxxx >= 0; _snowmanxxxxxxxxxxx--) {
         _snowmanxxxxxxxxx.d(_snowmanx, _snowmanxxxxxxxxxxx, _snowmanxx);
         ceh _snowmanxxxxxxxxxxxx = _snowman.d_(_snowmanxxxxxxxxx);
         if (_snowmanxxxxxxxxxx.a(_snowman.b()) && (_snowmanxxxxxxxxxxxx.g() || _snowmanxxxxxxxxxxxx == _snowman)) {
            for (int _snowmanxxxxxxxxxxxxx = 0; _snowmanxxxxxxxxxxxxx < _snowmanxxx; _snowmanxxxxxxxxxxxxx++) {
               _snowmanxxxxxxxxx.c(gc.b);
               if (!_snowman.d_(_snowmanxxxxxxxxx).a(_snowman.b())) {
                  break;
               }

               _snowman.a(_snowmanxxxxxxxxx, _snowmanxxxxxxx, false);
            }

            _snowmanxxxxxxxxx.d(_snowmanx, _snowmanxxxxxxxxxxx, _snowmanxx);
         }

         if ((_snowmanxxxxxxxxxx.g() || _snowmanxxxxxxxxxx == _snowman) && _snowmanxxxxxxxxxxxx.a(_snowman.b())) {
            for (int _snowmanxxxxxxxxxxxxx = 0; _snowmanxxxxxxxxxxxxx < _snowmanxxxx && _snowman.d_(_snowmanxxxxxxxxx).a(_snowman.b()); _snowmanxxxxxxxxxxxxx++) {
               if (_snowmanxxxxxx && _snowmanxxxxxxxxxxx >= _snowman - 4 && _snowmanxxxxxxxxxxx <= _snowman + 1) {
                  _snowman.a(_snowmanxxxxxxxxx, this.c(), false);
               } else {
                  _snowman.a(_snowmanxxxxxxxxx, _snowmanxxxxxxxx, false);
               }

               _snowmanxxxxxxxxx.c(gc.a);
            }
         }

         _snowmanxxxxxxxxxx = _snowmanxxxxxxxxxxxx;
      }
   }

   @Override
   public void a(long var1) {
      if (this.a != _snowman || this.d == null || this.b.isEmpty() || this.c.isEmpty()) {
         this.b = a(this.a(), _snowman);
         this.c = a(this.b(), _snowman + (long)this.b.size());
         this.d = new cub(new chx(_snowman + (long)this.b.size() + (long)this.c.size()), ImmutableList.of(0));
      }

      this.a = _snowman;
   }

   private static ImmutableMap<ceh, cub> a(ImmutableList<ceh> var0, long var1) {
      Builder<ceh, cub> _snowman = new Builder();
      UnmodifiableIterator var4 = _snowman.iterator();

      while (var4.hasNext()) {
         ceh _snowmanx = (ceh)var4.next();
         _snowman.put(_snowmanx, new cub(new chx(_snowman), ImmutableList.of(-4)));
         _snowman++;
      }

      return _snowman.build();
   }

   protected abstract ImmutableList<ceh> a();

   protected abstract ImmutableList<ceh> b();

   protected abstract ceh c();
}
