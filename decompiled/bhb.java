import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.Random;
import java.util.Set;
import java.util.UUID;
import java.util.function.Predicate;
import java.util.stream.Stream;
import javax.annotation.Nullable;

public class bhb {
   private static final nr a = new of("event.minecraft.raid");
   private static final nr b = new of("event.minecraft.raid.victory");
   private static final nr c = new of("event.minecraft.raid.defeat");
   private static final nr d = a.e().c(" - ").a(b);
   private static final nr e = a.e().c(" - ").a(c);
   private final Map<Integer, bhc> f = Maps.newHashMap();
   private final Map<Integer, Set<bhc>> g = Maps.newHashMap();
   private final Set<UUID> h = Sets.newHashSet();
   private long i;
   private fx j;
   private final aag k;
   private boolean l;
   private final int m;
   private float n;
   private int o;
   private boolean p;
   private int q;
   private final aad r = new aad(a, aok.a.c, aok.b.c);
   private int s;
   private int t;
   private final Random u = new Random();
   private final int v;
   private bhb.a w;
   private int x;
   private Optional<fx> y = Optional.empty();

   public bhb(int var1, aag var2, fx var3) {
      this.m = _snowman;
      this.k = _snowman;
      this.p = true;
      this.t = 300;
      this.r.a(0.0F);
      this.j = _snowman;
      this.v = this.a(_snowman.ad());
      this.w = bhb.a.a;
   }

   public bhb(aag var1, md var2) {
      this.k = _snowman;
      this.m = _snowman.h("Id");
      this.l = _snowman.q("Started");
      this.p = _snowman.q("Active");
      this.i = _snowman.i("TicksActive");
      this.o = _snowman.h("BadOmenLevel");
      this.q = _snowman.h("GroupsSpawned");
      this.t = _snowman.h("PreRaidTicks");
      this.s = _snowman.h("PostRaidTicks");
      this.n = _snowman.j("TotalHealth");
      this.j = new fx(_snowman.h("CX"), _snowman.h("CY"), _snowman.h("CZ"));
      this.v = _snowman.h("NumGroups");
      this.w = bhb.a.b(_snowman.l("Status"));
      this.h.clear();
      if (_snowman.c("HeroesOfTheVillage", 9)) {
         mj _snowman = _snowman.d("HeroesOfTheVillage", 11);

         for (int _snowmanx = 0; _snowmanx < _snowman.size(); _snowmanx++) {
            this.h.add(mp.a(_snowman.k(_snowmanx)));
         }
      }
   }

   public boolean a() {
      return this.e() || this.f();
   }

   public boolean b() {
      return this.c() && this.r() == 0 && this.t > 0;
   }

   public boolean c() {
      return this.q > 0;
   }

   public boolean d() {
      return this.w == bhb.a.d;
   }

   public boolean e() {
      return this.w == bhb.a.b;
   }

   public boolean f() {
      return this.w == bhb.a.c;
   }

   public brx i() {
      return this.k;
   }

   public boolean j() {
      return this.l;
   }

   public int k() {
      return this.q;
   }

   private Predicate<aah> x() {
      return var1 -> {
         fx _snowman = var1.cB();
         return var1.aX() && this.k.b_(_snowman) == this;
      };
   }

   private void y() {
      Set<aah> _snowman = Sets.newHashSet(this.r.h());
      List<aah> _snowmanx = this.k.a(this.x());

      for (aah _snowmanxx : _snowmanx) {
         if (!_snowman.contains(_snowmanxx)) {
            this.r.a(_snowmanxx);
         }
      }

      for (aah _snowmanxxx : _snowman) {
         if (!_snowmanx.contains(_snowmanxxx)) {
            this.r.b(_snowmanxxx);
         }
      }
   }

   public int l() {
      return 5;
   }

   public int m() {
      return this.o;
   }

   public void a(bfw var1) {
      if (_snowman.a(apw.E)) {
         this.o = this.o + _snowman.b(apw.E).c() + 1;
         this.o = afm.a(this.o, 0, this.l());
      }

      _snowman.d(apw.E);
   }

   public void n() {
      this.p = false;
      this.r.b();
      this.w = bhb.a.d;
   }

