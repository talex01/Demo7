CREATE DATABASE IF NOT EXISTS `todo` DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci;
USE `todo`;

-- --------------------------------------------------------

--
-- Структура таблицы `Categories`
--

CREATE TABLE `Categories` (
  `id` int(11) NOT NULL,
  `name` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Дамп данных таблицы `Categories`
--

INSERT INTO `Categories` (`id`, `name`) VALUES
  (1, 'General'),
  (2, 'Category 1'),
  (3, 'Category 2');

-- --------------------------------------------------------

--
-- Структура таблицы `Status`
--

CREATE TABLE `Status` (
  `id` int(11) NOT NULL,
  `name` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Дамп данных таблицы `Status`
--

INSERT INTO `Status` (`id`, `name`) VALUES
  (1, 'Выполнено'),
  (2, 'Не выполнено');

-- --------------------------------------------------------

--
-- Структура таблицы `ToDoList`
--

CREATE TABLE `ToDoList` (
  `id` int(11) NOT NULL,
  `short_description` varchar(255) NOT NULL,
  `long_description` text,
  `category_id` int(11) NOT NULL,
  `created` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `start` date DEFAULT NULL,
  `end` date DEFAULT NULL,
  `status_id` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Дамп данных таблицы `ToDoList`
--

INSERT INTO `ToDoList` (`id`, `short_description`, `long_description`, `category_id`, `created`, `start`, `end`, `status_id`) VALUES
  (6, 'wer', 'werwer', 1, '2017-03-20 12:26:55', NULL, '2017-05-05', 2);

--
-- Индексы сохранённых таблиц
--

--
-- Индексы таблицы `Categories`
--
ALTER TABLE `Categories`
  ADD PRIMARY KEY (`id`);

--
-- Индексы таблицы `Status`
--
ALTER TABLE `Status`
  ADD PRIMARY KEY (`id`);

--
-- Индексы таблицы `ToDoList`
--
ALTER TABLE `ToDoList`
  ADD PRIMARY KEY (`id`),
  ADD KEY `ToDoList_Categories_id_fk` (`category_id`);

--
-- AUTO_INCREMENT для сохранённых таблиц
--

--
-- AUTO_INCREMENT для таблицы `Categories`
--
ALTER TABLE `Categories`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;
--
-- AUTO_INCREMENT для таблицы `Status`
--
ALTER TABLE `Status`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;
--
-- AUTO_INCREMENT для таблицы `ToDoList`
--
ALTER TABLE `ToDoList`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=10;
--
-- Ограничения внешнего ключа сохраненных таблиц
--

--
-- Ограничения внешнего ключа таблицы `ToDoList`
--
ALTER TABLE `ToDoList`
  ADD CONSTRAINT `ToDoList_Categories_id_fk` FOREIGN KEY (`category_id`) REFERENCES `Categories` (`id`);

-- Привилегии для `mysqluser`@`localhost`
CREATE USER 'mysqluser'@'localhost' IDENTIFIED BY '';
GRANT USAGE ON *.* TO 'mysqluser'@'localhost';
GRANT ALL PRIVILEGES ON `todo`.* TO 'mysqluser'@'localhost' WITH GRANT OPTION;