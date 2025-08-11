package entities;

import Partner.Shareable;
import constants.BookGenre;


import java.util.Arrays;

import org.apache.commons.lang3.StringUtils;

public class Book extends Bookmark implements Shareable {
    private int publicationYear;
    private String publisher;
    private String[] author;
    private BookGenre genre;
    private double amazonRating;
    private String imageUrl;

    public int getPublicationYear() {
        return publicationYear;
    }

    public void setPublicationYear(int publicationYear) {
        this.publicationYear = publicationYear;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String[] getAuthor() {
        return author;
    }

    public void setAuthor(String[] author) {
        this.author = author;
    }

    public BookGenre getGenre() {
        return genre;
    }

    public void setGenre(BookGenre genre) {
        this.genre = genre;
    }

    public double getAmazonRating() {
        return amazonRating;
    }

    public void setAmazonRating(double amazonRating) {
        this.amazonRating = amazonRating;
    }
    public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

    @Override
    public String toString() {
        return "Book{" +
                "publicationYear=" + publicationYear +
                ", publisher='" + publisher + '\'' +
                ", author=" + Arrays.toString(author) +
                ", genre='" + genre + '\'' +
                ", amazonRating =" + amazonRating +
                ", imageUrl ="+ imageUrl +
                '}';
    }

    @Override
    public boolean isKidFriendlyEligible() {

        if(genre== BookGenre.PHILOSOPHY||genre==BookGenre.SELF_HELP){
            return false;
        }
        return true;
    }

    @Override
    public String getItemData() {

        StringBuilder builder = new StringBuilder();
        builder.append("<item>");
            builder.append("<type>Book</type>");
            builder.append("<title>").append(getTitle()).append("</title>");//title is present in superclass bookmark
            builder.append("<author>").append(StringUtils.join(author,",")).append("</author>");
            builder.append("<publisher>").append(publisher).append("</publisher>");
             builder.append("<publicationYear>").append(publicationYear).append("</publicationYear>");
              builder.append("<genre>").append(genre).append("</genre>");
            builder.append("<amazonRating>").append(amazonRating).append("</amazonRating>");
             builder.append("</item");

             return builder.toString();
    }

	
}
