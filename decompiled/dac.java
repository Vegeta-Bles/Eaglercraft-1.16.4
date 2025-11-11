import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSyntaxException;
import java.util.Collection;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class dac extends dai {
   private static final Logger a = LogManager.getLogger();
   private final List<bps> b;

   private dac(dbo[] var1, Collection<bps> var2) {
      super(_snowman);
      this.b = ImmutableList.copyOf(_snowman);
   }

   @Override
   public dak b() {
      return dal.d;
   }

   @Override
   public bmb a(bmb var1, cyv var2) {
      Random _snowman = _snowman.a();
      bps _snowmanx;
      if (this.b.isEmpty()) {
         boolean _snowmanxx = _snowman.b() == bmd.mc;
         List<bps> _snowmanxxx = gm.R.g().filter(bps::i).filter(var2x -> _snowman || var2x.a(_snowman)).collect(Collectors.toList());
         if (_snowmanxxx.isEmpty()) {
            a.warn("Couldn't find a compatible enchantment for {}", _snowman);
            return _snowman;
         }

         _snowmanx = _snowmanxxx.get(_snowman.nextInt(_snowmanxxx.size()));
      } else {
         _snowmanx = this.b.get(_snowman.nextInt(this.b.size()));
      }

      return a(_snowman, _snowmanx, _snowman);
   }

   private static bmb a(bmb var0, bps var1, Random var2) {
      int _snowman = afm.a(_snowman, _snowman.e(), _snowman.a());
      if (_snowman.b() == bmd.mc) {
         _snowman = new bmb(bmd.pq);
         blf.a(_snowman, new bpv(_snowman, _snowman));
      } else {
         _snowman.a(_snowman, _snowman);
      }

      return _snowman;
   }

   public static dai.a<?> d() {
      return a(var0 -> new dac(var0, ImmutableList.of()));
   }

   public static class a extends dai.a<dac.a> {
      private final Set<bps> a = Sets.newHashSet();

      public a() {
      }

      protected dac.a a() {
         return this;
      }

      public dac.a a(bps var1) {
         this.a.add(_snowman);
         return this;
      }

      @Override
      public daj b() {
         return new dac(this.g(), this.a);
      }
   }

   public static class b extends dai.c<dac> {
      public b() {
      }

      public void a(JsonObject var1, dac var2, JsonSerializationContext var3) {
         super.a(_snowman, _snowman, _snowman);
         if (!_snowman.b.isEmpty()) {
            JsonArray _snowman = new JsonArray();

            for (bps _snowmanx : _snowman.b) {
               vk _snowmanxx = gm.R.b(_snowmanx);
               if (_snowmanxx == null) {
                  throw new IllegalArgumentException("Don't know how to serialize enchantment " + _snowmanx);
               }

               _snowman.add(new JsonPrimitive(_snowmanxx.toString()));
            }

            _snowman.add("enchantments", _snowman);
         }
      }

      public dac a(JsonObject var1, JsonDeserializationContext var2, dbo[] var3) {
         List<bps> _snowman = Lists.newArrayList();
         if (_snowman.has("enchantments")) {
            for (JsonElement _snowmanx : afd.u(_snowman, "enchantments")) {
               String _snowmanxx = afd.a(_snowmanx, "enchantment");
               bps _snowmanxxx = gm.R.b(new vk(_snowmanxx)).orElseThrow(() -> new JsonSyntaxException("Unknown enchantment '" + _snowman + "'"));
               _snowman.add(_snowmanxxx);
            }
         }

         return new dac(_snowman, _snowman);
      }
   }
}
