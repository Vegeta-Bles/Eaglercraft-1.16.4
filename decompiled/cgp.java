import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import it.unimi.dsi.fastutil.longs.LongOpenHashSet;
import it.unimi.dsi.fastutil.longs.LongSet;
import it.unimi.dsi.fastutil.objects.Object2ObjectArrayMap;
import it.unimi.dsi.fastutil.shorts.ShortList;
import java.util.BitSet;
import java.util.Collection;
import java.util.Collections;
import java.util.EnumSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;
import java.util.stream.Stream;
import javax.annotation.Nullable;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class cgp implements cfw {
   private static final Logger a = LogManager.getLogger();
   private final brd b;
   private volatile boolean c;
   @Nullable
   private cfx d;
   @Nullable
   private volatile cuo e;
   private final Map<chn.a, chn> f = Maps.newEnumMap(chn.a.class);
   private volatile cga g = cga.a;
   private final Map<fx, ccj> h = Maps.newHashMap();
   private final Map<fx, md> i = Maps.newHashMap();
   private final cgi[] j = new cgi[16];
   private final List<md> k = Lists.newArrayList();
   private final List<fx> l = Lists.newArrayList();
   private final ShortList[] m = new ShortList[16];
   private final Map<cla<?>, crv<?>> n = Maps.newHashMap();
   private final Map<cla<?>, LongSet> o = Maps.newHashMap();
   private final cgr p;
   private final cgq<buo> q;
   private final cgq<cuw> r;
   private long s;
   private final Map<chm.a, BitSet> t = new Object2ObjectArrayMap();
   private volatile boolean u;

   public cgp(brd var1, cgr var2) {
      this(_snowman, _snowman, null, new cgq<>(var0 -> var0 == null || var0.n().g(), _snowman), new cgq<>(var0 -> var0 == null || var0 == cuy.a, _snowman));
   }

   public cgp(brd var1, cgr var2, @Nullable cgi[] var3, cgq<buo> var4, cgq<cuw> var5) {
      this.b = _snowman;
      this.p = _snowman;
      this.q = _snowman;
      this.r = _snowman;
      if (_snowman != null) {
         if (this.j.length == _snowman.length) {
            System.arraycopy(_snowman, 0, this.j, 0, this.j.length);
         } else {
            a.warn("Could not set level chunk sections, array length is {} instead of {}", _snowman.length, this.j.length);
         }
      }
   }

   @Override
   public ceh d_(fx var1) {
      int _snowman = _snowman.v();
      if (brx.b(_snowman)) {
         return bup.la.n();
      } else {
         cgi _snowmanx = this.d()[_snowman >> 4];
         return cgi.a(_snowmanx) ? bup.a.n() : _snowmanx.a(_snowman.u() & 15, _snowman & 15, _snowman.w() & 15);
      }
   }

   @Override
   public cux b(fx var1) {
      int _snowman = _snowman.v();
      if (brx.b(_snowman)) {
         return cuy.a.h();
      } else {
         cgi _snowmanx = this.d()[_snowman >> 4];
         return cgi.a(_snowmanx) ? cuy.a.h() : _snowmanx.b(_snowman.u() & 15, _snowman & 15, _snowman.w() & 15);
      }
   }

   @Override
   public Stream<fx> m() {
      return this.l.stream();
   }

   public ShortList[] w() {
      ShortList[] _snowman = new ShortList[16];

      for (fx _snowmanx : this.l) {
         cfw.a(_snowman, _snowmanx.v() >> 4).add(l(_snowmanx));
      }

      return _snowman;
   }

   public void b(short var1, int var2) {
      this.k(a(_snowman, _snowman, this.b));
   }

   public void k(fx var1) {
      this.l.add(_snowman.h());
   }

   @Nullable
   @Override
   public ceh a(fx var1, ceh var2, boolean var3) {
      int _snowman = _snowman.u();
      int _snowmanx = _snowman.v();
      int _snowmanxx = _snowman.w();
      if (_snowmanx >= 0 && _snowmanx < 256) {
         if (this.j[_snowmanx >> 4] == cgh.a && _snowman.a(bup.a)) {
            return _snowman;
         } else {
            if (_snowman.f() > 0) {
               this.l.add(new fx((_snowman & 15) + this.g().d(), _snowmanx, (_snowmanxx & 15) + this.g().e()));
            }

            cgi _snowmanxxx = this.a(_snowmanx >> 4);
            ceh _snowmanxxxx = _snowmanxxx.a(_snowman & 15, _snowmanx & 15, _snowmanxx & 15, _snowman);
            if (this.g.b(cga.i) && _snowman != _snowmanxxxx && (_snowman.b(this, _snowman) != _snowmanxxxx.b(this, _snowman) || _snowman.f() != _snowmanxxxx.f() || _snowman.e() || _snowmanxxxx.e())) {
               cuo _snowmanxxxxx = this.e();
               _snowmanxxxxx.a(_snowman);
            }

            EnumSet<chn.a> _snowmanxxxxx = this.k().h();
            EnumSet<chn.a> _snowmanxxxxxx = null;

            for (chn.a _snowmanxxxxxxx : _snowmanxxxxx) {
               chn _snowmanxxxxxxxx = this.f.get(_snowmanxxxxxxx);
               if (_snowmanxxxxxxxx == null) {
                  if (_snowmanxxxxxx == null) {
                     _snowmanxxxxxx = EnumSet.noneOf(chn.a.class);
                  }

                  _snowmanxxxxxx.add(_snowmanxxxxxxx);
               }
            }

            if (_snowmanxxxxxx != null) {
               chn.a(this, _snowmanxxxxxx);
            }

            for (chn.a _snowmanxxxxxxxx : _snowmanxxxxx) {
               this.f.get(_snowmanxxxxxxxx).a(_snowman & 15, _snowmanx, _snowmanxx & 15, _snowman);
            }

            return _snowmanxxxx;
         }
      } else {
         return bup.la.n();
      }
   }

   public cgi a(int var1) {
      if (this.j[_snowman] == cgh.a) {
         this.j[_snowman] = new cgi(_snowman << 4);
      }

      return this.j[_snowman];
   }

   @Override
   public void a(fx var1, ccj var2) {
      _snowman.a(_snowman);
      this.h.put(_snowman, _snowman);
   }

   @Override
   public Set<fx> c() {
      Set<fx> _snowman = Sets.newHashSet(this.i.keySet());
      _snowman.addAll(this.h.keySet());
      return _snowman;
   }

   @Nullable
   @Override
   public ccj c(fx var1) {
      return this.h.get(_snowman);
   }

   public Map<fx, ccj> x() {
      return this.h;
   }

   public void b(md var1) {
      this.k.add(_snowman);
   }

   @Override
   public void a(aqa var1) {
      if (!_snowman.br()) {
         md _snowman = new md();
         _snowman.d(_snowman);
         this.b(_snowman);
      }
   }

   public List<md> y() {
      return this.k;
   }

   public void a(cfx var1) {
      this.d = _snowman;
   }

   @Nullable
   @Override
   public cfx i() {
      return this.d;
   }

   @Override
   public void a(boolean var1) {
      this.c = _snowman;
   }

   @Override
   public boolean j() {
      return this.c;
   }

   @Override
   public cga k() {
      return this.g;
   }

   public void a(cga var1) {
      this.g = _snowman;
      this.a(true);
   }

   @Override
   public cgi[] d() {
      return this.j;
   }

   @Nullable
   public cuo e() {
      return this.e;
   }

   @Override
   public Collection<Entry<chn.a, chn>> f() {
      return Collections.unmodifiableSet(this.f.entrySet());
   }

   @Override
   public void a(chn.a var1, long[] var2) {
      this.a(_snowman).a(_snowman);
   }

   @Override
   public chn a(chn.a var1) {
      return this.f.computeIfAbsent(_snowman, var1x -> new chn(this, var1x));
   }

   @Override
   public int a(chn.a var1, int var2, int var3) {
      chn _snowman = this.f.get(_snowman);
      if (_snowman == null) {
         chn.a(this, EnumSet.of(_snowman));
         _snowman = this.f.get(_snowman);
      }

      return _snowman.a(_snowman & 15, _snowman & 15) - 1;
   }

   @Override
   public brd g() {
      return this.b;
   }

   @Override
   public void a(long var1) {
   }

   @Nullable
   @Override
   public crv<?> a(cla<?> var1) {
      return this.n.get(_snowman);
   }

   @Override
   public void a(cla<?> var1, crv<?> var2) {
      this.n.put(_snowman, _snowman);
      this.c = true;
   }

   @Override
   public Map<cla<?>, crv<?>> h() {
      return Collections.unmodifiableMap(this.n);
   }

   @Override
   public void a(Map<cla<?>, crv<?>> var1) {
      this.n.clear();
      this.n.putAll(_snowman);
      this.c = true;
   }

   @Override
   public LongSet b(cla<?> var1) {
      return this.o.computeIfAbsent(_snowman, var0 -> new LongOpenHashSet());
   }

   @Override
   public void a(cla<?> var1, long var2) {
      this.o.computeIfAbsent(_snowman, var0 -> new LongOpenHashSet()).add(_snowman);
      this.c = true;
   }

   @Override
   public Map<cla<?>, LongSet> v() {
      return Collections.unmodifiableMap(this.o);
   }

   @Override
   public void b(Map<cla<?>, LongSet> var1) {
      this.o.clear();
      this.o.putAll(_snowman);
      this.c = true;
   }

   public static short l(fx var0) {
      int _snowman = _snowman.u();
      int _snowmanx = _snowman.v();
      int _snowmanxx = _snowman.w();
      int _snowmanxxx = _snowman & 15;
      int _snowmanxxxx = _snowmanx & 15;
      int _snowmanxxxxx = _snowmanxx & 15;
      return (short)(_snowmanxxx | _snowmanxxxx << 4 | _snowmanxxxxx << 8);
   }

   public static fx a(short var0, int var1, brd var2) {
      int _snowman = (_snowman & 15) + (_snowman.b << 4);
      int _snowmanx = (_snowman >>> 4 & 15) + (_snowman << 4);
      int _snowmanxx = (_snowman >>> 8 & 15) + (_snowman.c << 4);
      return new fx(_snowman, _snowmanx, _snowmanxx);
   }

   @Override
   public void e(fx var1) {
      if (!brx.m(_snowman)) {
         cfw.a(this.m, _snowman.v() >> 4).add(l(_snowman));
      }
   }

   @Override
   public ShortList[] l() {
      return this.m;
   }

   @Override
   public void a(short var1, int var2) {
      cfw.a(this.m, _snowman).add(_snowman);
   }

   public cgq<buo> s() {
      return this.q;
   }

   public cgq<cuw> t() {
      return this.r;
   }

   @Override
   public cgr p() {
      return this.p;
   }

   @Override
   public void b(long var1) {
      this.s = _snowman;
   }

   @Override
   public long q() {
      return this.s;
   }

   @Override
   public void a(md var1) {
      this.i.put(new fx(_snowman.h("x"), _snowman.h("y"), _snowman.h("z")), _snowman);
   }

   public Map<fx, md> z() {
      return Collections.unmodifiableMap(this.i);
   }

   @Override
   public md i(fx var1) {
      return this.i.get(_snowman);
   }

   @Nullable
   @Override
   public md j(fx var1) {
      ccj _snowman = this.c(_snowman);
      return _snowman != null ? _snowman.a(new md()) : this.i.get(_snowman);
   }

   @Override
   public void d(fx var1) {
      this.h.remove(_snowman);
      this.i.remove(_snowman);
   }

   @Nullable
   public BitSet a(chm.a var1) {
      return this.t.get(_snowman);
   }

   public BitSet b(chm.a var1) {
      return this.t.computeIfAbsent(_snowman, var0 -> new BitSet(65536));
   }

   public void a(chm.a var1, BitSet var2) {
      this.t.put(_snowman, _snowman);
   }

   public void a(cuo var1) {
      this.e = _snowman;
   }

   @Override
   public boolean r() {
      return this.u;
   }

   @Override
   public void b(boolean var1) {
      this.u = _snowman;
      this.a(true);
   }
}