   public void o() {
      if (!this.d()) {
         if (this.w == bhb.a.a) {
            boolean _snowman = this.p;
            this.p = this.k.C(this.j);
            if (this.k.ad() == aor.a) {
               this.n();
               return;
            }

            if (_snowman != this.p) {
               this.r.d(this.p);
            }

            if (!this.p) {
               return;
            }

            if (!this.k.a_(this.j)) {
               this.z();
            }

            if (!this.k.a_(this.j)) {
               if (this.q > 0) {
                  this.w = bhb.a.c;
               } else {
                  this.n();
               }
            }

            this.i++;
            if (this.i >= 48000L) {
               this.n();
               return;
            }

            int _snowmanx = this.r();
            if (_snowmanx == 0 && this.A()) {
               if (this.t <= 0) {
                  if (this.t == 0 && this.q > 0) {
                     this.t = 300;
                     this.r.a(a);
                     return;
                  }
               } else {
                  boolean _snowmanxx = this.y.isPresent();
                  boolean _snowmanxxx = !_snowmanxx && this.t % 5 == 0;
                  if (_snowmanxx && !this.k.i().a(new brd(this.y.get()))) {
                     _snowmanxxx = true;
                  }

                  if (_snowmanxxx) {
                     int _snowmanxxxx = 0;
                     if (this.t < 100) {
                        _snowmanxxxx = 1;
                     } else if (this.t < 40) {
                        _snowmanxxxx = 2;
                     }

                     this.y = this.d(_snowmanxxxx);
                  }

                  if (this.t == 300 || this.t % 20 == 0) {
                     this.y();
                  }

                  this.t--;
                  this.r.a(afm.a((float)(300 - this.t) / 300.0F, 0.0F, 1.0F));
               }
            }

            if (this.i % 20L == 0L) {
               this.y();
               this.F();
               if (_snowmanx > 0) {
                  if (_snowmanx <= 2) {
                     this.r.a(a.e().c(" - ").a(new of("event.minecraft.raid.raiders_remaining", _snowmanx)));
                  } else {
                     this.r.a(a);
                  }
               } else {
                  this.r.a(a);
               }
            }

            boolean _snowmanxxxx = false;
            int _snowmanxxxxx = 0;

            while (this.G()) {
               fx _snowmanxxxxxx = this.y.isPresent() ? this.y.get() : this.a(_snowmanxxxxx, 20);
               if (_snowmanxxxxxx != null) {
                  this.l = true;
                  this.b(_snowmanxxxxxx);
                  if (!_snowmanxxxx) {
                     this.a(_snowmanxxxxxx);
                     _snowmanxxxx = true;
                  }
               } else {
                  _snowmanxxxxx++;
               }

               if (_snowmanxxxxx > 3) {
                  this.n();
                  break;
               }
            }

            if (this.j() && !this.A() && _snowmanx == 0) {
               if (this.s < 40) {
                  this.s++;
               } else {
                  this.w = bhb.a.b;

                  for (UUID _snowmanxxxxxxx : this.h) {
                     aqa _snowmanxxxxxxxx = this.k.a(_snowmanxxxxxxx);
                     if (_snowmanxxxxxxxx instanceof aqm && !_snowmanxxxxxxxx.a_()) {
                        aqm _snowmanxxxxxxxxx = (aqm)_snowmanxxxxxxxx;
                        _snowmanxxxxxxxxx.c(new apu(apw.F, 48000, this.o - 1, false, false, true));
                        if (_snowmanxxxxxxxxx instanceof aah) {
                           aah _snowmanxxxxxxxxxx = (aah)_snowmanxxxxxxxxx;
                           _snowmanxxxxxxxxxx.a(aea.aA);
                           ac.H.a(_snowmanxxxxxxxxxx);
                        }
                     }
                  }
               }
            }

            this.H();
         } else if (this.a()) {
            this.x++;
            if (this.x >= 600) {
               this.n();
               return;
            }

            if (this.x % 20 == 0) {
               this.y();
               this.r.d(true);
               if (this.e()) {
                  this.r.a(0.0F);
                  this.r.a(d);
               } else {
                  this.r.a(e);
               }
            }
         }
      }
   }

   private void z() {
      Stream<gp> _snowman = gp.a(gp.a(this.j), 2);
      _snowman.filter(this.k::a).map(gp::q).min(Comparator.comparingDouble(var1x -> var1x.j(this.j))).ifPresent(this::c);
   }

   private Optional<fx> d(int var1) {
      for (int _snowman = 0; _snowman < 3; _snowman++) {
         fx _snowmanx = this.a(_snowman, 1);
         if (_snowmanx != null) {
            return Optional.of(_snowmanx);
         }
      }

      return Optional.empty();
   }

   private boolean A() {
      return this.C() ? !this.D() : !this.B();
   }

   private boolean B() {
      return this.k() == this.v;
   }

   private boolean C() {
      return this.o > 1;
   }

   private boolean D() {
      return this.k() > this.v;
   }

   private boolean E() {
      return this.B() && this.r() == 0 && this.C();
   }

