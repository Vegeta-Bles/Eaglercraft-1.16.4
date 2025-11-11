import com.google.common.collect.Lists;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public class bre<T> implements bso<T> {
   private final List<bre.a<T>> a;
   private final Function<T, vk> b;

   public bre(Function<T, vk> var1, List<bsp<T>> var2, long var3) {
      this(_snowman, _snowman.stream().map(var2x -> new bre.a(var2x.b(), var2x.a, (int)(var2x.b - _snowman), var2x.c)).collect(Collectors.toList()));
   }

   private bre(Function<T, vk> var1, List<bre.a<T>> var2) {
      this.a = _snowman;
      this.b = _snowman;
   }

   @Override
   public boolean a(fx var1, T var2) {
      return false;
   }

   @Override
   public void a(fx var1, T var2, int var3, bsq var4) {
      this.a.add(new bre.a<>(_snowman, _snowman, _snowman, _snowman));
   }

   @Override
   public boolean b(fx var1, T var2) {
      return false;
   }

   public mj b() {
      mj _snowman = new mj();

      for (bre.a<T> _snowmanx : this.a) {
         md _snowmanxx = new md();
         _snowmanxx.a("i", this.b.apply(_snowmanx.d).toString());
         _snowmanxx.b("x", _snowmanx.a.u());
         _snowmanxx.b("y", _snowmanx.a.v());
         _snowmanxx.b("z", _snowmanx.a.w());
         _snowmanxx.b("t", _snowmanx.b);
         _snowmanxx.b("p", _snowmanx.c.a());
         _snowman.add(_snowmanxx);
      }

      return _snowman;
   }

   public static <T> bre<T> a(mj var0, Function<T, vk> var1, Function<vk, T> var2) {
      List<bre.a<T>> _snowman = Lists.newArrayList();

      for (int _snowmanx = 0; _snowmanx < _snowman.size(); _snowmanx++) {
         md _snowmanxx = _snowman.a(_snowmanx);
         T _snowmanxxx = _snowman.apply(new vk(_snowmanxx.l("i")));
         if (_snowmanxxx != null) {
            fx _snowmanxxxx = new fx(_snowmanxx.h("x"), _snowmanxx.h("y"), _snowmanxx.h("z"));
            _snowman.add(new bre.a<>(_snowmanxxx, _snowmanxxxx, _snowmanxx.h("t"), bsq.a(_snowmanxx.h("p"))));
         }
      }

      return new bre<>(_snowman, _snowman);
   }

   public void a(bso<T> var1) {
      this.a.forEach(var1x -> _snowman.a(var1x.a, var1x.d, var1x.b, var1x.c));
   }

   static class a<T> {
      private final T d;
      public final fx a;
      public final int b;
      public final bsq c;

      private a(T var1, fx var2, int var3, bsq var4) {
         this.d = _snowman;
         this.a = _snowman;
         this.b = _snowman;
         this.c = _snowman;
      }

      @Override
      public String toString() {
         return this.d + ": " + this.a + ", " + this.b + ", " + this.c;
      }
   }
}
