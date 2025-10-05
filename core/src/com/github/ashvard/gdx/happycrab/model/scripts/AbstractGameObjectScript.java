package com.github.ashvard.gdx.happycrab.model.scripts;

import com.artemis.World;
import games.rednblack.editor.renderer.physics.PhysicsContact;
import games.rednblack.editor.renderer.scripts.BasicScript;

public abstract class AbstractGameObjectScript extends BasicScript implements PhysicsContact {

    protected com.artemis.World engine;

    protected World getEngine() {
        return engine;
    }

}
