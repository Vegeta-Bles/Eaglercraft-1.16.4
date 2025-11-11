import com.google.common.collect.Lists;
import com.mojang.datafixers.util.Pair;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class aaf {
   private static final Logger a = LogManager.getLogger();
   private final aag b;
   private final aqa c;
   private final int d;
   private final boolean e;
   private final Consumer<oj<?>> f;
   private long g;
   private long h;
   private long i;
   private int j;
   private int k;
   private int l;
   private dcn m = dcn.a;
   private int n;
   private int o;
   private List<aqa> p = Collections.emptyList();
   private boolean q;
   private boolean r;

   public aaf(aag var1, aqa var2, int var3, boolean var4, Consumer<oj<?>> var5) {
      this.b = _snowman;
      this.f = _snowman;
      this.c = _snowman;
      this.d = _snowman;
      this.e = _snowman;
      this.d();
      this.j = afm.d(_snowman.p * 256.0F / 360.0F);
      this.k = afm.d(_snowman.q * 256.0F / 360.0F);
      this.l = afm.d(_snowman.bK() * 256.0F / 360.0F);
      this.r = _snowman.ao();
   }

   public void a() {
      List<aqa> _snowman = this.c.cn();
      if (!_snowman.equals(this.p)) {
         this.p = _snowman;
         this.f.accept(new rh(this.c));
      }

      if (this.c instanceof bcp && this.n % 10 == 0) {
         bcp _snowmanx = (bcp)this.c;
         bmb _snowmanxx = _snowmanx.o();
         if (_snowmanxx.b() instanceof bmh) {
            cxx _snowmanxxx = bmh.b(_snowmanxx, this.b);

            for (aah _snowmanxxxx : this.b.x()) {
               _snowmanxxx.a(_snowmanxxxx, _snowmanxx);
               oj<?> _snowmanxxxxx = ((bmh)_snowmanxx.b()).a(_snowmanxx, this.b, _snowmanxxxx);
               if (_snowmanxxxxx != null) {
                  _snowmanxxxx.b.a(_snowmanxxxxx);
               }
            }
         }

         this.c();
      }

      if (this.n % this.d == 0 || this.c.Z || this.c.ab().a()) {
         if (this.c.br()) {
            int _snowmanx = afm.d(this.c.p * 256.0F / 360.0F);
            int _snowmanxx = afm.d(this.c.q * 256.0F / 360.0F);
            boolean _snowmanxxx = Math.abs(_snowmanx - this.j) >= 1 || Math.abs(_snowmanxx - this.k) >= 1;
            if (_snowmanxxx) {
               this.f.accept(new qa.c(this.c.Y(), (byte)_snowmanx, (byte)_snowmanxx, this.c.ao()));
               this.j = _snowmanx;
               this.k = _snowmanxx;
            }

            this.d();
            this.c();
            this.q = true;
         } else {
            this.o++;
            int _snowmanx = afm.d(this.c.p * 256.0F / 360.0F);
            int _snowmanxx = afm.d(this.c.q * 256.0F / 360.0F);
            dcn _snowmanxxx = this.c.cA().d(qa.a(this.g, this.h, this.i));
            boolean _snowmanxxxxx = _snowmanxxx.g() >= 7.6293945E-6F;
            oj<?> _snowmanxxxxxx = null;
            boolean _snowmanxxxxxxx = _snowmanxxxxx || this.n % 60 == 0;
            boolean _snowmanxxxxxxxx = Math.abs(_snowmanx - this.j) >= 1 || Math.abs(_snowmanxx - this.k) >= 1;
            if (this.n > 0 || this.c instanceof bga) {
               long _snowmanxxxxxxxxx = qa.a(_snowmanxxx.b);
               long _snowmanxxxxxxxxxx = qa.a(_snowmanxxx.c);
               long _snowmanxxxxxxxxxxx = qa.a(_snowmanxxx.d);
               boolean _snowmanxxxxxxxxxxxx = _snowmanxxxxxxxxx < -32768L
                  || _snowmanxxxxxxxxx > 32767L
                  || _snowmanxxxxxxxxxx < -32768L
                  || _snowmanxxxxxxxxxx > 32767L
                  || _snowmanxxxxxxxxxxx < -32768L
                  || _snowmanxxxxxxxxxxx > 32767L;
               if (_snowmanxxxxxxxxxxxx || this.o > 400 || this.q || this.r != this.c.ao()) {
                  this.r = this.c.ao();
                  this.o = 0;
                  _snowmanxxxxxx = new rs(this.c);
               } else if ((!_snowmanxxxxxxx || !_snowmanxxxxxxxx) && !(this.c instanceof bga)) {
                  if (_snowmanxxxxxxx) {
                     _snowmanxxxxxx = new qa.a(this.c.Y(), (short)((int)_snowmanxxxxxxxxx), (short)((int)_snowmanxxxxxxxxxx), (short)((int)_snowmanxxxxxxxxxxx), this.c.ao());
                  } else if (_snowmanxxxxxxxx) {
                     _snowmanxxxxxx = new qa.c(this.c.Y(), (byte)_snowmanx, (byte)_snowmanxx, this.c.ao());
                  }
               } else {
                  _snowmanxxxxxx = new qa.b(
                     this.c.Y(), (short)((int)_snowmanxxxxxxxxx), (short)((int)_snowmanxxxxxxxxxx), (short)((int)_snowmanxxxxxxxxxxx), (byte)_snowmanx, (byte)_snowmanxx, this.c.ao()
                  );
               }
            }

            if ((this.e || this.c.Z || this.c instanceof aqm && ((aqm)this.c).ef()) && this.n > 0) {
               dcn _snowmanxxxxxxxxx = this.c.cC();
               double _snowmanxxxxxxxxxx = _snowmanxxxxxxxxx.g(this.m);
               if (_snowmanxxxxxxxxxx > 1.0E-7 || _snowmanxxxxxxxxxx > 0.0 && _snowmanxxxxxxxxx.g() == 0.0) {
                  this.m = _snowmanxxxxxxxxx;
                  this.f.accept(new rc(this.c.Y(), this.m));
               }
            }

            if (_snowmanxxxxxx != null) {
               this.f.accept(_snowmanxxxxxx);
            }

            this.c();
            if (_snowmanxxxxxxx) {
               this.d();
            }

            if (_snowmanxxxxxxxx) {
               this.j = _snowmanx;
               this.k = _snowmanxx;
            }

            this.q = false;
         }

         int _snowmanxxxxxxxxx = afm.d(this.c.bK() * 256.0F / 360.0F);
         if (Math.abs(_snowmanxxxxxxxxx - this.l) >= 1) {
            this.f.accept(new qq(this.c, (byte)_snowmanxxxxxxxxx));
            this.l = _snowmanxxxxxxxxx;
         }

         this.c.Z = false;
      }

      this.n++;
      if (this.c.w) {
         this.a(new rc(this.c));
         this.c.w = false;
      }
   }

   public void a(aah var1) {
      this.c.c(_snowman);
      _snowman.c(this.c);
   }

   public void b(aah var1) {
      this.a(_snowman.b::a);
      this.c.b(_snowman);
      _snowman.d(this.c);
   }

   public void a(Consumer<oj<?>> var1) {
      if (this.c.y) {
         a.warn("Fetching packet for removed entity " + this.c);
      }

      oj<?> _snowman = this.c.P();
      this.l = afm.d(this.c.bK() * 256.0F / 360.0F);
      _snowman.accept(_snowman);
      if (!this.c.ab().d()) {
         _snowman.accept(new ra(this.c.Y(), this.c.ab(), true));
      }

      boolean _snowmanx = this.e;
      if (this.c instanceof aqm) {
         Collection<arh> _snowmanxx = ((aqm)this.c).dB().b();
         if (!_snowmanxx.isEmpty()) {
            _snowman.accept(new ru(this.c.Y(), _snowmanxx));
         }

         if (((aqm)this.c).ef()) {
            _snowmanx = true;
         }
      }

      this.m = this.c.cC();
      if (_snowmanx && !(_snowman instanceof op)) {
         _snowman.accept(new rc(this.c.Y(), this.m));
      }

      if (this.c instanceof aqm) {
         List<Pair<aqf, bmb>> _snowmanxxx = Lists.newArrayList();

         for (aqf _snowmanxxxx : aqf.values()) {
            bmb _snowmanxxxxx = ((aqm)this.c).b(_snowmanxxxx);
            if (!_snowmanxxxxx.a()) {
               _snowmanxxx.add(Pair.of(_snowmanxxxx, _snowmanxxxxx.i()));
            }
         }

         if (!_snowmanxxx.isEmpty()) {
            _snowman.accept(new rd(this.c.Y(), _snowmanxxx));
         }
      }

      if (this.c instanceof aqm) {
         aqm _snowmanxxx = (aqm)this.c;

         for (apu _snowmanxxxxx : _snowmanxxx.dh()) {
            _snowman.accept(new rv(this.c.Y(), _snowmanxxxxx));
         }
      }

      if (!this.c.cn().isEmpty()) {
         _snowman.accept(new rh(this.c));
      }

      if (this.c.br()) {
         _snowman.accept(new rh(this.c.ct()));
      }

      if (this.c instanceof aqn) {
         aqn _snowmanxxx = (aqn)this.c;
         if (_snowmanxxx.eB()) {
            _snowman.accept(new rb(_snowmanxxx, _snowmanxxx.eC()));
         }
      }
   }

   private void c() {
      uv _snowman = this.c.ab();
      if (_snowman.a()) {
         this.a(new ra(this.c.Y(), _snowman, false));
      }

      if (this.c instanceof aqm) {
         Set<arh> _snowmanx = ((aqm)this.c).dB().a();
         if (!_snowmanx.isEmpty()) {
            this.a(new ru(this.c.Y(), _snowmanx));
         }

         _snowmanx.clear();
      }
   }

   private void d() {
      this.g = qa.a(this.c.cD());
      this.h = qa.a(this.c.cE());
      this.i = qa.a(this.c.cH());
   }

   public dcn b() {
      return qa.a(this.g, this.h, this.i);
   }

   private void a(oj<?> var1) {
      this.f.accept(_snowman);
      if (this.c instanceof aah) {
         ((aah)this.c).b.a(_snowman);
      }
   }
}
