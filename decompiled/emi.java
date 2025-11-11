import it.unimi.dsi.fastutil.objects.Object2ObjectArrayMap;
import java.util.Optional;
import java.util.Random;

public class emi implements eme {
   private final dzm a;
   private final enu b;
   private final bsx c;
   private final Random d;
   private Object2ObjectArrayMap<bsv, emi.a> e = new Object2ObjectArrayMap();
   private Optional<bst> f = Optional.empty();
   private Optional<bss> g = Optional.empty();
   private float h;
   private bsv i;

   public emi(dzm var1, enu var2, bsx var3) {
      this.d = _snowman.l.u_();
      this.a = _snowman;
      this.b = _snowman;
      this.c = _snowman;
   }

   public float b() {
      return this.h;
   }

   @Override
   public void a() {
      this.e.values().removeIf(emd::n);
      bsv _snowman = this.c.a(this.a.cD(), this.a.cE(), this.a.cH());
      if (_snowman != this.i) {
         this.i = _snowman;
         this.f = _snowman.q();
         this.g = _snowman.r();
         this.e.values().forEach(emi.a::p);
         _snowman.p().ifPresent(var2 -> {
            emi.a var10000 = (emi.a)this.e.compute(_snowman, (var2x, var3) -> {
               if (var3 == null) {
                  var3 = new emi.a(var2);
                  this.b.a((emt)var3);
               }

               var3.q();
               return var3;
            });
         });
      }

      this.g.ifPresent(var1x -> {
         if (this.d.nextDouble() < var1x.b()) {
            this.b.a(emp.b(var1x.a()));
         }
      });
      this.f
         .ifPresent(
            var1x -> {
               brx _snowmanx = this.a.l;
               int _snowmanx = var1x.c() * 2 + 1;
               fx _snowmanxx = new fx(
                  this.a.cD() + (double)this.d.nextInt(_snowmanx) - (double)var1x.c(),
                  this.a.cG() + (double)this.d.nextInt(_snowmanx) - (double)var1x.c(),
                  this.a.cH() + (double)this.d.nextInt(_snowmanx) - (double)var1x.c()
               );
               int _snowmanxxx = _snowmanx.a(bsf.a, _snowmanxx);
               if (_snowmanxxx > 0) {
                  this.h = this.h - (float)_snowmanxxx / (float)_snowmanx.K() * 0.001F;
               } else {
                  this.h = this.h - (float)(_snowmanx.a(bsf.b, _snowmanxx) - 1) / (float)var1x.b();
               }

               if (this.h >= 1.0F) {
                  double _snowmanxxxx = (double)_snowmanxx.u() + 0.5;
                  double _snowmanxxxxx = (double)_snowmanxx.v() + 0.5;
                  double _snowmanxxxxxx = (double)_snowmanxx.w() + 0.5;
                  double _snowmanxxxxxxx = _snowmanxxxx - this.a.cD();
                  double _snowmanxxxxxxxx = _snowmanxxxxx - this.a.cG();
                  double _snowmanxxxxxxxxx = _snowmanxxxxxx - this.a.cH();
                  double _snowmanxxxxxxxxxx = (double)afm.a(_snowmanxxxxxxx * _snowmanxxxxxxx + _snowmanxxxxxxxx * _snowmanxxxxxxxx + _snowmanxxxxxxxxx * _snowmanxxxxxxxxx);
                  double _snowmanxxxxxxxxxxx = _snowmanxxxxxxxxxx + var1x.d();
                  emp _snowmanxxxxxxxxxxxx = emp.b(
                     var1x.a(),
                     this.a.cD() + _snowmanxxxxxxx / _snowmanxxxxxxxxxx * _snowmanxxxxxxxxxxx,
                     this.a.cG() + _snowmanxxxxxxxx / _snowmanxxxxxxxxxx * _snowmanxxxxxxxxxxx,
                     this.a.cH() + _snowmanxxxxxxxxx / _snowmanxxxxxxxxxx * _snowmanxxxxxxxxxxx
                  );
                  this.b.a(_snowmanxxxxxxxxxxxx);
                  this.h = 0.0F;
               } else {
                  this.h = Math.max(this.h, 0.0F);
               }
            }
         );
   }

   public static class a extends emd {
      private int n;
      private int o;

      public a(adp var1) {
         super(_snowman, adr.i);
         this.i = true;
         this.j = 0;
         this.d = 1.0F;
         this.m = true;
      }

      @Override
      public void r() {
         if (this.o < 0) {
            this.o();
         }

         this.o = this.o + this.n;
         this.d = afm.a((float)this.o / 40.0F, 0.0F, 1.0F);
      }

      public void p() {
         this.o = Math.min(this.o, 40);
         this.n = -1;
      }

      public void q() {
         this.o = Math.max(0, this.o);
         this.n = 1;
      }
   }
}
