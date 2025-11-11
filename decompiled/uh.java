import java.io.IOException;
import java.security.PrivateKey;
import java.security.PublicKey;
import javax.crypto.SecretKey;

public class uh implements oj<ue> {
   private byte[] a = new byte[0];
   private byte[] b = new byte[0];

   public uh() {
   }

   public uh(SecretKey var1, PublicKey var2, byte[] var3) throws aev {
      this.a = aeu.a(_snowman, _snowman.getEncoded());
      this.b = aeu.a(_snowman, _snowman);
   }

   @Override
   public void a(nf var1) throws IOException {
      this.a = _snowman.a();
      this.b = _snowman.a();
   }

   @Override
   public void b(nf var1) throws IOException {
      _snowman.a(this.a);
      _snowman.a(this.b);
   }

   public void a(ue var1) {
      _snowman.a(this);
   }

   public SecretKey a(PrivateKey var1) throws aev {
      return aeu.a(_snowman, this.a);
   }

   public byte[] b(PrivateKey var1) throws aev {
      return aeu.b(_snowman, this.b);
   }
}
