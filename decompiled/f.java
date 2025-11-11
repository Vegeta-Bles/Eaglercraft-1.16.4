import com.mojang.datafixers.util.Pair;
import java.util.Objects;
import java.util.function.Supplier;
import javax.annotation.Nullable;
import org.apache.commons.lang3.tuple.Triple;

public final class f {
   private final b a;
   private boolean b;
   @Nullable
   private g c;
   @Nullable
   private d d;
   @Nullable
   private g e;
   @Nullable
   private d f;
   private static final f g = x.a((Supplier<f>)(() -> {
      b _snowman = new b();
      _snowman.a();
      f _snowmanx = new f(_snowman);
      _snowmanx.d();
      return _snowmanx;
   }));

   public f(@Nullable b var1) {
      if (_snowman == null) {
         this.a = g.a;
      } else {
         this.a = _snowman;
      }
   }

   public f(@Nullable g var1, @Nullable d var2, @Nullable g var3, @Nullable d var4) {
      this.a = a(_snowman, _snowman, _snowman, _snowman);
      this.c = _snowman != null ? _snowman : new g();
      this.d = _snowman != null ? _snowman : d.a.g();
      this.e = _snowman != null ? _snowman : new g(1.0F, 1.0F, 1.0F);
      this.f = _snowman != null ? _snowman : d.a.g();
      this.b = true;
   }

   public static f a() {
      return g;
   }

   public f a(f var1) {
      b _snowman = this.c();
      _snowman.a(_snowman.c());
      return new f(_snowman);
   }

   @Nullable
   public f b() {
      if (this == g) {
         return this;
      } else {
         b _snowman = this.c();
         return _snowman.c() ? new f(_snowman) : null;
      }
   }

   private void e() {
      if (!this.b) {
         Pair<a, g> _snowman = a(this.a);
         Triple<d, g, d> _snowmanx = ((a)_snowman.getFirst()).b();
         this.c = (g)_snowman.getSecond();
         this.d = (d)_snowmanx.getLeft();
         this.e = (g)_snowmanx.getMiddle();
         this.f = (d)_snowmanx.getRight();
         this.b = true;
      }
   }

   private static b a(@Nullable g var0, @Nullable d var1, @Nullable g var2, @Nullable d var3) {
      b _snowman = new b();
      _snowman.a();
      if (_snowman != null) {
         _snowman.a(new b(_snowman));
      }

      if (_snowman != null) {
         _snowman.a(b.a(_snowman.a(), _snowman.b(), _snowman.c()));
      }

      if (_snowman != null) {
         _snowman.a(new b(_snowman));
      }

      if (_snowman != null) {
         _snowman.d = _snowman.a();
         _snowman.h = _snowman.b();
         _snowman.l = _snowman.c();
      }

      return _snowman;
   }

   public static Pair<a, g> a(b var0) {
      _snowman.a(1.0F / _snowman.p);
      g _snowman = new g(_snowman.d, _snowman.h, _snowman.l);
      a _snowmanx = new a(_snowman);
      return Pair.of(_snowmanx, _snowman);
   }

   public b c() {
      return this.a.d();
   }

   public d d() {
      this.e();
      return this.d.g();
   }

   @Override
   public boolean equals(Object var1) {
      if (this == _snowman) {
         return true;
      } else if (_snowman != null && this.getClass() == _snowman.getClass()) {
         f _snowman = (f)_snowman;
         return Objects.equals(this.a, _snowman.a);
      } else {
         return false;
      }
   }

   @Override
   public int hashCode() {
      return Objects.hash(this.a);
   }
}
