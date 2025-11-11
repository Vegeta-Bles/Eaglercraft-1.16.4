import java.util.UUID;

public class ekj {
   private static final vk a = new vk("textures/entity/steve.png");
   private static final vk b = new vk("textures/entity/alex.png");

   public static vk a() {
      return a;
   }

   public static vk a(UUID var0) {
      return c(_snowman) ? b : a;
   }

   public static String b(UUID var0) {
      return c(_snowman) ? "slim" : "default";
   }

   private static boolean c(UUID var0) {
      return (_snowman.hashCode() & 1) == 1;
   }
}
