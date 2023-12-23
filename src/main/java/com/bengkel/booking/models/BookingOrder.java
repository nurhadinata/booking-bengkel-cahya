package com.bengkel.booking.models;

import java.security.Provider.Service;
import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import com.bengkel.booking.interfaces.IBengkelPayment;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookingOrder implements IBengkelPayment{
	private static final AtomicInteger count = new AtomicInteger(1);
	private String bookingId;
	private Customer customer;
	private List<ItemService> services;
	private String paymentMethod;
	private double totalServicePrice;
	private double totalPayment;

	public BookingOrder(Customer customer, Vehicle vehicle, List<ItemService> services, String paymentMethod) {
        String id = "";
        if(count.get()<10){
            id = "Book-"+customer.getCustomerId()+"-00"+count.getAndIncrement();
        }else if(count.get()<100){
			id = "Book-"+customer.getCustomerId()+"-0"+count.getAndIncrement();
		}else{
            id = "Book-"+customer.getCustomerId()+"-"+count.getAndIncrement();
        }
        this.bookingId = id;
        this.customer = customer;
        this.services = services;
		this.paymentMethod = paymentMethod;
        this.totalServicePrice = calculateServicePrice(services);
		calculatePayment();
    };

	public double calculateServicePrice(List<ItemService> services) {
		double price = 0;

		for (ItemService service : this.services) {
            price += service.getPrice();
        }
        
        return price;
	}	

	public String getServicesList(){
        String servicesList = "";
        for (ItemService service : this.services) {
            if(servicesList.equals("")){
                servicesList = service.getServiceName();
            }else{
                servicesList = servicesList +", "+ service.getServiceName();
            }
            
        }
        return servicesList;
    }
	
	@Override
	public void calculatePayment() {
		double discount = 0;
		if (paymentMethod.equalsIgnoreCase("Saldo Coin")) {
			discount = getTotalServicePrice() * RATES_DISCOUNT_SALDO_COIN;
		}else {
			discount = getTotalServicePrice() * RATES_DISCOUNT_CASH;
		}
		
		setTotalPayment(getTotalServicePrice() - discount);
	}

	
}
