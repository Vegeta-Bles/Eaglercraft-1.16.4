package net.minecraft.particle;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.serialization.Codec;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.util.registry.Registry;

public class DefaultParticleType extends ParticleType<DefaultParticleType> implements ParticleEffect {
   private static final ParticleEffect.Factory<DefaultParticleType> PARAMETER_FACTORY = new ParticleEffect.Factory<DefaultParticleType>() {
      public DefaultParticleType read(ParticleType<DefaultParticleType> arg, StringReader stringReader) throws CommandSyntaxException {
         return (DefaultParticleType)arg;
      }

      public DefaultParticleType read(ParticleType<DefaultParticleType> arg, PacketByteBuf arg2) {
         return (DefaultParticleType)arg;
      }
   };
   private final Codec<DefaultParticleType> codec = Codec.unit(this::getType);

   protected DefaultParticleType(boolean alwaysShow) {
      super(alwaysShow, PARAMETER_FACTORY);
   }

   public DefaultParticleType getType() {
      return this;
   }

   @Override
   public Codec<DefaultParticleType> getCodec() {
      return this.codec;
   }

   @Override
   public void write(PacketByteBuf buf) {
   }

   @Override
   public String asString() {
      return Registry.PARTICLE_TYPE.getId(this).toString();
   }
}
