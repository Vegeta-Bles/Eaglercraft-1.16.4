import java.util.Iterator;
import java.util.List;

public class lj {
   private final lf a;
   private final List<lc> b;
   private long c;

   public void a(long var1) {
      try {
         this.c(_snowman);
      } catch (Exception var4) {
      }
   }

   public void b(long var1) {
      try {
         this.c(_snowman);
      } catch (Exception var4) {
         this.a.a(var4);
      }
   }

   private void c(long var1) {
      Iterator<lc> _snowman = this.b.iterator();

      while (_snowman.hasNext()) {
         lc _snowmanx = _snowman.next();
         _snowmanx.b.run();
         _snowman.remove();
         long _snowmanxx = _snowman - this.c;
         long _snowmanxxx = this.c;
         this.c = _snowman;
         if (_snowmanx.a != null && _snowmanx.a != _snowmanxx) {
            this.a.a(new ky("Succeeded in invalid tick: expected " + (_snowmanxxx + _snowmanx.a) + ", but current tick is " + _snowman));
            break;
         }
      }
   }
}
