package chernyshov.ignat.feedback.entity;

import java.util.UUID;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document("product_review")
public class ProductReview {

	@Id
	private UUID id;
	
	private int productId;
	
	private int rating;
	
	private String review;
}
