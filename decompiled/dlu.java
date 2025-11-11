import com.google.common.collect.ImmutableList;
import java.util.Arrays;
import java.util.List;

public interface dlu {
   dlu a = new dlu() {
      @Override
      public int a(dfm var1, int var2, int var3) {
         return _snowman;
      }

      @Override
      public int a(dfm var1, int var2, int var3, int var4, int var5) {
         return _snowman;
      }

      @Override
      public int b(dfm var1, int var2, int var3, int var4, int var5) {
         return _snowman;
      }

      @Override
      public int c(dfm var1, int var2, int var3, int var4, int var5) {
         return _snowman;
      }

      @Override
      public int a() {
         return 0;
      }
   };

   static dlu a(dku var0, nu var1, int var2) {
      return b(_snowman, _snowman.b(_snowman, _snowman).stream().map(var1x -> new dlu.a(var1x, _snowman.a(var1x))).collect(ImmutableList.toImmutableList()));
   }

   static dlu a(dku var0, nu var1, int var2, int var3) {
      return b(_snowman, _snowman.b(_snowman, _snowman).stream().limit((long)_snowman).map(var1x -> new dlu.a(var1x, _snowman.a(var1x))).collect(ImmutableList.toImmutableList()));
   }

   static dlu a(dku var0, nr... var1) {
      return b(_snowman, Arrays.stream(_snowman).map(nr::f).map(var1x -> new dlu.a(var1x, _snowman.a(var1x))).collect(ImmutableList.toImmutableList()));
   }

   static dlu b(final dku var0, final List<dlu.a> var1) {
      return _snowman.isEmpty() ? a : new dlu() {
         @Override
         public int a(dfm var1x, int var2, int var3) {
            return this.a(_snowman, _snowman, _snowman, 9, 16777215);
         }

         @Override
         public int a(dfm var1x, int var2, int var3, int var4, int var5) {
            int _snowman = _snowman;

            for (dlu.a _snowmanx : _snowman) {
               _snowman.a(_snowman, _snowmanx.a, (float)(_snowman - _snowmanx.b / 2), (float)_snowman, _snowman);
               _snowman += _snowman;
            }

            return _snowman;
         }

         @Override
         public int b(dfm var1x, int var2, int var3, int var4, int var5) {
            int _snowman = _snowman;

            for (dlu.a _snowmanx : _snowman) {
               _snowman.a(_snowman, _snowmanx.a, (float)_snowman, (float)_snowman, _snowman);
               _snowman += _snowman;
            }

            return _snowman;
         }

         @Override
         public int c(dfm var1x, int var2, int var3, int var4, int var5) {
            int _snowman = _snowman;

            for (dlu.a _snowmanx : _snowman) {
               _snowman.b(_snowman, _snowmanx.a, (float)_snowman, (float)_snowman, _snowman);
               _snowman += _snowman;
            }

            return _snowman;
         }

         @Override
         public int a() {
            return _snowman.size();
         }
      };
   }

   int a(dfm var1, int var2, int var3);

   int a(dfm var1, int var2, int var3, int var4, int var5);

   int b(dfm var1, int var2, int var3, int var4, int var5);

   int c(dfm var1, int var2, int var3, int var4, int var5);

   int a();

   public static class a {
      private final afa a;
      private final int b;

      private a(afa var1, int var2) {
         this.a = _snowman;
         this.b = _snowman;
      }
   }
}
