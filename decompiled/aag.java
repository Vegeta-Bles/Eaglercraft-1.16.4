import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Queues;
import com.google.common.collect.Sets;
import it.unimi.dsi.fastutil.ints.Int2ObjectLinkedOpenHashMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap.Entry;
import it.unimi.dsi.fastutil.longs.LongSet;
import it.unimi.dsi.fastutil.longs.LongSets;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import it.unimi.dsi.fastutil.objects.ObjectIterator;
import it.unimi.dsi.fastutil.objects.ObjectLinkedOpenHashSet;
import java.io.IOException;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Queue;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.Executor;
import java.util.function.BooleanSupplier;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import net.minecraft.server.MinecraftServer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class aag extends brx implements bsr {
   public static final fx a = new fx(100, 50, 0);
   private static final Logger x = LogManager.getLogger();
   private final Int2ObjectMap<aqa> y = new Int2ObjectLinkedOpenHashMap();
   private final Map<UUID, aqa> z = Maps.newHashMap();
   private final Queue<aqa> A = Queues.newArrayDeque();
   private final List<aah> B = Lists.newArrayList();
   private final aae C;
   boolean b;
   private final MinecraftServer D;
   private final cym E;
   public boolean c;
   private boolean F;
   private int G;
   private final cxl H;
   private final bsl<buo> I = new bsl<>(this, var0 -> var0 == null || var0.n().g(), gm.Q::b, this::b);
   private final bsl<cuw> J = new bsl<>(this, var0 -> var0 == null || var0 == cuy.a, gm.O::b, this::a);
   private final Set<ayj> K = Sets.newHashSet();
   protected final bhd d;
   private final ObjectLinkedOpenHashSet<brb> L = new ObjectLinkedOpenHashSet();
   private boolean M;
   private final List<brj> N;
   @Nullable
   private final chg O;
   private final bsn P;
   private final boolean Q;

   public aag(
      MinecraftServer var1,
      Executor var2,
      cyg.a var3,
      cym var4,
      vj<brx> var5,
      chd var6,
      aap var7,
      cfy var8,
      boolean var9,
      long var10,
      List<brj> var12,
      boolean var13
   ) {
      super(_snowman, _snowman, _snowman, _snowman::aQ, false, _snowman, _snowman);
      this.Q = _snowman;
      this.D = _snowman;
      this.N = _snowman;
      this.E = _snowman;
      this.C = new aae(this, _snowman, _snowman.az(), _snowman.aW(), _snowman, _snowman, _snowman.ae().p(), _snowman.aV(), _snowman, () -> _snowman.E().s());
      this.H = new cxl(this);
      this.Q();
      this.R();
      this.f().a(_snowman.au());
      this.d = this.s().a(() -> new bhd(this), bhd.a(this.k()));
      if (!_snowman.O()) {
         _snowman.a(_snowman.s());
      }

      this.P = new bsn(this, _snowman.aX().A());
      if (this.k().l()) {
         this.O = new chg(this, _snowman.aX().A().a(), _snowman.aX().C());
      } else {
         this.O = null;
      }
   }

   public void a(int var1, int var2, boolean var3, boolean var4) {
      this.E.a(_snowman);
      this.E.f(_snowman);
      this.E.e(_snowman);
      this.E.b(_snowman);
      this.E.a(_snowman);
   }

   @Override
   public bsv a(int var1, int var2, int var3) {
      return this.i().g().d().b(_snowman, _snowman, _snowman);
   }

   public bsn a() {
      return this.P;
   }

   public void a(BooleanSupplier var1) {
      anw _snowman = this.Z();
      this.M = true;
      _snowman.a("world border");
      this.f().s();
      _snowman.b("weather");
      boolean _snowmanx = this.X();
      if (this.k().b()) {
         if (this.V().b(brt.t)) {
            int _snowmanxx = this.E.h();
            int _snowmanxxx = this.E.j();
            int _snowmanxxxx = this.E.l();
            boolean _snowmanxxxxx = this.u.i();
            boolean _snowmanxxxxxx = this.u.k();
            if (_snowmanxx > 0) {
               _snowmanxx--;
               _snowmanxxx = _snowmanxxxxx ? 0 : 1;
               _snowmanxxxx = _snowmanxxxxxx ? 0 : 1;
               _snowmanxxxxx = false;
               _snowmanxxxxxx = false;
            } else {
               if (_snowmanxxx > 0) {
                  if (--_snowmanxxx == 0) {
                     _snowmanxxxxx = !_snowmanxxxxx;
                  }
               } else if (_snowmanxxxxx) {
                  _snowmanxxx = this.t.nextInt(12000) + 3600;
               } else {
                  _snowmanxxx = this.t.nextInt(168000) + 12000;
               }

               if (_snowmanxxxx > 0) {
                  if (--_snowmanxxxx == 0) {
                     _snowmanxxxxxx = !_snowmanxxxxxx;
                  }
               } else if (_snowmanxxxxxx) {
                  _snowmanxxxx = this.t.nextInt(12000) + 12000;
               } else {
                  _snowmanxxxx = this.t.nextInt(168000) + 12000;
               }
            }

            this.E.e(_snowmanxxx);
            this.E.f(_snowmanxxxx);
            this.E.a(_snowmanxx);
            this.E.a(_snowmanxxxxx);
            this.E.b(_snowmanxxxxxx);
         }

         this.r = this.s;
         if (this.u.i()) {
            this.s = (float)((double)this.s + 0.01);
         } else {
            this.s = (float)((double)this.s - 0.01);
         }

         this.s = afm.a(this.s, 0.0F, 1.0F);
         this.p = this.q;
         if (this.u.k()) {
            this.q = (float)((double)this.q + 0.01);
         } else {
            this.q = (float)((double)this.q - 0.01);
         }

         this.q = afm.a(this.q, 0.0F, 1.0F);
      }

      if (this.p != this.q) {
         this.D.ae().a(new pq(pq.h, this.q), this.Y());
      }

      if (this.r != this.s) {
         this.D.ae().a(new pq(pq.i, this.s), this.Y());
      }

      if (_snowmanx != this.X()) {
         if (_snowmanx) {
            this.D.ae().a(new pq(pq.c, 0.0F));
         } else {
            this.D.ae().a(new pq(pq.b, 0.0F));
         }

         this.D.ae().a(new pq(pq.h, this.q));
         this.D.ae().a(new pq(pq.i, this.s));
      }

      if (this.F && this.B.stream().noneMatch(var0 -> !var0.a_() && !var0.eB())) {
         this.F = false;
         if (this.V().b(brt.j)) {
            long _snowmanxx = this.u.f() + 24000L;
            this.a(_snowmanxx - _snowmanxx % 24000L);
         }

         this.ah();
         if (this.V().b(brt.t)) {
            this.ai();
         }
      }

      this.Q();
      this.b();
      _snowman.b("chunkSource");
      this.i().a(_snowman);
      _snowman.b("tickPending");
      if (!this.ab()) {
         this.I.b();
         this.J.b();
      }

      _snowman.b("raid");
      this.d.a();
      _snowman.b("blockEvents");
      this.ak();
      this.M = false;
      _snowman.b("entities");
      boolean _snowmanxx = !this.B.isEmpty() || !this.w().isEmpty();
      if (_snowmanxx) {
         this.p_();
      }

      if (_snowmanxx || this.G++ < 300) {
         if (this.O != null) {
            this.O.b();
         }

         this.b = true;
         ObjectIterator<Entry<aqa>> _snowmanxxx = this.y.int2ObjectEntrySet().iterator();

         while (_snowmanxxx.hasNext()) {
            Entry<aqa> _snowmanxxxx = (Entry<aqa>)_snowmanxxx.next();
            aqa _snowmanxxxxx = (aqa)_snowmanxxxx.getValue();
            aqa _snowmanxxxxxx = _snowmanxxxxx.ct();
            if (!this.D.X() && (_snowmanxxxxx instanceof azz || _snowmanxxxxx instanceof bay)) {
               _snowmanxxxxx.ad();
            }

            if (!this.D.Y() && _snowmanxxxxx instanceof bfi) {
               _snowmanxxxxx.ad();
            }

            _snowman.a("checkDespawn");
            if (!_snowmanxxxxx.y) {
               _snowmanxxxxx.cI();
            }

            _snowman.c();
            if (_snowmanxxxxxx != null) {
               if (!_snowmanxxxxxx.y && _snowmanxxxxxx.w(_snowmanxxxxx)) {
                  continue;
               }

               _snowmanxxxxx.l();
            }

            _snowman.a("tick");
            if (!_snowmanxxxxx.y && !(_snowmanxxxxx instanceof bbp)) {
               this.a(this::a, _snowmanxxxxx);
            }

            _snowman.c();
            _snowman.a("remove");
            if (_snowmanxxxxx.y) {
               this.p(_snowmanxxxxx);
               _snowmanxxx.remove();
               this.h(_snowmanxxxxx);
            }

            _snowman.c();
         }

         this.b = false;

         aqa _snowmanxxxxxxx;
         while ((_snowmanxxxxxxx = this.A.poll()) != null) {
            this.o(_snowmanxxxxxxx);
         }

         this.O();
      }

      _snowman.c();
   }

   protected void b() {
      if (this.Q) {
         long _snowman = this.u.e() + 1L;
         this.E.a(_snowman);
         this.E.u().a(this.D, _snowman);
         if (this.u.q().b(brt.j)) {
            this.a(this.u.f() + 1L);
         }
      }
   }

   public void a(long var1) {
      this.E.b(_snowman);
   }

   public void a(boolean var1, boolean var2) {
      for (brj _snowman : this.N) {
         _snowman.a(this, _snowman, _snowman);
      }
   }

   private void ah() {
      this.B.stream().filter(aqm::em).collect(Collectors.toList()).forEach(var0 -> var0.a(false, false));
   }

   public void a(cgh var1, int var2) {
      brd _snowman = _snowman.g();
      boolean _snowmanx = this.X();
      int _snowmanxx = _snowman.d();
      int _snowmanxxx = _snowman.e();
      anw _snowmanxxxx = this.Z();
      _snowmanxxxx.a("thunder");
      if (_snowmanx && this.W() && this.t.nextInt(100000) == 0) {
         fx _snowmanxxxxx = this.a(this.a(_snowmanxx, 0, _snowmanxxx, 15));
         if (this.t(_snowmanxxxxx)) {
            aos _snowmanxxxxxx = this.d(_snowmanxxxxx);
            boolean _snowmanxxxxxxx = this.V().b(brt.d) && this.t.nextDouble() < (double)_snowmanxxxxxx.b() * 0.01;
            if (_snowmanxxxxxxx) {
               bbh _snowmanxxxxxxxx = aqe.aw.a(this);
               _snowmanxxxxxxxx.t(true);
               _snowmanxxxxxxxx.c_(0);
               _snowmanxxxxxxxx.d((double)_snowmanxxxxx.u(), (double)_snowmanxxxxx.v(), (double)_snowmanxxxxx.w());
               this.c(_snowmanxxxxxxxx);
            }

            aql _snowmanxxxxxxxx = aqe.P.a(this);
            _snowmanxxxxxxxx.d(dcn.c(_snowmanxxxxx));
            _snowmanxxxxxxxx.a(_snowmanxxxxxxx);
            this.c(_snowmanxxxxxxxx);
         }
      }

      _snowmanxxxx.b("iceandsnow");
      if (this.t.nextInt(16) == 0) {
         fx _snowmanxxxxx = this.a(chn.a.e, this.a(_snowmanxx, 0, _snowmanxxx, 15));
         fx _snowmanxxxxxx = _snowmanxxxxx.c();
         bsv _snowmanxxxxxxx = this.v(_snowmanxxxxx);
         if (_snowmanxxxxxxx.a(this, _snowmanxxxxxx)) {
            this.a(_snowmanxxxxxx, bup.cD.n());
         }

         if (_snowmanx && _snowmanxxxxxxx.b(this, _snowmanxxxxx)) {
            this.a(_snowmanxxxxx, bup.cC.n());
         }

         if (_snowmanx && this.v(_snowmanxxxxxx).c() == bsv.e.b) {
            this.d_(_snowmanxxxxxx).b().c((brx)this, _snowmanxxxxxx);
         }
      }

      _snowmanxxxx.b("tickBlocks");
      if (_snowman > 0) {
         for (cgi _snowmanxxxxxxxx : _snowman.d()) {
            if (_snowmanxxxxxxxx != cgh.a && _snowmanxxxxxxxx.d()) {
               int _snowmanxxxxxxxxx = _snowmanxxxxxxxx.g();

               for (int _snowmanxxxxxxxxxx = 0; _snowmanxxxxxxxxxx < _snowman; _snowmanxxxxxxxxxx++) {
                  fx _snowmanxxxxxxxxxxx = this.a(_snowmanxx, _snowmanxxxxxxxxx, _snowmanxxx, 15);
                  _snowmanxxxx.a("randomTick");
                  ceh _snowmanxxxxxxxxxxxx = _snowmanxxxxxxxx.a(_snowmanxxxxxxxxxxx.u() - _snowmanxx, _snowmanxxxxxxxxxxx.v() - _snowmanxxxxxxxxx, _snowmanxxxxxxxxxxx.w() - _snowmanxxx);
                  if (_snowmanxxxxxxxxxxxx.n()) {
                     _snowmanxxxxxxxxxxxx.b(this, _snowmanxxxxxxxxxxx, this.t);
                  }

                  cux _snowmanxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxx.m();
                  if (_snowmanxxxxxxxxxxxxx.f()) {
                     _snowmanxxxxxxxxxxxxx.b(this, _snowmanxxxxxxxxxxx, this.t);
                  }

                  _snowmanxxxx.c();
               }
            }
         }
      }

      _snowmanxxxx.c();
   }

   protected fx a(fx var1) {
      fx _snowman = this.a(chn.a.e, _snowman);
      dci _snowmanx = new dci(_snowman, new fx(_snowman.u(), this.L(), _snowman.w())).g(3.0);
      List<aqm> _snowmanxx = this.a(aqm.class, _snowmanx, var1x -> var1x != null && var1x.aX() && this.e(var1x.cB()));
      if (!_snowmanxx.isEmpty()) {
         return _snowmanxx.get(this.t.nextInt(_snowmanxx.size())).cB();
      } else {
         if (_snowman.v() == -1) {
            _snowman = _snowman.b(2);
         }

         return _snowman;
      }
   }

   public boolean m_() {
      return this.M;
   }

   public void n_() {
      this.F = false;
      if (!this.B.isEmpty()) {
         int _snowman = 0;
         int _snowmanx = 0;

         for (aah _snowmanxx : this.B) {
            if (_snowmanxx.a_()) {
               _snowman++;
            } else if (_snowmanxx.em()) {
               _snowmanx++;
            }
         }

         this.F = _snowmanx > 0 && _snowmanx >= this.B.size() - _snowman;
      }
   }

   public wa o_() {
      return this.D.aH();
   }

   private void ai() {
      this.E.f(0);
      this.E.b(false);
      this.E.e(0);
      this.E.a(false);
   }

   public void p_() {
      this.G = 0;
   }

   private void a(bsp<cuw> var1) {
      cux _snowman = this.b(_snowman.a);
      if (_snowman.a() == _snowman.b()) {
         _snowman.a((brx)this, _snowman.a);
      }
   }

   private void b(bsp<buo> var1) {
      ceh _snowman = this.d_(_snowman.a);
      if (_snowman.a(_snowman.b())) {
         _snowman.a(this, _snowman.a, this.t);
      }
   }

   public void a(aqa var1) {
      if (!(_snowman instanceof bfw) && !this.i().a(_snowman)) {
         this.b(_snowman);
      } else {
         _snowman.g(_snowman.cD(), _snowman.cE(), _snowman.cH());
         _snowman.r = _snowman.p;
         _snowman.s = _snowman.q;
         if (_snowman.U) {
            _snowman.K++;
            anw _snowman = this.Z();
            _snowman.a(() -> gm.S.b(_snowman.X()).toString());
            _snowman.c("tickNonPassenger");
            _snowman.j();
            _snowman.c();
         }

         this.b(_snowman);
         if (_snowman.U) {
            for (aqa _snowman : _snowman.cn()) {
               this.a(_snowman, _snowman);
            }
         }
      }
   }

   public void a(aqa var1, aqa var2) {
      if (_snowman.y || _snowman.ct() != _snowman) {
         _snowman.l();
      } else if (_snowman instanceof bfw || this.i().a(_snowman)) {
         _snowman.g(_snowman.cD(), _snowman.cE(), _snowman.cH());
         _snowman.r = _snowman.p;
         _snowman.s = _snowman.q;
         if (_snowman.U) {
            _snowman.K++;
            anw _snowman = this.Z();
            _snowman.a(() -> gm.S.b(_snowman.X()).toString());
            _snowman.c("tickPassenger");
            _snowman.ba();
            _snowman.c();
         }

         this.b(_snowman);
         if (_snowman.U) {
            for (aqa _snowman : _snowman.cn()) {
               this.a(_snowman, _snowman);
            }
         }
      }
   }

   public void b(aqa var1) {
      if (_snowman.cl()) {
         this.Z().a("chunkCheck");
         int _snowman = afm.c(_snowman.cD() / 16.0);
         int _snowmanx = afm.c(_snowman.cE() / 16.0);
         int _snowmanxx = afm.c(_snowman.cH() / 16.0);
         if (!_snowman.U || _snowman.V != _snowman || _snowman.W != _snowmanx || _snowman.X != _snowmanxx) {
            if (_snowman.U && this.b(_snowman.V, _snowman.X)) {
               this.d(_snowman.V, _snowman.X).a(_snowman, _snowman.W);
            }

            if (!_snowman.ck() && !this.b(_snowman, _snowmanxx)) {
               if (_snowman.U) {
                  x.warn("Entity {} left loaded chunk area", _snowman);
               }

               _snowman.U = false;
            } else {
               this.d(_snowman, _snowmanxx).a(_snowman);
            }
         }

         this.Z().c();
      }
   }

   @Override
   public boolean a(bfw var1, fx var2) {
      return !this.D.a(this, _snowman, _snowman) && this.f().a(_snowman);
   }

   public void a(@Nullable afn var1, boolean var2, boolean var3) {
      aae _snowman = this.i();
      if (!_snowman) {
         if (_snowman != null) {
            _snowman.a(new of("menu.savingLevel"));
         }

         this.aj();
         if (_snowman != null) {
            _snowman.c(new of("menu.savingChunks"));
         }

         _snowman.a(_snowman);
      }
   }

   private void aj() {
      if (this.O != null) {
         this.D.aX().a(this.O.a());
      }

      this.i().i().a();
   }

   public List<aqa> a(@Nullable aqe<?> var1, Predicate<? super aqa> var2) {
      List<aqa> _snowman = Lists.newArrayList();
      aae _snowmanx = this.i();
      ObjectIterator var5 = this.y.values().iterator();

      while (var5.hasNext()) {
         aqa _snowmanxx = (aqa)var5.next();
         if ((_snowman == null || _snowmanxx.X() == _snowman) && _snowmanx.b(afm.c(_snowmanxx.cD()) >> 4, afm.c(_snowmanxx.cH()) >> 4) && _snowman.test(_snowmanxx)) {
            _snowman.add(_snowmanxx);
         }
      }

      return _snowman;
   }

   public List<bbr> g() {
      List<bbr> _snowman = Lists.newArrayList();
      ObjectIterator var2 = this.y.values().iterator();

      while (var2.hasNext()) {
         aqa _snowmanx = (aqa)var2.next();
         if (_snowmanx instanceof bbr && _snowmanx.aX()) {
            _snowman.add((bbr)_snowmanx);
         }
      }

      return _snowman;
   }

   public List<aah> a(Predicate<? super aah> var1) {
      List<aah> _snowman = Lists.newArrayList();

      for (aah _snowmanx : this.B) {
         if (_snowman.test(_snowmanx)) {
            _snowman.add(_snowmanx);
         }
      }

      return _snowman;
   }

   @Nullable
   public aah q_() {
      List<aah> _snowman = this.a(aqm::aX);
      return _snowman.isEmpty() ? null : _snowman.get(this.t.nextInt(_snowman.size()));
   }

   @Override
   public boolean c(aqa var1) {
      return this.m(_snowman);
   }

   public boolean d(aqa var1) {
      return this.m(_snowman);
   }

   public void e(aqa var1) {
      boolean _snowman = _snowman.k;
      _snowman.k = true;
      this.d(_snowman);
      _snowman.k = _snowman;
      this.b(_snowman);
   }

   public void a(aah var1) {
      this.f(_snowman);
      this.b((aqa)_snowman);
   }

   public void b(aah var1) {
      this.f(_snowman);
      this.b((aqa)_snowman);
   }

   public void c(aah var1) {
      this.f(_snowman);
   }

   public void d(aah var1) {
      this.f(_snowman);
   }

   private void f(aah var1) {
      aqa _snowman = this.z.get(_snowman.bS());
      if (_snowman != null) {
         x.warn("Force-added player with duplicate UUID {}", _snowman.bS().toString());
         _snowman.V();
         this.e((aah)_snowman);
      }

      this.B.add(_snowman);
      this.n_();
      cfw _snowmanx = this.a(afm.c(_snowman.cD() / 16.0), afm.c(_snowman.cH() / 16.0), cga.m, true);
      if (_snowmanx instanceof cgh) {
         _snowmanx.a(_snowman);
      }

      this.o(_snowman);
   }

   private boolean m(aqa var1) {
      if (_snowman.y) {
         x.warn("Tried to add entity {} but it was marked as removed already", aqe.a(_snowman.X()));
         return false;
      } else if (this.n(_snowman)) {
         return false;
      } else {
         cfw _snowman = this.a(afm.c(_snowman.cD() / 16.0), afm.c(_snowman.cH() / 16.0), cga.m, _snowman.k);
         if (!(_snowman instanceof cgh)) {
            return false;
         } else {
            _snowman.a(_snowman);
            this.o(_snowman);
            return true;
         }
      }
   }

   public boolean f(aqa var1) {
      if (this.n(_snowman)) {
         return false;
      } else {
         this.o(_snowman);
         return true;
      }
   }

   private boolean n(aqa var1) {
      UUID _snowman = _snowman.bS();
      aqa _snowmanx = this.c(_snowman);
      if (_snowmanx == null) {
         return false;
      } else {
         x.warn("Trying to add entity with duplicated UUID {}. Existing {}#{}, new: {}#{}", _snowman, aqe.a(_snowmanx.X()), _snowmanx.Y(), aqe.a(_snowman.X()), _snowman.Y());
         return true;
      }
   }

   @Nullable
   private aqa c(UUID var1) {
      aqa _snowman = this.z.get(_snowman);
      if (_snowman != null) {
         return _snowman;
      } else {
         if (this.b) {
            for (aqa _snowmanx : this.A) {
               if (_snowmanx.bS().equals(_snowman)) {
                  return _snowmanx;
               }
            }
         }

         return null;
      }
   }

   public boolean g(aqa var1) {
      if (_snowman.cp().anyMatch(this::n)) {
         return false;
      } else {
         this.l(_snowman);
         return true;
      }
   }

   public void a(cgh var1) {
      this.m.addAll(_snowman.y().values());
      aes[] var2 = _snowman.z();
      int var3 = var2.length;

      for (int var4 = 0; var4 < var3; var4++) {
         for (aqa _snowman : var2[var4]) {
            if (!(_snowman instanceof aah)) {
               if (this.b) {
                  throw (IllegalStateException)x.c(new IllegalStateException("Removing entity while ticking!"));
               }

               this.y.remove(_snowman.Y());
               this.h(_snowman);
            }
         }
      }
   }

   public void h(aqa var1) {
      if (_snowman instanceof bbr) {
         for (bbp _snowman : ((bbr)_snowman).eJ()) {
            _snowman.ad();
         }
      }

      this.z.remove(_snowman.bS());
      this.i().b(_snowman);
      if (_snowman instanceof aah) {
         aah _snowman = (aah)_snowman;
         this.B.remove(_snowman);
      }

      this.o_().a(_snowman);
      if (_snowman instanceof aqn) {
         this.K.remove(((aqn)_snowman).x());
      }
   }

   private void o(aqa var1) {
      if (this.b) {
         this.A.add(_snowman);
      } else {
         this.y.put(_snowman.Y(), _snowman);
         if (_snowman instanceof bbr) {
            for (bbp _snowman : ((bbr)_snowman).eJ()) {
               this.y.put(_snowman.Y(), _snowman);
            }
         }

         this.z.put(_snowman.bS(), _snowman);
         this.i().c(_snowman);
         if (_snowman instanceof aqn) {
            this.K.add(((aqn)_snowman).x());
         }
      }
   }

   public void i(aqa var1) {
      if (this.b) {
         throw (IllegalStateException)x.c(new IllegalStateException("Removing entity while ticking!"));
      } else {
         this.p(_snowman);
         this.y.remove(_snowman.Y());
         this.h(_snowman);
      }
   }

   private void p(aqa var1) {
      cfw _snowman = this.a(_snowman.V, _snowman.X, cga.m, false);
      if (_snowman instanceof cgh) {
         ((cgh)_snowman).b(_snowman);
      }
   }

   public void e(aah var1) {
      _snowman.ad();
      this.i(_snowman);
      this.n_();
   }

   @Override
   public void a(int var1, fx var2, int var3) {
      for (aah _snowman : this.D.ae().s()) {
         if (_snowman != null && _snowman.l == this && _snowman.Y() != _snowman) {
            double _snowmanx = (double)_snowman.u() - _snowman.cD();
            double _snowmanxx = (double)_snowman.v() - _snowman.cE();
            double _snowmanxxx = (double)_snowman.w() - _snowman.cH();
            if (_snowmanx * _snowmanx + _snowmanxx * _snowmanxx + _snowmanxxx * _snowmanxxx < 1024.0) {
               _snowman.b.a(new ov(_snowman, _snowman, _snowman));
            }
         }
      }
   }

   @Override
   public void a(@Nullable bfw var1, double var2, double var4, double var6, adp var8, adr var9, float var10, float var11) {
      this.D.ae().a(_snowman, _snowman, _snowman, _snowman, _snowman > 1.0F ? (double)(16.0F * _snowman) : 16.0, this.Y(), new rn(_snowman, _snowman, _snowman, _snowman, _snowman, _snowman, _snowman));
   }

   @Override
   public void a(@Nullable bfw var1, aqa var2, adp var3, adr var4, float var5, float var6) {
      this.D.ae().a(_snowman, _snowman.cD(), _snowman.cE(), _snowman.cH(), _snowman > 1.0F ? (double)(16.0F * _snowman) : 16.0, this.Y(), new rm(_snowman, _snowman, _snowman, _snowman, _snowman));
   }

   @Override
   public void b(int var1, fx var2, int var3) {
      this.D.ae().a(new pu(_snowman, _snowman, _snowman, true));
   }

   @Override
   public void a(@Nullable bfw var1, int var2, fx var3, int var4) {
      this.D.ae().a(_snowman, (double)_snowman.u(), (double)_snowman.v(), (double)_snowman.w(), 64.0, this.Y(), new pu(_snowman, _snowman, _snowman, false));
   }

   @Override
   public void a(fx var1, ceh var2, ceh var3, int var4) {
      this.i().b(_snowman);
      ddh _snowman = _snowman.k(this, _snowman);
      ddh _snowmanx = _snowman.k(this, _snowman);
      if (dde.c(_snowman, _snowmanx, dcr.g)) {
         for (ayj _snowmanxx : this.K) {
            if (!_snowmanxx.i()) {
               _snowmanxx.b(_snowman);
            }
         }
      }
   }

   @Override
   public void a(aqa var1, byte var2) {
      this.i().a(_snowman, new pn(_snowman, _snowman));
   }

   public aae i() {
      return this.C;
   }

   @Override
   public brp a(@Nullable aqa var1, @Nullable apk var2, @Nullable brq var3, double var4, double var6, double var8, float var10, boolean var11, brp.a var12) {
      brp _snowman = new brp(this, _snowman, _snowman, _snowman, _snowman, _snowman, _snowman, _snowman, _snowman, _snowman);
      _snowman.a();
      _snowman.a(false);
      if (_snowman == brp.a.a) {
         _snowman.e();
      }

      for (aah _snowmanx : this.B) {
         if (_snowmanx.h(_snowman, _snowman, _snowman) < 4096.0) {
            _snowmanx.b.a(new po(_snowman, _snowman, _snowman, _snowman, _snowman.f(), _snowman.c().get(_snowmanx)));
         }
      }

      return _snowman;
   }

   @Override
   public void a(fx var1, buo var2, int var3, int var4) {
      this.L.add(new brb(_snowman, _snowman, _snowman, _snowman));
   }

   private void ak() {
      while (!this.L.isEmpty()) {
         brb _snowman = (brb)this.L.removeFirst();
         if (this.a(_snowman)) {
            this.D.ae().a(null, (double)_snowman.a().u(), (double)_snowman.a().v(), (double)_snowman.a().w(), 64.0, this.Y(), new ox(_snowman.a(), _snowman.b(), _snowman.c(), _snowman.d()));
         }
      }
   }

   private boolean a(brb var1) {
      ceh _snowman = this.d_(_snowman.a());
      return _snowman.a(_snowman.b()) ? _snowman.a(this, _snowman.a(), _snowman.c(), _snowman.d()) : false;
   }

   public bsl<buo> j() {
      return this.I;
   }

   public bsl<cuw> r_() {
      return this.J;
   }

   @Nonnull
   @Override
   public MinecraftServer l() {
      return this.D;
   }

   public cxl m() {
      return this.H;
   }

   public csw n() {
      return this.D.aW();
   }

   public <T extends hf> int a(T var1, double var2, double var4, double var6, int var8, double var9, double var11, double var13, double var15) {
      pv _snowman = new pv(_snowman, false, _snowman, _snowman, _snowman, (float)_snowman, (float)_snowman, (float)_snowman, (float)_snowman, _snowman);
      int _snowmanx = 0;

      for (int _snowmanxx = 0; _snowmanxx < this.B.size(); _snowmanxx++) {
         aah _snowmanxxx = this.B.get(_snowmanxx);
         if (this.a(_snowmanxxx, false, _snowman, _snowman, _snowman, _snowman)) {
            _snowmanx++;
         }
      }

      return _snowmanx;
   }

   public <T extends hf> boolean a(
      aah var1, T var2, boolean var3, double var4, double var6, double var8, int var10, double var11, double var13, double var15, double var17
   ) {
      oj<?> _snowman = new pv(_snowman, _snowman, _snowman, _snowman, _snowman, (float)_snowman, (float)_snowman, (float)_snowman, (float)_snowman, _snowman);
      return this.a(_snowman, _snowman, _snowman, _snowman, _snowman, _snowman);
   }

   private boolean a(aah var1, boolean var2, double var3, double var5, double var7, oj<?> var9) {
      if (_snowman.u() != this) {
         return false;
      } else {
         fx _snowman = _snowman.cB();
         if (_snowman.a(new dcn(_snowman, _snowman, _snowman), _snowman ? 512.0 : 32.0)) {
            _snowman.b.a(_snowman);
            return true;
         } else {
            return false;
         }
      }
   }

   @Nullable
   @Override
   public aqa a(int var1) {
      return (aqa)this.y.get(_snowman);
   }

   @Nullable
   public aqa a(UUID var1) {
      return this.z.get(_snowman);
   }

   @Nullable
   public fx a(cla<?> var1, fx var2, int var3, boolean var4) {
      return !this.D.aX().A().b() ? null : this.i().g().a(this, _snowman, _snowman, _snowman, _snowman);
   }

   @Nullable
   public fx a(bsv var1, fx var2, int var3, int var4) {
      return this.i().g().d().a(_snowman.u(), _snowman.v(), _snowman.w(), _snowman, _snowman, var1x -> var1x == _snowman, this.t, true);
   }

   @Override
   public bor o() {
      return this.D.aF();
   }

   @Override
   public aen p() {
      return this.D.aG();
   }

   @Override
   public boolean q() {
      return this.c;
   }

   @Override
   public gn r() {
      return this.D.aY();
   }

   public cyc s() {
      return this.i().i();
   }

   @Nullable
   @Override
   public cxx a(String var1) {
      return this.l().E().s().b(() -> new cxx(_snowman), _snowman);
   }

   @Override
   public void a(cxx var1) {
      this.l().E().s().a(_snowman);
   }

   @Override
   public int t() {
      return this.l().E().s().a(cxw::new, "idcounts").a();
   }

   public void a(fx var1, float var2) {
      brd _snowman = new brd(new fx(this.u.a(), 0, this.u.c()));
      this.u.a(_snowman, _snowman);
      this.i().b(aal.a, _snowman, 11, afx.a);
      this.i().a(aal.a, new brd(_snowman), 11, afx.a);
      this.l().ae().a(new qy(_snowman, _snowman));
   }

   public fx u() {
      fx _snowman = new fx(this.u.a(), this.u.b(), this.u.c());
      if (!this.f().a(_snowman)) {
         _snowman = this.a(chn.a.e, new fx(this.f().a(), 0.0, this.f().b()));
      }

      return _snowman;
   }

   public float v() {
      return this.u.d();
   }

   public LongSet w() {
      brs _snowman = this.s().b(brs::new, "chunks");
      return (LongSet)(_snowman != null ? LongSets.unmodifiable(_snowman.a()) : LongSets.EMPTY_SET);
   }

   public boolean a(int var1, int var2, boolean var3) {
      brs _snowman = this.s().a(brs::new, "chunks");
      brd _snowmanx = new brd(_snowman, _snowman);
      long _snowmanxx = _snowmanx.a();
      boolean _snowmanxxx;
      if (_snowman) {
         _snowmanxxx = _snowman.a().add(_snowmanxx);
         if (_snowmanxxx) {
            this.d(_snowman, _snowman);
         }
      } else {
         _snowmanxxx = _snowman.a().remove(_snowmanxx);
      }

      _snowman.a(_snowmanxxx);
      if (_snowmanxxx) {
         this.i().a(_snowmanx, _snowman);
      }

      return _snowmanxxx;
   }

   @Override
   public List<aah> x() {
      return this.B;
   }

   @Override
   public void a(fx var1, ceh var2, ceh var3) {
      Optional<azr> _snowman = azr.b(_snowman);
      Optional<azr> _snowmanx = azr.b(_snowman);
      if (!Objects.equals(_snowman, _snowmanx)) {
         fx _snowmanxx = _snowman.h();
         _snowman.ifPresent(var2x -> this.l().execute(() -> {
               this.y().a(_snowman);
               rz.b(this, _snowman);
            }));
         _snowmanx.ifPresent(var2x -> this.l().execute(() -> {
               this.y().a(_snowman, var2x);
               rz.a(this, _snowman);
            }));
      }
   }

   public azo y() {
      return this.i().j();
   }

   public boolean a_(fx var1) {
      return this.a(_snowman, 1);
   }

   public boolean a(gp var1) {
      return this.a_(_snowman.q());
   }

   public boolean a(fx var1, int var2) {
      return _snowman > 6 ? false : this.b(gp.a(_snowman)) <= _snowman;
   }

   public int b(gp var1) {
      return this.y().a(_snowman);
   }

   public bhd z() {
      return this.d;
   }

   @Nullable
   public bhb b_(fx var1) {
      return this.d.a(_snowman, 9216);
   }

   public boolean c_(fx var1) {
      return this.b_(_snowman) != null;
   }

   public void a(azl var1, aqa var2, aqz var3) {
      _snowman.a(_snowman, _snowman);
   }

   public void a(Path var1) throws IOException {
      zs _snowman = this.i().a;

      try (Writer _snowmanx = Files.newBufferedWriter(_snowman.resolve("stats.txt"))) {
         _snowmanx.write(String.format("spawning_chunks: %d\n", _snowman.e().b()));
         bsg.d _snowmanxx = this.i().k();
         if (_snowmanxx != null) {
            ObjectIterator var6 = _snowmanxx.b().object2IntEntrySet().iterator();

            while (var6.hasNext()) {
               it.unimi.dsi.fastutil.objects.Object2IntMap.Entry<aqo> _snowmanxxx = (it.unimi.dsi.fastutil.objects.Object2IntMap.Entry<aqo>)var6.next();
               _snowmanx.write(String.format("spawn_count.%s: %d\n", ((aqo)_snowmanxxx.getKey()).b(), _snowmanxxx.getIntValue()));
            }
         }

         _snowmanx.write(String.format("entities: %d\n", this.y.size()));
         _snowmanx.write(String.format("block_entities: %d\n", this.j.size()));
         _snowmanx.write(String.format("block_ticks: %d\n", this.j().a()));
         _snowmanx.write(String.format("fluid_ticks: %d\n", this.r_().a()));
         _snowmanx.write("distance_manager: " + _snowman.e().c() + "\n");
         _snowmanx.write(String.format("pending_tasks: %d\n", this.i().f()));
      }

      l _snowmanx = new l("Level dump", new Exception("dummy"));
      this.a(_snowmanx);

      try (Writer _snowmanxx = Files.newBufferedWriter(_snowman.resolve("example_crash.txt"))) {
         _snowmanxx.write(_snowmanx.e());
      }

      Path _snowmanxx = _snowman.resolve("chunks.csv");

      try (Writer _snowmanxxx = Files.newBufferedWriter(_snowmanxx)) {
         _snowman.a(_snowmanxxx);
      }

      Path _snowmanxxx = _snowman.resolve("entities.csv");

      try (Writer _snowmanxxxx = Files.newBufferedWriter(_snowmanxxx)) {
         a(_snowmanxxxx, this.y.values());
      }

      Path _snowmanxxxx = _snowman.resolve("block_entities.csv");

      try (Writer _snowmanxxxxx = Files.newBufferedWriter(_snowmanxxxx)) {
         this.a(_snowmanxxxxx);
      }
   }

   private static void a(Writer var0, Iterable<aqa> var1) throws IOException {
      aew _snowman = aew.a().a("x").a("y").a("z").a("uuid").a("type").a("alive").a("display_name").a("custom_name").a(_snowman);

      for (aqa _snowmanx : _snowman) {
         nr _snowmanxx = _snowmanx.T();
         nr _snowmanxxx = _snowmanx.d();
         _snowman.a(_snowmanx.cD(), _snowmanx.cE(), _snowmanx.cH(), _snowmanx.bS(), gm.S.b(_snowmanx.X()), _snowmanx.aX(), _snowmanxxx.getString(), _snowmanxx != null ? _snowmanxx.getString() : null);
      }
   }

   private void a(Writer var1) throws IOException {
      aew _snowman = aew.a().a("x").a("y").a("z").a("type").a(_snowman);

      for (ccj _snowmanx : this.j) {
         fx _snowmanxx = _snowmanx.o();
         _snowman.a(_snowmanxx.u(), _snowmanxx.v(), _snowmanxx.w(), gm.W.b(_snowmanx.u()));
      }
   }

   @VisibleForTesting
   public void a(cra var1) {
      this.L.removeIf(var1x -> _snowman.b(var1x.a()));
   }

   @Override
   public void a(fx var1, buo var2) {
      if (!this.ab()) {
         this.b(_snowman, _snowman);
      }
   }

   @Override
   public float a(gc var1, boolean var2) {
      return 1.0F;
   }

   public Iterable<aqa> A() {
      return Iterables.unmodifiableIterable(this.y.values());
   }

   @Override
   public String toString() {
      return "ServerLevel[" + this.E.g() + "]";
   }

   public boolean B() {
      return this.D.aX().A().h();
   }

   @Override
   public long C() {
      return this.D.aX().A().a();
   }

   @Nullable
   public chg D() {
      return this.O;
   }

   @Override
   public Stream<? extends crv<?>> a(gp var1, cla<?> var2) {
      return this.a().a(_snowman, _snowman);
   }

   @Override
   public aag E() {
      return this;
   }

   @VisibleForTesting
   public String F() {
      return String.format(
         "players: %s, entities: %d [%s], block_entities: %d [%s], block_ticks: %d, fluid_ticks: %d, chunk_source: %s",
         this.B.size(),
         this.y.size(),
         a(this.y.values(), var0 -> gm.S.b(var0.X())),
         this.k.size(),
         a(this.k, var0 -> gm.W.b(var0.u())),
         this.j().a(),
         this.r_().a(),
         this.P()
      );
   }

   private static <T> String a(Collection<T> var0, Function<T, vk> var1) {
      try {
         Object2IntOpenHashMap<vk> _snowman = new Object2IntOpenHashMap();

         for (T _snowmanx : _snowman) {
            vk _snowmanxx = _snowman.apply(_snowmanx);
            _snowman.addTo(_snowmanxx, 1);
         }

         return _snowman.object2IntEntrySet()
            .stream()
            .sorted(Comparator.comparing(it.unimi.dsi.fastutil.objects.Object2IntMap.Entry::getIntValue).reversed())
            .limit(5L)
            .map(var0x -> var0x.getKey() + ":" + var0x.getIntValue())
            .collect(Collectors.joining(","));
      } catch (Exception var6) {
         return "";
      }
   }

   public static void a(aag var0) {
      fx _snowman = a;
      int _snowmanx = _snowman.u();
      int _snowmanxx = _snowman.v() - 2;
      int _snowmanxxx = _snowman.w();
      fx.b(_snowmanx - 2, _snowmanxx + 1, _snowmanxxx - 2, _snowmanx + 2, _snowmanxx + 3, _snowmanxxx + 2).forEach(var1x -> _snowman.a(var1x, bup.a.n()));
      fx.b(_snowmanx - 2, _snowmanxx, _snowmanxxx - 2, _snowmanx + 2, _snowmanxx, _snowmanxxx + 2).forEach(var1x -> _snowman.a(var1x, bup.bK.n()));
   }
}
