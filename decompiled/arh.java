import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import it.unimi.dsi.fastutil.objects.Object2ObjectArrayMap;
import it.unimi.dsi.fastutil.objects.ObjectArraySet;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.function.Consumer;
import javax.annotation.Nullable;

public class arh {
   private final arg a;
   private final Map<arj.a, Set<arj>> b = Maps.newEnumMap(arj.a.class);
   private final Map<UUID, arj> c = new Object2ObjectArrayMap();
   private final Set<arj> d = new ObjectArraySet();
   private double e;
   private boolean f = true;
   private double g;
   private final Consumer<arh> h;

   public arh(arg var1, Consumer<arh> var2) {
      this.a = _snowman;
      this.h = _snowman;
      this.e = _snowman.a();
   }

   public arg a() {
      return this.a;
   }

   public double b() {
      return this.e;
   }

   public void a(double var1) {
      if (_snowman != this.e) {
         this.e = _snowman;
         this.d();
      }
   }

   public Set<arj> a(arj.a var1) {
      return this.b.computeIfAbsent(_snowman, var0 -> Sets.newHashSet());
   }

   public Set<arj> c() {
      return ImmutableSet.copyOf(this.c.values());
   }

   @Nullable
   public arj a(UUID var1) {
      return this.c.get(_snowman);
   }

   public boolean a(arj var1) {
      return this.c.get(_snowman.a()) != null;
   }

   private void e(arj var1) {
      arj _snowman = this.c.putIfAbsent(_snowman.a(), _snowman);
      if (_snowman != null) {
         throw new IllegalArgumentException("Modifier is already applied on this attribute!");
      } else {
         this.a(_snowman.c()).add(_snowman);
         this.d();
      }
   }

   public void b(arj var1) {
      this.e(_snowman);
   }

   public void c(arj var1) {
      this.e(_snowman);
      this.d.add(_snowman);
   }

   protected void d() {
      this.f = true;
      this.h.accept(this);
   }

   public void d(arj var1) {
      this.a(_snowman.c()).remove(_snowman);
      this.c.remove(_snowman.a());
      this.d.remove(_snowman);
      this.d();
   }

   public void b(UUID var1) {
      arj _snowman = this.a(_snowman);
      if (_snowman != null) {
         this.d(_snowman);
      }
   }

   public boolean c(UUID var1) {
      arj _snowman = this.a(_snowman);
      if (_snowman != null && this.d.contains(_snowman)) {
         this.d(_snowman);
         return true;
      } else {
         return false;
      }
   }

   public void e() {
      for (arj _snowman : this.c()) {
         this.d(_snowman);
      }
   }

   public double f() {
      if (this.f) {
         this.g = this.h();
         this.f = false;
      }

      return this.g;
   }

   private double h() {
      double _snowman = this.b();

      for (arj _snowmanx : this.b(arj.a.a)) {
         _snowman += _snowmanx.d();
      }

      double _snowmanx = _snowman;

      for (arj _snowmanxx : this.b(arj.a.b)) {
         _snowmanx += _snowman * _snowmanxx.d();
      }

      for (arj _snowmanxx : this.b(arj.a.c)) {
         _snowmanx *= 1.0 + _snowmanxx.d();
      }

      return this.a.a(_snowmanx);
   }

   private Collection<arj> b(arj.a var1) {
      return this.b.getOrDefault(_snowman, Collections.emptySet());
   }

   public void a(arh var1) {
      this.e = _snowman.e;
      this.c.clear();
      this.c.putAll(_snowman.c);
      this.d.clear();
      this.d.addAll(_snowman.d);
      this.b.clear();
      _snowman.b.forEach((var1x, var2) -> this.a(var1x).addAll(var2));
      this.d();
   }

   public md g() {
      md _snowman = new md();
      _snowman.a("Name", gm.af.b(this.a).toString());
      _snowman.a("Base", this.e);
      if (!this.d.isEmpty()) {
         mj _snowmanx = new mj();

         for (arj _snowmanxx : this.d) {
            _snowmanx.add(_snowmanxx.e());
         }

         _snowman.a("Modifiers", _snowmanx);
      }

      return _snowman;
   }

   public void a(md var1) {
      this.e = _snowman.k("Base");
      if (_snowman.c("Modifiers", 9)) {
         mj _snowman = _snowman.d("Modifiers", 10);

         for (int _snowmanx = 0; _snowmanx < _snowman.size(); _snowmanx++) {
            arj _snowmanxx = arj.a(_snowman.a(_snowmanx));
            if (_snowmanxx != null) {
               this.c.put(_snowmanxx.a(), _snowmanxx);
               this.a(_snowmanxx.c()).add(_snowmanxx);
               this.d.add(_snowmanxx);
            }
         }
      }

      this.d();
   }
}
