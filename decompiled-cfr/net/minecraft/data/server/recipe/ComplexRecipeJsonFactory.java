/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.gson.JsonObject
 *  javax.annotation.Nullable
 */
package net.minecraft.data.server.recipe;

import com.google.gson.JsonObject;
import java.util.function.Consumer;
import javax.annotation.Nullable;
import net.minecraft.data.server.recipe.RecipeJsonProvider;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.SpecialRecipeSerializer;
import net.minecraft.util.Identifier;

public class ComplexRecipeJsonFactory {
    private final SpecialRecipeSerializer<?> serializer;

    public ComplexRecipeJsonFactory(SpecialRecipeSerializer<?> serializer) {
        this.serializer = serializer;
    }

    public static ComplexRecipeJsonFactory create(SpecialRecipeSerializer<?> serializer) {
        return new ComplexRecipeJsonFactory(serializer);
    }

    public void offerTo(Consumer<RecipeJsonProvider> exporter, String recipeId) {
        exporter.accept(new RecipeJsonProvider(this, recipeId){
            final /* synthetic */ String field_11430;
            final /* synthetic */ ComplexRecipeJsonFactory field_11431;
            {
                this.field_11431 = complexRecipeJsonFactory;
                this.field_11430 = string;
            }

            public void serialize(JsonObject json) {
            }

            public RecipeSerializer<?> getSerializer() {
                return ComplexRecipeJsonFactory.method_10474(this.field_11431);
            }

            public Identifier getRecipeId() {
                return new Identifier(this.field_11430);
            }

            @Nullable
            public JsonObject toAdvancementJson() {
                return null;
            }

            public Identifier getAdvancementId() {
                return new Identifier("");
            }
        });
    }

    static /* synthetic */ SpecialRecipeSerializer method_10474(ComplexRecipeJsonFactory complexRecipeJsonFactory) {
        return complexRecipeJsonFactory.serializer;
    }
}

