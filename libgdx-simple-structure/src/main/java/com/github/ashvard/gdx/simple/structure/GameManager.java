package com.github.ashvard.gdx.simple.structure;

import com.github.ashvard.gdx.simple.structure.audio.AudioAssetManager;
import com.github.ashvard.gdx.simple.structure.screen.loading.LoadingScreen;
import com.github.ashvard.gdx.simple.structure.screen.statemachine.GameScreenState;
import com.github.ashvard.gdx.simple.structure.screen.statemachine.GameScreenStateManager;
import com.badlogic.gdx.assets.AssetManager;

public class GameManager<U extends UserFactory> {

    public GameSettings gameSettings;
    public AudioSettings audioSettings;
    public AudioAssetManager audioAssetManager;
    public SimpleUtils simpleUtils;
    public U userFactory;
    public AssetManager assetManager;
    public GameScreenStateManager screenStateManager;


    public void init(
            GameSettings gameSettings,
            AudioSettings audioSettings,
            SimpleUtils simpleUtils,
            AssetManager assetManager,
            AudioAssetManager audioAssetManager,
            LoadingScreen loadingScreen,
            GameScreenState startState,
            U userFactory
    ) {
        this.gameSettings = gameSettings;
        this.audioSettings = audioSettings;
        this.simpleUtils = simpleUtils;
        this.assetManager = assetManager;
        this.audioAssetManager = audioAssetManager;
        screenStateManager = new GameScreenStateManager(this, loadingScreen, startState);

        this.userFactory = userFactory;
        this.userFactory.init(this);
    }

}
