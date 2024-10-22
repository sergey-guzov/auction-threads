package com.java.guzov;

import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Auction {
     private ArrayList<Lot> lots;

     private ArrayList<Participant> participants;
     private int index;
     private ExecutorService executorService;

    public Auction(ArrayList<Lot> lots) {
        this.lots = lots;
        this.index = 0;
        this.participants = null;
        this.executorService = Executors.newCachedThreadPool();
    }

    public void setParticipants(ArrayList<Participant> participants) {
        this.participants = participants;
    }

    synchronized public Lot getCurrentLot() {
         if (index < lots.size()) {
             return lots.get(index);
         }
         return null;
     }

     public void inviteParticipants () {
         for (Participant participant:participants) {
             executorService.submit(participant);
         }
     }

     public void startAuction () {
         while (index < lots.size()) {
             Lot currentLot = getCurrentLot();
             System.out.println("Lot " + currentLot.getTitle() + " is on auction");
             try {
                 TimeUnit.SECONDS.sleep(10);
                 currentLot.setActive(false);
                 finishTrade(currentLot);
             } catch (InterruptedException e) {
                 throw new RuntimeException(e);
             }
             index += 1;
         }
         executorService.shutdownNow();
         try {
             TimeUnit.SECONDS.sleep(1);
         } catch (InterruptedException e) {
             throw new RuntimeException(e);
         }
         System.out.println("Auction ended");
     }

     public void finishTrade (Lot lot) {
        synchronized (lot) {
            Participant winner = lot.getBiggestParticipant();
            System.out.println("Trade for lot " + lot.getTitle() + " finished." + "\nThe winner is " + winner.getName());
            System.out.println("Payment process...");
            if (tryToPay()) {
                System.out.println("Lot " + lot.getTitle() + " successfully paid by " + winner.getName());
            } else {
                winner.banParticipant();
            }
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
     }

    public boolean tryToPay() {
        double value = Math.random();
        return value > 0.5;

    }

}
