/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.gson.JsonElement
 *  com.google.gson.JsonObject
 *  com.google.gson.JsonParseException
 *  com.google.gson.JsonSyntaxException
 *  com.mojang.brigadier.exceptions.CommandSyntaxException
 *  javax.annotation.Nullable
 */
package net.minecraft.advancement;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSyntaxException;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import javax.annotation.Nullable;
import net.minecraft.advancement.AdvancementFrame;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.StringNbtReader;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.JsonHelper;
import net.minecraft.util.registry.Registry;

public class AdvancementDisplay {
    private final Text title;
    private final Text description;
    private final ItemStack icon;
    private final Identifier background;
    private final AdvancementFrame frame;
    private final boolean showToast;
    private final boolean announceToChat;
    private final boolean hidden;
    private float xPos;
    private float yPos;

    public AdvancementDisplay(ItemStack icon, Text title, Text description, @Nullable Identifier background, AdvancementFrame frame, boolean showToast, boolean announceToChat, boolean hidden) {
        this.title = title;
        this.description = description;
        this.icon = icon;
        this.background = background;
        this.frame = frame;
        this.showToast = showToast;
        this.announceToChat = announceToChat;
        this.hidden = hidden;
    }

    public void setPosition(float xPos, float yPos) {
        this.xPos = xPos;
        this.yPos = yPos;
    }

    public Text getTitle() {
        return this.title;
    }

    public Text getDescription() {
        return this.description;
    }

    public ItemStack getIcon() {
        return this.icon;
    }

    @Nullable
    public Identifier getBackground() {
        return this.background;
    }

    public AdvancementFrame getFrame() {
        return this.frame;
    }

    public float getX() {
        return this.xPos;
    }

    public float getY() {
        return this.yPos;
    }

    public boolean shouldShowToast() {
        return this.showToast;
    }

    public boolean shouldAnnounceToChat() {
        return this.announceToChat;
    }

    public boolean isHidden() {
        return this.hidden;
    }

    public static AdvancementDisplay fromJson(JsonObject obj) {
        MutableText mutableText = Text.Serializer.fromJson(obj.get("title"));
        _snowman = Text.Serializer.fromJson(obj.get("description"));
        if (mutableText == null || _snowman == null) {
            throw new JsonSyntaxException("Both title and description must be set");
        }
        ItemStack _snowman2 = AdvancementDisplay.iconFromJson(JsonHelper.getObject(obj, "icon"));
        Identifier _snowman3 = obj.has("background") ? new Identifier(JsonHelper.getString(obj, "background")) : null;
        AdvancementFrame _snowman4 = obj.has("frame") ? AdvancementFrame.forName(JsonHelper.getString(obj, "frame")) : AdvancementFrame.TASK;
        boolean _snowman5 = JsonHelper.getBoolean(obj, "show_toast", true);
        boolean _snowman6 = JsonHelper.getBoolean(obj, "announce_to_chat", true);
        boolean _snowman7 = JsonHelper.getBoolean(obj, "hidden", false);
        return new AdvancementDisplay(_snowman2, mutableText, _snowman, _snowman3, _snowman4, _snowman5, _snowman6, _snowman7);
    }

    private static ItemStack iconFromJson(JsonObject json) {
        if (!json.has("item")) {
            throw new JsonSyntaxException("Unsupported icon type, currently only items are supported (add 'item' key)");
        }
        Item item = JsonHelper.getItem(json, "item");
        if (json.has("data")) {
            throw new JsonParseException("Disallowed data tag found");
        }
        ItemStack _snowman2 = new ItemStack(item);
        if (json.has("nbt")) {
            try {
                CompoundTag compoundTag = StringNbtReader.parse(JsonHelper.asString(json.get("nbt"), "nbt"));
                _snowman2.setTag(compoundTag);
            }
            catch (CommandSyntaxException commandSyntaxException) {
                throw new JsonSyntaxException("Invalid nbt tag: " + commandSyntaxException.getMessage());
            }
        }
        return _snowman2;
    }

    public void toPacket(PacketByteBuf buf) {
        buf.writeText(this.title);
        buf.writeText(this.description);
        buf.writeItemStack(this.icon);
        buf.writeEnumConstant(this.frame);
        int n = 0;
        if (this.background != null) {
            n |= 1;
        }
        if (this.showToast) {
            n |= 2;
        }
        if (this.hidden) {
            n |= 4;
        }
        buf.writeInt(n);
        if (this.background != null) {
            buf.writeIdentifier(this.background);
        }
        buf.writeFloat(this.xPos);
        buf.writeFloat(this.yPos);
    }

    public static AdvancementDisplay fromPacket(PacketByteBuf buf) {
        Text text = buf.readText();
        _snowman = buf.readText();
        ItemStack _snowman2 = buf.readItemStack();
        AdvancementFrame _snowman3 = buf.readEnumConstant(AdvancementFrame.class);
        int _snowman4 = buf.readInt();
        Identifier _snowman5 = (_snowman4 & 1) != 0 ? buf.readIdentifier() : null;
        boolean _snowman6 = (_snowman4 & 2) != 0;
        boolean _snowman7 = (_snowman4 & 4) != 0;
        AdvancementDisplay _snowman8 = new AdvancementDisplay(_snowman2, text, _snowman, _snowman5, _snowman3, _snowman6, false, _snowman7);
        _snowman8.setPosition(buf.readFloat(), buf.readFloat());
        return _snowman8;
    }

    public JsonElement toJson() {
        JsonObject jsonObject = new JsonObject();
        jsonObject.add("icon", (JsonElement)this.iconToJson());
        jsonObject.add("title", Text.Serializer.toJsonTree(this.title));
        jsonObject.add("description", Text.Serializer.toJsonTree(this.description));
        jsonObject.addProperty("frame", this.frame.getId());
        jsonObject.addProperty("show_toast", Boolean.valueOf(this.showToast));
        jsonObject.addProperty("announce_to_chat", Boolean.valueOf(this.announceToChat));
        jsonObject.addProperty("hidden", Boolean.valueOf(this.hidden));
        if (this.background != null) {
            jsonObject.addProperty("background", this.background.toString());
        }
        return jsonObject;
    }

    private JsonObject iconToJson() {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("item", Registry.ITEM.getId(this.icon.getItem()).toString());
        if (this.icon.hasTag()) {
            jsonObject.addProperty("nbt", this.icon.getTag().toString());
        }
        return jsonObject;
    }
}

