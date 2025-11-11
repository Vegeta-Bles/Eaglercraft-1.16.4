import java.util.List;
import java.util.ListIterator;
import java.util.Optional;
import java.util.function.BooleanSupplier;
import java.util.function.Supplier;
import javax.annotation.Nullable;

public interface dmh extends dmi {
   List<? extends dmi> au_();

   default Optional<dmi> d(double var1, double var3) {
      for (dmi _snowman : this.au_()) {
         if (_snowman.b(_snowman, _snowman)) {
            return Optional.of(_snowman);
         }
      }

      return Optional.empty();
   }

   @Override
   default boolean a(double var1, double var3, int var5) {
      for (dmi _snowman : this.au_()) {
         if (_snowman.a(_snowman, _snowman, _snowman)) {
            this.a(_snowman);
            if (_snowman == 0) {
               this.b_(true);
            }

            return true;
         }
      }

      return false;
   }

   @Override
   default boolean c(double var1, double var3, int var5) {
      this.b_(false);
      return this.d(_snowman, _snowman).filter(var5x -> var5x.c(_snowman, _snowman, _snowman)).isPresent();
   }

   @Override
   default boolean a(double var1, double var3, int var5, double var6, double var8) {
      return this.aw_() != null && this.av_() && _snowman == 0 ? this.aw_().a(_snowman, _snowman, _snowman, _snowman, _snowman) : false;
   }

   boolean av_();

   void b_(boolean var1);

   @Override
   default boolean a(double var1, double var3, double var5) {
      return this.d(_snowman, _snowman).filter(var6 -> var6.a(_snowman, _snowman, _snowman)).isPresent();
   }

   @Override
   default boolean a(int var1, int var2, int var3) {
      return this.aw_() != null && this.aw_().a(_snowman, _snowman, _snowman);
   }

   @Override
   default boolean b(int var1, int var2, int var3) {
      return this.aw_() != null && this.aw_().b(_snowman, _snowman, _snowman);
   }

   @Override
   default boolean a(char var1, int var2) {
      return this.aw_() != null && this.aw_().a(_snowman, _snowman);
   }

   @Nullable
   dmi aw_();

   void a(@Nullable dmi var1);

   default void b(@Nullable dmi var1) {
      this.a(_snowman);
      _snowman.c_(true);
   }

   default void c(@Nullable dmi var1) {
      this.a(_snowman);
   }

   @Override
   default boolean c_(boolean var1) {
      dmi _snowman = this.aw_();
      boolean _snowmanx = _snowman != null;
      if (_snowmanx && _snowman.c_(_snowman)) {
         return true;
      } else {
         List<? extends dmi> _snowmanxx = this.au_();
         int _snowmanxxx = _snowmanxx.indexOf(_snowman);
         int _snowmanxxxx;
         if (_snowmanx && _snowmanxxx >= 0) {
            _snowmanxxxx = _snowmanxxx + (_snowman ? 1 : 0);
         } else if (_snowman) {
            _snowmanxxxx = 0;
         } else {
            _snowmanxxxx = _snowmanxx.size();
         }

         ListIterator<? extends dmi> _snowmanxxxxx = _snowmanxx.listIterator(_snowmanxxxx);
         BooleanSupplier _snowmanxxxxxx = _snowman ? _snowmanxxxxx::hasNext : _snowmanxxxxx::hasPrevious;
         Supplier<? extends dmi> _snowmanxxxxxxx = _snowman ? _snowmanxxxxx::next : _snowmanxxxxx::previous;

         while (_snowmanxxxxxx.getAsBoolean()) {
            dmi _snowmanxxxxxxxx = _snowmanxxxxxxx.get();
            if (_snowmanxxxxxxxx.c_(_snowman)) {
               this.a(_snowmanxxxxxxxx);
               return true;
            }
         }

         this.a(null);
         return false;
      }
   }
}
