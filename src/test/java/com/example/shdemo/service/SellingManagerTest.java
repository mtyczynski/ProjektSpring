package com.example.shdemo.service;

import static org.junit.Assert.assertEquals;

import java.util.List;

import com.example.shdemo.domain.Lekarstwo;
import com.example.shdemo.domain.Osoba;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:/beans.xml" })
@TransactionConfiguration(transactionManager = "txManager", defaultRollback = true)
@Transactional
public class SellingManagerTest {

	@Autowired
	SellingManager sellingManager;

	private final String NAME_1 = "Bolek";
	private final String PIN_1 = "1234";

	private final String NAME_2 = "Lolek";
	private final String PIN_2 = "4321";

	private final String MODEL_1 = "126p";
	private final String MAKE_1 = "Fiat";

	private final String MODEL_2 = "Mondeo";
	private final String MAKE_2 = "Ford";

	@Test
	public void addClientCheck() {

		List<Osoba> retrievedClients = sellingManager.getAllClients();

		// If there is a client with PIN_1 delete it
		for (Osoba client : retrievedClients) {
			if (client.getPin().equals(PIN_1)) {
				sellingManager.deleteClient(client);
			}
		}

		Osoba osoba = new Osoba();
		osoba.setFirstName(NAME_1);
		osoba.setPin(PIN_1);
		// ... other properties here

		// Pin is Unique
		sellingManager.addClient(osoba);

		Osoba retrievedClient = sellingManager.findClientByPin(PIN_1);

		assertEquals(NAME_1, retrievedClient.getFirstName());
		assertEquals(PIN_1, retrievedClient.getPin());
		// ... check other properties here
	}

	@Test
	public void addCarCheck() {

		Lekarstwo lekarstwo = new Lekarstwo();
		lekarstwo.setMake(MAKE_1);
		lekarstwo.setModel(MODEL_1);
		// ... other properties here

		Long carId = sellingManager.addNewCar(lekarstwo);

		Lekarstwo retrievedLekarstwo = sellingManager.findCarById(carId);
		assertEquals(MAKE_1, retrievedLekarstwo.getMake());
		assertEquals(MODEL_1, retrievedLekarstwo.getModel());
		// ... check other properties here

	}

	@Test
	public void sellCarCheck() {

		Osoba osoba = new Osoba();
		osoba.setFirstName(NAME_2);
		osoba.setPin(PIN_2);

		sellingManager.addClient(osoba);

		Osoba retrievedOsoba = sellingManager.findClientByPin(PIN_2);

		Lekarstwo lekarstwo = new Lekarstwo();
		lekarstwo.setMake(MAKE_2);
		lekarstwo.setModel(MODEL_2);

		Long carId = sellingManager.addNewCar(lekarstwo);

		sellingManager.sellCar(retrievedOsoba.getId(), carId);

		List<Lekarstwo> ownedLekarstwos = sellingManager.getOwnedCars(retrievedOsoba);

		assertEquals(1, ownedLekarstwos.size());
		assertEquals(MAKE_2, ownedLekarstwos.get(0).getMake());
		assertEquals(MODEL_2, ownedLekarstwos.get(0).getModel());
	}

	// @Test -
	public void disposeCarCheck() {
		// Do it yourself
	}

}
