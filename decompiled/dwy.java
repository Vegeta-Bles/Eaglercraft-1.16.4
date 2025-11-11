import com.mojang.datafixers.util.Pair;
import java.net.IDN;
import java.util.Hashtable;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;

public class dwy {
   private final String a;
   private final int b;

   private dwy(String var1, int var2) {
      this.a = _snowman;
      this.b = _snowman;
   }

   public String a() {
      try {
         return IDN.toASCII(this.a);
      } catch (IllegalArgumentException var2) {
         return "";
      }
   }

   public int b() {
      return this.b;
   }

   public static dwy a(String var0) {
      if (_snowman == null) {
         return null;
      } else {
         String[] _snowman = _snowman.split(":");
         if (_snowman.startsWith("[")) {
            int _snowmanx = _snowman.indexOf("]");
            if (_snowmanx > 0) {
               String _snowmanxx = _snowman.substring(1, _snowmanx);
               String _snowmanxxx = _snowman.substring(_snowmanx + 1).trim();
               if (_snowmanxxx.startsWith(":") && !_snowmanxxx.isEmpty()) {
                  _snowmanxxx = _snowmanxxx.substring(1);
                  _snowman = new String[]{_snowmanxx, _snowmanxxx};
               } else {
                  _snowman = new String[]{_snowmanxx};
               }
            }
         }

         if (_snowman.length > 2) {
            _snowman = new String[]{_snowman};
         }

         String _snowmanx = _snowman[0];
         int _snowmanxx = _snowman.length > 1 ? a(_snowman[1], 25565) : 25565;
         if (_snowmanxx == 25565) {
            Pair<String, Integer> _snowmanxxx = b(_snowmanx);
            _snowmanx = (String)_snowmanxxx.getFirst();
            _snowmanxx = (Integer)_snowmanxxx.getSecond();
         }

         return new dwy(_snowmanx, _snowmanxx);
      }
   }

   private static Pair<String, Integer> b(String var0) {
      try {
         String _snowman = "com.sun.jndi.dns.DnsContextFactory";
         Class.forName("com.sun.jndi.dns.DnsContextFactory");
         Hashtable<String, String> _snowmanx = new Hashtable<>();
         _snowmanx.put("java.naming.factory.initial", "com.sun.jndi.dns.DnsContextFactory");
         _snowmanx.put("java.naming.provider.url", "dns:");
         _snowmanx.put("com.sun.jndi.dns.timeout.retries", "1");
         DirContext _snowmanxx = new InitialDirContext(_snowmanx);
         Attributes _snowmanxxx = _snowmanxx.getAttributes("_minecraft._tcp." + _snowman, new String[]{"SRV"});
         Attribute _snowmanxxxx = _snowmanxxx.get("srv");
         if (_snowmanxxxx != null) {
            String[] _snowmanxxxxx = _snowmanxxxx.get().toString().split(" ", 4);
            return Pair.of(_snowmanxxxxx[3], a(_snowmanxxxxx[2], 25565));
         }
      } catch (Throwable var7) {
      }

      return Pair.of(_snowman, 25565);
   }

   private static int a(String var0, int var1) {
      try {
         return Integer.parseInt(_snowman.trim());
      } catch (Exception var3) {
         return _snowman;
      }
   }
}
