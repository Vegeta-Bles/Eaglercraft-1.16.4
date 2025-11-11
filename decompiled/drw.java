import java.util.Set;

public class drw extends drl {
   private static final nr i = new of("gui.recipebook.toggleRecipes.smeltable");

   public drw() {
   }

   @Override
   protected nr c() {
      return i;
   }

   @Override
   protected Set<blx> b() {
      return cbz.f().keySet();
   }
}
