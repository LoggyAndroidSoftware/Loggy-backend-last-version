package upc.edu.LoggyAPI.product.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import upc.edu.LoggyAPI.product.model.Category;
import upc.edu.LoggyAPI.product.model.Product;
import upc.edu.LoggyAPI.product.repository.CategoryRepository;
import upc.edu.LoggyAPI.product.repository.ProductRepository;
import upc.edu.LoggyAPI.product.service.ProductCategoryService;
import upc.edu.LoggyAPI.utils.exception.ResourceNotFoundException;

import java.util.Set;

@Service
public class ProductCategoryServiceImpl implements ProductCategoryService {
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public Product addCategoryToProductById(Long productId, Long category_id) {
        existProductById(productId);
        existCategoryById(category_id);
        Product product = productRepository.findById(productId).get();
        Category category = categoryRepository.findById(category_id).get();
        existCategoryInProduct(category, product);
        product.getCategories().add(category);
        return productRepository.save(product);
    }

    @Override
    public Product quitCategoryToProductById(Long product_id, Long category_id) {
        existProductById(product_id);
        existCategoryById(category_id);
        Product product = productRepository.findById(product_id).get();
        Category category = categoryRepository.findById(category_id).get();
        dontExistCategoryInProduct(category, product);
        product.getCategories().remove(category);
        return productRepository.save(product);
    }

    @Override
    public Product quitAllCategories(Long product_id) {
        existProductById(product_id);
        Product product = productRepository.findById(product_id).get();
        dontExistsCategoriesInProduct(product);
        product.getCategories().clear();
        return productRepository.save(product);
    }

    @Override
    public Set<Category> getAllCategoriesToProductById(Long product_id) {
        existProductById(product_id);
        Product product = productRepository.findById(product_id).get();
        dontExistsCategoriesInProduct(product);
        return product.getCategories();
    }

    @Override
    public Set<Product> getAllProductsToCategoryById(Long category_id) {
        existCategoryById(category_id);
        Category category = categoryRepository.findById(category_id).get();
        Set<Product> products = productRepository.findByCategoriesContains(category);
        if(products.isEmpty()){
            throw new ResourceNotFoundException(String.format("Category with id %s not have products", category.getId()));
        }
        return products;
    }

    private void existProductById(Long productId){
        if(!productRepository.existsById(productId)){
            throw new ResourceNotFoundException(String.format("Product with id %s not found", productId));
        }
    }

    private void existCategoryById(Long categoryId){
        if(!categoryRepository.existsById(categoryId)){
            throw new ResourceNotFoundException(String.format("Category with id %s not found", categoryId));
        }
    }

    private void existCategoryInProduct(Category category, Product product){
        if(product.getCategories().contains(category)){
            throw new ResourceNotFoundException(String.format("Category with id %s already exists in product with id %s", category.getId(), product.getId()));
        }
    }
    private void dontExistCategoryInProduct(Category category, Product product){
        if(!product.getCategories().contains(category)){
            throw new ResourceNotFoundException(String.format("Category with id %s not exists in product with id %s", category.getId(), product.getId()));
        }
    }
    private void dontExistsCategoriesInProduct(Product product){
        if(product.getCategories().isEmpty()){
            throw new ResourceNotFoundException(String.format("Product with id %s not have categories", product.getId()));
        }
    }
    /*
    private void dontExistsProductsInCategory(Category category){
        if(category.getProducts().isEmpty()){
            throw new ResourceNotFoundException(String.format("Category with id %s not have products", category.getId()));
        }
    }

     */
}
