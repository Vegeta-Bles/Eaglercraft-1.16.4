import com.google.common.collect.Lists;
import java.util.List;
import javax.annotation.Nullable;

public class eoe {
   private final djz a;
   @Nullable
   private eof b;
   private List<eoe.a> c = Lists.newArrayList();

   public eoe(djz var1) {
      this.a = _snowman;
   }

   public void a(dzk var1) {
      if (this.b != null) {
         this.b.a(_snowman);
      }
   }

   public void a(double var1, double var3) {
      if (this.b != null) {
         this.b.a(_snowman, _snowman);
      }
   }

   public void a(@Nullable dwt var1, @Nullable dcl var2) {
      if (this.b != null && _snowman != null && _snowman != null) {
         this.b.a(_snowman, _snowman);
      }
   }

   public void a(dwt var1, fx var2, ceh var3, float var4) {
      if (this.b != null) {
         this.b.a(_snowman, _snowman, _snowman, _snowman);
      }
   }

   public void a() {
      if (this.b != null) {
         this.b.c();
      }
   }

   public void a(bmb var1) {
      if (this.b != null) {
         this.b.a(_snowman);
      }
   }

   public void b() {
      if (this.b != null) {
         this.b.b();
         this.b = null;
      }
   }

   public void c() {
      if (this.b != null) {
         this.b();
      }

      this.b = this.a.k.D.a(this);
   }

   public void a(dms var1, int var2) {
      this.c.add(new eoe.a(_snowman, _snowman));
      this.a.an().a(_snowman);
   }

   public void a(dms var1) {
      this.c.removeIf(var1x -> var1x.a == _snowman);
      _snowman.b();
   }

   public void d() {
      this.c.removeIf(var0 -> var0.a());
      if (this.b != null) {
         if (this.a.r != null) {
            this.b.a();
         } else {
            this.b();
         }
      } else if (this.a.r != null) {
         this.c();
      }
   }

   public void a(eog var1) {
      this.a.k.D = _snowman;
      this.a.k.b();
      if (this.b != null) {
         this.b.b();
         this.b = _snowman.a(this);
      }
   }

   public djz e() {
      return this.a;
   }

   public bru f() {
      return this.a.q == null ? bru.a : this.a.q.l();
   }

   public static nr a(String var0) {
      return new nw("key." + _snowman).a(k.r);
   }

   static final class a {
      private final dms a;
      private final int b;
      private int c;

      private a(dms var1, int var2) {
         this.a = _snowman;
         this.b = _snowman;
      }

      private boolean a() {
         this.a.a(Math.min((float)(++this.c) / (float)this.b, 1.0F));
         if (this.c > this.b) {
            this.a.b();
            return true;
         } else {
            return false;
         }
      }
   }
}
