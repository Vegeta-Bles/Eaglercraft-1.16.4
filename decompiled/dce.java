import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.Maps;
import java.util.Map;
import javax.annotation.Nullable;
import net.minecraft.server.MinecraftServer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class dce<C> {
   private static final Logger b = LogManager.getLogger();
   public static final dce<MinecraftServer> a = new dce<MinecraftServer>().a(new dcb.a()).a(new dcc.a());
   private final Map<vk, dcd.a<C, ?>> c = Maps.newHashMap();
   private final Map<Class<?>, dcd.a<C, ?>> d = Maps.newHashMap();

   @VisibleForTesting
   public dce() {
   }

   public dce<C> a(dcd.a<C, ?> var1) {
      this.c.put(_snowman.a(), _snowman);
      this.d.put(_snowman.b(), _snowman);
      return this;
   }

   private <T extends dcd<C>> dcd.a<C, T> a(Class<?> var1) {
      return (dcd.a<C, T>)this.d.get(_snowman);
   }

   public <T extends dcd<C>> md a(T var1) {
      dcd.a<C, T> _snowman = this.a(_snowman.getClass());
      md _snowmanx = new md();
      _snowman.a(_snowmanx, _snowman);
      _snowmanx.a("Type", _snowman.a().toString());
      return _snowmanx;
   }

   @Nullable
   public dcd<C> a(md var1) {
      vk _snowman = vk.a(_snowman.l("Type"));
      dcd.a<C, ?> _snowmanx = this.c.get(_snowman);
      if (_snowmanx == null) {
         b.error("Failed to deserialize timer callback: " + _snowman);
         return null;
      } else {
         try {
            return _snowmanx.b(_snowman);
         } catch (Exception var5) {
            b.error("Failed to deserialize timer callback: " + _snowman, var5);
            return null;
         }
      }
   }
}
