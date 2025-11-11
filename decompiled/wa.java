import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import javax.annotation.Nullable;
import net.minecraft.server.MinecraftServer;

public class wa extends ddn {
   private final MinecraftServer a;
   private final Set<ddk> b = Sets.newHashSet();
   private Runnable[] c = new Runnable[0];

   public wa(MinecraftServer var1) {
      this.a = _snowman;
   }

   @Override
   public void a(ddm var1) {
      super.a(_snowman);
      if (this.b.contains(_snowman.d())) {
         this.a.ae().a(new rj(wa.a.a, _snowman.d().b(), _snowman.e(), _snowman.b()));
      }

      this.b();
   }

   @Override
   public void a(String var1) {
      super.a(_snowman);
      this.a.ae().a(new rj(wa.a.b, null, _snowman, 0));
      this.b();
   }

   @Override
   public void a(String var1, ddk var2) {
      super.a(_snowman, _snowman);
      if (this.b.contains(_snowman)) {
         this.a.ae().a(new rj(wa.a.b, _snowman.b(), _snowman, 0));
      }

      this.b();
   }

   @Override
   public void a(int var1, @Nullable ddk var2) {
      ddk _snowman = this.a(_snowman);
      super.a(_snowman, _snowman);
      if (_snowman != _snowman && _snowman != null) {
         if (this.h(_snowman) > 0) {
            this.a.ae().a(new qz(_snowman, _snowman));
         } else {
            this.g(_snowman);
         }
      }

      if (_snowman != null) {
         if (this.b.contains(_snowman)) {
            this.a.ae().a(new qz(_snowman, _snowman));
         } else {
            this.e(_snowman);
         }
      }

      this.b();
   }

   @Override
   public boolean a(String var1, ddl var2) {
      if (super.a(_snowman, _snowman)) {
         this.a.ae().a(new ri(_snowman, Arrays.asList(_snowman), 3));
         this.b();
         return true;
      } else {
         return false;
      }
   }

   @Override
   public void b(String var1, ddl var2) {
      super.b(_snowman, _snowman);
      this.a.ae().a(new ri(_snowman, Arrays.asList(_snowman), 4));
      this.b();
   }

   @Override
   public void a(ddk var1) {
      super.a(_snowman);
      this.b();
   }

   @Override
   public void b(ddk var1) {
      super.b(_snowman);
      if (this.b.contains(_snowman)) {
         this.a.ae().a(new rg(_snowman, 2));
      }

      this.b();
   }

   @Override
   public void c(ddk var1) {
      super.c(_snowman);
      if (this.b.contains(_snowman)) {
         this.g(_snowman);
      }

      this.b();
   }

   @Override
   public void a(ddl var1) {
      super.a(_snowman);
      this.a.ae().a(new ri(_snowman, 0));
      this.b();
   }

   @Override
   public void b(ddl var1) {
      super.b(_snowman);
      this.a.ae().a(new ri(_snowman, 2));
      this.b();
   }

   @Override
   public void c(ddl var1) {
      super.c(_snowman);
      this.a.ae().a(new ri(_snowman, 1));
      this.b();
   }

   public void a(Runnable var1) {
      this.c = Arrays.copyOf(this.c, this.c.length + 1);
      this.c[this.c.length - 1] = _snowman;
   }

   protected void b() {
      for (Runnable _snowman : this.c) {
         _snowman.run();
      }
   }

   public List<oj<?>> d(ddk var1) {
      List<oj<?>> _snowman = Lists.newArrayList();
      _snowman.add(new rg(_snowman, 0));

      for (int _snowmanx = 0; _snowmanx < 19; _snowmanx++) {
         if (this.a(_snowmanx) == _snowman) {
            _snowman.add(new qz(_snowmanx, _snowman));
         }
      }

      for (ddm _snowmanxx : this.i(_snowman)) {
         _snowman.add(new rj(wa.a.a, _snowmanxx.d().b(), _snowmanxx.e(), _snowmanxx.b()));
      }

      return _snowman;
   }

   public void e(ddk var1) {
      List<oj<?>> _snowman = this.d(_snowman);

      for (aah _snowmanx : this.a.ae().s()) {
         for (oj<?> _snowmanxx : _snowman) {
            _snowmanx.b.a(_snowmanxx);
         }
      }

      this.b.add(_snowman);
   }

   public List<oj<?>> f(ddk var1) {
      List<oj<?>> _snowman = Lists.newArrayList();
      _snowman.add(new rg(_snowman, 1));

      for (int _snowmanx = 0; _snowmanx < 19; _snowmanx++) {
         if (this.a(_snowmanx) == _snowman) {
            _snowman.add(new qz(_snowmanx, _snowman));
         }
      }

      return _snowman;
   }

   public void g(ddk var1) {
      List<oj<?>> _snowman = this.f(_snowman);

      for (aah _snowmanx : this.a.ae().s()) {
         for (oj<?> _snowmanxx : _snowman) {
            _snowmanx.b.a(_snowmanxx);
         }
      }

      this.b.remove(_snowman);
   }

   public int h(ddk var1) {
      int _snowman = 0;

      for (int _snowmanx = 0; _snowmanx < 19; _snowmanx++) {
         if (this.a(_snowmanx) == _snowman) {
            _snowman++;
         }
      }

      return _snowman;
   }

   public static enum a {
      a,
      b;

      private a() {
      }
   }
}
