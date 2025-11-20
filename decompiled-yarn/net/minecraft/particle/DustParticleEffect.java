package net.minecraft.particle;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.Locale;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.registry.Registry;

public class DustParticleEffect implements ParticleEffect {
   public static final DustParticleEffect RED = new DustParticleEffect(1.0F, 0.0F, 0.0F, 1.0F);
   public static final Codec<DustParticleEffect> CODEC = RecordCodecBuilder.create(
      _snowman -> _snowman.group(
               Codec.FLOAT.fieldOf("r").forGetter(_snowmanx -> _snowmanx.red),
               Codec.FLOAT.fieldOf("g").forGetter(_snowmanx -> _snowmanx.green),
               Codec.FLOAT.fieldOf("b").forGetter(_snowmanx -> _snowmanx.blue),
               Codec.FLOAT.fieldOf("scale").forGetter(_snowmanx -> _snowmanx.scale)
            )
            .apply(_snowman, DustParticleEffect::new)
   );
   public static final ParticleEffect.Factory<DustParticleEffect> PARAMETERS_FACTORY = new ParticleEffect.Factory<DustParticleEffect>() {
      public DustParticleEffect read(ParticleType<DustParticleEffect> _snowman, StringReader _snowman) throws CommandSyntaxException {
         _snowman.expect(' ');
         float _snowmanxx = (float)_snowman.readDouble();
         _snowman.expect(' ');
         float _snowmanxxx = (float)_snowman.readDouble();
         _snowman.expect(' ');
         float _snowmanxxxx = (float)_snowman.readDouble();
         _snowman.expect(' ');
         float _snowmanxxxxx = (float)_snowman.readDouble();
         return new DustParticleEffect(_snowmanxx, _snowmanxxx, _snowmanxxxx, _snowmanxxxxx);
      }

      public DustParticleEffect read(ParticleType<DustParticleEffect> _snowman, PacketByteBuf _snowman) {
         return new DustParticleEffect(_snowman.readFloat(), _snowman.readFloat(), _snowman.readFloat(), _snowman.readFloat());
      }
   };
   private final float red;
   private final float green;
   private final float blue;
   private final float scale;

   public DustParticleEffect(float red, float green, float blue, float scale) {
      this.red = red;
      this.green = green;
      this.blue = blue;
      this.scale = MathHelper.clamp(scale, 0.01F, 4.0F);
   }

   @Override
   public void write(PacketByteBuf buf) {
      buf.writeFloat(this.red);
      buf.writeFloat(this.green);
      buf.writeFloat(this.blue);
      buf.writeFloat(this.scale);
   }

   @Override
   public String asString() {
      return String.format(Locale.ROOT, "%s %.2f %.2f %.2f %.2f", Registry.PARTICLE_TYPE.getId(this.getType()), this.red, this.green, this.blue, this.scale);
   }

   @Override
   public ParticleType<DustParticleEffect> getType() {
      return ParticleTypes.DUST;
   }

   public float getRed() {
      return this.red;
   }

   public float getGreen() {
      return this.green;
   }

   public float getBlue() {
      return this.blue;
   }

   public float getScale() {
      return this.scale;
   }
}
