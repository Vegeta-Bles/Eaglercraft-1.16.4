import com.google.common.base.Functions;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;
import java.util.function.Function;
import javax.annotation.Nullable;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class z {
   private static final Logger a = LogManager.getLogger();
   private final Map<vk, y> b = Maps.newHashMap();
   private final Set<y> c = Sets.newLinkedHashSet();
   private final Set<y> d = Sets.newLinkedHashSet();
   private z.a e;

   public z() {
   }

   private void a(y var1) {
      for (y _snowman : _snowman.e()) {
         this.a(_snowman);
      }

      a.info("Forgot about advancement {}", _snowman.h());
      this.b.remove(_snowman.h());
      if (_snowman.b() == null) {
         this.c.remove(_snowman);
         if (this.e != null) {
            this.e.b(_snowman);
         }
      } else {
         this.d.remove(_snowman);
         if (this.e != null) {
            this.e.d(_snowman);
         }
      }
   }

   public void a(Set<vk> var1) {
      for (vk _snowman : _snowman) {
         y _snowmanx = this.b.get(_snowman);
         if (_snowmanx == null) {
            a.warn("Told to remove advancement {} but I don't know what that is", _snowman);
         } else {
            this.a(_snowmanx);
         }
      }
   }

   public void a(Map<vk, y.a> var1) {
      Function<vk, y> _snowman = Functions.forMap(this.b, null);

      while (!_snowman.isEmpty()) {
         boolean _snowmanx = false;
         Iterator<Entry<vk, y.a>> _snowmanxx = _snowman.entrySet().iterator();

         while (_snowmanxx.hasNext()) {
            Entry<vk, y.a> _snowmanxxx = _snowmanxx.next();
            vk _snowmanxxxx = _snowmanxxx.getKey();
            y.a _snowmanxxxxx = _snowmanxxx.getValue();
            if (_snowmanxxxxx.a(_snowman)) {
               y _snowmanxxxxxx = _snowmanxxxxx.b(_snowmanxxxx);
               this.b.put(_snowmanxxxx, _snowmanxxxxxx);
               _snowmanx = true;
               _snowmanxx.remove();
               if (_snowmanxxxxxx.b() == null) {
                  this.c.add(_snowmanxxxxxx);
                  if (this.e != null) {
                     this.e.a(_snowmanxxxxxx);
                  }
               } else {
                  this.d.add(_snowmanxxxxxx);
                  if (this.e != null) {
                     this.e.c(_snowmanxxxxxx);
                  }
               }
            }
         }

         if (!_snowmanx) {
            for (Entry<vk, y.a> _snowmanxxx : _snowman.entrySet()) {
               a.error("Couldn't load advancement {}: {}", _snowmanxxx.getKey(), _snowmanxxx.getValue());
            }
            break;
         }
      }

      a.info("Loaded {} advancements", this.b.size());
   }

   public void a() {
      this.b.clear();
      this.c.clear();
      this.d.clear();
      if (this.e != null) {
         this.e.a();
      }
   }

   public Iterable<y> b() {
      return this.c;
   }

   public Collection<y> c() {
      return this.b.values();
   }

   @Nullable
   public y a(vk var1) {
      return this.b.get(_snowman);
   }

   public void a(@Nullable z.a var1) {
      this.e = _snowman;
      if (_snowman != null) {
         for (y _snowman : this.c) {
            _snowman.a(_snowman);
         }

         for (y _snowman : this.d) {
            _snowman.c(_snowman);
         }
      }
   }

   public interface a {
      void a(y var1);

      void b(y var1);

      void c(y var1);

      void d(y var1);

      void a();
   }
}
