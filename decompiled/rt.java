import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import java.io.IOException;
import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

public class rt implements oj<om> {
   private boolean a;
   private Map<vk, y.a> b;
   private Set<vk> c;
   private Map<vk, aa> d;

   public rt() {
   }

   public rt(boolean var1, Collection<y> var2, Set<vk> var3, Map<vk, aa> var4) {
      this.a = _snowman;
      this.b = Maps.newHashMap();

      for (y _snowman : _snowman) {
         this.b.put(_snowman.h(), _snowman.a());
      }

      this.c = _snowman;
      this.d = Maps.newHashMap(_snowman);
   }

   public void a(om var1) {
      _snowman.a(this);
   }

   @Override
   public void a(nf var1) throws IOException {
      this.a = _snowman.readBoolean();
      this.b = Maps.newHashMap();
      this.c = Sets.newLinkedHashSet();
      this.d = Maps.newHashMap();
      int _snowman = _snowman.i();

      for (int _snowmanx = 0; _snowmanx < _snowman; _snowmanx++) {
         vk _snowmanxx = _snowman.p();
         y.a _snowmanxxx = y.a.b(_snowman);
         this.b.put(_snowmanxx, _snowmanxxx);
      }

      _snowman = _snowman.i();

      for (int _snowmanx = 0; _snowmanx < _snowman; _snowmanx++) {
         vk _snowmanxx = _snowman.p();
         this.c.add(_snowmanxx);
      }

      _snowman = _snowman.i();

      for (int _snowmanx = 0; _snowmanx < _snowman; _snowmanx++) {
         vk _snowmanxx = _snowman.p();
         this.d.put(_snowmanxx, aa.b(_snowman));
      }
   }

   @Override
   public void b(nf var1) throws IOException {
      _snowman.writeBoolean(this.a);
      _snowman.d(this.b.size());

      for (Entry<vk, y.a> _snowman : this.b.entrySet()) {
         vk _snowmanx = _snowman.getKey();
         y.a _snowmanxx = _snowman.getValue();
         _snowman.a(_snowmanx);
         _snowmanxx.a(_snowman);
      }

      _snowman.d(this.c.size());

      for (vk _snowman : this.c) {
         _snowman.a(_snowman);
      }

      _snowman.d(this.d.size());

      for (Entry<vk, aa> _snowman : this.d.entrySet()) {
         _snowman.a(_snowman.getKey());
         _snowman.getValue().a(_snowman);
      }
   }

   public Map<vk, y.a> b() {
      return this.b;
   }

   public Set<vk> c() {
      return this.c;
   }

   public Map<vk, aa> d() {
      return this.d;
   }

   public boolean e() {
      return this.a;
   }
}
