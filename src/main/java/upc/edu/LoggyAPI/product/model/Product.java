package upc.edu.LoggyAPI.product.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import upc.edu.LoggyAPI.brand.model.Brand;

import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "product_codename", length = 20, nullable = false, unique = true)
    private String codename;
    @Column(name = "product_image", nullable = false)
    private String image;

    @ManyToOne
    @JoinColumn(name = "product_brand_id")
    Brand brand;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "product_description",
            joinColumns = @JoinColumn(
                    name = "product_id",
                    referencedColumnName = "id"
            ),
            inverseJoinColumns = @JoinColumn(
                    name = "description_id",
                    referencedColumnName = "id"
            )
    )
    private Set<Description> descriptions;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "product_category",
            joinColumns = @JoinColumn(
                    name = "product_id",
                    referencedColumnName = "id"
            ),
            inverseJoinColumns = @JoinColumn(
                    name = "category_id",
                    referencedColumnName = "id"
            )
    )
    private Set<Category> categories;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "product_measurement",
            joinColumns = @JoinColumn(
                    name = "product_id",
                    referencedColumnName = "id"
            ),
            inverseJoinColumns = @JoinColumn(
                    name = "measurement_id",
                    referencedColumnName = "id"
            )
    )
    private Set<Measurement> measurements;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "product_specification",
            joinColumns = @JoinColumn(
                    name = "product_id",
                    referencedColumnName = "id"
            ),
            inverseJoinColumns = @JoinColumn(
                    name = "specification_id",
                    referencedColumnName = "id"
            )
    )

    private Set<Specification> specifications;
}
