import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.mojang.datafixers.util.Pair;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import it.unimi.dsi.fastutil.objects.ObjectListIterator;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Random;
import java.util.Set;
import javax.annotation.Nullable;

public class brp {
   private static final brq a = new brq();
   private final boolean b;
   private final brp.a c;
   private final Random d = new Random();
   private final brx e;
   private final double f;
   private final double g;
   private final double h;
   @Nullable
   private final aqa i;
   private final float j;
   private final apk k;
   private final brq l;
   private final List<fx> m = Lists.newArrayList();
   private final Map<bfw, dcn> n = Maps.newHashMap();

   public brp(brx var1, @Nullable aqa var2, double var3, double var5, double var7, float var9, List<fx> var10) {
      this(_snowman, _snowman, _snowman, _snowman, _snowman, _snowman, false, brp.a.c, _snowman);
   }

   public brp(brx var1, @Nullable aqa var2, double var3, double var5, double var7, float var9, boolean var10, brp.a var11, List<fx> var12) {
      this(_snowman, _snowman, _snowman, _snowman, _snowman, _snowman, _snowman, _snowman);
      this.m.addAll(_snowman);
   }

   public brp(brx var1, @Nullable aqa var2, double var3, double var5, double var7, float var9, boolean var10, brp.a var11) {
      this(_snowman, _snowman, null, null, _snowman, _snowman, _snowman, _snowman, _snowman, _snowman);
   }

   public brp(
      brx var1, @Nullable aqa var2, @Nullable apk var3, @Nullable brq var4, double var5, double var7, double var9, float var11, boolean var12, brp.a var13
   ) {
      this.e = _snowman;
      this.i = _snowman;
      this.j = _snowman;
      this.f = _snowman;
      this.g = _snowman;
      this.h = _snowman;
      this.b = _snowman;
      this.c = _snowman;
      this.k = _snowman == null ? apk.a(this) : _snowman;
      this.l = _snowman == null ? this.a(_snowman) : _snowman;
   }

   private brq a(@Nullable aqa var1) {
      return (brq)(_snowman == null ? a : new brn(_snowman));
   }

   public static float a(dcn var0, aqa var1) {
      dci _snowman = _snowman.cc();
      double _snowmanx = 1.0 / ((_snowman.d - _snowman.a) * 2.0 + 1.0);
      double _snowmanxx = 1.0 / ((_snowman.e - _snowman.b) * 2.0 + 1.0);
      double _snowmanxxx = 1.0 / ((_snowman.f - _snowman.c) * 2.0 + 1.0);
      double _snowmanxxxx = (1.0 - Math.floor(1.0 / _snowmanx) * _snowmanx) / 2.0;
      double _snowmanxxxxx = (1.0 - Math.floor(1.0 / _snowmanxxx) * _snowmanxxx) / 2.0;
      if (!(_snowmanx < 0.0) && !(_snowmanxx < 0.0) && !(_snowmanxxx < 0.0)) {
         int _snowmanxxxxxx = 0;
         int _snowmanxxxxxxx = 0;

         for (float _snowmanxxxxxxxx = 0.0F; _snowmanxxxxxxxx <= 1.0F; _snowmanxxxxxxxx = (float)((double)_snowmanxxxxxxxx + _snowmanx)) {
            for (float _snowmanxxxxxxxxx = 0.0F; _snowmanxxxxxxxxx <= 1.0F; _snowmanxxxxxxxxx = (float)((double)_snowmanxxxxxxxxx + _snowmanxx)) {
               for (float _snowmanxxxxxxxxxx = 0.0F; _snowmanxxxxxxxxxx <= 1.0F; _snowmanxxxxxxxxxx = (float)((double)_snowmanxxxxxxxxxx + _snowmanxxx)) {
                  double _snowmanxxxxxxxxxxx = afm.d((double)_snowmanxxxxxxxx, _snowman.a, _snowman.d);
                  double _snowmanxxxxxxxxxxxx = afm.d((double)_snowmanxxxxxxxxx, _snowman.b, _snowman.e);
                  double _snowmanxxxxxxxxxxxxx = afm.d((double)_snowmanxxxxxxxxxx, _snowman.c, _snowman.f);
                  dcn _snowmanxxxxxxxxxxxxxx = new dcn(_snowmanxxxxxxxxxxx + _snowmanxxxx, _snowmanxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxx + _snowmanxxxxx);
                  if (_snowman.l.a(new brf(_snowmanxxxxxxxxxxxxxx, _snowman, brf.a.a, brf.b.a, _snowman)).c() == dcl.a.a) {
                     _snowmanxxxxxx++;
                  }

                  _snowmanxxxxxxx++;
               }
            }
         }

         return (float)_snowmanxxxxxx / (float)_snowmanxxxxxxx;
      } else {
         return 0.0F;
      }
   }

