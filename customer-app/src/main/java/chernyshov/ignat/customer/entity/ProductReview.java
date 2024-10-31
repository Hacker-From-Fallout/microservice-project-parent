package chernyshov.ignat.customer.entity;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductReview {

	private UUID id;
	
	private int productId;
	
	private int rating;
	
	private String review;
}
