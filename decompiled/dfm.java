import com.google.common.collect.Queues;
import java.util.Deque;

public class dfm {
   private final Deque<dfm.a> a = x.a(Queues.newArrayDeque(), var0 -> {
      b _snowman = new b();
      _snowman.a();
      a _snowmanx = new a();
      _snowmanx.c();
      var0.add(new dfm.a(_snowman, _snowmanx));
   });

   public dfm() {
   }

   public void a(double var1, double var3, double var5) {
      dfm.a _snowman = this.a.getLast();
      _snowman.a.a(b.b((float)_snowman, (float)_snowman, (float)_snowman));
   }

   public void a(float var1, float var2, float var3) {
      dfm.a _snowman = this.a.getLast();
      _snowman.a.a(b.a(_snowman, _snowman, _snowman));
      if (_snowman == _snowman && _snowman == _snowman) {
         if (_snowman > 0.0F) {
            return;
         }

         _snowman.b.a(-1.0F);
      }

      float _snowmanx = 1.0F / _snowman;
      float _snowmanxx = 1.0F / _snowman;
      float _snowmanxxx = 1.0F / _snowman;
      float _snowmanxxxx = afm.j(_snowmanx * _snowmanxx * _snowmanxxx);
      _snowman.b.b(a.b(_snowmanxxxx * _snowmanx, _snowmanxxxx * _snowmanxx, _snowmanxxxx * _snowmanxxx));
   }

   public void a(d var1) {
      dfm.a _snowman = this.a.getLast();
      _snowman.a.a(_snowman);
      _snowman.b.a(_snowman);
   }

   public void a() {
      dfm.a _snowman = this.a.getLast();
      this.a.addLast(new dfm.a(_snowman.a.d(), _snowman.b.d()));
   }

   public void b() {
      this.a.removeLast();
   }

   public dfm.a c() {
      return this.a.getLast();
   }

   public boolean d() {
      return this.a.size() == 1;
   }

   public static final class a {
      private final b a;
      private final a b;

      private a(b var1, a var2) {
         this.a = _snowman;
         this.b = _snowman;
      }

      public b a() {
         return this.a;
      }

      public a b() {
         return this.b;
      }
   }
}
