import java.util.List;

import io.javalin.Javalin;
import pojo.BookPojo;
import service.BookService;
import service.BookServiceImpl;

public class BookCrud {

	public static void main(String[] args) {
		
		// create an object of service layer to call its methods in each endpoint method
		BookService bookService = new BookServiceImpl();
		
		// cors is enabled here so that any incomming request froma  different domain is accepted
		Javalin server = Javalin.create((config) -> config.enableCorsForAllOrigins()); // javalin creates the jetty server (the default ), we can change the internal server if we want to
		server.start(7474); // javlin starts the server at port 7474

		// our first get endpoint
		// can access this endpoint through postman
		
		// will not using this anymore
		// this is just a demo
		server.get("/hello", (ctx)->{
			System.out.println("hello endpoint called....");
			ctx.result("hello returned from the endpoint");
		});
		
		
		// let's create the other endpoints
		//CRUD
		
		// Endpoint to Read all books
		// we need a get endpoint
		// http://localhost:7474/books
		server.get("/books", (ctx)->{
			// here we contact service, service contacts dao 
			// dao fetches all the books and return it back here
			
			// allBooks contains all the books fetched from the DB
			List<BookPojo> allBooks = bookService.getAllBooks();
			
			//now put the books in the response body, it has to converted to json format, 
			// the ctx.json() will take care of the above 2 and sends back the response to the client/consumer
			ctx.json(allBooks);
			
		});
		
		// endpoint to Read a book
		// http://localhost:7474/books/3
		server.get("/books/{bid}", (ctx)->{
			// here we contact service, service contacts dao 
			// dao fetches the book and return it back here
			
			BookPojo returnedBook = bookService.getABook(Integer.parseInt(ctx.pathParam("bid")));
			
			//now put the books in the response body, it has to converted to json format, 
			// the ctx.json() will take care of the above 2 and sends back the response to the client/consumer
			ctx.json(returnedBook);
			
		});
		// endpoint to Delete a book- create a delete end point
		// http://localhost:7474/books/2
		// here 2 is a path param which is represented as {bid} in the endpoint
		server.delete("/books/{bid}", (ctx) ->{
			// here we retrieve the bid from the path/url
			String bookId = ctx.pathParam("bid");
			
			// convert String to int
			int bookIdInteger = Integer.parseInt(bookId);
			
			// now call the service layer and pass the book id to be deleted
			// it returns boolean, converting to string and sending it as a result back to the caller
			ctx.result(bookService.deleteBook(bookIdInteger)+"");
		});
		
		// endpoint to Post - insert a new book
		// http://localhost:7474/books with request body
		server.post("/books", (ctx) -> {
			// take out the incoming json data from the request body and put/copy it into a BookPojo
			// it is important to make sure that the incoming json 's properties matches the book pojo properties for the copying to be successful
			BookPojo newBookPojo = ctx.bodyAsClass(BookPojo.class);
			
			// next send the bookPojo object to the service and then to dao to insert into the database
			BookPojo returnBookPojo = bookService.addBook(newBookPojo);
			
			// this returns a bookpojo which has the new book id, send this back in the response body in json format
			ctx.json(returnBookPojo);
		});
		
		// Put
	}

}
