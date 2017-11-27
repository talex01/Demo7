package org.project07_1;

import java.sql.*;
import java.util.*;

import static org.project07_1.Methods.*;

public class App {
    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println(
                    "\n1. Показать все дела\n" +
                    "2. Добавить дело\n" +
                    "3. Выполнить дело\n" +
                    "4. Добавить категорию\n" +
                    "5. Удалить дело по id\n" +
                    "6. Удалить завершенные дела\n" +
                    "7. Удалить категорию по id\n" +
                    "8. Выход\n" +
                    "Ваш выбор => ");
            if (scanner.hasNextInt()) {
                switch (scanner.nextInt()) {
                    case 1:
                        showTodo();
                        break;
                    case 2:
                        createTodo();
                        break;
                    case 3:
                        todoDone();
                        break;
                    case 4:
                        createCategory();
                        break;
                    case 5:
                        delTodoById();
                        break;
                    case 6:
                        delDoneTodo();
                        break;
                    case 7:
                        delCategoryById();
                    case 8:
                        System.exit(0);
                        break;
                }
            }
        }
    }
}
