package com.github.ashvard.gdx.happycrab;

import com.badlogic.gdx.assets.AssetLoaderParameters;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.utils.Array;
import com.github.ashvard.gdx.simple.animation.SimpleAnimation;
import com.github.ashvard.gdx.simple.animation.SimpleAnimationSyncLoader;

public class SimpleAnimationHyperLap2dLoadedCallback implements AssetLoaderParameters.LoadedCallback {

    private final SimpleAnimationPath[] paths;

    public SimpleAnimationHyperLap2dLoadedCallback(SimpleAnimationPath[] paths) {
        this.paths = paths;
    }

    @Override
    public void finishedLoading(AssetManager assetManager, String fileName, Class type) {
        TextureAtlas textureAtlas = assetManager.get("orig/pack.atlas", TextureAtlas.class);
        for (SimpleAnimationPath path : paths) {
            SimpleAnimationSyncLoader.SimpleAnimationParameter parameters = new SimpleAnimationSyncLoader.SimpleAnimationParameter();
            parameters.customTextureAtlas = new CustomTextureAtlas(textureAtlas.findRegions(path.atlasRegionsName));
            assetManager.load(path.pathToAfsmFile, SimpleAnimation.class, parameters);
        }
    }

    public static class SimpleAnimationPath {

        public final String pathToAfsmFile;
        public final String atlasRegionsName;

        public SimpleAnimationPath(String pathToAfsmFile, String atlasRegionsName) {
            this.pathToAfsmFile = pathToAfsmFile;
            this.atlasRegionsName = atlasRegionsName;
        }
    }

    private static class CustomTextureAtlas extends TextureAtlas {

        private final Array<AtlasRegion> regions;

        public CustomTextureAtlas(Array<AtlasRegion> regions) {
            this.regions = regions;
        }

        @Override
        public Array<AtlasRegion> getRegions () {
            return regions;
        }

        @Override
        public void dispose() {
        }

    }
}
