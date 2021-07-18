package com.arlania;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.jboss.netty.bootstrap.ServerBootstrap;
import org.jboss.netty.channel.socket.nio.NioServerSocketChannelFactory;
import org.jboss.netty.util.HashedWheelTimer;

import com.arlania.engine.GameEngine;
import com.arlania.engine.task.TaskManager;
import com.arlania.engine.task.impl.ServerTimeUpdateTask;
import com.arlania.model.container.impl.Shop.ShopManager;
import com.arlania.model.definitions.ItemDefinition;
import com.arlania.model.definitions.NPCDrops;
import com.arlania.model.definitions.NpcDefinition;
import com.arlania.model.definitions.WeaponInterfaces;
import com.arlania.net.PipelineFactory;
import com.arlania.net.security.ConnectionHandler;
import com.arlania.world.clip.region.RegionClipping;
import com.arlania.world.content.Bork;
import com.arlania.world.content.CustomObjects;
import com.arlania.world.content.DropSimulator;
import com.arlania.world.content.ItemComparing;
import com.arlania.world.content.Lottery;
import com.arlania.world.content.MonsterDrops;
import com.arlania.world.content.Onslaught;
import com.arlania.world.content.PlayerPunishment;
import com.arlania.world.content.Scoreboards;
import com.arlania.world.content.TheMay;
import com.arlania.world.content.TheRick;
import com.arlania.world.content.TheSeph;
import com.arlania.world.content.Tztok;
import com.arlania.world.content.WellOfGoodwill;
import com.arlania.world.content.aoesystem.AOESystem;
import com.arlania.world.content.clan.ClanChatManager;
import com.arlania.world.content.combat.effect.CombatPoisonEffect.CombatPoisonData;
import com.arlania.world.content.combat.strategy.CombatStrategies;
import com.arlania.world.content.dialogue.DialogueManager;
import com.arlania.world.content.discordbot.JavaCord;
import com.arlania.world.content.grandexchange.GrandExchangeOffers;
import com.arlania.world.content.groupironman.GroupIronmanGroup;
import com.arlania.world.content.guidesInterface.GuideBook;
import com.arlania.world.content.pos.PlayerOwnedShopManager;
import com.arlania.world.content.serverperks.GlobalPerks;
import com.arlania.world.content.serverperks.PersonalPerks;
import com.arlania.world.content.starterprogression.StarterProgression;
import com.arlania.world.entity.impl.npc.NPC;
import com.google.common.base.Preconditions;
import com.google.common.util.concurrent.ThreadFactoryBuilder;


/**
 * Credit: lare96, Gabbe
 */
public final class GameLoader {

	private final ExecutorService serviceLoader = Executors.newSingleThreadExecutor(new ThreadFactoryBuilder().setNameFormat("GameLoadingThread").build());
	private final ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor(new ThreadFactoryBuilder().setNameFormat("GameThread").build());
	private final GameEngine engine;
	private final int port;

	protected GameLoader(int port) {
		this.port = port;
		this.engine = new GameEngine();
	}

	public void init() {
		Preconditions.checkState(!serviceLoader.isShutdown(), "The bootstrap has been bound already!");
		executeServiceLoad();
		serviceLoader.shutdown();
	}

	public void finish() throws IOException, InterruptedException {
		if (!serviceLoader.awaitTermination(15, TimeUnit.MINUTES))
			throw new IllegalStateException("The background service load took too long!");
		ExecutorService networkExecutor = Executors.newCachedThreadPool();
		ServerBootstrap serverBootstrap = new ServerBootstrap (new NioServerSocketChannelFactory(networkExecutor, networkExecutor));
        serverBootstrap.setPipelineFactory(new PipelineFactory(new HashedWheelTimer()));
		serverBootstrap.bind(new InetSocketAddress(port));
		executor.scheduleAtFixedRate(engine, 0, GameSettings.ENGINE_PROCESSING_CYCLE_RATE, TimeUnit.MILLISECONDS);
		TaskManager.submit(new ServerTimeUpdateTask());
	}
        
	private void executeServiceLoad() {
		if (GameSettings.MYSQL_ENABLED) {
		}    
                
		serviceLoader.execute(() -> ConnectionHandler.init());
		serviceLoader.execute(() -> PlayerPunishment.init());
		serviceLoader.execute(() -> RegionClipping.init());
		serviceLoader.execute(() -> CustomObjects.init());
		serviceLoader.execute(() -> ItemDefinition.init());
		serviceLoader.execute(() -> Lottery.init());
		serviceLoader.execute(() -> GrandExchangeOffers.init());
		serviceLoader.execute(() -> Scoreboards.init());
		serviceLoader.execute(() -> WellOfGoodwill.init());
		serviceLoader.execute(() -> ClanChatManager.init());
		serviceLoader.execute(() -> CombatPoisonData.init());
		serviceLoader.execute(() -> CombatStrategies.init());
		serviceLoader.execute(() -> NpcDefinition.parseNpcs().load());
		serviceLoader.execute(() -> NPCDrops.parseDrops().load());
		serviceLoader.execute(() -> WeaponInterfaces.parseInterfaces().load());
		serviceLoader.execute(() -> ShopManager.parseShops().load());
		serviceLoader.execute(() -> DialogueManager.parseDialogues().load());
		serviceLoader.execute(() -> NPC.init());
		serviceLoader.execute(() -> ItemComparing.getSingleton().loadItems());
	//	serviceLoader.execute(() -> ProfileViewing.init());
		serviceLoader.execute(() -> PlayerOwnedShopManager.loadShops());
		serviceLoader.execute(() -> MonsterDrops.initialize());
		serviceLoader.execute(() -> Tztok.initialize());
		serviceLoader.execute(() -> Bork.initialize());
		serviceLoader.execute(() -> Onslaught.initialize());
		serviceLoader.execute(() -> TheRick.initialize());
		serviceLoader.execute(() -> TheMay.initialize());
		serviceLoader.execute(() -> TheSeph.initialize());
		serviceLoader.execute(() -> GuideBook.loadGuideDataFile());
		serviceLoader.execute(() -> AOESystem.getSingleton().parseData());
		serviceLoader.execute(() -> DropSimulator.initializeNpcs());
		serviceLoader.execute(StarterProgression::loadTasks);
        serviceLoader.execute(GroupIronmanGroup::loadGroups);
		serviceLoader.execute(GlobalPerks.getInstance()::load);
		serviceLoader.execute(PersonalPerks.getInstance()::load);
		 if (!GameSettings.LOCALHOST)
				serviceLoader.execute(() -> {
					JavaCord.init();
					 System.err.println("Bot connected");
				});
	}

	public GameEngine getEngine() {
		return engine;
	}
}