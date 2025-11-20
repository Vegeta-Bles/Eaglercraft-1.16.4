package net.minecraft.client.render.entity.feature;

import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.objects.Object2ObjectMap;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import java.io.IOException;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.render.entity.model.ModelWithHat;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.LivingEntity;
import net.minecraft.resource.ReloadableResourceManager;
import net.minecraft.resource.Resource;
import net.minecraft.resource.ResourceManager;
import net.minecraft.resource.SynchronousResourceReloadListener;
import net.minecraft.util.Identifier;
import net.minecraft.util.Util;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.registry.DefaultedRegistry;
import net.minecraft.util.registry.Registry;
import net.minecraft.village.VillagerData;
import net.minecraft.village.VillagerDataContainer;
import net.minecraft.village.VillagerProfession;
import net.minecraft.village.VillagerType;

public class VillagerClothingFeatureRenderer<T extends LivingEntity & VillagerDataContainer, M extends EntityModel<T> & ModelWithHat>
   extends FeatureRenderer<T, M>
   implements SynchronousResourceReloadListener {
   private static final Int2ObjectMap<Identifier> LEVEL_TO_ID = Util.make(new Int2ObjectOpenHashMap(), _snowman -> {
      _snowman.put(1, new Identifier("stone"));
      _snowman.put(2, new Identifier("iron"));
      _snowman.put(3, new Identifier("gold"));
      _snowman.put(4, new Identifier("emerald"));
      _snowman.put(5, new Identifier("diamond"));
   });
   private final Object2ObjectMap<VillagerType, VillagerResourceMetadata.HatType> villagerTypeToHat = new Object2ObjectOpenHashMap();
   private final Object2ObjectMap<VillagerProfession, VillagerResourceMetadata.HatType> professionToHat = new Object2ObjectOpenHashMap();
   private final ReloadableResourceManager resourceManager;
   private final String entityType;

   public VillagerClothingFeatureRenderer(FeatureRendererContext<T, M> context, ReloadableResourceManager resourceManager, String entityType) {
      super(context);
      this.resourceManager = resourceManager;
      this.entityType = entityType;
      resourceManager.registerListener(this);
   }

   public void render(MatrixStack _snowman, VertexConsumerProvider _snowman, int _snowman, T _snowman, float _snowman, float _snowman, float _snowman, float _snowman, float _snowman, float _snowman) {
      if (!_snowman.isInvisible()) {
         VillagerData _snowmanxxxxxxxxxx = _snowman.getVillagerData();
         VillagerType _snowmanxxxxxxxxxxx = _snowmanxxxxxxxxxx.getType();
         VillagerProfession _snowmanxxxxxxxxxxxx = _snowmanxxxxxxxxxx.getProfession();
         VillagerResourceMetadata.HatType _snowmanxxxxxxxxxxxxx = this.getHatType(this.villagerTypeToHat, "type", Registry.VILLAGER_TYPE, _snowmanxxxxxxxxxxx);
         VillagerResourceMetadata.HatType _snowmanxxxxxxxxxxxxxx = this.getHatType(this.professionToHat, "profession", Registry.VILLAGER_PROFESSION, _snowmanxxxxxxxxxxxx);
         M _snowmanxxxxxxxxxxxxxxx = this.getContextModel();
         _snowmanxxxxxxxxxxxxxxx.setHatVisible(
            _snowmanxxxxxxxxxxxxxx == VillagerResourceMetadata.HatType.NONE
               || _snowmanxxxxxxxxxxxxxx == VillagerResourceMetadata.HatType.PARTIAL && _snowmanxxxxxxxxxxxxx != VillagerResourceMetadata.HatType.FULL
         );
         Identifier _snowmanxxxxxxxxxxxxxxxx = this.findTexture("type", Registry.VILLAGER_TYPE.getId(_snowmanxxxxxxxxxxx));
         renderModel(_snowmanxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxxx, _snowman, _snowman, _snowman, _snowman, 1.0F, 1.0F, 1.0F);
         _snowmanxxxxxxxxxxxxxxx.setHatVisible(true);
         if (_snowmanxxxxxxxxxxxx != VillagerProfession.NONE && !_snowman.isBaby()) {
            Identifier _snowmanxxxxxxxxxxxxxxxxx = this.findTexture("profession", Registry.VILLAGER_PROFESSION.getId(_snowmanxxxxxxxxxxxx));
            renderModel(_snowmanxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxxxx, _snowman, _snowman, _snowman, _snowman, 1.0F, 1.0F, 1.0F);
            if (_snowmanxxxxxxxxxxxx != VillagerProfession.NITWIT) {
               Identifier _snowmanxxxxxxxxxxxxxxxxxx = this.findTexture(
                  "profession_level", (Identifier)LEVEL_TO_ID.get(MathHelper.clamp(_snowmanxxxxxxxxxx.getLevel(), 1, LEVEL_TO_ID.size()))
               );
               renderModel(_snowmanxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxxxxx, _snowman, _snowman, _snowman, _snowman, 1.0F, 1.0F, 1.0F);
            }
         }
      }
   }

   private Identifier findTexture(String keyType, Identifier keyId) {
      return new Identifier(keyId.getNamespace(), "textures/entity/" + this.entityType + "/" + keyType + "/" + keyId.getPath() + ".png");
   }

   public <K> VillagerResourceMetadata.HatType getHatType(
      Object2ObjectMap<K, VillagerResourceMetadata.HatType> hatLookUp, String keyType, DefaultedRegistry<K> registry, K key
   ) {
      return (VillagerResourceMetadata.HatType)hatLookUp.computeIfAbsent(key, _snowmanxxx -> {
         try (Resource _snowmanxxxx = this.resourceManager.getResource(this.findTexture(keyType, registry.getId(key)))) {
            VillagerResourceMetadata _snowmanx = _snowmanxxxx.getMetadata(VillagerResourceMetadata.READER);
            if (_snowmanx != null) {
               return _snowmanx.getHatType();
            }
         } catch (IOException var21) {
         }

         return VillagerResourceMetadata.HatType.NONE;
      });
   }

   @Override
   public void apply(ResourceManager manager) {
      this.professionToHat.clear();
      this.villagerTypeToHat.clear();
   }
}
