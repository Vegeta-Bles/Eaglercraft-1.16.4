import com.google.common.collect.Lists;
import java.io.IOException;
import java.util.Collection;
import java.util.List;

public class rw implements oj<om> {
   private List<boq<?>> a;

   public rw() {
   }

   public rw(Collection<boq<?>> var1) {
      this.a = Lists.newArrayList(_snowman);
   }

   public void a(om var1) {
      _snowman.a(this);
   }

   @Override
   public void a(nf var1) throws IOException {
      this.a = Lists.newArrayList();
      int _snowman = _snowman.i();

      for (int _snowmanx = 0; _snowmanx < _snowman; _snowmanx++) {
         this.a.add(c(_snowman));
      }
   }

   @Override
   public void b(nf var1) throws IOException {
      _snowman.d(this.a.size());

      for (boq<?> _snowman : this.a) {
         a(_snowman, _snowman);
      }
   }

   public List<boq<?>> b() {
      return this.a;
   }

   public static boq<?> c(nf var0) {
      vk _snowman = _snowman.p();
      vk _snowmanx = _snowman.p();
      return gm.ae.b(_snowman).orElseThrow(() -> new IllegalArgumentException("Unknown recipe serializer " + _snowman)).a(_snowmanx, _snowman);
   }

   public static <T extends boq<?>> void a(T var0, nf var1) {
      _snowman.a(gm.ae.b(_snowman.ag_()));
      _snowman.a(_snowman.f());
      ((bos<T>)_snowman.ag_()).a(_snowman, _snowman);
   }
}
