import com.google.common.collect.Maps;
import java.util.Map;

public class enb implements aci {
   public static final enb.a<bmb> a = new enb.a<>();
   public static final enb.a<bmb> b = new enb.a<>();
   public static final enb.a<drt> c = new enb.a<>();
   private final Map<enb.a<?>, emy<?>> d = Maps.newHashMap();

   public enb() {
   }

   @Override
   public void a(ach var1) {
      for (emy<?> _snowman : this.d.values()) {
         _snowman.b();
      }
   }

   public <T> void a(enb.a<T> var1, emy<T> var2) {
      this.d.put(_snowman, _snowman);
   }

   public <T> emy<T> a(enb.a<T> var1) {
      return (emy<T>)this.d.get(_snowman);
   }

   public static class a<T> {
      public a() {
      }
   }
}
