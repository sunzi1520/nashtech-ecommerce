package com.nashtech.ecommerce.restcontroller;

import com.nashtech.ecommerce.dto.MessageResponse;
import com.nashtech.ecommerce.dto.RatingDto;
import com.nashtech.ecommerce.service.ProductService;
import com.nashtech.ecommerce.service.RatingService;
import com.nashtech.ecommerce.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/v1/ratings")
public class RatingController {

    @Autowired
    RatingService ratingService;

    @Autowired
    UserService userService;

    @Autowired
    ProductService productService;

    @GetMapping
    public ResponseEntity<?> getAll(@RequestParam(name = "product", required = false) Long productId,
                                    @RequestParam(name = "page", defaultValue = "0") int page,
                                    @RequestParam(name = "size", defaultValue = "15") int size)
    {
        try {
            List<RatingDto> ratingDtoList = new ArrayList<>();
            Pageable pageable = PageRequest.of(page, size, Sort.by("updatedDate").descending().and(Sort.by("createdDate").descending()));

            Page<RatingDto> pageRatingDto;
            if (productId == null)
                pageRatingDto = ratingService.getAll(pageable);
            else
                pageRatingDto = ratingService.getByProductId(productId, pageable);

            ratingDtoList = pageRatingDto.getContent();
            Map<String, Object> response = new HashMap<>();
            response.put("ratings", ratingDtoList);
            response.put("currentPage", pageRatingDto.getNumber());
            response.put("totalRatings", pageRatingDto.getTotalElements());
            response.put("totalPage", pageRatingDto.getTotalPages());

            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (RuntimeException error) {
            return new ResponseEntity<>(new MessageResponse(error.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<?> getAllByUserId(@PathVariable("userId") Long userId) {
        return new ResponseEntity<>(ratingService.getByUserId(userId), HttpStatus.OK);
    }

    @GetMapping("/product/{productId}")
    public ResponseEntity<?> getAllByProductId(@PathVariable("productId") Long productId) {
        return new ResponseEntity<>(ratingService.getByProductId(productId), HttpStatus.OK);
    }

    @GetMapping("/user/{userId}/product/{productId}")
    public ResponseEntity<?> getById(@PathVariable("userId") Long userId,
                                     @PathVariable("productId") Long productId) {
        return new ResponseEntity<>(ratingService.getById(userId, productId), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN') or (hasRole('CUSTOMER') and #userId==authentication.principal.id)")
    @PutMapping("/user/{userId}/product/{productId}")
    public ResponseEntity<?> updateRating(@PathVariable("userId") Long userId,
                                          @PathVariable("productId") Long productId,
                                          @Validated @RequestBody RatingDto ratingDto) {
        if (!userService.existsById(userId)) {
            return new ResponseEntity<>(new MessageResponse("User with ID "
                    + ratingDto.getUserId()
                    + " does not exist.")
                    , HttpStatus.BAD_REQUEST);
        }

        if (!productService.existsById(productId)) {
            return new ResponseEntity<>(new MessageResponse("Product with ID "
                    + ratingDto.getProductId()
                    + " does not exist.")
                    , HttpStatus.BAD_REQUEST);
        }

        if (ratingDto.getProductId() == null || !productId.equals(ratingDto.getProductId())) {
            ratingDto.setProductId(productId);
        }

        if (ratingDto.getUserId() == null || !userId.equals(ratingDto.getUserId())) {
            ratingDto.setUserId(userId);
        }

        try {
            if (ratingService.updateRating(ratingDto)) {
                return new ResponseEntity<>(HttpStatus.OK);
            }

            return  new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
        } catch(RuntimeException e) {
            return new ResponseEntity<>(new MessageResponse(e.getMessage())
                    , HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('CUSTOMER')")
    @PostMapping
    public ResponseEntity<?> saveRating(@Validated @RequestBody RatingDto ratingDto) {
        if (!userService.existsById(ratingDto.getUserId())) {
            return new ResponseEntity<>(new MessageResponse("User with ID "
                                            + ratingDto.getUserId()
                                            + " does not exist.")
                                        , HttpStatus.BAD_REQUEST);
        }

        if (!productService.existsById(ratingDto.getProductId())) {
            return new ResponseEntity<>(new MessageResponse("Product with ID "
                                            + ratingDto.getProductId()
                                            + " does not exist.")
                                        , HttpStatus.BAD_REQUEST);
        }

        if (ratingService.existsByProductIdAndUserId(ratingDto.getProductId(), ratingDto.getUserId())){
            return new ResponseEntity<>(new MessageResponse("User has rated this product already.")
                    , HttpStatus.BAD_REQUEST);
        }

        try {
            RatingDto savedRatingDto = ratingService.saveRating(ratingDto);

            return new ResponseEntity<>(savedRatingDto, HttpStatus.OK);
        } catch(RuntimeException e) {
            return new ResponseEntity<>(new MessageResponse(e.getMessage())
                                        , HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

}
