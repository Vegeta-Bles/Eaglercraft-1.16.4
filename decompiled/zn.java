import java.util.Vector;
import javax.swing.JList;
import net.minecraft.server.MinecraftServer;

public class zn extends JList<String> {
   private final MinecraftServer a;
   private int b;

   public zn(MinecraftServer var1) {
      this.a = _snowman;
      _snowman.b(this::a);
   }

   public void a() {
      if (this.b++ % 20 == 0) {
         Vector<String> _snowman = new Vector<>();

         for (int _snowmanx = 0; _snowmanx < this.a.ae().s().size(); _snowmanx++) {
            _snowman.add(this.a.ae().s().get(_snowmanx).eA().getName());
         }

         this.setListData(_snowman);
      }
   }
}
