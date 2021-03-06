package TestJava.testjava.repositories;

import TestJava.testjava.TestJava;
import TestJava.testjava.models.EatableModel;

import javax.annotation.Nonnull;
import java.util.Collection;

public class EatableRepository {
    public static Collection<EatableModel> getAll() {
        return TestJava.database.findAll(EatableModel.class);
    }

    public static void update(@Nonnull EatableModel eatable) {
        TestJava.database.upsert(eatable);
    }

    public static void remove(@Nonnull EatableModel eatable) {
        EatableModel eatableModel = TestJava.database.findById(eatable.getId(), EatableModel.class);
        if (eatableModel != null) {
            TestJava.database.remove(eatableModel, EatableModel.class);
        }
    }

    public static Collection<EatableModel> getWhere(String id) {
        String jxQuery = String.format("/.[village=\"%s\"]", id);
        return TestJava.database.find(jxQuery, EatableModel.class);
    }
}
