import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSyntaxException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ae {
   private static final SimpleDateFormat a = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss Z");
   private Date b;

   public ae() {
   }

   public boolean a() {
      return this.b != null;
   }

   public void b() {
      this.b = new Date();
   }

   public void c() {
      this.b = null;
   }

   public Date d() {
      return this.b;
   }

   @Override
   public String toString() {
      return "CriterionProgress{obtained=" + (this.b == null ? "false" : this.b) + '}';
   }

   public void a(nf var1) {
      _snowman.writeBoolean(this.b != null);
      if (this.b != null) {
         _snowman.a(this.b);
      }
   }

   public JsonElement e() {
      return (JsonElement)(this.b != null ? new JsonPrimitive(a.format(this.b)) : JsonNull.INSTANCE);
   }

   public static ae b(nf var0) {
      ae _snowman = new ae();
      if (_snowman.readBoolean()) {
         _snowman.b = _snowman.q();
      }

      return _snowman;
   }

   public static ae a(String var0) {
      ae _snowman = new ae();

      try {
         _snowman.b = a.parse(_snowman);
         return _snowman;
      } catch (ParseException var3) {
         throw new JsonSyntaxException("Invalid datetime: " + _snowman, var3);
      }
   }
}
