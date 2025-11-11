import com.google.common.collect.Lists;
import java.util.List;
import java.util.UUID;
import java.util.function.Predicate;
import java.util.stream.Stream;
import javax.annotation.Nullable;

public interface bro {
   List<aqa> a(@Nullable aqa var1, dci var2, @Nullable Predicate<? super aqa> var3);

   <T extends aqa> List<T> a(Class<? extends T> var1, dci var2, @Nullable Predicate<? super T> var3);

   default <T extends aqa> List<T> b(Class<? extends T> var1, dci var2, @Nullable Predicate<? super T> var3) {
      return this.a(_snowman, _snowman, _snowman);
   }

   List<? extends bfw> x();

   default List<aqa> a(@Nullable aqa var1, dci var2) {
      return this.a(_snowman, _snowman, aqd.g);
   }

   default boolean a(@Nullable aqa var1, ddh var2) {
      if (_snowman.b()) {
         return true;
      } else {
         for (aqa _snowman : this.a(_snowman, _snowman.a())) {
            if (!_snowman.y && _snowman.i && (_snowman == null || !_snowman.x(_snowman)) && dde.c(_snowman, dde.a(_snowman.cc()), dcr.i)) {
               return false;
            }
         }

         return true;
      }
   }

   default <T extends aqa> List<T> a(Class<? extends T> var1, dci var2) {
      return this.a(_snowman, _snowman, aqd.g);
   }

   default <T extends aqa> List<T> b(Class<? extends T> var1, dci var2) {
      return this.b(_snowman, _snowman, aqd.g);
   }

   default Stream<ddh> c(@Nullable aqa var1, dci var2, Predicate<aqa> var3) {
      if (_snowman.a() < 1.0E-7) {
         return Stream.empty();
      } else {
         dci _snowman = _snowman.g(1.0E-7);
         return this.a(_snowman, _snowman, _snowman.and(var2x -> var2x.cc().c(_snowman) && (_snowman == null ? var2x.aZ() : _snowman.j(var2x)))).stream().map(aqa::cc).map(dde::a);
      }
   }

   @Nullable
   default bfw a(double var1, double var3, double var5, double var7, @Nullable Predicate<aqa> var9) {
      double _snowman = -1.0;
      bfw _snowmanx = null;

      for (bfw _snowmanxx : this.x()) {
         if (_snowman == null || _snowman.test(_snowmanxx)) {
            double _snowmanxxx = _snowmanxx.h(_snowman, _snowman, _snowman);
            if ((_snowman < 0.0 || _snowmanxxx < _snowman * _snowman) && (_snowman == -1.0 || _snowmanxxx < _snowman)) {
               _snowman = _snowmanxxx;
               _snowmanx = _snowmanxx;
            }
         }
      }

      return _snowmanx;
   }

   @Nullable
   default bfw a(aqa var1, double var2) {
      return this.a(_snowman.cD(), _snowman.cE(), _snowman.cH(), _snowman, false);
   }

   @Nullable
   default bfw a(double var1, double var3, double var5, double var7, boolean var9) {
      Predicate<aqa> _snowman = _snowman ? aqd.e : aqd.g;
      return this.a(_snowman, _snowman, _snowman, _snowman, _snowman);
   }

   default boolean a(double var1, double var3, double var5, double var7) {
      for (bfw _snowman : this.x()) {
         if (aqd.g.test(_snowman) && aqd.b.test(_snowman)) {
            double _snowmanx = _snowman.h(_snowman, _snowman, _snowman);
            if (_snowman < 0.0 || _snowmanx < _snowman * _snowman) {
               return true;
            }
         }
      }

      return false;
   }

   @Nullable
   default bfw a(azg var1, aqm var2) {
      return this.a(this.x(), _snowman, _snowman, _snowman.cD(), _snowman.cE(), _snowman.cH());
   }

   @Nullable
   default bfw a(azg var1, aqm var2, double var3, double var5, double var7) {
      return this.a(this.x(), _snowman, _snowman, _snowman, _snowman, _snowman);
   }

   @Nullable
   default bfw a(azg var1, double var2, double var4, double var6) {
      return this.a(this.x(), _snowman, null, _snowman, _snowman, _snowman);
   }

   @Nullable
   default <T extends aqm> T a(Class<? extends T> var1, azg var2, @Nullable aqm var3, double var4, double var6, double var8, dci var10) {
      return this.a(this.a(_snowman, _snowman, null), _snowman, _snowman, _snowman, _snowman, _snowman);
   }

   @Nullable
   default <T extends aqm> T b(Class<? extends T> var1, azg var2, @Nullable aqm var3, double var4, double var6, double var8, dci var10) {
      return this.a(this.b(_snowman, _snowman, null), _snowman, _snowman, _snowman, _snowman, _snowman);
   }

   @Nullable
   default <T extends aqm> T a(List<? extends T> var1, azg var2, @Nullable aqm var3, double var4, double var6, double var8) {
      double _snowman = -1.0;
      T _snowmanx = null;

      for (T _snowmanxx : _snowman) {
         if (_snowman.a(_snowman, _snowmanxx)) {
            double _snowmanxxx = _snowmanxx.h(_snowman, _snowman, _snowman);
            if (_snowman == -1.0 || _snowmanxxx < _snowman) {
               _snowman = _snowmanxxx;
               _snowmanx = _snowmanxx;
            }
         }
      }

      return _snowmanx;
   }

   default List<bfw> a(azg var1, aqm var2, dci var3) {
      List<bfw> _snowman = Lists.newArrayList();

      for (bfw _snowmanx : this.x()) {
         if (_snowman.e(_snowmanx.cD(), _snowmanx.cE(), _snowmanx.cH()) && _snowman.a(_snowman, _snowmanx)) {
            _snowman.add(_snowmanx);
         }
      }

      return _snowman;
   }

   default <T extends aqm> List<T> a(Class<? extends T> var1, azg var2, aqm var3, dci var4) {
      List<T> _snowman = this.a(_snowman, _snowman, null);
      List<T> _snowmanx = Lists.newArrayList();

      for (T _snowmanxx : _snowman) {
         if (_snowman.a(_snowman, _snowmanxx)) {
            _snowmanx.add(_snowmanxx);
         }
      }

      return _snowmanx;
   }

   @Nullable
   default bfw b(UUID var1) {
      for (int _snowman = 0; _snowman < this.x().size(); _snowman++) {
         bfw _snowmanx = this.x().get(_snowman);
         if (_snowman.equals(_snowmanx.bS())) {
            return _snowmanx;
         }
      }

      return null;
   }
}
