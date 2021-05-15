-- phpMyAdmin SQL Dump
-- version 5.0.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: May 15, 2021 at 05:57 AM
-- Server version: 10.4.11-MariaDB
-- PHP Version: 7.2.27

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `abc_orders`
--

-- --------------------------------------------------------

--
-- Table structure for table `abc_orders`
--

CREATE TABLE `abc_orders` (
  `id` bigint(20) NOT NULL,
  `date` varchar(255) DEFAULT NULL,
  `product_id` bigint(20) DEFAULT NULL,
  `status` varchar(255) DEFAULT NULL,
  `user_id` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `abc_orders`
--

INSERT INTO `abc_orders` (`id`, `date`, `product_id`, `status`, `user_id`) VALUES
(2, 'Wed May 12 09:40:41 IST 2021', 1, 'Pending', 2),
(3, 'Wed May 12 22:44:51 IST 2021', 1, 'Pending', 1),
(4, 'Thu May 13 07:26:55 IST 2021', 1, 'Pending', NULL),
(8, 'Thu May 13 07:32:36 IST 2021', 1, 'Pending', NULL),
(9, 'Thu May 13 08:04:45 IST 2021', 1, 'Pending', 2),
(10, 'Thu May 13 08:06:02 IST 2021', 1, 'Pending', 2);

-- --------------------------------------------------------

--
-- Table structure for table `product`
--

CREATE TABLE `product` (
  `id` int(11) NOT NULL,
  `date` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Indexes for dumped tables
--

--
-- Indexes for table `abc_orders`
--
ALTER TABLE `abc_orders`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `product`
--
ALTER TABLE `product`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `abc_orders`
--
ALTER TABLE `abc_orders`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=11;

--
-- AUTO_INCREMENT for table `product`
--
ALTER TABLE `product`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
