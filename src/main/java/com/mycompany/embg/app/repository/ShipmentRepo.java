package com.mycompany.embg.app.repository;

import com.mycompany.embg.app.models.Shipment;

import java.util.ArrayList;
import java.util.List;

public class ShipmentRepo {

    private static final List<Shipment> shipmentList =
            new ArrayList<>();

    static {

        shipmentList.add(
                new Shipment(
                        "1",
                        "SD Negeri 01 Menteng",
                        "Nasi Ayam",
                        150,
                        "Dimasak"
                )
        );

        shipmentList.add(
                new Shipment(
                        "2",
                        "SMP Bina Bangsa",
                        "Nasi Ikan",
                        220,
                        "Dikirim"
                )
        );

        shipmentList.add(
                new Shipment(
                        "3",
                        "SMA 4 Jakarta",
                        "Nasi Daging",
                        350,
                        "Diterima"
                )
        );
    }

    public List<Shipment> getAllShipments() {
        return shipmentList;
    }

    public void updateStatus(
            String shipmentId,
            String status
    ) {

        for (Shipment s : shipmentList) {

            if (s.getId().equals(shipmentId)) {

                s.setStatus(status);
                break;
            }
        }
    }
}