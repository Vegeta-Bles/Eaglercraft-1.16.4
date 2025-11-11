import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.mojang.blaze3d.systems.RenderSystem;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import javax.annotation.Nullable;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class edc implements edh.a {
   private static final Logger a = LogManager.getLogger();
   private final djz b;
   private final Map<fx, edc.b> c = Maps.newHashMap();
   private final Map<UUID, edc.a> d = Maps.newHashMap();
   @Nullable
   private UUID e;

   public edc(djz var1) {
      this.b = _snowman;
   }

   @Override
   public void a() {
      this.c.clear();
      this.d.clear();
      this.e = null;
   }

   public void a(edc.b var1) {
      this.c.put(_snowman.a, _snowman);
   }

   public void a(fx var1) {
      this.c.remove(_snowman);
   }

   public void a(fx var1, int var2) {
      edc.b _snowman = this.c.get(_snowman);
      if (_snowman == null) {
         a.warn("Strange, setFreeTicketCount was called for an unknown POI: " + _snowman);
      } else {
         _snowman.c = _snowman;
      }
   }

   public void a(edc.a var1) {
      this.d.put(_snowman.a, _snowman);
   }

   @Override
   public void a(dfm var1, eag var2, double var3, double var5, double var7) {
      RenderSystem.pushMatrix();
      RenderSystem.enableBlend();
      RenderSystem.defaultBlendFunc();
      RenderSystem.disableTexture();
      this.b();
      this.a(_snowman, _snowman, _snowman);
      RenderSystem.enableTexture();
      RenderSystem.disableBlend();
      RenderSystem.popMatrix();
      if (!this.b.s.a_()) {
         this.d();
      }
   }

   private void b() {
      this.d.entrySet().removeIf(var1 -> {
         aqa _snowman = this.b.r.a(var1.getValue().b);
         return _snowman == null || _snowman.y;
      });
   }

   private void a(double var1, double var3, double var5) {
      fx _snowman = new fx(_snowman, _snowman, _snowman);
      this.d.values().forEach(var7x -> {
         if (this.c(var7x)) {
            this.b(var7x, _snowman, _snowman, _snowman);
         }
      });

      for (fx _snowmanx : this.c.keySet()) {
         if (_snowman.a(_snowmanx, 30.0)) {
            b(_snowmanx);
         }
      }

      this.c.values().forEach(var2 -> {
         if (_snowman.a(var2.a, 30.0)) {
            this.b(var2);
         }
      });
      this.c().forEach((var2, var3x) -> {
         if (_snowman.a(var2, 30.0)) {
            this.a(var2, (List<String>)var3x);
         }
      });
   }

   private static void b(fx var0) {
      float _snowman = 0.05F;
      RenderSystem.enableBlend();
      RenderSystem.defaultBlendFunc();
      edh.a(_snowman, 0.05F, 0.2F, 0.2F, 1.0F, 0.3F);
   }

   private void a(fx var1, List<String> var2) {
      float _snowman = 0.05F;
      RenderSystem.enableBlend();
      RenderSystem.defaultBlendFunc();
      edh.a(_snowman, 0.05F, 0.2F, 0.2F, 1.0F, 0.3F);
      a("" + _snowman, _snowman, 0, -256);
      a("Ghost POI", _snowman, 1, -65536);
   }

   private void b(edc.b var1) {
      int _snowman = 0;
      Set<String> _snowmanx = this.c(_snowman);
      if (_snowmanx.size() < 4) {
         a("Owners: " + _snowmanx, _snowman, _snowman, -256);
      } else {
         a("" + _snowmanx.size() + " ticket holders", _snowman, _snowman, -256);
      }

      _snowman++;
      Set<String> _snowmanxx = this.d(_snowman);
      if (_snowmanxx.size() < 4) {
         a("Candidates: " + _snowmanxx, _snowman, _snowman, -23296);
      } else {
         a("" + _snowmanxx.size() + " potential owners", _snowman, _snowman, -23296);
      }

      a("Free tickets: " + _snowman.c, _snowman, ++_snowman, -256);
      a(_snowman.b, _snowman, ++_snowman, -1);
   }

   private void a(edc.a var1, double var2, double var4, double var6) {
      if (_snowman.j != null) {
         edn.a(_snowman.j, 0.5F, false, false, _snowman, _snowman, _snowman);
      }
   }

   private void b(edc.a var1, double var2, double var4, double var6) {
      boolean _snowman = this.b(_snowman);
      int _snowmanx = 0;
      a(_snowman.h, _snowmanx, _snowman.c, -1, 0.03F);
      _snowmanx++;
      if (_snowman) {
         a(_snowman.h, _snowmanx, _snowman.d + " " + _snowman.e + " xp", -1, 0.02F);
         _snowmanx++;
      }

      if (_snowman) {
         int _snowmanxx = _snowman.f < _snowman.g ? -23296 : -1;
         a(_snowman.h, _snowmanx, "health: " + String.format("%.1f", _snowman.f) + " / " + String.format("%.1f", _snowman.g), _snowmanxx, 0.02F);
         _snowmanx++;
      }

      if (_snowman && !_snowman.i.equals("")) {
         a(_snowman.h, _snowmanx, _snowman.i, -98404, 0.02F);
         _snowmanx++;
      }

      if (_snowman) {
         for (String _snowmanxx : _snowman.m) {
            a(_snowman.h, _snowmanx, _snowmanxx, -16711681, 0.02F);
            _snowmanx++;
         }
      }

      if (_snowman) {
         for (String _snowmanxx : _snowman.l) {
            a(_snowman.h, _snowmanx, _snowmanxx, -16711936, 0.02F);
            _snowmanx++;
         }
      }

      if (_snowman.k) {
         a(_snowman.h, _snowmanx, "Wants Golem", -23296, 0.02F);
         _snowmanx++;
      }

      if (_snowman) {
         for (String _snowmanxx : _snowman.o) {
            if (_snowmanxx.startsWith(_snowman.c)) {
               a(_snowman.h, _snowmanx, _snowmanxx, -1, 0.02F);
            } else {
               a(_snowman.h, _snowmanx, _snowmanxx, -23296, 0.02F);
            }

            _snowmanx++;
         }
      }

      if (_snowman) {
         for (String _snowmanxx : Lists.reverse(_snowman.n)) {
            a(_snowman.h, _snowmanx, _snowmanxx, -3355444, 0.02F);
            _snowmanx++;
         }
      }

      if (_snowman) {
         this.a(_snowman, _snowman, _snowman, _snowman);
      }
   }

   private static void a(String var0, edc.b var1, int var2, int var3) {
      fx _snowman = _snowman.a;
      a(_snowman, _snowman, _snowman, _snowman);
   }

   private static void a(String var0, fx var1, int var2, int var3) {
      double _snowman = 1.3;
      double _snowmanx = 0.2;
      double _snowmanxx = (double)_snowman.u() + 0.5;
      double _snowmanxxx = (double)_snowman.v() + 1.3 + (double)_snowman * 0.2;
      double _snowmanxxxx = (double)_snowman.w() + 0.5;
      edh.a(_snowman, _snowmanxx, _snowmanxxx, _snowmanxxxx, _snowman, 0.02F, true, 0.0F, true);
   }

   private static void a(gk var0, int var1, String var2, int var3, float var4) {
      double _snowman = 2.4;
      double _snowmanx = 0.25;
      fx _snowmanxx = new fx(_snowman);
      double _snowmanxxx = (double)_snowmanxx.u() + 0.5;
      double _snowmanxxxx = _snowman.b() + 2.4 + (double)_snowman * 0.25;
      double _snowmanxxxxx = (double)_snowmanxx.w() + 0.5;
      float _snowmanxxxxxx = 0.5F;
      edh.a(_snowman, _snowmanxxx, _snowmanxxxx, _snowmanxxxxx, _snowman, _snowman, false, 0.5F, true);
   }

   private Set<String> c(edc.b var1) {
      return this.c(_snowman.a).stream().map(ry::a).collect(Collectors.toSet());
   }

   private Set<String> d(edc.b var1) {
      return this.d(_snowman.a).stream().map(ry::a).collect(Collectors.toSet());
   }

   private boolean b(edc.a var1) {
      return Objects.equals(this.e, _snowman.a);
   }

   private boolean c(edc.a var1) {
      bfw _snowman = this.b.s;
      fx _snowmanx = new fx(_snowman.cD(), _snowman.h.b(), _snowman.cH());
      fx _snowmanxx = new fx(_snowman.h);
      return _snowmanx.a(_snowmanxx, 30.0);
   }

   private Collection<UUID> c(fx var1) {
      return this.d.values().stream().filter(var1x -> var1x.a(_snowman)).map(edc.a::a).collect(Collectors.toSet());
   }

   private Collection<UUID> d(fx var1) {
      return this.d.values().stream().filter(var1x -> var1x.b(_snowman)).map(edc.a::a).collect(Collectors.toSet());
   }

   private Map<fx, List<String>> c() {
      Map<fx, List<String>> _snowman = Maps.newHashMap();

      for (edc.a _snowmanx : this.d.values()) {
         for (fx _snowmanxx : Iterables.concat(_snowmanx.p, _snowmanx.q)) {
            if (!this.c.containsKey(_snowmanxx)) {
               _snowman.computeIfAbsent(_snowmanxx, var0 -> Lists.newArrayList()).add(_snowmanx.c);
            }
         }
      }

      return _snowman;
   }

   private void d() {
      edh.a(this.b.aa(), 8).ifPresent(var1 -> this.e = var1.bS());
   }

   public static class a {
      public final UUID a;
      public final int b;
      public final String c;
      public final String d;
      public final int e;
      public final float f;
      public final float g;
      public final gk h;
      public final String i;
      public final cxd j;
      public final boolean k;
      public final List<String> l = Lists.newArrayList();
      public final List<String> m = Lists.newArrayList();
      public final List<String> n = Lists.newArrayList();
      public final List<String> o = Lists.newArrayList();
      public final Set<fx> p = Sets.newHashSet();
      public final Set<fx> q = Sets.newHashSet();

      public a(UUID var1, int var2, String var3, String var4, int var5, float var6, float var7, gk var8, String var9, @Nullable cxd var10, boolean var11) {
         this.a = _snowman;
         this.b = _snowman;
         this.c = _snowman;
         this.d = _snowman;
         this.e = _snowman;
         this.f = _snowman;
         this.g = _snowman;
         this.h = _snowman;
         this.i = _snowman;
         this.j = _snowman;
         this.k = _snowman;
      }

      private boolean a(fx var1) {
         return this.p.stream().anyMatch(_snowman::equals);
      }

      private boolean b(fx var1) {
         return this.q.contains(_snowman);
      }

      public UUID a() {
         return this.a;
      }
   }

   public static class b {
      public final fx a;
      public String b;
      public int c;

      public b(fx var1, String var2, int var3) {
         this.a = _snowman;
         this.b = _snowman;
         this.c = _snowman;
      }
   }
}
