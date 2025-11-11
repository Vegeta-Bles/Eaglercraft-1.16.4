public class dom extends dot {
   public dom() {
      super(new oe("Out of memory!"));
   }

   @Override
   protected void b() {
      this.a(new dlj(this.k / 2 - 155, this.l / 4 + 120 + 12, 150, 20, new of("gui.toTitle"), var1 -> this.i.a(new doy())));
      this.a(new dlj(this.k / 2 - 155 + 160, this.l / 4 + 120 + 12, 150, 20, new of("menu.quit"), var1 -> this.i.n()));
   }

   @Override
   public boolean as_() {
      return false;
   }

   @Override
   public void a(dfm var1, int var2, int var3, float var4) {
      this.a(_snowman);
      a(_snowman, this.o, this.d, this.k / 2, this.l / 4 - 60 + 20, 16777215);
      b(_snowman, this.o, "Minecraft has run out of memory.", this.k / 2 - 140, this.l / 4 - 60 + 60 + 0, 10526880);
      b(_snowman, this.o, "This could be caused by a bug in the game or by the", this.k / 2 - 140, this.l / 4 - 60 + 60 + 18, 10526880);
      b(_snowman, this.o, "Java Virtual Machine not being allocated enough", this.k / 2 - 140, this.l / 4 - 60 + 60 + 27, 10526880);
      b(_snowman, this.o, "memory.", this.k / 2 - 140, this.l / 4 - 60 + 60 + 36, 10526880);
      b(_snowman, this.o, "To prevent level corruption, the current game has quit.", this.k / 2 - 140, this.l / 4 - 60 + 60 + 54, 10526880);
      b(_snowman, this.o, "We've tried to free up enough memory to let you go back to", this.k / 2 - 140, this.l / 4 - 60 + 60 + 63, 10526880);
      b(_snowman, this.o, "the main menu and back to playing, but this may not have worked.", this.k / 2 - 140, this.l / 4 - 60 + 60 + 72, 10526880);
      b(_snowman, this.o, "Please restart the game if_ you see this message again.", this.k / 2 - 140, this.l / 4 - 60 + 60 + 81, 10526880);
      super.a(_snowman, _snowman, _snowman, _snowman);
   }
}
