package com.finsentinel.cnp;

public enum CnpPerformative {
    CFP,       // Call for Proposal (Task Announcement)
    PROPOSE,   // Bid
    REFUSE,    // Reject task
    ACCEPT_PROPOSAL, // Award task
    REJECT_PROPOSAL,
    INFORM,    // Result execution
    FAILURE
}
