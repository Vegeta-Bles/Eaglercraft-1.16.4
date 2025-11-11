import com.google.common.collect.Iterators;
import com.mojang.serialization.Codec;
import it.unimi.dsi.fastutil.longs.Long2ObjectMap;
import it.unimi.dsi.fastutil.longs.Long2ObjectOpenHashMap;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
import java.util.Random;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.annotation.Nullable;

public enum gc implements afs {
   a(0, 1, -1, "down", gc.b.b, gc.a.b, new gr(0, -1, 0)),
   b(1, 0, -1, "up", gc.b.a, gc.a.b, new gr(0, 1, 0)),
   c(2, 3, 2, "north", gc.b.b, gc.a.c, new gr(0, 0, -1)),
   d(3, 2, 0, "south", gc.b.a, gc.a.c, new gr(0, 0, 1)),
   e(4, 5, 1, "west", gc.b.b, gc.a.a, new gr(-1, 0, 0)),
   f(5, 4, 3, "east", gc.b.a, gc.a.a, new gr(1, 0, 0));

   private final int g;
   private final int h;
   private final int i;
   private final String j;
   private final gc.a k;
   private final gc.b l;
   private final gr m;
   private static final gc[] n = values();
   private static final Map<String, gc> o = Arrays.stream(n).collect(Collectors.toMap(gc::m, var0 -> (gc)var0));
   private static final gc[] p = Arrays.stream(n).sorted(Comparator.comparingInt(var0 -> var0.g)).toArray(gc[]::new);
   private static final gc[] q = Arrays.stream(n).filter(var0 -> var0.n().d()).sorted(Comparator.comparingInt(var0 -> var0.i)).toArray(gc[]::new);
   private static final Long2ObjectMap<gc> r = Arrays.stream(n).collect(Collectors.toMap(var0 -> new fx(var0.p()).a(), var0 -> (gc)var0, (var0, var1) -> {
      throw new IllegalArgumentException("Duplicate keys");
   }, Long2ObjectOpenHashMap::new));

   private gc(int var3, int var4, int var5, String var6, gc.b var7, gc.a var8, gr var9) {
      this.g = _snowman;
      this.i = _snowman;
      this.h = _snowman;
      this.j = _snowman;
      this.k = _snowman;
      this.l = _snowman;
      this.m = _snowman;
   }

   public static gc[] a(aqa var0) {
      float _snowman = _snowman.g(1.0F) * (float) (Math.PI / 180.0);
      float _snowmanx = -_snowman.h(1.0F) * (float) (Math.PI / 180.0);
      float _snowmanxx = afm.a(_snowman);
      float _snowmanxxx = afm.b(_snowman);
      float _snowmanxxxx = afm.a(_snowmanx);
      float _snowmanxxxxx = afm.b(_snowmanx);
      boolean _snowmanxxxxxx = _snowmanxxxx > 0.0F;
      boolean _snowmanxxxxxxx = _snowmanxx < 0.0F;
      boolean _snowmanxxxxxxxx = _snowmanxxxxx > 0.0F;
      float _snowmanxxxxxxxxx = _snowmanxxxxxx ? _snowmanxxxx : -_snowmanxxxx;
      float _snowmanxxxxxxxxxx = _snowmanxxxxxxx ? -_snowmanxx : _snowmanxx;
      float _snowmanxxxxxxxxxxx = _snowmanxxxxxxxx ? _snowmanxxxxx : -_snowmanxxxxx;
      float _snowmanxxxxxxxxxxxx = _snowmanxxxxxxxxx * _snowmanxxx;
      float _snowmanxxxxxxxxxxxxx = _snowmanxxxxxxxxxxx * _snowmanxxx;
      gc _snowmanxxxxxxxxxxxxxx = _snowmanxxxxxx ? f : e;
      gc _snowmanxxxxxxxxxxxxxxx = _snowmanxxxxxxx ? b : a;
      gc _snowmanxxxxxxxxxxxxxxxx = _snowmanxxxxxxxx ? d : c;
      if (_snowmanxxxxxxxxx > _snowmanxxxxxxxxxxx) {
         if (_snowmanxxxxxxxxxx > _snowmanxxxxxxxxxxxx) {
            return a(_snowmanxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxxx);
         } else {
            return _snowmanxxxxxxxxxxxxx > _snowmanxxxxxxxxxx
               ? a(_snowmanxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxx)
               : a(_snowmanxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxxx);
         }
      } else if (_snowmanxxxxxxxxxx > _snowmanxxxxxxxxxxxxx) {
         return a(_snowmanxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxx);
      } else {
         return _snowmanxxxxxxxxxxxx > _snowmanxxxxxxxxxx ? a(_snowmanxxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxx) : a(_snowmanxxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxx);
      }
   }

