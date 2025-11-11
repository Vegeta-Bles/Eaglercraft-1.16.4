import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;
import com.google.common.primitives.UnsignedLong;
import com.mojang.serialization.Dynamic;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;
import java.util.stream.Stream;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class dcf<T> {
   private static final Logger a = LogManager.getLogger();
   private final dce<T> b;
   private final Queue<dcf.a<T>> c = new PriorityQueue<>(c());
   private UnsignedLong d = UnsignedLong.ZERO;
   private final Table<String, Long, dcf.a<T>> e = HashBasedTable.create();

   private static <T> Comparator<dcf.a<T>> c() {
      return Comparator.<dcf.a<T>>comparingLong(var0 -> var0.a).thenComparing(var0 -> var0.b);
   }

   public dcf(dce<T> var1, Stream<Dynamic<mt>> var2) {
      this(_snowman);
      this.c.clear();
      this.e.clear();
      this.d = UnsignedLong.ZERO;
      _snowman.forEach(var1x -> {
         if (!(var1x.getValue() instanceof md)) {
            a.warn("Invalid format of events: {}", var1x);
         } else {
            this.a((md)var1x.getValue());
         }
      });
   }

   public dcf(dce<T> var1) {
      this.b = _snowman;
   }

   public void a(T var1, long var2) {
      while (true) {
         dcf.a<T> _snowman = this.c.peek();
         if (_snowman == null || _snowman.a > _snowman) {
            return;
         }

         this.c.remove();
         this.e.remove(_snowman.c, _snowman);
         _snowman.d.a(_snowman, this, _snowman);
      }
   }

   public void a(String var1, long var2, dcd<T> var4) {
      if (!this.e.contains(_snowman, _snowman)) {
         this.d = this.d.plus(UnsignedLong.ONE);
         dcf.a<T> _snowman = new dcf.a<>(_snowman, this.d, _snowman, _snowman);
         this.e.put(_snowman, _snowman, _snowman);
         this.c.add(_snowman);
      }
   }

   public int a(String var1) {
      Collection<dcf.a<T>> _snowman = this.e.row(_snowman).values();
      _snowman.forEach(this.c::remove);
      int _snowmanx = _snowman.size();
      _snowman.clear();
      return _snowmanx;
   }

   public Set<String> a() {
      return Collections.unmodifiableSet(this.e.rowKeySet());
   }

   private void a(md var1) {
      md _snowman = _snowman.p("Callback");
      dcd<T> _snowmanx = this.b.a(_snowman);
      if (_snowmanx != null) {
         String _snowmanxx = _snowman.l("Name");
         long _snowmanxxx = _snowman.i("TriggerTime");
         this.a(_snowmanxx, _snowmanxxx, _snowmanx);
      }
   }

   private md a(dcf.a<T> var1) {
      md _snowman = new md();
      _snowman.a("Name", _snowman.c);
      _snowman.a("TriggerTime", _snowman.a);
      _snowman.a("Callback", this.b.a(_snowman.d));
      return _snowman;
   }

   public mj b() {
      mj _snowman = new mj();
      this.c.stream().sorted(c()).map(this::a).forEach(_snowman::add);
      return _snowman;
   }

   public static class a<T> {
      public final long a;
      public final UnsignedLong b;
      public final String c;
      public final dcd<T> d;

      private a(long var1, UnsignedLong var3, String var4, dcd<T> var5) {
         this.a = _snowman;
         this.b = _snowman;
         this.c = _snowman;
         this.d = _snowman;
      }
   }
}
