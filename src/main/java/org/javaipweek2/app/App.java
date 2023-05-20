package org.javaipweek2.app;


import org.javaipweek2.app.model.Hero;
import org.javaipweek2.app.model.Squad;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import static spark.Spark.*;

public class App {
    private static final List<Hero> heroes = new ArrayList<>();
    private static final List<Squad> squads = new ArrayList<>();
    private static final Gson gson = new Gson();

    public static void main(String[] args) {
        port(4567);

        get("/", (request, response) -> "Hello, world!");

        // Routes for heroes
        path("/heroes", () -> {
            post("", (request, response) -> {
                Hero hero = gson.fromJson(request.body(), Hero.class);
                heroes.add(hero);
                return gson.toJson(hero);
            });

            get("", (request, response) -> gson.toJson(heroes));

            get("/:id", (request, response) -> {
                int id = Integer.parseInt(request.params("id"));
                Hero hero = getHeroById(id);
                if (hero != null) {
                    return gson.toJson(hero);
                } else {
                    response.status(404);
                    return "Hero not found";
                }
            });

            put("/:id", (request, response) -> {

                int id = Integer.parseInt(request.params("id"));
                Hero hero = getHeroById(id);
                if (hero != null) {
                    Hero updatedHero = gson.fromJson(request.body(), Hero.class);
                    hero.setName(updatedHero.getName());
                    hero.setAge(updatedHero.getAge());
                    hero.setSpecialPower(updatedHero.getSpecialPower());
                    hero.setWeakness(updatedHero.getWeakness());
                    return gson.toJson(hero);
                } else {
                    response.status(404);
                    return "Hero not found";
                }
            });

            delete("/:id", (request, response) -> {
                int id = Integer.parseInt(request.params("id"));
                Hero hero = getHeroById(id);
                if (hero != null) {
                    heroes.remove(hero);
                    return "Hero deleted";
                } else {
                    response.status(404);
                    return "Hero not found";
                }
            });
        });

        // Routes for squads
        path("/squads", () -> {
            post("", (request, response) -> {
                Squad squad = gson.fromJson(request.body(), Squad.class);
                squads.add(squad);
                return gson.toJson(squad);
            });
                    // Initialize database connector
            String databaseConnector = String.valueOf(new DatabaseConnector());
            try {
                databaseConnector.wait();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });

            get("", (request, response) -> gson.toJson(squads));

            get("/:id", (request, response) -> {
                int id = Integer.parseInt(request.params("id"));
                Squad squad = getSquadById(id);
                if (squad != null) {
                    return gson.toJson(squad);
                } else {
                    response.status(404);
                    return "Squad not found";
                }
            });

            put("/:id", (request, response) -> {
                int id = Integer.parseInt(request.params("id"));
                Squad squad = getSquadById(id);
                if (squad != null) {
                    Squad updatedSquad = gson.fromJson(request.body(), Squad.class);
                    squad.setMaxSize(updatedSquad.getMaxSize());
                    squad.setName(updatedSquad.getName());
                    squad.setCause(updatedSquad.getCause());
                    return gson.toJson(squad);
                } else {
                    response.status(404);
                    return "Squad not found";
                }
            });

            delete("/:id", (request, response) -> {
                int id = Integer.parseInt(request.params("id"));
                Squad squad = getSquadById(id);
                if (squad != null) {
                    squads.remove(squad);
                    return "Squad deleted";
                } else {
                    response.status(404);
                    return "Squad not found";
                }
            });
        }


    private static Hero getHeroById(int id) {
        for (Hero hero : heroes) {
            if (hero.getId() == id) {
                return hero;
            }
        }
        return null;
    }

    private static Squad getSquadById(int id) {
        for (Squad squad : squads) {
            if (squad.getId() == id) {
                return squad;
            }
        }
        return null;
    }
}
