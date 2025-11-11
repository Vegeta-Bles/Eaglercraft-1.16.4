import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Sets;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import java.util.Set;

public class czz extends dai {
   private final buo a;
   private final Set<cfj<?>> b;

   private czz(dbo[] var1, buo var2, Set<cfj<?>> var3) {
      super(_snowman);
      this.a = _snowman;
      this.b = _snowman;
   }

   @Override
   public dak b() {
      return dal.v;
   }

   @Override
   public Set<daz<?>> a() {
      return ImmutableSet.of(dbc.g);
   }

   @Override
   protected bmb a(bmb var1, cyv var2) {
      ceh _snowman = _snowman.c(dbc.g);
      if (_snowman != null) {
         md _snowmanx = _snowman.p();
         md _snowmanxx;
         if (_snowmanx.c("BlockStateTag", 10)) {
            _snowmanxx = _snowmanx.p("BlockStateTag");
         } else {
            _snowmanxx = new md();
            _snowmanx.a("BlockStateTag", _snowmanxx);
         }

         this.b.stream().filter(_snowman::b).forEach(var2x -> _snowman.a(var2x.f(), a(_snowman, (cfj<?>)var2x)));
      }

      return _snowman;
   }

   public static czz.a a(buo var0) {
      return new czz.a(_snowman);
   }

   private static <T extends Comparable<T>> String a(ceh var0, cfj<T> var1) {
      T _snowman = _snowman.c(_snowman);
      return _snowman.a(_snowman);
   }

   public static class a extends dai.a<czz.a> {
      private final buo a;
      private final Set<cfj<?>> b = Sets.newHashSet();

      private a(buo var1) {
         this.a = _snowman;
      }

      public czz.a a(cfj<?> var1) {
         if (!this.a.m().d().contains(_snowman)) {
            throw new IllegalStateException("Property " + _snowman + " is not present on block " + this.a);
         } else {
            this.b.add(_snowman);
            return this;
         }
      }

      protected czz.a a() {
         return this;
      }

      @Override
      public daj b() {
         return new czz(this.g(), this.a, this.b);
      }
   }

   public static class b extends dai.c<czz> {
      public b() {
      }

      public void a(JsonObject var1, czz var2, JsonSerializationContext var3) {
         super.a(_snowman, _snowman, _snowman);
         _snowman.addProperty("block", gm.Q.b(_snowman.a).toString());
         JsonArray _snowman = new JsonArray();
         _snowman.b.forEach(var1x -> _snowman.add(var1x.f()));
         _snowman.add("properties", _snowman);
      }

      public czz a(JsonObject var1, JsonDeserializationContext var2, dbo[] var3) {
         vk _snowman = new vk(afd.h(_snowman, "block"));
         buo _snowmanx = gm.Q.b(_snowman).orElseThrow(() -> new IllegalArgumentException("Can't find block " + _snowman));
         cei<buo, ceh> _snowmanxx = _snowmanx.m();
         Set<cfj<?>> _snowmanxxx = Sets.newHashSet();
         JsonArray _snowmanxxxx = afd.a(_snowman, "properties", null);
         if (_snowmanxxxx != null) {
            _snowmanxxxx.forEach(var2x -> _snowman.add(_snowman.a(afd.a(var2x, "property"))));
         }

         return new czz(_snowman, _snowmanx, _snowmanxxx);
      }
   }
}
