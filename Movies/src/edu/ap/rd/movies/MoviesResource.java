package edu.ap.rd.movies;


import java.util.ArrayList;

import javax.ws.rs.*;

import redis.clients.jedis.Jedis;

@Path("/movies")
public class MoviesResource {

	private String url = "http://www.omdbapi.com/?t=";
	private String apiKey = "&apikey=plzBanMe";
	
	//@GET
	//@Produces({ "text/html" })
	//public String getAllMovies() {
		// Uncomment voor opvullen
		//this.fillDb(true);

		//Jedis jedis = JedisConnection.getInstance().getConnection();
		//StringBuilder builder = new StringBuilder();
		//String htmlString = "<html><body>";
		
		//htmlString +="<title>Movie</title>";
  	//htmlString +="</head>";

		//htmlString +="<body>";
		//htmlString +="<ul>";
		//ArrayList<String> movieList = new ArrayList<String>();
		//
		//for (String key : jedis.keys("movie:*")) {
		//	movieList.add(jedis.get(key));
		//}
		//for(String movie: movieList){
		//	//System.out.println(author);
		//	String[] full_name = movie.split(" ");
		//	
		//	
		//	if(full_name.length == 1){
		//		htmlString +="<li><a href=/Movies/movies/"+ full_name[0]+"/movies>"+movie+"</a></li>";
		//		
		//	}else{
		//		htmlString +="<li><a href=/Movies/movies/"+ full_name[0]+"%20"+full_name[1]+"/movies>"+movie+"</a></li>";
		//		
		//	}
		//	
		//	
		//}
		//htmlString +="<br><a href=/Movies/movies/allmovies>All movies</a>";
		//htmlString +="</ul>";
		//htmlString +="</body>";
		//htmlString +="</html>";
//
		//return htmlString;	
	//}
	
	@GET
	@Path("{name}")
	@Produces({ "text/html" })
	public String getMovie(@PathParam("name") String movie_name) {
		
		// Uncomment voor opvullen
		this.fillDb(true);

		Jedis jedis = JedisConnection.getInstance().getConnection();
		StringBuilder builder = new StringBuilder();
		
		builder.append("<html>");
		builder.append("<head>");
		builder.append("<title>Movie</title>");
		builder.append("</head>");

		builder.append("<body>");
		builder.append("<ul>");
		if (jedis.get(movie_name) != null) {
		ArrayList<String> moviesList = new ArrayList<String>();
		for (String key : jedis.keys("movie:*")) {		
			String movie = jedis.get(key);
			if(movie.equals(movie_name)){
				
				String id = key.split(":")[1];
				for (String mov : jedis.smembers("movies:" + id)) {
					moviesList.add(mov);
				}
				
			}
		}
		
		
		for(String movie: moviesList){
			
			builder.append("<li>"+movie+"</li>");
			
		}
		}
		else {
			movie_name.toLowerCase();
			url += movie_name + apiKey;
		}
		builder.append("<br><br><a href=/Movies/movies/>Home</a>");
				
		builder.append("</body>");
		builder.append("</html>");

		return builder.toString();
		
		
}
	
