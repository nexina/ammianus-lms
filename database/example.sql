CREATE DATABASE library;

USE library;

CREATE TABLE books (
  id int(11) NOT NULL AUTO_INCREMENT,
  title varchar(255) DEFAULT NULL,
  author varchar(50) NOT NULL,
  publisher varchar(255) DEFAULT NULL,
  category varchar(255) DEFAULT NULL,
  available tinyint(1) DEFAULT NULL,
  borrowed varchar(50) DEFAULT NULL,
  bookshelf varchar(30) NOT NULL,
  shelf int(10) NOT NULL,
  PRIMARY KEY (id)
);

INSERT INTO books (id, title, author, publisher, category, available, borrowed, bookshelf, shelf) VALUES
(1, 'The Great Gatsby', 'F. Scott Fitzgerald', 'Scribner', 'Fiction', 1, NULL, 'A', 1),
(2, 'The Catcher in the Rye', 'J.D. Salinger', 'Little, Brown and Company', 'Fiction', 1, NULL, 'A', 2),
(3, 'To Kill a Mockingbird', 'Harper Lee', 'J.B. Lippincott & Co.', 'Fiction', 1, NULL, 'A', 3),
(4, '1984', 'George Orwell', 'Secker & Warburg', 'Fiction', 1, NULL, 'A', 4),
(5, 'Animal Farm', 'George Orwell', 'Secker & Warburg', 'Fiction', 1, NULL, 'A', 5),
(6, 'Brave New World', 'Aldous Huxley', 'Chatto & Windus', 'Fiction', 1, NULL, 'A', 6),
(7, 'The Lord of the Rings', 'J.R.R. Tolkien', 'Allen & Unwin', 'Fiction', 1, NULL, 'A', 7),
(8, 'The Hobbit', 'J.R.R. Tolkien', 'Allen & Unwin', 'Fiction', 1, NULL, 'A', 8),
(9, 'The Silmarillion', 'J.R.R. Tolkien', 'Allen & Unwin', 'Fiction', 1, NULL, 'A', 9),
(10, 'The Da Vinci Code', 'Dan Brown', 'Doubleday', 'Fiction', 1, NULL, 'A', 10),
(11, 'Angels & Demons', 'Dan Brown', 'Pocket Books', 'Fiction', 1, NULL, 'A', 11),
(12, 'Inferno', 'Dan Brown', 'Doubleday', 'Fiction', 1, NULL, 'A', 12),
(13, 'The Lost Symbol', 'Dan Brown', 'Doubleday', 'Fiction', 1, NULL, 'A', 13),
(14, 'Digital Fortress', 'Dan Brown', 'St. Martin''s Press', 'Fiction', 1, NULL, 'A', 14);

CREATE TABLE users (
  id int(11) NOT NULL AUTO_INCREMENT,
  role varchar(255) DEFAULT NULL,
  name varchar(255) DEFAULT NULL,
  email varchar(255) DEFAULT NULL,
  username varchar(255) DEFAULT NULL,
  password varchar(255) DEFAULT NULL,
  fine bigint(255) UNSIGNED NOT NULL DEFAULT 0,
  PRIMARY KEY (id),
  UNIQUE KEY (username)
);

INSERT INTO `users` (`id`, `role`, `name`, `email`, `username`, `password`, `fine`) VALUES
(1, 'Librarian', 'Admin', 'admin@admin.com', 'adminl', 'adminl', 0),
(2, 'Library Staff', 'Admin', 'admin@admin.com', 'adminls', 'adminls', 0),
(3, 'Patron', 'Admin', 'admin@admin.com', 'adminp', 'adminp', 0);
