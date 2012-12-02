/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import javax.xml.ws.Endpoint;
import library.webservice.impl.BookWebServiceImpl;
import library.webservice.impl.UserWebServiceImpl;
 
public class Server {
    public static void main(String[] args) throws InterruptedException {
        String address = "http://localhost:9000/bookWebService";
        Endpoint.publish(address, new BookWebServiceImpl());
        address = "http://localhost:9000/userWebService";
        Endpoint.publish(address, new UserWebServiceImpl());
        System.out.println("Server ready...");
    }
}