   private static gc[] a(gc var0, gc var1, gc var2) {
      return new gc[]{_snowman, _snowman, _snowman, _snowman.f(), _snowman.f(), _snowman.f()};
   }

   public static gc a(b var0, gc var1) {
      gr _snowman = _snowman.p();
      h _snowmanx = new h((float)_snowman.u(), (float)_snowman.v(), (float)_snowman.w(), 0.0F);
      _snowmanx.a(_snowman);
      return a(_snowmanx.a(), _snowmanx.b(), _snowmanx.c());
   }

   public d b() {
      d _snowman = g.b.a(90.0F);
      switch (this) {
         case a:
            return g.b.a(180.0F);
         case b:
            return d.a.g();
         case c:
            _snowman.a(g.f.a(180.0F));
            return _snowman;
         case d:
            return _snowman;
         case e:
            _snowman.a(g.f.a(90.0F));
            return _snowman;
         case f:
         default:
            _snowman.a(g.f.a(-90.0F));
            return _snowman;
      }
   }

   public int c() {
      return this.g;
   }

   public int d() {
      return this.i;
   }

   public gc.b e() {
      return this.l;
   }

   public gc f() {
      return a(this.h);
   }

   public gc g() {
      switch (this) {
         case c:
            return f;
         case d:
            return e;
         case e:
            return c;
         case f:
            return d;
         default:
            throw new IllegalStateException("Unable to get Y-rotated facing of " + this);
      }
   }

   public gc h() {
      switch (this) {
         case c:
            return e;
         case d:
            return f;
         case e:
            return d;
         case f:
            return c;
         default:
            throw new IllegalStateException("Unable to get CCW facing of " + this);
      }
   }

   public int i() {
      return this.m.u();
   }

   public int j() {
      return this.m.v();
   }

   public int k() {
      return this.m.w();
   }

   public g l() {
      return new g((float)this.i(), (float)this.j(), (float)this.k());
   }

   public String m() {
      return this.j;
   }

   public gc.a n() {
      return this.k;
   }

   @Nullable
   public static gc a(@Nullable String var0) {
      return _snowman == null ? null : o.get(_snowman.toLowerCase(Locale.ROOT));
   }

   public static gc a(int var0) {
      return p[afm.a(_snowman % p.length)];
   }

   public static gc b(int var0) {
      return q[afm.a(_snowman % q.length)];
   }

   @Nullable
   public static gc a(int var0, int var1, int var2) {
      return (gc)r.get(fx.a(_snowman, _snowman, _snowman));
   }

   public static gc a(double var0) {
      return b(afm.c(_snowman / 90.0 + 0.5) & 3);
   }

   public static gc a(gc.a var0, gc.b var1) {
      switch (_snowman) {
         case a:
            return _snowman == gc.b.a ? f : e;
         case b:
            return _snowman == gc.b.a ? b : a;
         case c:
         default:
            return _snowman == gc.b.a ? d : c;
      }
   }

   public float o() {
      return (float)((this.i & 3) * 90);
   }

   public static gc a(Random var0) {
      return x.a(n, _snowman);
   }

   public static gc a(double var0, double var2, double var4) {
      return a((float)_snowman, (float)_snowman, (float)_snowman);
   }

   public static gc a(float var0, float var1, float var2) {
      gc _snowman = c;
      float _snowmanx = Float.MIN_VALUE;

      for (gc _snowmanxx : n) {
         float _snowmanxxx = _snowman * (float)_snowmanxx.m.u() + _snowman * (float)_snowmanxx.m.v() + _snowman * (float)_snowmanxx.m.w();
         if (_snowmanxxx > _snowmanx) {
            _snowmanx = _snowmanxxx;
            _snowman = _snowmanxx;
         }
      }

      return _snowman;
   }

