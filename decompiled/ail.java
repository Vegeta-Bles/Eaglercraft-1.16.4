import com.mojang.datafixers.schemas.Schema;
import java.util.Objects;

public class ail extends akw {
   public ail(Schema var1, boolean var2) {
      super("EntityTippedArrowFix", _snowman, _snowman);
   }

   @Override
   protected String a(String var1) {
      return Objects.equals(_snowman, "TippedArrow") ? "Arrow" : _snowman;
   }
}
