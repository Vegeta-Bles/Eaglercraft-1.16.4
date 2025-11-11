import com.google.common.collect.ImmutableList;
import com.google.common.collect.UnmodifiableIterator;
import java.util.List;
import javax.annotation.Nullable;

public class bnt {
   private final String a;
   private final ImmutableList<apu> b;

   public static bnt a(String var0) {
      return gm.U.a(vk.a(_snowman));
   }

   public bnt(apu... var1) {
      this(null, _snowman);
   }

   public bnt(@Nullable String var1, apu... var2) {
      this.a = _snowman;
      this.b = ImmutableList.copyOf(_snowman);
   }

   public String b(String var1) {
      return _snowman + (this.a == null ? gm.U.b(this).a() : this.a);
   }

   public List<apu> a() {
      return this.b;
   }

   public boolean b() {
      if (!this.b.isEmpty()) {
         UnmodifiableIterator var1 = this.b.iterator();

         while (var1.hasNext()) {
            apu _snowman = (apu)var1.next();
            if (_snowman.a().a()) {
               return true;
            }
         }
      }

      return false;
   }
}
