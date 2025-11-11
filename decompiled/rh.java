import java.io.IOException;
import java.util.List;

public class rh implements oj<om> {
   private int a;
   private int[] b;

   public rh() {
   }

   public rh(aqa var1) {
      this.a = _snowman.Y();
      List<aqa> _snowman = _snowman.cn();
      this.b = new int[_snowman.size()];

      for (int _snowmanx = 0; _snowmanx < _snowman.size(); _snowmanx++) {
         this.b[_snowmanx] = _snowman.get(_snowmanx).Y();
      }
   }

   @Override
   public void a(nf var1) throws IOException {
      this.a = _snowman.i();
      this.b = _snowman.b();
   }

   @Override
   public void b(nf var1) throws IOException {
      _snowman.d(this.a);
      _snowman.a(this.b);
   }

   public void a(om var1) {
      _snowman.a(this);
   }

   public int[] b() {
      return this.b;
   }

   public int c() {
      return this.a;
   }
}
