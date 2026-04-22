package com.tushar.order_management_system.service;

import com.tushar.order_management_system.dto.ProductDto;
import com.tushar.order_management_system.entity.Product;
import com.tushar.order_management_system.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final ModelMapper modelMapper;

    public ProductDto createProduct(ProductDto productDto){
        Product product = modelMapper.map(productDto, Product.class);
        Product saved = productRepository.save(product);
        return modelMapper.map(saved,ProductDto.class);
    }

    public List<ProductDto> getAllProducts(){
        List<Product> products = productRepository.findAll();
        return products
                .stream()
                .map((product) -> modelMapper.map(product, ProductDto.class))
                .toList();
    }

    public ProductDto findProductById(Long id){
       Product product = productRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Product Not Found with ID : " +id));
       return modelMapper.map(product,ProductDto.class);
    }

    public void deleteProduct(Long id) {
        if (!productRepository.existsById(id)){
            throw new IllegalArgumentException("Student Does not exist by id: " +id);
        }
        productRepository.deleteById(id);
    }
}
