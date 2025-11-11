import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Maps;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import java.util.Map;
import java.util.Random;
import java.util.Set;

public class czx extends dai {
   private static final Map<vk, czx.c> a = Maps.newHashMap();
   private final bps b;
   private final czx.b d;

   private czx(dbo[] var1, bps var2, czx.b var3) {
      super(_snowman);
      this.b = _snowman;
      this.d = _snowman;
   }

   @Override
   public dak b() {
      return dal.p;
   }

   @Override
   public Set<daz<?>> a() {
      return ImmutableSet.of(dbc.i);
   }

   @Override
   public bmb a(bmb var1, cyv var2) {
      bmb _snowman = _snowman.c(dbc.i);
      if (_snowman != null) {
         int _snowmanx = bpu.a(this.b, _snowman);
         int _snowmanxx = this.d.a(_snowman.a(), _snowman.E(), _snowmanx);
         _snowman.e(_snowmanxx);
      }

      return _snowman;
   }

   public static dai.a<?> a(bps var0, float var1, int var2) {
      return a(var3 -> new czx(var3, _snowman, new czx.a(_snowman, _snowman)));
   }

   public static dai.a<?> a(bps var0) {
      return a(var1 -> new czx(var1, _snowman, new czx.d()));
   }

   public static dai.a<?> b(bps var0) {
      return a(var1 -> new czx(var1, _snowman, new czx.f(1)));
   }

   public static dai.a<?> a(bps var0, int var1) {
      return a(var2 -> new czx(var2, _snowman, new czx.f(_snowman)));
   }

   static {
      a.put(czx.a.a, czx.a::a);
      a.put(czx.d.a, czx.d::a);
      a.put(czx.f.a, czx.f::a);
   }

   static final class a implements czx.b {
      public static final vk a = new vk("binomial_with_bonus_count");
      private final int b;
      private final float c;

      public a(int var1, float var2) {
         this.b = _snowman;
         this.c = _snowman;
      }

      @Override
      public int a(Random var1, int var2, int var3) {
         for (int _snowman = 0; _snowman < _snowman + this.b; _snowman++) {
            if (_snowman.nextFloat() < this.c) {
               _snowman++;
            }
         }

         return _snowman;
      }

      @Override
      public void a(JsonObject var1, JsonSerializationContext var2) {
         _snowman.addProperty("extra", this.b);
         _snowman.addProperty("probability", this.c);
      }

      public static czx.b a(JsonObject var0, JsonDeserializationContext var1) {
         int _snowman = afd.n(_snowman, "extra");
         float _snowmanx = afd.l(_snowman, "probability");
         return new czx.a(_snowman, _snowmanx);
      }

      @Override
      public vk a() {
         return a;
      }
   }

   interface b {
      int a(Random var1, int var2, int var3);

      void a(JsonObject var1, JsonSerializationContext var2);

      vk a();
   }

   interface c {
      czx.b deserialize(JsonObject var1, JsonDeserializationContext var2);
   }

   static final class d implements czx.b {
      public static final vk a = new vk("ore_drops");

      private d() {
      }

      @Override
      public int a(Random var1, int var2, int var3) {
         if (_snowman > 0) {
            int _snowman = _snowman.nextInt(_snowman + 2) - 1;
            if (_snowman < 0) {
               _snowman = 0;
            }

            return _snowman * (_snowman + 1);
         } else {
            return _snowman;
         }
      }

      @Override
      public void a(JsonObject var1, JsonSerializationContext var2) {
      }

      public static czx.b a(JsonObject var0, JsonDeserializationContext var1) {
         return new czx.d();
      }

      @Override
      public vk a() {
         return a;
      }
   }

   public static class e extends dai.c<czx> {
      public e() {
      }

      public void a(JsonObject var1, czx var2, JsonSerializationContext var3) {
         super.a(_snowman, _snowman, _snowman);
         _snowman.addProperty("enchantment", gm.R.b(_snowman.b).toString());
         _snowman.addProperty("formula", _snowman.d.a().toString());
         JsonObject _snowman = new JsonObject();
         _snowman.d.a(_snowman, _snowman);
         if (_snowman.size() > 0) {
            _snowman.add("parameters", _snowman);
         }
      }

      public czx a(JsonObject var1, JsonDeserializationContext var2, dbo[] var3) {
         vk _snowman = new vk(afd.h(_snowman, "enchantment"));
         bps _snowmanx = gm.R.b(_snowman).orElseThrow(() -> new JsonParseException("Invalid enchantment id: " + _snowman));
         vk _snowmanxx = new vk(afd.h(_snowman, "formula"));
         czx.c _snowmanxxx = czx.a.get(_snowmanxx);
         if (_snowmanxxx == null) {
            throw new JsonParseException("Invalid formula id: " + _snowmanxx);
         } else {
            czx.b _snowmanxxxx;
            if (_snowman.has("parameters")) {
               _snowmanxxxx = _snowmanxxx.deserialize(afd.t(_snowman, "parameters"), _snowman);
            } else {
               _snowmanxxxx = _snowmanxxx.deserialize(new JsonObject(), _snowman);
            }

            return new czx(_snowman, _snowmanx, _snowmanxxxx);
         }
      }
   }

   static final class f implements czx.b {
      public static final vk a = new vk("uniform_bonus_count");
      private final int b;

      public f(int var1) {
         this.b = _snowman;
      }

      @Override
      public int a(Random var1, int var2, int var3) {
         return _snowman + _snowman.nextInt(this.b * _snowman + 1);
      }

      @Override
      public void a(JsonObject var1, JsonSerializationContext var2) {
         _snowman.addProperty("bonusMultiplier", this.b);
      }

      public static czx.b a(JsonObject var0, JsonDeserializationContext var1) {
         int _snowman = afd.n(_snowman, "bonusMultiplier");
         return new czx.f(_snowman);
      }

      @Override
      public vk a() {
         return a;
      }
   }
}
