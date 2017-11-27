-- База данных: `sonnets`
--
CREATE DATABASE IF NOT EXISTS `sonnets` DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci;
USE `sonnets`;

-- --------------------------------------------------------

--
-- Структура таблицы `sonnets`
--

CREATE TABLE `sonnets` (
  `id` int(11) NOT NULL,
  `path` text NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Дамп данных таблицы `sonnets`
--

INSERT INTO `sonnets` (`id`, `path`) VALUES
  (1, 'sonnets/1.txt'),
  (2, 'sonnets/2.txt'),
  (3, 'sonnets/3.txt'),
  (4, 'sonnets/4.txt'),
  (5, 'sonnets/5.txt'),
  (6, 'sonnets/6.txt'),
  (7, 'sonnets/7.txt'),
  (8, 'sonnets/8.txt'),
  (9, 'sonnets/9.txt'),
  (10, 'sonnets/10.txt'),
  (11, 'sonnets/11.txt'),
  (12, 'sonnets/12.txt'),
  (13, 'sonnets/13.txt'),
  (14, 'sonnets/14.txt'),
  (15, 'sonnets/15.txt'),
  (16, 'sonnets/16.txt'),
  (17, 'sonnets/17.txt'),
  (18, 'sonnets/18.txt'),
  (19, 'sonnets/19.txt'),
  (20, 'sonnets/20.txt'),
  (21, 'sonnets/21.txt'),
  (22, 'sonnets/22.txt'),
  (23, 'sonnets/23.txt'),
  (24, 'sonnets/24.txt'),
  (25, 'sonnets/25.txt'),
  (26, 'sonnets/26.txt'),
  (27, 'sonnets/27.txt'),
  (28, 'sonnets/28.txt'),
  (29, 'sonnets/29.txt'),
  (30, 'sonnets/30.txt');

--
-- Индексы сохранённых таблиц
--

--
-- Индексы таблицы `sonnets`
--
ALTER TABLE `sonnets`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT для сохранённых таблиц
--

--
-- AUTO_INCREMENT для таблицы `sonnets`
--
ALTER TABLE `sonnets`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=31;

-- Привилегии для `mysqluser`@`localhost`
CREATE USER 'mysqluser'@'localhost' IDENTIFIED BY '';
GRANT USAGE ON *.* TO 'mysqluser'@'localhost';
GRANT ALL PRIVILEGES ON `sonnets`.* TO 'mysqluser'@'localhost' WITH GRANT OPTION;