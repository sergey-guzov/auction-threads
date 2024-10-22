package com.java.guzov;

import java.util.concurrent.TimeUnit;

public class Participant implements Runnable{

    private String name;
    private Auction auction;
    private boolean active;

    public Participant(String name, Auction auction) {
        this.name = name;
        this.auction = auction;
        this.active = true;
    }

    public String getName() {
        return name;
    }

    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
                if (active) {
                    Lot lot = auction.getCurrentLot();
                    if (!lot.isActive()) {
                        try {
                            TimeUnit.SECONDS.sleep(1);
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                        continue;
                    }
                    double suggestedCost = lot.getBiggestCost() + Math.random() * 100;
                    System.out.println(name + " suggests " + suggestedCost);
                    if (suggestedCost > lot.getBiggestCost()) {
                        lot.setBiggestParticipant(this,suggestedCost);
                        //System.out.println("Suggestion of the " + name + " was applied " + suggestedCost);
                    }
                    try {
                        TimeUnit.SECONDS.sleep(2);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }

                } else {
                    try {
                        TimeUnit.SECONDS.sleep(3);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
    }

    public void banParticipant(){
        this.active = false;
        Runnable task = () -> {
            try {
                System.out.println("Participant " + this.name + " couldn't pay and was banned");
                TimeUnit.SECONDS.sleep(6);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            unbanParticipant();
        };
        Thread thread = new Thread(task);
        thread.setDaemon(true);
        thread.start();
    }

    public void unbanParticipant(){
        this.active = true;
        System.out.println("Participant " + this.name + " was unbanned");
    }


}
