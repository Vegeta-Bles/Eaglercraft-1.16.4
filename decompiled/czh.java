import com.google.common.collect.Lists;
import java.util.List;
import org.apache.commons.lang3.ArrayUtils;

public class czh extends czj {
   czh(czq[] var1, dbo[] var2) {
      super(_snowman, _snowman);
   }

   @Override
   public czr a() {
      return czo.f;
   }

   @Override
   protected czi a(czi[] var1) {
      switch (_snowman.length) {
         case 0:
            return a;
         case 1:
            return _snowman[0];
         case 2:
            return _snowman[0].b(_snowman[1]);
         default:
            return (var1x, var2) -> {
               for (czi _snowman : _snowman) {
                  if (_snowman.expand(var1x, var2)) {
                     return true;
                  }
               }

               return false;
            };
      }
   }

   @Override
   public void a(czg var1) {
      super.a(_snowman);

      for (int _snowman = 0; _snowman < this.c.length - 1; _snowman++) {
         if (ArrayUtils.isEmpty(this.c[_snowman].d)) {
            _snowman.a("Unreachable entry!");
         }
      }
   }

   public static czh.a a(czq.a<?>... var0) {
      return new czh.a(_snowman);
   }

   public static class a extends czq.a<czh.a> {
      private final List<czq> a = Lists.newArrayList();

      public a(czq.a<?>... var1) {
         for (czq.a<?> _snowman : _snowman) {
            this.a.add(_snowman.b());
         }
      }

      protected czh.a a() {
         return this;
      }

      @Override
      public czh.a a(czq.a<?> var1) {
         this.a.add(_snowman.b());
         return this;
      }

      @Override
      public czq b() {
         return new czh(this.a.toArray(new czq[0]), this.f());
      }
   }
}
