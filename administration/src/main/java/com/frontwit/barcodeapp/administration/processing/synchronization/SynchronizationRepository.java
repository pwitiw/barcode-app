package com.frontwit.barcodeapp.administration.processing.synchronization;


import java.time.Instant;

public interface SynchronizationRepository {
    Instant getLastSynchronizationDate();

    void updateSyncDate();
}
