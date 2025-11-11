import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.mojang.datafixers.util.Pair;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import javax.annotation.Nullable;

public class ebs implements ely {
   private final cei<buo, ceh> a;
   private final List<ebu> b;

   public ebs(cei<buo, ceh> var1, List<ebu> var2) {
      this.a = _snowman;
      this.b = _snowman;
   }

   public List<ebu> a() {
      return this.b;
   }

   public Set<ebn> b() {
      Set<ebn> _snowman = Sets.newHashSet();

      for (ebu _snowmanx : this.b) {
         _snowman.add(_snowmanx.a());
      }

      return _snowman;
   }

   @Override
   public boolean equals(Object var1) {
      if (this == _snowman) {
         return true;
      } else if (!(_snowman instanceof ebs)) {
         return false;
      } else {
         ebs _snowman = (ebs)_snowman;
         return Objects.equals(this.a, _snowman.a) && Objects.equals(this.b, _snowman.b);
      }
   }

   @Override
   public int hashCode() {
      return Objects.hash(this.a, this.b);
   }

   @Override
   public Collection<vk> f() {
      return this.a().stream().flatMap(var0 -> var0.a().f().stream()).collect(Collectors.toSet());
   }

   @Override
   public Collection<elr> a(Function<vk, ely> var1, Set<Pair<String, String>> var2) {
      return this.a().stream().flatMap(var2x -> var2x.a().a(_snowman, _snowman).stream()).collect(Collectors.toSet());
   }

   @Nullable
   @Override
   public elo a(els var1, Function<elr, ekc> var2, elv var3, vk var4) {
      elw.a _snowman = new elw.a();

      for (ebu _snowmanx : this.a()) {
         elo _snowmanxx = _snowmanx.a().a(_snowman, _snowman, _snowman, _snowman);
         if (_snowmanxx != null) {
            _snowman.a(_snowmanx.a(this.a), _snowmanxx);
         }
      }

      return _snowman.a();
   }

   public static class a implements JsonDeserializer<ebs> {
      private final ebg.a a;

      public a(ebg.a var1) {
         this.a = _snowman;
      }

      public ebs a(JsonElement var1, Type var2, JsonDeserializationContext var3) throws JsonParseException {
         return new ebs(this.a.a(), this.a(_snowman, _snowman.getAsJsonArray()));
      }

      private List<ebu> a(JsonDeserializationContext var1, JsonArray var2) {
         List<ebu> _snowman = Lists.newArrayList();

         for (JsonElement _snowmanx : _snowman) {
            _snowman.add((ebu)_snowman.deserialize(_snowmanx, ebu.class));
         }

         return _snowman;
      }
   }
}
