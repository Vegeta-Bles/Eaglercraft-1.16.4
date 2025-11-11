import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class asg extends arv<bfj> {
   private static final Map<bfm, vk> b = x.a(Maps.newHashMap(), var0 -> {
      var0.put(bfm.b, cyq.al);
      var0.put(bfm.c, cyq.am);
      var0.put(bfm.d, cyq.an);
      var0.put(bfm.e, cyq.ao);
      var0.put(bfm.f, cyq.ap);
      var0.put(bfm.g, cyq.aq);
      var0.put(bfm.h, cyq.ar);
      var0.put(bfm.i, cyq.as);
      var0.put(bfm.j, cyq.at);
      var0.put(bfm.k, cyq.au);
      var0.put(bfm.m, cyq.av);
      var0.put(bfm.n, cyq.aw);
      var0.put(bfm.o, cyq.ax);
   });
   private int c = 600;
   private boolean d;
   private long e;

   public asg(int var1) {
      super(ImmutableMap.of(ayd.m, aye.c, ayd.n, aye.c, ayd.q, aye.c, ayd.k, aye.a), _snowman);
   }

   protected boolean a(aag var1, bfj var2) {
      if (!this.b(_snowman)) {
         return false;
      } else if (this.c > 0) {
         this.c--;
         return false;
      } else {
         return true;
      }
   }

   protected void a(aag var1, bfj var2, long var3) {
      this.d = false;
      this.e = _snowman;
      bfw _snowman = this.c(_snowman).get();
      _snowman.cJ().a(ayd.q, _snowman);
      arw.a(_snowman, _snowman);
   }

   protected boolean b(aag var1, bfj var2, long var3) {
      return this.b(_snowman) && !this.d;
   }

   protected void c(aag var1, bfj var2, long var3) {
      bfw _snowman = this.c(_snowman).get();
      arw.a(_snowman, _snowman);
      if (this.a(_snowman, _snowman)) {
         if (_snowman - this.e > 20L) {
            this.a(_snowman, (aqm)_snowman);
            this.d = true;
         }
      } else {
         arw.a(_snowman, _snowman, 0.5F, 5);
      }
   }

   protected void d(aag var1, bfj var2, long var3) {
      this.c = a(_snowman);
      _snowman.cJ().b(ayd.q);
      _snowman.cJ().b(ayd.m);
      _snowman.cJ().b(ayd.n);
   }

   private void a(bfj var1, aqm var2) {
      for (bmb _snowman : this.a(_snowman)) {
         arw.a(_snowman, _snowman, _snowman.cA());
      }
   }

   private List<bmb> a(bfj var1) {
      if (_snowman.w_()) {
         return ImmutableList.of(new bmb(bmd.bi));
      } else {
         bfm _snowman = _snowman.eX().b();
         if (b.containsKey(_snowman)) {
            cyy _snowmanx = _snowman.l.l().aJ().a(b.get(_snowman));
            cyv.a _snowmanxx = new cyv.a((aag)_snowman.l).a(dbc.f, _snowman.cA()).a(dbc.a, _snowman).a(_snowman.cY());
            return _snowmanx.a(_snowmanxx.a(dbb.g));
         } else {
            return ImmutableList.of(new bmb(bmd.kV));
         }
      }
   }

   private boolean b(bfj var1) {
      return this.c(_snowman).isPresent();
   }

   private Optional<bfw> c(bfj var1) {
      return _snowman.cJ().c(ayd.k).filter(this::a);
   }

   private boolean a(bfw var1) {
      return _snowman.a(apw.F);
   }

   private boolean a(bfj var1, bfw var2) {
      fx _snowman = _snowman.cB();
      fx _snowmanx = _snowman.cB();
      return _snowmanx.a(_snowman, 5.0);
   }

   private static int a(aag var0) {
      return 600 + _snowman.t.nextInt(6001);
   }
}
