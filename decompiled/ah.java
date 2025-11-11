import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSyntaxException;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import javax.annotation.Nullable;

public class ah {
   private final nr a;
   private final nr b;
   private final bmb c;
   private final vk d;
   private final ai e;
   private final boolean f;
   private final boolean g;
   private final boolean h;
   private float i;
   private float j;

   public ah(bmb var1, nr var2, nr var3, @Nullable vk var4, ai var5, boolean var6, boolean var7, boolean var8) {
      this.a = _snowman;
      this.b = _snowman;
      this.c = _snowman;
      this.d = _snowman;
      this.e = _snowman;
      this.f = _snowman;
      this.g = _snowman;
      this.h = _snowman;
   }

   public void a(float var1, float var2) {
      this.i = _snowman;
      this.j = _snowman;
   }

   public nr a() {
      return this.a;
   }

   public nr b() {
      return this.b;
   }

   public bmb c() {
      return this.c;
   }

   @Nullable
   public vk d() {
      return this.d;
   }

   public ai e() {
      return this.e;
   }

   public float f() {
      return this.i;
   }

   public float g() {
      return this.j;
   }

   public boolean h() {
      return this.f;
   }

   public boolean i() {
      return this.g;
   }

   public boolean j() {
      return this.h;
   }

   public static ah a(JsonObject var0) {
      nr _snowman = nr.a.a(_snowman.get("title"));
      nr _snowmanx = nr.a.a(_snowman.get("description"));
      if (_snowman != null && _snowmanx != null) {
         bmb _snowmanxx = b(afd.t(_snowman, "icon"));
         vk _snowmanxxx = _snowman.has("background") ? new vk(afd.h(_snowman, "background")) : null;
         ai _snowmanxxxx = _snowman.has("frame") ? ai.a(afd.h(_snowman, "frame")) : ai.a;
         boolean _snowmanxxxxx = afd.a(_snowman, "show_toast", true);
         boolean _snowmanxxxxxx = afd.a(_snowman, "announce_to_chat", true);
         boolean _snowmanxxxxxxx = afd.a(_snowman, "hidden", false);
         return new ah(_snowmanxx, _snowman, _snowmanx, _snowmanxxx, _snowmanxxxx, _snowmanxxxxx, _snowmanxxxxxx, _snowmanxxxxxxx);
      } else {
         throw new JsonSyntaxException("Both title and description must be set");
      }
   }

   private static bmb b(JsonObject var0) {
      if (!_snowman.has("item")) {
         throw new JsonSyntaxException("Unsupported icon type, currently only items are supported (add 'item' key)");
      } else {
         blx _snowman = afd.i(_snowman, "item");
         if (_snowman.has("data")) {
            throw new JsonParseException("Disallowed data tag found");
         } else {
            bmb _snowmanx = new bmb(_snowman);
            if (_snowman.has("nbt")) {
               try {
                  md _snowmanxx = mu.a(afd.a(_snowman.get("nbt"), "nbt"));
                  _snowmanx.c(_snowmanxx);
               } catch (CommandSyntaxException var4) {
                  throw new JsonSyntaxException("Invalid nbt tag: " + var4.getMessage());
               }
            }

            return _snowmanx;
         }
      }
   }

   public void a(nf var1) {
      _snowman.a(this.a);
      _snowman.a(this.b);
      _snowman.a(this.c);
      _snowman.a(this.e);
      int _snowman = 0;
      if (this.d != null) {
         _snowman |= 1;
      }

      if (this.f) {
         _snowman |= 2;
      }

      if (this.h) {
         _snowman |= 4;
      }

      _snowman.writeInt(_snowman);
      if (this.d != null) {
         _snowman.a(this.d);
      }

      _snowman.writeFloat(this.i);
      _snowman.writeFloat(this.j);
   }

   public static ah b(nf var0) {
      nr _snowman = _snowman.h();
      nr _snowmanx = _snowman.h();
      bmb _snowmanxx = _snowman.n();
      ai _snowmanxxx = _snowman.a(ai.class);
      int _snowmanxxxx = _snowman.readInt();
      vk _snowmanxxxxx = (_snowmanxxxx & 1) != 0 ? _snowman.p() : null;
      boolean _snowmanxxxxxx = (_snowmanxxxx & 2) != 0;
      boolean _snowmanxxxxxxx = (_snowmanxxxx & 4) != 0;
      ah _snowmanxxxxxxxx = new ah(_snowmanxx, _snowman, _snowmanx, _snowmanxxxxx, _snowmanxxx, _snowmanxxxxxx, false, _snowmanxxxxxxx);
      _snowmanxxxxxxxx.a(_snowman.readFloat(), _snowman.readFloat());
      return _snowmanxxxxxxxx;
   }

   public JsonElement k() {
      JsonObject _snowman = new JsonObject();
      _snowman.add("icon", this.l());
      _snowman.add("title", nr.a.b(this.a));
      _snowman.add("description", nr.a.b(this.b));
      _snowman.addProperty("frame", this.e.a());
      _snowman.addProperty("show_toast", this.f);
      _snowman.addProperty("announce_to_chat", this.g);
      _snowman.addProperty("hidden", this.h);
      if (this.d != null) {
         _snowman.addProperty("background", this.d.toString());
      }

      return _snowman;
   }

   private JsonObject l() {
      JsonObject _snowman = new JsonObject();
      _snowman.addProperty("item", gm.T.b(this.c.b()).toString());
      if (this.c.n()) {
         _snowman.addProperty("nbt", this.c.o().toString());
      }

      return _snowman;
   }
}
