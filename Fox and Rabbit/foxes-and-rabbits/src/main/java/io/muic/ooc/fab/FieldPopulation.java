package io.muic.ooc.fab;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class FieldPopulation {
    private static final Random RANDOM = new Random();

    private Map<AnimalType,Double> probabilityMap = new HashMap<AnimalType, Double> (){{
        AnimalType[] animalTypes = AnimalType.values();
        for (int i = 0; i < animalTypes.length; i++) {
           put(animalTypes[i], animalTypes[i].getBreedingProbability());
        }}};

        public void populate(Field field, List<AnimalProperties> animals) {
            field.clear();
            for (int row = 0; row < field.getDepth(); row++) {
                for (int col = 0; col < field.getWidth(); col++) {
                    for (Map.Entry<AnimalType, Double> entry : probabilityMap.entrySet()){
                        if (RANDOM.nextDouble() <= entry.getValue()){
                            Location location = new Location(row, col);
                            Animal animal = AnimalFactory.createAnimal(entry.getKey(), field, location);
                            animals.add(animal);
                            break;
                        }
                    }
                }
            }
        }
    }