   public void a() {
      Set<fx> _snowman = Sets.newHashSet();
      int _snowmanx = 16;

      for (int _snowmanxx = 0; _snowmanxx < 16; _snowmanxx++) {
         for (int _snowmanxxx = 0; _snowmanxxx < 16; _snowmanxxx++) {
            for (int _snowmanxxxx = 0; _snowmanxxxx < 16; _snowmanxxxx++) {
               if (_snowmanxx == 0 || _snowmanxx == 15 || _snowmanxxx == 0 || _snowmanxxx == 15 || _snowmanxxxx == 0 || _snowmanxxxx == 15) {
                  double _snowmanxxxxx = (double)((float)_snowmanxx / 15.0F * 2.0F - 1.0F);
                  double _snowmanxxxxxx = (double)((float)_snowmanxxx / 15.0F * 2.0F - 1.0F);
                  double _snowmanxxxxxxx = (double)((float)_snowmanxxxx / 15.0F * 2.0F - 1.0F);
                  double _snowmanxxxxxxxx = Math.sqrt(_snowmanxxxxx * _snowmanxxxxx + _snowmanxxxxxx * _snowmanxxxxxx + _snowmanxxxxxxx * _snowmanxxxxxxx);
                  _snowmanxxxxx /= _snowmanxxxxxxxx;
                  _snowmanxxxxxx /= _snowmanxxxxxxxx;
                  _snowmanxxxxxxx /= _snowmanxxxxxxxx;
                  float _snowmanxxxxxxxxx = this.j * (0.7F + this.e.t.nextFloat() * 0.6F);
                  double _snowmanxxxxxxxxxx = this.f;
                  double _snowmanxxxxxxxxxxx = this.g;
                  double _snowmanxxxxxxxxxxxx = this.h;

                  for (float _snowmanxxxxxxxxxxxxx = 0.3F; _snowmanxxxxxxxxx > 0.0F; _snowmanxxxxxxxxx -= 0.22500001F) {
                     fx _snowmanxxxxxxxxxxxxxx = new fx(_snowmanxxxxxxxxxx, _snowmanxxxxxxxxxxx, _snowmanxxxxxxxxxxxx);
                     ceh _snowmanxxxxxxxxxxxxxxx = this.e.d_(_snowmanxxxxxxxxxxxxxx);
                     cux _snowmanxxxxxxxxxxxxxxxx = this.e.b(_snowmanxxxxxxxxxxxxxx);
                     Optional<Float> _snowmanxxxxxxxxxxxxxxxxx = this.l.a(this, this.e, _snowmanxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxxx);
                     if (_snowmanxxxxxxxxxxxxxxxxx.isPresent()) {
                        _snowmanxxxxxxxxx -= (_snowmanxxxxxxxxxxxxxxxxx.get() + 0.3F) * 0.3F;
                     }

                     if (_snowmanxxxxxxxxx > 0.0F && this.l.a(this, this.e, _snowmanxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxx, _snowmanxxxxxxxxx)) {
                        _snowman.add(_snowmanxxxxxxxxxxxxxx);
                     }

                     _snowmanxxxxxxxxxx += _snowmanxxxxx * 0.3F;
                     _snowmanxxxxxxxxxxx += _snowmanxxxxxx * 0.3F;
                     _snowmanxxxxxxxxxxxx += _snowmanxxxxxxx * 0.3F;
                  }
               }
            }
         }
      }

      this.m.addAll(_snowman);
      float _snowmanxx = this.j * 2.0F;
      int _snowmanxxx = afm.c(this.f - (double)_snowmanxx - 1.0);
      int _snowmanxxxxx = afm.c(this.f + (double)_snowmanxx + 1.0);
      int _snowmanxxxxxx = afm.c(this.g - (double)_snowmanxx - 1.0);
      int _snowmanxxxxxxx = afm.c(this.g + (double)_snowmanxx + 1.0);
      int _snowmanxxxxxxxx = afm.c(this.h - (double)_snowmanxx - 1.0);
      int _snowmanxxxxxxxxx = afm.c(this.h + (double)_snowmanxx + 1.0);
      List<aqa> _snowmanxxxxxxxxxx = this.e.a(this.i, new dci((double)_snowmanxxx, (double)_snowmanxxxxxx, (double)_snowmanxxxxxxxx, (double)_snowmanxxxxx, (double)_snowmanxxxxxxx, (double)_snowmanxxxxxxxxx));
      dcn _snowmanxxxxxxxxxxx = new dcn(this.f, this.g, this.h);

      for (int _snowmanxxxxxxxxxxxx = 0; _snowmanxxxxxxxxxxxx < _snowmanxxxxxxxxxx.size(); _snowmanxxxxxxxxxxxx++) {
         aqa _snowmanxxxxxxxxxxxxx = _snowmanxxxxxxxxxx.get(_snowmanxxxxxxxxxxxx);
         if (!_snowmanxxxxxxxxxxxxx.ci()) {
            double _snowmanxxxxxxxxxxxxxxxxxx = (double)(afm.a(_snowmanxxxxxxxxxxxxx.e(_snowmanxxxxxxxxxxx)) / _snowmanxx);
            if (_snowmanxxxxxxxxxxxxxxxxxx <= 1.0) {
               double _snowmanxxxxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxx.cD() - this.f;
               double _snowmanxxxxxxxxxxxxxxxxxxxx = (_snowmanxxxxxxxxxxxxx instanceof bcw ? _snowmanxxxxxxxxxxxxx.cE() : _snowmanxxxxxxxxxxxxx.cG()) - this.g;
               double _snowmanxxxxxxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxx.cH() - this.h;
               double _snowmanxxxxxxxxxxxxxxxxxxxxxx = (double)afm.a(
                  _snowmanxxxxxxxxxxxxxxxxxxx * _snowmanxxxxxxxxxxxxxxxxxxx + _snowmanxxxxxxxxxxxxxxxxxxxx * _snowmanxxxxxxxxxxxxxxxxxxxx + _snowmanxxxxxxxxxxxxxxxxxxxxx * _snowmanxxxxxxxxxxxxxxxxxxxxx
               );
               if (_snowmanxxxxxxxxxxxxxxxxxxxxxx != 0.0) {
                  _snowmanxxxxxxxxxxxxxxxxxxx /= _snowmanxxxxxxxxxxxxxxxxxxxxxx;
                  _snowmanxxxxxxxxxxxxxxxxxxxx /= _snowmanxxxxxxxxxxxxxxxxxxxxxx;
                  _snowmanxxxxxxxxxxxxxxxxxxxxx /= _snowmanxxxxxxxxxxxxxxxxxxxxxx;
                  double _snowmanxxxxxxxxxxxxxxxxxxxxxxx = (double)a(_snowmanxxxxxxxxxxx, _snowmanxxxxxxxxxxxxx);
                  double _snowmanxxxxxxxxxxxxxxxxxxxxxxxx = (1.0 - _snowmanxxxxxxxxxxxxxxxxxx) * _snowmanxxxxxxxxxxxxxxxxxxxxxxx;
                  _snowmanxxxxxxxxxxxxx.a(
                     this.b(),
                     (float)((int)((_snowmanxxxxxxxxxxxxxxxxxxxxxxxx * _snowmanxxxxxxxxxxxxxxxxxxxxxxxx + _snowmanxxxxxxxxxxxxxxxxxxxxxxxx) / 2.0 * 7.0 * (double)_snowmanxx + 1.0))
                  );
                  double _snowmanxxxxxxxxxxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxxxxxxxxxxxxx;
                  if (_snowmanxxxxxxxxxxxxx instanceof aqm) {
                     _snowmanxxxxxxxxxxxxxxxxxxxxxxxxx = bqf.a((aqm)_snowmanxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxxxxxxxxxxx);
                  }

                  _snowmanxxxxxxxxxxxxx.f(
                     _snowmanxxxxxxxxxxxxx.cC()
                        .b(
                           _snowmanxxxxxxxxxxxxxxxxxxx * _snowmanxxxxxxxxxxxxxxxxxxxxxxxxx,
                           _snowmanxxxxxxxxxxxxxxxxxxxx * _snowmanxxxxxxxxxxxxxxxxxxxxxxxxx,
                           _snowmanxxxxxxxxxxxxxxxxxxxxx * _snowmanxxxxxxxxxxxxxxxxxxxxxxxxx
                        )
                  );
                  if (_snowmanxxxxxxxxxxxxx instanceof bfw) {
                     bfw _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxx = (bfw)_snowmanxxxxxxxxxxxxx;
                     if (!_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxx.a_() && (!_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxx.b_() || !_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxx.bC.b)) {
                        this.n
                           .put(
                              _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxx,
                              new dcn(
                                 _snowmanxxxxxxxxxxxxxxxxxxx * _snowmanxxxxxxxxxxxxxxxxxxxxxxxx,
                                 _snowmanxxxxxxxxxxxxxxxxxxxx * _snowmanxxxxxxxxxxxxxxxxxxxxxxxx,
                                 _snowmanxxxxxxxxxxxxxxxxxxxxx * _snowmanxxxxxxxxxxxxxxxxxxxxxxxx
                              )
                           );
                     }
                  }
               }
            }
         }
      }
   }

