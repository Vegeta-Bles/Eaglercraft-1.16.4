import com.google.gson.JsonObject;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.annotation.Nullable;

public abstract class acp<T> extends acx<T> {
   public static final SimpleDateFormat a = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss Z");
   protected final Date b;
   protected final String c;
   protected final Date d;
   protected final String e;

   public acp(T var1, @Nullable Date var2, @Nullable String var3, @Nullable Date var4, @Nullable String var5) {
      super(_snowman);
      this.b = _snowman == null ? new Date() : _snowman;
      this.c = _snowman == null ? "(Unknown)" : _snowman;
      this.d = _snowman;
      this.e = _snowman == null ? "Banned by an operator." : _snowman;
   }

   protected acp(T var1, JsonObject var2) {
      super(_snowman);

      Date _snowman;
      try {
         _snowman = _snowman.has("created") ? a.parse(_snowman.get("created").getAsString()) : new Date();
      } catch (ParseException var7) {
         _snowman = new Date();
      }

      this.b = _snowman;
      this.c = _snowman.has("source") ? _snowman.get("source").getAsString() : "(Unknown)";

      Date _snowmanx;
      try {
         _snowmanx = _snowman.has("expires") ? a.parse(_snowman.get("expires").getAsString()) : null;
      } catch (ParseException var6) {
         _snowmanx = null;
      }

      this.d = _snowmanx;
      this.e = _snowman.has("reason") ? _snowman.get("reason").getAsString() : "Banned by an operator.";
   }

   public String b() {
      return this.c;
   }

   public Date c() {
      return this.d;
   }

   public String d() {
      return this.e;
   }

   public abstract nr e();

   @Override
   boolean f() {
      return this.d == null ? false : this.d.before(new Date());
   }

   @Override
   protected void a(JsonObject var1) {
      _snowman.addProperty("created", a.format(this.b));
      _snowman.addProperty("source", this.c);
      _snowman.addProperty("expires", this.d == null ? "forever" : a.format(this.d));
      _snowman.addProperty("reason", this.e);
   }
}
