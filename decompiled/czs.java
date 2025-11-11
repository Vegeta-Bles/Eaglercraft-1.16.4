import com.google.common.collect.Lists;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import org.apache.commons.lang3.ArrayUtils;

public abstract class czs extends czq {
   protected final int c;
   protected final int e;
   protected final daj[] f;
   private final BiFunction<bmb, cyv, bmb> g;
   private final czp h = new czs.c() {
      @Override
      public void a(Consumer<bmb> var1, cyv var2) {
         czs.this.a(daj.a(czs.this.g, _snowman, _snowman), _snowman);
      }
   };

   protected czs(int var1, int var2, dbo[] var3, daj[] var4) {
      super(_snowman);
      this.c = _snowman;
      this.e = _snowman;
      this.f = _snowman;
      this.g = dal.a(_snowman);
   }

   @Override
   public void a(czg var1) {
      super.a(_snowman);

      for (int _snowman = 0; _snowman < this.f.length; _snowman++) {
         this.f[_snowman].a(_snowman.b(".functions[" + _snowman + "]"));
      }
   }

   protected abstract void a(Consumer<bmb> var1, cyv var2);

   @Override
   public boolean expand(cyv var1, Consumer<czp> var2) {
      if (this.a(_snowman)) {
         _snowman.accept(this.h);
         return true;
      } else {
         return false;
      }
   }

   public static czs.a<?> a(czs.d var0) {
      return new czs.b(_snowman);
   }

   public abstract static class a<T extends czs.a<T>> extends czq.a<T> implements dag<T> {
      protected int a = 1;
      protected int b = 0;
      private final List<daj> c = Lists.newArrayList();

      public a() {
      }

      public T a(daj.a var1) {
         this.c.add(_snowman.b());
         return this.d();
      }

      protected daj[] a() {
         return this.c.toArray(new daj[0]);
      }

      public T a(int var1) {
         this.a = _snowman;
         return this.d();
      }

      public T b(int var1) {
         this.b = _snowman;
         return this.d();
      }
   }

   static class b extends czs.a<czs.b> {
      private final czs.d c;

      public b(czs.d var1) {
         this.c = _snowman;
      }

      protected czs.b g() {
         return this;
      }

      @Override
      public czq b() {
         return this.c.build(this.a, this.b, this.f(), this.a());
      }
   }

   public abstract class c implements czp {
      protected c() {
      }

      @Override
      public int a(float var1) {
         return Math.max(afm.d((float)czs.this.c + (float)czs.this.e * _snowman), 0);
      }
   }

   @FunctionalInterface
   public interface d {
      czs build(int var1, int var2, dbo[] var3, daj[] var4);
   }

   public abstract static class e<T extends czs> extends czq.b<T> {
      public e() {
      }

      public void a(JsonObject var1, T var2, JsonSerializationContext var3) {
         if (_snowman.c != 1) {
            _snowman.addProperty("weight", _snowman.c);
         }

         if (_snowman.e != 0) {
            _snowman.addProperty("quality", _snowman.e);
         }

         if (!ArrayUtils.isEmpty(_snowman.f)) {
            _snowman.add("functions", _snowman.serialize(_snowman.f));
         }
      }

      public final T a(JsonObject var1, JsonDeserializationContext var2, dbo[] var3) {
         int _snowman = afd.a(_snowman, "weight", 1);
         int _snowmanx = afd.a(_snowman, "quality", 0);
         daj[] _snowmanxx = afd.a(_snowman, "functions", new daj[0], _snowman, daj[].class);
         return this.b(_snowman, _snowman, _snowman, _snowmanx, _snowman, _snowmanxx);
      }

      protected abstract T b(JsonObject var1, JsonDeserializationContext var2, int var3, int var4, dbo[] var5, daj[] var6);
   }
}
