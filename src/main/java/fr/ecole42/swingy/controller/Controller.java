package fr.ecole42.swingy.controller;

import fr.ecole42.swingy.dao.HeroDAO;
import fr.ecole42.swingy.model.GameMap;
import fr.ecole42.swingy.model.Player;
import fr.ecole42.swingy.model.enemies.Enemy;
import fr.ecole42.swingy.model.enemies.EnemyBuilder;
import fr.ecole42.swingy.model.enemies.EnemyType;
import fr.ecole42.swingy.model.hero.Hero;
import fr.ecole42.swingy.view.ViewMode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@DependsOn("dbInitBean")
public class Controller {
	private final HeroDAO heroDAO;
	private final Player player;

	@Autowired
	public Controller(HeroDAO heroDAO, Player player) {
		this.heroDAO = heroDAO;
		this.player = player;

		this.player.setAllHeroes(this.heroDAO.index());
	}

	public boolean fightSimulation(EnemyType enemyType) {
		Hero hero = player.getCurrentHero();
		boolean res = hero.fight(new EnemyBuilder().enemyType(enemyType).build());
		if (res) {
			switch (enemyType) {
				case KOBOLD -> player.increaseExp(300 + 10);
				case GHOST -> player.increaseExp(300 + 15);
				case GOLEM -> player.increaseExp(300 + 20);
			}
		}
		return res;
	}

	public void setView(ViewMode view) {
		player.setView(view);
	}

	public void setCurrentHero(Hero hero) {
		player.setCurrentHero(hero);
	}

	public List<Hero> getAllHeroes() {
		return heroDAO.index();
	}

	public void save(Hero c) {
		heroDAO.save(c);
		player.setAllHeroes(this.heroDAO.index());
	}

	public void moveDirection(GameMap.directions direction) {
		player.movePlayer(direction);
	}

	public void resetCurrentHero() {
		player.setCurrentHero(null);
	}

	public Player getPlayer() {
		return player;
	}

	public boolean heroIsChosen() {
		return player.getCurrentHero() != null;
	}
}
