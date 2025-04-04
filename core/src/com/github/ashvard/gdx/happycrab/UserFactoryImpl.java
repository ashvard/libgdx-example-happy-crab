package com.github.ashvard.gdx.happycrab;

import com.github.ashvard.gdx.simple.structure.GameManager;
import com.github.ashvard.gdx.simple.structure.UserFactory;

public class UserFactoryImpl implements UserFactory {

    private GameManager gameManager;

    @Override
    public void init(GameManager gameManager) {
        this.gameManager = gameManager;
    }

}
