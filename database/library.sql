-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Apr 13, 2024 at 05:40 PM
-- Server version: 10.4.32-MariaDB
-- PHP Version: 8.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `library`
--

-- --------------------------------------------------------

--
-- Table structure for table `books`
--

CREATE TABLE `books` (
  `id` int(11) NOT NULL,
  `title` varchar(255) DEFAULT NULL,
  `author` varchar(50) NOT NULL,
  `publisher` varchar(255) DEFAULT NULL,
  `category` varchar(255) DEFAULT NULL,
  `available` tinyint(1) DEFAULT NULL,
  `borrowed` varchar(50) DEFAULT NULL,
  `bookshelf` varchar(10) NOT NULL,
  `shelf` int(10) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `books`
--

INSERT INTO `books` (`id`, `title`, `author`, `publisher`, `category`, `available`, `borrowed`, `bookshelf`, `shelf`) VALUES
(2, 'Human PPP', 'fun', 'KK Rowling', 'Spooky', 1, NULL, 'C2', 3),
(4, 'fasd', '0', 'asd', 'adas', 0, NULL, '', 0),
(6, 'ad', '0', 'das', 'duck', 0, NULL, '', 0),
(8, 'sdfdssdf', 'sdffd', 'sdf', 'sdf', 1, NULL, 'sdf', 1),
(9, 'dasd', 'das', 'asd', 'asd', 1, NULL, 'asda2', 112),
(10, 'asdasda', 'asdas', 'asdasd', 'ewe', 1, NULL, 'asdasdfa', 11212),
(11, 'asdasdasd', 'asdfas', 'asdadaw', 'dasdas', 1, NULL, 'aedasda', 112),
(12, 'sdasd', 'sdas', 'asda', 'asda', 0, NULL, 'asdas', 123),
(13, 'asdasdsa', 'asdcasdasd', 'zcdfasdas', 'asdasd', 0, NULL, 'asdafas', 123),
(14, 'asdasdaasd', 'asdasdas', 'ascdasd', 'asdas', 0, NULL, 'asdasd', 12312312),
(15, 'dcadasasda', 'acdad', 'sdas', 'asdasd', 0, NULL, 'asdasdas', 12312),
(16, 'asdasmdklas', 'qweqw', 'asdasda', 'asdasd', 1, NULL, 'asdasd', 12312),
(17, 'asdasmdklas', 'qweqw', 'asdasda', 'asdasd', 1, NULL, 'asdasd', 1),
(18, 'faklfasjdl', 'dkasjpodkasp', 'fmansfmas', 'djkasd', 1, NULL, 'adska;lda', 12),
(19, 'dasdas', 'daas', 'sdas', 'sdad', 1, NULL, '123wd', 12),
(20, 'hello', 'das', 'asd', 'asda', 1, NULL, 'asda12', 12),
(21, 'hello', 'das', 'asd', 'asda', 1, NULL, 'asda12', 12),
(22, 'hello', 'das', 'asd', 'asda', 1, NULL, 'asda12', 12),
(23, 'hi', 'ai', 'df', 'df', 1, NULL, 'sdfsdf', 12),
(24, 'sdfs', 'fsd', 'sdfsd', 'sdfsd', 0, NULL, 'sdfsd', 12),
(25, 'zdvkfsd', 'dsfmsdn', 'sdf', 'sdf', 0, NULL, 'wef', 123),
(26, 'you llok fucking stupid', 'my dick', 'dis nutz', 'sece', 1, NULL, 'c2', 69);

-- --------------------------------------------------------

--
-- Table structure for table `users`
--

CREATE TABLE `users` (
  `id` int(11) NOT NULL,
  `role` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `username` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `fine` bigint(255) UNSIGNED NOT NULL DEFAULT 0
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `users`
--

INSERT INTO `users` (`id`, `role`, `name`, `email`, `username`, `password`, `fine`) VALUES
(6, 'Librarian', 'sdffsdf', '12312fmsdkfl', '101rakibulhasan', '12345', 1805),
(7, 'Library Staff', 'Abidur Rahman', 'abidur1098@gmail.com', 'abidurrahman', '12345', 246),
(8, 'Patron', 'Israt Jahan Choity', 'isratjahanchoityami@gmail.com', 'isratjahanchoity', '12345', 2000),
(9, 'Librarian', 'Hello World', '224312@gmail.com', 'pussy', '123143', 6299),
(10, 'Librarian', 'oopsie', 'adrqwe', 'fasddf', 'zxfas', 0),
(12, 'Librarian', 'qw', 'dfsfa', 'adad', 'adas', 0),
(13, 'Librarian', 'wdwedasdf', 'asdasd', 'asda', 'xscfsd', 0),
(14, 'Librarian', 'sdffsdf', '12312fmsdkfl', 'fsdfs', 'sdfsfsd', 0),
(423, 'Librarian', 'sdasa', 'zdfsa@gmail.com', '\\zxd\\d', 'dasdasd', 0);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `books`
--
ALTER TABLE `books`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `username` (`username`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `books`
--
ALTER TABLE `books`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=28;

--
-- AUTO_INCREMENT for table `users`
--
ALTER TABLE `users`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=424;

DELIMITER $$
--
-- Events
--
CREATE DEFINER=`root`@`localhost` EVENT `calculate_fines_daily` ON SCHEDULE EVERY 10 SECOND STARTS '2024-04-05 21:52:02' ON COMPLETION NOT PRESERVE ENABLE DO UPDATE users 
    SET fine = fine + 100 
    WHERE borrowed_date < DATE_SUB(NOW(), INTERVAL 10 SECOND)$$

DELIMITER ;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