	@GET
	@Path("allmovies")
	@Produces({ "text/html" })
	public String getAllMovie() {
		// Uncomment voor opvullen
		this.fillDb(true);
		
		Jedis jedis = JedisConnection.getInstance().getConnection();
		StringBuilder builder = new StringBuilder();
		builder.append("<html>");
		builder.append("<head>");
		builder.append("<title>Movies</title>");
		builder.append("</head>");

		builder.append("<body>");
		builder.append("<ul>");
		for (String key : jedis.keys("movies:*")) {
			for (String movie : jedis.smembers(key)) {
				builder.append("<li>" + movie);
			}
		}
		builder.append("</ul>");
		builder.append("<br><br><a href=/Movies/movies/>Home</a>");
		builder.append("</body>");
		builder.append("</html>");

		return builder.toString();
	}

/*
	@GET
	@Produces({ "text/html" })
	public String getAllQuotes() {
		// Uncomment voor opvullen
		this.fillDb(true);

		Jedis jedis = JedisConnection.getInstance().getConnection();
		StringBuilder builder = new StringBuilder();

		builder.append("<html>");
		builder.append("<head>");
		builder.append("<title>Quotes</title>");
		builder.append("</head>");

		builder.append("<body>");
		builder.append("<ul>");
		for (String key : jedis.keys("quotes:*")) {
			for (String quote : jedis.smembers(key)) {
				builder.append("<li>" + quote);
			}
		}
		builder.append("</ul>");
		builder.append("</body>");
		builder.append("</html>");

		return builder.toString();
	}

/*
	// Specifieke auteur
	// Data => de data die wordt gepost
	@POST
	public String getAuthorQuotes(String data) {
		// Uncomment voor opvullen
		//this.fillDb(true);

		Jedis jedis = JedisConnection.getInstance().getConnection();
		StringBuilder builder = new StringBuilder();

		builder.append("<html>");
		builder.append("<head>");
		builder.append("<title>Quotes</title>");
		builder.append("</head>");

		builder.append("<body>");
		// Selecteren van correcte author
		for (String author : jedis.keys("author:*")) {
			String tmpAuthor = jedis.get(author);
			if (tmpAuthor.equals(data)) {
				builder.append("testink");
				int authorId = Integer.parseInt(author.split(":")[1]);

				builder.append("<h1>" + tmpAuthor + "</h1>");
				builder.append("<ul>");
				for (String quote : jedis.smembers("quotes:" + authorId)) {
					builder.append("<li>" + quote);
				}
				builder.append("</ul>");

				break; 
			}
		}

		builder.append("</body>");
		builder.append("</html>");

		return builder.toString();
	}



	*/
	private void fillDb(boolean flush) {
		Jedis jedis = JedisConnection.getInstance().getConnection();

		if (flush) {
			jedis.flushDB();
		}
		//jedis.set("The Big Lebowski", "{'Title':'The Big Lebowski','Year':'1998','Rated':'R','Released':'6 Mar 1998','Runtime':'117 min','Genre':'Comedy, Crime','Director':'Joel Coen, Ethan Coen','Writer':'Ethan Coen, Joel Coen','Actors':'Jeff Bridges, John Goodman, Julianne Moore, Steve Buscemi','Plot':'The Dude Lebowski, mistaken for a millionaire Lebowski, seeks restitution for his ruined rug and enlists his bowling buddies to help get it.','Language':'English, German, Hebrew, Spanish','Country':'USA, UK','Awards':'4 wins & 14 nominations.','Poster':'http://ia.media-imdb.com/images/M/MV5BMTQ0NjUzMDMyOF5BMl5BanBnXkFtZTgwODA1OTU0MDE@._V1_SX300.jpg','Metascore':'69','imdbRating':'8.2','imdbVotes':'453365','imdbID':'tt0118715','Type':'movie','Response':'True'}");
		jedis.set("movie:1", "The Big Lebowski");
		jedis.sadd("movies:1", "{'Title':'The Big Lebowski','Year':'1998','Rated':'R','Released':'6 Mar 1998','Runtime':'117 min','Genre':'Comedy, Crime','Director':'Joel Coen, Ethan Coen','Writer':'Ethan Coen, Joel Coen','Actors':'Jeff Bridges, John Goodman, Julianne Moore, Steve Buscemi','Plot':'The Dude Lebowski, mistaken for a millionaire Lebowski, seeks restitution for his ruined rug and enlists his bowling buddies to help get it.','Language':'English, German, Hebrew, Spanish','Country':'USA, UK','Awards':'4 wins & 14 nominations.','Poster':'http://ia.media-imdb.com/images/M/MV5BMTQ0NjUzMDMyOF5BMl5BanBnXkFtZTgwODA1OTU0MDE@._V1_SX300.jpg','Metascore':'69','imdbRating':'8.2','imdbVotes':'453365','imdbID':'tt0118715','Type':'movie','Response':'True'}");
		jedis.set("movie:2", "Deadpool");
		jedis.sadd("movies:2", "{'Title':'Deadpool','Year':'2016','Rated':'R','Released':'12 Feb 2016','Runtime':'117 min','Genre':'Comedy, Crime','Director':'Joel Coen, Ethan Coen','Writer':'Ethan Coen, Joel Coen','Actors':'Jeff Bridges, John Goodman, Julianne Moore, Steve Buscemi','Plot':'The Dude Lebowski, mistaken for a millionaire Lebowski, seeks restitution for his ruined rug and enlists his bowling buddies to help get it.','Language':'English, German, Hebrew, Spanish','Country':'USA, UK','Awards':'4 wins & 14 nominations.','Poster':'http://ia.media-imdb.com/images/M/MV5BMTQ0NjUzMDMyOF5BMl5BanBnXkFtZTgwODA1OTU0MDE@._V1_SX300.jpg','Metascore':'69','imdbRating':'8.2','imdbVotes':'453365','imdbID':'tt0118715','Type':'movie','Response':'True'}");

		//jedis.set("movie:1", "Titanic");
		//jedis.set('movie:2', '{"name": "Tranformers", "info": "Rare robots die tegen elkaar vechten en soms veranderen in autos. Jaar: 2007 Sia, Megan"}');
		//jedis.set('movie:3', '{"name": "The Shawshank Redemption", "info": "Man wordt onterecht in een gevangenis geplaatst en ontmoet morgan freeman. Jaar: 1993, Morgan Freeman"}');
		//jedis.set('movie:4', '{"name": "Excuses", "info": "Deze film is niet beschikbaar!"}');
		
		//jedis.set("movie:1", "Einstein");
		//jedis.set("movie:2", "Stephen Hawking");
		//jedis.set("movie:3", "Mister Popo");

		//jedis.sadd("movies:1", "'{\"name\": \"Titanic\", \"info\": \"Herbeleef het zinken van het meest onzinkbare schip ooit! Kinderen toegelaten! Jaar: 2001, Leonardo and Kate\"}'");
		//jedis.sadd("quotes:1", "If you want to live a happy life, tie it to a goal, not to people or objects");
		//jedis.sadd("quotes:2", "People who boast about their IQ are losers");
		//jedis.sadd("quotes:3", "IL testa");
	}

}
