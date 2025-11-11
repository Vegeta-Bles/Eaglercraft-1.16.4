import com.google.common.collect.Lists;
import java.util.List;
import java.util.Objects;
import javax.annotation.Nullable;

public abstract class nn implements nx {
   protected final List<nr> a = Lists.newArrayList();
   private afa d = afa.a;
   @Nullable
   private ly e;
   private ob f = ob.a;

   public nn() {
   }

   @Override
   public nx a(nr var1) {
      this.a.add(_snowman);
      return this;
   }

   @Override
   public String a() {
      return "";
   }

   @Override
   public List<nr> b() {
      return this.a;
   }

   @Override
   public nx a(ob var1) {
      this.f = _snowman;
      return this;
   }

   @Override
   public ob c() {
      return this.f;
   }

   public abstract nn d();

   @Override
   public final nx e() {
      nn _snowman = this.d();
      _snowman.a.addAll(this.a);
      _snowman.a(this.f);
      return _snowman;
   }

   @Override
   public afa f() {
      ly _snowman = ly.a();
      if (this.e != _snowman) {
         this.d = _snowman.a(this);
         this.e = _snowman;
      }

      return this.d;
   }

   @Override
   public boolean equals(Object var1) {
      if (this == _snowman) {
         return true;
      } else if (!(_snowman instanceof nn)) {
         return false;
      } else {
         nn _snowman = (nn)_snowman;
         return this.a.equals(_snowman.a) && Objects.equals(this.c(), _snowman.c());
      }
   }

   @Override
   public int hashCode() {
      return Objects.hash(this.c(), this.a);
   }

   @Override
   public String toString() {
      return "BaseComponent{style=" + this.f + ", siblings=" + this.a + '}';
   }
}
