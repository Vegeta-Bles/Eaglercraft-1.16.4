import com.google.common.collect.ImmutableList;
import java.util.List;
import java.util.Optional;

public interface nu {
   Optional<afx> b = Optional.of(afx.a);
   nu c = new nu() {
      @Override
      public <T> Optional<T> a(nu.a<T> var1) {
         return Optional.empty();
      }

      @Override
      public <T> Optional<T> a(nu.b<T> var1, ob var2) {
         return Optional.empty();
      }
   };

   <T> Optional<T> a(nu.a<T> var1);

   <T> Optional<T> a(nu.b<T> var1, ob var2);

   static nu b(final String var0) {
      return new nu() {
         @Override
         public <T> Optional<T> a(nu.a<T> var1) {
            return _snowman.accept(_snowman);
         }

         @Override
         public <T> Optional<T> a(nu.b<T> var1, ob var2) {
            return _snowman.accept(_snowman, _snowman);
         }
      };
   }

   static nu a(final String var0, final ob var1) {
      return new nu() {
         @Override
         public <T> Optional<T> a(nu.a<T> var1x) {
            return _snowman.accept(_snowman);
         }

         @Override
         public <T> Optional<T> a(nu.b<T> var1x, ob var2) {
            return _snowman.accept(_snowman.a(_snowman), _snowman);
         }
      };
   }

   static nu a(nu... var0) {
      return a(ImmutableList.copyOf(_snowman));
   }

   static nu a(final List<nu> var0) {
      return new nu() {
         @Override
         public <T> Optional<T> a(nu.a<T> var1) {
            for (nu _snowman : _snowman) {
               Optional<T> _snowmanx = _snowman.a(_snowman);
               if (_snowmanx.isPresent()) {
                  return _snowmanx;
               }
            }

            return Optional.empty();
         }

         @Override
         public <T> Optional<T> a(nu.b<T> var1, ob var2) {
            for (nu _snowman : _snowman) {
               Optional<T> _snowmanx = _snowman.a(_snowman, _snowman);
               if (_snowmanx.isPresent()) {
                  return _snowmanx;
               }
            }

            return Optional.empty();
         }
      };
   }

   default String getString() {
      StringBuilder _snowman = new StringBuilder();
      this.a(var1x -> {
         _snowman.append(var1x);
         return Optional.empty();
      });
      return _snowman.toString();
   }

   public interface a<T> {
      Optional<T> accept(String var1);
   }

   public interface b<T> {
      Optional<T> accept(ob var1, String var2);
   }
}
