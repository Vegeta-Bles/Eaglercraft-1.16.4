import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import it.unimi.dsi.fastutil.objects.ObjectIterator;
import it.unimi.dsi.fastutil.objects.Object2IntMap.Entry;
import java.io.IOException;
import java.util.Map;

public class ot implements oj<om> {
   private Object2IntMap<adx<?>> a;

   public ot() {
   }

   public ot(Object2IntMap<adx<?>> var1) {
      this.a = _snowman;
   }

   public void a(om var1) {
      _snowman.a(this);
   }

   @Override
   public void a(nf var1) throws IOException {
      int _snowman = _snowman.i();
      this.a = new Object2IntOpenHashMap(_snowman);

      for (int _snowmanx = 0; _snowmanx < _snowman; _snowmanx++) {
         this.a(gm.ag.a(_snowman.i()), _snowman);
      }
   }

   private <T> void a(adz<T> var1, nf var2) {
      int _snowman = _snowman.i();
      int _snowmanx = _snowman.i();
      this.a.put(_snowman.b(_snowman.a().a(_snowman)), _snowmanx);
   }

   @Override
   public void b(nf var1) throws IOException {
      _snowman.d(this.a.size());
      ObjectIterator var2 = this.a.object2IntEntrySet().iterator();

      while (var2.hasNext()) {
         Entry<adx<?>> _snowman = (Entry<adx<?>>)var2.next();
         adx<?> _snowmanx = (adx<?>)_snowman.getKey();
         _snowman.d(gm.ag.a(_snowmanx.a()));
         _snowman.d(this.a(_snowmanx));
         _snowman.d(_snowman.getIntValue());
      }
   }

   private <T> int a(adx<T> var1) {
      return _snowman.a().a().a(_snowman.b());
   }

   public Map<adx<?>, Integer> b() {
      return this.a;
   }
}
