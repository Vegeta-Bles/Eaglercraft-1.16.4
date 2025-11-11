import com.google.common.collect.Maps;
import java.util.Map;
import java.util.stream.Stream;

public class cya {
   private final Map<String, cya.a> a = Maps.newHashMap();
   private final cyc b;

   public cya(cyc var1) {
      this.b = _snowman;
   }

   private cya.a a(String var1, String var2) {
      cya.a _snowman = new cya.a(_snowman);
      this.a.put(_snowman, _snowman);
      return _snowman;
   }

   public md a(vk var1) {
      String _snowman = _snowman.b();
      String _snowmanx = a(_snowman);
      cya.a _snowmanxx = this.b.b(() -> this.a(_snowman, _snowman), _snowmanx);
      return _snowmanxx != null ? _snowmanxx.a(_snowman.a()) : new md();
   }

   public void a(vk var1, md var2) {
      String _snowman = _snowman.b();
      String _snowmanx = a(_snowman);
      this.b.a(() -> this.a(_snowman, _snowman), _snowmanx).a(_snowman.a(), _snowman);
   }

   public Stream<vk> a() {
      return this.a.entrySet().stream().flatMap(var0 -> var0.getValue().b(var0.getKey()));
   }

   private static String a(String var0) {
      return "command_storage_" + _snowman;
   }

   static class a extends cxs {
      private final Map<String, md> a = Maps.newHashMap();

      public a(String var1) {
         super(_snowman);
      }

      @Override
      public void a(md var1) {
         md _snowman = _snowman.p("contents");

         for (String _snowmanx : _snowman.d()) {
            this.a.put(_snowmanx, _snowman.p(_snowmanx));
         }
      }

      @Override
      public md b(md var1) {
         md _snowman = new md();
         this.a.forEach((var1x, var2x) -> _snowman.a(var1x, var2x.g()));
         _snowman.a("contents", _snowman);
         return _snowman;
      }

      public md a(String var1) {
         md _snowman = this.a.get(_snowman);
         return _snowman != null ? _snowman : new md();
      }

      public void a(String var1, md var2) {
         if (_snowman.isEmpty()) {
            this.a.remove(_snowman);
         } else {
            this.a.put(_snowman, _snowman);
         }

         this.b();
      }

      public Stream<vk> b(String var1) {
         return this.a.keySet().stream().map(var1x -> new vk(_snowman, var1x));
      }
   }
}
