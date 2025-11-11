import com.google.common.collect.ImmutableSet;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import java.util.Set;
import java.util.function.UnaryOperator;
import javax.annotation.Nullable;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class dat extends dai {
   private static final Logger a = LogManager.getLogger();
   private final nr b;
   @Nullable
   private final cyv.c d;

   private dat(dbo[] var1, @Nullable nr var2, @Nullable cyv.c var3) {
      super(_snowman);
      this.b = _snowman;
      this.d = _snowman;
   }

   @Override
   public dak b() {
      return dal.j;
   }

   @Override
   public Set<daz<?>> a() {
      return this.d != null ? ImmutableSet.of(this.d.a()) : ImmutableSet.of();
   }

   public static UnaryOperator<nr> a(cyv var0, @Nullable cyv.c var1) {
      if (_snowman != null) {
         aqa _snowman = _snowman.c(_snowman.a());
         if (_snowman != null) {
            db _snowmanx = _snowman.cw().a(2);
            return var2x -> {
               try {
                  return ns.a(_snowman, var2x, _snowman, 0);
               } catch (CommandSyntaxException var4) {
                  a.warn("Failed to resolve text component", var4);
                  return var2x;
               }
            };
         }
      }

      return var0x -> var0x;
   }

   @Override
   public bmb a(bmb var1, cyv var2) {
      if (this.b != null) {
         _snowman.a(a(_snowman, this.d).apply(this.b));
      }

      return _snowman;
   }

   public static class a extends dai.c<dat> {
      public a() {
      }

      public void a(JsonObject var1, dat var2, JsonSerializationContext var3) {
         super.a(_snowman, _snowman, _snowman);
         if (_snowman.b != null) {
            _snowman.add("name", nr.a.b(_snowman.b));
         }

         if (_snowman.d != null) {
            _snowman.add("entity", _snowman.serialize(_snowman.d));
         }
      }

      public dat a(JsonObject var1, JsonDeserializationContext var2, dbo[] var3) {
         nr _snowman = nr.a.a(_snowman.get("name"));
         cyv.c _snowmanx = afd.a(_snowman, "entity", null, _snowman, cyv.c.class);
         return new dat(_snowman, _snowman, _snowmanx);
      }
   }
}
