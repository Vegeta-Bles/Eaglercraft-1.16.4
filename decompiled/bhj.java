import com.google.common.collect.Lists;
import it.unimi.dsi.fastutil.ints.Int2ObjectAVLTreeMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectSortedMap;
import java.util.List;

public class bhj {
   private final List<bhg> a = Lists.newArrayList();
   private int b;

   public bhj() {
   }

   public bhj a(int var1, float var2) {
      this.a.add(new bhg(_snowman, _snowman));
      this.b();
      return this;
   }

   private void b() {
      Int2ObjectSortedMap<bhg> _snowman = new Int2ObjectAVLTreeMap();
      this.a.forEach(var1x -> {
         bhg var10000 = (bhg)_snowman.put(var1x.a(), var1x);
      });
      this.a.clear();
      this.a.addAll(_snowman.values());
      this.b = 0;
   }

   public float a(int var1) {
      if (this.a.size() <= 0) {
         return 0.0F;
      } else {
         bhg _snowman = this.a.get(this.b);
         bhg _snowmanx = this.a.get(this.a.size() - 1);
         boolean _snowmanxx = _snowman < _snowman.a();
         int _snowmanxxx = _snowmanxx ? 0 : this.b;
         float _snowmanxxxx = _snowmanxx ? _snowmanx.b() : _snowman.b();

         for (int _snowmanxxxxx = _snowmanxxx; _snowmanxxxxx < this.a.size(); _snowmanxxxxx++) {
            bhg _snowmanxxxxxx = this.a.get(_snowmanxxxxx);
            if (_snowmanxxxxxx.a() > _snowman) {
               break;
            }

            this.b = _snowmanxxxxx;
            _snowmanxxxx = _snowmanxxxxxx.b();
         }

         return _snowmanxxxx;
      }
   }
}
