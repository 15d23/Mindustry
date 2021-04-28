package mindustry.ui;

import arc.scene.style.*;
import arc.scene.ui.*;
import arc.util.*;

public class MobileButton extends ImageButton{

    public MobileButton(Drawable icon, String text, Runnable listener){
        super(icon);
        clicked(listener);
        row();
        add(text).growX().wrap().center().get().setAlignment(Align.center, Align.center);
    }
}
