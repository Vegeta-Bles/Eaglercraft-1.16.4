import com.google.common.collect.Lists;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.mojang.datafixers.util.Pair;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import javax.annotation.Nullable;

public class ebn implements ely {
   private final List<ebo> a;

   public ebn(List<ebo> var1) {
      this.a = _snowman;
   }

   public List<ebo> a() {
      return this.a;
   }

   @Override
   public boolean equals(Object var1) {
      if (this == _snowman) {
         return true;
      } else if (_snowman instanceof ebn) {
         ebn _snowman = (ebn)_snowman;
         return this.a.equals(_snowman.a);
      } else {
         return false;
      }
   }

   @Override
   public int hashCode() {
      return this.a.hashCode();
   }

   @Override
   public Collection<vk> f() {
      return this.a().stream().map(ebo::a).collect(Collectors.toSet());
   }

   @Override
   public Collection<elr> a(Function<vk, ely> var1, Set<Pair<String, String>> var2) {
      return this.a().stream().map(ebo::a).distinct().flatMap(var2x -> _snowman.apply(var2x).a(_snowman, _snowman).stream()).collect(Collectors.toSet());
   }

   @Nullable
   @Override
   public elo a(els var1, Function<elr, ekc> var2, elv var3, vk var4) {
      if (this.a().isEmpty()) {
         return null;
      } else {
         elz.a _snowman = new elz.a();

         for (ebo _snowmanx : this.a()) {
            elo _snowmanxx = _snowman.a(_snowmanx.a(), _snowmanx);
            _snowman.a(_snowmanxx, _snowmanx.d());
         }

         return _snowman.a();
      }
   }

   public static class a implements JsonDeserializer<ebn> {
      public a() {
      }

      public ebn a(JsonElement var1, Type var2, JsonDeserializationContext var3) throws JsonParseException {
         List<ebo> _snowman = Lists.newArrayList();
         if (_snowman.isJsonArray()) {
            JsonArray _snowmanx = _snowman.getAsJsonArray();
            if (_snowmanx.size() == 0) {
               throw new JsonParseException("Empty variant array");
            }

            for (JsonElement _snowmanxx : _snowmanx) {
               _snowman.add((ebo)_snowman.deserialize(_snowmanxx, ebo.class));
            }
         } else {
            _snowman.add((ebo)_snowman.deserialize(_snowman, ebo.class));
         }

         return new ebn(_snowman);
      }
   }
}
