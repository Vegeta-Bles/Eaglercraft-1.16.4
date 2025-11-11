import com.google.common.collect.Lists;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import java.util.Arrays;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.server.MinecraftServer;

public class ab {
   public static final ab a = new ab(0, new vk[0], new vk[0], cy.a.a);
   private final int b;
   private final vk[] c;
   private final vk[] d;
   private final cy.a e;

   public ab(int var1, vk[] var2, vk[] var3, cy.a var4) {
      this.b = _snowman;
      this.c = _snowman;
      this.d = _snowman;
      this.e = _snowman;
   }

   public void a(aah var1) {
      _snowman.d(this.b);
      cyv _snowman = new cyv.a(_snowman.u()).a(dbc.a, _snowman).a(dbc.f, _snowman.cA()).a(_snowman.cY()).a(dbb.i);
      boolean _snowmanx = false;

      for (vk _snowmanxx : this.c) {
         for (bmb _snowmanxxx : _snowman.c.aJ().a(_snowmanxx).a(_snowman)) {
            if (_snowman.g(_snowmanxxx)) {
               _snowman.l.a(null, _snowman.cD(), _snowman.cE(), _snowman.cH(), adq.gL, adr.h, 0.2F, ((_snowman.cY().nextFloat() - _snowman.cY().nextFloat()) * 0.7F + 1.0F) * 2.0F);
               _snowmanx = true;
            } else {
               bcv _snowmanxxxx = _snowman.a(_snowmanxxx, false);
               if (_snowmanxxxx != null) {
                  _snowmanxxxx.n();
                  _snowmanxxxx.b(_snowman.bS());
               }
            }
         }
      }

      if (_snowmanx) {
         _snowman.bo.c();
      }

      if (this.d.length > 0) {
         _snowman.a(this.d);
      }

      MinecraftServer _snowmanxx = _snowman.c;
      this.e.a(_snowmanxx.aB()).ifPresent(var2x -> _snowman.aB().a(var2x, _snowman.cw().a().a(2)));
   }

   @Override
   public String toString() {
      return "AdvancementRewards{experience="
         + this.b
         + ", loot="
         + Arrays.toString((Object[])this.c)
         + ", recipes="
         + Arrays.toString((Object[])this.d)
         + ", function="
         + this.e
         + '}';
   }

   public JsonElement b() {
      if (this == a) {
         return JsonNull.INSTANCE;
      } else {
         JsonObject _snowman = new JsonObject();
         if (this.b != 0) {
            _snowman.addProperty("experience", this.b);
         }

         if (this.c.length > 0) {
            JsonArray _snowmanx = new JsonArray();

            for (vk _snowmanxx : this.c) {
               _snowmanx.add(_snowmanxx.toString());
            }

            _snowman.add("loot", _snowmanx);
         }

         if (this.d.length > 0) {
            JsonArray _snowmanx = new JsonArray();

            for (vk _snowmanxx : this.d) {
               _snowmanx.add(_snowmanxx.toString());
            }

            _snowman.add("recipes", _snowmanx);
         }

         if (this.e.a() != null) {
            _snowman.addProperty("function", this.e.a().toString());
         }

         return _snowman;
      }
   }

   public static ab a(JsonObject var0) throws JsonParseException {
      int _snowman = afd.a(_snowman, "experience", 0);
      JsonArray _snowmanx = afd.a(_snowman, "loot", new JsonArray());
      vk[] _snowmanxx = new vk[_snowmanx.size()];

      for (int _snowmanxxx = 0; _snowmanxxx < _snowmanxx.length; _snowmanxxx++) {
         _snowmanxx[_snowmanxxx] = new vk(afd.a(_snowmanx.get(_snowmanxxx), "loot[" + _snowmanxxx + "]"));
      }

      JsonArray _snowmanxxx = afd.a(_snowman, "recipes", new JsonArray());
      vk[] _snowmanxxxx = new vk[_snowmanxxx.size()];

      for (int _snowmanxxxxx = 0; _snowmanxxxxx < _snowmanxxxx.length; _snowmanxxxxx++) {
         _snowmanxxxx[_snowmanxxxxx] = new vk(afd.a(_snowmanxxx.get(_snowmanxxxxx), "recipes[" + _snowmanxxxxx + "]"));
      }

      cy.a _snowmanxxxxx;
      if (_snowman.has("function")) {
         _snowmanxxxxx = new cy.a(new vk(afd.h(_snowman, "function")));
      } else {
         _snowmanxxxxx = cy.a.a;
      }

      return new ab(_snowman, _snowmanxx, _snowmanxxxx, _snowmanxxxxx);
   }

   public static class a {
      private int a;
      private final List<vk> b = Lists.newArrayList();
      private final List<vk> c = Lists.newArrayList();
      @Nullable
      private vk d;

      public a() {
      }

      public static ab.a a(int var0) {
         return new ab.a().b(_snowman);
      }

      public ab.a b(int var1) {
         this.a += _snowman;
         return this;
      }

      public static ab.a c(vk var0) {
         return new ab.a().d(_snowman);
      }

      public ab.a d(vk var1) {
         this.c.add(_snowman);
         return this;
      }

      public ab a() {
         return new ab(this.a, this.b.toArray(new vk[0]), this.c.toArray(new vk[0]), this.d == null ? cy.a.a : new cy.a(this.d));
      }
   }
}
