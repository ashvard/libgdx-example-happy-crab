package com.github.ashvard.gdx.happycrab.screen.level.level1;

import com.badlogic.gdx.assets.AssetManager;
import com.github.ashvard.gdx.happycrab.Resources;
import com.github.ashvard.gdx.simple.animation.SimpleAnimation;
import com.github.ashvard.gdx.simple.structure.screen.loading.AssetsLoader;
import games.rednblack.editor.renderer.resources.AsyncResourceManager;

public class Level1AssetLoader implements AssetsLoader {

    @Override
    public void loadAssets(AssetManager assetManager) {
        assetManager.load(Resources.HyperLap2D.PROJECT, AsyncResourceManager.class);

        assetManager.load(Resources.Animations.CRAB_ANIM_FSM, SimpleAnimation.class);
        assetManager.load(Resources.Animations.GREEN_FISH_FSM, SimpleAnimation.class);
    }

    @Override
    public void unloadAssets(AssetManager assetManager) {
        assetManager.unload(Resources.HyperLap2D.PROJECT);

        assetManager.unload(Resources.Animations.CRAB_ANIM_FSM);
        assetManager.unload(Resources.Animations.GREEN_FISH_FSM);
    }

}
