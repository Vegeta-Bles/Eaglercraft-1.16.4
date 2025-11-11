import com.mojang.datafixers.util.Pair;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import it.unimi.dsi.fastutil.objects.ObjectList;
import it.unimi.dsi.fastutil.objects.ObjectListIterator;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.IntSupplier;
import javax.annotation.Nullable;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class aaj extends cuo implements AutoCloseable {
   private static final Logger a = LogManager.getLogger();
   private final aoe<Runnable> b;
   private final ObjectList<Pair<aaj.a, Runnable>> c = new ObjectArrayList();
   private final zs d;
   private final aod<zu.a<Runnable>> e;
   private volatile int f = 5;
   private final AtomicBoolean g = new AtomicBoolean();

   public aaj(cgj var1, zs var2, boolean var3, aoe<Runnable> var4, aod<zu.a<Runnable>> var5) {
      super(_snowman, true, _snowman);
      this.d = _snowman;
      this.e = _snowman;
      this.b = _snowman;
   }

   @Override
   public void close() {
   }

   @Override
   public int a(int var1, boolean var2, boolean var3) {
      throw (UnsupportedOperationException)x.c(new UnsupportedOperationException("Ran authomatically on a different thread!"));
   }

   @Override
   public void a(fx var1, int var2) {
      throw (UnsupportedOperationException)x.c(new UnsupportedOperationException("Ran authomatically on a different thread!"));
   }

   @Override
   public void a(fx var1) {
      fx _snowman = _snowman.h();
      this.a(_snowman.u() >> 4, _snowman.w() >> 4, aaj.a.b, x.a(() -> super.a(_snowman), () -> "checkBlock " + _snowman));
   }

   protected void a(brd var1) {
      this.a(_snowman.b, _snowman.c, () -> 0, aaj.a.a, x.a(() -> {
         super.b(_snowman, false);
         super.a(_snowman, false);

         for (int _snowman = -1; _snowman < 17; _snowman++) {
            super.a(bsf.b, gp.a(_snowman, _snowman), null, true);
            super.a(bsf.a, gp.a(_snowman, _snowman), null, true);
         }

         for (int _snowman = 0; _snowman < 16; _snowman++) {
            super.a(gp.a(_snowman, _snowman), true);
         }
      }, () -> "updateChunkStatus " + _snowman + " " + true));
   }

   @Override
   public void a(gp var1, boolean var2) {
      this.a(_snowman.a(), _snowman.c(), () -> 0, aaj.a.a, x.a(() -> super.a(_snowman, _snowman), () -> "updateSectionStatus " + _snowman + " " + _snowman));
   }

   @Override
   public void a(brd var1, boolean var2) {
      this.a(_snowman.b, _snowman.c, aaj.a.a, x.a(() -> super.a(_snowman, _snowman), () -> "enableLight " + _snowman + " " + _snowman));
   }

   @Override
   public void a(bsf var1, gp var2, @Nullable cgb var3, boolean var4) {
      this.a(_snowman.a(), _snowman.c(), () -> 0, aaj.a.a, x.a(() -> super.a(_snowman, _snowman, _snowman, _snowman), () -> "queueData " + _snowman));
   }

   private void a(int var1, int var2, aaj.a var3, Runnable var4) {
      this.a(_snowman, _snowman, this.d.c(brd.a(_snowman, _snowman)), _snowman, _snowman);
   }

   private void a(int var1, int var2, IntSupplier var3, aaj.a var4, Runnable var5) {
      this.e.a(zu.a(() -> {
         this.c.add(Pair.of(_snowman, _snowman));
         if (this.c.size() >= this.f) {
            this.b();
         }
      }, brd.a(_snowman, _snowman), _snowman));
   }

   @Override
   public void b(brd var1, boolean var2) {
      this.a(_snowman.b, _snowman.c, () -> 0, aaj.a.a, x.a(() -> super.b(_snowman, _snowman), () -> "retainData " + _snowman));
   }

   public CompletableFuture<cfw> a(cfw var1, boolean var2) {
      brd _snowman = _snowman.g();
      _snowman.b(false);
      this.a(_snowman.b, _snowman.c, aaj.a.a, x.a(() -> {
         cgi[] _snowmanx = _snowman.d();

         for (int _snowmanx = 0; _snowmanx < 16; _snowmanx++) {
            cgi _snowmanxx = _snowmanx[_snowmanx];
            if (!cgi.a(_snowmanxx)) {
               super.a(gp.a(_snowman, _snowmanx), false);
            }
         }

         super.a(_snowman, true);
         if (!_snowman) {
            _snowman.m().forEach(var2x -> super.a(var2x, _snowman.g(var2x)));
         }

         this.d.c(_snowman);
      }, () -> "lightChunk " + _snowman + " " + _snowman));
      return CompletableFuture.supplyAsync(() -> {
         _snowman.b(true);
         super.b(_snowman, false);
         return _snowman;
      }, var2x -> this.a(_snowman.b, _snowman.c, aaj.a.b, var2x));
   }

   public void z_() {
      if ((!this.c.isEmpty() || super.a()) && this.g.compareAndSet(false, true)) {
         this.b.a(() -> {
            this.b();
            this.g.set(false);
         });
      }
   }

   private void b() {
      int _snowman = Math.min(this.c.size(), this.f);
      ObjectListIterator<Pair<aaj.a, Runnable>> _snowmanx = this.c.iterator();

      int _snowmanxx;
      for (_snowmanxx = 0; _snowmanx.hasNext() && _snowmanxx < _snowman; _snowmanxx++) {
         Pair<aaj.a, Runnable> _snowmanxxx = (Pair<aaj.a, Runnable>)_snowmanx.next();
         if (_snowmanxxx.getFirst() == aaj.a.a) {
            ((Runnable)_snowmanxxx.getSecond()).run();
         }
      }

      _snowmanx.back(_snowmanxx);
      super.a(Integer.MAX_VALUE, true, true);

      for (int var5 = 0; _snowmanx.hasNext() && var5 < _snowman; var5++) {
         Pair<aaj.a, Runnable> _snowmanxxx = (Pair<aaj.a, Runnable>)_snowmanx.next();
         if (_snowmanxxx.getFirst() == aaj.a.b) {
            ((Runnable)_snowmanxxx.getSecond()).run();
         }

         _snowmanx.remove();
      }
   }

   public void a(int var1) {
      this.f = _snowman;
   }

   static enum a {
      a,
      b;

      private a() {
      }
   }
}
