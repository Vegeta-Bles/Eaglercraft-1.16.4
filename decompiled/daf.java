import com.google.common.collect.ImmutableSet;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.mojang.authlib.GameProfile;
import java.util.Set;

public class daf extends dai {
   private final cyv.c a;

   public daf(dbo[] var1, cyv.c var2) {
      super(_snowman);
      this.a = _snowman;
   }

   @Override
   public dak b() {
      return dal.t;
   }

   @Override
   public Set<daz<?>> a() {
      return ImmutableSet.of(this.a.a());
   }

   @Override
   public bmb a(bmb var1, cyv var2) {
      if (_snowman.b() == bmd.pg) {
         aqa _snowman = _snowman.c(this.a.a());
         if (_snowman instanceof bfw) {
            GameProfile _snowmanx = ((bfw)_snowman).eA();
            _snowman.p().a("SkullOwner", mp.a(new md(), _snowmanx));
         }
      }

      return _snowman;
   }

   public static class a extends dai.c<daf> {
      public a() {
      }

      public void a(JsonObject var1, daf var2, JsonSerializationContext var3) {
         super.a(_snowman, _snowman, _snowman);
         _snowman.add("entity", _snowman.serialize(_snowman.a));
      }

      public daf a(JsonObject var1, JsonDeserializationContext var2, dbo[] var3) {
         cyv.c _snowman = afd.a(_snowman, "entity", _snowman, cyv.c.class);
         return new daf(_snowman, _snowman);
      }
   }
}
