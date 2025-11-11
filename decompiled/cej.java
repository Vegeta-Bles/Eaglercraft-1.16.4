import com.google.common.collect.ArrayTable;
import com.google.common.collect.HashBasedTable;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import com.google.common.collect.Table;
import com.google.common.collect.UnmodifiableIterator;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.Map;
import java.util.Optional;
import java.util.Map.Entry;
import java.util.function.Function;
import java.util.stream.Collectors;
import javax.annotation.Nullable;

public abstract class cej<O, S> {
   private static final Function<Entry<cfj<?>, Comparable<?>>, String> a = new Function<Entry<cfj<?>, Comparable<?>>, String>() {
      public String a(@Nullable Entry<cfj<?>, Comparable<?>> var1) {
         if (_snowman == null) {
            return "<NULL>";
         } else {
            cfj<?> _snowman = _snowman.getKey();
            return _snowman.f() + "=" + this.a(_snowman, _snowman.getValue());
         }
      }

      private <T extends Comparable<T>> String a(cfj<T> var1, Comparable<?> var2) {
         return _snowman.a((T)_snowman);
      }
   };
   protected final O c;
   private final ImmutableMap<cfj<?>, Comparable<?>> b;
   private Table<cfj<?>, Comparable<?>, S> e;
   protected final MapCodec<S> d;

   protected cej(O var1, ImmutableMap<cfj<?>, Comparable<?>> var2, MapCodec<S> var3) {
      this.c = _snowman;
      this.b = _snowman;
      this.d = _snowman;
   }

   public <T extends Comparable<T>> S a(cfj<T> var1) {
      return this.a(_snowman, a(_snowman.a(), this.c(_snowman)));
   }

   protected static <T> T a(Collection<T> var0, T var1) {
      Iterator<T> _snowman = _snowman.iterator();

      while (_snowman.hasNext()) {
         if (_snowman.next().equals(_snowman)) {
            if (_snowman.hasNext()) {
               return _snowman.next();
            }

            return _snowman.iterator().next();
         }
      }

      return _snowman.next();
   }

   @Override
   public String toString() {
      StringBuilder _snowman = new StringBuilder();
      _snowman.append(this.c);
      if (!this.s().isEmpty()) {
         _snowman.append('[');
         _snowman.append(this.s().entrySet().stream().map(a).collect(Collectors.joining(",")));
         _snowman.append(']');
      }

      return _snowman.toString();
   }

   public Collection<cfj<?>> r() {
      return Collections.unmodifiableCollection(this.b.keySet());
   }

   public <T extends Comparable<T>> boolean b(cfj<T> var1) {
      return this.b.containsKey(_snowman);
   }

   public <T extends Comparable<T>> T c(cfj<T> var1) {
      Comparable<?> _snowman = (Comparable<?>)this.b.get(_snowman);
      if (_snowman == null) {
         throw new IllegalArgumentException("Cannot get property " + _snowman + " as it does not exist in " + this.c);
      } else {
         return _snowman.g().cast(_snowman);
      }
   }

   public <T extends Comparable<T>> Optional<T> d(cfj<T> var1) {
      Comparable<?> _snowman = (Comparable<?>)this.b.get(_snowman);
      return _snowman == null ? Optional.empty() : Optional.of(_snowman.g().cast(_snowman));
   }

   public <T extends Comparable<T>, V extends T> S a(cfj<T> var1, V var2) {
      Comparable<?> _snowman = (Comparable<?>)this.b.get(_snowman);
      if (_snowman == null) {
         throw new IllegalArgumentException("Cannot set property " + _snowman + " as it does not exist in " + this.c);
      } else if (_snowman == _snowman) {
         return (S)this;
      } else {
         S _snowmanx = (S)this.e.get(_snowman, _snowman);
         if (_snowmanx == null) {
            throw new IllegalArgumentException("Cannot set property " + _snowman + " to " + _snowman + " on " + this.c + ", it is not an allowed value");
         } else {
            return _snowmanx;
         }
      }
   }

   public void a(Map<Map<cfj<?>, Comparable<?>>, S> var1) {
      if (this.e != null) {
         throw new IllegalStateException();
      } else {
         Table<cfj<?>, Comparable<?>, S> _snowman = HashBasedTable.create();
         UnmodifiableIterator var3 = this.b.entrySet().iterator();

         while (var3.hasNext()) {
            Entry<cfj<?>, Comparable<?>> _snowmanx = (Entry<cfj<?>, Comparable<?>>)var3.next();
            cfj<?> _snowmanxx = _snowmanx.getKey();

            for (Comparable<?> _snowmanxxx : _snowmanxx.a()) {
               if (_snowmanxxx != _snowmanx.getValue()) {
                  _snowman.put(_snowmanxx, _snowmanxxx, _snowman.get(this.b(_snowmanxx, _snowmanxxx)));
               }
            }
         }

         this.e = (Table<cfj<?>, Comparable<?>, S>)(_snowman.isEmpty() ? _snowman : ArrayTable.create(_snowman));
      }
   }

   private Map<cfj<?>, Comparable<?>> b(cfj<?> var1, Comparable<?> var2) {
      Map<cfj<?>, Comparable<?>> _snowman = Maps.newHashMap(this.b);
      _snowman.put(_snowman, _snowman);
      return _snowman;
   }

   public ImmutableMap<cfj<?>, Comparable<?>> s() {
      return this.b;
   }

   protected static <O, S extends cej<O, S>> Codec<S> a(Codec<O> var0, Function<O, S> var1) {
      return _snowman.dispatch("Name", var0x -> var0x.c, var1x -> {
         S _snowman = _snowman.apply((O)var1x);
         return _snowman.s().isEmpty() ? Codec.unit(_snowman) : _snowman.d.fieldOf("Properties").codec();
      });
   }
}
