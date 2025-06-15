package com.github.ashvard.gdx.happycrab.screen.level.level0;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.github.ashvard.gdx.happycrab.Resources;
import com.github.ashvard.gdx.simple.animation.SimpleAnimation;
import com.github.ashvard.gdx.simple.structure.screen.loading.AssetsLoader;

public class Level0AssetLoader implements AssetsLoader {

    @Override
    public void loadAssets(AssetManager assetManager) {
        assetManager.load(Resources.Shaders.BLINK_SHADER, ShaderProgram.class);
        assetManager.load(Resources.Backgrounds.BACKGROUND_PNG, Texture.class);
        assetManager.load(Resources.Animations.CRAB_ANIM_FSM, SimpleAnimation.class);
    }

    @Override
    public void unloadAssets(AssetManager assetManager) {
        assetManager.unload(Resources.Shaders.BLINK_SHADER);
        assetManager.unload(Resources.Backgrounds.BACKGROUND_PNG);
        assetManager.unload(Resources.Animations.CRAB_ANIM_FSM);
    }

}
