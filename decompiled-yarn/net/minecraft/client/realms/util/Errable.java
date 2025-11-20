package net.minecraft.client.realms.util;

import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;

public interface Errable {
   void error(Text var1);

   default void error(String _snowman) {
      this.error(new LiteralText(_snowman));
   }
}
