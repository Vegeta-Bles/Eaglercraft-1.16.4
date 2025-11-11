import com.google.common.collect.ForwardingList;
import java.util.List;

public class dzo extends ForwardingList<bmb> {
   private final gj<bmb> a = gj.a(bfv.g(), bmb.b);

   public dzo() {
   }

   protected List<bmb> delegate() {
      return this.a;
   }

   public mj a() {
      mj _snowman = new mj();

      for (bmb _snowmanx : this.delegate()) {
         _snowman.add(_snowmanx.b(new md()));
      }

      return _snowman;
   }

   public void a(mj var1) {
      List<bmb> _snowman = this.delegate();

      for (int _snowmanx = 0; _snowmanx < _snowman.size(); _snowmanx++) {
         _snowman.set(_snowmanx, bmb.a(_snowman.a(_snowmanx)));
      }
   }

   public boolean isEmpty() {
      for (bmb _snowman : this.delegate()) {
         if (!_snowman.a()) {
            return false;
         }
      }

      return true;
   }
}
