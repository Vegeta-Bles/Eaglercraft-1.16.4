import com.google.common.collect.Lists;
import com.google.common.primitives.Doubles;
import com.google.common.primitives.Floats;
import com.mojang.brigadier.ParseResults;
import com.mojang.brigadier.StringReader;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;
import it.unimi.dsi.fastutil.ints.Int2ShortMap;
import it.unimi.dsi.fastutil.ints.Int2ShortOpenHashMap;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.annotation.Nullable;
import net.minecraft.server.MinecraftServer;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class aay implements sa {
   private static final Logger c = LogManager.getLogger();
   public final nd a;
   private final MinecraftServer d;
   public aah b;
   private int e;
   private long f;
   private boolean g;
   private long h;
   private int i;
   private int j;
   private final Int2ShortMap k = new Int2ShortOpenHashMap();
   private double l;
   private double m;
   private double n;
   private double o;
   private double p;
   private double q;
   private aqa r;
   private double s;
   private double t;
   private double u;
   private double v;
   private double w;
   private double x;
   private dcn y;
   private int z;
   private int A;
   private boolean B;
   private int C;
   private boolean D;
   private int E;
   private int F;
   private int G;

   public aay(MinecraftServer var1, nd var2, aah var3) {
      this.d = _snowman;
      this.a = _snowman;
      _snowman.a(this);
      this.b = _snowman;
      _snowman.b = this;
      abc _snowman = _snowman.Q();
      if (_snowman != null) {
         _snowman.a();
      }
   }

   public void b() {
      this.c();
      this.b.m = this.b.cD();
      this.b.n = this.b.cE();
      this.b.o = this.b.cH();
      this.b.v_();
      this.b.a(this.l, this.m, this.n, this.b.p, this.b.q);
      this.e++;
      this.G = this.F;
      if (this.B && !this.b.em()) {
         if (++this.C > 80) {
            c.warn("{} was kicked for floating too long!", this.b.R().getString());
            this.b(new of("multiplayer.disconnect.flying"));
            return;
         }
      } else {
         this.B = false;
         this.C = 0;
      }

      this.r = this.b.cr();
      if (this.r != this.b && this.r.cm() == this.b) {
         this.s = this.r.cD();
         this.t = this.r.cE();
         this.u = this.r.cH();
         this.v = this.r.cD();
         this.w = this.r.cE();
         this.x = this.r.cH();
         if (this.D && this.b.cr().cm() == this.b) {
            if (++this.E > 80) {
               c.warn("{} was kicked for floating a vehicle too long!", this.b.R().getString());
               this.b(new of("multiplayer.disconnect.flying"));
               return;
            }
         } else {
            this.D = false;
            this.E = 0;
         }
      } else {
         this.r = null;
         this.D = false;
         this.E = 0;
      }

      this.d.aQ().a("keepAlive");
      long _snowman = x.b();
      if (_snowman - this.f >= 15000L) {
         if (this.g) {
            this.b(new of("disconnect.timeout"));
         } else {
            this.g = true;
            this.f = _snowman;
            this.h = _snowman;
            this.a(new ps(this.h));
         }
      }

      this.d.aQ().c();
      if (this.i > 0) {
         this.i--;
      }

      if (this.j > 0) {
         this.j--;
      }

      if (this.b.F() > 0L && this.d.ao() > 0 && x.b() - this.b.F() > (long)(this.d.ao() * 1000 * 60)) {
         this.b(new of("multiplayer.disconnect.idling"));
      }
   }

   public void c() {
      this.l = this.b.cD();
      this.m = this.b.cE();
      this.n = this.b.cH();
      this.o = this.b.cD();
      this.p = this.b.cE();
      this.q = this.b.cH();
   }

   @Override
   public nd a() {
      return this.a;
   }

   private boolean d() {
      return this.d.a(this.b.eA());
   }

   public void b(nr var1) {
      this.a.a(new pm(_snowman), var2 -> this.a.a(_snowman));
      this.a.l();
      this.d.g(this.a::m);
   }

   private <T> void a(T var1, Consumer<T> var2, BiFunction<abc, T, CompletableFuture<Optional<T>>> var3) {
      aob<?> _snowman = this.b.u().l();
      Consumer<T> _snowmanx = var2x -> {
         if (this.a().h()) {
            _snowman.accept(var2x);
         } else {
            c.debug("Ignoring packet due to disconnection");
         }
      };
      abc _snowmanxx = this.b.Q();
      if (_snowmanxx != null) {
         _snowman.apply(_snowmanxx, _snowman).thenAcceptAsync(var1x -> var1x.ifPresent(_snowman), _snowman);
      } else {
         _snowman.execute(() -> _snowman.accept(_snowman));
      }
   }

   private void a(String var1, Consumer<String> var2) {
      this.a(_snowman, _snowman, abc::a);
   }

   private void a(List<String> var1, Consumer<List<String>> var2) {
      this.a(_snowman, _snowman, abc::a);
   }

   @Override
   public void a(tb var1) {
      ol.a(_snowman, this, this.b.u());
      this.b.a(_snowman.b(), _snowman.c(), _snowman.d(), _snowman.e());
   }

   private static boolean b(st var0) {
      return Doubles.isFinite(_snowman.a(0.0)) && Doubles.isFinite(_snowman.b(0.0)) && Doubles.isFinite(_snowman.c(0.0)) && Floats.isFinite(_snowman.b(0.0F)) && Floats.isFinite(_snowman.a(0.0F))
         ? Math.abs(_snowman.a(0.0)) > 3.0E7 || Math.abs(_snowman.b(0.0)) > 3.0E7 || Math.abs(_snowman.c(0.0)) > 3.0E7
         : true;
   }

   private static boolean b(su var0) {
      return !Doubles.isFinite(_snowman.b()) || !Doubles.isFinite(_snowman.c()) || !Doubles.isFinite(_snowman.d()) || !Floats.isFinite(_snowman.f()) || !Floats.isFinite(_snowman.e());
   }

   @Override
   public void a(su var1) {
      ol.a(_snowman, this, this.b.u());
      if (b(_snowman)) {
         this.b(new of("multiplayer.disconnect.invalid_vehicle_movement"));
      } else {
         aqa _snowman = this.b.cr();
         if (_snowman != this.b && _snowman.cm() == this.b && _snowman == this.r) {
            aag _snowmanx = this.b.u();
            double _snowmanxx = _snowman.cD();
            double _snowmanxxx = _snowman.cE();
            double _snowmanxxxx = _snowman.cH();
            double _snowmanxxxxx = _snowman.b();
            double _snowmanxxxxxx = _snowman.c();
            double _snowmanxxxxxxx = _snowman.d();
            float _snowmanxxxxxxxx = _snowman.e();
            float _snowmanxxxxxxxxx = _snowman.f();
            double _snowmanxxxxxxxxxx = _snowmanxxxxx - this.s;
            double _snowmanxxxxxxxxxxx = _snowmanxxxxxx - this.t;
            double _snowmanxxxxxxxxxxxx = _snowmanxxxxxxx - this.u;
            double _snowmanxxxxxxxxxxxxx = _snowman.cC().g();
            double _snowmanxxxxxxxxxxxxxx = _snowmanxxxxxxxxxx * _snowmanxxxxxxxxxx + _snowmanxxxxxxxxxxx * _snowmanxxxxxxxxxxx + _snowmanxxxxxxxxxxxx * _snowmanxxxxxxxxxxxx;
            if (_snowmanxxxxxxxxxxxxxx - _snowmanxxxxxxxxxxxxx > 100.0 && !this.d()) {
               c.warn("{} (vehicle of {}) moved too quickly! {},{},{}", _snowman.R().getString(), this.b.R().getString(), _snowmanxxxxxxxxxx, _snowmanxxxxxxxxxxx, _snowmanxxxxxxxxxxxx);
               this.a.a(new qb(_snowman));
               return;
            }

            boolean _snowmanxxxxxxxxxxxxxxx = _snowmanx.a_(_snowman, _snowman.cc().h(0.0625));
            _snowmanxxxxxxxxxx = _snowmanxxxxx - this.v;
            _snowmanxxxxxxxxxxx = _snowmanxxxxxx - this.w - 1.0E-6;
            _snowmanxxxxxxxxxxxx = _snowmanxxxxxxx - this.x;
            _snowman.a(aqr.b, new dcn(_snowmanxxxxxxxxxx, _snowmanxxxxxxxxxxx, _snowmanxxxxxxxxxxxx));
            _snowmanxxxxxxxxxx = _snowmanxxxxx - _snowman.cD();
            _snowmanxxxxxxxxxxx = _snowmanxxxxxx - _snowman.cE();
            if (_snowmanxxxxxxxxxxx > -0.5 || _snowmanxxxxxxxxxxx < 0.5) {
               _snowmanxxxxxxxxxxx = 0.0;
            }

            _snowmanxxxxxxxxxxxx = _snowmanxxxxxxx - _snowman.cH();
            _snowmanxxxxxxxxxxxxxx = _snowmanxxxxxxxxxx * _snowmanxxxxxxxxxx + _snowmanxxxxxxxxxxx * _snowmanxxxxxxxxxxx + _snowmanxxxxxxxxxxxx * _snowmanxxxxxxxxxxxx;
            boolean _snowmanxxxxxxxxxxxxxxxx = false;
            if (_snowmanxxxxxxxxxxxxxx > 0.0625) {
               _snowmanxxxxxxxxxxxxxxxx = true;
               c.warn("{} (vehicle of {}) moved wrongly! {}", _snowman.R().getString(), this.b.R().getString(), Math.sqrt(_snowmanxxxxxxxxxxxxxx));
            }

            _snowman.a(_snowmanxxxxx, _snowmanxxxxxx, _snowmanxxxxxxx, _snowmanxxxxxxxx, _snowmanxxxxxxxxx);
            boolean _snowmanxxxxxxxxxxxxxxxxx = _snowmanx.a_(_snowman, _snowman.cc().h(0.0625));
            if (_snowmanxxxxxxxxxxxxxxx && (_snowmanxxxxxxxxxxxxxxxx || !_snowmanxxxxxxxxxxxxxxxxx)) {
               _snowman.a(_snowmanxx, _snowmanxxx, _snowmanxxxx, _snowmanxxxxxxxx, _snowmanxxxxxxxxx);
               this.a.a(new qb(_snowman));
               return;
            }

            this.b.u().i().a(this.b);
            this.b.p(this.b.cD() - _snowmanxx, this.b.cE() - _snowmanxxx, this.b.cH() - _snowmanxxxx);
            this.D = _snowmanxxxxxxxxxxx >= -0.03125 && !this.d.aa() && this.a(_snowman);
            this.v = _snowman.cD();
            this.w = _snowman.cE();
            this.x = _snowman.cH();
         }
      }
   }

   private boolean a(aqa var1) {
      return _snowman.l.a(_snowman.cc().g(0.0625).b(0.0, -0.55, 0.0)).allMatch(ceg.a::g);
   }

   @Override
   public void a(sb var1) {
      ol.a(_snowman, this, this.b.u());
      if (_snowman.b() == this.z) {
         this.b.a(this.y.b, this.y.c, this.y.d, this.b.p, this.b.q);
         this.o = this.y.b;
         this.p = this.y.c;
         this.q = this.y.d;
         if (this.b.H()) {
            this.b.I();
         }

         this.y = null;
      }
   }

   @Override
   public void a(td var1) {
      ol.a(_snowman, this, this.b.u());
      this.d.aF().a(_snowman.b()).ifPresent(this.b.B()::e);
   }

   @Override
   public void a(tc var1) {
      ol.a(_snowman, this, this.b.u());
      this.b.B().a(_snowman.b(), _snowman.c(), _snowman.d());
   }

   @Override
   public void a(tg var1) {
      ol.a(_snowman, this, this.b.u());
      if (_snowman.c() == tg.a.a) {
         vk _snowman = _snowman.d();
         y _snowmanx = this.d.aA().a(_snowman);
         if (_snowmanx != null) {
            this.b.J().a(_snowmanx);
         }
      }
   }

   @Override
   public void a(sh var1) {
      ol.a(_snowman, this, this.b.u());
      StringReader _snowman = new StringReader(_snowman.c());
      if (_snowman.canRead() && _snowman.peek() == '/') {
         _snowman.skip();
      }

      ParseResults<db> _snowmanx = this.d.aD().a().parse(_snowman, this.b.cw());
      this.d.aD().a().getCompletionSuggestions(_snowmanx).thenAccept(var2x -> this.a.a(new pc(_snowman.b(), var2x)));
   }

   @Override
   public void a(tk var1) {
      ol.a(_snowman, this, this.b.u());
      if (!this.d.m()) {
         this.b.a(new of("advMode.notEnabled"), x.b);
      } else if (!this.b.eV()) {
         this.b.a(new of("advMode.notAllowed"), x.b);
      } else {
         bqy _snowman = null;
         cco _snowmanx = null;
         fx _snowmanxx = _snowman.b();
         ccj _snowmanxxx = this.b.l.c(_snowmanxx);
         if (_snowmanxxx instanceof cco) {
            _snowmanx = (cco)_snowmanxxx;
            _snowman = _snowmanx.d();
         }

         String _snowmanxxxx = _snowman.c();
         boolean _snowmanxxxxx = _snowman.d();
         if (_snowman != null) {
            cco.a _snowmanxxxxxx = _snowmanx.m();
            gc _snowmanxxxxxxx = this.b.l.d_(_snowmanxx).c(bvi.a);
            switch (_snowman.g()) {
               case a:
                  ceh _snowmanxxxxxxxx = bup.iH.n();
                  this.b.l.a(_snowmanxx, _snowmanxxxxxxxx.a(bvi.a, _snowmanxxxxxxx).a(bvi.b, Boolean.valueOf(_snowman.e())), 2);
                  break;
               case b:
                  ceh _snowmanxxxxxxxxx = bup.iG.n();
                  this.b.l.a(_snowmanxx, _snowmanxxxxxxxxx.a(bvi.a, _snowmanxxxxxxx).a(bvi.b, Boolean.valueOf(_snowman.e())), 2);
                  break;
               case c:
               default:
                  ceh _snowmanxxxxxxxxxx = bup.er.n();
                  this.b.l.a(_snowmanxx, _snowmanxxxxxxxxxx.a(bvi.a, _snowmanxxxxxxx).a(bvi.b, Boolean.valueOf(_snowman.e())), 2);
            }

            _snowmanxxx.r();
            this.b.l.a(_snowmanxx, _snowmanxxx);
            _snowman.a(_snowmanxxxx);
            _snowman.a(_snowmanxxxxx);
            if (!_snowmanxxxxx) {
               _snowman.b(null);
            }

            _snowmanx.b(_snowman.f());
            if (_snowmanxxxxxx != _snowman.g()) {
               _snowmanx.h();
            }

            _snowman.e();
            if (!aft.b(_snowmanxxxx)) {
               this.b.a(new of("advMode.setCommand.success", _snowmanxxxx), x.b);
            }
         }
      }
   }

   @Override
   public void a(tl var1) {
      ol.a(_snowman, this, this.b.u());
      if (!this.d.m()) {
         this.b.a(new of("advMode.notEnabled"), x.b);
      } else if (!this.b.eV()) {
         this.b.a(new of("advMode.notAllowed"), x.b);
      } else {
         bqy _snowman = _snowman.a(this.b.l);
         if (_snowman != null) {
            _snowman.a(_snowman.b());
            _snowman.a(_snowman.c());
            if (!_snowman.c()) {
               _snowman.b(null);
            }

            _snowman.e();
            this.b.a(new of("advMode.setCommand.success", _snowman.b()), x.b);
         }
      }
   }

   @Override
   public void a(sw var1) {
      ol.a(_snowman, this, this.b.u());
      this.b.bm.c(_snowman.b());
      this.b.b.a(new pi(-2, this.b.bm.d, this.b.bm.a(this.b.bm.d)));
      this.b.b.a(new pi(-2, _snowman.b(), this.b.bm.a(_snowman.b())));
      this.b.b.a(new qv(this.b.bm.d));
   }

   @Override
   public void a(te var1) {
      ol.a(_snowman, this, this.b.u());
      if (this.b.bp instanceof bie) {
         bie _snowman = (bie)this.b.bp;
         String _snowmanx = w.a(_snowman.b());
         if (_snowmanx.length() <= 35) {
            _snowman.a(_snowmanx);
         }
      }
   }

   @Override
   public void a(ti var1) {
      ol.a(_snowman, this, this.b.u());
      if (this.b.bp instanceof bif) {
         ((bif)this.b.bp).c(_snowman.b(), _snowman.c());
      }
   }

   @Override
   public void a(to var1) {
      ol.a(_snowman, this, this.b.u());
      if (this.b.eV()) {
         fx _snowman = _snowman.b();
         ceh _snowmanx = this.b.l.d_(_snowman);
         ccj _snowmanxx = this.b.l.c(_snowman);
         if (_snowmanxx instanceof cdj) {
            cdj _snowmanxxx = (cdj)_snowmanxx;
            _snowmanxxx.a(_snowman.d());
            _snowmanxxx.a(_snowman.e());
            _snowmanxxx.b(_snowman.f());
            _snowmanxxx.c(_snowman.g());
            _snowmanxxx.b(_snowman.h());
            _snowmanxxx.b(_snowman.i());
            _snowmanxxx.b(_snowman.j());
            _snowmanxxx.a(_snowman.k());
            _snowmanxxx.d(_snowman.l());
            _snowmanxxx.e(_snowman.m());
            _snowmanxxx.a(_snowman.n());
            _snowmanxxx.a(_snowman.o());
            if (_snowmanxxx.g()) {
               String _snowmanxxxx = _snowmanxxx.d();
               if (_snowman.c() == cdj.a.b) {
                  if (_snowmanxxx.D()) {
                     this.b.a(new of("structure_block.save_success", _snowmanxxxx), false);
                  } else {
                     this.b.a(new of("structure_block.save_failure", _snowmanxxxx), false);
                  }
               } else if (_snowman.c() == cdj.a.c) {
                  if (!_snowmanxxx.F()) {
                     this.b.a(new of("structure_block.load_not_found", _snowmanxxxx), false);
                  } else if (_snowmanxxx.a(this.b.u())) {
                     this.b.a(new of("structure_block.load_success", _snowmanxxxx), false);
                  } else {
                     this.b.a(new of("structure_block.load_prepare", _snowmanxxxx), false);
                  }
               } else if (_snowman.c() == cdj.a.d) {
                  if (_snowmanxxx.C()) {
                     this.b.a(new of("structure_block.size_success", _snowmanxxxx), false);
                  } else {
                     this.b.a(new of("structure_block.size_failure"), false);
                  }
               }
            } else {
               this.b.a(new of("structure_block.invalid_structure_name", _snowman.e()), false);
            }

            _snowmanxxx.X_();
            this.b.l.a(_snowman, _snowmanx, _snowmanx, 3);
         }
      }
   }

   @Override
   public void a(tn var1) {
      ol.a(_snowman, this, this.b.u());
      if (this.b.eV()) {
         fx _snowman = _snowman.b();
         ceh _snowmanx = this.b.l.d_(_snowman);
         ccj _snowmanxx = this.b.l.c(_snowman);
         if (_snowmanxx instanceof ccz) {
            ccz _snowmanxxx = (ccz)_snowmanxx;
            _snowmanxxx.a(_snowman.c());
            _snowmanxxx.b(_snowman.d());
            _snowmanxxx.c(_snowman.e());
            _snowmanxxx.a(_snowman.f());
            _snowmanxxx.a(_snowman.g());
            _snowmanxxx.X_();
            this.b.l.a(_snowman, _snowmanx, _snowmanx, 3);
         }
      }
   }

   @Override
   public void a(sq var1) {
      ol.a(_snowman, this, this.b.u());
      if (this.b.eV()) {
         fx _snowman = _snowman.b();
         ccj _snowmanx = this.b.l.c(_snowman);
         if (_snowmanx instanceof ccz) {
            ccz _snowmanxx = (ccz)_snowmanx;
            _snowmanxx.a(this.b.u(), _snowman.c(), _snowman.d());
         }
      }
   }

   @Override
   public void a(th var1) {
      ol.a(_snowman, this, this.b.u());
      int _snowman = _snowman.b();
      bic _snowmanx = this.b.bp;
      if (_snowmanx instanceof bjg) {
         bjg _snowmanxx = (bjg)_snowmanx;
         _snowmanxx.d(_snowman);
         _snowmanxx.g(_snowman);
      }
   }

   @Override
   public void a(sn var1) {
      bmb _snowman = _snowman.b();
      if (_snowman.b() == bmd.oT) {
         md _snowmanx = _snowman.o();
         if (bnr.a(_snowmanx)) {
            List<String> _snowmanxx = Lists.newArrayList();
            boolean _snowmanxxx = _snowman.c();
            if (_snowmanxxx) {
               _snowmanxx.add(_snowmanx.l("title"));
            }

            mj _snowmanxxxx = _snowmanx.d("pages", 8);

            for (int _snowmanxxxxx = 0; _snowmanxxxxx < _snowmanxxxx.size(); _snowmanxxxxx++) {
               _snowmanxx.add(_snowmanxxxx.j(_snowmanxxxxx));
            }

            int _snowmanxxxxx = _snowman.d();
            if (bfv.d(_snowmanxxxxx) || _snowmanxxxxx == 40) {
               this.a(_snowmanxx, _snowmanxxx ? var2x -> this.a(var2x.get(0), var2x.subList(1, var2x.size()), _snowman) : var2x -> this.a(var2x, _snowman));
            }
         }
      }
   }

   private void a(List<String> var1, int var2) {
      bmb _snowman = this.b.bm.a(_snowman);
      if (_snowman.b() == bmd.oT) {
         mj _snowmanx = new mj();
         _snowman.stream().map(ms::a).forEach(_snowmanx::add);
         _snowman.a("pages", _snowmanx);
      }
   }

   private void a(String var1, List<String> var2, int var3) {
      bmb _snowman = this.b.bm.a(_snowman);
      if (_snowman.b() == bmd.oT) {
         bmb _snowmanx = new bmb(bmd.oU);
         md _snowmanxx = _snowman.o();
         if (_snowmanxx != null) {
            _snowmanx.c(_snowmanxx.g());
         }

         _snowmanx.a("author", ms.a(this.b.R().getString()));
         _snowmanx.a("title", ms.a(_snowman));
         mj _snowmanxxx = new mj();

         for (String _snowmanxxxx : _snowman) {
            nr _snowmanxxxxx = new oe(_snowmanxxxx);
            String _snowmanxxxxxx = nr.a.a(_snowmanxxxxx);
            _snowmanxxx.add(ms.a(_snowmanxxxxxx));
         }

         _snowmanx.a("pages", _snowmanxxx);
         this.b.bm.a(_snowman, _snowmanx);
      }
   }

   @Override
   public void a(so var1) {
      ol.a(_snowman, this, this.b.u());
      if (this.b.k(2)) {
         aqa _snowman = this.b.u().a(_snowman.c());
         if (_snowman != null) {
            md _snowmanx = _snowman.e(new md());
            this.b.b.a(new rq(_snowman.b(), _snowmanx));
         }
      }
   }

   @Override
   public void a(sc var1) {
      ol.a(_snowman, this, this.b.u());
      if (this.b.k(2)) {
         ccj _snowman = this.b.u().c(_snowman.c());
         md _snowmanx = _snowman != null ? _snowman.a(new md()) : null;
         this.b.b.a(new rq(_snowman.b(), _snowmanx));
      }
   }

   @Override
   public void a(st var1) {
      ol.a(_snowman, this, this.b.u());
      if (b(_snowman)) {
         this.b(new of("multiplayer.disconnect.invalid_player_movement"));
      } else {
         aag _snowman = this.b.u();
         if (!this.b.g) {
            if (this.e == 0) {
               this.c();
            }

            if (this.y != null) {
               if (this.e - this.A > 20) {
                  this.A = this.e;
                  this.a(this.y.b, this.y.c, this.y.d, this.b.p, this.b.q);
               }
            } else {
               this.A = this.e;
               if (this.b.br()) {
                  this.b.a(this.b.cD(), this.b.cE(), this.b.cH(), _snowman.a(this.b.p), _snowman.b(this.b.q));
                  this.b.u().i().a(this.b);
               } else {
                  double _snowmanx = this.b.cD();
                  double _snowmanxx = this.b.cE();
                  double _snowmanxxx = this.b.cH();
                  double _snowmanxxxx = this.b.cE();
                  double _snowmanxxxxx = _snowman.a(this.b.cD());
                  double _snowmanxxxxxx = _snowman.b(this.b.cE());
                  double _snowmanxxxxxxx = _snowman.c(this.b.cH());
                  float _snowmanxxxxxxxx = _snowman.a(this.b.p);
                  float _snowmanxxxxxxxxx = _snowman.b(this.b.q);
                  double _snowmanxxxxxxxxxx = _snowmanxxxxx - this.l;
                  double _snowmanxxxxxxxxxxx = _snowmanxxxxxx - this.m;
                  double _snowmanxxxxxxxxxxxx = _snowmanxxxxxxx - this.n;
                  double _snowmanxxxxxxxxxxxxx = this.b.cC().g();
                  double _snowmanxxxxxxxxxxxxxx = _snowmanxxxxxxxxxx * _snowmanxxxxxxxxxx + _snowmanxxxxxxxxxxx * _snowmanxxxxxxxxxxx + _snowmanxxxxxxxxxxxx * _snowmanxxxxxxxxxxxx;
                  if (this.b.em()) {
                     if (_snowmanxxxxxxxxxxxxxx > 1.0) {
                        this.a(this.b.cD(), this.b.cE(), this.b.cH(), _snowman.a(this.b.p), _snowman.b(this.b.q));
                     }
                  } else {
                     this.F++;
                     int _snowmanxxxxxxxxxxxxxxx = this.F - this.G;
                     if (_snowmanxxxxxxxxxxxxxxx > 5) {
                        c.debug("{} is sending move packets too frequently ({} packets since last tick)", this.b.R().getString(), _snowmanxxxxxxxxxxxxxxx);
                        _snowmanxxxxxxxxxxxxxxx = 1;
                     }

                     if (!this.b.H() && (!this.b.u().V().b(brt.r) || !this.b.ef())) {
                        float _snowmanxxxxxxxxxxxxxxxx = this.b.ef() ? 300.0F : 100.0F;
                        if (_snowmanxxxxxxxxxxxxxx - _snowmanxxxxxxxxxxxxx > (double)(_snowmanxxxxxxxxxxxxxxxx * (float)_snowmanxxxxxxxxxxxxxxx) && !this.d()) {
                           c.warn("{} moved too quickly! {},{},{}", this.b.R().getString(), _snowmanxxxxxxxxxx, _snowmanxxxxxxxxxxx, _snowmanxxxxxxxxxxxx);
                           this.a(this.b.cD(), this.b.cE(), this.b.cH(), this.b.p, this.b.q);
                           return;
                        }
                     }

                     dci _snowmanxxxxxxxxxxxxxxxx = this.b.cc();
                     _snowmanxxxxxxxxxx = _snowmanxxxxx - this.o;
                     _snowmanxxxxxxxxxxx = _snowmanxxxxxx - this.p;
                     _snowmanxxxxxxxxxxxx = _snowmanxxxxxxx - this.q;
                     boolean _snowmanxxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxx > 0.0;
                     if (this.b.ao() && !_snowman.b() && _snowmanxxxxxxxxxxxxxxxxx) {
                        this.b.dK();
                     }

                     this.b.a(aqr.b, new dcn(_snowmanxxxxxxxxxx, _snowmanxxxxxxxxxxx, _snowmanxxxxxxxxxxxx));
                     _snowmanxxxxxxxxxx = _snowmanxxxxx - this.b.cD();
                     _snowmanxxxxxxxxxxx = _snowmanxxxxxx - this.b.cE();
                     if (_snowmanxxxxxxxxxxx > -0.5 || _snowmanxxxxxxxxxxx < 0.5) {
                        _snowmanxxxxxxxxxxx = 0.0;
                     }

                     _snowmanxxxxxxxxxxxx = _snowmanxxxxxxx - this.b.cH();
                     _snowmanxxxxxxxxxxxxxx = _snowmanxxxxxxxxxx * _snowmanxxxxxxxxxx + _snowmanxxxxxxxxxxx * _snowmanxxxxxxxxxxx + _snowmanxxxxxxxxxxxx * _snowmanxxxxxxxxxxxx;
                     boolean _snowmanxxxxxxxxxxxxxxxxxx = false;
                     if (!this.b.H() && _snowmanxxxxxxxxxxxxxx > 0.0625 && !this.b.em() && !this.b.d.e() && this.b.d.b() != bru.e) {
                        _snowmanxxxxxxxxxxxxxxxxxx = true;
                        c.warn("{} moved wrongly!", this.b.R().getString());
                     }

                     this.b.a(_snowmanxxxxx, _snowmanxxxxxx, _snowmanxxxxxxx, _snowmanxxxxxxxx, _snowmanxxxxxxxxx);
                     if (this.b.H || this.b.em() || (!_snowmanxxxxxxxxxxxxxxxxxx || !_snowman.a_(this.b, _snowmanxxxxxxxxxxxxxxxx)) && !this.a(_snowman, _snowmanxxxxxxxxxxxxxxxx)) {
                        this.B = _snowmanxxxxxxxxxxx >= -0.03125
                           && this.b.d.b() != bru.e
                           && !this.d.aa()
                           && !this.b.bC.c
                           && !this.b.a(apw.y)
                           && !this.b.ef()
                           && this.a(this.b);
                        this.b.u().i().a(this.b);
                        this.b.a(this.b.cE() - _snowmanxxxx, _snowman.b());
                        this.b.c(_snowman.b());
                        if (_snowmanxxxxxxxxxxxxxxxxx) {
                           this.b.C = 0.0F;
                        }

                        this.b.p(this.b.cD() - _snowmanx, this.b.cE() - _snowmanxx, this.b.cH() - _snowmanxxx);
                        this.o = this.b.cD();
                        this.p = this.b.cE();
                        this.q = this.b.cH();
                     } else {
                        this.a(_snowmanx, _snowmanxx, _snowmanxxx, _snowmanxxxxxxxx, _snowmanxxxxxxxxx);
                     }
                  }
               }
            }
         }
      }
   }

   private boolean a(brz var1, dci var2) {
      Stream<ddh> _snowman = _snowman.d(this.b, this.b.cc().h(1.0E-5F), var0 -> true);
      ddh _snowmanx = dde.a(_snowman.h(1.0E-5F));
      return _snowman.anyMatch(var1x -> !dde.c(var1x, _snowman, dcr.i));
   }

   public void a(double var1, double var3, double var5, float var7, float var8) {
      this.a(_snowman, _snowman, _snowman, _snowman, _snowman, Collections.emptySet());
   }

   public void a(double var1, double var3, double var5, float var7, float var8, Set<qk.a> var9) {
      double _snowman = _snowman.contains(qk.a.a) ? this.b.cD() : 0.0;
      double _snowmanx = _snowman.contains(qk.a.b) ? this.b.cE() : 0.0;
      double _snowmanxx = _snowman.contains(qk.a.c) ? this.b.cH() : 0.0;
      float _snowmanxxx = _snowman.contains(qk.a.d) ? this.b.p : 0.0F;
      float _snowmanxxxx = _snowman.contains(qk.a.e) ? this.b.q : 0.0F;
      this.y = new dcn(_snowman, _snowman, _snowman);
      if (++this.z == Integer.MAX_VALUE) {
         this.z = 0;
      }

      this.A = this.e;
      this.b.a(_snowman, _snowman, _snowman, _snowman, _snowman);
      this.b.b.a(new qk(_snowman - _snowman, _snowman - _snowmanx, _snowman - _snowmanxx, _snowman - _snowmanxxx, _snowman - _snowmanxxxx, _snowman, this.z));
   }

   @Override
   public void a(sz var1) {
      ol.a(_snowman, this, this.b.u());
      fx _snowman = _snowman.b();
      this.b.z();
      sz.a _snowmanx = _snowman.d();
      switch (_snowmanx) {
         case g:
            if (!this.b.a_()) {
               bmb _snowmanxx = this.b.b(aot.b);
               this.b.a(aot.b, this.b.b(aot.a));
               this.b.a(aot.a, _snowmanxx);
               this.b.ec();
            }

            return;
         case e:
            if (!this.b.a_()) {
               this.b.a(false);
            }

            return;
         case d:
            if (!this.b.a_()) {
               this.b.a(true);
            }

            return;
         case f:
            this.b.eb();
            return;
         case a:
         case b:
         case c:
            this.b.d.a(_snowman, _snowmanx, _snowman.c(), this.d.ac());
            return;
         default:
            throw new IllegalArgumentException("Invalid player action");
      }
   }

   private static boolean a(aah var0, bmb var1) {
      if (_snowman.a()) {
         return false;
      } else {
         blx _snowman = _snowman.b();
         return (_snowman instanceof bkh || _snowman instanceof bko) && !_snowman.eT().a(_snowman);
      }
   }

   @Override
   public void a(ts var1) {
      ol.a(_snowman, this, this.b.u());
      aag _snowman = this.b.u();
      aot _snowmanx = _snowman.b();
      bmb _snowmanxx = this.b.b(_snowmanx);
      dcj _snowmanxxx = _snowman.c();
      fx _snowmanxxxx = _snowmanxxx.a();
      gc _snowmanxxxxx = _snowmanxxx.b();
      this.b.z();
      if (_snowmanxxxx.v() < this.d.ac()) {
         if (this.y == null && this.b.h((double)_snowmanxxxx.u() + 0.5, (double)_snowmanxxxx.v() + 0.5, (double)_snowmanxxxx.w() + 0.5) < 64.0 && _snowman.a(this.b, _snowmanxxxx)) {
            aou _snowmanxxxxxx = this.b.d.a(this.b, _snowman, _snowmanxx, _snowmanx, _snowmanxxx);
            if (_snowmanxxxxx == gc.b && !_snowmanxxxxxx.a() && _snowmanxxxx.v() >= this.d.ac() - 1 && a(this.b, _snowmanxx)) {
               nr _snowmanxxxxxxx = new of("build.tooHigh", this.d.ac()).a(k.m);
               this.b.b.a(new pb(_snowmanxxxxxxx, no.c, x.b));
            } else if (_snowmanxxxxxx.b()) {
               this.b.a(_snowmanx, true);
            }
         }
      } else {
         nr _snowmanxxxxxx = new of("build.tooHigh", this.d.ac()).a(k.m);
         this.b.b.a(new pb(_snowmanxxxxxx, no.c, x.b));
      }

      this.b.b.a(new oy(_snowman, _snowmanxxxx));
      this.b.b.a(new oy(_snowman, _snowmanxxxx.a(_snowmanxxxxx)));
   }

   @Override
   public void a(tt var1) {
      ol.a(_snowman, this, this.b.u());
      aag _snowman = this.b.u();
      aot _snowmanx = _snowman.b();
      bmb _snowmanxx = this.b.b(_snowmanx);
      this.b.z();
      if (!_snowmanxx.a()) {
         aou _snowmanxxx = this.b.d.a(this.b, _snowman, _snowmanxx, _snowmanx);
         if (_snowmanxxx.b()) {
            this.b.a(_snowmanx, true);
         }
      }
   }

   @Override
   public void a(tr var1) {
      ol.a(_snowman, this, this.b.u());
      if (this.b.a_()) {
         for (aag _snowman : this.d.G()) {
            aqa _snowmanx = _snowman.a(_snowman);
            if (_snowmanx != null) {
               this.b.a(_snowman, _snowmanx.cD(), _snowmanx.cE(), _snowmanx.cH(), _snowmanx.p, _snowmanx.q);
               return;
            }
         }
      }
   }

   @Override
   public void a(tf var1) {
   }

   @Override
   public void a(sv var1) {
      ol.a(_snowman, this, this.b.u());
      aqa _snowman = this.b.ct();
      if (_snowman instanceof bhn) {
         ((bhn)_snowman).a(_snowman.b(), _snowman.c());
      }
   }

   @Override
   public void a(nr var1) {
      c.info("{} lost connection: {}", this.b.R().getString(), _snowman.getString());
      this.d.at();
      this.d.ae().a(new of("multiplayer.player.left", this.b.d()).a(k.o), no.b, x.b);
      this.b.p();
      this.d.ae().c(this.b);
      abc _snowman = this.b.Q();
      if (_snowman != null) {
         _snowman.b();
      }

      if (this.d()) {
         c.info("Stopping singleplayer server as player logged out");
         this.d.a(false);
      }
   }

   public void a(oj<?> var1) {
      this.a(_snowman, null);
   }

   public void a(oj<?> var1, @Nullable GenericFutureListener<? extends Future<? super Void>> var2) {
      if (_snowman instanceof pb) {
         pb _snowman = (pb)_snowman;
         bfu _snowmanx = this.b.x();
         if (_snowmanx == bfu.c && _snowman.d() != no.c) {
            return;
         }

         if (_snowmanx == bfu.b && !_snowman.c()) {
            return;
         }
      }

      try {
         this.a.a(_snowman, _snowman);
      } catch (Throwable var6) {
         l _snowmanxx = l.a(var6, "Sending packet");
         m _snowmanxxx = _snowmanxx.a("Packet being sent");
         _snowmanxxx.a("Packet class", () -> _snowman.getClass().getCanonicalName());
         throw new u(_snowmanxx);
      }
   }

   @Override
   public void a(tj var1) {
      ol.a(_snowman, this, this.b.u());
      if (_snowman.b() >= 0 && _snowman.b() < bfv.g()) {
         if (this.b.bm.d != _snowman.b() && this.b.dX() == aot.a) {
            this.b.ec();
         }

         this.b.bm.d = _snowman.b();
         this.b.z();
      } else {
         c.warn("{} tried to set an invalid carried item", this.b.R().getString());
      }
   }

   @Override
   public void a(se var1) {
      String _snowman = StringUtils.normalizeSpace(_snowman.b());
      if (_snowman.startsWith("/")) {
         ol.a(_snowman, this, this.b.u());
         this.c(_snowman);
      } else {
         this.a(_snowman, this::c);
      }
   }

   private void c(String var1) {
      if (this.b.x() == bfu.c) {
         this.a(new pb(new of("chat.cannotSend").a(k.m), no.b, x.b));
      } else {
         this.b.z();

         for (int _snowman = 0; _snowman < _snowman.length(); _snowman++) {
            if (!w.a(_snowman.charAt(_snowman))) {
               this.b(new of("multiplayer.disconnect.illegal_characters"));
               return;
            }
         }

         if (_snowman.startsWith("/")) {
            this.d(_snowman);
         } else {
            nr _snowmanx = new of("chat.type.text", this.b.d(), _snowman);
            this.d.ae().a(_snowmanx, no.a, this.b.bS());
         }

         this.i += 20;
         if (this.i > 200 && !this.d.ae().h(this.b.eA())) {
            this.b(new of("disconnect.spam"));
         }
      }
   }

   private void d(String var1) {
      this.d.aD().a(this.b.cw(), _snowman);
   }

   @Override
   public void a(tq var1) {
      ol.a(_snowman, this, this.b.u());
      this.b.z();
      this.b.a(_snowman.b());
   }

   @Override
   public void a(ta var1) {
      ol.a(_snowman, this, this.b.u());
      this.b.z();
      switch (_snowman.c()) {
         case a:
            this.b.f(true);
            break;
         case b:
            this.b.f(false);
            break;
         case d:
            this.b.g(true);
            break;
         case e:
            this.b.g(false);
            break;
         case c:
            if (this.b.em()) {
               this.b.a(false, true);
               this.y = this.b.cA();
            }
            break;
         case f:
            if (this.b.ct() instanceof aqw) {
               aqw _snowman = (aqw)this.b.ct();
               int _snowmanx = _snowman.d();
               if (_snowman.P_() && _snowmanx > 0) {
                  _snowman.b(_snowmanx);
               }
            }
            break;
         case g:
            if (this.b.ct() instanceof aqw) {
               aqw _snowman = (aqw)this.b.ct();
               _snowman.c();
            }
            break;
         case h:
            if (this.b.ct() instanceof bbb) {
               ((bbb)this.b.ct()).f(this.b);
            }
            break;
         case i:
            if (!this.b.eD()) {
               this.b.eF();
            }
            break;
         default:
            throw new IllegalArgumentException("Invalid client command!");
      }
   }

   @Override
   public void a(sp var1) {
      ol.a(_snowman, this, this.b.u());
      aag _snowman = this.b.u();
      aqa _snowmanx = _snowman.a(_snowman);
      this.b.z();
      this.b.f(_snowman.e());
      if (_snowmanx != null) {
         double _snowmanxx = 36.0;
         if (this.b.h(_snowmanx) < 36.0) {
            aot _snowmanxxx = _snowman.c();
            bmb _snowmanxxxx = _snowmanxxx != null ? this.b.b(_snowmanxxx).i() : bmb.b;
            Optional<aou> _snowmanxxxxx = Optional.empty();
            if (_snowman.b() == sp.a.a) {
               _snowmanxxxxx = Optional.of(this.b.a(_snowmanx, _snowmanxxx));
            } else if (_snowman.b() == sp.a.c) {
               _snowmanxxxxx = Optional.of(_snowmanx.a(this.b, _snowman.d(), _snowmanxxx));
            } else if (_snowman.b() == sp.a.b) {
               if (_snowmanx instanceof bcv || _snowmanx instanceof aqg || _snowmanx instanceof bga || _snowmanx == this.b) {
                  this.b(new of("multiplayer.disconnect.invalid_entity_attacked"));
                  c.warn("Player {} tried to attack an invalid entity", this.b.R().getString());
                  return;
               }

               this.b.f(_snowmanx);
            }

            if (_snowmanxxxxx.isPresent() && _snowmanxxxxx.get().a()) {
               ac.P.a(this.b, _snowmanxxxx, _snowmanx);
               if (_snowmanxxxxx.get().b()) {
                  this.b.a(_snowmanxxx, true);
               }
            }
         }
      }
   }

   @Override
   public void a(sf var1) {
      ol.a(_snowman, this, this.b.u());
      this.b.z();
      sf.a _snowman = _snowman.b();
      switch (_snowman) {
         case a:
            if (this.b.g) {
               this.b.g = false;
               this.b = this.d.ae().a(this.b, true);
               ac.v.a(this.b, brx.i, brx.g);
            } else {
               if (this.b.dk() > 0.0F) {
                  return;
               }

               this.b = this.d.ae().a(this.b, false);
               if (this.d.f()) {
                  this.b.a(bru.e);
                  this.b.u().V().a(brt.p).a(false, this.d);
               }
            }
            break;
         case b:
            this.b.A().a(this.b);
      }
   }

   @Override
   public void a(sl var1) {
      ol.a(_snowman, this, this.b.u());
      this.b.o();
   }

   @Override
   public void a(sk var1) {
      ol.a(_snowman, this, this.b.u());
      this.b.z();
      if (this.b.bp.b == _snowman.b() && this.b.bp.c(this.b)) {
         if (this.b.a_()) {
            gj<bmb> _snowman = gj.a();

            for (int _snowmanx = 0; _snowmanx < this.b.bp.a.size(); _snowmanx++) {
               _snowman.add(this.b.bp.a.get(_snowmanx).e());
            }

            this.b.a(this.b.bp, _snowman);
         } else {
            bmb _snowman = this.b.bp.a(_snowman.c(), _snowman.d(), _snowman.g(), this.b);
            if (bmb.b(_snowman.f(), _snowman)) {
               this.b.b.a(new pe(_snowman.b(), _snowman.e(), true));
               this.b.e = true;
               this.b.bp.c();
               this.b.n();
               this.b.e = false;
            } else {
               this.k.put(this.b.bp.b, _snowman.e());
               this.b.b.a(new pe(_snowman.b(), _snowman.e(), false));
               this.b.bp.a(this.b, false);
               gj<bmb> _snowmanx = gj.a();

               for (int _snowmanxx = 0; _snowmanxx < this.b.bp.a.size(); _snowmanxx++) {
                  bmb _snowmanxxx = this.b.bp.a.get(_snowmanxx).e();
                  _snowmanx.add(_snowmanxxx.a() ? bmb.b : _snowmanxxx);
               }

               this.b.a(this.b.bp, _snowmanx);
            }
         }
      }
   }

   @Override
   public void a(sx var1) {
      ol.a(_snowman, this, this.b.u());
      this.b.z();
      if (!this.b.a_() && this.b.bp.b == _snowman.b() && this.b.bp.c(this.b) && this.b.bp instanceof bjj) {
         this.d.aF().a(_snowman.c()).ifPresent(var2 -> ((bjj)this.b.bp).a(_snowman.d(), (boq<?>)var2, this.b));
      }
   }

   @Override
   public void a(sj var1) {
      ol.a(_snowman, this, this.b.u());
      this.b.z();
      if (this.b.bp.b == _snowman.b() && this.b.bp.c(this.b) && !this.b.a_()) {
         this.b.bp.a(this.b, _snowman.c());
         this.b.bp.c();
      }
   }

   @Override
   public void a(tm var1) {
      ol.a(_snowman, this, this.b.u());
      if (this.b.d.e()) {
         boolean _snowman = _snowman.b() < 0;
         bmb _snowmanx = _snowman.c();
         md _snowmanxx = _snowmanx.b("BlockEntityTag");
         if (!_snowmanx.a() && _snowmanxx != null && _snowmanxx.e("x") && _snowmanxx.e("y") && _snowmanxx.e("z")) {
            fx _snowmanxxx = new fx(_snowmanxx.h("x"), _snowmanxx.h("y"), _snowmanxx.h("z"));
            ccj _snowmanxxxx = this.b.l.c(_snowmanxxx);
            if (_snowmanxxxx != null) {
               md _snowmanxxxxx = _snowmanxxxx.a(new md());
               _snowmanxxxxx.r("x");
               _snowmanxxxxx.r("y");
               _snowmanxxxxx.r("z");
               _snowmanx.a("BlockEntityTag", _snowmanxxxxx);
            }
         }

         boolean _snowmanxxx = _snowman.b() >= 1 && _snowman.b() <= 45;
         boolean _snowmanxxxx = _snowmanx.a() || _snowmanx.g() >= 0 && _snowmanx.E() <= 64 && !_snowmanx.a();
         if (_snowmanxxx && _snowmanxxxx) {
            if (_snowmanx.a()) {
               this.b.bo.a(_snowman.b(), bmb.b);
            } else {
               this.b.bo.a(_snowman.b(), _snowmanx);
            }

            this.b.bo.a(this.b, true);
            this.b.bo.c();
         } else if (_snowman && _snowmanxxxx && this.j < 200) {
            this.j += 20;
            this.b.a(_snowmanx, true);
         }
      }
   }

   @Override
   public void a(si var1) {
      ol.a(_snowman, this, this.b.u());
      int _snowman = this.b.bp.b;
      if (_snowman == _snowman.b() && this.k.getOrDefault(_snowman, (short)(_snowman.c() + 1)) == _snowman.c() && !this.b.bp.c(this.b) && !this.b.a_()) {
         this.b.bp.a(this.b, true);
      }
   }

   @Override
   public void a(tp var1) {
      List<String> _snowman = Stream.of(_snowman.c()).map(k::a).collect(Collectors.toList());
      this.a(_snowman, var2x -> this.a(_snowman, var2x));
   }

   private void a(tp var1, List<String> var2) {
      this.b.z();
      aag _snowman = this.b.u();
      fx _snowmanx = _snowman.b();
      if (_snowman.C(_snowmanx)) {
         ceh _snowmanxx = _snowman.d_(_snowmanx);
         ccj _snowmanxxx = _snowman.c(_snowmanx);
         if (!(_snowmanxxx instanceof cdf)) {
            return;
         }

         cdf _snowmanxxxx = (cdf)_snowmanxxx;
         if (!_snowmanxxxx.d() || _snowmanxxxx.f() != this.b) {
            c.warn("Player {} just tried to change non-editable sign", this.b.R().getString());
            return;
         }

         for (int _snowmanxxxxx = 0; _snowmanxxxxx < _snowman.size(); _snowmanxxxxx++) {
            _snowmanxxxx.a(_snowmanxxxxx, new oe(_snowman.get(_snowmanxxxxx)));
         }

         _snowmanxxxx.X_();
         _snowman.a(_snowmanx, _snowmanxx, _snowmanxx, 3);
      }
   }

   @Override
   public void a(sr var1) {
      if (this.g && _snowman.b() == this.h) {
         int _snowman = (int)(x.b() - this.f);
         this.b.f = (this.b.f * 3 + _snowman) / 4;
         this.g = false;
      } else if (!this.d()) {
         this.b(new of("disconnect.timeout"));
      }
   }

   @Override
   public void a(sy var1) {
      ol.a(_snowman, this, this.b.u());
      this.b.bC.b = _snowman.b() && this.b.bC.c;
   }

   @Override
   public void a(sg var1) {
      ol.a(_snowman, this, this.b.u());
      this.b.a(_snowman);
   }

   @Override
   public void a(sm var1) {
   }

   @Override
   public void a(sd var1) {
      ol.a(_snowman, this, this.b.u());
      if (this.b.k(2) || this.d()) {
         this.d.a(_snowman.b(), false);
      }
   }

   @Override
   public void a(ss var1) {
      ol.a(_snowman, this, this.b.u());
      if (this.b.k(2) || this.d()) {
         this.d.b(_snowman.b());
      }
   }
}
