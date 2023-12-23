package com.bengkel.booking.repositories;

import java.util.ArrayList;
import java.util.List;

import com.bengkel.booking.models.BookingOrder;
import com.bengkel.booking.models.Customer;
import com.bengkel.booking.models.ItemService;
import com.bengkel.booking.models.MemberCustomer;
import com.bengkel.booking.models.Vehicle;
import com.bengkel.booking.services.Validation;

public class BookingOrderRepository {
    static List<BookingOrder> listBookingOrders = new ArrayList<BookingOrder>();
    int existingID = 0;
    public static void addBookingOrder(Customer customer, Vehicle vehicle, List<ItemService> services, String paymentMethod){
        BookingOrder bookingOrder = new BookingOrder(customer, vehicle, services, paymentMethod);
        if(Validation.isMember(customer)&& paymentMethod.equals("Saldo Coin")){
            MemberCustomer member = (MemberCustomer)customer;
            member.setSaldoCoin(member.getSaldoCoin()-bookingOrder.getTotalPayment());
        }
        listBookingOrders.add(bookingOrder);
    }

    public static List<BookingOrder> getAllBookingOrder(){
        return listBookingOrders;
    }
}