   private void F() {
      Iterator<Set<bhc>> _snowman = this.g.values().iterator();
      Set<bhc> _snowmanx = Sets.newHashSet();

      while (_snowman.hasNext()) {
         Set<bhc> _snowmanxx = _snowman.next();

         for (bhc _snowmanxxx : _snowmanxx) {
            fx _snowmanxxxx = _snowmanxxx.cB();
            if (_snowmanxxx.y || _snowmanxxx.l.Y() != this.k.Y() || this.j.j(_snowmanxxxx) >= 12544.0) {
               _snowmanx.add(_snowmanxxx);
            } else if (_snowmanxxx.K > 600) {
               if (this.k.a(_snowmanxxx.bS()) == null) {
                  _snowmanx.add(_snowmanxxx);
               }

               if (!this.k.a_(_snowmanxxxx) && _snowmanxxx.dd() > 2400) {
                  _snowmanxxx.b(_snowmanxxx.fe() + 1);
               }

               if (_snowmanxxx.fe() >= 30) {
                  _snowmanx.add(_snowmanxxx);
               }
            }
         }
      }

      for (bhc _snowmanxx : _snowmanx) {
         this.a(_snowmanxx, true);
      }
   }

   private void a(fx var1) {
      float _snowman = 13.0F;
      int _snowmanx = 64;
      Collection<aah> _snowmanxx = this.r.h();

      for (aah _snowmanxxx : this.k.x()) {
         dcn _snowmanxxxx = _snowmanxxx.cA();
         dcn _snowmanxxxxx = dcn.a(_snowman);
         float _snowmanxxxxxx = afm.a((_snowmanxxxxx.b - _snowmanxxxx.b) * (_snowmanxxxxx.b - _snowmanxxxx.b) + (_snowmanxxxxx.d - _snowmanxxxx.d) * (_snowmanxxxxx.d - _snowmanxxxx.d));
         double _snowmanxxxxxxx = _snowmanxxxx.b + (double)(13.0F / _snowmanxxxxxx) * (_snowmanxxxxx.b - _snowmanxxxx.b);
         double _snowmanxxxxxxxx = _snowmanxxxx.d + (double)(13.0F / _snowmanxxxxxx) * (_snowmanxxxxx.d - _snowmanxxxx.d);
         if (_snowmanxxxxxx <= 64.0F || _snowmanxx.contains(_snowmanxxx)) {
            _snowmanxxx.b.a(new rn(adq.md, adr.g, _snowmanxxxxxxx, _snowmanxxx.cE(), _snowmanxxxxxxxx, 64.0F, 1.0F));
         }
      }
   }

   private void b(fx var1) {
      boolean _snowman = false;
      int _snowmanx = this.q + 1;
      this.n = 0.0F;
      aos _snowmanxx = this.k.d(_snowman);
      boolean _snowmanxxx = this.E();

      for (bhb.b _snowmanxxxx : bhb.b.f) {
         int _snowmanxxxxx = this.a(_snowmanxxxx, _snowmanx, _snowmanxxx) + this.a(_snowmanxxxx, this.u, _snowmanx, _snowmanxx, _snowmanxxx);
         int _snowmanxxxxxx = 0;

         for (int _snowmanxxxxxxx = 0; _snowmanxxxxxxx < _snowmanxxxxx; _snowmanxxxxxxx++) {
            bhc _snowmanxxxxxxxx = _snowmanxxxx.g.a(this.k);
            if (!_snowman && _snowmanxxxxxxxx.eN()) {
               _snowmanxxxxxxxx.t(true);
               this.a(_snowmanx, _snowmanxxxxxxxx);
               _snowman = true;
            }

            this.a(_snowmanx, _snowmanxxxxxxxx, _snowman, false);
            if (_snowmanxxxx.g == aqe.ap) {
               bhc _snowmanxxxxxxxxx = null;
               if (_snowmanx == this.a(aor.c)) {
                  _snowmanxxxxxxxxx = aqe.ak.a(this.k);
               } else if (_snowmanx >= this.a(aor.d)) {
                  if (_snowmanxxxxxx == 0) {
                     _snowmanxxxxxxxxx = aqe.w.a(this.k);
                  } else {
                     _snowmanxxxxxxxxx = aqe.aQ.a(this.k);
                  }
               }

               _snowmanxxxxxx++;
               if (_snowmanxxxxxxxxx != null) {
                  this.a(_snowmanx, _snowmanxxxxxxxxx, _snowman, false);
                  _snowmanxxxxxxxxx.a(_snowman, 0.0F, 0.0F);
                  _snowmanxxxxxxxxx.m(_snowmanxxxxxxxx);
               }
            }
         }
      }

      this.y = Optional.empty();
      this.q++;
      this.p();
      this.H();
   }

