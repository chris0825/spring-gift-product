package gift.product;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/product")
public class ProductController {

    private final Map<Long, ProductVo> products = new HashMap<>();
    private final AtomicLong idCounter = new AtomicLong();

    @PostMapping("/add")
    public void addProduct(@RequestParam("name") String name, @RequestParam("price") int price, @RequestParam("imageUrl") String imageUrl) {
        ProductVo product = new ProductVo();

        product.setName(name);
        product.setPrice(price);
        product.setImageUrl(imageUrl);

        products.put(idCounter.incrementAndGet(), product);
    }

    @PostMapping("/modify")
    public void modifyProduct(@RequestParam("id") Long id, @RequestParam(value="name", defaultValue="NULL") String name, @RequestParam(value="price", defaultValue="-1") int price, @RequestParam(value="imageUrl", defaultValue="NULL") String imageUrl) {
        ProductVo product = products.get(id);
        if(!name.equals("NULL"))
            product.setName(name);
        if(price != -1)
            product.setPrice(price);
        if(!imageUrl.equals("NULL"))
            product.setImageUrl(imageUrl);
        products.put(id, product);
    }

    @GetMapping("/select")
    public ProductVo selectProduct(@RequestParam("id") Long id) {
        if(products.get(id) != null)
            return products.get(id);
        return new ProductVo();
    }

    public void deleteProduct(Long id) {
        products.remove(id);
    }
}
