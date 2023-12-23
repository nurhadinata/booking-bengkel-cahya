package com.bengkel.booking.services;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import com.bengkel.booking.models.BookingOrder;
import com.bengkel.booking.models.Car;
import com.bengkel.booking.models.Customer;
import com.bengkel.booking.models.ItemService;
import com.bengkel.booking.models.MemberCustomer;
import com.bengkel.booking.models.Vehicle;
import com.bengkel.booking.repositories.BookingOrderRepository;

public class PrintService {
	
	public static void printLogin(String[] listMenu, String title) {
		String line = "+---------------------------------+";
		int number = 1;
		String formatTable = " %-2s. %-25s %n";
		
		System.out.printf("%-25s %n", title);
		System.out.println(line);
		
		for (String data : listMenu) {
			if (number < listMenu.length) {
				System.out.printf(formatTable, number, data);
			}else {
				System.out.printf(formatTable, 0, data);
			}
			number++;
		}
		System.out.println(line);
		System.out.println();
	}
	
	public static void printMenu(String[] listMenu, String title) {
		String line = "+---------------------------------+";
		int number = 1;
		String formatTable = " %-2s. %-25s %n";
		
		System.out.printf("%-25s %n", title);
		System.out.println(line);
		
		for (String data : listMenu) {
			if (number < listMenu.length) {
				System.out.printf(formatTable, number, data);
			}else {
				System.out.printf(formatTable, 0, data);
			}
			number++;
		}
		System.out.println(line);
		System.out.println();
	}
	
	public static void printVechicle(List<Vehicle> listVehicle) {
		String formatTable = "| %-2s | %-15s | %-10s | %-15s | %-15s | %-5s | %-15s |%n";
		String line = "+----+-----------------+------------+-----------------+-----------------+-------+-----------------+%n";
		System.out.format(line);
	    System.out.format(formatTable, "No", "Vechicle Id", "Warna", "Brand", "Transmisi", "Tahun", "Tipe Kendaraan");
	    System.out.format(line);
	    int number = 1;
	    String vehicleType = "";
	    for (Vehicle vehicle : listVehicle) {
	    	if (vehicle instanceof Car) {
				vehicleType = "Mobil";
			}else {
				vehicleType = "Motor";
			}
	    	System.out.format(formatTable, number, vehicle.getVehiclesId(), vehicle.getColor(), vehicle.getBrand(), vehicle.getTransmisionType(), vehicle.getYearRelease(), vehicleType);
	    	number++;
	    }
	    System.out.printf(line);
	}

	public static void printCustomer(Customer customer) {
		System.out.printf("Customer ID    : %-20s\n", customer.getCustomerId());
		System.out.printf("Nama           : %-20s\n", customer.getName());
		System.out.printf("Customer Status: %-20s\n", "Non Member");
		System.out.printf("Alamat         : %-20s\n", customer.getAddress());
		printVechicle(customer.getVehicles());
	}

	public static void printMember(Customer customer) {
		MemberCustomer member = (MemberCustomer)customer;
		System.out.printf("Customer ID    : %-20s\n", member.getCustomerId());
		System.out.printf("Nama           : %-20s\n", member.getName());
		System.out.printf("Customer Status: %-20s\n", "Member");
		System.out.printf("Alamat         : %-20s\n", member.getAddress());
		System.out.printf("Saldo          : %-20s\n", member.getSaldoCoin());
		printVechicle(customer.getVehicles());
	}

	public static void printService(List<ItemService> itemServices, String vehicleType){
		AtomicInteger num=new AtomicInteger(0);
		String formatTable = "| %-2s | %-15s | %-20s | %-15s | %-15s |%n";
		String line = "+----+-----------------+----------------------+-----------------+-----------------+%n";
		System.out.format(line);
	    System.out.format(formatTable, "No", "Service Id", "Nama Service", "Tipe Kendaraan", "Harga");
	    System.out.format(line);
		itemServices.stream()
					.filter(service -> service.getVehicleType().equals(vehicleType))
					.forEach(service ->{
						System.out.format(formatTable, num.incrementAndGet(), service.getServiceId(), service.getServiceName(), service.getVehicleType(), service.getPrice());
					});
	    System.out.format(line);
		System.out.printf("| %-2s | %-74s |%n", "0", "Kembali Ke Home Menu");
		System.out.format(line);

	}

	public static void showBookingOrder(Customer customer){
        List<BookingOrder> listAllBookingOrders = BookingOrderRepository.getAllBookingOrder();

        AtomicInteger num=new AtomicInteger(0);
		String formatTable = "| %-4s | %-20s | %-15s | %-15s | %-15s | %-15s | %-30s |%n";
		String line = "+------+----------------------+-----------------+-----------------+-----------------+-----------------+--------------------------------+%n";
		System.out.format(line);
		System.out.format(formatTable, "No.", "Booking ID", "Nama Customer", "Payment Method", "Total Service", "Total Payment", "List Service");
		System.out.format(line);
        // System.out.printf("| %-4s | %-15s | %-15s | %-15s | %-15s | %-15s | %-30s |\n",
        //         "No.", "Booking ID", "Nama Customer", "Payment Method", "Total Service", "Total Payment", "List Service");
        // System.out.println("+=============================================================================================+");
        listAllBookingOrders.stream()
            .filter(order -> order.getCustomer().equals(customer))
            .forEach(order ->{
				System.out.format(formatTable, num.incrementAndGet(), order.getBookingId(), order.getCustomer().getName(), order.getPaymentMethod(), order.getTotalServicePrice(), order.getTotalPayment(), order.getServicesList());
                // System.out.printf("| %-4s | %-15s | %-15s | %-15s | %-15s | %-15s | %-30s |\n",
                // num.incrementAndGet(), order.getBookingId(), order.getCustomer().getName(), order.getPaymentMethod(), order.getTotalServicePrice(), order.getTotalPayment(), order.getServicesList());
            });
		
		System.out.format(line);
            
        
    }
	
	//Silahkan Tambahkan function print sesuai dengan kebutuhan.
	
}
