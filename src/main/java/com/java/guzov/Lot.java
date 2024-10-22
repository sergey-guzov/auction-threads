package com.java.guzov;

import java.util.concurrent.locks.ReentrantLock;

public class Lot {

    private Participant biggestParticipant;

    private boolean isActive;
    private double initialCost;
    private double biggestCost;
    private String title;

    ReentrantLock lock = new ReentrantLock(true);

    public Lot(String title, int cost) {
        this.biggestParticipant = null;
        this.initialCost = cost;
        this.biggestCost = cost;
        this.title = title;
        this.isActive = true;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public boolean isActive() {
        return isActive;
    }

    public Participant getBiggestParticipant() {
        return biggestParticipant;
    }

    public double getInitialCost() {
        return initialCost;
    }

    public double getBiggestCost() {
        return biggestCost;
    }

    public String getTitle() {
        return title;
    }

    public void setBiggestParticipant(Participant biggestParticipant, double biggestCost) {
        lock.lock();
        this.biggestParticipant = biggestParticipant;
        this.biggestCost = biggestCost;
        System.out.println("Current biggest price " + this.biggestCost + " suggested by " + this.biggestParticipant.getName());
        lock.unlock();
    }
}
