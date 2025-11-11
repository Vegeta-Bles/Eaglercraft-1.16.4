import com.google.common.collect.Lists;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class adv extends adt {
   private static final Logger c = LogManager.getLogger();

   public adv() {
   }

   public int a(Collection<boq<?>> var1, aah var2) {
      List<vk> _snowman = Lists.newArrayList();
      int _snowmanx = 0;

      for (boq<?> _snowmanxx : _snowman) {
         vk _snowmanxxx = _snowmanxx.f();
         if (!this.a.contains(_snowmanxxx) && !_snowmanxx.af_()) {
            this.a(_snowmanxxx);
            this.d(_snowmanxxx);
            _snowman.add(_snowmanxxx);
            ac.f.a(_snowman, _snowmanxx);
            _snowmanx++;
         }
      }

      this.a(ql.a.b, _snowman, _snowman);
      return _snowmanx;
   }

   public int b(Collection<boq<?>> var1, aah var2) {
      List<vk> _snowman = Lists.newArrayList();
      int _snowmanx = 0;

      for (boq<?> _snowmanxx : _snowman) {
         vk _snowmanxxx = _snowmanxx.f();
         if (this.a.contains(_snowmanxxx)) {
            this.c(_snowmanxxx);
            _snowman.add(_snowmanxxx);
            _snowmanx++;
         }
      }

      this.a(ql.a.c, _snowman, _snowman);
      return _snowmanx;
   }

   private void a(ql.a var1, aah var2, List<vk> var3) {
      _snowman.b.a(new ql(_snowman, _snowman, Collections.emptyList(), this.a()));
   }

   public md b() {
      md _snowman = new md();
      this.a().b(_snowman);
      mj _snowmanx = new mj();

      for (vk _snowmanxx : this.a) {
         _snowmanx.add(ms.a(_snowmanxx.toString()));
      }

      _snowman.a("recipes", _snowmanx);
      mj _snowmanxx = new mj();

      for (vk _snowmanxxx : this.b) {
         _snowmanxx.add(ms.a(_snowmanxxx.toString()));
      }

      _snowman.a("toBeDisplayed", _snowmanxx);
      return _snowman;
   }

   public void a(md var1, bor var2) {
      this.a(adu.a(_snowman));
      mj _snowman = _snowman.d("recipes", 8);
      this.a(_snowman, this::a, _snowman);
      mj _snowmanx = _snowman.d("toBeDisplayed", 8);
      this.a(_snowmanx, this::f, _snowman);
   }

   private void a(mj var1, Consumer<boq<?>> var2, bor var3) {
      for (int _snowman = 0; _snowman < _snowman.size(); _snowman++) {
         String _snowmanx = _snowman.j(_snowman);

         try {
            vk _snowmanxx = new vk(_snowmanx);
            Optional<? extends boq<?>> _snowmanxxx = _snowman.a(_snowmanxx);
            if (!_snowmanxxx.isPresent()) {
               c.error("Tried to load unrecognized recipe: {} removed now.", _snowmanxx);
            } else {
               _snowman.accept((boq<?>)_snowmanxxx.get());
            }
         } catch (v var8) {
            c.error("Tried to load improperly formatted recipe: {} removed now.", _snowmanx);
         }
      }
   }

   public void a(aah var1) {
      _snowman.b.a(new ql(ql.a.a, this.a, this.b, this.a()));
   }
}
