package com.frontwit.barcodeapp.administration.processing.synchronization;


import java.time.LocalDate;

public interface SynchronizationRepository {
    LocalDate getLastSynchronizationDate();

    void updateSyncDate();
}
