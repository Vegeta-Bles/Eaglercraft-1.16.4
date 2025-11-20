package net.minecraft.text;

import com.google.common.collect.Lists;
import java.util.List;
import java.util.Objects;
import javax.annotation.Nullable;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.util.Language;

public abstract class BaseText implements MutableText {
   protected final List<Text> siblings = Lists.newArrayList();
   private OrderedText orderedText = OrderedText.EMPTY;
   @Nullable
   @Environment(EnvType.CLIENT)
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
      BaseText lv = this.copy();
      lv.siblings.addAll(this.siblings);
      lv.setStyle(this.style);
      return lv;
   }

   @Environment(EnvType.CLIENT)
   @Override
   public OrderedText asOrderedText() {
      Language lv = Language.getInstance();
      if (this.previousLanguage != lv) {
         this.orderedText = lv.reorder(this);
         this.previousLanguage = lv;
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
         BaseText lv = (BaseText)obj;
         return this.siblings.equals(lv.siblings) && Objects.equals(this.getStyle(), lv.getStyle());
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