   public void a(int var1, bhc var2, @Nullable fx var3, boolean var4) {
      boolean _snowman = this.b(_snowman, _snowman);
      if (_snowman) {
         _snowman.a(this);
         _snowman.a(_snowman);
         _snowman.w(true);
         _snowman.b(0);
         if (!_snowman && _snowman != null) {
            _snowman.d((double)_snowman.u() + 0.5, (double)_snowman.v() + 1.0, (double)_snowman.w() + 0.5);
            _snowman.a(this.k, this.k.d(_snowman), aqp.h, null, null);
            _snowman.a(_snowman, false);
            _snowman.c(true);
            this.k.l(_snowman);
         }
      }
   }

   public void p() {
      this.r.a(afm.a(this.q() / this.n, 0.0F, 1.0F));
   }

   public float q() {
      float _snowman = 0.0F;

      for (Set<bhc> _snowmanx : this.g.values()) {
         for (bhc _snowmanxx : _snowmanx) {
            _snowman += _snowmanxx.dk();
         }
      }

      return _snowman;
   }

   private boolean G() {
      return this.t == 0 && (this.q < this.v || this.E()) && this.r() == 0;
   }

   public int r() {
      return this.g.values().stream().mapToInt(Set::size).sum();
   }

   public void a(bhc var1, boolean var2) {
      Set<bhc> _snowman = this.g.get(_snowman.fc());
      if (_snowman != null) {
         boolean _snowmanx = _snowman.remove(_snowman);
         if (_snowmanx) {
            if (_snowman) {
               this.n = this.n - _snowman.dk();
            }

            _snowman.a(null);
            this.p();
            this.H();
         }
      }
   }

   private void H() {
      this.k.z().b();
   }

   public static bmb s() {
      bmb _snowman = new bmb(bmd.pM);
      md _snowmanx = _snowman.a("BlockEntityTag");
      mj _snowmanxx = new ccb.a().a(ccb.z, bkx.j).a(ccb.f, bkx.i).a(ccb.j, bkx.h).a(ccb.E, bkx.i).a(ccb.k, bkx.p).a(ccb.B, bkx.i).a(ccb.y, bkx.i).a(ccb.E, bkx.p).a();
      _snowmanx.a("Patterns", _snowmanxx);
      _snowman.a(bmb.a.f);
      _snowman.a(new of("block.minecraft.ominous_banner").a(k.g));
      return _snowman;
   }

   @Nullable
   public bhc b(int var1) {
      return this.f.get(_snowman);
   }

   @Nullable
   private fx a(int var1, int var2) {
      int _snowman = _snowman == 0 ? 2 : 2 - _snowman;
      fx.a _snowmanx = new fx.a();

      for (int _snowmanxx = 0; _snowmanxx < _snowman; _snowmanxx++) {
         float _snowmanxxx = this.k.t.nextFloat() * (float) (Math.PI * 2);
         int _snowmanxxxx = this.j.u() + afm.d(afm.b(_snowmanxxx) * 32.0F * (float)_snowman) + this.k.t.nextInt(5);
         int _snowmanxxxxx = this.j.w() + afm.d(afm.a(_snowmanxxx) * 32.0F * (float)_snowman) + this.k.t.nextInt(5);
         int _snowmanxxxxxx = this.k.a(chn.a.b, _snowmanxxxx, _snowmanxxxxx);
         _snowmanx.d(_snowmanxxxx, _snowmanxxxxxx, _snowmanxxxxx);
         if ((!this.k.a_(_snowmanx) || _snowman >= 2)
            && this.k.a(_snowmanx.u() - 10, _snowmanx.v() - 10, _snowmanx.w() - 10, _snowmanx.u() + 10, _snowmanx.v() + 10, _snowmanx.w() + 10)
            && this.k.i().a(new brd(_snowmanx))
            && (bsg.a(ard.c.a, this.k, _snowmanx, aqe.ap) || this.k.d_(_snowmanx.c()).a(bup.cC) && this.k.d_(_snowmanx).g())) {
            return _snowmanx;
         }
      }

      return null;
   }

   private boolean b(int var1, bhc var2) {
      return this.a(_snowman, _snowman, true);
   }

   public boolean a(int var1, bhc var2, boolean var3) {
      this.g.computeIfAbsent(_snowman, var0 -> Sets.newHashSet());
      Set<bhc> _snowman = this.g.get(_snowman);
      bhc _snowmanx = null;

      for (bhc _snowmanxx : _snowman) {
         if (_snowmanxx.bS().equals(_snowman.bS())) {
            _snowmanx = _snowmanxx;
            break;
         }
      }

      if (_snowmanx != null) {
         _snowman.remove(_snowmanx);
         _snowman.add(_snowman);
      }

      _snowman.add(_snowman);
      if (_snowman) {
         this.n = this.n + _snowman.dk();
      }

      this.p();
      this.H();
      return true;
   }

