package net.minecraft.text;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import javax.annotation.Nullable;
import net.minecraft.command.EntitySelector;
import net.minecraft.command.EntitySelectorReader;
import net.minecraft.entity.Entity;
import net.minecraft.server.command.ServerCommandSource;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class SelectorText extends BaseText implements ParsableText {
   private static final Logger LOGGER = LogManager.getLogger();
   private final String pattern;
   @Nullable
   private final EntitySelector selector;

   public SelectorText(String pattern) {
      this.pattern = pattern;
      EntitySelector _snowman = null;

      try {
         EntitySelectorReader _snowmanx = new EntitySelectorReader(new StringReader(pattern));
         _snowman = _snowmanx.read();
      } catch (CommandSyntaxException var4) {
         LOGGER.warn("Invalid selector component: {}", pattern, var4.getMessage());
      }

      this.selector = _snowman;
   }

   public String getPattern() {
      return this.pattern;
   }

   @Override
   public MutableText parse(@Nullable ServerCommandSource source, @Nullable Entity sender, int depth) throws CommandSyntaxException {
      return (MutableText)(source != null && this.selector != null ? EntitySelector.getNames(this.selector.getEntities(source)) : new LiteralText(""));
   }

   @Override
   public String asString() {
      return this.pattern;
   }

   public SelectorText copy() {
      return new SelectorText(this.pattern);
   }

   @Override
   public boolean equals(Object _snowman) {
      if (this == _snowman) {
         return true;
      } else if (!(_snowman instanceof SelectorText)) {
         return false;
      } else {
         SelectorText _snowmanx = (SelectorText)_snowman;
         return this.pattern.equals(_snowmanx.pattern) && super.equals(_snowman);
      }
   }

   @Override
   public String toString() {
      return "SelectorComponent{pattern='" + this.pattern + '\'' + ", siblings=" + this.siblings + ", style=" + this.getStyle() + '}';
   }
}
