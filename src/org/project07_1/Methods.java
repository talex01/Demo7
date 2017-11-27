package org.project07_1;

import java.sql.*;
import java.util.Scanner;

class Methods {
    private static Statement makeConnection() throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.jdbc.Driver");
        Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/todo?useUnicode=yes&characterEncoding=UTF-8&autoReconnect=true&useSSL=false", "mysqluser", "");
        return conn.createStatement();
    }

    static void showTodo() throws SQLException, ClassNotFoundException {
        Statement statement = makeConnection();
        ResultSet data = statement.executeQuery("SELECT * FROM ToDoList");

        System.out.println("Все дела: ");
        while (data.next()) {
            // категории
            statement = makeConnection();
            ResultSet categories = statement.executeQuery("SELECT name FROM Categories WHERE id =" + data.getInt("category_id"));
            categories.next();
            // статус
            statement = makeConnection();
            ResultSet status = statement.executeQuery("SELECT name FROM Status WHERE id =" + data.getInt("status_id"));
            status.next();

            System.out.println("id: " + data.getInt("id") + " " + data.getString("short_description") + "\n\t" +
                    data.getString("long_description") +
                    "\n\tКатегория: " + categories.getString("name") +
                    "\n\tВремя старта: " + data.getString("start") +
                    "\n\tСрок: " + data.getString("end") +
                    "\n\tСтатус: " + status.getString("name"));
        }
    }

    static void createTodo() throws SQLException, ClassNotFoundException {
        System.out.println("Выберите категорию:\n");

        Statement statement = makeConnection();
        ResultSet resultSet = statement.executeQuery("SELECT * FROM Categories");
        while (resultSet.next()) {
            System.out.println(resultSet.getInt("id") + " - " + resultSet.getString("name"));
        }
        // выбор категории
        statement = makeConnection();
        resultSet = statement.executeQuery("SELECT MAX(id) AS id FROM Categories");
        resultSet.next();

        Scanner scanner = new Scanner(System.in);
        Scanner scannerInt = new Scanner(System.in);
        int category = scannerInt.nextInt();
        if (category > resultSet.getInt("id")) {
            System.out.println("Нет такой категории");
        } else {
            System.out.print("Введите краткое задание: ");
            String short_description = scanner.nextLine();
            System.out.print("Введите полное описание: ");
            String long_description = scanner.nextLine();
            System.out.print("Дата исполнения в формате ГГГГ-ММ-ДД: ");
            String end = scanner.next();

            statement = makeConnection();
            statement.execute("INSERT INTO ToDoList (short_description, long_description, category_id, end, status_id) VALUES ('"
                    + short_description + "', '" + long_description + "', " + category + ", '" + end + "', 2)");
            System.out.println("Дело добавлено");
        }
    }

    static void createCategory() throws SQLException, ClassNotFoundException {
        Statement statement = makeConnection();
        String name;
        System.out.println("Введите имя категории: ");
        Scanner scanner = new Scanner(System.in);
        if(scanner.hasNext()) {
            name = scanner.next();
            statement.execute("INSERT INTO Categories(name) VALUES ('" + name + "')");
            System.out.println("Категория создана");
        }
    }

    static void delTodoById() throws SQLException, ClassNotFoundException {
        Statement statement = makeConnection();
        ResultSet resultSet = statement.executeQuery("SELECT id as id FROM ToDoList ORDER BY id");
        System.out.println("Существующие id дел: ");
        while (resultSet.next()){
            System.out.println(resultSet.getInt("id"));
        }
        System.out.print("Введите номер: ");
        Scanner scanner = new Scanner(System.in);
        statement.executeUpdate("DELETE FROM ToDoList WHERE id = " + scanner.nextInt());
        System.out.println("Дело удалено");
    }

    static void delDoneTodo() throws SQLException, ClassNotFoundException {
        Statement statement = makeConnection();
        int result = statement.executeUpdate("DELETE FROM ToDoList WHERE status_id = 1");
        System.out.println("Удалено " + result + " дел");
    }

    static void delCategoryById() throws SQLException, ClassNotFoundException{
        Statement statement = makeConnection();
        System.out.println("Существующие категории: ");
        ResultSet resultSet = statement.executeQuery("SELECT * FROM Categories");
        while (resultSet.next()){
            System.out.println(resultSet.getInt("id") + " " + resultSet.getString("name"));
        }
        System.out.print("Введите id: ");
        Scanner scanner = new Scanner(System.in);
        statement.executeUpdate("DELETE FROM Categories WHERE id = " + scanner.nextInt());
        System.out.println("Категория удалена");
    }

    static void todoDone() throws SQLException, ClassNotFoundException {
        Statement statement = makeConnection();
        System.out.println("Существующие дела: ");
        ResultSet resultSet = statement.executeQuery("SELECT * FROM ToDoList");
        while (resultSet.next()){
            System.out.println(resultSet.getInt("id"));
        }
        System.out.print("Введите id дела, которое Вы хотите завершить: ");
        Scanner scanner = new Scanner(System.in);
        statement.executeUpdate("UPDATE ToDoList SET status_id = 1 WHERE id = " + scanner.nextInt());
        System.out.println("Выполнено");
    }
}