   public void a(int var1, bhc var2) {
      this.f.put(_snowman, _snowman);
      _snowman.a(aqf.f, s());
      _snowman.a(aqf.f, 2.0F);
   }

   public void c(int var1) {
      this.f.remove(_snowman);
   }

   public fx t() {
      return this.j;
   }

   private void c(fx var1) {
      this.j = _snowman;
   }

   public int u() {
      return this.m;
   }

   private int a(bhb.b var1, int var2, boolean var3) {
      return _snowman ? _snowman.h[this.v] : _snowman.h[_snowman];
   }

   private int a(bhb.b var1, Random var2, int var3, aos var4, boolean var5) {
      aor _snowman = _snowman.a();
      boolean _snowmanx = _snowman == aor.b;
      boolean _snowmanxx = _snowman == aor.c;
      int _snowmanxxx;
      switch (_snowman) {
         case d:
            if (_snowmanx || _snowman <= 2 || _snowman == 4) {
               return 0;
            }

            _snowmanxxx = 1;
            break;
         case c:
         case a:
            if (_snowmanx) {
               _snowmanxxx = _snowman.nextInt(2);
            } else if (_snowmanxx) {
               _snowmanxxx = 1;
            } else {
               _snowmanxxx = 2;
            }
            break;
         case e:
            _snowmanxxx = !_snowmanx && _snowman ? 1 : 0;
            break;
         default:
            return 0;
      }

      return _snowmanxxx > 0 ? _snowman.nextInt(_snowmanxxx + 1) : 0;
   }

   public boolean v() {
      return this.p;
   }

   public md a(md var1) {
      _snowman.b("Id", this.m);
      _snowman.a("Started", this.l);
      _snowman.a("Active", this.p);
      _snowman.a("TicksActive", this.i);
      _snowman.b("BadOmenLevel", this.o);
      _snowman.b("GroupsSpawned", this.q);
      _snowman.b("PreRaidTicks", this.t);
      _snowman.b("PostRaidTicks", this.s);
      _snowman.a("TotalHealth", this.n);
      _snowman.b("NumGroups", this.v);
      _snowman.a("Status", this.w.a());
      _snowman.b("CX", this.j.u());
      _snowman.b("CY", this.j.v());
      _snowman.b("CZ", this.j.w());
      mj _snowman = new mj();

      for (UUID _snowmanx : this.h) {
         _snowman.add(mp.a(_snowmanx));
      }

      _snowman.a("HeroesOfTheVillage", _snowman);
      return _snowman;
   }

   public int a(aor var1) {
      switch (_snowman) {
         case b:
            return 3;
         case c:
            return 5;
         case d:
            return 7;
         default:
            return 0;
      }
   }

   public float w() {
      int _snowman = this.m();
      if (_snowman == 2) {
         return 0.1F;
      } else if (_snowman == 3) {
         return 0.25F;
      } else if (_snowman == 4) {
         return 0.5F;
      } else {
         return _snowman == 5 ? 0.75F : 0.0F;
      }
   }

   public void a(aqa var1) {
      this.h.add(_snowman.bS());
   }

   static enum a {
      a,
      b,
      c,
      d;

      private static final bhb.a[] e = values();

      private a() {
      }

      private static bhb.a b(String var0) {
         for (bhb.a _snowman : e) {
            if (_snowman.equalsIgnoreCase(_snowman.name())) {
               return _snowman;
            }
         }

         return a;
      }

      public String a() {
         return this.name().toLowerCase(Locale.ROOT);
      }
   }

   static enum b {
      a(aqe.aQ, new int[]{0, 0, 2, 0, 1, 4, 2, 5}),
      b(aqe.w, new int[]{0, 0, 0, 0, 0, 1, 1, 2}),
      c(aqe.ak, new int[]{0, 4, 3, 3, 4, 4, 4, 2}),
      d(aqe.aS, new int[]{0, 0, 0, 0, 3, 0, 0, 1}),
      e(aqe.ap, new int[]{0, 0, 0, 1, 0, 1, 0, 2});

      private static final bhb.b[] f = values();
      private final aqe<? extends bhc> g;
      private final int[] h;

      private b(aqe<? extends bhc> var3, int[] var4) {
         this.g = _snowman;
         this.h = _snowman;
      }
   }
}