   public void a(boolean var1) {
      if (this.e.v) {
         this.e.a(this.f, this.g, this.h, adq.eL, adr.e, 4.0F, (1.0F + (this.e.t.nextFloat() - this.e.t.nextFloat()) * 0.2F) * 0.7F, false);
      }

      boolean _snowman = this.c != brp.a.a;
      if (_snowman) {
         if (!(this.j < 2.0F) && _snowman) {
            this.e.a(hh.v, this.f, this.g, this.h, 1.0, 0.0, 0.0);
         } else {
            this.e.a(hh.w, this.f, this.g, this.h, 1.0, 0.0, 0.0);
         }
      }

      if (_snowman) {
         ObjectArrayList<Pair<bmb, fx>> _snowmanx = new ObjectArrayList();
         Collections.shuffle(this.m, this.e.t);

         for (fx _snowmanxx : this.m) {
            ceh _snowmanxxx = this.e.d_(_snowmanxx);
            buo _snowmanxxxx = _snowmanxxx.b();
            if (!_snowmanxxx.g()) {
               fx _snowmanxxxxx = _snowmanxx.h();
               this.e.Z().a("explosion_blocks");
               if (_snowmanxxxx.a(this) && this.e instanceof aag) {
                  ccj _snowmanxxxxxx = _snowmanxxxx.q() ? this.e.c(_snowmanxx) : null;
                  cyv.a _snowmanxxxxxxx = new cyv.a((aag)this.e).a(this.e.t).a(dbc.f, dcn.a(_snowmanxx)).a(dbc.i, bmb.b).b(dbc.h, _snowmanxxxxxx).b(dbc.a, this.i);
                  if (this.c == brp.a.c) {
                     _snowmanxxxxxxx.a(dbc.j, this.j);
                  }

                  _snowmanxxx.a(_snowmanxxxxxxx).forEach(var2x -> a(_snowman, var2x, _snowman));
               }

               this.e.a(_snowmanxx, bup.a.n(), 3);
               _snowmanxxxx.a(this.e, _snowmanxx, this);
               this.e.Z().c();
            }
         }

         ObjectListIterator var12 = _snowmanx.iterator();

         while (var12.hasNext()) {
            Pair<bmb, fx> _snowmanxxx = (Pair<bmb, fx>)var12.next();
            buo.a(this.e, (fx)_snowmanxxx.getSecond(), (bmb)_snowmanxxx.getFirst());
         }
      }

      if (this.b) {
         for (fx _snowmanx : this.m) {
            if (this.d.nextInt(3) == 0 && this.e.d_(_snowmanx).g() && this.e.d_(_snowmanx.c()).i(this.e, _snowmanx.c())) {
               this.e.a(_snowmanx, bue.a(this.e, _snowmanx));
            }
         }
      }
   }

