package com.bengkel.booking.services;

import java.util.List;
import java.util.Scanner;

import com.bengkel.booking.models.Customer;
import com.bengkel.booking.models.ItemService;
import com.bengkel.booking.repositories.CustomerRepository;
import com.bengkel.booking.repositories.ItemServiceRepository;

public class MenuService {
	private static List<Customer> listAllCustomers = CustomerRepository.getAllCustomer();
	private static List<ItemService> listAllItemService = ItemServiceRepository.getAllItemService();
	private static Scanner input = new Scanner(System.in);
	public static void run() {
		boolean isLooping = true;
		do {
			login();
			mainMenu();
		} while (isLooping);
		
	}
	
	public static void login() {
		String[] listMenu = {"Login", "Exit"};
		int menuChoice = 0;
		boolean isLooping = true;

		do {
			PrintService.printLogin(listMenu, "Login");
			menuChoice = Validation.validasiNumberWithRange("Masukan Pilihan Menu:", "Input Harus Berupa Angka!", "^[0-9]+$", listMenu.length-1, 0);
			System.out.println(menuChoice);
			
			switch (menuChoice) {
			case 1:
				//panggil fitur Login
				BengkelService.login();
				isLooping = false;
				break;
			case 0:
				//Exit
				System.exit(0);
				break;
			default:
				System.out.println("Input tidak valid");
				break;
			}
		} while (isLooping);
		
	}
	
	public static void mainMenu() {
		String[] listMenu = {"Informasi Customer", "Booking Bengkel", "Top Up Bengkel Coin", "Informasi Booking", "Logout"};
		int menuChoice = 0;
		boolean isLooping = true;
		Customer customer = BengkelService.customer;

		if(customer != null){
			do {
				PrintService.printMenu(listMenu, "Booking Bengkel Menu");
				menuChoice = Validation.validasiNumberWithRange("Masukan Pilihan Menu:", "Input Harus Berupa Angka!", "^[0-9]+$", listMenu.length-1, 0);
				System.out.println(menuChoice);
				
				switch (menuChoice) {
				case 1:
					//panggil fitur Informasi Customer
					BengkelService.infoCustomer();
					break;
				case 2:
					//panggil fitur Booking Bengkel
					BengkelService.booking(listAllItemService);
					break;
				case 3:
					//panggil fitur Top Up Saldo Coin
					BengkelService.topUp();
					break;
				case 4:
					//panggil fitur Informasi Booking Order
					BengkelService.bookingList();
					break;
				default:
					System.out.println("Logout");
					BengkelService.logout();
					isLooping = false;
					break;
				}
			} while (isLooping);
		}
		
		
		
	}

	public static List<Customer> getAllCustomers(){
		return listAllCustomers;
	}

	public static List<ItemService> getAllServices(){
		return listAllItemService;
	}
	
	//Silahkan tambahkan kodingan untuk keperluan Menu Aplikasi
}
