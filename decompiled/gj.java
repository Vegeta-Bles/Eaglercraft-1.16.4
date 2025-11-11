import com.google.common.collect.Lists;
import java.util.AbstractList;
import java.util.Arrays;
import java.util.List;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import org.apache.commons.lang3.Validate;

public class gj<E> extends AbstractList<E> {
   private final List<E> a;
   private final E b;

   public static <E> gj<E> a() {
      return new gj<>();
   }

   public static <E> gj<E> a(int var0, E var1) {
      Validate.notNull(_snowman);
      Object[] _snowman = new Object[_snowman];
      Arrays.fill(_snowman, _snowman);
      return new gj<>(Arrays.asList((E[])_snowman), _snowman);
   }

   @SafeVarargs
   public static <E> gj<E> a(E var0, E... var1) {
      return new gj<>(Arrays.asList(_snowman), _snowman);
   }

   protected gj() {
      this(Lists.newArrayList(), null);
   }

   protected gj(List<E> var1, @Nullable E var2) {
      this.a = _snowman;
      this.b = _snowman;
   }

   @Nonnull
   @Override
   public E get(int var1) {
      return this.a.get(_snowman);
   }

   @Override
   public E set(int var1, E var2) {
      Validate.notNull(_snowman);
      return this.a.set(_snowman, _snowman);
   }

   @Override
   public void add(int var1, E var2) {
      Validate.notNull(_snowman);
      this.a.add(_snowman, _snowman);
   }

   @Override
   public E remove(int var1) {
      return this.a.remove(_snowman);
   }

   @Override
   public int size() {
      return this.a.size();
   }

   @Override
   public void clear() {
      if (this.b == null) {
         super.clear();
      } else {
         for (int _snowman = 0; _snowman < this.size(); _snowman++) {
            this.set(_snowman, this.b);
         }
      }
   }
}
