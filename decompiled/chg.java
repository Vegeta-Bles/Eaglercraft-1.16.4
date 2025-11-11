import com.google.common.collect.ContiguousSet;
import com.google.common.collect.DiscreteDomain;
import com.google.common.collect.Lists;
import com.google.common.collect.Range;
import com.google.common.collect.Sets;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.UUID;
import java.util.function.Predicate;
import javax.annotation.Nullable;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class chg {
   private static final Logger a = LogManager.getLogger();
   private static final Predicate<aqa> b = aqd.a.and(aqd.a(0.0, 128.0, 0.0, 192.0));
   private final aad c = (aad)new aad(new of("entity.minecraft.ender_dragon"), aok.a.a, aok.b.a).b(true).c(true);
   private final aag d;
   private final List<Integer> e = Lists.newArrayList();
   private final cem f;
   private int g;
   private int h;
   private int i;
   private int j;
   private boolean k;
   private boolean l;
   private UUID m;
   private boolean n = true;
   private fx o;
   private chf p;
   private int q;
   private List<bbq> r;

   public chg(aag var1, long var2, md var4) {
      this.d = _snowman;
      if (_snowman.c("DragonKilled", 99)) {
         if (_snowman.b("Dragon")) {
            this.m = _snowman.a("Dragon");
         }

         this.k = _snowman.q("DragonKilled");
         this.l = _snowman.q("PreviouslyKilled");
         if (_snowman.q("IsRespawning")) {
            this.p = chf.a;
         }

         if (_snowman.c("ExitPortalLocation", 10)) {
            this.o = mp.b(_snowman.p("ExitPortalLocation"));
         }
      } else {
         this.k = true;
         this.l = true;
      }

      if (_snowman.c("Gateways", 9)) {
         mj _snowman = _snowman.d("Gateways", 3);

         for (int _snowmanx = 0; _snowmanx < _snowman.size(); _snowmanx++) {
            this.e.add(_snowman.e(_snowmanx));
         }
      } else {
         this.e.addAll(ContiguousSet.create(Range.closedOpen(0, 20), DiscreteDomain.integers()));
         Collections.shuffle(this.e, new Random(_snowman));
      }

      this.f = cen.a()
         .a("       ", "       ", "       ", "   #   ", "       ", "       ", "       ")
         .a("       ", "       ", "       ", "   #   ", "       ", "       ", "       ")
         .a("       ", "       ", "       ", "   #   ", "       ", "       ", "       ")
         .a("  ###  ", " #   # ", "#     #", "#  #  #", "#     #", " #   # ", "  ###  ")
         .a("       ", "  ###  ", " ##### ", " ##### ", " ##### ", "  ###  ", "       ")
         .a('#', cel.a(ceq.a(bup.z)))
         .b();
   }

   public md a() {
      md _snowman = new md();
      if (this.m != null) {
         _snowman.a("Dragon", this.m);
      }

      _snowman.a("DragonKilled", this.k);
      _snowman.a("PreviouslyKilled", this.l);
      if (this.o != null) {
         _snowman.a("ExitPortalLocation", mp.a(this.o));
      }

      mj _snowmanx = new mj();

      for (int _snowmanxx : this.e) {
         _snowmanx.add(mi.a(_snowmanxx));
      }

      _snowman.a("Gateways", _snowmanx);
      return _snowman;
   }

   public void b() {
      this.c.d(!this.k);
      if (++this.j >= 20) {
         this.l();
         this.j = 0;
      }

      if (!this.c.h().isEmpty()) {
         this.d.i().a(aal.b, new brd(0, 0), 9, afx.a);
         boolean _snowman = this.k();
         if (this.n && _snowman) {
            this.g();
            this.n = false;
         }

         if (this.p != null) {
            if (this.r == null && _snowman) {
               this.p = null;
               this.e();
            }

            this.p.a(this.d, this, this.r, this.q++, this.o);
         }

         if (!this.k) {
            if ((this.m == null || ++this.g >= 1200) && _snowman) {
               this.h();
               this.g = 0;
            }

            if (++this.i >= 100 && _snowman) {
               this.m();
               this.i = 0;
            }
         }
      } else {
         this.d.i().b(aal.b, new brd(0, 0), 9, afx.a);
      }
   }

   private void g() {
      a.info("Scanning for legacy world dragon fight...");
      boolean _snowman = this.i();
      if (_snowman) {
         a.info("Found that the dragon has been killed in this world already.");
         this.l = true;
      } else {
         a.info("Found that the dragon has not yet been killed in this world.");
         this.l = false;
         if (this.j() == null) {
            this.a(false);
         }
      }

      List<bbr> _snowmanx = this.d.g();
      if (_snowmanx.isEmpty()) {
         this.k = true;
      } else {
         bbr _snowmanxx = _snowmanx.get(0);
         this.m = _snowmanxx.bS();
         a.info("Found that there's a dragon still alive ({})", _snowmanxx);
         this.k = false;
         if (!_snowman) {
            a.info("But we didn't have a portal, let's remove it.");
            _snowmanxx.ad();
            this.m = null;
         }
      }

      if (!this.l && this.k) {
         this.k = false;
      }
   }

   private void h() {
      List<bbr> _snowman = this.d.g();
      if (_snowman.isEmpty()) {
         a.debug("Haven't seen the dragon, respawning it");
         this.o();
      } else {
         a.debug("Haven't seen our dragon, but found another one to use.");
         this.m = _snowman.get(0).bS();
      }
   }

   protected void a(chf var1) {
      if (this.p == null) {
         throw new IllegalStateException("Dragon respawn isn't in progress, can't skip ahead in the animation.");
      } else {
         this.q = 0;
         if (_snowman == chf.e) {
            this.p = null;
            this.k = false;
            bbr _snowman = this.o();

            for (aah _snowmanx : this.c.h()) {
               ac.n.a(_snowmanx, _snowman);
            }
         } else {
            this.p = _snowman;
         }
      }
   }

   private boolean i() {
      for (int _snowman = -8; _snowman <= 8; _snowman++) {
         for (int _snowmanx = -8; _snowmanx <= 8; _snowmanx++) {
            cgh _snowmanxx = this.d.d(_snowman, _snowmanx);

            for (ccj _snowmanxxx : _snowmanxx.y().values()) {
               if (_snowmanxxx instanceof cdl) {
                  return true;
               }
            }
         }
      }

      return false;
   }

   @Nullable
   private cem.b j() {
      for (int _snowman = -8; _snowman <= 8; _snowman++) {
         for (int _snowmanx = -8; _snowmanx <= 8; _snowmanx++) {
            cgh _snowmanxx = this.d.d(_snowman, _snowmanx);

            for (ccj _snowmanxxx : _snowmanxx.y().values()) {
               if (_snowmanxxx instanceof cdl) {
                  cem.b _snowmanxxxx = this.f.a(this.d, _snowmanxxx.o());
                  if (_snowmanxxxx != null) {
                     fx _snowmanxxxxx = _snowmanxxxx.a(3, 3, 3).d();
                     if (this.o == null && _snowmanxxxxx.u() == 0 && _snowmanxxxxx.w() == 0) {
                        this.o = _snowmanxxxxx;
                     }

                     return _snowmanxxxx;
                  }
               }
            }
         }
      }

      int _snowman = this.d.a(chn.a.e, cjk.a).v();

      for (int _snowmanx = _snowman; _snowmanx >= 0; _snowmanx--) {
         cem.b _snowmanxx = this.f.a(this.d, new fx(cjk.a.u(), _snowmanx, cjk.a.w()));
         if (_snowmanxx != null) {
            if (this.o == null) {
               this.o = _snowmanxx.a(3, 3, 3).d();
            }

            return _snowmanxx;
         }
      }

      return null;
   }

   private boolean k() {
      for (int _snowman = -8; _snowman <= 8; _snowman++) {
         for (int _snowmanx = 8; _snowmanx <= 8; _snowmanx++) {
            cfw _snowmanxx = this.d.a(_snowman, _snowmanx, cga.m, false);
            if (!(_snowmanxx instanceof cgh)) {
               return false;
            }

            zr.b _snowmanxxx = ((cgh)_snowmanxx).u();
            if (!_snowmanxxx.a(zr.b.c)) {
               return false;
            }
         }
      }

      return true;
   }

   private void l() {
      Set<aah> _snowman = Sets.newHashSet();

      for (aah _snowmanx : this.d.a(b)) {
         this.c.a(_snowmanx);
         _snowman.add(_snowmanx);
      }

      Set<aah> _snowmanx = Sets.newHashSet(this.c.h());
      _snowmanx.removeAll(_snowman);

      for (aah _snowmanxx : _snowmanx) {
         this.c.b(_snowmanxx);
      }
   }

   private void m() {
      this.i = 0;
      this.h = 0;

      for (ckx.a _snowman : ckx.a(this.d)) {
         this.h = this.h + this.d.a(bbq.class, _snowman.f()).size();
      }

      a.debug("Found {} end crystals still alive", this.h);
   }

   public void a(bbr var1) {
      if (_snowman.bS().equals(this.m)) {
         this.c.a(0.0F);
         this.c.d(false);
         this.a(true);
         this.n();
         if (!this.l) {
            this.d.a(this.d.a(chn.a.e, cjk.a), bup.ef.n());
         }

         this.l = true;
         this.k = true;
      }
   }

   private void n() {
      if (!this.e.isEmpty()) {
         int _snowman = this.e.remove(this.e.size() - 1);
         int _snowmanx = afm.c(96.0 * Math.cos(2.0 * (-Math.PI + (Math.PI / 20) * (double)_snowman)));
         int _snowmanxx = afm.c(96.0 * Math.sin(2.0 * (-Math.PI + (Math.PI / 20) * (double)_snowman)));
         this.a(new fx(_snowmanx, 75, _snowmanxx));
      }
   }

   private void a(fx var1) {
      this.d.c(3000, _snowman, 0);
      kh.c.a(this.d, this.d.i().g(), new Random(), _snowman);
   }

   private void a(boolean var1) {
      cjk _snowman = new cjk(_snowman);
      if (this.o == null) {
         this.o = this.d.a(chn.a.f, cjk.a).c();

         while (this.d.d_(this.o).a(bup.z) && this.o.v() > this.d.t_()) {
            this.o = this.o.c();
         }
      }

      _snowman.b(cma.k).a(this.d, this.d.i().g(), new Random(), this.o);
   }

   private bbr o() {
      this.d.n(new fx(0, 128, 0));
      bbr _snowman = aqe.t.a(this.d);
      _snowman.eK().a(bch.a);
      _snowman.b(0.0, 128.0, 0.0, this.d.t.nextFloat() * 360.0F, 0.0F);
      this.d.c(_snowman);
      this.m = _snowman.bS();
      return _snowman;
   }

   public void b(bbr var1) {
      if (_snowman.bS().equals(this.m)) {
         this.c.a(_snowman.dk() / _snowman.dx());
         this.g = 0;
         if (_snowman.S()) {
            this.c.a(_snowman.d());
         }
      }
   }

   public int c() {
      return this.h;
   }

   public void a(bbq var1, apk var2) {
      if (this.p != null && this.r.contains(_snowman)) {
         a.debug("Aborting respawn sequence");
         this.p = null;
         this.q = 0;
         this.f();
         this.a(true);
      } else {
         this.m();
         aqa _snowman = this.d.a(this.m);
         if (_snowman instanceof bbr) {
            ((bbr)_snowman).a(_snowman, _snowman.cB(), _snowman);
         }
      }
   }

   public boolean d() {
      return this.l;
   }

   public void e() {
      if (this.k && this.p == null) {
         fx _snowman = this.o;
         if (_snowman == null) {
            a.debug("Tried to respawn, but need to find the portal first.");
            cem.b _snowmanx = this.j();
            if (_snowmanx == null) {
               a.debug("Couldn't find a portal, so we made one.");
               this.a(true);
            } else {
               a.debug("Found the exit portal & temporarily using it.");
            }

            _snowman = this.o;
         }

         List<bbq> _snowmanx = Lists.newArrayList();
         fx _snowmanxx = _snowman.b(1);

         for (gc _snowmanxxx : gc.c.a) {
            List<bbq> _snowmanxxxx = this.d.a(bbq.class, new dci(_snowmanxx.a(_snowmanxxx, 2)));
            if (_snowmanxxxx.isEmpty()) {
               return;
            }

            _snowmanx.addAll(_snowmanxxxx);
         }

         a.debug("Found all crystals, respawning dragon.");
         this.a(_snowmanx);
      }
   }

   private void a(List<bbq> var1) {
      if (this.k && this.p == null) {
         for (cem.b _snowman = this.j(); _snowman != null; _snowman = this.j()) {
            for (int _snowmanx = 0; _snowmanx < this.f.c(); _snowmanx++) {
               for (int _snowmanxx = 0; _snowmanxx < this.f.b(); _snowmanxx++) {
                  for (int _snowmanxxx = 0; _snowmanxxx < this.f.a(); _snowmanxxx++) {
                     cel _snowmanxxxx = _snowman.a(_snowmanx, _snowmanxx, _snowmanxxx);
                     if (_snowmanxxxx.a().a(bup.z) || _snowmanxxxx.a().a(bup.ec)) {
                        this.d.a(_snowmanxxxx.d(), bup.ee.n());
                     }
                  }
               }
            }
         }

         this.p = chf.a;
         this.q = 0;
         this.a(false);
         this.r = _snowman;
      }
   }

   public void f() {
      for (ckx.a _snowman : ckx.a(this.d)) {
         for (bbq _snowmanx : this.d.a(bbq.class, _snowman.f())) {
            _snowmanx.m(false);
            _snowmanx.a(null);
         }
      }
   }
}
