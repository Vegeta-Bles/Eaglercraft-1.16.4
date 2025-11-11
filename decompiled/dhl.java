import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class dhl {
   private static final Logger a = LogManager.getLogger();
   private final ScheduledExecutorService b = Executors.newScheduledThreadPool(3);
   private volatile boolean c = true;
   private final Runnable d = new dhl.c();
   private final Runnable e = new dhl.b();
   private final Runnable f = new dhl.e();
   private final Runnable g = new dhl.a();
   private final Runnable h = new dhl.f();
   private final Set<dgq> i = Sets.newHashSet();
   private List<dgq> j = Lists.newArrayList();
   private dgv k;
   private int l;
   private boolean m;
   private boolean n;
   private String o;
   private ScheduledFuture<?> p;
   private ScheduledFuture<?> q;
   private ScheduledFuture<?> r;
   private ScheduledFuture<?> s;
   private ScheduledFuture<?> t;
   private final Map<dhl.d, Boolean> u = new ConcurrentHashMap<>(dhl.d.values().length);

   public dhl() {
   }

   public boolean a() {
      return this.c;
   }

   public synchronized void b() {
      if (this.c) {
         this.c = false;
         this.o();
         this.n();
      }
   }

   public synchronized void c() {
      if (this.c) {
         this.c = false;
         this.o();
         this.u.put(dhl.d.b, false);
         this.q = this.b.scheduleAtFixedRate(this.e, 0L, 10L, TimeUnit.SECONDS);
         this.u.put(dhl.d.c, false);
         this.r = this.b.scheduleAtFixedRate(this.f, 0L, 60L, TimeUnit.SECONDS);
         this.u.put(dhl.d.e, false);
         this.t = this.b.scheduleAtFixedRate(this.h, 0L, 300L, TimeUnit.SECONDS);
      }
   }

   public boolean a(dhl.d var1) {
      Boolean _snowman = this.u.get(_snowman);
      return _snowman == null ? false : _snowman;
   }

   public void d() {
      for (dhl.d _snowman : this.u.keySet()) {
         this.u.put(_snowman, false);
      }
   }

   public synchronized void e() {
      this.l();
      this.b();
   }

   public synchronized List<dgq> f() {
      return Lists.newArrayList(this.j);
   }

   public synchronized int g() {
      return this.l;
   }

   public synchronized boolean h() {
      return this.m;
   }

   public synchronized dgv i() {
      return this.k;
   }

   public synchronized boolean j() {
      return this.n;
   }

   public synchronized String k() {
      return this.o;
   }

   public synchronized void l() {
      this.c = true;
      this.o();
   }

   private void n() {
      for (dhl.d _snowman : dhl.d.values()) {
         this.u.put(_snowman, false);
      }

      this.p = this.b.scheduleAtFixedRate(this.d, 0L, 60L, TimeUnit.SECONDS);
      this.q = this.b.scheduleAtFixedRate(this.e, 0L, 10L, TimeUnit.SECONDS);
      this.r = this.b.scheduleAtFixedRate(this.f, 0L, 60L, TimeUnit.SECONDS);
      this.s = this.b.scheduleAtFixedRate(this.g, 0L, 10L, TimeUnit.SECONDS);
      this.t = this.b.scheduleAtFixedRate(this.h, 0L, 300L, TimeUnit.SECONDS);
   }

   private void o() {
      try {
         if (this.p != null) {
            this.p.cancel(false);
         }

         if (this.q != null) {
            this.q.cancel(false);
         }

         if (this.r != null) {
            this.r.cancel(false);
         }

         if (this.s != null) {
            this.s.cancel(false);
         }

         if (this.t != null) {
            this.t.cancel(false);
         }
      } catch (Exception var2) {
         a.error("Failed to cancel Realms tasks", var2);
      }
   }

   private synchronized void a(List<dgq> var1) {
      int _snowman = 0;

      for (dgq _snowmanx : this.i) {
         if (_snowman.remove(_snowmanx)) {
            _snowman++;
         }
      }

      if (_snowman == 0) {
         this.i.clear();
      }

      this.j = _snowman;
   }

   public synchronized void a(dgq var1) {
      this.j.remove(_snowman);
      this.i.add(_snowman);
   }

   private boolean p() {
      return !this.c;
   }

   class a implements Runnable {
      private a() {
      }

      @Override
      public void run() {
         if (dhl.this.p()) {
            this.a();
         }
      }

      private void a() {
         try {
            dgb _snowman = dgb.a();
            dhl.this.k = _snowman.f();
            dhl.this.u.put(dhl.d.d, true);
         } catch (Exception var2) {
            dhl.a.error("Couldn't get live stats", var2);
         }
      }
   }

   class b implements Runnable {
      private b() {
      }

      @Override
      public void run() {
         if (dhl.this.p()) {
            this.a();
         }
      }

      private void a() {
         try {
            dgb _snowman = dgb.a();
            dhl.this.l = _snowman.j();
            dhl.this.u.put(dhl.d.b, true);
         } catch (Exception var2) {
            dhl.a.error("Couldn't get pending invite count", var2);
         }
      }
   }

   class c implements Runnable {
      private c() {
      }

      @Override
      public void run() {
         if (dhl.this.p()) {
            this.a();
         }
      }

      private void a() {
         try {
            dgb _snowman = dgb.a();
            List<dgq> _snowmanx = _snowman.e().a;
            if (_snowmanx != null) {
               _snowmanx.sort(new dgq.a(djz.C().J().c()));
               dhl.this.a(_snowmanx);
               dhl.this.u.put(dhl.d.a, true);
            } else {
               dhl.a.warn("Realms server list was null or empty");
            }
         } catch (Exception var3) {
            dhl.this.u.put(dhl.d.a, true);
            dhl.a.error("Couldn't get server list", var3);
         }
      }
   }

   public static enum d {
      a,
      b,
      c,
      d,
      e;

      private d() {
      }
   }

   class e implements Runnable {
      private e() {
      }

      @Override
      public void run() {
         if (dhl.this.p()) {
            this.a();
         }
      }

      private void a() {
         try {
            dgb _snowman = dgb.a();
            dhl.this.m = _snowman.n();
            dhl.this.u.put(dhl.d.c, true);
         } catch (Exception var2) {
            dhl.a.error("Couldn't get trial availability", var2);
         }
      }
   }

   class f implements Runnable {
      private f() {
      }

      @Override
      public void run() {
         if (dhl.this.p()) {
            this.a();
         }
      }

      private void a() {
         try {
            dgb _snowman = dgb.a();
            dgp _snowmanx = null;

            try {
               _snowmanx = _snowman.m();
            } catch (Exception var5) {
            }

            diq.a _snowmanxx = diq.a();
            if (_snowmanx != null) {
               String _snowmanxxx = _snowmanx.a;
               if (_snowmanxxx != null && !_snowmanxxx.equals(_snowmanxx.a)) {
                  _snowmanxx.b = true;
                  _snowmanxx.a = _snowmanxxx;
                  diq.a(_snowmanxx);
               }
            }

            dhl.this.n = _snowmanxx.b;
            dhl.this.o = _snowmanxx.a;
            dhl.this.u.put(dhl.d.e, true);
         } catch (Exception var6) {
            dhl.a.error("Couldn't get unread news", var6);
         }
      }
   }
}
