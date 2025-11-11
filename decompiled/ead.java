import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.objects.ObjectIterator;
import java.util.Map.Entry;
import javax.annotation.Nullable;

public class ead {
   public final Int2ObjectMap<elu> a = new Int2ObjectOpenHashMap(256);
   private final Int2ObjectMap<elo> b = new Int2ObjectOpenHashMap(256);
   private final elt c;

   public ead(elt var1) {
      this.c = _snowman;
   }

   public ekc a(brw var1) {
      return this.a(new bmb(_snowman));
   }

   public ekc a(bmb var1) {
      elo _snowman = this.b(_snowman);
      return _snowman == this.c.a() && _snowman.b() instanceof bkh ? this.c.b().a(((bkh)_snowman.b()).e().n()) : _snowman.e();
   }

   public elo b(bmb var1) {
      elo _snowman = this.a(_snowman.b());
      return _snowman == null ? this.c.a() : _snowman;
   }

   @Nullable
   public elo a(blx var1) {
      return (elo)this.b.get(b(_snowman));
   }

   private static int b(blx var0) {
      return blx.a(_snowman);
   }

   public void a(blx var1, elu var2) {
      this.a.put(b(_snowman), _snowman);
   }

   public elt a() {
      return this.c;
   }

   public void b() {
      this.b.clear();
      ObjectIterator var1 = this.a.entrySet().iterator();

      while (var1.hasNext()) {
         Entry<Integer, elu> _snowman = (Entry<Integer, elu>)var1.next();
         this.b.put(_snowman.getKey(), this.c.a(_snowman.getValue()));
      }
   }
}