   @Override
   public String toString() {
      return this.j;
   }

   @Override
   public String a() {
      return this.j;
   }

   public static gc a(gc.b var0, gc.a var1) {
      for (gc _snowman : n) {
         if (_snowman.e() == _snowman && _snowman.n() == _snowman) {
            return _snowman;
         }
      }

      throw new IllegalArgumentException("No such direction: " + _snowman + " " + _snowman);
   }

   public gr p() {
      return this.m;
   }

   public boolean a(float var1) {
      float _snowman = _snowman * (float) (Math.PI / 180.0);
      float _snowmanx = -afm.a(_snowman);
      float _snowmanxx = afm.b(_snowman);
      return (float)this.m.u() * _snowmanx + (float)this.m.w() * _snowmanxx > 0.0F;
   }

   public static enum a implements afs, Predicate<gc> {
      a("x") {
         @Override
         public int a(int var1, int var2, int var3) {
            return _snowman;
         }

         @Override
         public double a(double var1, double var3, double var5) {
            return _snowman;
         }
      },
      b("y") {
         @Override
         public int a(int var1, int var2, int var3) {
            return _snowman;
         }

         @Override
         public double a(double var1, double var3, double var5) {
            return _snowman;
         }
      },
      c("z") {
         @Override
         public int a(int var1, int var2, int var3) {
            return _snowman;
         }

         @Override
         public double a(double var1, double var3, double var5) {
            return _snowman;
         }
      };

      private static final gc.a[] e = values();
      public static final Codec<gc.a> d = afs.a(gc.a::values, gc.a::a);
      private static final Map<String, gc.a> f = Arrays.stream(e).collect(Collectors.toMap(gc.a::b, var0 -> (gc.a)var0));
      private final String g;

      private a(String var3) {
         this.g = _snowman;
      }

      @Nullable
      public static gc.a a(String var0) {
         return f.get(_snowman.toLowerCase(Locale.ROOT));
      }

      public String b() {
         return this.g;
      }

      public boolean c() {
         return this == b;
      }

      public boolean d() {
         return this == a || this == c;
      }

      @Override
      public String toString() {
         return this.g;
      }

      public static gc.a a(Random var0) {
         return x.a(e, _snowman);
      }

      public boolean a(@Nullable gc var1) {
         return _snowman != null && _snowman.n() == this;
      }

      public gc.c e() {
         switch (this) {
            case a:
            case c:
               return gc.c.a;
            case b:
               return gc.c.b;
            default:
               throw new Error("Someone's been tampering with the universe!");
         }
      }

      @Override
      public String a() {
         return this.g;
      }

      public abstract int a(int var1, int var2, int var3);

      public abstract double a(double var1, double var3, double var5);
   }

   public static enum b {
      a(1, "Towards positive"),
      b(-1, "Towards negative");

      private final int c;
      private final String d;

      private b(int var3, String var4) {
         this.c = _snowman;
         this.d = _snowman;
      }

      public int a() {
         return this.c;
      }

      @Override
      public String toString() {
         return this.d;
      }

      public gc.b c() {
         return this == a ? b : a;
      }
   }

   public static enum c implements Iterable<gc>, Predicate<gc> {
      a(new gc[]{gc.c, gc.f, gc.d, gc.e}, new gc.a[]{gc.a.a, gc.a.c}),
      b(new gc[]{gc.b, gc.a}, new gc.a[]{gc.a.b});

      private final gc[] c;
      private final gc.a[] d;

      private c(gc[] var3, gc.a[] var4) {
         this.c = _snowman;
         this.d = _snowman;
      }

      public gc a(Random var1) {
         return x.a(this.c, _snowman);
      }

      public boolean a(@Nullable gc var1) {
         return _snowman != null && _snowman.n().e() == this;
      }

      @Override
      public Iterator<gc> iterator() {
         return Iterators.forArray(this.c);
      }

      public Stream<gc> a() {
         return Arrays.stream(this.c);
      }
   }
}
