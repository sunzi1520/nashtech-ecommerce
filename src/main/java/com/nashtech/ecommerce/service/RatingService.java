package com.nashtech.ecommerce.service;

import com.nashtech.ecommerce.dto.RatingDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface RatingService {

    public List<RatingDto> getAll();

    public Page<RatingDto> getAll(Pageable pageable);

    public List<RatingDto> getByUserId(Long userId);

    public List<RatingDto> getByProductId(Long productId);

    public Page<RatingDto> getByProductId(Long productId, Pageable pageable);

    public RatingDto getById(Long userId, Long productId);

    public RatingDto saveRating(RatingDto ratingDto);

    public Boolean updateRating(RatingDto ratingDto);

    public Boolean deleteRating(Long userId, Long productId);

    public Boolean existsByProductIdAndUserId(Long productId, Long userId);

}
