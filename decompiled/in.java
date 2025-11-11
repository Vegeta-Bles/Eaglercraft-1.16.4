import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import java.util.List;
import java.util.function.Supplier;

public class in implements il {
   private final buo a;
   private final List<in.b> b = Lists.newArrayList();

   private in(buo var1) {
      this.a = _snowman;
   }

   @Override
   public buo a() {
      return this.a;
   }

   public static in a(buo var0) {
      return new in(_snowman);
   }

   public in a(List<ir> var1) {
      this.b.add(new in.b(_snowman));
      return this;
   }

   public in a(ir var1) {
      return this.a(ImmutableList.of(_snowman));
   }

   public in a(im var1, List<ir> var2) {
      this.b.add(new in.a(_snowman, _snowman));
      return this;
   }

   public in a(im var1, ir... var2) {
      return this.a(_snowman, ImmutableList.copyOf(_snowman));
   }

   public in a(im var1, ir var2) {
      return this.a(_snowman, ImmutableList.of(_snowman));
   }

   public JsonElement b() {
      cei<buo, ceh> _snowman = this.a.m();
      this.b.forEach(var1x -> var1x.a(_snowman));
      JsonArray _snowmanx = new JsonArray();
      this.b.stream().map(in.b::a).forEach(_snowmanx::add);
      JsonObject _snowmanxx = new JsonObject();
      _snowmanxx.add("multipart", _snowmanx);
      return _snowmanxx;
   }

   static class a extends in.b {
      private final im a;

      private a(im var1, List<ir> var2) {
         super(_snowman);
         this.a = _snowman;
      }

      @Override
      public void a(cei<?, ?> var1) {
         this.a.a(_snowman);
      }

      @Override
      public void a(JsonObject var1) {
         _snowman.add("when", this.a.get());
      }
   }

   static class b implements Supplier<JsonElement> {
      private final List<ir> a;

      private b(List<ir> var1) {
         this.a = _snowman;
      }

      public void a(cei<?, ?> var1) {
      }

      public void a(JsonObject var1) {
      }

      public JsonElement a() {
         JsonObject _snowman = new JsonObject();
         this.a(_snowman);
         _snowman.add("apply", ir.a(this.a));
         return _snowman;
      }
   }
}
