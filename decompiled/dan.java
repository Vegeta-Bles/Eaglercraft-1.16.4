import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSyntaxException;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import javax.annotation.Nullable;

public class dan extends dai {
   private final List<dan.b> a;

   private dan(dbo[] var1, List<dan.b> var2) {
      super(_snowman);
      this.a = ImmutableList.copyOf(_snowman);
   }

   @Override
   public dak b() {
      return dal.i;
   }

   @Override
   public bmb a(bmb var1, cyv var2) {
      Random _snowman = _snowman.a();

      for (dan.b _snowmanx : this.a) {
         UUID _snowmanxx = _snowmanx.e;
         if (_snowmanxx == null) {
            _snowmanxx = UUID.randomUUID();
         }

         aqf _snowmanxxx = x.a(_snowmanx.f, _snowman);
         _snowman.a(_snowmanx.b, new arj(_snowmanxx, _snowmanx.a, (double)_snowmanx.d.b(_snowman), _snowmanx.c), _snowmanxxx);
      }

      return _snowman;
   }

   static class b {
      private final String a;
      private final arg b;
      private final arj.a c;
      private final czd d;
      @Nullable
      private final UUID e;
      private final aqf[] f;

      private b(String var1, arg var2, arj.a var3, czd var4, aqf[] var5, @Nullable UUID var6) {
         this.a = _snowman;
         this.b = _snowman;
         this.c = _snowman;
         this.d = _snowman;
         this.e = _snowman;
         this.f = _snowman;
      }

      public JsonObject a(JsonSerializationContext var1) {
         JsonObject _snowman = new JsonObject();
         _snowman.addProperty("name", this.a);
         _snowman.addProperty("attribute", gm.af.b(this.b).toString());
         _snowman.addProperty("operation", a(this.c));
         _snowman.add("amount", _snowman.serialize(this.d));
         if (this.e != null) {
            _snowman.addProperty("id", this.e.toString());
         }

         if (this.f.length == 1) {
            _snowman.addProperty("slot", this.f[0].d());
         } else {
            JsonArray _snowmanx = new JsonArray();

            for (aqf _snowmanxx : this.f) {
               _snowmanx.add(new JsonPrimitive(_snowmanxx.d()));
            }

            _snowman.add("slot", _snowmanx);
         }

         return _snowman;
      }

      public static dan.b a(JsonObject var0, JsonDeserializationContext var1) {
         String _snowman = afd.h(_snowman, "name");
         vk _snowmanx = new vk(afd.h(_snowman, "attribute"));
         arg _snowmanxx = gm.af.a(_snowmanx);
         if (_snowmanxx == null) {
            throw new JsonSyntaxException("Unknown attribute: " + _snowmanx);
         } else {
            arj.a _snowmanxxx = a(afd.h(_snowman, "operation"));
            czd _snowmanxxxx = afd.a(_snowman, "amount", _snowman, czd.class);
            UUID _snowmanxxxxx = null;
            aqf[] _snowmanxxxxxx;
            if (afd.a(_snowman, "slot")) {
               _snowmanxxxxxx = new aqf[]{aqf.a(afd.h(_snowman, "slot"))};
            } else {
               if (!afd.d(_snowman, "slot")) {
                  throw new JsonSyntaxException("Invalid or missing attribute modifier slot; must be either string or array of strings.");
               }

               JsonArray _snowmanxxxxxxx = afd.u(_snowman, "slot");
               _snowmanxxxxxx = new aqf[_snowmanxxxxxxx.size()];
               int _snowmanxxxxxxxx = 0;

               for (JsonElement _snowmanxxxxxxxxx : _snowmanxxxxxxx) {
                  _snowmanxxxxxx[_snowmanxxxxxxxx++] = aqf.a(afd.a(_snowmanxxxxxxxxx, "slot"));
               }

               if (_snowmanxxxxxx.length == 0) {
                  throw new JsonSyntaxException("Invalid attribute modifier slot; must contain at least one entry.");
               }
            }

            if (_snowman.has("id")) {
               String _snowmanxxxxxxx = afd.h(_snowman, "id");

               try {
                  _snowmanxxxxx = UUID.fromString(_snowmanxxxxxxx);
               } catch (IllegalArgumentException var13) {
                  throw new JsonSyntaxException("Invalid attribute modifier id '" + _snowmanxxxxxxx + "' (must be UUID format, with dashes)");
               }
            }

            return new dan.b(_snowman, _snowmanxx, _snowmanxxx, _snowmanxxxx, _snowmanxxxxxx, _snowmanxxxxx);
         }
      }

      private static String a(arj.a var0) {
         switch (_snowman) {
            case a:
               return "addition";
            case b:
               return "multiply_base";
            case c:
               return "multiply_total";
            default:
               throw new IllegalArgumentException("Unknown operation " + _snowman);
         }
      }

      private static arj.a a(String var0) {
         switch (_snowman) {
            case "addition":
               return arj.a.a;
            case "multiply_base":
               return arj.a.b;
            case "multiply_total":
               return arj.a.c;
            default:
               throw new JsonSyntaxException("Unknown attribute modifier operation " + _snowman);
         }
      }
   }

   public static class d extends dai.c<dan> {
      public d() {
      }

      public void a(JsonObject var1, dan var2, JsonSerializationContext var3) {
         super.a(_snowman, _snowman, _snowman);
         JsonArray _snowman = new JsonArray();

         for (dan.b _snowmanx : _snowman.a) {
            _snowman.add(_snowmanx.a(_snowman));
         }

         _snowman.add("modifiers", _snowman);
      }

      public dan a(JsonObject var1, JsonDeserializationContext var2, dbo[] var3) {
         JsonArray _snowman = afd.u(_snowman, "modifiers");
         List<dan.b> _snowmanx = Lists.newArrayListWithExpectedSize(_snowman.size());

         for (JsonElement _snowmanxx : _snowman) {
            _snowmanx.add(dan.b.a(afd.m(_snowmanxx, "modifier"), _snowman));
         }

         if (_snowmanx.isEmpty()) {
            throw new JsonSyntaxException("Invalid attribute modifiers array; cannot be empty");
         } else {
            return new dan(_snowman, _snowmanx);
         }
      }
   }
}
