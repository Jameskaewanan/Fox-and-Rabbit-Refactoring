package io.muic.ooc.fab;


import java.util.Iterator;
import java.util.List;

public class Tiger extends Animal {

    // The tiger's food level, which is increased by eating rabbits and foxes.
    private int foodLevel;

    @Override
    public void init(boolean randomAge, Field field, Location location) {
        super.init(randomAge, field, location);
        foodLevel = RANDOM.nextInt(AnimalType.FOX.getFoodValue());
    }

    @Override
    public Location moveToNewLocation() {
        Location newLocation = findFood();
        if (newLocation == null) {
            newLocation = field.freeAdjacentLocation(getLocation());
        }
        return newLocation;
    }

    @Override
    protected int getMaxAge() {
        return 175;
    }

    @Override
    protected double getBreedingProbability() {
        return  AnimalType.TIGER.getBreedingProbability();
    }

    @Override
    protected int getMaxLiterSize() {
        return 2;
    }

    @Override
    protected int getBreedingAge() {
        return 20;
    }

    public void incrementHunger() {
        foodLevel--;
        if (foodLevel <= 0) {
            setDead();
        }
    }

    @Override
    public void act(List<AnimalProperties> newAnimals) {
        super.act(newAnimals);
        incrementHunger();
    }

    public Location findFood() {
        List<Location> adjacent = field.adjacentLocations(getLocation());
        Iterator<Location> it = adjacent.iterator();
        while (it.hasNext()) {
            Location where = it.next();
            Object animal = field.getObjectAt(where);
            if (animal instanceof Rabbit) {
                Rabbit rabbit = (Rabbit) animal;
                if (rabbit.isAlive()) {
                    rabbit.setDead();
                    foodLevel = AnimalType.RABBIT.getFoodValue();
                    return where;
                }
            }

            if (animal instanceof Fox) {
                Fox fox = (Fox) animal;
                if (fox.isAlive()) {
                    fox.setDead();
                    foodLevel = AnimalType.FOX.getFoodValue();
                    return where;
                }
            }
        }
        return null;
    }
}