package io.muic.ooc.fab;

import java.util.HashMap;
import java.util.Map;

public class AnimalFactory {
    private static Map<AnimalType, Class> animalClassMap = new HashMap<AnimalType, Class>() {{
        AnimalType[] animalTypes = AnimalType.values();
        for (int i = 0; i < animalTypes.length; i++) {
            put(animalTypes[i], animalTypes[i].getAnimalClass());
        }
    }};

    public static Animal createAnimal(AnimalType animalType, Field field, Location location) {
        Class animalClass = animalClassMap.get(animalType);
        return createAnimal(animalClass, field, location);
    }

    public static Animal createAnimal(Class animalClass, Field field, Location location) {
        if (animalClass != null) {
            try {
                Animal animal = (Animal) animalClass.newInstance();
                animal.init(true, field, location);
                return animal;
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        throw new IllegalArgumentException("Unknown Species");
    }
}