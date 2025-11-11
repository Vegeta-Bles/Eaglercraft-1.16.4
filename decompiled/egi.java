public class egi extends efw<baq, dvj<baq>> {
   private static final vk a = new vk("textures/entity/rabbit/brown.png");
   private static final vk g = new vk("textures/entity/rabbit/white.png");
   private static final vk h = new vk("textures/entity/rabbit/black.png");
   private static final vk i = new vk("textures/entity/rabbit/gold.png");
   private static final vk j = new vk("textures/entity/rabbit/salt.png");
   private static final vk k = new vk("textures/entity/rabbit/white_splotched.png");
   private static final vk l = new vk("textures/entity/rabbit/toast.png");
   private static final vk m = new vk("textures/entity/rabbit/caerbannog.png");

   public egi(eet var1) {
      super(_snowman, new dvj<>(), 0.3F);
   }

   public vk a(baq var1) {
      String _snowman = k.a(_snowman.R().getString());
      if (_snowman != null && "Toast".equals(_snowman)) {
         return l;
      } else {
         switch (_snowman.eN()) {
            case 0:
            default:
               return a;
            case 1:
               return g;
            case 2:
               return h;
            case 3:
               return k;
            case 4:
               return i;
            case 5:
               return j;
            case 99:
               return m;
         }
      }
   }
}
