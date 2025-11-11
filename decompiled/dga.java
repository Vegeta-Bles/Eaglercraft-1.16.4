import com.google.common.collect.Lists;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.util.Comparator;
import java.util.List;

public class dga {
   public static List<dgz> a(dga.a... var0) {
      for (dga.a _snowman : _snowman) {
         a(_snowman.j);
      }

      List<dgz> _snowman = Lists.newArrayList();

      for (dga.a _snowmanx : _snowman) {
         _snowman.add(new dgz(_snowmanx.i, a(_snowmanx.j)));
      }

      _snowman.sort(Comparator.comparingInt(dgz::a));
      return _snowman;
   }

   private static int a(String var0) {
      int _snowman = 700;
      long _snowmanx = 0L;
      Socket _snowmanxx = null;

      for (int _snowmanxxx = 0; _snowmanxxx < 5; _snowmanxxx++) {
         try {
            SocketAddress _snowmanxxxx = new InetSocketAddress(_snowman, 80);
            _snowmanxx = new Socket();
            long _snowmanxxxxx = b();
            _snowmanxx.connect(_snowmanxxxx, 700);
            _snowmanx += b() - _snowmanxxxxx;
         } catch (Exception var12) {
            _snowmanx += 700L;
         } finally {
            a(_snowmanxx);
         }
      }

      return (int)((double)_snowmanx / 5.0);
   }

   private static void a(Socket var0) {
      try {
         if (_snowman != null) {
            _snowman.close();
         }
      } catch (Throwable var2) {
      }
   }

   private static long b() {
      return x.b();
   }

   public static List<dgz> a() {
      return a(dga.a.values());
   }

   static enum a {
      a("us-east-1", "ec2.us-east-1.amazonaws.com"),
      b("us-west-2", "ec2.us-west-2.amazonaws.com"),
      c("us-west-1", "ec2.us-west-1.amazonaws.com"),
      d("eu-west-1", "ec2.eu-west-1.amazonaws.com"),
      e("ap-southeast-1", "ec2.ap-southeast-1.amazonaws.com"),
      f("ap-southeast-2", "ec2.ap-southeast-2.amazonaws.com"),
      g("ap-northeast-1", "ec2.ap-northeast-1.amazonaws.com"),
      h("sa-east-1", "ec2.sa-east-1.amazonaws.com");

      private final String i;
      private final String j;

      private a(String var3, String var4) {
         this.i = _snowman;
         this.j = _snowman;
      }
   }
}
