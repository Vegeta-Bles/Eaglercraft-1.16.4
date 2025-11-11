import com.google.common.collect.ImmutableList;
import com.google.common.collect.UnmodifiableIterator;
import java.util.List;

public class dop extends dot {
   private final nu a;
   private final ImmutableList<dop.a> b;
   private dlu c = dlu.a;
   private int p;
   private int q;

   protected dop(nr var1, List<nu> var2, ImmutableList<dop.a> var3) {
      super(_snowman);
      this.a = nu.a(_snowman);
      this.b = _snowman;
   }

   @Override
   public String ax_() {
      return super.ax_() + ". " + this.a.getString();
   }

   @Override
   public void b(djz var1, int var2, int var3) {
      super.b(_snowman, _snowman, _snowman);
      UnmodifiableIterator var4 = this.b.iterator();

      while (var4.hasNext()) {
         dop.a _snowman = (dop.a)var4.next();
         this.q = Math.max(this.q, 20 + this.o.a(_snowman.a) + 20);
      }

      int _snowman = 5 + this.q + 5;
      int _snowmanx = _snowman * this.b.size();
      this.c = dlu.a(this.o, this.a, _snowmanx);
      int _snowmanxx = this.c.a() * 9;
      this.p = (int)((double)_snowman / 2.0 - (double)_snowmanxx / 2.0);
      int _snowmanxxx = this.p + _snowmanxx + 9 * 2;
      int _snowmanxxxx = (int)((double)_snowman / 2.0 - (double)_snowmanx / 2.0);

      for (UnmodifiableIterator var9 = this.b.iterator(); var9.hasNext(); _snowmanxxxx += _snowman) {
         dop.a _snowmanxxxxx = (dop.a)var9.next();
         this.a(new dlj(_snowmanxxxx, _snowmanxxx, this.q, 20, _snowmanxxxxx.a, _snowmanxxxxx.b));
      }
   }

   @Override
   public void a(dfm var1, int var2, int var3, float var4) {
      this.e(0);
      a(_snowman, this.o, this.d, this.k / 2, this.p - 9 * 2, -1);
      this.c.a(_snowman, this.k / 2, this.p);
      super.a(_snowman, _snowman, _snowman, _snowman);
   }

   @Override
   public boolean as_() {
      return false;
   }

   public static final class a {
      private final nr a;
      private final dlj.a b;

      public a(nr var1, dlj.a var2) {
         this.a = _snowman;
         this.b = _snowman;
      }
   }
}
