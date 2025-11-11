import com.google.common.collect.ImmutableSet;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import java.util.Set;

public class daa extends dai {
   private final daa.a a;

   private daa(dbo[] var1, daa.a var2) {
      super(_snowman);
      this.a = _snowman;
   }

   @Override
   public dak b() {
      return dal.m;
   }

   @Override
   public Set<daz<?>> a() {
      return ImmutableSet.of(this.a.f);
   }

   @Override
   public bmb a(bmb var1, cyv var2) {
      Object _snowman = _snowman.c(this.a.f);
      if (_snowman instanceof aoy) {
         aoy _snowmanx = (aoy)_snowman;
         if (_snowmanx.S()) {
            _snowman.a(_snowmanx.d());
         }
      }

      return _snowman;
   }

   public static dai.a<?> a(daa.a var0) {
      return a(var1 -> new daa(var1, _snowman));
   }

   public static enum a {
      a("this", dbc.a),
      b("killer", dbc.d),
      c("killer_player", dbc.b),
      d("block_entity", dbc.h);

      public final String e;
      public final daz<?> f;

      private a(String var3, daz<?> var4) {
         this.e = _snowman;
         this.f = _snowman;
      }

      public static daa.a a(String var0) {
         for (daa.a _snowman : values()) {
            if (_snowman.e.equals(_snowman)) {
               return _snowman;
            }
         }

         throw new IllegalArgumentException("Invalid name source " + _snowman);
      }
   }

   public static class b extends dai.c<daa> {
      public b() {
      }

      public void a(JsonObject var1, daa var2, JsonSerializationContext var3) {
         super.a(_snowman, _snowman, _snowman);
         _snowman.addProperty("source", _snowman.a.e);
      }

      public daa a(JsonObject var1, JsonDeserializationContext var2, dbo[] var3) {
         daa.a _snowman = daa.a.a(afd.h(_snowman, "source"));
         return new daa(_snowman, _snowman);
      }
   }
}
