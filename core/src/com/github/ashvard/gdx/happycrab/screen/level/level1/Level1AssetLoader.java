package com.github.ashvard.gdx.happycrab.screen.level.level1;

import com.badlogic.gdx.assets.AssetManager;
import com.github.ashvard.gdx.happycrab.Resources;
import com.github.ashvard.gdx.happycrab.SimpleAnimationHyperLap2dLoadedCallback;
import com.github.ashvard.gdx.simple.structure.screen.loading.AssetsLoader;
import games.rednblack.editor.renderer.resources.AsyncResourceManager;
import games.rednblack.editor.renderer.resources.ResourceManagerLoader;
import games.rednblack.editor.renderer.utils.ShaderCompiler;
import games.rednblack.editor.renderer.utils.TextureArrayPolygonSpriteBatch;

public class Level1AssetLoader implements AssetsLoader {

    @Override
    public void loadAssets(AssetManager assetManager) {
        // обязательно до загрузки ассетов, на этапе которых компилируются шейдеры,
        // определяем число доступных для GPU текстур.
        // По умолчанию MAX_TEXTURE_UNIT=1 и если более 1 текстуры,то корректно шейдеры применяться не будут
        ShaderCompiler.MAX_TEXTURE_UNIT = TextureArrayPolygonSpriteBatch.getMaxTextureUnits();

        ResourceManagerLoader.AsyncResourceManagerParam managerParam = new ResourceManagerLoader.AsyncResourceManagerParam();
        managerParam.loadedCallback = new SimpleAnimationHyperLap2dLoadedCallback(
                new SimpleAnimationHyperLap2dLoadedCallback.SimpleAnimationPath[]{
                        new SimpleAnimationHyperLap2dLoadedCallback.SimpleAnimationPath(
                                Resources.Animations.CRAB_ANIM_FSM,
                                Resources.Animations.CRAB
                        ),
                        new SimpleAnimationHyperLap2dLoadedCallback.SimpleAnimationPath(
                                Resources.Animations.GREEN_FISH_FSM,
                                Resources.Animations.GREEN_FISH
                        )
                }
        );

        assetManager.load(Resources.HyperLap2D.PROJECT, AsyncResourceManager.class, managerParam);
    }

    @Override
    public void unloadAssets(AssetManager assetManager) {
        assetManager.unload(Resources.Animations.CRAB_ANIM_FSM);
        assetManager.unload(Resources.Animations.GREEN_FISH_FSM);
        assetManager.unload(Resources.HyperLap2D.PROJECT);
    }

}
