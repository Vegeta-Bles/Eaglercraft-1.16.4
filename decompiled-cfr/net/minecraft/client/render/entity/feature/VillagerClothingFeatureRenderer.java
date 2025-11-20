/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  it.unimi.dsi.fastutil.ints.Int2ObjectMap
 *  it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap
 *  it.unimi.dsi.fastutil.objects.Object2ObjectMap
 *  it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap
 */
package net.minecraft.client.render.entity.feature;

import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.objects.Object2ObjectMap;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import java.io.IOException;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.feature.FeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.render.entity.feature.VillagerResourceMetadata;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.render.entity.model.ModelWithHat;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
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

public class VillagerClothingFeatureRenderer<T extends LivingEntity, M extends EntityModel<T>>
extends FeatureRenderer<T, M>
implements SynchronousResourceReloadListener {
    private static final Int2ObjectMap<Identifier> LEVEL_TO_ID = (Int2ObjectMap)Util.make(new Int2ObjectOpenHashMap(), int2ObjectOpenHashMap -> {
        int2ObjectOpenHashMap.put(1, (Object)new Identifier("stone"));
        int2ObjectOpenHashMap.put(2, (Object)new Identifier("iron"));
        int2ObjectOpenHashMap.put(3, (Object)new Identifier("gold"));
        int2ObjectOpenHashMap.put(4, (Object)new Identifier("emerald"));
        int2ObjectOpenHashMap.put(5, (Object)new Identifier("diamond"));
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

    @Override
    public void render(MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int n, T t, float f, float f2, float f3, float f4, float f5, float f6) {
        if (((Entity)t).isInvisible()) {
            return;
        }
        VillagerData villagerData = ((VillagerDataContainer)t).getVillagerData();
        VillagerType _snowman2 = villagerData.getType();
        VillagerProfession _snowman3 = villagerData.getProfession();
        VillagerResourceMetadata.HatType _snowman4 = this.getHatType(this.villagerTypeToHat, "type", Registry.VILLAGER_TYPE, _snowman2);
        VillagerResourceMetadata.HatType _snowman5 = this.getHatType(this.professionToHat, "profession", Registry.VILLAGER_PROFESSION, _snowman3);
        Object _snowman6 = this.getContextModel();
        ((ModelWithHat)_snowman6).setHatVisible(_snowman5 == VillagerResourceMetadata.HatType.NONE || _snowman5 == VillagerResourceMetadata.HatType.PARTIAL && _snowman4 != VillagerResourceMetadata.HatType.FULL);
        Identifier _snowman7 = this.findTexture("type", Registry.VILLAGER_TYPE.getId(_snowman2));
        VillagerClothingFeatureRenderer.renderModel(_snowman6, _snowman7, matrixStack, vertexConsumerProvider, n, t, 1.0f, 1.0f, 1.0f);
        ((ModelWithHat)_snowman6).setHatVisible(true);
        if (_snowman3 != VillagerProfession.NONE && !((LivingEntity)t).isBaby()) {
            Identifier identifier = this.findTexture("profession", Registry.VILLAGER_PROFESSION.getId(_snowman3));
            VillagerClothingFeatureRenderer.renderModel(_snowman6, identifier, matrixStack, vertexConsumerProvider, n, t, 1.0f, 1.0f, 1.0f);
            if (_snowman3 != VillagerProfession.NITWIT) {
                _snowman = this.findTexture("profession_level", (Identifier)LEVEL_TO_ID.get(MathHelper.clamp(villagerData.getLevel(), 1, LEVEL_TO_ID.size())));
                VillagerClothingFeatureRenderer.renderModel(_snowman6, _snowman, matrixStack, vertexConsumerProvider, n, t, 1.0f, 1.0f, 1.0f);
            }
        }
    }

    private Identifier findTexture(String keyType, Identifier keyId) {
        return new Identifier(keyId.getNamespace(), "textures/entity/" + this.entityType + "/" + keyType + "/" + keyId.getPath() + ".png");
    }

    public <K> VillagerResourceMetadata.HatType getHatType(Object2ObjectMap<K, VillagerResourceMetadata.HatType> hatLookUp, String keyType, DefaultedRegistry<K> registry, K key) {
        return (VillagerResourceMetadata.HatType)((Object)hatLookUp.computeIfAbsent(key, object2 -> {
            try (Resource resource = this.resourceManager.getResource(this.findTexture(keyType, registry.getId(key)));){
                VillagerResourceMetadata _snowman2 = resource.getMetadata(VillagerResourceMetadata.READER);
                if (_snowman2 == null) return VillagerResourceMetadata.HatType.NONE;
                VillagerResourceMetadata.HatType hatType = _snowman2.getHatType();
                return hatType;
            }
            catch (IOException iOException) {
                // empty catch block
            }
            return VillagerResourceMetadata.HatType.NONE;
        }));
    }

    @Override
    public void apply(ResourceManager manager) {
        this.professionToHat.clear();
        this.villagerTypeToHat.clear();
    }
}

