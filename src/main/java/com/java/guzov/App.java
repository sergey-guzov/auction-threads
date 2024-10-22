package com.java.guzov;

import java.util.ArrayList;


public class App {
    public static void main(String[] args) {
        ArrayList<Lot> lots = new ArrayList<>();
        lots.add(new Lot("Queens crown", 500));
        lots.add(new Lot("The royal watch", 430));
        lots.add(new Lot("The ancient map", 200));
        lots.add(new Lot("Unknown Picasso painting", 800));
        lots.add(new Lot("Book printed in XVI centre", 600));
        ArrayList<Participant> participants = new ArrayList<>();
        Auction auction = new Auction(lots);
        participants.add(new Participant("Artur", auction));
        participants.add(new Participant("Sergey", auction));
        participants.add(new Participant("Mary", auction));
        participants.add(new Participant("Vladimir", auction));
        participants.add(new Participant("Robert", auction));
        auction.setParticipants(participants);
        auction.inviteParticipants();
        auction.startAuction();
    }
}
