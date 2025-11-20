package net.minecraft.client.particle;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Streams;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.util.Identifier;
import net.minecraft.util.JsonHelper;

public class ParticleTextureData {
   @Nullable
   private final List<Identifier> textureList;

   private ParticleTextureData(@Nullable List<Identifier> textureList) {
      this.textureList = textureList;
   }

   @Nullable
   public List<Identifier> getTextureList() {
      return this.textureList;
   }

   public static ParticleTextureData load(JsonObject _snowman) {
      JsonArray _snowmanx = JsonHelper.getArray(_snowman, "textures", null);
      List<Identifier> _snowmanxx;
      if (_snowmanx != null) {
         _snowmanxx = Streams.stream(_snowmanx).map(_snowmanxxx -> JsonHelper.asString(_snowmanxxx, "texture")).map(Identifier::new).collect(ImmutableList.toImmutableList());
      } else {
         _snowmanxx = null;
      }

      return new ParticleTextureData(_snowmanxx);
   }
}
