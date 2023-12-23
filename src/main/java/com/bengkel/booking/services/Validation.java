package com.bengkel.booking.services;

import java.lang.reflect.Member;
import java.security.Provider.Service;
import java.util.List;
import java.util.Scanner;

import com.bengkel.booking.models.Customer;
import com.bengkel.booking.models.ItemService;
import com.bengkel.booking.models.MemberCustomer;
import com.bengkel.booking.models.Vehicle;
import com.bengkel.booking.repositories.CustomerRepository;

public class Validation {
	
        
	
	public static String validasiInput(String question, String errorMessage, String regex) {
	    Scanner input = new Scanner(System.in);
	    String result;
	    boolean isLooping = true;
	    do {
	      System.out.print(question);
	      result = input.nextLine();

	      //validasi menggunakan matches
	      if (result.matches(regex)) {
	        isLooping = false;
	      }else {
	        System.out.println(errorMessage);
	      }

	    } while (isLooping);

	    return result;
	  }
	
	public static int validasiNumberWithRange(String question, String errorMessage, String regex, int max, int min) {
	    int result;
	    boolean isLooping = true;
	    do {
	      result = Integer.valueOf(validasiInput(question, errorMessage, regex));
	      if (result >= min && result <= max) {
	        isLooping = false;
	      }else {
	        System.out.println("Pilihan angka " + min + " s.d " + max);
	      }
	    } while (isLooping);

	    return result;
	  }

	public static Customer validasiLogin(String customerId, String password) {
		List<Customer> listAllCustomer = MenuService.getAllCustomers();
	    Customer customer;
	    
		customer = listAllCustomer.stream()
					.filter(customers -> customers.getCustomerId().equals(customerId))
					.findFirst()
					.orElse(null);

		if(customer!=null){
			if(customer.getPassword().equals(password)){
				return customer;
			}else{
				System.out.println("Password yang anda Masukan Salah!");
				return null;
			}
		}else{
			System.out.println("Customer Id Tidak Ditemukan atau Salah!");
			return null;
		}
	}

	public static boolean validasiLoginAttempt(int loginCounter) {
		
		if(loginCounter>=2){
			return false;
		}else{
			return true;
		}
	}

	public static Vehicle findVehicle(Customer customer, String vehicleId) {
		List<Vehicle> vehicles = customer.getVehicles();
			String checkeId = vehicleId;
			return vehicles.stream()
				.filter(vehicle -> vehicle.getVehiclesId().equals(checkeId))
				.findFirst()
				.orElse(null);
	}

	

	public static boolean validasiVehicle(Vehicle vehicle) {
		if(vehicle!=null){
			return true;
		}else{
			System.out.println("Kendaraan Tidak ditemukan.");
			return false;
		}
	}

	public static ItemService findService(String serviceId) {

		ItemService choosedService = MenuService.getAllServices().stream()
                           .filter(service -> service.getServiceId().equals(serviceId))
                           .findFirst()
                           .orElse(null);

		return choosedService;

	}

	public static ItemService validateService(String serviceId, List<ItemService> servicesList) {

		ItemService choosedService = servicesList.stream()
                           .filter(service -> service.getServiceId().equals(serviceId))
                           .findFirst()
                           .orElse(null);

		return choosedService;

	}

	public static boolean isMember(Customer customer) {
		if(customer instanceof MemberCustomer){
			return true;
		}else{
			return false;
		}
	}
}
