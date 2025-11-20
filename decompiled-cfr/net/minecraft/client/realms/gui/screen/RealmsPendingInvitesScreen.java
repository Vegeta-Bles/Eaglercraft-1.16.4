/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 *  org.apache.logging.log4j.LogManager
 *  org.apache.logging.log4j.Logger
 */
package net.minecraft.client.realms.gui.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import javax.annotation.Nullable;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.ScreenTexts;
import net.minecraft.client.gui.widget.AlwaysSelectedEntryListWidget;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.realms.Realms;
import net.minecraft.client.realms.RealmsClient;
import net.minecraft.client.realms.RealmsLabel;
import net.minecraft.client.realms.RealmsObjectSelectionList;
import net.minecraft.client.realms.dto.PendingInvite;
import net.minecraft.client.realms.exception.RealmsServiceException;
import net.minecraft.client.realms.gui.screen.RealmsAcceptRejectButton;
import net.minecraft.client.realms.gui.screen.RealmsMainScreen;
import net.minecraft.client.realms.gui.screen.RealmsScreen;
import net.minecraft.client.realms.util.RealmsTextureManager;
import net.minecraft.client.realms.util.RealmsUtil;
import net.minecraft.client.resource.language.I18n;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Identifier;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class RealmsPendingInvitesScreen
extends RealmsScreen {
    private static final Logger LOGGER = LogManager.getLogger();
    private static final Identifier ACCEPT_ICON = new Identifier("realms", "textures/gui/realms/accept_icon.png");
    private static final Identifier REJECT_ICON = new Identifier("realms", "textures/gui/realms/reject_icon.png");
    private static final Text field_26493 = new TranslatableText("mco.invites.nopending");
    private static final Text field_26494 = new TranslatableText("mco.invites.button.accept");
    private static final Text field_26495 = new TranslatableText("mco.invites.button.reject");
    private final Screen parent;
    @Nullable
    private Text toolTip;
    private boolean loaded;
    private PendingInvitationSelectionList pendingInvitationSelectionList;
    private RealmsLabel titleLabel;
    private int selectedInvite = -1;
    private ButtonWidget acceptButton;
    private ButtonWidget rejectButton;

    public RealmsPendingInvitesScreen(Screen parent) {
        this.parent = parent;
    }

    @Override
    public void init() {
        this.client.keyboard.setRepeatEvents(true);
        this.pendingInvitationSelectionList = new PendingInvitationSelectionList();
        new Thread(this, "Realms-pending-invitations-fetcher"){
            final /* synthetic */ RealmsPendingInvitesScreen field_19944;
            {
                this.field_19944 = realmsPendingInvitesScreen;
                super(string);
            }

            /*
             * WARNING - Removed try catching itself - possible behaviour change.
             */
            public void run() {
                RealmsClient realmsClient = RealmsClient.createRealmsClient();
                try {
                    List<PendingInvite> list = realmsClient.pendingInvites().pendingInvites;
                    List<T> _snowman2 = list.stream().map(pendingInvite -> this.field_19944.new PendingInvitationSelectionListEntry((PendingInvite)pendingInvite)).collect(Collectors.toList());
                    RealmsPendingInvitesScreen.method_25174(this.field_19944).execute(() -> RealmsPendingInvitesScreen.method_21302(this.field_19944).replaceEntries(_snowman2));
                }
                catch (RealmsServiceException realmsServiceException) {
                    RealmsPendingInvitesScreen.method_21299().error("Couldn't list invites");
                }
                finally {
                    RealmsPendingInvitesScreen.method_21305(this.field_19944, true);
                }
            }
        }.start();
        this.addChild(this.pendingInvitationSelectionList);
        this.acceptButton = this.addButton(new ButtonWidget(this.width / 2 - 174, this.height - 32, 100, 20, new TranslatableText("mco.invites.button.accept"), buttonWidget -> {
            this.accept(this.selectedInvite);
            this.selectedInvite = -1;
            this.updateButtonStates();
        }));
        this.addButton(new ButtonWidget(this.width / 2 - 50, this.height - 32, 100, 20, ScreenTexts.DONE, buttonWidget -> this.client.openScreen(new RealmsMainScreen(this.parent))));
        this.rejectButton = this.addButton(new ButtonWidget(this.width / 2 + 74, this.height - 32, 100, 20, new TranslatableText("mco.invites.button.reject"), buttonWidget -> {
            this.reject(this.selectedInvite);
            this.selectedInvite = -1;
            this.updateButtonStates();
        }));
        this.titleLabel = new RealmsLabel(new TranslatableText("mco.invites.title"), this.width / 2, 12, 0xFFFFFF);
        this.addChild(this.titleLabel);
        this.narrateLabels();
        this.updateButtonStates();
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        if (keyCode == 256) {
            this.client.openScreen(new RealmsMainScreen(this.parent));
            return true;
        }
        return super.keyPressed(keyCode, scanCode, modifiers);
    }

    private void updateList(int slot) {
        this.pendingInvitationSelectionList.removeAtIndex(slot);
    }

    private void reject(int slot) {
        if (slot < this.pendingInvitationSelectionList.getItemCount()) {
            new Thread(this, "Realms-reject-invitation", slot){
                final /* synthetic */ int field_19948;
                final /* synthetic */ RealmsPendingInvitesScreen field_19949;
                {
                    this.field_19949 = realmsPendingInvitesScreen;
                    this.field_19948 = n;
                    super(string);
                }

                public void run() {
                    try {
                        RealmsClient realmsClient = RealmsClient.createRealmsClient();
                        realmsClient.rejectInvitation(PendingInvitationSelectionListEntry.method_25187((PendingInvitationSelectionListEntry)((PendingInvitationSelectionListEntry)RealmsPendingInvitesScreen.method_21302((RealmsPendingInvitesScreen)this.field_19949).children().get((int)this.field_19948))).invitationId);
                        RealmsPendingInvitesScreen.method_25178(this.field_19949).execute(() -> RealmsPendingInvitesScreen.method_21316(this.field_19949, this.field_19948));
                    }
                    catch (RealmsServiceException realmsServiceException) {
                        RealmsPendingInvitesScreen.method_21299().error("Couldn't reject invite");
                    }
                }
            }.start();
        }
    }

    private void accept(int slot) {
        if (slot < this.pendingInvitationSelectionList.getItemCount()) {
            new Thread(this, "Realms-accept-invitation", slot){
                final /* synthetic */ int field_19950;
                final /* synthetic */ RealmsPendingInvitesScreen field_19951;
                {
                    this.field_19951 = realmsPendingInvitesScreen;
                    this.field_19950 = n;
                    super(string);
                }

                public void run() {
                    try {
                        RealmsClient realmsClient = RealmsClient.createRealmsClient();
                        realmsClient.acceptInvitation(PendingInvitationSelectionListEntry.method_25187((PendingInvitationSelectionListEntry)((PendingInvitationSelectionListEntry)RealmsPendingInvitesScreen.method_21302((RealmsPendingInvitesScreen)this.field_19951).children().get((int)this.field_19950))).invitationId);
                        RealmsPendingInvitesScreen.method_25181(this.field_19951).execute(() -> RealmsPendingInvitesScreen.method_21316(this.field_19951, this.field_19950));
                    }
                    catch (RealmsServiceException realmsServiceException) {
                        RealmsPendingInvitesScreen.method_21299().error("Couldn't accept invite");
                    }
                }
            }.start();
        }
    }

    @Override
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        this.toolTip = null;
        this.renderBackground(matrices);
        this.pendingInvitationSelectionList.render(matrices, mouseX, mouseY, delta);
        this.titleLabel.render(this, matrices);
        if (this.toolTip != null) {
            this.renderMousehoverTooltip(matrices, this.toolTip, mouseX, mouseY);
        }
        if (this.pendingInvitationSelectionList.getItemCount() == 0 && this.loaded) {
            RealmsPendingInvitesScreen.drawCenteredText(matrices, this.textRenderer, field_26493, this.width / 2, this.height / 2 - 20, 0xFFFFFF);
        }
        super.render(matrices, mouseX, mouseY, delta);
    }

    protected void renderMousehoverTooltip(MatrixStack matrixStack, @Nullable Text text, int n, int n2) {
        if (text == null) {
            return;
        }
        _snowman = n + 12;
        _snowman = n2 - 12;
        _snowman = this.textRenderer.getWidth(text);
        this.fillGradient(matrixStack, _snowman - 3, _snowman - 3, _snowman + _snowman + 3, _snowman + 8 + 3, -1073741824, -1073741824);
        this.textRenderer.drawWithShadow(matrixStack, text, (float)_snowman, (float)_snowman, 0xFFFFFF);
    }

    private void updateButtonStates() {
        this.acceptButton.visible = this.shouldAcceptAndRejectButtonBeVisible(this.selectedInvite);
        this.rejectButton.visible = this.shouldAcceptAndRejectButtonBeVisible(this.selectedInvite);
    }

    private boolean shouldAcceptAndRejectButtonBeVisible(int invite) {
        return invite != -1;
    }

    static /* synthetic */ MinecraftClient method_25174(RealmsPendingInvitesScreen realmsPendingInvitesScreen) {
        return realmsPendingInvitesScreen.client;
    }

    static /* synthetic */ Logger method_21299() {
        return LOGGER;
    }

    static /* synthetic */ boolean method_21305(RealmsPendingInvitesScreen realmsPendingInvitesScreen, boolean bl) {
        realmsPendingInvitesScreen.loaded = bl;
        return realmsPendingInvitesScreen.loaded;
    }

    static /* synthetic */ MinecraftClient method_25178(RealmsPendingInvitesScreen realmsPendingInvitesScreen) {
        return realmsPendingInvitesScreen.client;
    }

    static /* synthetic */ void method_21316(RealmsPendingInvitesScreen realmsPendingInvitesScreen, int n) {
        realmsPendingInvitesScreen.updateList(n);
    }

    static /* synthetic */ MinecraftClient method_25181(RealmsPendingInvitesScreen realmsPendingInvitesScreen) {
        return realmsPendingInvitesScreen.client;
    }

    class PendingInvitationSelectionListEntry
    extends AlwaysSelectedEntryListWidget.Entry<PendingInvitationSelectionListEntry> {
        private final PendingInvite mPendingInvite;
        private final List<RealmsAcceptRejectButton> buttons;

        PendingInvitationSelectionListEntry(PendingInvite pendingInvite) {
            this.mPendingInvite = pendingInvite;
            this.buttons = Arrays.asList(new AcceptButton(), new RejectButton());
        }

        @Override
        public void render(MatrixStack matrices, int index, int y, int x, int entryWidth, int entryHeight, int mouseX, int mouseY, boolean hovered, float tickDelta) {
            this.renderPendingInvitationItem(matrices, this.mPendingInvite, x, y, mouseX, mouseY);
        }

        @Override
        public boolean mouseClicked(double mouseX, double mouseY, int button) {
            RealmsAcceptRejectButton.handleClick(RealmsPendingInvitesScreen.this.pendingInvitationSelectionList, this, this.buttons, button, mouseX, mouseY);
            return true;
        }

        private void renderPendingInvitationItem(MatrixStack matrixStack, PendingInvite pendingInvite, int n, int n2, int n3, int n4) {
            RealmsPendingInvitesScreen.this.textRenderer.draw(matrixStack, pendingInvite.worldName, (float)(n + 38), (float)(n2 + 1), 0xFFFFFF);
            RealmsPendingInvitesScreen.this.textRenderer.draw(matrixStack, pendingInvite.worldOwnerName, (float)(n + 38), (float)(n2 + 12), 0x6C6C6C);
            RealmsPendingInvitesScreen.this.textRenderer.draw(matrixStack, RealmsUtil.method_25282(pendingInvite.date), (float)(n + 38), (float)(n2 + 24), 0x6C6C6C);
            RealmsAcceptRejectButton.render(matrixStack, this.buttons, RealmsPendingInvitesScreen.this.pendingInvitationSelectionList, n, n2, n3, n4);
            RealmsTextureManager.withBoundFace(pendingInvite.worldOwnerUuid, () -> {
                RenderSystem.color4f(1.0f, 1.0f, 1.0f, 1.0f);
                DrawableHelper.drawTexture(matrixStack, n, n2, 32, 32, 8.0f, 8.0f, 8, 8, 64, 64);
                DrawableHelper.drawTexture(matrixStack, n, n2, 32, 32, 40.0f, 8.0f, 8, 8, 64, 64);
            });
        }

        class RejectButton
        extends RealmsAcceptRejectButton {
            RejectButton() {
                super(15, 15, 235, 5);
            }

            @Override
            protected void render(MatrixStack matrixStack, int y, int n, boolean bl) {
                RealmsPendingInvitesScreen.this.client.getTextureManager().bindTexture(REJECT_ICON);
                RenderSystem.color4f(1.0f, 1.0f, 1.0f, 1.0f);
                float f = bl ? 19.0f : 0.0f;
                DrawableHelper.drawTexture(matrixStack, y, n, f, 0.0f, 18, 18, 37, 18);
                if (bl) {
                    RealmsPendingInvitesScreen.this.toolTip = field_26495;
                }
            }

            @Override
            public void handleClick(int index) {
                RealmsPendingInvitesScreen.this.reject(index);
            }
        }

        class AcceptButton
        extends RealmsAcceptRejectButton {
            AcceptButton() {
                super(15, 15, 215, 5);
            }

            @Override
            protected void render(MatrixStack matrixStack, int y, int n, boolean bl) {
                RealmsPendingInvitesScreen.this.client.getTextureManager().bindTexture(ACCEPT_ICON);
                RenderSystem.color4f(1.0f, 1.0f, 1.0f, 1.0f);
                float f = bl ? 19.0f : 0.0f;
                DrawableHelper.drawTexture(matrixStack, y, n, f, 0.0f, 18, 18, 37, 18);
                if (bl) {
                    RealmsPendingInvitesScreen.this.toolTip = field_26494;
                }
            }

            @Override
            public void handleClick(int index) {
                RealmsPendingInvitesScreen.this.accept(index);
            }
        }
    }

    class PendingInvitationSelectionList
    extends RealmsObjectSelectionList<PendingInvitationSelectionListEntry> {
        public PendingInvitationSelectionList() {
            super(RealmsPendingInvitesScreen.this.width, RealmsPendingInvitesScreen.this.height, 32, RealmsPendingInvitesScreen.this.height - 40, 36);
        }

        public void removeAtIndex(int index) {
            this.remove(index);
        }

        @Override
        public int getMaxPosition() {
            return this.getItemCount() * 36;
        }

        @Override
        public int getRowWidth() {
            return 260;
        }

        @Override
        public boolean isFocused() {
            return RealmsPendingInvitesScreen.this.getFocused() == this;
        }

        @Override
        public void renderBackground(MatrixStack matrices) {
            RealmsPendingInvitesScreen.this.renderBackground(matrices);
        }

        @Override
        public void setSelected(int index) {
            this.setSelectedItem(index);
            if (index != -1) {
                List list = RealmsPendingInvitesScreen.this.pendingInvitationSelectionList.children();
                PendingInvite _snowman2 = ((PendingInvitationSelectionListEntry)list.get(index)).mPendingInvite;
                String _snowman3 = I18n.translate("narrator.select.list.position", index + 1, list.size());
                String _snowman4 = Realms.joinNarrations(Arrays.asList(_snowman2.worldName, _snowman2.worldOwnerName, RealmsUtil.method_25282(_snowman2.date), _snowman3));
                Realms.narrateNow(I18n.translate("narrator.select", _snowman4));
            }
            this.selectInviteListItem(index);
        }

        public void selectInviteListItem(int item) {
            RealmsPendingInvitesScreen.this.selectedInvite = item;
            RealmsPendingInvitesScreen.this.updateButtonStates();
        }

        @Override
        public void setSelected(@Nullable PendingInvitationSelectionListEntry pendingInvitationSelectionListEntry) {
            super.setSelected(pendingInvitationSelectionListEntry);
            RealmsPendingInvitesScreen.this.selectedInvite = this.children().indexOf(pendingInvitationSelectionListEntry);
            RealmsPendingInvitesScreen.this.updateButtonStates();
        }
    }
}

