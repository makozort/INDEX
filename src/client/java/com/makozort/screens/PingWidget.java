package com.makozort.screens;





import com.google.common.collect.Lists;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import com.makozort.PDA;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.logging.LogUtils;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadPoolExecutor;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.LoadingDisplay;
import net.minecraft.client.gui.screen.world.WorldIcon;
import net.minecraft.client.gui.widget.AlwaysSelectedEntryListWidget;
import net.minecraft.client.network.ServerInfo;
import net.minecraft.text.OrderedText;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.Util;
import net.minecraft.util.logging.UncaughtExceptionLogger;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;


public class PingWidget extends AlwaysSelectedEntryListWidget<PingWidget.Entry> {
    static final Identifier JOIN_HIGHLIGHTED_TEXTURE = Identifier.ofVanilla("server_list/join_highlighted");
    static final Identifier JOIN_TEXTURE = Identifier.ofVanilla("server_list/join");
    static final Identifier MOVE_UP_HIGHLIGHTED_TEXTURE = Identifier.ofVanilla("server_list/move_up_highlighted");
    static final Identifier MOVE_UP_TEXTURE = Identifier.ofVanilla("server_list/move_up");
    static final Identifier MOVE_DOWN_HIGHLIGHTED_TEXTURE = Identifier.ofVanilla("server_list/move_down_highlighted");
    static final Identifier MOVE_DOWN_TEXTURE = Identifier.ofVanilla("server_list/move_down");
    static final Logger LOGGER = LogUtils.getLogger();
    private final CustomScreen screen;
    private final List<PingEntry> notes = Lists.newArrayList();

    public PingWidget(CustomScreen screen, MinecraftClient client, int width, int height, int top, int bottom) {
        super(client, width, height, top, bottom);
        this.screen = screen;
        PingWidget.LOGGER.info("PingWidget initialized with dimensions: {}x{}", width, height);
    }

    private void updateEntries() {
        this.clearEntries();
        this.notes.forEach((note) -> {
            this.addEntry(note);
        });
        PingWidget.LOGGER.info("Entries updated, total: {}", this.notes.size());

    }

    public void setSelected(@Nullable PingWidget.Entry entry) {
        super.setSelected(entry);
    }

    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        Entry entry = (Entry)this.getSelectedOrNull();
        return entry != null && entry.keyPressed(keyCode, scanCode, modifiers) || super.keyPressed(keyCode, scanCode, modifiers);
    }

    public void setLogs(List<LogEntry> logs) {
        this.notes.clear();
        for(int i = 0; i < logs.size(); ++i) {
            this.notes.add(new PingEntry(this.screen, logs.get(i)));
        }
        PingWidget.LOGGER.info("Number of logs: " + logs.size());
        this.updateEntries();
    }


    public int getRowWidth() {
        return 305;
    }


    public abstract static class Entry extends AlwaysSelectedEntryListWidget.Entry<Entry> implements AutoCloseable {
        public Entry() {
        }

        public void close() {
        }
    }


    public class PingEntry extends Entry {
        private static final int field_32387 = 32;
        private static final int field_32388 = 32;
        private static final int field_47852 = 5;
        private static final int field_47853 = 10;
        private static final int field_47854 = 8;
        private long time;
        private final CustomScreen screen;
        private final LogEntry log;
        private final MinecraftClient client;

        protected PingEntry(final CustomScreen screen, final LogEntry log) {
            this.screen = screen;
            this.log = log;
            this.client = MinecraftClient.getInstance();
        }

        public void render(DrawContext context, int index, int y, int x, int entryWidth, int entryHeight, int mouseX, int mouseY, boolean hovered, float tickDelta) {
            PingWidget.LOGGER.info("Rendering entry: " + this.log.name);  // Add this line
            context.drawText(this.client.textRenderer, this.log.name, x + 32 + 3, y + 1, 16777215, false);
            List<OrderedText> list = this.client.textRenderer.wrapLines(this.log.label, entryWidth - 32 - 2);

            int i;
            for(i = 0; i < Math.min(list.size(), 2); ++i) {
                TextRenderer var10001 = this.client.textRenderer;
                OrderedText var10002 = (OrderedText)list.get(i);
                int var10003 = x + 32 + 3;
                int var10004 = y + 12;
                Objects.requireNonNull(this.client.textRenderer);
                context.drawText(var10001, var10002, var10003, var10004 + 9 * i, -8355712, false);
            }


            if ((Boolean)this.client.options.getTouchscreen().getValue() || hovered) {
                context.fill(x, y, x + 32, y + 32, -1601138544);
                int l = mouseX - x;
                int m = mouseY - y;
                    if (l < 32 && l > 16) {
                        context.drawGuiTexture(JOIN_HIGHLIGHTED_TEXTURE, x, y, 32, 32);
                    } else {
                        context.drawGuiTexture(JOIN_TEXTURE, x, y, 32, 32);
                    }


                if (index > 0) {
                    if (l < 16 && m < 16) {
                        context.drawGuiTexture(MOVE_UP_HIGHLIGHTED_TEXTURE, x, y, 32, 32);
                    } else {
                        context.drawGuiTexture(MOVE_UP_TEXTURE, x, y, 32, 32);
                    }
                }

                if (index < this.screen.getLogList().size() - 1) {
                    if (l < 16 && m > 16) {
                        context.drawGuiTexture(MOVE_DOWN_HIGHLIGHTED_TEXTURE, x, y, 32, 32);
                    } else {
                        context.drawGuiTexture(MOVE_DOWN_TEXTURE, x, y, 32, 32);
                    }
                }
            }

        }

        protected void draw(DrawContext context, int x, int y, Identifier textureId) {
            RenderSystem.enableBlend();
            context.drawTexture(textureId, x, y, 0.0F, 0.0F, 32, 32, 32, 32);
            RenderSystem.disableBlend();
        }

        @Override
        public Text getNarration() {
            return Text.of("fix later");
        }

        public boolean mouseClicked(double mouseX, double mouseY, int button) {
            double d = mouseX - (double) PingWidget.this.getRowLeft();
            double e = mouseY - (double) PingWidget.this.getRowTop(PingWidget.this.children().indexOf(this));


            this.screen.select(this);
            if (Util.getMeasuringTimeMs() - this.time < 250L) {
                PDA.LOGGER.info("hi from the client");
            }

            this.time = Util.getMeasuringTimeMs();
            return super.mouseClicked(mouseX, mouseY, button);
        }

        public LogEntry getLog() {
            return this.log;
        }
    }
}

