package net.minecraft.client.util;

import com.google.common.collect.Lists;
import java.util.List;
import javax.annotation.Nullable;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.text.StringVisitable;

@Environment(EnvType.CLIENT)
public class TextCollector {
   private final List<StringVisitable> field_25260 = Lists.newArrayList();

   public TextCollector() {
   }

   public void add(StringVisitable arg) {
      this.field_25260.add(arg);
   }

   @Nullable
   public StringVisitable getRawCombined() {
      if (this.field_25260.isEmpty()) {
         return null;
      } else {
         return this.field_25260.size() == 1 ? this.field_25260.get(0) : StringVisitable.concat(this.field_25260);
      }
   }

   public StringVisitable getCombined() {
      StringVisitable lv = this.getRawCombined();
      return lv != null ? lv : StringVisitable.EMPTY;
   }
}
