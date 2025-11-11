import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import java.util.Arrays;
import java.util.List;

public class dao extends dai {
   private final List<czq> a;

   private dao(dbo[] var1, List<czq> var2) {
      super(_snowman);
      this.a = ImmutableList.copyOf(_snowman);
   }

   @Override
   public dak b() {
      return dal.n;
   }

   @Override
   public bmb a(bmb var1, cyv var2) {
      if (_snowman.a()) {
         return _snowman;
      } else {
         gj<bmb> _snowman = gj.a();
         this.a.forEach(var2x -> var2x.expand(_snowman, var2xx -> var2xx.a(cyy.a(_snowman::add), _snowman)));
         md _snowmanx = new md();
         aoo.a(_snowmanx, _snowman);
         md _snowmanxx = _snowman.p();
         _snowmanxx.a("BlockEntityTag", _snowmanx.a(_snowmanxx.p("BlockEntityTag")));
         return _snowman;
      }
   }

   @Override
   public void a(czg var1) {
      super.a(_snowman);

      for (int _snowman = 0; _snowman < this.a.size(); _snowman++) {
         this.a.get(_snowman).a(_snowman.b(".entry[" + _snowman + "]"));
      }
   }

   public static dao.a c() {
      return new dao.a();
   }

   public static class a extends dai.a<dao.a> {
      private final List<czq> a = Lists.newArrayList();

      public a() {
      }

      protected dao.a a() {
         return this;
      }

      public dao.a a(czq.a<?> var1) {
         this.a.add(_snowman.b());
         return this;
      }

      @Override
      public daj b() {
         return new dao(this.g(), this.a);
      }
   }

   public static class b extends dai.c<dao> {
      public b() {
      }

      public void a(JsonObject var1, dao var2, JsonSerializationContext var3) {
         super.a(_snowman, _snowman, _snowman);
         _snowman.add("entries", _snowman.serialize(_snowman.a));
      }

      public dao a(JsonObject var1, JsonDeserializationContext var2, dbo[] var3) {
         czq[] _snowman = afd.a(_snowman, "entries", _snowman, czq[].class);
         return new dao(_snowman, Arrays.asList(_snowman));
      }
   }
}
