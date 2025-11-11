import java.util.function.BiPredicate;
import java.util.function.Function;

public class bwc {
   public static <S extends ccj> bwc.c<S> a(
      cck<S> var0, Function<ceh, bwc.a> var1, Function<ceh, gc> var2, cfb var3, ceh var4, bry var5, fx var6, BiPredicate<bry, fx> var7
   ) {
      S _snowman = _snowman.a(_snowman, _snowman);
      if (_snowman == null) {
         return bwc.b::b;
      } else if (_snowman.test(_snowman, _snowman)) {
         return bwc.b::b;
      } else {
         bwc.a _snowmanx = _snowman.apply(_snowman);
         boolean _snowmanxx = _snowmanx == bwc.a.a;
         boolean _snowmanxxx = _snowmanx == bwc.a.b;
         if (_snowmanxx) {
            return new bwc.c.b<>(_snowman);
         } else {
            fx _snowmanxxxx = _snowman.a(_snowman.apply(_snowman));
            ceh _snowmanxxxxx = _snowman.d_(_snowmanxxxx);
            if (_snowmanxxxxx.a(_snowman.b())) {
               bwc.a _snowmanxxxxxx = _snowman.apply(_snowmanxxxxx);
               if (_snowmanxxxxxx != bwc.a.a && _snowmanx != _snowmanxxxxxx && _snowmanxxxxx.c(_snowman) == _snowman.c(_snowman)) {
                  if (_snowman.test(_snowman, _snowmanxxxx)) {
                     return bwc.b::b;
                  }

                  S _snowmanxxxxxxx = _snowman.a(_snowman, _snowmanxxxx);
                  if (_snowmanxxxxxxx != null) {
                     S _snowmanxxxxxxxx = _snowmanxxx ? _snowman : _snowmanxxxxxxx;
                     S _snowmanxxxxxxxxx = _snowmanxxx ? _snowmanxxxxxxx : _snowman;
                     return new bwc.c.a<>(_snowmanxxxxxxxx, _snowmanxxxxxxxxx);
                  }
               }
            }

            return new bwc.c.b<>(_snowman);
         }
      }
   }

   public static enum a {
      a,
      b,
      c;

      private a() {
      }
   }

   public interface b<S, T> {
      T a(S var1, S var2);

      T a(S var1);

      T b();
   }

   public interface c<S> {
      <T> T apply(bwc.b<? super S, T> var1);

      public static final class a<S> implements bwc.c<S> {
         private final S a;
         private final S b;

         public a(S var1, S var2) {
            this.a = _snowman;
            this.b = _snowman;
         }

         @Override
         public <T> T apply(bwc.b<? super S, T> var1) {
            return _snowman.a(this.a, this.b);
         }
      }

      public static final class b<S> implements bwc.c<S> {
         private final S a;

         public b(S var1) {
            this.a = _snowman;
         }

         @Override
         public <T> T apply(bwc.b<? super S, T> var1) {
            return _snowman.a(this.a);
         }
      }
   }
}
