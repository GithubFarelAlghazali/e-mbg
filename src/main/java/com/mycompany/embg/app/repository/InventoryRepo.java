package com.mycompany.embg.app.repository;

import com.mycompany.embg.app.models.InventoryItem;

import java.util.ArrayList;
import java.util.List;

public class InventoryRepo {

    private static final List<InventoryItem> inventoryList =
            new ArrayList<>();

    public InventoryRepo() {

    }

    static {

        inventoryList.add(
                new InventoryItem(
                        "1",
                        "Beras Putih Premium",
                        1250,
                        "Kg",
                        16000
                )
        );

        inventoryList.add(
                new InventoryItem(
                        "2",
                        "Telur Ayam Ras",
                        45,
                        "Papan (30)",
                        65000
                )
        );

        inventoryList.add(
                new InventoryItem(
                        "3",
                        "Minyak Goreng Sawit",
                        320,
                        "Liter",
                        18000
                )
        );

        inventoryList.add(
                new InventoryItem(
                        "4",
                        "Daging Ayam Potong",
                        150,
                        "Kg",
                        38000
                )
        );
    }

    public List<InventoryItem> getAllItems() {
        return inventoryList;
    }

    public void addItem(InventoryItem item) {
        inventoryList.add(item);
    }

    public void deleteItem(String id) {

        inventoryList.removeIf(
                item -> item.getId().equals(id)
        );
    }

    public void updateItem(
            String id,
            String namaBarang,
            int stok,
            String satuan,
            int harga
    ) {

        for (InventoryItem item : inventoryList) {

            if (item.getId().equals(id)) {

                item.setNamaBarang(namaBarang);
                item.setStok(stok);
                item.setSatuan(satuan);
                item.setHarga(harga);

                return;
            }
        }
    }
}