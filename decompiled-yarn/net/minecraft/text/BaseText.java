package net.minecraft.text;

import com.google.common.collect.Lists;
import java.util.List;
import java.util.Objects;
import javax.annotation.Nullable;
import net.minecraft.util.Language;

public abstract class BaseText implements MutableText {
   protected final List<Text> siblings = Lists.newArrayList();
   private OrderedText orderedText = OrderedText.EMPTY;
   @Nullable
   private Language previousLanguage;
   private Style style = Style.EMPTY;

   public BaseText() {
   }

   @Override
   public MutableText append(Text text) {
      this.siblings.add(text);
      return this;
   }

   @Override
   public String asString() {
      return "";
   }

   @Override
   public List<Text> getSiblings() {
      return this.siblings;
   }

   @Override
   public MutableText setStyle(Style style) {
      this.style = style;
      return this;
   }

   @Override
   public Style getStyle() {
      return this.style;
   }

   public abstract BaseText copy();

   @Override
   public final MutableText shallowCopy() {
      BaseText _snowman = this.copy();
      _snowman.siblings.addAll(this.siblings);
      _snowman.setStyle(this.style);
      return _snowman;
   }

   @Override
   public OrderedText asOrderedText() {
      Language _snowman = Language.getInstance();
      if (this.previousLanguage != _snowman) {
         this.orderedText = _snowman.reorder(this);
         this.previousLanguage = _snowman;
      }

      return this.orderedText;
   }

   @Override
   public boolean equals(Object obj) {
      if (this == obj) {
         return true;
      } else if (!(obj instanceof BaseText)) {
         return false;
      } else {
         BaseText _snowman = (BaseText)obj;
         return this.siblings.equals(_snowman.siblings) && Objects.equals(this.getStyle(), _snowman.getStyle());
      }
   }

   @Override
   public int hashCode() {
      return Objects.hash(this.getStyle(), this.siblings);
   }

   @Override
   public String toString() {
      return "BaseComponent{style=" + this.style + ", siblings=" + this.siblings + '}';
   }
}
