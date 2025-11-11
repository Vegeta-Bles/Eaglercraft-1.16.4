import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Streams;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import java.util.List;
import java.util.Set;
import java.util.function.UnaryOperator;
import javax.annotation.Nullable;

public class das extends dai {
   private final boolean a;
   private final List<nr> b;
   @Nullable
   private final cyv.c d;

   public das(dbo[] var1, boolean var2, List<nr> var3, @Nullable cyv.c var4) {
      super(_snowman);
      this.a = _snowman;
      this.b = ImmutableList.copyOf(_snowman);
      this.d = _snowman;
   }

   @Override
   public dak b() {
      return dal.s;
   }

   @Override
   public Set<daz<?>> a() {
      return this.d != null ? ImmutableSet.of(this.d.a()) : ImmutableSet.of();
   }

   @Override
   public bmb a(bmb var1, cyv var2) {
      mj _snowman = this.a(_snowman, !this.b.isEmpty());
      if (_snowman != null) {
         if (this.a) {
            _snowman.clear();
         }

         UnaryOperator<nr> _snowmanx = dat.a(_snowman, this.d);
         this.b.stream().map(_snowmanx).map(nr.a::a).map(ms::a).forEach(_snowman::add);
      }

      return _snowman;
   }

   @Nullable
   private mj a(bmb var1, boolean var2) {
      md _snowman;
      if (_snowman.n()) {
         _snowman = _snowman.o();
      } else {
         if (!_snowman) {
            return null;
         }

         _snowman = new md();
         _snowman.c(_snowman);
      }

      md _snowmanx;
      if (_snowman.c("display", 10)) {
         _snowmanx = _snowman.p("display");
      } else {
         if (!_snowman) {
            return null;
         }

         _snowmanx = new md();
         _snowman.a("display", _snowmanx);
      }

      if (_snowmanx.c("Lore", 9)) {
         return _snowmanx.d("Lore", 8);
      } else if (_snowman) {
         mj _snowmanxx = new mj();
         _snowmanx.a("Lore", _snowmanxx);
         return _snowmanxx;
      } else {
         return null;
      }
   }

   public static class b extends dai.c<das> {
      public b() {
      }

      public void a(JsonObject var1, das var2, JsonSerializationContext var3) {
         super.a(_snowman, _snowman, _snowman);
         _snowman.addProperty("replace", _snowman.a);
         JsonArray _snowman = new JsonArray();

         for (nr _snowmanx : _snowman.b) {
            _snowman.add(nr.a.b(_snowmanx));
         }

         _snowman.add("lore", _snowman);
         if (_snowman.d != null) {
            _snowman.add("entity", _snowman.serialize(_snowman.d));
         }
      }

      public das a(JsonObject var1, JsonDeserializationContext var2, dbo[] var3) {
         boolean _snowman = afd.a(_snowman, "replace", false);
         List<nr> _snowmanx = Streams.stream(afd.u(_snowman, "lore")).map(nr.a::a).collect(ImmutableList.toImmutableList());
         cyv.c _snowmanxx = afd.a(_snowman, "entity", null, _snowman, cyv.c.class);
         return new das(_snowman, _snowman, _snowmanx, _snowmanxx);
      }
   }
}
