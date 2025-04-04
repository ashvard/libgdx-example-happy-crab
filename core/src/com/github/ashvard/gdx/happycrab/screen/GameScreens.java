package com.github.ashvard.gdx.happycrab.screen;


import com.github.ashvard.gdx.happycrab.screen.level.level.Level0AssetLoader;
import com.github.ashvard.gdx.happycrab.screen.level.level.Level0Screen;
import com.github.ashvard.gdx.happycrab.screen.level.level1.Level1AssetLoader;
import com.github.ashvard.gdx.happycrab.screen.level.level1.Level1Screen;
import com.github.ashvard.gdx.simple.structure.screen.statemachine.GameScreenState;


public interface GameScreens {

    GameScreenState LEVEL_0 = new GameScreenState(new Level0Screen(), new Level0AssetLoader());
    GameScreenState LEVEL_1 = new GameScreenState(new Level1Screen(), new Level1AssetLoader());

}
