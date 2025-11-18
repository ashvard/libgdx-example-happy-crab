package com.github.ashvard.gdx.happycrab.screen.level.level1.script;

import com.artemis.ComponentMapper;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.utils.ObjectMap;
import com.github.ashvard.gdx.happycrab.model.scripts.AbstractGameObjectScript;
import com.github.ashvard.gdx.happycrab.screen.level.level1.systems.CameraSystem;
import games.rednblack.editor.renderer.components.MainItemComponent;
import games.rednblack.editor.renderer.components.TransformComponent;
import games.rednblack.editor.renderer.components.ViewPortComponent;

public class CameraScript extends AbstractGameObjectScript {

    private static final String ZOOM = "zoom";

    protected ComponentMapper<TransformComponent> transformMapper;
    protected ComponentMapper<ViewPortComponent> viewportMapper;
    protected ComponentMapper<MainItemComponent> mainItemMapper;

    @Override
    public void beginContact(int entityId, Fixture fixture, Fixture fixture1, Contact contact) {
        MainItemComponent mainItemComponent = mainItemMapper.get(getEntity());
        ObjectMap<String, String> customVariables = mainItemComponent.customVariables;
        float newZoom = Float.parseFloat(customVariables.get(ZOOM));

        CameraSystem system = getEngine().getSystem(CameraSystem.class);
        system.changeZoomTo(newZoom);
    }

    @Override
    public void endContact(int entityId, Fixture fixture, Fixture fixture1, Contact contact) {

    }

    @Override
    public void preSolve(int entityId, Fixture fixture, Fixture fixture1, Contact contact) {

    }

    @Override
    public void postSolve(int entityId, Fixture fixture, Fixture fixture1, Contact contact) {

    }

    @Override
    public void act(float deltaTime) {

    }

    @Override
    public void dispose() {

    }

}
