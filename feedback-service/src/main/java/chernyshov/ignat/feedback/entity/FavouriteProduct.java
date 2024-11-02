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
@Document("favourite_product")
public class FavouriteProduct {
	
	@Id
	private UUID id;
	
	private int productId;
	
	private String userId;
}
