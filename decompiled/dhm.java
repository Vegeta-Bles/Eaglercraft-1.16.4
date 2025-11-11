import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.datafixers.util.Pair;
import java.util.function.Consumer;
import java.util.function.Supplier;
import javax.annotation.Nullable;

public class dhm extends dlj implements dmc {
   public static final vk a = new vk("realms", "textures/gui/realms/slot_frame.png");
   public static final vk b = new vk("realms", "textures/gui/realms/empty_frame.png");
   public static final vk c = new vk("minecraft", "textures/gui/title/background/panorama_0.png");
   public static final vk d = new vk("minecraft", "textures/gui/title/background/panorama_2.png");
   public static final vk e = new vk("minecraft", "textures/gui/title/background/panorama_3.png");
   private static final nr v = new of("mco.configure.world.slot.tooltip.active");
   private static final nr w = new of("mco.configure.world.slot.tooltip.minigame");
   private static final nr x = new of("mco.configure.world.slot.tooltip");
   private final Supplier<dgq> y;
   private final Consumer<nr> z;
   private final int A;
   private int B;
   @Nullable
   private dhm.b C;

   public dhm(int var1, int var2, int var3, int var4, Supplier<dgq> var5, Consumer<nr> var6, int var7, dlj.a var8) {
      super(_snowman, _snowman, _snowman, _snowman, oe.d, _snowman);
      this.y = _snowman;
      this.A = _snowman;
      this.z = _snowman;
   }

   @Nullable
   public dhm.b a() {
      return this.C;
   }

   @Override
   public void d() {
      this.B++;
      dgq _snowman = this.y.get();
      if (_snowman != null) {
         dgw _snowmanx = _snowman.i.get(this.A);
         boolean _snowmanxx = this.A == 4;
         boolean _snowmanxxx;
         String _snowmanxxxx;
         long _snowmanxxxxx;
         String _snowmanxxxxxx;
         boolean _snowmanxxxxxxx;
         if (_snowmanxx) {
            _snowmanxxx = _snowman.m == dgq.c.b;
            _snowmanxxxx = "Minigame";
            _snowmanxxxxx = (long)_snowman.p;
            _snowmanxxxxxx = _snowman.q;
            _snowmanxxxxxxx = _snowman.p == -1;
         } else {
            _snowmanxxx = _snowman.n == this.A && _snowman.m != dgq.c.b;
            _snowmanxxxx = _snowmanx.a(this.A);
            _snowmanxxxxx = _snowmanx.k;
            _snowmanxxxxxx = _snowmanx.l;
            _snowmanxxxxxxx = _snowmanx.n;
         }

         dhm.a _snowmanxxxxxxxx = a(_snowman, _snowmanxxx, _snowmanxx);
         Pair<nr, nr> _snowmanxxxxxxxxx = this.a(_snowman, _snowmanxxxx, _snowmanxxxxxxx, _snowmanxx, _snowmanxxxxxxxx);
         this.C = new dhm.b(_snowmanxxx, _snowmanxxxx, _snowmanxxxxx, _snowmanxxxxxx, _snowmanxxxxxxx, _snowmanxx, _snowmanxxxxxxxx, (nr)_snowmanxxxxxxxxx.getFirst());
         this.a((nr)_snowmanxxxxxxxxx.getSecond());
      }
   }

   private static dhm.a a(dgq var0, boolean var1, boolean var2) {
      if (_snowman) {
         if (!_snowman.j && _snowman.e != dgq.b.c) {
            return dhm.a.c;
         }
      } else {
         if (!_snowman) {
            return dhm.a.b;
         }

         if (!_snowman.j) {
            return dhm.a.b;
         }
      }

      return dhm.a.a;
   }

   private Pair<nr, nr> a(dgq var1, String var2, boolean var3, boolean var4, dhm.a var5) {
      if (_snowman == dhm.a.a) {
         return Pair.of(null, new oe(_snowman));
      } else {
         nr _snowman;
         if (_snowman) {
            if (_snowman) {
               _snowman = oe.d;
            } else {
               _snowman = new oe(" ").c(_snowman).c(" ").c(_snowman.o);
            }
         } else {
            _snowman = new oe(" ").c(_snowman);
         }

         nr _snowmanx;
         if (_snowman == dhm.a.c) {
            _snowmanx = v;
         } else {
            _snowmanx = _snowman ? w : x;
         }

         nr _snowmanxx = _snowmanx.e().a(_snowman);
         return Pair.of(_snowmanx, _snowmanxx);
      }
   }

   @Override
   public void b(dfm var1, int var2, int var3, float var4) {
      if (this.C != null) {
         this.a(_snowman, this.l, this.m, _snowman, _snowman, this.C.d, this.C.e, this.A, this.C.f, this.C.g, this.C.a, this.C.b, this.C.c, this.C.h);
      }
   }

   private void a(
      dfm var1,
      int var2,
      int var3,
      int var4,
      int var5,
      boolean var6,
      String var7,
      int var8,
      long var9,
      @Nullable String var11,
      boolean var12,
      boolean var13,
      dhm.a var14,
      @Nullable nr var15
   ) {
      boolean _snowman = this.g();
      if (this.b((double)_snowman, (double)_snowman) && _snowman != null) {
         this.z.accept(_snowman);
      }

      djz _snowmanx = djz.C();
      ekd _snowmanxx = _snowmanx.M();
      if (_snowman) {
         dir.a(String.valueOf(_snowman), _snowman);
      } else if (_snowman) {
         _snowmanxx.a(b);
      } else if (_snowman != null && _snowman != -1L) {
         dir.a(String.valueOf(_snowman), _snowman);
      } else if (_snowman == 1) {
         _snowmanxx.a(c);
      } else if (_snowman == 2) {
         _snowmanxx.a(d);
      } else if (_snowman == 3) {
         _snowmanxx.a(e);
      }

      if (_snowman) {
         float _snowmanxxx = 0.85F + 0.15F * afm.b((float)this.B * 0.2F);
         RenderSystem.color4f(_snowmanxxx, _snowmanxxx, _snowmanxxx, 1.0F);
      } else {
         RenderSystem.color4f(0.56F, 0.56F, 0.56F, 1.0F);
      }

      a(_snowman, _snowman + 3, _snowman + 3, 0.0F, 0.0F, 74, 74, 74, 74);
      _snowmanxx.a(a);
      boolean _snowmanxxx = _snowman && _snowman != dhm.a.a;
      if (_snowmanxxx) {
         RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
      } else if (_snowman) {
         RenderSystem.color4f(0.8F, 0.8F, 0.8F, 1.0F);
      } else {
         RenderSystem.color4f(0.56F, 0.56F, 0.56F, 1.0F);
      }

      a(_snowman, _snowman, _snowman, 0.0F, 0.0F, 80, 80, 80, 80);
      a(_snowman, _snowmanx.g, _snowman, _snowman + 40, _snowman + 66, 16777215);
   }

   public static enum a {
      a,
      b,
      c;

      private a() {
      }
   }

   public static class b {
      private final boolean d;
      private final String e;
      private final long f;
      private final String g;
      public final boolean a;
      public final boolean b;
      public final dhm.a c;
      @Nullable
      private final nr h;

      b(boolean var1, String var2, long var3, @Nullable String var5, boolean var6, boolean var7, dhm.a var8, @Nullable nr var9) {
         this.d = _snowman;
         this.e = _snowman;
         this.f = _snowman;
         this.g = _snowman;
         this.a = _snowman;
         this.b = _snowman;
         this.c = _snowman;
         this.h = _snowman;
      }
   }
}
