package com.bengkel.booking.services;

import java.security.Provider.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.bengkel.booking.models.Customer;
import com.bengkel.booking.models.ItemService;
import com.bengkel.booking.models.MemberCustomer;
import com.bengkel.booking.models.Vehicle;
import com.bengkel.booking.repositories.BookingOrderRepository;

public class BengkelService {
	static Scanner input = new Scanner(System.in);
	static Customer customer;
	
	//Silahkan tambahkan fitur-fitur utama aplikasi disini
	
	//Login
	public static void login() {
		int loginCounter = 0;
		boolean isLooping = true;

		do {
			System.out.println("Masukan Customer Id:");
			String customerId = input.nextLine();
			System.out.println("Masukan Password:");
			String password = input.nextLine();

			customer = Validation.validasiLogin(customerId, password);

			if(customer!=null){
				loginCounter = 0;
				isLooping = false;
			}else{
				isLooping = Validation.validasiLoginAttempt(loginCounter++);
			}	
		} while (isLooping);
		
	}
	
	//Info Customer
	public static void infoCustomer() {
		if(customer instanceof MemberCustomer){
			PrintService.printMember(customer);
		}else{
			PrintService.printCustomer(customer);
		}
		
		
	}
	
	//Booking atau Reservation
	public static void booking(List<ItemService> itemServices){
		boolean isLooping = true;
		boolean isCanceled = false;
		String vehicleId;
		Vehicle choosedVehicle;
		do{
			System.out.println("Masukan Vehicle Id:");
			vehicleId = input.nextLine();
			choosedVehicle = Validation.findVehicle(customer, vehicleId);
			isLooping = !(Validation.validasiVehicle(choosedVehicle));
		}while(isLooping);

		PrintService.printService(itemServices, choosedVehicle.getVehicleType());

		boolean addService = true;
		boolean isMember = Validation.isMember(customer);
		List<ItemService> listServices = new ArrayList<ItemService>();
		do {
            System.out.println("Silahkan Masukkan Service Id:");
            String inputServiceID = input.nextLine();

			if(inputServiceID.equals("0")){
				isCanceled = true;
				break;
			}
			ItemService choosedService = Validation.findService(inputServiceID);
			ItemService validateService = Validation.validateService(inputServiceID, listServices);
			
			boolean dataValidation = false;

			if(choosedService!=null && validateService==null){
                listServices.add(choosedService);
				dataValidation = true;
            }else if(choosedService == null){
                System.out.println("Service yang dicari tidak tersedia");
            }else{
                System.out.println("Service sudah dipilih");
            }

			if(dataValidation){
				if(isMember){
					System.out.println("Ingin pilih service yang lain (Y/T)?");
					String continueInput = input.nextLine();
					if(continueInput.equalsIgnoreCase("T")){
						addService = false;
					}else if(continueInput.equalsIgnoreCase("Y")){
						addService = true;
					}
				}else{
					addService = false;
				}
			}
            
        }while (addService);


		String paymentMethod = "Cash";
		if(isMember){
			boolean isValid = false;
			do{
				System.out.println("Silahkan Pilih Metode Pembayaran (Saldo Coin atau Cash)");
				String inputPaymentMethod = input.nextLine();
				if(inputPaymentMethod.equalsIgnoreCase("Saldo Coin")||inputPaymentMethod.equalsIgnoreCase("Cash")){
					paymentMethod = inputPaymentMethod;
					isValid = true;
				}else{
					System.out.println("Input tidak valid!!");
				}
			}while(!isValid);
			
		}
		if(!isCanceled){
			BookingOrderRepository.addBookingOrder(customer, choosedVehicle, listServices, paymentMethod);
		}
		

	}
	
	//Top Up Saldo Coin Untuk Member Customer
	public static void topUp(){
		boolean isMember = Validation.isMember(customer);
		if(isMember){
			MemberCustomer member = (MemberCustomer) customer;
			System.out.println("Masukan besaran Top Up:");
			double topup = input.nextDouble();
			member.setSaldoCoin(member.getSaldoCoin()+topup);
		}else{
			System.out.println("Maaf fitur ini hanya untuk Member saja!");
		}
		
	}

	//Tampilkan Data Booking
	public static void bookingList(){
		PrintService.showBookingOrder(customer);
	}
	
	//Logout
	public static void logout(){
		customer = null;
	}
	
}
