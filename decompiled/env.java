import com.google.common.collect.Lists;
import java.util.List;
import java.util.Random;
import javax.annotation.Nullable;

public class env implements enw<emq> {
   private final List<enw<emq>> a = Lists.newArrayList();
   private final Random b = new Random();
   private final vk c;
   @Nullable
   private final nr d;

   public env(vk var1, @Nullable String var2) {
      this.c = _snowman;
      this.d = _snowman == null ? null : new of(_snowman);
   }

   @Override
   public int e() {
      int _snowman = 0;

      for (enw<emq> _snowmanx : this.a) {
         _snowman += _snowmanx.e();
      }

      return _snowman;
   }

   public emq a() {
      int _snowman = this.e();
      if (!this.a.isEmpty() && _snowman != 0) {
         int _snowmanx = this.b.nextInt(_snowman);

         for (enw<emq> _snowmanxx : this.a) {
            _snowmanx -= _snowmanxx.e();
            if (_snowmanx < 0) {
               return _snowmanxx.k();
            }
         }

         return enu.a;
      } else {
         return enu.a;
      }
   }

   public void a(enw<emq> var1) {
      this.a.add(_snowman);
   }

   @Nullable
   public nr c() {
      return this.d;
   }

   @Override
   public void a(enr var1) {
      for (enw<emq> _snowman : this.a) {
         _snowman.a(_snowman);
      }
   }
}
