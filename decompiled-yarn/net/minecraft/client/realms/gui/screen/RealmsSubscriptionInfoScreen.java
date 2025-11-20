package net.minecraft.client.realms.gui.screen;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.TimeZone;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.ScreenTexts;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.realms.Realms;
import net.minecraft.client.realms.RealmsClient;
import net.minecraft.client.realms.dto.RealmsServer;
import net.minecraft.client.realms.dto.Subscription;
import net.minecraft.client.realms.exception.RealmsServiceException;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.LiteralText;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Util;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class RealmsSubscriptionInfoScreen extends RealmsScreen {
   private static final Logger LOGGER = LogManager.getLogger();
   private static final Text subscriptionTitle = new TranslatableText("mco.configure.world.subscription.title");
   private static final Text subscriptionStartLabelText = new TranslatableText("mco.configure.world.subscription.start");
   private static final Text timeLeftLabelText = new TranslatableText("mco.configure.world.subscription.timeleft");
   private static final Text daysLeftLabelText = new TranslatableText("mco.configure.world.subscription.recurring.daysleft");
   private static final Text field_26517 = new TranslatableText("mco.configure.world.subscription.expired");
   private static final Text field_26518 = new TranslatableText("mco.configure.world.subscription.less_than_a_day");
   private static final Text field_26519 = new TranslatableText("mco.configure.world.subscription.month");
   private static final Text field_26520 = new TranslatableText("mco.configure.world.subscription.months");
   private static final Text field_26521 = new TranslatableText("mco.configure.world.subscription.day");
   private static final Text field_26522 = new TranslatableText("mco.configure.world.subscription.days");
   private final Screen parent;
   private final RealmsServer serverData;
   private final Screen mainScreen;
   private Text daysLeft;
   private String startDate;
   private Subscription.SubscriptionType type;

   public RealmsSubscriptionInfoScreen(Screen parent, RealmsServer serverData, Screen mainScreen) {
      this.parent = parent;
      this.serverData = serverData;
      this.mainScreen = mainScreen;
   }

   @Override
   public void init() {
      this.getSubscription(this.serverData.id);
      Realms.narrateNow(
         subscriptionTitle.getString(), subscriptionStartLabelText.getString(), this.startDate, timeLeftLabelText.getString(), this.daysLeft.getString()
      );
      this.client.keyboard.setRepeatEvents(true);
      this.addButton(
         new ButtonWidget(
            this.width / 2 - 100,
            row(6),
            200,
            20,
            new TranslatableText("mco.configure.world.subscription.extend"),
            _snowman -> {
               String _snowmanx = "https://aka.ms/ExtendJavaRealms?subscriptionId="
                  + this.serverData.remoteSubscriptionId
                  + "&profileId="
                  + this.client.getSession().getUuid();
               this.client.keyboard.setClipboard(_snowmanx);
               Util.getOperatingSystem().open(_snowmanx);
            }
         )
      );
      this.addButton(new ButtonWidget(this.width / 2 - 100, row(12), 200, 20, ScreenTexts.BACK, _snowman -> this.client.openScreen(this.parent)));
      if (this.serverData.expired) {
         this.addButton(new ButtonWidget(this.width / 2 - 100, row(10), 200, 20, new TranslatableText("mco.configure.world.delete.button"), _snowman -> {
            Text _snowmanx = new TranslatableText("mco.configure.world.delete.question.line1");
            Text _snowmanxx = new TranslatableText("mco.configure.world.delete.question.line2");
            this.client.openScreen(new RealmsLongConfirmationScreen(this::method_25271, RealmsLongConfirmationScreen.Type.Warning, _snowmanx, _snowmanxx, true));
         }));
      }
   }

   private void method_25271(boolean _snowman) {
      if (_snowman) {
         (new Thread("Realms-delete-realm") {
               @Override
               public void run() {
                  try {
                     RealmsClient _snowman = RealmsClient.createRealmsClient();
                     _snowman.deleteWorld(RealmsSubscriptionInfoScreen.this.serverData.id);
                  } catch (RealmsServiceException var2) {
                     RealmsSubscriptionInfoScreen.LOGGER.error("Couldn't delete world");
                     RealmsSubscriptionInfoScreen.LOGGER.error(var2);
                  }

                  RealmsSubscriptionInfoScreen.this.client
                     .execute(() -> RealmsSubscriptionInfoScreen.this.client.openScreen(RealmsSubscriptionInfoScreen.this.mainScreen));
               }
            })
            .start();
      }

      this.client.openScreen(this);
   }

   private void getSubscription(long worldId) {
      RealmsClient _snowman = RealmsClient.createRealmsClient();

      try {
         Subscription _snowmanx = _snowman.subscriptionFor(worldId);
         this.daysLeft = this.daysLeftPresentation(_snowmanx.daysLeft);
         this.startDate = localPresentation(_snowmanx.startDate);
         this.type = _snowmanx.type;
      } catch (RealmsServiceException var5) {
         LOGGER.error("Couldn't get subscription");
         this.client.openScreen(new RealmsGenericErrorScreen(var5, this.parent));
      }
   }

   private static String localPresentation(long _snowman) {
      Calendar _snowmanx = new GregorianCalendar(TimeZone.getDefault());
      _snowmanx.setTimeInMillis(_snowman);
      return DateFormat.getDateTimeInstance().format(_snowmanx.getTime());
   }

   @Override
   public void removed() {
      this.client.keyboard.setRepeatEvents(false);
   }

   @Override
   public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
      if (keyCode == 256) {
         this.client.openScreen(this.parent);
         return true;
      } else {
         return super.keyPressed(keyCode, scanCode, modifiers);
      }
   }

   @Override
   public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
      this.renderBackground(matrices);
      int _snowman = this.width / 2 - 100;
      drawCenteredText(matrices, this.textRenderer, subscriptionTitle, this.width / 2, 17, 16777215);
      this.textRenderer.draw(matrices, subscriptionStartLabelText, (float)_snowman, (float)row(0), 10526880);
      this.textRenderer.draw(matrices, this.startDate, (float)_snowman, (float)row(1), 16777215);
      if (this.type == Subscription.SubscriptionType.NORMAL) {
         this.textRenderer.draw(matrices, timeLeftLabelText, (float)_snowman, (float)row(3), 10526880);
      } else if (this.type == Subscription.SubscriptionType.RECURRING) {
         this.textRenderer.draw(matrices, daysLeftLabelText, (float)_snowman, (float)row(3), 10526880);
      }

      this.textRenderer.draw(matrices, this.daysLeft, (float)_snowman, (float)row(4), 16777215);
      super.render(matrices, mouseX, mouseY, delta);
   }

   private Text daysLeftPresentation(int daysLeft) {
      if (daysLeft < 0 && this.serverData.expired) {
         return field_26517;
      } else if (daysLeft <= 1) {
         return field_26518;
      } else {
         int _snowman = daysLeft / 30;
         int _snowmanx = daysLeft % 30;
         MutableText _snowmanxx = new LiteralText("");
         if (_snowman > 0) {
            _snowmanxx.append(Integer.toString(_snowman)).append(" ");
            if (_snowman == 1) {
               _snowmanxx.append(field_26519);
            } else {
               _snowmanxx.append(field_26520);
            }
         }

         if (_snowmanx > 0) {
            if (_snowman > 0) {
               _snowmanxx.append(", ");
            }

            _snowmanxx.append(Integer.toString(_snowmanx)).append(" ");
            if (_snowmanx == 1) {
               _snowmanxx.append(field_26521);
            } else {
               _snowmanxx.append(field_26522);
            }
         }

         return _snowmanxx;
      }
   }
}
