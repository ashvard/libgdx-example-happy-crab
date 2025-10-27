package com.github.ashvard.gdx.happycrab.screen.level.level1;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.IntSet;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.github.ashvard.gdx.happycrab.Resources;
import com.github.ashvard.gdx.happycrab.action.ActionMapperImpl;
import com.github.ashvard.gdx.happycrab.model.GameObjectFactory;
import com.github.ashvard.gdx.happycrab.screen.HUD;
import com.github.ashvard.gdx.happycrab.screen.level.Tags;
import com.github.ashvard.gdx.happycrab.screen.level.level1.script.GreenFishScript;
import com.github.ashvard.gdx.happycrab.screen.level.level1.script.HeroScript;
import com.github.ashvard.gdx.happycrab.screen.level.level1.systems.AnimationSystem;
import com.github.ashvard.gdx.happycrab.screen.level.level1.systems.CameraSystem;
import com.github.ashvard.gdx.happycrab.screen.level.level1.systems.PlatformSystem;
import com.github.ashvard.gdx.happycrab.screen.level.level1.systems.components.*;
import com.github.ashvard.gdx.simple.animation.SimpleAnimation;
import com.github.ashvard.gdx.simple.input.InputSystem;
import com.github.ashvard.gdx.simple.structure.screen.AbstractGameScreen;
import games.rednblack.editor.renderer.SceneConfiguration;
import games.rednblack.editor.renderer.SceneLoader;
import games.rednblack.editor.renderer.components.ScriptComponent;
import games.rednblack.editor.renderer.resources.AsyncResourceManager;
import games.rednblack.editor.renderer.systems.render.HyperLap2dRenderer;
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

    private ItemWrapper root;

    @Override
    public void show() {
        super.show();

        hud = new HUD();
        hud.setGameManager(getGameManager());
        hud.show();

        camera = new OrthographicCamera();
        viewport = new ExtendViewport(10f, 7f, camera);

        AssetManager assetManager = getGameManager().assetManager;
        asyncResourceManager = assetManager.get(Resources.HyperLap2D.PROJECT, AsyncResourceManager.class);

        SceneConfiguration config = new SceneConfiguration();
        //config.setRendererSystem();
        config.setResourceRetriever(asyncResourceManager);

        CameraSystem cameraSystem = new CameraSystem(6.5f, 40f, 4f, 6f);

        AnimationSystem animationSystem = new AnimationSystem();
        animationSystem.addAnimation(assetManager.<SimpleAnimation>get(Resources.Animations.CRAB_ANIM_FSM));
        animationSystem.addAnimation(assetManager.<SimpleAnimation>get(Resources.Animations.GREEN_FISH_FSM));

        config.addSystem(new PlatformSystem());
        config.addSystem(cameraSystem);
        config.addSystem(animationSystem);

        HyperLap2dRenderer.clearColor.set(new Color(0x9f9ff900)); // цвет фона задника
        sceneLoader = new SceneLoader(config);
        artemisWorld = sceneLoader.getEngine();

        // после new SceneLoader(config) должны быть добавления новых компонент во фреймворк
        ComponentRetriever.addMapper(PlatformComponent.class);
        ComponentRetriever.addMapper(AnimationComponent.class);
        ComponentRetriever.addMapper(InputComponent.class);
        ComponentRetriever.addMapper(HeroComponent.class);
        ComponentRetriever.addMapper(DamageComponent.class);

        sceneLoader.loadScene("MainScene", viewport);
        root = new ItemWrapper(sceneLoader.getRoot(), artemisWorld);

        // platforms
        initPlatforms(root);

        // hero
        ItemWrapper hero = root.getChild(Tags.HERO_ID);
        int heroEntity = hero.getEntity();
        AnimationComponent crabAnimationComponent = ComponentRetriever.create(heroEntity, AnimationComponent.class, artemisWorld);
        crabAnimationComponent.simpleAnimationComponent = GameObjectFactory.createAnimationComponentCrab();
        HeroScript heroScript = new HeroScript();
        hero.addScript(heroScript);

        // hero component
        HeroComponent heroComponent = ComponentRetriever.create(heroEntity, HeroComponent.class, artemisWorld);
        heroComponent.setLifeCount(4);
        heroComponent.addListener(hud);
        heroComponent.notifyListeners();

        // camera
        cameraSystem.setFocusEntityId(heroEntity);

        // InputSystem
        inputSystem = new InputSystem(5, new ActionMapperImpl());
        InputComponent inputComponent = ComponentRetriever.create(heroEntity, InputComponent.class, artemisWorld);
        inputComponent.inputActions = inputSystem.getInputActions();
        Gdx.input.setInputProcessor(inputSystem.getInputProcessor());

        // green fish
        initGreenFish(root);

        // collectables
        sceneLoader.addComponentByTagName(Tags.SEA_SHELL, SeaShellComponent.class);
        sceneLoader.addComponentByTagName(Tags.PEARL, PearlComponent.class);
    }

    private void initGreenFish(ItemWrapper root) {
        initGreenFishesByTag(root);
        initGreenFishesByCustomIds(root);
    }

    private void initGreenFishesByCustomIds(ItemWrapper root) {
        ItemWrapper greenFish1 = root.getChild(Tags.GREEN_FISH_1_ID);
        PlatformComponent platform1Component = ComponentRetriever.create(greenFish1.getEntity(), PlatformComponent.class, artemisWorld);
        platform1Component.isPingPong = true;
        platform1Component.startX = 16.5f + 0f / 2f;
        platform1Component.startY = 2.3f + 0f / 2f;
        platform1Component.endX = 16.5f + 0f / 2f;
        platform1Component.endY = 8.3f + 0f / 2f;
        platform1Component.timeMs = 4f;

        ItemWrapper greenFish2 = root.getChild(Tags.GREEN_FISH_2_ID);
        PlatformComponent platform2Component = ComponentRetriever.create(greenFish2.getEntity(), PlatformComponent.class, artemisWorld);
        platform2Component.isPingPong = true;
        platform2Component.startX = 17.72f + 2.56f / 2f;
        platform2Component.startY = 4.0f + 4f / 2f;
        platform2Component.endX = 17.72f + 2.56f / 2f;
        platform2Component.endY = 0.5f + 4f / 2f;
        platform2Component.timeMs = 2f;

        ItemWrapper greenFish3 = root.getChild(Tags.GREEN_FISH_3_ID);
        PlatformComponent platform3Component = ComponentRetriever.create(greenFish3.getEntity(), PlatformComponent.class, artemisWorld);
        platform3Component.isPingPong = true;
        platform3Component.startX = 22.0f + 0f / 2f;
        platform3Component.startY = 2.3f + 0f / 2f;
        platform3Component.endX = 22.0f + 0f / 2f;
        platform3Component.endY = 8.3f + 0f / 2f;
        platform3Component.timeMs = 4f;

        ItemWrapper greenFish4 = root.getChild(Tags.GREEN_FISH_4_ID);
        PlatformComponent platform4Component = ComponentRetriever.create(greenFish4.getEntity(), PlatformComponent.class, artemisWorld);
        platform4Component.isPingPong = true;
        platform4Component.startX = 28.0f + 0f / 2f;
        platform4Component.startY = 4.5f + 0f / 2f;
        platform4Component.endX = 37.0f + 0f / 2f;
        platform4Component.endY = 4.5f + 0f / 2f;
        platform4Component.timeMs = 10f;
    }

    private void initGreenFishesByTag(ItemWrapper root) {
        IntSet childrenByTag = root.getChildrenByTag(Tags.GREEN_FISH);
        IntSet.IntSetIterator iterator = childrenByTag.iterator();
        while(iterator.hasNext) {
            int greenFish = iterator.next();
            AnimationComponent greenFishAnimationComponent = ComponentRetriever.create(greenFish, AnimationComponent.class, artemisWorld);
            greenFishAnimationComponent.simpleAnimationComponent = GameObjectFactory.createAnimationComponentGreenFish();

            ScriptComponent component = ComponentRetriever.get(greenFish, ScriptComponent.class, artemisWorld);
            component.addScript(GreenFishScript.class);
        }
    }

    private void initPlatforms(ItemWrapper root) {
        ItemWrapper platform1 = root.getChild(Tags.PLATFORM_1_ID);
        int platformEntity = platform1.getEntity();
        PlatformComponent platform1Component = ComponentRetriever.create(platformEntity, PlatformComponent.class, artemisWorld);
        platform1Component.isPingPong = true;
        platform1Component.startX = 11.84f + 1.09f / 2f;
        platform1Component.startY = 5.4f + 0.14f / 2f;
        platform1Component.endX = 12.84f + 1.09f / 2f;
        platform1Component.endY = 6.4f + 0.14f / 2f;
        platform1Component.timeMs = 2f;
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(Color.BLACK.r, Color.BLACK.g, Color.BLACK.b, Color.BLACK.a);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        viewport.apply();
        camera.update();

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
