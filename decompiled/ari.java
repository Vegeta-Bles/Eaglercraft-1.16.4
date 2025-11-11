import com.google.common.collect.Maps;
import com.google.common.collect.Multimap;
import com.google.common.collect.Sets;
import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import javax.annotation.Nullable;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ari {
   private static final Logger a = LogManager.getLogger();
   private final Map<arg, arh> b = Maps.newHashMap();
   private final Set<arh> c = Sets.newHashSet();
   private final ark d;

   public ari(ark var1) {
      this.d = _snowman;
   }

   private void a(arh var1) {
      if (_snowman.a().b()) {
         this.c.add(_snowman);
      }
   }

   public Set<arh> a() {
      return this.c;
   }

   public Collection<arh> b() {
      return this.b.values().stream().filter(var0 -> var0.a().b()).collect(Collectors.toList());
   }

   @Nullable
   public arh a(arg var1) {
      return this.b.computeIfAbsent(_snowman, var1x -> this.d.a(this::a, var1x));
   }

   public boolean b(arg var1) {
      return this.b.get(_snowman) != null || this.d.c(_snowman);
   }

   public boolean a(arg var1, UUID var2) {
      arh _snowman = this.b.get(_snowman);
      return _snowman != null ? _snowman.a(_snowman) != null : this.d.b(_snowman, _snowman);
   }

   public double c(arg var1) {
      arh _snowman = this.b.get(_snowman);
      return _snowman != null ? _snowman.f() : this.d.a(_snowman);
   }

   public double d(arg var1) {
      arh _snowman = this.b.get(_snowman);
      return _snowman != null ? _snowman.b() : this.d.b(_snowman);
   }

   public double b(arg var1, UUID var2) {
      arh _snowman = this.b.get(_snowman);
      return _snowman != null ? _snowman.a(_snowman).d() : this.d.a(_snowman, _snowman);
   }

   public void a(Multimap<arg, arj> var1) {
      _snowman.asMap().forEach((var1x, var2) -> {
         arh _snowman = this.b.get(var1x);
         if (_snowman != null) {
            var2.forEach(_snowman::d);
         }
      });
   }

   public void b(Multimap<arg, arj> var1) {
      _snowman.forEach((var1x, var2) -> {
         arh _snowman = this.a(var1x);
         if (_snowman != null) {
            _snowman.d(var2);
            _snowman.b(var2);
         }
      });
   }

   public void a(ari var1) {
      _snowman.b.values().forEach(var1x -> {
         arh _snowman = this.a(var1x.a());
         if (_snowman != null) {
            _snowman.a(var1x);
         }
      });
   }

   public mj c() {
      mj _snowman = new mj();

      for (arh _snowmanx : this.b.values()) {
         _snowman.add(_snowmanx.g());
      }

      return _snowman;
   }

   public void a(mj var1) {
      for (int _snowman = 0; _snowman < _snowman.size(); _snowman++) {
         md _snowmanx = _snowman.a(_snowman);
         String _snowmanxx = _snowmanx.l("Name");
         x.a(gm.af.b(vk.a(_snowmanxx)), var2x -> {
            arh _snowmanxxx = this.a(var2x);
            if (_snowmanxxx != null) {
               _snowmanxxx.a(_snowman);
            }
         }, () -> a.warn("Ignoring unknown attribute '{}'", _snowman));
      }
   }
}
