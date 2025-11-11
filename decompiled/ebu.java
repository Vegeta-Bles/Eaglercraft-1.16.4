import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.Streams;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Set;
import java.util.Map.Entry;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class ebu {
   private final ebq a;
   private final ebn b;

   public ebu(ebq var1, ebn var2) {
      if (_snowman == null) {
         throw new IllegalArgumentException("Missing condition for selector");
      } else if (_snowman == null) {
         throw new IllegalArgumentException("Missing variant for selector");
      } else {
         this.a = _snowman;
         this.b = _snowman;
      }
   }

   public ebn a() {
      return this.b;
   }

   public Predicate<ceh> a(cei<buo, ceh> var1) {
      return this.a.getPredicate(_snowman);
   }

   @Override
   public boolean equals(Object var1) {
      return this == _snowman;
   }

   @Override
   public int hashCode() {
      return System.identityHashCode(this);
   }

   public static class a implements JsonDeserializer<ebu> {
      public a() {
      }

      public ebu a(JsonElement var1, Type var2, JsonDeserializationContext var3) throws JsonParseException {
         JsonObject _snowman = _snowman.getAsJsonObject();
         return new ebu(this.b(_snowman), (ebn)_snowman.deserialize(_snowman.get("apply"), ebn.class));
      }

      private ebq b(JsonObject var1) {
         return _snowman.has("when") ? a(afd.t(_snowman, "when")) : ebq.a;
      }

      @VisibleForTesting
      static ebq a(JsonObject var0) {
         Set<Entry<String, JsonElement>> _snowman = _snowman.entrySet();
         if (_snowman.isEmpty()) {
            throw new JsonParseException("No elements found in selector");
         } else if (_snowman.size() == 1) {
            if (_snowman.has("OR")) {
               List<ebq> _snowmanx = Streams.stream(afd.u(_snowman, "OR")).map(var0x -> a(var0x.getAsJsonObject())).collect(Collectors.toList());
               return new ebt(_snowmanx);
            } else if (_snowman.has("AND")) {
               List<ebq> _snowmanx = Streams.stream(afd.u(_snowman, "AND")).map(var0x -> a(var0x.getAsJsonObject())).collect(Collectors.toList());
               return new ebp(_snowmanx);
            } else {
               return a(_snowman.iterator().next());
            }
         } else {
            return new ebp(_snowman.stream().map(ebu.a::a).collect(Collectors.toList()));
         }
      }

      private static ebq a(Entry<String, JsonElement> var0) {
         return new ebr(_snowman.getKey(), _snowman.getValue().getAsString());
      }
   }
}
