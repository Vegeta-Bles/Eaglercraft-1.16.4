/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.entity;

public interface EntityInteraction {
    public static final EntityInteraction ZOMBIE_VILLAGER_CURED = EntityInteraction.create("zombie_villager_cured");
    public static final EntityInteraction GOLEM_KILLED = EntityInteraction.create("golem_killed");
    public static final EntityInteraction VILLAGER_HURT = EntityInteraction.create("villager_hurt");
    public static final EntityInteraction VILLAGER_KILLED = EntityInteraction.create("villager_killed");
    public static final EntityInteraction TRADE = EntityInteraction.create("trade");

    public static EntityInteraction create(String key) {
        return new EntityInteraction(key){
            final /* synthetic */ String field_17066;
            {
                this.field_17066 = string;
            }

            public String toString() {
                return this.field_17066;
            }
        };
    }
}

