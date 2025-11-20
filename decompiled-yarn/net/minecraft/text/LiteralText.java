package net.minecraft.text;

public class LiteralText extends BaseText {
   public static final Text EMPTY = new LiteralText("");
   private final String string;

   public LiteralText(String string) {
      this.string = string;
   }

   public String getRawString() {
      return this.string;
   }

   @Override
   public String asString() {
      return this.string;
   }

   public LiteralText copy() {
      return new LiteralText(this.string);
   }

   @Override
   public boolean equals(Object _snowman) {
      if (this == _snowman) {
         return true;
      } else if (!(_snowman instanceof LiteralText)) {
         return false;
      } else {
         LiteralText _snowmanx = (LiteralText)_snowman;
         return this.string.equals(_snowmanx.getRawString()) && super.equals(_snowman);
      }
   }

   @Override
   public String toString() {
      return "TextComponent{text='" + this.string + '\'' + ", siblings=" + this.siblings + ", style=" + this.getStyle() + '}';
   }
}
