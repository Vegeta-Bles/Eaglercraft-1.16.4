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

public class edb implements edh.a {
   private final djz a;
   private final Map<fx, edb.b> b = Maps.newHashMap();
   private final Map<UUID, edb.a> c = Maps.newHashMap();
   private UUID d;

   public edb(djz var1) {
      this.a = _snowman;
   }

   @Override
   public void a() {
      this.b.clear();
      this.c.clear();
      this.d = null;
   }

   public void a(edb.b var1) {
      this.b.put(_snowman.a, _snowman);
   }

   public void a(edb.a var1) {
      this.c.put(_snowman.a, _snowman);
   }

   @Override
   public void a(dfm var1, eag var2, double var3, double var5, double var7) {
      RenderSystem.pushMatrix();
      RenderSystem.enableBlend();
      RenderSystem.defaultBlendFunc();
      RenderSystem.disableTexture();
      this.c();
      this.b();
      this.d();
      RenderSystem.enableTexture();
      RenderSystem.disableBlend();
      RenderSystem.popMatrix();
      if (!this.a.s.a_()) {
         this.i();
      }
   }

   private void b() {
      this.c.entrySet().removeIf(var1 -> this.a.r.a(var1.getValue().b) == null);
   }

   private void c() {
      long _snowman = this.a.r.T() - 20L;
      this.b.entrySet().removeIf(var2 -> var2.getValue().f < _snowman);
   }

   private void d() {
      fx _snowman = this.g().c();
      this.c.values().forEach(var1x -> {
         if (this.e(var1x)) {
            this.c(var1x);
         }
      });
      this.f();

      for (fx _snowmanx : this.b.keySet()) {
         if (_snowman.a(_snowmanx, 30.0)) {
            a(_snowmanx);
         }
      }

      Map<fx, Set<UUID>> _snowmanxx = this.e();
      this.b.values().forEach(var3x -> {
         if (_snowman.a(var3x.a, 30.0)) {
            Set<UUID> _snowmanxxx = _snowman.get(var3x.a);
            this.a(var3x, (Collection<UUID>)(_snowmanxxx == null ? Sets.newHashSet() : _snowmanxxx));
         }
      });
      this.h().forEach((var2, var3x) -> {
         if (_snowman.a(var2, 30.0)) {
            this.a(var2, (List<String>)var3x);
         }
      });
   }

   private Map<fx, Set<UUID>> e() {
      Map<fx, Set<UUID>> _snowman = Maps.newHashMap();
      this.c.values().forEach(var1x -> var1x.i.forEach(var2 -> _snowman.computeIfAbsent(var2, var0x -> Sets.newHashSet()).add(var1x.a())));
      return _snowman;
   }

   private void f() {
      Map<fx, Set<UUID>> _snowman = Maps.newHashMap();
      this.c.values().stream().filter(edb.a::c).forEach(var1x -> _snowman.computeIfAbsent(var1x.f, var0x -> Sets.newHashSet()).add(var1x.a()));
      _snowman.entrySet().forEach(var0 -> {
         fx _snowmanx = var0.getKey();
         Set<UUID> _snowmanx = var0.getValue();
         Set<String> _snowmanxx = _snowmanx.stream().map(ry::a).collect(Collectors.toSet());
         int _snowmanxxx = 1;
         a(_snowmanxx.toString(), _snowmanx, _snowmanxxx++, -256);
         a("Flower", _snowmanx, _snowmanxxx++, -1);
         float _snowmanxxxx = 0.05F;
         a(_snowmanx, 0.05F, 0.8F, 0.8F, 0.0F, 0.3F);
      });
   }

   private static String a(Collection<UUID> var0) {
      if (_snowman.isEmpty()) {
         return "-";
      } else {
         return _snowman.size() > 3 ? "" + _snowman.size() + " bees" : _snowman.stream().map(ry::a).collect(Collectors.toSet()).toString();
      }
   }

   private static void a(fx var0) {
      float _snowman = 0.05F;
      a(_snowman, 0.05F, 0.2F, 0.2F, 1.0F, 0.3F);
   }

   private void a(fx var1, List<String> var2) {
      float _snowman = 0.05F;
      a(_snowman, 0.05F, 0.2F, 0.2F, 1.0F, 0.3F);
      a("" + _snowman, _snowman, 0, -256);
      a("Ghost Hive", _snowman, 1, -65536);
   }

   private static void a(fx var0, float var1, float var2, float var3, float var4, float var5) {
      RenderSystem.enableBlend();
      RenderSystem.defaultBlendFunc();
      edh.a(_snowman, _snowman, _snowman, _snowman, _snowman, _snowman);
   }

