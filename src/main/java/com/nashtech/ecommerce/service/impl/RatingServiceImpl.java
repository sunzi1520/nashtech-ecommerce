package com.nashtech.ecommerce.service.impl;

import com.nashtech.ecommerce.dto.RatingDto;
import com.nashtech.ecommerce.entity.Product;
import com.nashtech.ecommerce.entity.Rating;
import com.nashtech.ecommerce.entity.RatingId;
import com.nashtech.ecommerce.entity.User;
import com.nashtech.ecommerce.repository.ProductRepository;
import com.nashtech.ecommerce.repository.RatingRepository;
import com.nashtech.ecommerce.repository.UserRepository;
import com.nashtech.ecommerce.service.RatingService;
import com.nashtech.ecommerce.utils.RatingDtoToRatingConverter;
import com.nashtech.ecommerce.utils.RatingToRatingDtoConverter;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class RatingServiceImpl implements RatingService {

    @Autowired
    RatingRepository ratingRepository;

    @Autowired
    ProductRepository productRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    ModelMapper modelMapper;

    private Rating convertRatingDtoToRating(RatingDto ratingDto) {
        return modelMapper.typeMap(RatingDto.class, Rating.class)
                        .setConverter(new RatingDtoToRatingConverter())
                        .map(ratingDto);
    }

    private RatingDto convertRatingToRatingDto(Rating rating) {
        return modelMapper.typeMap(Rating.class, RatingDto.class)
                    .setConverter(new RatingToRatingDtoConverter())
                    .map(rating);
    }

    @Override
    public List<RatingDto> getAll() {
        List<Rating> ratings = ratingRepository.findAll();
        return ratings.stream()
                .map(this::convertRatingToRatingDto)
                .collect(Collectors.toList());
    }

    @Override
    public Page<RatingDto> getAll(Pageable pageable) {
        Page<Rating> ratings = ratingRepository.findAll(pageable);
        List<RatingDto> ratingDtos = ratings.stream()
                                        .map(this::convertRatingToRatingDto)
                                        .collect(Collectors.toList());
        return new PageImpl<>(ratingDtos);
    }

    @Override
    public List<RatingDto> getByUserId(Long userId) {
        List<Rating> ratings = ratingRepository.findAllByRatingId_UserId(userId);
        return ratings.stream()
                .map(this::convertRatingToRatingDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<RatingDto> getByProductId(Long productId) {
        List<Rating> ratings = ratingRepository.findAllByRatingId_ProductId(productId);
        return ratings.stream()
                .map(this::convertRatingToRatingDto)
                .collect(Collectors.toList());
    }

    @Override
    public Page<RatingDto> getByProductId(Long productId, Pageable pageable) {
        Page<Rating> ratings = ratingRepository.findAllByRatingId_ProductId(productId, pageable);
        List<RatingDto> ratingDtos = ratings.stream()
                                        .map(this::convertRatingToRatingDto)
                                        .collect(Collectors.toList());
        return new PageImpl<>(ratingDtos);
    }

    @Override
    public RatingDto getById(Long userId, Long productId) {
        Rating rating = ratingRepository.getById(new RatingId(userId, productId));
        return convertRatingToRatingDto(rating);
    }

    @Override
    @Transactional
    public RatingDto saveRating(RatingDto ratingDto) {
        Optional <Product> product = productRepository.findById(ratingDto.getProductId());
        if (!product.isPresent()) throw new RuntimeException("Product does not exists");
        Optional <User> user = userRepository.findById(ratingDto.getUserId());
        if (!user.isPresent()) throw new RuntimeException("User does not exists");
        Rating rating = convertRatingDtoToRating(ratingDto);
        rating.setProduct(product.get());
        rating.setUser(user.get());
        Rating savedRating = ratingRepository.save(rating);
        return convertRatingToRatingDto(savedRating);
    }

    @Override
    @Transactional
    public Boolean updateRating(RatingDto ratingDto) {
        Optional <Product> product = productRepository.findById(ratingDto.getProductId());
        if (!product.isPresent()) throw new RuntimeException("Product does not exists");
        Optional <User> user = userRepository.findById(ratingDto.getUserId());
        if (!user.isPresent()) throw new RuntimeException("User does not exists");
        Rating rating = ratingRepository.getById(new RatingId(ratingDto.getUserId(), ratingDto.getProductId()));
        rating.setPoint(ratingDto.getPoint());
        rating.setContent(ratingDto.getContent());
        ratingRepository.save(rating);
        return true;
    }

    @Override
    public Boolean deleteRating(Long userId, Long productId) {
        return null;
    }

    @Override
    public Boolean existsByProductIdAndUserId(Long productId, Long userId) {
        return ratingRepository.existsById(new RatingId(userId, productId));
    }
}
