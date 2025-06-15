package com.github.ashvard.gdx.happycrab.screen.level.level1;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.github.ashvard.gdx.happycrab.Resources;
import com.github.ashvard.gdx.happycrab.action.ActionMapperImpl;
import com.github.ashvard.gdx.happycrab.model.GameObjectFactory;
import com.github.ashvard.gdx.happycrab.screen.HUD;
import com.github.ashvard.gdx.happycrab.screen.level.Tags;
import com.github.ashvard.gdx.happycrab.screen.level.level1.script.HeroScript;
import com.github.ashvard.gdx.happycrab.screen.level.level1.systems.AnimationSystem;
import com.github.ashvard.gdx.happycrab.screen.level.level1.systems.CameraSystem;
import com.github.ashvard.gdx.happycrab.screen.level.level1.systems.PlatformSystem;
import com.github.ashvard.gdx.happycrab.screen.level.level1.systems.components.*;
import com.github.ashvard.gdx.simple.animation.SimpleAnimation;
import com.github.ashvard.gdx.simple.structure.screen.AbstractGameScreen;
import com.github.ashvard.gdx.simple.input.InputSystem;
import games.rednblack.editor.renderer.SceneConfiguration;
import games.rednblack.editor.renderer.SceneLoader;
import games.rednblack.editor.renderer.resources.AsyncResourceManager;
import games.rednblack.editor.renderer.utils.ComponentRetriever;
import games.rednblack.editor.renderer.utils.ItemWrapper;

public class Level1Screen extends AbstractGameScreen {

    private SceneLoader sceneLoader;
    private AsyncResourceManager asyncResourceManager;
    private HUD hud;

    private OrthographicCamera camera;
    private Viewport viewport;

    private com.artemis.World artemisWorld;

    private InputSystem inputSystem;

    @Override
    public void show() {
        super.show();

        hud = new HUD();
        hud.setGameManager(getGameManager());
        hud.show();

        camera = new OrthographicCamera();
        viewport = new ExtendViewport(10, 7, camera);

        AssetManager assetManager = getGameManager().assetManager;
        asyncResourceManager = assetManager.get(Resources.HyperLap2D.PROJECT, AsyncResourceManager.class);

        SceneConfiguration config = new SceneConfiguration();
        //config.setRendererSystem();
        config.setResourceRetriever(asyncResourceManager);

        CameraSystem cameraSystem = new CameraSystem(6.5f, 40, 4, 6);

        AnimationSystem animationSystem = new AnimationSystem();
        animationSystem.addAnimation(assetManager.<SimpleAnimation>get(Resources.Animations.CRAB_ANIM_FSM));

        config.addSystem(new PlatformSystem());
        config.addSystem(cameraSystem);
        config.addSystem(animationSystem);

        sceneLoader = new SceneLoader(config);
        artemisWorld = sceneLoader.getEngine();

        // после new SceneLoader(config) должны быть добавления новых компонент во фреймворк
        ComponentRetriever.addMapper(PlatformComponent.class);
        ComponentRetriever.addMapper(AnimationComponent.class);
        ComponentRetriever.addMapper(InputComponent.class);
        ComponentRetriever.addMapper(HeroComponent.class);

        sceneLoader.loadScene("MainScene", viewport);
        ItemWrapper root = new ItemWrapper(sceneLoader.getRoot(), artemisWorld);

        // platforms
        initPlatforms(root);

        // hero
        ItemWrapper hero = root.getChild(Tags.HERO_ID);
        int heroEntity = hero.getEntity();
        AnimationComponent animationComponent = ComponentRetriever.create(heroEntity, AnimationComponent.class, artemisWorld);
        animationComponent.simpleAnimationComponent = GameObjectFactory.createAnimationComponentCrab();

        HeroScript heroScript = new HeroScript();
        hero.addScript(heroScript);

        // camera
        cameraSystem.setFocus(heroEntity);

        // InputSystem
        inputSystem = new InputSystem(5, new ActionMapperImpl());
        InputComponent inputComponent = ComponentRetriever.create(heroEntity, InputComponent.class, artemisWorld);
        inputComponent.inputActions = inputSystem.getInputActions();
        Gdx.input.setInputProcessor(inputSystem.getInputProcessor());

        // collectables
        sceneLoader.addComponentByTagName(Tags.SEA_SHELL, SeaShellComponent.class);
        sceneLoader.addComponentByTagName(Tags.PEARL, PearlComponent.class);

        // hero component
        HeroComponent heroComponent = ComponentRetriever.create(heroEntity, HeroComponent.class, artemisWorld);
        heroComponent.setLifeCount(4);
        heroComponent.addListener(hud);
        heroComponent.notifyListeners();
    }

    private void initPlatforms(ItemWrapper root) {
        ItemWrapper platform1 = root.getChild(Tags.PLATFORM_1_ID);
        int platformEntity = platform1.getEntity();
        PlatformComponent platform1Component = ComponentRetriever.create(platformEntity, PlatformComponent.class, artemisWorld);
        platform1Component.isPingPong = true;
        platform1Component.startX = 11.84f + 1.09f / 2f;
        platform1Component.startY = 5.4f + 0.14f / 2f;
        platform1Component.endX = 12.84f + 1.09f / 2f; // platform1Component.startX;
        platform1Component.endY = 6.4f + 0.14f / 2f;
        platform1Component.timeMs = 2f;
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(Color.BLACK.r, Color.BLACK.g, Color.BLACK.b, Color.BLACK.a);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        viewport.apply();

        inputSystem.update(delta);
        artemisWorld.process();
        inputSystem.reset();

        hud.render(delta);
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
        if (width != 0 && height != 0) {
            sceneLoader.resize(width, height);
            hud.resize(width, height);
        }

    }

    @Override
    public void pause() {
        if (hud == null) {
            return;
        }
        hud.pause();
    }

    @Override
    public void resume() {
        if (hud == null) {
            return;
        }
        hud.resume();
    }

    @Override
    public void hide() {
        if (hud == null) {
            return;
        }
        hud.hide();
    }

    @Override
    public void dispose() {
        if (hud == null) {
            return;
        }
        hud.dispose();
    }

}
