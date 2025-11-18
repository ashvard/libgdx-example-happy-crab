package com.github.ashvard.gdx.happycrab.screen.level.level1.systems;

import com.artemis.ComponentMapper;
import com.artemis.annotations.All;
import com.artemis.systems.IteratingSystem;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector3;
import games.rednblack.editor.renderer.components.TransformComponent;
import games.rednblack.editor.renderer.components.ViewPortComponent;

@All(ViewPortComponent.class)
public class CameraSystem extends IteratingSystem {

    protected ComponentMapper<TransformComponent> transformMapper;
    protected ComponentMapper<ViewPortComponent> viewportMapper;

    private int focusEntityId = -1;
    private final float xMin, xMax, yMin, yMax;

    private final Vector3 mVector3 = new Vector3();
    private float newZoom = 1f;

    public CameraSystem(float xMin, float xMax, float yMin, float yMax) {
        this.xMin = xMin;
        this.xMax = xMax;
        this.yMin = yMin;
        this.yMax = yMax;
    }

    @Override
    protected void process(int entity) {
        ViewPortComponent viewPortComponent = viewportMapper.get(entity);
        OrthographicCamera camera = (OrthographicCamera) viewPortComponent.viewPort.getCamera();

        if (camera.zoom != newZoom) {
            camera.zoom = MathUtils.lerp(camera.zoom, newZoom, 0.01f);
        }

        if (focusEntityId != -1) {
            TransformComponent transformComponent = transformMapper.get(focusEntityId);

            if (transformComponent != null) {
                float x = Math.max(xMin, Math.min(xMax, transformComponent.x));
                float y = Math.max(yMin, Math.min(yMax, transformComponent.y + 2));

                mVector3.set(x, y, 0);
                camera.position.lerp(mVector3, 0.1f);
            }
        }
    }

    public void changeZoomTo(float newZoom) {
        this.newZoom = newZoom;
    }

    public void setFocusEntityId(int focusEntityId) {
        this.focusEntityId = focusEntityId;
    }
}
