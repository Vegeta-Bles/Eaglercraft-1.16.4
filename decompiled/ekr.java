import java.util.stream.Stream;

public class ekr extends eku {
   private static final vk a = new vk("back");

   public ekr(ekd var1) {
      super(_snowman, new vk("textures/atlas/paintings.png"), "painting");
   }

   @Override
   protected Stream<vk> a() {
      return Stream.concat(gm.X.c().stream(), Stream.of(a));
   }

   public ekc a(bcr var1) {
      return this.a(gm.X.b(_snowman));
   }

   public ekc b() {
      return this.a(a);
   }
}
