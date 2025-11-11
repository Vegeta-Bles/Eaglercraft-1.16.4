import com.google.common.collect.Lists;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import java.util.List;
import java.util.function.Predicate;
import org.apache.commons.lang3.ArrayUtils;

public abstract class czq implements czi {
   protected final dbo[] d;
   private final Predicate<cyv> c;

   protected czq(dbo[] var1) {
      this.d = _snowman;
      this.c = dbq.a(_snowman);
   }

   public void a(czg var1) {
      for (int _snowman = 0; _snowman < this.d.length; _snowman++) {
         this.d[_snowman].a(_snowman.b(".condition[" + _snowman + "]"));
      }
   }

   protected final boolean a(cyv var1) {
      return this.c.test(_snowman);
   }

   public abstract czr a();

   public abstract static class a<T extends czq.a<T>> implements dbh<T> {
      private final List<dbo> a = Lists.newArrayList();

      public a() {
      }

      protected abstract T d();

      public T a(dbo.a var1) {
         this.a.add(_snowman.build());
         return this.d();
      }

      public final T e() {
         return this.d();
      }

      protected dbo[] f() {
         return this.a.toArray(new dbo[0]);
      }

      public czh.a a(czq.a<?> var1) {
         return new czh.a(this, _snowman);
      }

      public abstract czq b();
   }

   public abstract static class b<T extends czq> implements cze<T> {
      public b() {
      }

      public final void b(JsonObject var1, T var2, JsonSerializationContext var3) {
         if (!ArrayUtils.isEmpty(_snowman.d)) {
            _snowman.add("conditions", _snowman.serialize(_snowman.d));
         }

         this.a(_snowman, _snowman, _snowman);
      }

      public final T b(JsonObject var1, JsonDeserializationContext var2) {
         dbo[] _snowman = afd.a(_snowman, "conditions", new dbo[0], _snowman, dbo[].class);
         return this.b(_snowman, _snowman, _snowman);
      }

      public abstract void a(JsonObject var1, T var2, JsonSerializationContext var3);

      public abstract T b(JsonObject var1, JsonDeserializationContext var2, dbo[] var3);
   }
}
