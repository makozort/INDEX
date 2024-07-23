package com.makozort.screens;

import com.makozort.PDA;
import io.wispforest.owo.ui.base.BaseOwoScreen;

import io.wispforest.owo.ui.component.Components;
import io.wispforest.owo.ui.container.Containers;
import io.wispforest.owo.ui.container.FlowLayout;
import io.wispforest.owo.ui.container.StackLayout;
import io.wispforest.owo.ui.core.*;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.NotNull;

public class HomeScreen extends BaseOwoScreen<FlowLayout> {

    public static final int BOOK_HEIGHT =  200;
    public static final int BOOK_WIDTH = 300;
    public String[] strings = {"test", "best", "fest", "rest"};
//

    public static final Identifier BOOK_TEXTURE = Identifier.of(PDA.MOD_ID, "textures/gui/book.png");

    @Override
    protected @NotNull OwoUIAdapter<FlowLayout> createAdapter() {
        return OwoUIAdapter.create(this, Containers::verticalFlow);
    }

    @Override
    protected void build(FlowLayout rootComponent) {


        StackLayout layout =  Containers.stack(Sizing.content(),Sizing.content())
            .child(Components.texture(BOOK_TEXTURE,0,0,BOOK_WIDTH,BOOK_HEIGHT,BOOK_WIDTH,BOOK_HEIGHT)
             );

        int y = 25;
        for(String i: strings) {
            layout.child(Components.button(Text.of(i),buttonComponent -> {System.out.println(i); })
                    .sizing(Sizing.fixed(60),Sizing.content())
                .positioning(Positioning.relative(110,y)));
            y +=15;
        }

        rootComponent
                .verticalAlignment(VerticalAlignment.CENTER)
                .horizontalAlignment(HorizontalAlignment.CENTER);

        rootComponent.child(
            layout
                .allowOverflow(true)
                .padding(Insets.right(30))
        );
    }
}
