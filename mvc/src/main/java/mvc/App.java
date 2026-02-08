package mvc;
import java.sql.*;

import mvc.controller.Controller;

public class App {
    public static void main(String[] args) {
       Controller contro = new Controller();
       contro.start();
    }
}