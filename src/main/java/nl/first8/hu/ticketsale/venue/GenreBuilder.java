package nl.first8.hu.ticketsale.venue;

public class GenreBuilder {
	public static Genre build(String genre) {
		Genre object;

		switch (genre) {
		case "ROCK":
			object = Genre.ROCK;
			break;
		case "EDM":
			object = Genre.EDM;
			break;
		case "DANCE":
			object = Genre.DANCE;
			break;
		case "Grindcore":
			object = Genre.Grindcore;
			break;
		case "metal":
			object = Genre.metal;
			break;
		case "electronica":
			object = Genre.electronica;
			break;
		default:
			object = Genre.DANCE;
			break;
		}

		return object;
	}
}
