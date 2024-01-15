package company;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import  company.ProductModelAssembler;
import company.ProductNotFoundException;
import company.ProductRepository;

// tag::constructor[]
@RestController
class ProductController {

	private final ProductRepository repository;

	private final ProductModelAssembler assembler;

	ProductController(ProductRepository repository, ProductModelAssembler assembler) {

		this.repository = repository;
		this.assembler = assembler;
	}
	// end::constructor[]

	// Aggregate root

	// tag::get-aggregate-root[]
	@GetMapping("/products")
	CollectionModel<EntityModel<Product>> all() {

		List<EntityModel<Product>> products = repository.findAll().stream() //
				.map(assembler::toModel) //
				.collect(Collectors.toList());

		return CollectionModel.of(products, linkTo(methodOn(ProductController.class).all()).withSelfRel());
	}
	// end::get-aggregate-root[]

	// tag::post[]
	@PostMapping("/products")
	ResponseEntity<?> newProduct(@RequestBody Product newProduct) {

		EntityModel<Product> entityModel = assembler.toModel(repository.save(newProduct));

		return ResponseEntity //
				.created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()) //
				.body(entityModel);
	}
	// end::post[]

	// Single item

	// tag::get-single-item[]
	@GetMapping("/products/{id}")
	EntityModel<Product> one(@PathVariable Long id) {

		Product product = repository.findById(id) //
				.orElseThrow(() -> new ProductNotFoundException(id));

		return assembler.toModel(product);
	}
	// end::get-single-item[]

	// tag::put[]
	@PutMapping("/products/{id}")
	Product replaceCar(@RequestBody Product newProduct, @PathVariable Long id) {

		return repository.findById(id)
				.map(product -> {
					product.setName(newProduct.getName());
					product.setPrice(newProduct.getPrice());
					product.setQuantity(newProduct.getQuantity());
					product.setAvailable(newProduct.getAvailable());
					product.setProductStatus(newProduct.getProductStatus());
					return repository.save(product);
				})
				.orElseGet(() -> {
					newProduct.setId(id);
					return repository.save(newProduct);
				});
	}
	// end::put[]

	// tag::delete[]
	@DeleteMapping("/products/{id}")
	ResponseEntity<?> deleteProduct(@PathVariable Long id) {

		repository.deleteById(id);

		return ResponseEntity.noContent().build();
	}
	// end::delete[]
}
