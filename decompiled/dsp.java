import com.google.common.collect.Lists;
import java.util.List;

public class dsp implements dsr {
   private static final nr a = new of("spectatorMenu.root.prompt");
   private final List<dss> b = Lists.newArrayList();

   public dsp() {
      this.b.add(new dsv());
      this.b.add(new dsw());
   }

   @Override
   public List<dss> a() {
      return this.b;
   }

   @Override
   public nr b() {
      return a;
   }
}