   private static void a(ObjectArrayList<Pair<bmb, fx>> var0, bmb var1, fx var2) {
      int _snowman = _snowman.size();

      for (int _snowmanx = 0; _snowmanx < _snowman; _snowmanx++) {
         Pair<bmb, fx> _snowmanxx = (Pair<bmb, fx>)_snowman.get(_snowmanx);
         bmb _snowmanxxx = (bmb)_snowmanxx.getFirst();
         if (bcv.a(_snowmanxxx, _snowman)) {
            bmb _snowmanxxxx = bcv.a(_snowmanxxx, _snowman, 16);
            _snowman.set(_snowmanx, Pair.of(_snowmanxxxx, _snowmanxx.getSecond()));
            if (_snowman.a()) {
               return;
            }
         }
      }

      _snowman.add(Pair.of(_snowman, _snowman));
   }

   public apk b() {
      return this.k;
   }

   public Map<bfw, dcn> c() {
      return this.n;
   }

   @Nullable
   public aqm d() {
      if (this.i == null) {
         return null;
      } else if (this.i instanceof bcw) {
         return ((bcw)this.i).g();
      } else if (this.i instanceof aqm) {
         return (aqm)this.i;
      } else {
         if (this.i instanceof bgm) {
            aqa _snowman = ((bgm)this.i).v();
            if (_snowman instanceof aqm) {
               return (aqm)_snowman;
            }
         }

         return null;
      }
   }

   public void e() {
      this.m.clear();
   }

   public List<fx> f() {
      return this.m;
   }

   public static enum a {
      a,
      b,
      c;

      private a() {
      }
   }
}
