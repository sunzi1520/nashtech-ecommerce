package com.nashtech.ecommerce.repository;

import com.nashtech.ecommerce.entity.Rating;
import com.nashtech.ecommerce.entity.RatingId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RatingRepository extends JpaRepository<Rating, RatingId> {

    Page<Rating> findAll(Pageable pageable);

    Page<Rating> findAllByRatingId_ProductId(Long productId, Pageable pageable);

    List<Rating> findAllByRatingId_ProductId(Long productId);

    List<Rating> findAllByRatingId_UserId(Long userId);

}
