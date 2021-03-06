package TestJava.testjava.classes;

import TestJava.testjava.helpers.CustomName;
import TestJava.testjava.models.VillageModel;
import TestJava.testjava.repositories.VillageRepository;
import org.bukkit.entity.LivingEntity;

public class CustomEntity {
    private LivingEntity entity;

    private CustomEntity() {
    }

    public CustomEntity(LivingEntity entity) {
        this.entity = entity;
    }

    public LivingEntity getEntity() {
        return entity;
    }

    public VillageModel getVillage() {
        return VillageRepository.get(CustomName.squareBrackets(this.entity.getCustomName(), 0));
    }

    public void setVillage(VillageModel village) {
        this.entity.setCustomName(this.entity.getCustomName()
                .replaceAll("(?<=\\[).*?(?=\\])", village.getId()));
    }
}
