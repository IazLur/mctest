package TestJava.testjava.threads;

import TestJava.testjava.TestJava;
import TestJava.testjava.classes.CustomEntity;
import TestJava.testjava.helpers.Colorize;
import TestJava.testjava.helpers.CustomName;
import TestJava.testjava.models.EmpireModel;
import TestJava.testjava.models.VillageModel;
import TestJava.testjava.repositories.EmpireRepository;
import TestJava.testjava.repositories.VillageRepository;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Pillager;

import java.util.Collection;
import java.util.UUID;

import static org.bukkit.Bukkit.getScheduler;

public class WarThread implements Runnable {

    private final String village;
    private final UUID uniq;
    private final VillageModel enemy;
    private final EmpireModel empire;
    private final String me;

    public WarThread(String village, UUID uniq, VillageModel enemy, EmpireModel empire, String displayName) {
        this.village = village;
        this.uniq = uniq;
        this.enemy = enemy;
        this.empire = empire;
        this.me = displayName;
    }

    @Override
    public void run() {
        Collection<CustomEntity> entities = CustomName.whereVillage(village);
        VillageModel other = VillageRepository.get(enemy.getId());
        VillageModel meVillage = VillageRepository.get(village);
        if (entities.size() == 0 || other.getPlayerName().equals(me)) {
            Bukkit.getServer().broadcastMessage("La guerre entre " + Colorize.name(village)
                    + " et " + Colorize.name(enemy.getId()) + " s'est terminée (" +
                    (entities.size() == 0 ? "défaite" : "victoire") + ")");
            getScheduler().cancelTask(TestJava.threads.get(uniq));
            empire.setIsInWar(false);
            empire.setEnemyName("");
            Location bell = VillageRepository.getBellLocation(meVillage);
            for (CustomEntity entity : entities) {
                if (entity.getEntity() instanceof Pillager) {
                    ((Pillager) entity.getEntity()).getPathfinder().moveTo(bell);
                }
            }
            EmpireRepository.update(empire);
            TestJava.threads.remove(uniq);
        }
        Location bell = VillageRepository.getBellLocation(enemy);
        for (CustomEntity entity : entities) {
            if (entity.getEntity() instanceof Pillager) {
                ((Pillager) entity.getEntity()).getPathfinder().moveTo(bell);
            }
        }
    }
}