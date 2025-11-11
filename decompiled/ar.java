import com.google.gson.JsonObject;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ar extends ck<ar.a> {
   private static final vk a = new vk("channeled_lightning");

   public ar() {
   }

   @Override
   public vk a() {
      return a;
   }

   public ar.a a(JsonObject var1, bg.b var2, ax var3) {
      bg.b[] _snowman = bg.b.b(_snowman, "victims", _snowman);
      return new ar.a(_snowman, _snowman);
   }

   public void a(aah var1, Collection<? extends aqa> var2) {
      List<cyv> _snowman = _snowman.stream().map(var1x -> bg.b(_snowman, var1x)).collect(Collectors.toList());
      this.a(_snowman, var1x -> var1x.a(_snowman));
   }

   public static class a extends al {
      private final bg.b[] a;

      public a(bg.b var1, bg.b[] var2) {
         super(ar.a, _snowman);
         this.a = _snowman;
      }

      public static ar.a a(bg... var0) {
         return new ar.a(bg.b.a, Stream.of(_snowman).map(bg.b::a).toArray(bg.b[]::new));
      }

      public boolean a(Collection<? extends cyv> var1) {
         for (bg.b _snowman : this.a) {
            boolean _snowmanx = false;

            for (cyv _snowmanxx : _snowman) {
               if (_snowman.a(_snowmanxx)) {
                  _snowmanx = true;
                  break;
               }
            }

            if (!_snowmanx) {
               return false;
            }
         }

         return true;
      }

      @Override
      public JsonObject a(ci var1) {
         JsonObject _snowman = super.a(_snowman);
         _snowman.add("victims", bg.b.a(this.a, _snowman));
         return _snowman;
      }
   }
}
