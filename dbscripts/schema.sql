CREATE TABLE book_details(
							book_id INT PRIMARY KEY GENERATED ALWAYS AS IDENTITY, 
							book_title VARCHAR(50), 
							book_author VARCHAR(50), 
							book_genre VARCHAR(20), 
							book_cost INT, 
							book_image_url varchar(255));