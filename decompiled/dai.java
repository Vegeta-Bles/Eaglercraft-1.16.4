import com.google.common.collect.Lists;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;
import org.apache.commons.lang3.ArrayUtils;

public abstract class dai implements daj {
   protected final dbo[] c;
   private final Predicate<cyv> a;

   protected dai(dbo[] var1) {
      this.c = _snowman;
      this.a = dbq.a(_snowman);
   }

   public final bmb b(bmb var1, cyv var2) {
      return this.a.test(_snowman) ? this.a(_snowman, _snowman) : _snowman;
   }

   protected abstract bmb a(bmb var1, cyv var2);

   @Override
   public void a(czg var1) {
      daj.super.a(_snowman);

      for (int _snowman = 0; _snowman < this.c.length; _snowman++) {
         this.c[_snowman].a(_snowman.b(".conditions[" + _snowman + "]"));
      }
   }

   protected static dai.a<?> a(Function<dbo[], daj> var0) {
      return new dai.b(_snowman);
   }

   public abstract static class a<T extends dai.a<T>> implements daj.a, dbh<T> {
      private final List<dbo> a = Lists.newArrayList();

      public a() {
      }

      public T a(dbo.a var1) {
         this.a.add(_snowman.build());
         return this.d();
      }

      public final T f() {
         return this.d();
      }

      protected abstract T d();

      protected dbo[] g() {
         return this.a.toArray(new dbo[0]);
      }
   }

   static final class b extends dai.a<dai.b> {
      private final Function<dbo[], daj> a;

      public b(Function<dbo[], daj> var1) {
         this.a = _snowman;
      }

      protected dai.b a() {
         return this;
      }

      @Override
      public daj b() {
         return this.a.apply(this.g());
      }
   }

   public abstract static class c<T extends dai> implements cze<T> {
      public c() {
      }

      public void a(JsonObject var1, T var2, JsonSerializationContext var3) {
         if (!ArrayUtils.isEmpty(_snowman.c)) {
            _snowman.add("conditions", _snowman.serialize(_snowman.c));
         }
      }

      public final T b(JsonObject var1, JsonDeserializationContext var2) {
         dbo[] _snowman = afd.a(_snowman, "conditions", new dbo[0], _snowman, dbo[].class);
         return this.b(_snowman, _snowman, _snowman);
      }

      public abstract T b(JsonObject var1, JsonDeserializationContext var2, dbo[] var3);
   }
}
