import dao.EmployeeDAO;
import impl.EmployeeDAOImpl;
import models.City;
import models.Employee;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Application {
    public static void main(String[] args) throws SQLException {
        // Создаем переменные с данными для подключения к базе
        final String user = "postgres";
        final String password = "as55Jv.l";
        final String url = "jdbc:postgresql://localhost:5432/skypro";

        // Создаем соединение с базой с помощью Connection
        try (final Connection connection = DriverManager.getConnection(url, user, password);
             // Формируем запрос к базе с помощью PreparedStatement
             PreparedStatement statement = connection.prepareStatement("SELECT * FROM employee WHERE id = (?)")
        ) {
            // Подставляем значение вместо wildcard
            statement.setInt(1, 1);

            // Делаем запрос к базе и результат кладем в ResultSet
            final ResultSet resultSet = statement.executeQuery();

            // Методом next проверяем есть ли следующий элемент в resultSet
            // и одновременно переходим к нему, если таковой есть
            while (resultSet.next()) {

                // С помощью методов getInt и getString получаем данные из resultSet
                String firstName = "First name: " + resultSet.getString("first_name");
                String lastName = "Last name: " + resultSet.getString("last_name");
                String gender = "Gender: " + resultSet.getString("gender");
                String city = "City : " + resultSet.getString("city_id");

                // Выводим данные в консоль
                System.out.println(firstName);
                System.out.println(lastName);
                System.out.println(gender);
                System.out.println(city);
            }

            EmployeeDAO employeeDAO = new EmployeeDAOImpl(connection);
            City city = new City(3, "city_name");
            employeeDAO.create(new Employee("Misha", "Mishkin", "man", 48, city.getCity_id()));
            System.out.println(employeeDAO.readById(3));
            employeeDAO.create(new Employee("Vasya", "Vasilyev", "man", 25, city.getCity_id()));
            System.out.println(employeeDAO.readById(2));
            Employee employee1 = new Employee
                    (7, "Egor", "Egorov", "man", 35, city.getCity_id());
            Employee employee2 = new Employee
                    (8, "Igor", "Igorov", "man", 45, city.getCity_id());
            employeeDAO.create(employee1);
            System.out.println(employee1);
            employeeDAO.create(employee2);
            System.out.println(employee2);
            System.out.println(employeeDAO.readById(4));
            employeeDAO.deleteById(7);
            List<Employee> employeeList = new ArrayList<>();
            employeeList.forEach(System.out::println);
        }

    }

}


