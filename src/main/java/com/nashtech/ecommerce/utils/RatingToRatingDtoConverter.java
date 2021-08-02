package com.nashtech.ecommerce.utils;

import com.nashtech.ecommerce.dto.RatingDto;
import com.nashtech.ecommerce.entity.Rating;
import org.modelmapper.AbstractConverter;

public class RatingToRatingDtoConverter extends AbstractConverter<Rating, RatingDto> {
    @Override
    protected RatingDto convert(Rating source) {
        RatingDto dest = new RatingDto();
        dest.setUserId(source.getRatingId().getUserId());
        dest.setUserName(source.getUser().getUsername());
        dest.setProductId(source.getRatingId().getProductId());
        dest.setProductName(source.getProduct().getName());
        dest.setPoint(source.getPoint());
        dest.setContent(source.getContent());
        dest.setCreatedDate(source.getCreatedDate());
        dest.setUpdatedDate(source.getUpdatedDate());
        return dest;
    }
}
