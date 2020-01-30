package mindustry.entities;

import arc.math.geom.*;
import arc.util.ArcAnnotate.*;
import mindustry.world.*;

import static mindustry.Vars.*;
import static mindustry.gen.Sys.*;

/** Class for storing build requests. Can be either a place or remove request. */
public class BuildRequest{
    /** Position and rotation of this request. */
    public int x, y, rotation;
    /** Block being placed. If null, this is a breaking request.*/
    public @Nullable Block block;
    /** Whether this is a break request.*/
    public boolean breaking;
    /** Whether this request comes with a config int. If yes, any blocks placed with this request will not call playerPlaced.*/
    public boolean hasConfig;
    /** Config int. Not used unless hasConfig is true.*/
    public int config;
    /** Original position, only used in schematics.*/
    public int originalX, originalY, originalWidth, originalHeight;

    /** Last progress.*/
    public float progress;
    /** Whether construction has started for this request, and other special variables.*/
    public boolean initialized, worldContext = true, stuck;

    /** Visual scale. Used only for rendering.*/
    public float animScale = 0f;

    /** This creates a build request. */
    public BuildRequest(int x, int y, int rotation, Block block){
        this.x = x;
        this.y = y;
        this.rotation = rotation;
        this.block = block;
        this.breaking = false;
    }

    /** This creates a remove request. */
    public BuildRequest(int x, int y){
        this.x = x;
        this.y = y;
        this.rotation = -1;
        this.block = world.tile(x, y).block();
        this.breaking = true;
    }

    public BuildRequest(){

    }

    public BuildRequest copy(){
        BuildRequest copy = new BuildRequest();
        copy.x = x;
        copy.y = y;
        copy.rotation = rotation;
        copy.block = block;
        copy.breaking = breaking;
        copy.hasConfig = hasConfig;
        copy.config = config;
        copy.originalX = originalX;
        copy.originalY = originalY;
        copy.progress = progress;
        copy.initialized = initialized;
        copy.animScale = animScale;
        return copy;
    }

    public BuildRequest original(int x, int y, int originalWidth, int originalHeight){
        originalX = x;
        originalY = y;
        this.originalWidth = originalWidth;
        this.originalHeight = originalHeight;
        return this;
    }

    public Rect bounds(Rect rect){
        if(breaking){
            return rect.set(-100f, -100f, 0f, 0f);
        }else{
            return block.bounds(x, y, rect);
        }
    }

    public BuildRequest set(int x, int y, int rotation, Block block){
        this.x = x;
        this.y = y;
        this.rotation = rotation;
        this.block = block;
        this.breaking = false;
        return this;
    }

    public float drawx(){
        return x*tilesize + block.offset();
    }

    public float drawy(){
        return y*tilesize + block.offset();
    }

    public BuildRequest configure(int config){
        this.config = config;
        this.hasConfig = true;
        return this;
    }

    public @Nullable Tile tile(){
        return world.tile(x, y);
    }

    @Override
    public String toString(){
        return "BuildRequest{" +
        "x=" + x +
        ", y=" + y +
        ", rotation=" + rotation +
        ", recipe=" + block +
        ", breaking=" + breaking +
        ", progress=" + progress +
        ", initialized=" + initialized +
        '}';
    }
}
