import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import it.unimi.dsi.fastutil.objects.ObjectIterator;
import it.unimi.dsi.fastutil.objects.Object2IntMap.Entry;

public class cxw extends cxs {
   private final Object2IntMap<String> a = new Object2IntOpenHashMap();

   public cxw() {
      super("idcounts");
      this.a.defaultReturnValue(-1);
   }

   @Override
   public void a(md var1) {
      this.a.clear();

      for (String _snowman : _snowman.d()) {
         if (_snowman.c(_snowman, 99)) {
            this.a.put(_snowman, _snowman.h(_snowman));
         }
      }
   }

   @Override
   public md b(md var1) {
      ObjectIterator var2 = this.a.object2IntEntrySet().iterator();

      while (var2.hasNext()) {
         Entry<String> _snowman = (Entry<String>)var2.next();
         _snowman.b((String)_snowman.getKey(), _snowman.getIntValue());
      }

      return _snowman;
   }

   public int a() {
      int _snowman = this.a.getInt("map") + 1;
      this.a.put("map", _snowman);
      this.b();
      return _snowman;
   }
}
