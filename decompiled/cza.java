import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableMap.Builder;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import java.util.Collections;
import java.util.Map;
import java.util.Set;
import java.util.function.Predicate;
import javax.annotation.Nullable;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class cza extends acj {
   private static final Logger a = LogManager.getLogger();
   private static final Gson b = cys.a().create();
   private Map<vk, dbo> c = ImmutableMap.of();

   public cza() {
      super(b, "predicates");
   }

   @Nullable
   public dbo a(vk var1) {
      return this.c.get(_snowman);
   }

   protected void a(Map<vk, JsonElement> var1, ach var2, anw var3) {
      Builder<vk, dbo> _snowman = ImmutableMap.builder();
      _snowman.forEach((var1x, var2x) -> {
         try {
            if (var2x.isJsonArray()) {
               dbo[] _snowmanx = (dbo[])b.fromJson(var2x, dbo[].class);
               _snowman.put(var1x, new cza.a(_snowmanx));
            } else {
               dbo _snowmanx = (dbo)b.fromJson(var2x, dbo.class);
               _snowman.put(var1x, _snowmanx);
            }
         } catch (Exception var4x) {
            a.error("Couldn't parse loot table {}", var1x, var4x);
         }
      });
      Map<vk, dbo> _snowmanx = _snowman.build();
      czg _snowmanxx = new czg(dbb.k, _snowmanx::get, var0 -> null);
      _snowmanx.forEach((var1x, var2x) -> var2x.a(_snowman.b("{" + var1x + "}", var1x)));
      _snowmanxx.a().forEach((var0, var1x) -> a.warn("Found validation problem in " + var0 + ": " + var1x));
      this.c = _snowmanx;
   }

   public Set<vk> a() {
      return Collections.unmodifiableSet(this.c.keySet());
   }

   static class a implements dbo {
      private final dbo[] a;
      private final Predicate<cyv> b;

      private a(dbo[] var1) {
         this.a = _snowman;
         this.b = dbq.a(_snowman);
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

      @Override
      public dbp b() {
         throw new UnsupportedOperationException();
      }
   }
}
