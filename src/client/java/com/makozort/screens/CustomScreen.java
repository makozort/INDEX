package com.makozort.screens;

import com.mojang.logging.LogUtils;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.multiplayer.ConnectScreen;
import net.minecraft.client.gui.widget.*;
import net.minecraft.client.network.*;
import net.minecraft.screen.ScreenTexts;
import net.minecraft.text.Text;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;

import java.util.List;

public class CustomScreen extends Screen {
    public static final int field_41849 = 308;
    public static final int field_41850 = 100;
    public static final int field_41851 = 74;
    public static final int field_41852 = 64;
    private static final Logger LOGGER = LogUtils.getLogger();
    private final Screen parent;
    protected PingWidget pingWidget;
    private List<LogEntry> logList;
    private LanServerQueryManager.LanServerEntryList lanServers;
    @Nullable
    private LanServerQueryManager.LanServerDetector lanServerDetector;
    private boolean initialized;

    public CustomScreen(Screen parent,List<LogEntry> logList) {
        super(Text.translatable("multiplayer.title"));
        this.logList = logList;
        this.parent = parent;
    }

    protected void init() {
        if (this.initialized) {
            this.pingWidget.setDimensionsAndPosition(this.width, this.height - 64 - 32, 0, 32);
        } else {
            this.initialized = true;
            this.pingWidget = new PingWidget(this, this.client, this.width, this.height - 64 - 32, 32, 36);
            this.pingWidget.setLogs(this.logList);
            PingWidget.LOGGER.info("PingWidget created and servers set.");
            this.addSelectableChild(this.pingWidget);  // Ensure the widget is added as a child
            PingWidget.LOGGER.info("PingWidget added to screen as a selectable child.");
        }

        ButtonWidget buttonWidget4 = (ButtonWidget)this.addDrawableChild(ButtonWidget.builder(ScreenTexts.BACK, (button) -> {
            this.close();
        }).width(74).build());

        DirectionalLayoutWidget directionalLayoutWidget = DirectionalLayoutWidget.vertical();
        AxisGridWidget axisGridWidget = (AxisGridWidget)directionalLayoutWidget.add(new AxisGridWidget(308, 20, AxisGridWidget.DisplayAxis.HORIZONTAL));
        directionalLayoutWidget.add(EmptyWidget.ofHeight(4));
        AxisGridWidget axisGridWidget2 = (AxisGridWidget)directionalLayoutWidget.add(new AxisGridWidget(308, 20, AxisGridWidget.DisplayAxis.HORIZONTAL));
        axisGridWidget2.add(buttonWidget4);
        directionalLayoutWidget.refreshPositions();
        SimplePositioningWidget.setPos(directionalLayoutWidget, 0, this.height - 64, this.width, 64);
    }



    public void close() {
        this.client.setScreen(this.parent);
    }

    public void tick() {
        super.tick();
    }

    public void removed() {
        if (this.lanServerDetector != null) {
            this.lanServerDetector.interrupt();
            this.lanServerDetector = null;
        }
    }


    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        super.render(context, mouseX, mouseY, delta);
        context.drawCenteredTextWithShadow(this.textRenderer, this.title, this.width / 2, 20, 16777215);
    }



    private void connect(ServerInfo entry) {
        ConnectScreen.connect(this, this.client, ServerAddress.parse(entry.address), entry, false, (CookieStorage)null);
    }

    public void select(PingWidget.PingEntry entry) {
        this.pingWidget.setSelected(entry);
    }



    public List<LogEntry> getLogList() {
        return this.logList;
    }
}
