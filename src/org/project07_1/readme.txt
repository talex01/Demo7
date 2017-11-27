---------
Задание 1
---------

Разработать базу данных в mysql для ведения списка дел (ToDo)
В базе данных должны находиться таблицы:
	ToDoList с полями:
		id (int),
                short_description (varchar(255)),
                long_description (text),
                category_id (foreign key),
		created (timestamp),
                start (date),
	        end (date),
                status_id (int)
        Categories с полями:
                id (int),
                name (varchar(255))
	Status
                id (int)
                name (varchar(255))

Разработать приложение на java для ведения списка дел.
В программе предусмотреть возможности:
    1. Вывод всех записей таблицы ToDoList
    2. Добавление новой записи в таблицы ToDoList и Categories
    3. Удаление записей из таблиц ToDoList и Categories по id, а из ToDoList и по завершенности

Реализация:
-----------
Для работы необходимо подключение библиотеки mysql-connector-java-5.1.41-bin.jar
Методы реализованы в классе Methods
