package io.muic.ooc.fab;

import java.util.List;
import java.util.Random;

public abstract class Animal implements AnimalProperties {
    // Characteristics shared by all animals (class variables).

    protected static final Random RANDOM = new Random();

    private boolean alive = true;

    private Location location;

    protected Field field;

    private int age = 0;

    public abstract Location moveToNewLocation();

    protected abstract int getBreedingAge();

    protected abstract int getMaxLiterSize();

    protected abstract double getBreedingProbability();

    protected abstract int getMaxAge();

    @Override
    public void init(boolean randomAge, Field field, Location location) {
        this.field = field;
        setLocation(location);
        if (randomAge) {
            age = RANDOM.nextInt(getMaxAge());
        }
    }

    @Override
    public void act(List<AnimalProperties> newAnimals) {
        incrementAge();
        if (isAlive()) {
            giveBirth(newAnimals);

            Location newLocation = moveToNewLocation();

            if (newLocation != null) {
                setLocation(newLocation);
            } else {
                setDead();
            }
        }
    }
    // Check whether the animal is alive or not, return true if animal is alive
    @Override
    public boolean isAlive() {
        return alive;
    }

    // Indicate that the animal is no longer alive, remove from the field
    @Override
    public void setDead() {
        alive = false;
        if (location != null) {
            field.clear(location);
            location = null;
            field = null;
        }
    }

    // Get location of the animal
    @Override
    public Location getLocation() {
        return location;
    }

    // Place the animal at the new location in the field
    @Override
    public void setLocation(Location newLocation) {
        if (location != null) {
            field.clear(location);
        }
        location = newLocation;
        field.place(this, newLocation);
    }

    // Increment the age of the animal
    @Override
    public void incrementAge() {
        age++;
        if (age > getMaxAge()) {
            setDead();
        }
    }

    // Check whether animal can give birth at this stage, new births will be made into free adjacent location
    @Override
    public void giveBirth(List<AnimalProperties> newAnimals) {

        List<Location> free = field.getFreeAdjacentLocations(location);
        int births = breed();
        for (int b = 0; b < births && free.size() > 0; b++) {
            Location loc = free.remove(0);

            Animal young = breedOne(field, loc);
            newAnimals.add(young);
        }
    }

    @Override
    public Animal breedOne(Field field, Location location) {
        return AnimalFactory.createAnimal(getClass(), field, location);
    }

    // Generate a births
    @Override
    public int breed() {
        int births = 0;
        if (canBreed() && RANDOM.nextDouble() <= getBreedingProbability()) {
            births = RANDOM.nextInt(getMaxLiterSize()) + 1;
        }
        return births;
    }

    // Check if can breed
    @Override
    public boolean canBreed() {
        return age >= getBreedingAge();
    }

}