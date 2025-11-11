import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import it.unimi.dsi.fastutil.longs.LongOpenHashSet;
import it.unimi.dsi.fastutil.longs.LongSet;
import it.unimi.dsi.fastutil.shorts.ShortList;
import it.unimi.dsi.fastutil.shorts.ShortListIterator;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;
import javax.annotation.Nullable;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class cgh implements cfw {
   private static final Logger b = LogManager.getLogger();
   @Nullable
   public static final cgi a = null;
   private final cgi[] c = new cgi[16];
   private cfx d;
   private final Map<fx, md> e = Maps.newHashMap();
   private boolean f;
   private final brx g;
   private final Map<chn.a, chn> h = Maps.newEnumMap(chn.a.class);
   private final cgr i;
   private final Map<fx, ccj> j = Maps.newHashMap();
   private final aes<aqa>[] k;
   private final Map<cla<?>, crv<?>> l = Maps.newHashMap();
   private final Map<cla<?>, LongSet> m = Maps.newHashMap();
   private final ShortList[] n = new ShortList[16];
   private bso<buo> o;
   private bso<cuw> p;
   private boolean q;
   private long r;
   private volatile boolean s;
   private long t;
   @Nullable
   private Supplier<zr.b> u;
   @Nullable
   private Consumer<cgh> v;
   private final brd w;
   private volatile boolean x;

   public cgh(brx var1, brd var2, cfx var3) {
      this(_snowman, _snowman, _snowman, cgr.a, brm.b(), brm.b(), 0L, null, null);
   }

   public cgh(brx var1, brd var2, cfx var3, cgr var4, bso<buo> var5, bso<cuw> var6, long var7, @Nullable cgi[] var9, @Nullable Consumer<cgh> var10) {
      this.k = new aes[16];
      this.g = _snowman;
      this.w = _snowman;
      this.i = _snowman;

      for (chn.a _snowman : chn.a.values()) {
         if (cga.m.h().contains(_snowman)) {
            this.h.put(_snowman, new chn(this, _snowman));
         }
      }

      for (int _snowmanx = 0; _snowmanx < this.k.length; _snowmanx++) {
         this.k[_snowmanx] = new aes<>(aqa.class);
      }

      this.d = _snowman;
      this.o = _snowman;
      this.p = _snowman;
      this.t = _snowman;
      this.v = _snowman;
      if (_snowman != null) {
         if (this.c.length == _snowman.length) {
            System.arraycopy(_snowman, 0, this.c, 0, this.c.length);
         } else {
            b.warn("Could not set level chunk sections, array length is {} instead of {}", _snowman.length, this.c.length);
         }
      }
   }

   public cgh(brx var1, cgp var2) {
      this(_snowman, _snowman.g(), _snowman.i(), _snowman.p(), _snowman.s(), _snowman.t(), _snowman.q(), _snowman.d(), null);

      for (md _snowman : _snowman.y()) {
         aqe.a(_snowman, _snowman, var1x -> {
            this.a(var1x);
            return var1x;
         });
      }

      for (ccj _snowman : _snowman.x().values()) {
         this.a(_snowman);
      }

      this.e.putAll(_snowman.z());

      for (int _snowman = 0; _snowman < _snowman.l().length; _snowman++) {
         this.n[_snowman] = _snowman.l()[_snowman];
      }

      this.a(_snowman.h());
      this.b(_snowman.v());

      for (Entry<chn.a, chn> _snowman : _snowman.f()) {
         if (cga.m.h().contains(_snowman.getKey())) {
            this.a(_snowman.getKey()).a(_snowman.getValue().a());
         }
      }

      this.b(_snowman.r());
      this.s = true;
   }

   @Override
   public chn a(chn.a var1) {
      return this.h.computeIfAbsent(_snowman, var1x -> new chn(this, var1x));
   }

   @Override
   public Set<fx> c() {
      Set<fx> _snowman = Sets.newHashSet(this.e.keySet());
      _snowman.addAll(this.j.keySet());
      return _snowman;
   }

   @Override
   public cgi[] d() {
      return this.c;
   }

   @Override
   public ceh d_(fx var1) {
      int _snowman = _snowman.u();
      int _snowmanx = _snowman.v();
      int _snowmanxx = _snowman.w();
      if (this.g.ab()) {
         ceh _snowmanxxx = null;
         if (_snowmanx == 60) {
            _snowmanxxx = bup.go.n();
         }

         if (_snowmanx == 70) {
            _snowmanxxx = chj.b(_snowman, _snowmanxx);
         }

         return _snowmanxxx == null ? bup.a.n() : _snowmanxxx;
      } else {
         try {
            if (_snowmanx >= 0 && _snowmanx >> 4 < this.c.length) {
               cgi _snowmanxxxx = this.c[_snowmanx >> 4];
               if (!cgi.a(_snowmanxxxx)) {
                  return _snowmanxxxx.a(_snowman & 15, _snowmanx & 15, _snowmanxx & 15);
               }
            }

            return bup.a.n();
         } catch (Throwable var8) {
            l _snowmanxxxx = l.a(var8, "Getting block state");
            m _snowmanxxxxx = _snowmanxxxx.a("Block being got");
            _snowmanxxxxx.a("Location", () -> m.a(_snowman, _snowman, _snowman));
            throw new u(_snowmanxxxx);
         }
      }
   }

   @Override
   public cux b(fx var1) {
      return this.a(_snowman.u(), _snowman.v(), _snowman.w());
   }

   public cux a(int var1, int var2, int var3) {
      try {
         if (_snowman >= 0 && _snowman >> 4 < this.c.length) {
            cgi _snowman = this.c[_snowman >> 4];
            if (!cgi.a(_snowman)) {
               return _snowman.b(_snowman & 15, _snowman & 15, _snowman & 15);
            }
         }

         return cuy.a.h();
      } catch (Throwable var7) {
         l _snowman = l.a(var7, "Getting fluid state");
         m _snowmanx = _snowman.a("Block being got");
         _snowmanx.a("Location", () -> m.a(_snowman, _snowman, _snowman));
         throw new u(_snowman);
      }
   }

   @Nullable
   @Override
   public ceh a(fx var1, ceh var2, boolean var3) {
      int _snowman = _snowman.u() & 15;
      int _snowmanx = _snowman.v();
      int _snowmanxx = _snowman.w() & 15;
      cgi _snowmanxxx = this.c[_snowmanx >> 4];
      if (_snowmanxxx == a) {
         if (_snowman.g()) {
            return null;
         }

         _snowmanxxx = new cgi(_snowmanx >> 4 << 4);
         this.c[_snowmanx >> 4] = _snowmanxxx;
      }

      boolean _snowmanxxxx = _snowmanxxx.c();
      ceh _snowmanxxxxx = _snowmanxxx.a(_snowman, _snowmanx & 15, _snowmanxx, _snowman);
      if (_snowmanxxxxx == _snowman) {
         return null;
      } else {
         buo _snowmanxxxxxx = _snowman.b();
         buo _snowmanxxxxxxx = _snowmanxxxxx.b();
         this.h.get(chn.a.e).a(_snowman, _snowmanx, _snowmanxx, _snowman);
         this.h.get(chn.a.f).a(_snowman, _snowmanx, _snowmanxx, _snowman);
         this.h.get(chn.a.d).a(_snowman, _snowmanx, _snowmanxx, _snowman);
         this.h.get(chn.a.b).a(_snowman, _snowmanx, _snowmanxx, _snowman);
         boolean _snowmanxxxxxxxx = _snowmanxxx.c();
         if (_snowmanxxxx != _snowmanxxxxxxxx) {
            this.g.H().l().a(_snowman, _snowmanxxxxxxxx);
         }

         if (!this.g.v) {
            _snowmanxxxxx.b(this.g, _snowman, _snowman, _snowman);
         } else if (_snowmanxxxxxxx != _snowmanxxxxxx && _snowmanxxxxxxx instanceof bwm) {
            this.g.o(_snowman);
         }

         if (!_snowmanxxx.a(_snowman, _snowmanx & 15, _snowmanxx).a(_snowmanxxxxxx)) {
            return null;
         } else {
            if (_snowmanxxxxxxx instanceof bwm) {
               ccj _snowmanxxxxxxxxx = this.a(_snowman, cgh.a.c);
               if (_snowmanxxxxxxxxx != null) {
                  _snowmanxxxxxxxxx.s();
               }
            }

            if (!this.g.v) {
               _snowman.a(this.g, _snowman, _snowmanxxxxx, _snowman);
            }

            if (_snowmanxxxxxx instanceof bwm) {
               ccj _snowmanxxxxxxxxx = this.a(_snowman, cgh.a.c);
               if (_snowmanxxxxxxxxx == null) {
                  _snowmanxxxxxxxxx = ((bwm)_snowmanxxxxxx).a(this.g);
                  this.g.a(_snowman, _snowmanxxxxxxxxx);
               } else {
                  _snowmanxxxxxxxxx.s();
               }
            }

            this.s = true;
            return _snowmanxxxxx;
         }
      }
   }

   @Nullable
   public cuo e() {
      return this.g.H().l();
   }

   @Override
   public void a(aqa var1) {
      this.q = true;
      int _snowman = afm.c(_snowman.cD() / 16.0);
      int _snowmanx = afm.c(_snowman.cH() / 16.0);
      if (_snowman != this.w.b || _snowmanx != this.w.c) {
         b.warn("Wrong location! ({}, {}) should be ({}, {}), {}", _snowman, _snowmanx, this.w.b, this.w.c, _snowman);
         _snowman.y = true;
      }

      int _snowmanxx = afm.c(_snowman.cE() / 16.0);
      if (_snowmanxx < 0) {
         _snowmanxx = 0;
      }

      if (_snowmanxx >= this.k.length) {
         _snowmanxx = this.k.length - 1;
      }

      _snowman.U = true;
      _snowman.V = this.w.b;
      _snowman.W = _snowmanxx;
      _snowman.X = this.w.c;
      this.k[_snowmanxx].add(_snowman);
   }

   @Override
   public void a(chn.a var1, long[] var2) {
      this.h.get(_snowman).a(_snowman);
   }

   public void b(aqa var1) {
      this.a(_snowman, _snowman.W);
   }

   public void a(aqa var1, int var2) {
      if (_snowman < 0) {
         _snowman = 0;
      }

      if (_snowman >= this.k.length) {
         _snowman = this.k.length - 1;
      }

      this.k[_snowman].remove(_snowman);
   }

   @Override
   public int a(chn.a var1, int var2, int var3) {
      return this.h.get(_snowman).a(_snowman & 15, _snowman & 15) - 1;
   }

   @Nullable
   private ccj k(fx var1) {
      ceh _snowman = this.d_(_snowman);
      buo _snowmanx = _snowman.b();
      return !_snowmanx.q() ? null : ((bwm)_snowmanx).a(this.g);
   }

   @Nullable
   @Override
   public ccj c(fx var1) {
      return this.a(_snowman, cgh.a.c);
   }

   @Nullable
   public ccj a(fx var1, cgh.a var2) {
      ccj _snowman = this.j.get(_snowman);
      if (_snowman == null) {
         md _snowmanx = this.e.remove(_snowman);
         if (_snowmanx != null) {
            ccj _snowmanxx = this.a(_snowman, _snowmanx);
            if (_snowmanxx != null) {
               return _snowmanxx;
            }
         }
      }

      if (_snowman == null) {
         if (_snowman == cgh.a.a) {
            _snowman = this.k(_snowman);
            this.g.a(_snowman, _snowman);
         }
      } else if (_snowman.q()) {
         this.j.remove(_snowman);
         return null;
      }

      return _snowman;
   }

   public void a(ccj var1) {
      this.a(_snowman.o(), _snowman);
      if (this.f || this.g.s_()) {
         this.g.a(_snowman.o(), _snowman);
      }
   }

   @Override
   public void a(fx var1, ccj var2) {
      if (this.d_(_snowman).b() instanceof bwm) {
         _snowman.a(this.g, _snowman);
         _snowman.r();
         ccj _snowman = this.j.put(_snowman.h(), _snowman);
         if (_snowman != null && _snowman != _snowman) {
            _snowman.al_();
         }
      }
   }

   @Override
   public void a(md var1) {
      this.e.put(new fx(_snowman.h("x"), _snowman.h("y"), _snowman.h("z")), _snowman);
   }

   @Nullable
   @Override
   public md j(fx var1) {
      ccj _snowman = this.c(_snowman);
      if (_snowman != null && !_snowman.q()) {
         md _snowmanx = _snowman.a(new md());
         _snowmanx.a("keepPacked", false);
         return _snowmanx;
      } else {
         md _snowmanx = this.e.get(_snowman);
         if (_snowmanx != null) {
            _snowmanx = _snowmanx.g();
            _snowmanx.a("keepPacked", true);
         }

         return _snowmanx;
      }
   }

   @Override
   public void d(fx var1) {
      if (this.f || this.g.s_()) {
         ccj _snowman = this.j.remove(_snowman);
         if (_snowman != null) {
            _snowman.al_();
         }
      }
   }

   public void w() {
      if (this.v != null) {
         this.v.accept(this);
         this.v = null;
      }
   }

   public void s() {
      this.s = true;
   }

   public void a(@Nullable aqa var1, dci var2, List<aqa> var3, @Nullable Predicate<? super aqa> var4) {
      int _snowman = afm.c((_snowman.b - 2.0) / 16.0);
      int _snowmanx = afm.c((_snowman.e + 2.0) / 16.0);
      _snowman = afm.a(_snowman, 0, this.k.length - 1);
      _snowmanx = afm.a(_snowmanx, 0, this.k.length - 1);

      for (int _snowmanxx = _snowman; _snowmanxx <= _snowmanx; _snowmanxx++) {
         aes<aqa> _snowmanxxx = this.k[_snowmanxx];
         List<aqa> _snowmanxxxx = _snowmanxxx.a();
         int _snowmanxxxxx = _snowmanxxxx.size();

         for (int _snowmanxxxxxx = 0; _snowmanxxxxxx < _snowmanxxxxx; _snowmanxxxxxx++) {
            aqa _snowmanxxxxxxx = _snowmanxxxx.get(_snowmanxxxxxx);
            if (_snowmanxxxxxxx.cc().c(_snowman) && _snowmanxxxxxxx != _snowman) {
               if (_snowman == null || _snowman.test(_snowmanxxxxxxx)) {
                  _snowman.add(_snowmanxxxxxxx);
               }

               if (_snowmanxxxxxxx instanceof bbr) {
                  for (bbp _snowmanxxxxxxxx : ((bbr)_snowmanxxxxxxx).eJ()) {
                     if (_snowmanxxxxxxxx != _snowman && _snowmanxxxxxxxx.cc().c(_snowman) && (_snowman == null || _snowman.test(_snowmanxxxxxxxx))) {
                        _snowman.add(_snowmanxxxxxxxx);
                     }
                  }
               }
            }
         }
      }
   }

   public <T extends aqa> void a(@Nullable aqe<?> var1, dci var2, List<? super T> var3, Predicate<? super T> var4) {
      int _snowman = afm.c((_snowman.b - 2.0) / 16.0);
      int _snowmanx = afm.c((_snowman.e + 2.0) / 16.0);
      _snowman = afm.a(_snowman, 0, this.k.length - 1);
      _snowmanx = afm.a(_snowmanx, 0, this.k.length - 1);

      for (int _snowmanxx = _snowman; _snowmanxx <= _snowmanx; _snowmanxx++) {
         for (aqa _snowmanxxx : this.k[_snowmanxx].a(aqa.class)) {
            if ((_snowman == null || _snowmanxxx.X() == _snowman) && _snowmanxxx.cc().c(_snowman) && _snowman.test((T)_snowmanxxx)) {
               _snowman.add((T)_snowmanxxx);
            }
         }
      }
   }

   public <T extends aqa> void a(Class<? extends T> var1, dci var2, List<T> var3, @Nullable Predicate<? super T> var4) {
      int _snowman = afm.c((_snowman.b - 2.0) / 16.0);
      int _snowmanx = afm.c((_snowman.e + 2.0) / 16.0);
      _snowman = afm.a(_snowman, 0, this.k.length - 1);
      _snowmanx = afm.a(_snowmanx, 0, this.k.length - 1);

      for (int _snowmanxx = _snowman; _snowmanxx <= _snowmanx; _snowmanxx++) {
         for (T _snowmanxxx : this.k[_snowmanxx].a(_snowman)) {
            if (_snowmanxxx.cc().c(_snowman) && (_snowman == null || _snowman.test(_snowmanxxx))) {
               _snowman.add(_snowmanxxx);
            }
         }
      }
   }

   public boolean t() {
      return false;
   }

   @Override
   public brd g() {
      return this.w;
   }

   public void a(@Nullable cfx var1, nf var2, md var3, int var4) {
      boolean _snowman = _snowman != null;
      Predicate<fx> _snowmanx = _snowman ? var0 -> true : var1x -> (_snowman & 1 << (var1x.v() >> 4)) != 0;
      Sets.newHashSet(this.j.keySet()).stream().filter(_snowmanx).forEach(this.g::o);

      for (int _snowmanxx = 0; _snowmanxx < this.c.length; _snowmanxx++) {
         cgi _snowmanxxx = this.c[_snowmanxx];
         if ((_snowman & 1 << _snowmanxx) == 0) {
            if (_snowman && _snowmanxxx != a) {
               this.c[_snowmanxx] = a;
            }
         } else {
            if (_snowmanxxx == a) {
               _snowmanxxx = new cgi(_snowmanxx << 4);
               this.c[_snowmanxx] = _snowmanxxx;
            }

            _snowmanxxx.a(_snowman);
         }
      }

      if (_snowman != null) {
         this.d = _snowman;
      }

      for (chn.a _snowmanxxx : chn.a.values()) {
         String _snowmanxxxx = _snowmanxxx.b();
         if (_snowman.c(_snowmanxxxx, 12)) {
            this.a(_snowmanxxx, _snowman.o(_snowmanxxxx));
         }
      }

      for (ccj _snowmanxxxx : this.j.values()) {
         _snowmanxxxx.s();
      }
   }

   @Override
   public cfx i() {
      return this.d;
   }

   public void c(boolean var1) {
      this.f = _snowman;
   }

   public brx x() {
      return this.g;
   }

   @Override
   public Collection<Entry<chn.a, chn>> f() {
      return Collections.unmodifiableSet(this.h.entrySet());
   }

   public Map<fx, ccj> y() {
      return this.j;
   }

   public aes<aqa>[] z() {
      return this.k;
   }

   @Override
   public md i(fx var1) {
      return this.e.get(_snowman);
   }

   @Override
   public Stream<fx> m() {
      return StreamSupport.stream(fx.b(this.w.d(), 0, this.w.e(), this.w.f(), 255, this.w.g()).spliterator(), false).filter(var1 -> this.d_(var1).f() != 0);
   }

   @Override
   public bso<buo> n() {
      return this.o;
   }

   @Override
   public bso<cuw> o() {
      return this.p;
   }

   @Override
   public void a(boolean var1) {
      this.s = _snowman;
   }

   @Override
   public boolean j() {
      return this.s || this.q && this.g.T() != this.r;
   }

   public void d(boolean var1) {
      this.q = _snowman;
   }

   @Override
   public void a(long var1) {
      this.r = _snowman;
   }

   @Nullable
   @Override
   public crv<?> a(cla<?> var1) {
      return this.l.get(_snowman);
   }

   @Override
   public void a(cla<?> var1, crv<?> var2) {
      this.l.put(_snowman, _snowman);
   }

   @Override
   public Map<cla<?>, crv<?>> h() {
      return this.l;
   }

   @Override
   public void a(Map<cla<?>, crv<?>> var1) {
      this.l.clear();
      this.l.putAll(_snowman);
   }

   @Override
   public LongSet b(cla<?> var1) {
      return this.m.computeIfAbsent(_snowman, var0 -> new LongOpenHashSet());
   }

   @Override
   public void a(cla<?> var1, long var2) {
      this.m.computeIfAbsent(_snowman, var0 -> new LongOpenHashSet()).add(_snowman);
   }

   @Override
   public Map<cla<?>, LongSet> v() {
      return this.m;
   }

   @Override
   public void b(Map<cla<?>, LongSet> var1) {
      this.m.clear();
      this.m.putAll(_snowman);
   }

   @Override
   public long q() {
      return this.t;
   }

   @Override
   public void b(long var1) {
      this.t = _snowman;
   }

   public void A() {
      brd _snowman = this.g();

      for (int _snowmanx = 0; _snowmanx < this.n.length; _snowmanx++) {
         if (this.n[_snowmanx] != null) {
            ShortListIterator var3 = this.n[_snowmanx].iterator();

            while (var3.hasNext()) {
               Short _snowmanxx = (Short)var3.next();
               fx _snowmanxxx = cgp.a(_snowmanxx, _snowmanx, _snowman);
               ceh _snowmanxxxx = this.d_(_snowmanxxx);
               ceh _snowmanxxxxx = buo.b(_snowmanxxxx, (bry)this.g, _snowmanxxx);
               this.g.a(_snowmanxxx, _snowmanxxxxx, 20);
            }

            this.n[_snowmanx].clear();
         }
      }

      this.B();

      for (fx _snowmanxx : Sets.newHashSet(this.e.keySet())) {
         this.c(_snowmanxx);
      }

      this.e.clear();
      this.i.a(this);
   }

   @Nullable
   private ccj a(fx var1, md var2) {
      ceh _snowman = this.d_(_snowman);
      ccj _snowmanx;
      if ("DUMMY".equals(_snowman.l("id"))) {
         buo _snowmanxx = _snowman.b();
         if (_snowmanxx instanceof bwm) {
            _snowmanx = ((bwm)_snowmanxx).a(this.g);
         } else {
            _snowmanx = null;
            b.warn("Tried to load a DUMMY block entity @ {} but found not block entity block {} at location", _snowman, _snowman);
         }
      } else {
         _snowmanx = ccj.b(_snowman, _snowman);
      }

      if (_snowmanx != null) {
         _snowmanx.a(this.g, _snowman);
         this.a(_snowmanx);
      } else {
         b.warn("Tried to load a block entity for block {} but failed at location {}", _snowman, _snowman);
      }

      return _snowmanx;
   }

   @Override
   public cgr p() {
      return this.i;
   }

   @Override
   public ShortList[] l() {
      return this.n;
   }

   public void B() {
      if (this.o instanceof cgq) {
         ((cgq)this.o).a(this.g.J(), var1 -> this.d_(var1).b());
         this.o = brm.b();
      } else if (this.o instanceof bre) {
         ((bre)this.o).a(this.g.J());
         this.o = brm.b();
      }

      if (this.p instanceof cgq) {
         ((cgq)this.p).a(this.g.I(), var1 -> this.b(var1).a());
         this.p = brm.b();
      } else if (this.p instanceof bre) {
         ((bre)this.p).a(this.g.I());
         this.p = brm.b();
      }
   }

   public void a(aag var1) {
      if (this.o == brm.b()) {
         this.o = new bre<>(gm.Q::b, _snowman.j().a(this.w, true, false), _snowman.T());
         this.a(true);
      }

      if (this.p == brm.b()) {
         this.p = new bre<>(gm.O::b, _snowman.r_().a(this.w, true, false), _snowman.T());
         this.a(true);
      }
   }

   @Override
   public cga k() {
      return cga.m;
   }

   public zr.b u() {
      return this.u == null ? zr.b.b : this.u.get();
   }

   public void a(Supplier<zr.b> var1) {
      this.u = _snowman;
   }

   @Override
   public boolean r() {
      return this.x;
   }

   @Override
   public void b(boolean var1) {
      this.x = _snowman;
      this.a(true);
   }

   public static enum a {
      a,
      b,
      c;

      private a() {
      }
   }
}