   private void a(edb.b var1, Collection<UUID> var2) {
      int _snowman = 0;
      if (!_snowman.isEmpty()) {
         a("Blacklisted by " + a(_snowman), _snowman, _snowman++, -65536);
      }

      a("Out: " + a(this.b(_snowman.a)), _snowman, _snowman++, -3355444);
      if (_snowman.c == 0) {
         a("In: -", _snowman, _snowman++, -256);
      } else if (_snowman.c == 1) {
         a("In: 1 bee", _snowman, _snowman++, -256);
      } else {
         a("In: " + _snowman.c + " bees", _snowman, _snowman++, -256);
      }

      a("Honey: " + _snowman.d, _snowman, _snowman++, -23296);
      a(_snowman.b + (_snowman.e ? " (sedated)" : ""), _snowman, _snowman++, -1);
   }

   private void b(edb.a var1) {
      if (_snowman.d != null) {
         edn.a(_snowman.d, 0.5F, false, false, this.g().b().a(), this.g().b().b(), this.g().b().c());
      }
   }

   private void c(edb.a var1) {
      boolean _snowman = this.d(_snowman);
      int _snowmanx = 0;
      a(_snowman.c, _snowmanx++, _snowman.toString(), -1, 0.03F);
      if (_snowman.e == null) {
         a(_snowman.c, _snowmanx++, "No hive", -98404, 0.02F);
      } else {
         a(_snowman.c, _snowmanx++, "Hive: " + this.a(_snowman, _snowman.e), -256, 0.02F);
      }

      if (_snowman.f == null) {
         a(_snowman.c, _snowmanx++, "No flower", -98404, 0.02F);
      } else {
         a(_snowman.c, _snowmanx++, "Flower: " + this.a(_snowman, _snowman.f), -256, 0.02F);
      }

      for (String _snowmanxx : _snowman.h) {
         a(_snowman.c, _snowmanx++, _snowmanxx, -16711936, 0.02F);
      }

      if (_snowman) {
         this.b(_snowman);
      }

      if (_snowman.g > 0) {
         int _snowmanxx = _snowman.g < 600 ? -3355444 : -23296;
         a(_snowman.c, _snowmanx++, "Travelling: " + _snowman.g + " ticks", _snowmanxx, 0.02F);
      }
   }

   private static void a(String var0, edb.b var1, int var2, int var3) {
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

   private djk g() {
      return this.a.h.k();
   }

   private String a(edb.a var1, fx var2) {
      float _snowman = afm.a(_snowman.a(_snowman.c.a(), _snowman.c.b(), _snowman.c.c(), true));
      double _snowmanx = (double)Math.round(_snowman * 10.0F) / 10.0;
      return _snowman.x() + " (dist " + _snowmanx + ")";
   }

   private boolean d(edb.a var1) {
      return Objects.equals(this.d, _snowman.a);
   }

   private boolean e(edb.a var1) {
      bfw _snowman = this.a.s;
      fx _snowmanx = new fx(_snowman.cD(), _snowman.c.b(), _snowman.cH());
      fx _snowmanxx = new fx(_snowman.c);
      return _snowmanx.a(_snowmanxx, 30.0);
   }

   private Collection<UUID> b(fx var1) {
      return this.c.values().stream().filter(var1x -> var1x.a(_snowman)).map(edb.a::a).collect(Collectors.toSet());
   }

   private Map<fx, List<String>> h() {
      Map<fx, List<String>> _snowman = Maps.newHashMap();

      for (edb.a _snowmanx : this.c.values()) {
         if (_snowmanx.e != null && !this.b.containsKey(_snowmanx.e)) {
            _snowman.computeIfAbsent(_snowmanx.e, var0 -> Lists.newArrayList()).add(_snowmanx.b());
         }
      }

      return _snowman;
   }

   private void i() {
      edh.a(this.a.aa(), 8).ifPresent(var1 -> this.d = var1.bS());
   }

   public static class a {
      public final UUID a;
      public final int b;
      public final gk c;
      @Nullable
      public final cxd d;
      @Nullable
      public final fx e;
      @Nullable
      public final fx f;
      public final int g;
      public final List<String> h = Lists.newArrayList();
      public final Set<fx> i = Sets.newHashSet();

      public a(UUID var1, int var2, gk var3, cxd var4, fx var5, fx var6, int var7) {
         this.a = _snowman;
         this.b = _snowman;
         this.c = _snowman;
         this.d = _snowman;
         this.e = _snowman;
         this.f = _snowman;
         this.g = _snowman;
      }

      public boolean a(fx var1) {
         return this.e != null && this.e.equals(_snowman);
      }

      public UUID a() {
         return this.a;
      }

      public String b() {
         return ry.a(this.a);
      }

      @Override
      public String toString() {
         return this.b();
      }

      public boolean c() {
         return this.f != null;
      }
   }

   public static class b {
      public final fx a;
      public final String b;
      public final int c;
      public final int d;
      public final boolean e;
      public final long f;

      public b(fx var1, String var2, int var3, int var4, boolean var5, long var6) {
         this.a = _snowman;
         this.b = _snowman;
         this.c = _snowman;
         this.d = _snowman;
         this.e = _snowman;
         this.f = _snowman;
      }
   }
}
