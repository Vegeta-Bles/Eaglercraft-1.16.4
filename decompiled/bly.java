import com.google.common.collect.Maps;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

public class bly {
   private final Map<blx, bly.a> a = Maps.newHashMap();
   private int b;

   public bly() {
   }

   public boolean a(blx var1) {
      return this.a(_snowman, 0.0F) > 0.0F;
   }

   public float a(blx var1, float var2) {
      bly.a _snowman = this.a.get(_snowman);
      if (_snowman != null) {
         float _snowmanx = (float)(_snowman.c - _snowman.b);
         float _snowmanxx = (float)_snowman.c - ((float)this.b + _snowman);
         return afm.a(_snowmanxx / _snowmanx, 0.0F, 1.0F);
      } else {
         return 0.0F;
      }
   }

   public void a() {
      this.b++;
      if (!this.a.isEmpty()) {
         Iterator<Entry<blx, bly.a>> _snowman = this.a.entrySet().iterator();

         while (_snowman.hasNext()) {
            Entry<blx, bly.a> _snowmanx = _snowman.next();
            if (_snowmanx.getValue().c <= this.b) {
               _snowman.remove();
               this.c(_snowmanx.getKey());
            }
         }
      }
   }

   public void a(blx var1, int var2) {
      this.a.put(_snowman, new bly.a(this.b, this.b + _snowman));
      this.b(_snowman, _snowman);
   }

   public void b(blx var1) {
      this.a.remove(_snowman);
      this.c(_snowman);
   }

   protected void b(blx var1, int var2) {
   }

   protected void c(blx var1) {
   }

   class a {
      private final int b;
      private final int c;

      private a(int var2, int var3) {
         this.b = _snowman;
         this.c = _snowman;
      }
   }
}
