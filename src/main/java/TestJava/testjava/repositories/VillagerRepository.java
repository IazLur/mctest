package TestJava.testjava.repositories;

import TestJava.testjava.TestJava;
import TestJava.testjava.models.VillagerModel;

import java.util.Collection;
import java.util.UUID;

public class VillagerRepository {
    public static void remove(UUID uniqueId) {
        VillagerModel villager = TestJava.database.findById(uniqueId, VillagerModel.class);
        if (villager != null) {
            TestJava.database.remove(villager, VillagerModel.class);
        }
    }

    public static void update(VillagerModel villager) {
        TestJava.database.upsert(villager);
    }

    public static Collection<VillagerModel> getAll() {
        return TestJava.database.findAll(VillagerModel.class);
    }

    public static VillagerModel find(UUID uniqueId) {
        return TestJava.database.findById(uniqueId, VillagerModel.class);
    }
}
