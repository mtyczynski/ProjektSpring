package com.example.shdemo.service;

import java.util.ArrayList;
import java.util.List;

import com.example.shdemo.domain.Lekarstwo;
import com.example.shdemo.domain.Osoba;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional
public class SellingMangerHibernateImpl implements SellingManager {

	@Autowired
	private SessionFactory sessionFactory;

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	
	@Override
	public void addClient(Osoba osoba) {
		osoba.setId(null);
		sessionFactory.getCurrentSession().persist(osoba);
	}
	
	//@ride
	public void deleteClient(Osoba osoba) {
		osoba = (Osoba) sessionFactory.getCurrentSession().get(Osoba.class,
				osoba.getId());
		// lazy loading here
		for (Lekarstwo lekarstwo : osoba.getLekarstwos()) {
			lekarstwo.setSold(false);
			sessionFactory.getCurrentSession().update(lekarstwo);
		}
		sessionFactory.getCurrentSession().delete(osoba);
	}

	@Override
	public List<Lekarstwo> getOwnedCars(Osoba osoba) {
		osoba = (Osoba) sessionFactory.getCurrentSession().get(Osoba.class,
				osoba.getId());
		// lazy loading here - try this code without (shallow) copying
		List<Lekarstwo> lekarstwos = new ArrayList<Lekarstwo>(osoba.getLekarstwos());
		return lekarstwos;
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<Osoba> getAllClients() {
		return sessionFactory.getCurrentSession().getNamedQuery("osoba.all")				.list();
	}

	@Override
	public Osoba findClientByPin(String pin) {
		return (Osoba) sessionFactory.getCurrentSession().getNamedQuery("osoba.byPin").setString("pin", pin).uniqueResult();
	}


	@Override
	public Long addNewCar(Lekarstwo lekarstwo) {
		lekarstwo.setId(null);
		return (Long) sessionFactory.getCurrentSession().save(lekarstwo);
	}

	@Override
	public void sellCar(Long personId, Long carId) {
		Osoba osoba = (Osoba) sessionFactory.getCurrentSession().get(
				Osoba.class, personId);
		Lekarstwo lekarstwo = (Lekarstwo) sessionFactory.getCurrentSession()
				.get(Lekarstwo.class, carId);
		lekarstwo.setSold(true);
		osoba.getLekarstwos().add(lekarstwo);
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<Lekarstwo> getAvailableCars() {
		return sessionFactory.getCurrentSession().getNamedQuery("lekarstwo.unsold")
				.list();
	}
	@Override
	public void disposeCar(Osoba osoba, Lekarstwo lekarstwo) {

		osoba = (Osoba) sessionFactory.getCurrentSession().get(Osoba.class,
				osoba.getId());
		lekarstwo = (Lekarstwo) sessionFactory.getCurrentSession().get(Lekarstwo.class,
				lekarstwo.getId());

		Lekarstwo toRemove = null;
		// lazy loading here (osoba.getLekarstwos)
		for (Lekarstwo aLekarstwo : osoba.getLekarstwos())
			if (aLekarstwo.getId().compareTo(lekarstwo.getId()) == 0) {
				toRemove = aLekarstwo;
				break;
			}

		if (toRemove != null)
			osoba.getLekarstwos().remove(toRemove);

		lekarstwo.setSold(false);
	}

	@Override
	public Lekarstwo findCarById(Long id) {
		return (Lekarstwo) sessionFactory.getCurrentSession().get(Lekarstwo.class, id);
	}

}
