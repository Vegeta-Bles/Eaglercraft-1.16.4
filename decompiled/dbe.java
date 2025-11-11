import com.google.common.collect.Lists;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import java.util.List;
import java.util.function.Predicate;

public class dbe implements dbo {
   private final dbo[] a;
   private final Predicate<cyv> b;

   private dbe(dbo[] var1) {
      this.a = _snowman;
      this.b = dbq.b(_snowman);
   }

   @Override
   public dbp b() {
      return dbq.b;
   }

   public final boolean a(cyv var1) {
      return this.b.test(_snowman);
   }

   @Override
   public void a(czg var1) {
      dbo.super.a(_snowman);

      for (int _snowman = 0; _snowman < this.a.length; _snowman++) {
         this.a[_snowman].a(_snowman.b(".term[" + _snowman + "]"));
      }
   }

   public static dbe.a a(dbo.a... var0) {
      return new dbe.a(_snowman);
   }

   public static class a implements dbo.a {
      private final List<dbo> a = Lists.newArrayList();

      public a(dbo.a... var1) {
         for (dbo.a _snowman : _snowman) {
            this.a.add(_snowman.build());
         }
      }

      @Override
      public dbe.a a(dbo.a var1) {
         this.a.add(_snowman.build());
         return this;
      }

      @Override
      public dbo build() {
         return new dbe(this.a.toArray(new dbo[0]));
      }
   }

   public static class b implements cze<dbe> {
      public b() {
      }

      public void a(JsonObject var1, dbe var2, JsonSerializationContext var3) {
         _snowman.add("terms", _snowman.serialize(_snowman.a));
      }

      public dbe b(JsonObject var1, JsonDeserializationContext var2) {
         dbo[] _snowman = afd.a(_snowman, "terms", _snowman, dbo[].class);
         return new dbe(_snowman);
      }
   }
}
