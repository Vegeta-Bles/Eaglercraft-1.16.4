/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.ImmutableList
 *  com.google.common.collect.Lists
 */
package net.minecraft.network.packet.s2c.play;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import java.io.IOException;
import java.util.Collection;
import java.util.List;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.recipe.book.RecipeBookOptions;
import net.minecraft.util.Identifier;

public class UnlockRecipesS2CPacket
implements Packet<ClientPlayPacketListener> {
    private Action action;
    private List<Identifier> recipeIdsToChange;
    private List<Identifier> recipeIdsToInit;
    private RecipeBookOptions options;

    public UnlockRecipesS2CPacket() {
    }

    public UnlockRecipesS2CPacket(Action action, Collection<Identifier> collection, Collection<Identifier> collection2, RecipeBookOptions options) {
        this.action = action;
        this.recipeIdsToChange = ImmutableList.copyOf(collection);
        this.recipeIdsToInit = ImmutableList.copyOf(collection2);
        this.options = options;
    }

    @Override
    public void apply(ClientPlayPacketListener clientPlayPacketListener) {
        clientPlayPacketListener.onUnlockRecipes(this);
    }

    @Override
    public void read(PacketByteBuf buf) throws IOException {
        this.action = buf.readEnumConstant(Action.class);
        this.options = RecipeBookOptions.fromPacket(buf);
        int n = buf.readVarInt();
        this.recipeIdsToChange = Lists.newArrayList();
        for (_snowman = 0; _snowman < n; ++_snowman) {
            this.recipeIdsToChange.add(buf.readIdentifier());
        }
        if (this.action == Action.INIT) {
            n = buf.readVarInt();
            this.recipeIdsToInit = Lists.newArrayList();
            for (_snowman = 0; _snowman < n; ++_snowman) {
                this.recipeIdsToInit.add(buf.readIdentifier());
            }
        }
    }

    @Override
    public void write(PacketByteBuf buf) throws IOException {
        buf.writeEnumConstant(this.action);
        this.options.toPacket(buf);
        buf.writeVarInt(this.recipeIdsToChange.size());
        for (Identifier identifier : this.recipeIdsToChange) {
            buf.writeIdentifier(identifier);
        }
        if (this.action == Action.INIT) {
            buf.writeVarInt(this.recipeIdsToInit.size());
            for (Identifier identifier : this.recipeIdsToInit) {
                buf.writeIdentifier(identifier);
            }
        }
    }

    public List<Identifier> getRecipeIdsToChange() {
        return this.recipeIdsToChange;
    }

    public List<Identifier> getRecipeIdsToInit() {
        return this.recipeIdsToInit;
    }

    public RecipeBookOptions getOptions() {
        return this.options;
    }

    public Action getAction() {
        return this.action;
    }

    public static enum Action {
        INIT,
        ADD,
        REMOVE;

    }
}

