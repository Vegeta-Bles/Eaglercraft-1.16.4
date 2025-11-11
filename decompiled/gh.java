import com.google.common.base.Predicates;
import com.google.common.collect.Iterators;
import com.google.common.collect.Lists;
import java.util.IdentityHashMap;
import java.util.Iterator;
import java.util.List;
import javax.annotation.Nullable;

public class gh<T> implements gg<T> {
   private int a;
   private final IdentityHashMap<T, Integer> b;
   private final List<T> c;

   public gh() {
      this(512);
   }

   public gh(int var1) {
      this.c = Lists.newArrayListWithExpectedSize(_snowman);
      this.b = new IdentityHashMap<>(_snowman);
   }

   public void a(T var1, int var2) {
      this.b.put(_snowman, _snowman);

      while (this.c.size() <= _snowman) {
         this.c.add(null);
      }

      this.c.set(_snowman, _snowman);
      if (this.a <= _snowman) {
         this.a = _snowman + 1;
      }
   }

   public void b(T var1) {
      this.a(_snowman, this.a);
   }

   @Override
   public int a(T var1) {
      Integer _snowman = this.b.get(_snowman);
      return _snowman == null ? -1 : _snowman;
   }

   @Nullable
   @Override
   public final T a(int var1) {
      return _snowman >= 0 && _snowman < this.c.size() ? this.c.get(_snowman) : null;
   }

   @Override
   public Iterator<T> iterator() {
      return Iterators.filter(this.c.iterator(), Predicates.notNull());
   }

   public int a() {
      return this.b.size();
   }
}
