package com.example.shdemo.service;

import java.util.List;

import com.example.shdemo.domain.Lekarstwo;
import com.example.shdemo.domain.Osoba;

public interface SellingManager {
	
	void addClient(Osoba osoba);
	List<Osoba> getAllClients();
	void deleteClient(Osoba osoba);
	Osoba findClientByPin(String pin);
	
	Long addNewCar(Lekarstwo lekarstwo);
	List<Lekarstwo> getAvailableCars();
	void disposeCar(Osoba osoba, Lekarstwo lekarstwo);
	Lekarstwo findCarById(Long id);

	List<Lekarstwo> getOwnedCars(Osoba osoba);
	void sellCar(Long personId, Long carId);

}
