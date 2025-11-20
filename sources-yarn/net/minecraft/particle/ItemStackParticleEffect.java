package net.minecraft.particle;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.serialization.Codec;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.command.argument.ItemStackArgument;
import net.minecraft.command.argument.ItemStringReader;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.util.registry.Registry;

public class ItemStackParticleEffect implements ParticleEffect {
   public static final ParticleEffect.Factory<ItemStackParticleEffect> PARAMETERS_FACTORY = new ParticleEffect.Factory<ItemStackParticleEffect>() {
      public ItemStackParticleEffect read(ParticleType<ItemStackParticleEffect> arg, StringReader stringReader) throws CommandSyntaxException {
         stringReader.expect(' ');
         ItemStringReader lv = new ItemStringReader(stringReader, false).consume();
         ItemStack lv2 = new ItemStackArgument(lv.getItem(), lv.getTag()).createStack(1, false);
         return new ItemStackParticleEffect(arg, lv2);
      }

      public ItemStackParticleEffect read(ParticleType<ItemStackParticleEffect> arg, PacketByteBuf arg2) {
         return new ItemStackParticleEffect(arg, arg2.readItemStack());
      }
   };
   private final ParticleType<ItemStackParticleEffect> type;
   private final ItemStack stack;

   public static Codec<ItemStackParticleEffect> method_29136(ParticleType<ItemStackParticleEffect> arg) {
      return ItemStack.CODEC.xmap(arg2 -> new ItemStackParticleEffect(arg, arg2), argx -> argx.stack);
   }

   public ItemStackParticleEffect(ParticleType<ItemStackParticleEffect> type, ItemStack stack) {
      this.type = type;
      this.stack = stack;
   }

   @Override
   public void write(PacketByteBuf buf) {
      buf.writeItemStack(this.stack);
   }

   @Override
   public String asString() {
      return Registry.PARTICLE_TYPE.getId(this.getType()) + " " + new ItemStackArgument(this.stack.getItem(), this.stack.getTag()).asString();
   }

   @Override
   public ParticleType<ItemStackParticleEffect> getType() {
      return this.type;
   }

   @Environment(EnvType.CLIENT)
   public ItemStack getItemStack() {
      return this.stack;
   }
}
